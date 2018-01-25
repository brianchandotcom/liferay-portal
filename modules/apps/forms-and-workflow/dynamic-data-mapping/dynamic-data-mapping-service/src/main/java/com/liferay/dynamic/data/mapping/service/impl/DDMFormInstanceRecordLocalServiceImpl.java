/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.exception.FormInstanceRecordGroupIdException;
import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceRecordException;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.internal.storage.StorageEngineAccessor;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordLocalServiceImpl
	extends DDMFormInstanceRecordLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord addFormInstanceRecord(
			long userId, long groupId, long ddmFormInstanceId,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		DDMFormInstance ddmFormInstance =
			ddmFormInstancePersistence.findByPrimaryKey(ddmFormInstanceId);

		validate(groupId, ddmFormInstance);

		long recordId = counterLocalService.increment();

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.create(recordId);

		ddmFormInstanceRecord.setUuid(serviceContext.getUuid());
		ddmFormInstanceRecord.setGroupId(groupId);
		ddmFormInstanceRecord.setCompanyId(user.getCompanyId());
		ddmFormInstanceRecord.setUserId(user.getUserId());
		ddmFormInstanceRecord.setUserName(user.getFullName());
		ddmFormInstanceRecord.setVersionUserId(user.getUserId());
		ddmFormInstanceRecord.setVersionUserName(user.getFullName());

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		long ddmStorageId = storageEngine.create(
			ddmFormInstance.getCompanyId(), ddmFormInstance.getStructureId(),
			ddmFormValues, serviceContext);

		ddmFormInstanceRecord.setStorageId(ddmStorageId);

		ddmFormInstanceRecord.setFormInstanceId(ddmFormInstanceId);
		ddmFormInstanceRecord.setFormInstanceVersion(
			ddmFormInstance.getVersion());
		ddmFormInstanceRecord.setVersion(_VERSION_DEFAULT);

		ddmFormInstanceRecordPersistence.update(ddmFormInstanceRecord);

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_DRAFT);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			addFormInstanceRecordVersion(
				user, ddmFormInstanceRecord, ddmStorageId, status,
				_VERSION_DEFAULT);

		// Asset

		Locale locale = serviceContext.getLocale();

		updateAsset(
			userId, ddmFormInstanceRecord, ddmFormInstanceRecordVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), locale,
			serviceContext.getAssetPriority());

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
				ddmFormInstanceRecordVersion, serviceContext);
		}

		return ddmFormInstanceRecord;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public DDMFormInstanceRecord deleteFormInstanceRecord(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws PortalException {

		ddmFormInstanceRecordPersistence.remove(ddmFormInstanceRecord);

		List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions =
			ddmFormInstanceRecordVersionPersistence.findByFormInstanceRecordId(
				ddmFormInstanceRecord.getFormInstanceRecordId());

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				ddmFormInstanceRecordVersions) {

			ddmFormInstanceRecordVersionPersistence.remove(
				ddmFormInstanceRecordVersion);

			storageEngine.deleteByClass(
				ddmFormInstanceRecordVersion.getStorageId());

			deleteWorkflowInstanceLink(
				ddmFormInstanceRecord.getCompanyId(),
				ddmFormInstanceRecord.getGroupId(),
				ddmFormInstanceRecordVersion.getPrimaryKey());
		}

		return ddmFormInstanceRecord;
	}

	@Override
	public void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		deleteFormInstanceRecord(ddmFormInstanceRecord);
	}

	@Override
	public void deleteFormInstanceRecords(long ddmFormInstanceId)
		throws PortalException {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords =
			ddmFormInstanceRecordPersistence.findByFormInstanceId(
				ddmFormInstanceId);

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			deleteFormInstanceRecord(ddmFormInstanceRecord);
		}
	}

	@Override
	public DDMFormInstanceRecord fetchFormInstanceRecord(
		long ddmFormInstanceRecordId) {

		return ddmFormInstanceRecordPersistence.fetchByPrimaryKey(
			ddmFormInstanceRecordId);
	}

	@Override
	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException {

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		return storageEngine.getDDMFormValues(ddmStorageId);
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws PortalException {

		return ddmFormInstanceRecordPersistence.findByPrimaryKey(
			ddmFormInstanceRecordId);
	}

	@Override
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId) {

		return ddmFormInstanceRecordPersistence.findByFormInstanceId(
			ddmFormInstanceId);
	}

	@Override
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, int status, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return ddmFormInstanceRecordFinder.findByF_S(
			ddmFormInstanceId, status, start, end, orderByComparator);
	}

	@Override
	public List<DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, long userId, int start, int end,
		OrderByComparator<DDMFormInstanceRecord> orderByComparator) {

		return ddmFormInstanceRecordPersistence.findByU_F(
			userId, ddmFormInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getFormInstanceRecordsCount(long ddmFormInstanceId) {
		return ddmFormInstanceRecordPersistence.countByFormInstanceId(
			ddmFormInstanceId);
	}

	@Override
	public int getFormInstanceRecordsCount(long ddmFormInstanceId, int status) {
		return ddmFormInstanceRecordFinder.countByF_S(
			ddmFormInstanceId, status);
	}

	@Override
	public int getFormInstanceRecordsCount(
		long ddmFormInstanceId, long userId) {

		return ddmFormInstanceRecordPersistence.countByU_F(
			userId, ddmFormInstanceId);
	}

	@Override
	public void revertFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersion(ddmFormInstanceRecordId, version);

		if (!ddmFormInstanceRecordVersion.isApproved()) {
			return;
		}

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		DDMFormValues ddmFormValues = storageEngine.getDDMFormValues(
			ddmFormInstanceRecordVersion.getStorageId());

		serviceContext.setCommand(Constants.REVERT);

		updateFormInstanceRecord(
			userId, ddmFormInstanceRecordId, true, ddmFormValues,
			serviceContext);
	}

	@Override
	public BaseModelSearchResult<DDMFormInstanceRecord>
		searchFormInstanceRecords(SearchContext searchContext) {

		try {
			Indexer<DDMFormInstanceRecord> indexer =
				getDDMFormInstanceRecordIndexer();

			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<DDMFormInstanceRecord> formInstanceRecords =
				getFormInstanceRecords(hits);

			return new BaseModelSearchResult<>(
				formInstanceRecords, hits.getLength());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord updateFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, boolean majorVersion,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		ddmFormInstanceRecord.setModifiedDate(
			serviceContext.getModifiedDate(null));

		ddmFormInstanceRecord = ddmFormInstanceRecordPersistence.update(
			ddmFormInstanceRecord);

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getLatestFormInstanceRecordVersion();

		if (ddmFormInstanceRecordVersion.isApproved()) {
			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			long ddmStorageId = storageEngine.create(
				ddmFormInstance.getCompanyId(),
				ddmFormInstance.getStructureId(), ddmFormValues,
				serviceContext);

			String version = getNextVersion(
				ddmFormInstanceRecordVersion.getVersion(), majorVersion,
				serviceContext.getWorkflowAction());

			ddmFormInstanceRecordVersion = addFormInstanceRecordVersion(
				user, ddmFormInstanceRecord, ddmStorageId,
				WorkflowConstants.STATUS_DRAFT, version);
		}
		else {
			storageEngine.update(
				ddmFormInstanceRecordVersion.getStorageId(), ddmFormValues,
				serviceContext);

			String version = ddmFormInstanceRecordVersion.getVersion();

			updateFormInstanceRecordVersion(
				user, ddmFormInstanceRecordVersion,
				ddmFormInstanceRecordVersion.getStatus(), version,
				serviceContext);
		}

		if (isKeepFormInstanceRecordVersionLabel(
				ddmFormInstanceRecord.getFormInstanceRecordVersion(),
				ddmFormInstanceRecordVersion, serviceContext)) {

			ddmFormInstanceRecordVersionPersistence.remove(
				ddmFormInstanceRecordVersion);

			storageEngine.deleteByClass(
				ddmFormInstanceRecordVersion.getStorageId());

			return ddmFormInstanceRecord;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), ddmFormInstanceRecord.getGroupId(), userId,
				DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
				ddmFormInstanceRecordVersion, serviceContext);
		}

		return ddmFormInstanceRecord;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord updateStatus(
			long userId, long recordVersionId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Record version

		User user = userLocalService.getUser(userId);

		DDMFormInstanceRecordVersion formInstanceRecordVersion =
			ddmFormInstanceRecordVersionPersistence.findByPrimaryKey(
				recordVersionId);

		formInstanceRecordVersion.setStatus(status);
		formInstanceRecordVersion.setStatusByUserId(user.getUserId());
		formInstanceRecordVersion.setStatusByUserName(user.getFullName());
		formInstanceRecordVersion.setStatusDate(new Date());

		ddmFormInstanceRecordVersionPersistence.update(
			formInstanceRecordVersion);

		// Record

		DDMFormInstanceRecord formInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				formInstanceRecordVersion.getFormInstanceRecordId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (DLUtil.compareVersions(
					formInstanceRecord.getVersion(),
					formInstanceRecordVersion.getVersion()) <= 0) {

				formInstanceRecord.setStorageId(
					formInstanceRecordVersion.getStorageId());
				formInstanceRecord.setVersion(
					formInstanceRecordVersion.getVersion());
				formInstanceRecord.setFormInstanceId(
					formInstanceRecordVersion.getFormInstanceId());
				formInstanceRecord.setVersion(
					formInstanceRecordVersion.getVersion());
				formInstanceRecord.setVersionUserId(
					formInstanceRecordVersion.getUserId());
				formInstanceRecord.setVersionUserName(
					formInstanceRecordVersion.getUserName());

				ddmFormInstanceRecordPersistence.update(formInstanceRecord);
			}
		}
		else {
			if (Objects.equals(
					formInstanceRecord.getVersion(),
					formInstanceRecordVersion.getVersion())) {

				String newVersion = _VERSION_DEFAULT;

				List<DDMFormInstanceRecordVersion> approvedRecordVersions =
					ddmFormInstanceRecordVersionPersistence.findByF_S(
						formInstanceRecord.getFormInstanceRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedRecordVersions.isEmpty()) {
					DDMFormInstanceRecordVersion firstApprovedVersion =
						approvedRecordVersions.get(0);

					newVersion = firstApprovedVersion.getVersion();
				}

				formInstanceRecord.setVersion(newVersion);

				ddmFormInstanceRecordPersistence.update(formInstanceRecord);
			}
		}

		return formInstanceRecord;
	}

	protected DDMFormInstanceRecordVersion addFormInstanceRecordVersion(
		User user, DDMFormInstanceRecord ddmFormInstanceRecord,
		long ddmStorageId, int status, String version) {

		long ddmFormInstanceRecordVersionId = counterLocalService.increment();

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecordVersionPersistence.create(
				ddmFormInstanceRecordVersionId);

		ddmFormInstanceRecordVersion.setGroupId(
			ddmFormInstanceRecord.getGroupId());
		ddmFormInstanceRecordVersion.setCompanyId(
			ddmFormInstanceRecord.getCompanyId());
		ddmFormInstanceRecordVersion.setUserId(user.getUserId());
		ddmFormInstanceRecordVersion.setUserName(user.getFullName());
		ddmFormInstanceRecordVersion.setCreateDate(
			ddmFormInstanceRecord.getModifiedDate());
		ddmFormInstanceRecordVersion.setFormInstanceId(
			ddmFormInstanceRecord.getFormInstanceId());
		ddmFormInstanceRecordVersion.setFormInstanceVersion(
			ddmFormInstanceRecord.getFormInstanceVersion());
		ddmFormInstanceRecordVersion.setFormInstanceRecordId(
			ddmFormInstanceRecord.getFormInstanceRecordId());
		ddmFormInstanceRecordVersion.setStorageId(ddmStorageId);
		ddmFormInstanceRecordVersion.setVersion(version);
		ddmFormInstanceRecordVersion.setStatus(status);
		ddmFormInstanceRecordVersion.setStatusByUserId(user.getUserId());
		ddmFormInstanceRecordVersion.setStatusByUserName(user.getFullName());
		ddmFormInstanceRecordVersion.setStatusDate(
			ddmFormInstanceRecord.getModifiedDate());

		ddmFormInstanceRecordVersionPersistence.update(
			ddmFormInstanceRecordVersion);

		return ddmFormInstanceRecordVersion;
	}

	protected void deleteWorkflowInstanceLink(
			long companyId, long groupId, long ddmFormInstanceRecordVersionId)
		throws PortalException {

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecordVersionId);
	}

	protected Indexer<DDMFormInstanceRecord> getDDMFormInstanceRecordIndexer() {
		Indexer<DDMFormInstanceRecord> indexer =
			indexerRegistry.nullSafeGetIndexer(DDMFormInstanceRecord.class);

		return indexer;
	}

	protected List<DDMFormInstanceRecord> getFormInstanceRecords(Hits hits)
		throws PortalException {

		List<DDMFormInstanceRecord> formInstanceRecords = new ArrayList<>();

		for (Document document : hits.toList()) {
			long recordId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				DDMFormInstanceRecord formInstanceRecord =
					getFormInstanceRecord(recordId);

				formInstanceRecords.add(formInstanceRecord);
			}
			catch (NoSuchFormInstanceRecordException nsfire) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDM form instance record index is stale and" +
							"contains record " + recordId,
						nsfire);
				}

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				Indexer<DDMFormInstanceRecord> indexer =
					getDDMFormInstanceRecordIndexer();

				indexer.delete(companyId, document.getUID());
			}
		}

		return formInstanceRecords;
	}

	protected String getNextVersion(
		String version, boolean majorVersion, int workflowAction) {

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected boolean isKeepFormInstanceRecordVersionLabel(
			DDMFormInstanceRecordVersion lastDDMFormInstanceRecordVersion,
			DDMFormInstanceRecordVersion latestDDMFormInstanceRecordVersion,
			ServiceContext serviceContext)
		throws PortalException {

		if (Objects.equals(serviceContext.getCommand(), Constants.REVERT)) {
			return false;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			return false;
		}

		if (Objects.equals(
				lastDDMFormInstanceRecordVersion.getVersion(),
				latestDDMFormInstanceRecordVersion.getVersion())) {

			return false;
		}

		StorageEngine storageEngine = storageEngineAccessor.getStorageEngine();

		DDMFormValues lastDDMFormValues = storageEngine.getDDMFormValues(
			lastDDMFormInstanceRecordVersion.getStorageId());
		DDMFormValues latestDDMFormValues = storageEngine.getDDMFormValues(
			latestDDMFormInstanceRecordVersion.getStorageId());

		if (!lastDDMFormValues.equals(latestDDMFormValues)) {
			return false;
		}

		ExpandoBridge lastExpandoBridge =
			lastDDMFormInstanceRecordVersion.getExpandoBridge();
		ExpandoBridge latestExpandoBridge =
			latestDDMFormInstanceRecordVersion.getExpandoBridge();

		Map<String, Serializable> lastAttributes =
			lastExpandoBridge.getAttributes();
		Map<String, Serializable> latestAttributes =
			latestExpandoBridge.getAttributes();

		if (!lastAttributes.equals(latestAttributes)) {
			return false;
		}

		return true;
	}

	protected void updateAsset(
			long userId, DDMFormInstanceRecord formInstanceRecord,
			DDMFormInstanceRecordVersion formInstanceRecordVersion,
			long[] assetCategoryIds, String[] assetTagNames, Locale locale,
			Double priority)
		throws PortalException {

		boolean addDraftAssetEntry = false;

		if ((formInstanceRecordVersion != null) &&
			!formInstanceRecordVersion.isApproved()) {

			String version = formInstanceRecordVersion.getVersion();

			if (!version.equals(_VERSION_DEFAULT)) {
				int approvedRecordVersionsCount =
					ddmFormInstanceRecordVersionPersistence.countByF_S(
						formInstanceRecord.getFormInstanceRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (approvedRecordVersionsCount > 0) {
					addDraftAssetEntry = true;
				}
			}
		}

		DDMFormInstance formInstance = formInstanceRecord.getFormInstance();

		DDMStructure ddmStructure = formInstance.getStructure();

		String ddmStructureName = ddmStructure.getName(locale);

		String recordSetName = formInstance.getName(locale);

		String title = LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, recordSetName}, false);

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, formInstanceRecord.getGroupId(),
				formInstanceRecord.getCreateDate(),
				formInstanceRecord.getModifiedDate(),
				DDMFormInstanceRecord.class.getName(),
				formInstanceRecordVersion.getFormInstanceRecordVersionId(),
				formInstanceRecord.getUuid(), 0, assetCategoryIds,
				assetTagNames, true, false, null, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, priority);
		}
		else {
			assetEntryLocalService.updateEntry(
				userId, formInstanceRecord.getGroupId(),
				formInstanceRecord.getCreateDate(),
				formInstanceRecord.getModifiedDate(),
				DDMFormInstanceRecord.class.getName(),
				formInstanceRecord.getFormInstanceRecordId(),
				formInstanceRecord.getUuid(), 0, assetCategoryIds,
				assetTagNames, true, false, null, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, priority);
		}
	}

	protected void updateFormInstanceRecordVersion(
		User user, DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
		int status, String version, ServiceContext serviceContext) {

		ddmFormInstanceRecordVersion.setUserId(user.getUserId());
		ddmFormInstanceRecordVersion.setUserName(user.getFullName());
		ddmFormInstanceRecordVersion.setVersion(version);
		ddmFormInstanceRecordVersion.setStatus(status);
		ddmFormInstanceRecordVersion.setStatusByUserId(user.getUserId());
		ddmFormInstanceRecordVersion.setStatusByUserName(user.getFullName());
		ddmFormInstanceRecordVersion.setStatusDate(
			serviceContext.getModifiedDate(null));

		ddmFormInstanceRecordVersionPersistence.update(
			ddmFormInstanceRecordVersion);
	}

	protected void validate(long groupId, DDMFormInstance ddmFormInstance)
		throws PortalException {

		if (ddmFormInstance.getGroupId() != groupId) {
			throw new FormInstanceRecordGroupIdException(
				"Record group ID is not the same as the form instance group " +
					"ID");
		}
	}

	@ServiceReference(type = IndexerRegistry.class)
	protected IndexerRegistry indexerRegistry;

	@ServiceReference(type = StorageEngineAccessor.class)
	protected StorageEngineAccessor storageEngineAccessor;

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.UID};

	private static final String _VERSION_DEFAULT = "1.0";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordLocalServiceImpl.class);

}