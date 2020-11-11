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
import com.liferay.dynamic.data.mapping.internal.notification.DDMFormEmailNotificationSender;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord",
	service = AopService.class
)
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

		long ddmStorageId = createDDMContent(
			ddmFormInstanceId, ddmFormValues, serviceContext);

		ddmFormInstanceRecord.setStorageId(ddmStorageId);

		ddmFormInstanceRecord.setFormInstanceId(ddmFormInstanceId);
		ddmFormInstanceRecord.setFormInstanceVersion(
			ddmFormInstance.getVersion());
		ddmFormInstanceRecord.setVersion(_VERSION_DEFAULT);

		ddmFormInstanceRecord = ddmFormInstanceRecordPersistence.update(
			ddmFormInstanceRecord);

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_DRAFT);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			addFormInstanceRecordVersion(
				user, ddmFormInstanceRecord, ddmStorageId, status,
				_VERSION_DEFAULT);

		// Asset

		updateAsset(
			userId, ddmFormInstanceRecord, ddmFormInstanceRecordVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
				ddmFormInstanceRecordVersion, serviceContext);

			if (isEmailNotificationEnabled(ddmFormInstance)) {
				_ddmFormEmailNotificationSender.sendEmailNotification(
					serviceContext, ddmFormInstanceRecord);
			}
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

		String storageType = ddmFormInstanceRecord.getStorageType();

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				ddmFormInstanceRecordVersions) {

			ddmFormInstanceRecordVersionPersistence.remove(
				ddmFormInstanceRecordVersion);

			long storageId = ddmFormInstanceRecordVersion.getStorageId();

			deleteStorage(storageId, storageType);

			_ddmStorageLinkLocalService.deleteClassStorageLink(storageId);

			deleteWorkflowInstanceLink(
				ddmFormInstanceRecord.getCompanyId(),
				ddmFormInstanceRecord.getGroupId(),
				ddmFormInstanceRecordVersion.getPrimaryKey());
		}

		assetEntryLocalService.deleteEntry(
			DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecord.getFormInstanceRecordId());

		return ddmFormInstanceRecord;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDMFormInstanceRecord deleteFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws PortalException {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		return ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecord);
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
	public DDMFormValues getDDMFormValues(
			DDMForm ddmForm, long storageId, String storageType)
		throws StorageException {

		DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter(storageType);

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			ddmStorageAdapter.get(
				DDMStorageAdapterGetRequest.Builder.newBuilder(
					storageId, ddmForm
				).build());

		return ddmStorageAdapterGetResponse.getDDMFormValues();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDDMFormValues(DDMForm, long, String)}
	 */
	@Deprecated
	@Override
	public DDMFormValues getDDMFormValues(long storageId, DDMForm ddmForm)
		throws StorageException {

		return getDDMFormValues(
			ddmForm, storageId, StorageType.JSON.toString());
	}

	@Override
	public DDMFormInstanceRecord getFormInstanceRecord(
			long formInstanceRecordId)
		throws PortalException {

		return ddmFormInstanceRecordPersistence.findByPrimaryKey(
			formInstanceRecordId);
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
			_ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersion(ddmFormInstanceRecordId, version);

		if (!ddmFormInstanceRecordVersion.isApproved()) {
			return;
		}

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		DDMFormValues ddmFormValues = getDDMFormValues(
			ddmFormInstanceRecordVersion.getDDMForm(),
			ddmFormInstanceRecordVersion.getStorageId(),
			ddmFormInstance.getStorageType());

		serviceContext.setCommand(Constants.REVERT);

		updateFormInstanceRecord(
			userId, ddmFormInstanceRecordId, true, ddmFormValues,
			serviceContext);
	}

	@Override
	public BaseModelSearchResult<DDMFormInstanceRecord>
			searchFormInstanceRecords(
				long formInstanceId, String[] notEmptyFields, int status,
				int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = _buildSearchContext(
			formInstanceId, notEmptyFields, status, start, end, sort);

		return searchFormInstanceRecords(searchContext);
	}

	@Override
	public BaseModelSearchResult<DDMFormInstanceRecord>
		searchFormInstanceRecords(SearchContext searchContext) {

		try {
			Indexer<DDMFormInstanceRecord> indexer =
				getDDMFormInstanceRecordIndexer();

			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			return new BaseModelSearchResult<>(
				getFormInstanceRecords(hits), hits.getLength());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDMFormInstanceRecord updateFormInstanceRecord(
			long userId, long ddmFormInstanceRecordId, boolean majorVersion,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		validate(ddmFormValues, serviceContext);

		User user = userLocalService.getUser(userId);

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				ddmFormInstanceRecordId);

		ddmFormInstanceRecord.setModifiedDate(
			serviceContext.getModifiedDate(null));

		ddmFormInstanceRecord = ddmFormInstanceRecordPersistence.update(
			ddmFormInstanceRecord);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			ddmFormInstanceRecord.getLatestFormInstanceRecordVersion();

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		if (ddmFormInstanceRecordVersion.isApproved()) {
			long ddmStorageId = createDDMContent(
				ddmFormInstance.getFormInstanceId(), ddmFormValues,
				serviceContext);

			String version = getNextVersion(
				ddmFormInstanceRecordVersion.getVersion(), majorVersion,
				serviceContext.getWorkflowAction());

			ddmFormInstanceRecordVersion = addFormInstanceRecordVersion(
				user, ddmFormInstanceRecord, ddmStorageId,
				WorkflowConstants.STATUS_DRAFT, version);
		}
		else {
			updateDDMContent(
				ddmFormInstanceRecordVersion, ddmFormValues, serviceContext);

			String version = ddmFormInstanceRecordVersion.getVersion();

			updateFormInstanceRecordVersion(
				user, ddmFormInstanceRecordVersion,
				ddmFormInstanceRecordVersion.getStatus(), version,
				serviceContext);

			ddmFormInstanceRecordVersionPersistence.clearCache(
				ddmFormInstanceRecordVersion);
		}

		// Asset

		updateAsset(
			userId, ddmFormInstanceRecord, ddmFormInstanceRecordVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		if (isKeepFormInstanceRecordVersionLabel(
				ddmFormInstanceRecord.getFormInstanceRecordVersion(),
				ddmFormInstanceRecordVersion, serviceContext)) {

			ddmFormInstanceRecordVersionPersistence.remove(
				ddmFormInstanceRecordVersion);

			deleteStorage(
				ddmFormInstanceRecordVersion.getStorageId(),
				ddmFormInstance.getStorageType());

			return ddmFormInstanceRecord;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), ddmFormInstanceRecord.getGroupId(), userId,
				DDMFormInstanceRecord.class.getName(),
				ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId(),
				ddmFormInstanceRecordVersion, serviceContext);

			if (isEmailNotificationEnabled(ddmFormInstance)) {
				_ddmFormEmailNotificationSender.sendEmailNotification(
					serviceContext, ddmFormInstanceRecord);
			}
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

		formInstanceRecordVersion =
			ddmFormInstanceRecordVersionPersistence.update(
				formInstanceRecordVersion);

		// Record

		DDMFormInstanceRecord formInstanceRecord =
			ddmFormInstanceRecordPersistence.findByPrimaryKey(
				formInstanceRecordVersion.getFormInstanceRecordId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			int value = DLUtil.compareVersions(
				formInstanceRecord.getVersion(),
				formInstanceRecordVersion.getVersion());

			if (value <= 0) {
				formInstanceRecord.setVersionUserId(
					formInstanceRecordVersion.getUserId());
				formInstanceRecord.setVersionUserName(
					formInstanceRecordVersion.getUserName());
				formInstanceRecord.setFormInstanceId(
					formInstanceRecordVersion.getFormInstanceId());
				formInstanceRecord.setStorageId(
					formInstanceRecordVersion.getStorageId());
				formInstanceRecord.setVersion(
					formInstanceRecordVersion.getVersion());

				formInstanceRecord = ddmFormInstanceRecordPersistence.update(
					formInstanceRecord);
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

				formInstanceRecord =
					ddmFormInstanceRecordPersistence.findByPrimaryKey(
						formInstanceRecordVersion.getFormInstanceRecordId());

				formInstanceRecord.setVersion(newVersion);

				formInstanceRecord = ddmFormInstanceRecordPersistence.update(
					formInstanceRecord);
			}
			else if (formInstanceRecord.getStatus() ==
						WorkflowConstants.STATUS_APPROVED) {

				updateFormInstanceRecordVersion(
					user, formInstanceRecordVersion,
					WorkflowConstants.STATUS_APPROVED,
					formInstanceRecordVersion.getVersion(), serviceContext);

				formInstanceRecord.setVersion(
					formInstanceRecordVersion.getVersion());

				formInstanceRecord = ddmFormInstanceRecordPersistence.update(
					formInstanceRecord);
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
		ddmFormInstanceRecordVersion.setVersion(version);
		ddmFormInstanceRecordVersion.setStorageId(ddmStorageId);
		ddmFormInstanceRecordVersion.setStatus(status);
		ddmFormInstanceRecordVersion.setStatusByUserId(user.getUserId());
		ddmFormInstanceRecordVersion.setStatusByUserName(user.getFullName());
		ddmFormInstanceRecordVersion.setStatusDate(
			ddmFormInstanceRecord.getModifiedDate());

		return ddmFormInstanceRecordVersionPersistence.update(
			ddmFormInstanceRecordVersion);
	}

	protected long createDDMContent(
			long ddmFormInstanceId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws PortalException {

		validate(ddmFormValues, serviceContext);

		DDMFormInstance ddmFormInstance =
			ddmFormInstancePersistence.findByPrimaryKey(ddmFormInstanceId);

		DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter(
			ddmFormInstance.getStorageType());

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			ddmStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), ddmFormValues
				).withUuid(
					serviceContext.getUuid()
				).withClassName(
					DDMStorageLink.class.getName()
				).build());

		long primaryKey = ddmStorageAdapterSaveResponse.getPrimaryKey();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getLatestStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			_portal.getClassNameId(DDMContent.class.getName()), primaryKey,
			ddmStructureVersion.getStructureVersionId(), serviceContext);

		return primaryKey;
	}

	protected void deleteStorage(long storageId, String storageType)
		throws StorageException {

		DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter(storageType);

		ddmStorageAdapter.delete(
			DDMStorageAdapterDeleteRequest.Builder.newBuilder(
				storageId
			).build());
	}

	protected void deleteWorkflowInstanceLink(
			long companyId, long groupId, long ddmFormInstanceRecordVersionId)
		throws PortalException {

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecordVersionId);
	}

	protected Indexer<DDMFormInstanceRecord> getDDMFormInstanceRecordIndexer() {
		return _indexerRegistry.nullSafeGetIndexer(DDMFormInstanceRecord.class);
	}

	protected DDMStorageAdapter getDDMStorageAdapter(String type) {
		return _ddmStorageAdapterTracker.getDDMStorageAdapter(
			GetterUtil.getString(type, StorageType.JSON.toString()));
	}

	protected List<DDMFormInstanceRecord> getFormInstanceRecords(Hits hits)
		throws PortalException {

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		for (Document document : hits.toList()) {
			long formInstanceRecordId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				ddmFormInstanceRecords.add(
					getFormInstanceRecord(formInstanceRecordId));
			}
			catch (NoSuchFormInstanceRecordException
						noSuchFormInstanceRecordException) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDM form instance record index is stale and" +
							"contains record " + formInstanceRecordId,
						noSuchFormInstanceRecordException);
				}

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				Indexer<DDMFormInstanceRecord> indexer =
					getDDMFormInstanceRecordIndexer();

				indexer.delete(companyId, document.getUID());
			}
		}

		return ddmFormInstanceRecords;
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

	protected ResourceBundle getResourceBundle(Locale defaultLocale) {
		return _portal.getResourceBundle(defaultLocale);
	}

	protected boolean isEmailNotificationEnabled(
			DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceSettings formInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return formInstanceSettings.sendEmailNotification();
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

		DDMFormInstance lastFormInstance =
			lastDDMFormInstanceRecordVersion.getFormInstance();

		DDMFormValues lastDDMFormValues = getDDMFormValues(
			lastDDMFormInstanceRecordVersion.getDDMForm(),
			lastDDMFormInstanceRecordVersion.getStorageId(),
			lastFormInstance.getStorageType());

		DDMFormInstance latestFormInstance =
			latestDDMFormInstanceRecordVersion.getFormInstance();

		DDMFormValues latestDDMFormValues = getDDMFormValues(
			latestDDMFormInstanceRecordVersion.getDDMForm(),
			latestDDMFormInstanceRecordVersion.getStorageId(),
			latestFormInstance.getStorageType());

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

		String title = LanguageUtil.format(
			getResourceBundle(locale), "form-record-for-form-x",
			formInstance.getName(locale), false);

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, formInstanceRecord.getGroupId(),
				formInstanceRecord.getCreateDate(),
				formInstanceRecord.getModifiedDate(),
				DDMFormInstanceRecord.class.getName(),
				formInstanceRecordVersion.getFormInstanceRecordVersionId(),
				formInstanceRecord.getUuid(), 0, assetCategoryIds,
				assetTagNames, true, true, null, null, null, null,
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
				assetTagNames, true, true, null, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, priority);
		}
	}

	protected void updateDDMContent(
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		validate(ddmFormValues, serviceContext);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecordVersion.getFormInstance();

		DDMStorageAdapter ddmStorageAdapter = getDDMStorageAdapter(
			ddmFormInstance.getStorageType());

		ddmStorageAdapter.save(
			DDMStorageAdapterSaveRequest.Builder.newBuilder(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				ddmFormValues
			).withPrimaryKey(
				ddmFormInstanceRecordVersion.getStorageId()
			).build());
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

	protected void validate(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		boolean validateDDMFormValues = GetterUtil.getBoolean(
			serviceContext.getAttribute("validateDDMFormValues"), true);

		if (!validateDDMFormValues) {
			return;
		}

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	protected void validate(long groupId, DDMFormInstance ddmFormInstance)
		throws PortalException {

		if (ddmFormInstance.getGroupId() != groupId) {
			throw new FormInstanceRecordGroupIdException(
				"Record group ID is not the same as the form instance group " +
					"ID");
		}
	}

	private SearchContext _buildSearchContext(
			long formInstanceId, String[] notEmptyFields, int status, int start,
			int end, Sort sort)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			ddmFormInstancePersistence.findByPrimaryKey(formInstanceId);

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CLASS_NAME_ID,
				_classNameLocalService.getClassNameId(DDMFormInstance.class)
			).put(
				Field.STATUS, status
			).put(
				"formInstanceId", ddmFormInstance.getFormInstanceId()
			).put(
				"languageIds", ddmFormInstance.getAvailableLanguageIds()
			).put(
				"notEmptyFields", notEmptyFields
			).put(
				"structureId", ddmFormInstance.getStructureId()
			).build());

		searchContext.setCompanyId(ddmFormInstance.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {ddmFormInstance.getGroupId()});
		searchContext.setSorts(sort);
		searchContext.setStart(start);

		return searchContext;
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.MODIFIED_DATE, Field.UID
	};

	private static final String _VERSION_DEFAULT = "1.0";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordLocalServiceImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDMFormEmailNotificationSender _ddmFormEmailNotificationSender;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DDMFormValuesValidator _ddmFormValuesValidator;

	@Reference
	private DDMStorageAdapterTracker _ddmStorageAdapterTracker;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

}