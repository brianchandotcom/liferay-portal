/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatalists.util.comparator.DDLRecordVersionVersionComparator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data list (DDL) records.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class DDLRecordLocalServiceImpl extends DDLRecordLocalServiceBaseImpl {

	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			Fields fields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		long recordId = counterLocalService.increment();

		DDLRecord record = ddlRecordPersistence.create(recordId);

		record.setUuid(serviceContext.getUuid());
		record.setGroupId(groupId);
		record.setCompanyId(user.getCompanyId());
		record.setUserId(user.getUserId());
		record.setUserName(user.getFullName());
		record.setVersionUserId(user.getUserId());
		record.setVersionUserName(user.getFullName());
		record.setCreateDate(serviceContext.getCreateDate(now));
		record.setModifiedDate(serviceContext.getModifiedDate(now));

		long ddmStorageId = StorageEngineUtil.create(
			recordSet.getCompanyId(), recordSet.getDDMStructureId(), fields,
			serviceContext);

		record.setDDMStorageId(ddmStorageId);

		record.setRecordSetId(recordSetId);
		record.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		record.setDisplayIndex(displayIndex);

		ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = addRecordVersion(
			user, record, ddmStorageId, DDLRecordConstants.VERSION_DEFAULT,
			displayIndex, WorkflowConstants.STATUS_DRAFT);

		// Asset

		Locale locale = serviceContext.getLocale();

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), locale);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, DDLRecord.class.getName(),
			recordVersion.getRecordVersionId(), recordVersion, serviceContext);

		return record;
	}

	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			Map<String, Serializable> fieldsMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = toFields(ddmStructure.getStructureId(), fieldsMap);

		return addRecord(
			userId, groupId, recordSetId, displayIndex, fields, serviceContext);
	}

	public void deleteRecord(DDLRecord record)
		throws PortalException, SystemException {

		// Record

		String dirName = DDMUtil.getFileUploadPath(record);

		ddlRecordPersistence.remove(record);

		// Record Versions

		List<DDLRecordVersion> recordVersions =
			ddlRecordVersionPersistence.findByRecordId(record.getRecordId());

		for (DDLRecordVersion recordVersion : recordVersions) {
			ddlRecordVersionPersistence.remove(recordVersion);

			// Dynamic data mapping storage

			StorageEngineUtil.deleteByClass(recordVersion.getDDMStorageId());

			// Workflow

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				record.getCompanyId(), record.getGroupId(),
				DDLRecord.class.getName(), recordVersion.getPrimaryKey());
		}

		// Indexer

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DDLRecord.class);

		indexer.delete(record);

		// Document library

		try {
			DLStoreUtil.deleteDirectory(
				record.getCompanyId(), CompanyConstants.SYSTEM, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteRecord(long recordId)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		deleteRecord(record);
	}

	public DDLRecord deleteRecordLocale(
			long recordId, Locale locale, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		Fields fields = StorageEngineUtil.getFields(record.getDDMStorageId());

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			Map<Locale, List<Serializable>> valuesMap = field.getValuesMap();

			valuesMap.remove(locale);
		}

		return updateRecord(
			serviceContext.getUserId(), recordId, false,
			DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields, false,
			serviceContext);
	}

	public void deleteRecords(long recordSetId)
		throws PortalException, SystemException {

		List<DDLRecord> records = ddlRecordPersistence.findByRecordSetId(
			recordSetId);

		for (DDLRecord record : records) {
			deleteRecord(record);
		}
	}

	public DDLRecord fetchRecord(long recordId) throws SystemException {
		return ddlRecordPersistence.fetchByPrimaryKey(recordId);
	}

	public List<DDLRecord> getCompanyRecords(
			long companyId, int status, int scope, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlRecordFinder.findByC_S_S(
			companyId, status, scope, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getCompanyRecords(long, int,
	 *             int, int, int, OrderByComparator)}
	 */
	public List<DDLRecord> getCompanyRecords(
			long companyId, int scope, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return getCompanyRecords(
			companyId, WorkflowConstants.STATUS_ANY, scope, start, end,
			orderByComparator);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getCompanyRecordsCount(long,
	 *             int, int)}
	 */
	public int getCompanyRecordsCount(long companyId, int scope)
		throws SystemException {

		return getCompanyRecordsCount(
			companyId, WorkflowConstants.STATUS_ANY, scope);
	}

	public int getCompanyRecordsCount(long companyId, int status, int scope)
		throws SystemException {

		return ddlRecordFinder.countByC_S_S(companyId, status, scope);
	}

	public DDLRecordVersion getLatestRecordVersion(long recordId)
		throws PortalException, SystemException {

		List<DDLRecordVersion> recordVersions =
			ddlRecordVersionPersistence.findByRecordId(recordId);

		if (recordVersions.isEmpty()) {
			throw new NoSuchRecordVersionException(
				"No record versions found for recordId " + recordId);
		}

		recordVersions = ListUtil.copy(recordVersions);

		Collections.sort(
			recordVersions, new DDLRecordVersionVersionComparator());

		return recordVersions.get(0);
	}

	public Long[] getMinAndMaxCompanyRecordIds(
			long companyId, int status, int scope)
		throws SystemException {

		return ddlRecordFinder.findByC_S_S_MinAndMax(companyId, status, scope);
	}

	public List<DDLRecord> getMinAndMaxCompanyRecords(
			long companyId, int status, int scope, long minRecordId,
			long maxRecordId)
		throws SystemException {

		return ddlRecordFinder.findByC_S_S_MinAndMax(
			companyId, status, scope, minRecordId, maxRecordId);
	}

	public DDLRecord getRecord(long recordId)
		throws PortalException, SystemException {

		return ddlRecordPersistence.findByPrimaryKey(recordId);
	}

	public List<DDLRecord> getRecords(long recordSetId) throws SystemException {
		return ddlRecordPersistence.findByRecordSetId(recordSetId);
	}

	public List<DDLRecord> getRecords(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlRecordFinder.findByR_S(
			recordSetId, status, start, end, orderByComparator);
	}

	public List<DDLRecord> getRecords(long recordSetId, long userId)
		throws SystemException {

		return ddlRecordPersistence.findByR_U(recordSetId, userId);
	}

	public int getRecordsCount(long recordSetId, int status)
		throws SystemException {

		return ddlRecordFinder.countByR_S(recordSetId, status);
	}

	public DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException, SystemException {

		return ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);
	}

	public DDLRecordVersion getRecordVersion(long recordId, String version)
		throws PortalException, SystemException {

		return ddlRecordVersionPersistence.findByR_V(recordId, version);
	}

	public List<DDLRecordVersion> getRecordVersions(
			long recordId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlRecordVersionPersistence.findByRecordId(
			recordId, start, end, orderByComparator);
	}

	public int getRecordVersionsCount(long recordId) throws SystemException {
		return ddlRecordVersionPersistence.countByRecordId(recordId);
	}

	public void revertRecordVersion(
			long userId, long recordId, String version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecordVersion recordVersion = getRecordVersion(recordId, version);

		if (!recordVersion.isApproved()) {
			return;
		}

		Fields fields = StorageEngineUtil.getFields(
			recordVersion.getDDMStorageId());

		updateRecord(
			userId, recordId, true, recordVersion.getDisplayIndex(), fields,
			false, serviceContext);
	}

	public Hits search(SearchContext searchContext) throws SystemException {
		try {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DDLRecord.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void updateAsset(
			long userId, DDLRecord record, DDLRecordVersion recordVersion,
			long[] assetCategoryIds, String[] assetTagNames, Locale locale)
		throws PortalException, SystemException {

		boolean addDraftAssetEntry = false;

		if ((recordVersion != null) && !recordVersion.isApproved()) {
			String version = recordVersion.getVersion();

			if (!version.equals(DDLRecordConstants.VERSION_DEFAULT)) {
				int approvedRecordVersionsCount =
					ddlRecordVersionPersistence.countByR_S(
						record.getRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (approvedRecordVersionsCount > 0) {
					addDraftAssetEntry = true;
				}
			}
		}

		boolean visible = false;

		if ((recordVersion != null) && !recordVersion.isApproved()) {
			visible = false;
		}

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		String ddmStructureName = ddmStructure.getName(locale);

		String recordSetName = recordSet.getName(locale);

		String title = LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, recordSetName});

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(), DDLRecordConstants.getClassName(),
				recordVersion.getRecordVersionId(), record.getUuid(), 0,
				assetCategoryIds, assetTagNames, false, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, null, false);
		}
		else {
			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(), DDLRecordConstants.getClassName(),
				record.getRecordId(), record.getUuid(), 0, assetCategoryIds,
				assetTagNames, visible, null, null, null,
				ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null,
				null, 0, 0, null, false);
		}
	}

	public DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			Fields fields, boolean mergeFields, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));

		ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		if (recordVersion.isApproved()) {
			DDLRecordSet recordSet = record.getRecordSet();

			if (mergeFields) {
				Fields existingFields = StorageEngineUtil.getFields(
					recordVersion.getDDMStorageId());

				fields = DDMUtil.mergeFields(fields, existingFields);
			}

			long ddmStorageId = StorageEngineUtil.create(
				recordSet.getCompanyId(), recordSet.getDDMStructureId(), fields,
				serviceContext);
			String version = getNextVersion(
				recordVersion.getVersion(), majorVersion,
				serviceContext.getWorkflowAction());

			recordVersion = addRecordVersion(
				user, record, ddmStorageId, version, displayIndex,
				WorkflowConstants.STATUS_DRAFT);
		}
		else {
			StorageEngineUtil.update(
				recordVersion.getDDMStorageId(), fields, mergeFields,
				serviceContext);

			String version = recordVersion.getVersion();

			updateRecordVersion(
				user, recordVersion, version, displayIndex,
				recordVersion.getStatus(), serviceContext);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), record.getGroupId(), userId,
			DDLRecord.class.getName(), recordVersion.getRecordVersionId(),
			recordVersion, serviceContext);

		return record;
	}

	public DDLRecord updateRecord(
			long userId, long recordId, int displayIndex,
			Map<String, Serializable> fieldsMap, boolean mergeFields,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = toFields(ddmStructure.getStructureId(), fieldsMap);

		return updateRecord(
			userId, recordId, false, displayIndex, fields, mergeFields,
			serviceContext);
	}

	public DDLRecord updateStatus(
			long userId, long recordVersionId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record version

		User user = userPersistence.findByPrimaryKey(userId);

		DDLRecordVersion recordVersion =
			ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);

		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(new Date());

		ddlRecordVersionPersistence.update(recordVersion);

		// Record

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(
			recordVersion.getRecordId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (DLUtil.compareVersions(
					record.getVersion(), recordVersion.getVersion()) <= 0) {

				record.setDDMStorageId(recordVersion.getDDMStorageId());
				record.setVersion(recordVersion.getVersion());
				record.setRecordSetId(recordVersion.getRecordSetId());
				record.setDisplayIndex(recordVersion.getDisplayIndex());
				record.setVersion(recordVersion.getVersion());
				record.setVersionUserId(recordVersion.getUserId());
				record.setVersionUserName(recordVersion.getUserName());
				record.setModifiedDate(recordVersion.getCreateDate());

				ddlRecordPersistence.update(record);
			}
		}
		else {
			if (Validator.equals(
					record.getVersion(), recordVersion.getVersion())) {

				String newVersion = DDLRecordConstants.VERSION_DEFAULT;

				List<DDLRecordVersion> approvedRecordVersions =
					ddlRecordVersionPersistence.findByR_S(
						record.getRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedRecordVersions.isEmpty()) {
					newVersion = approvedRecordVersions.get(0).getVersion();
				}

				record.setVersion(newVersion);

				ddlRecordPersistence.update(record);
			}

			// Indexer

			if (Validator.equals(
					recordVersion.getVersion(),
					DDLRecordConstants.VERSION_DEFAULT)) {

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DDLRecord.class);

				indexer.delete(record);
			}
		}

		// Indexer

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DDLRecord.class);

			indexer.reindex(record);
		}

		return record;
	}

	protected DDLRecordVersion addRecordVersion(
			User user, DDLRecord record, long ddmStorageId, String version,
			int displayIndex, int status)
		throws SystemException {

		long recordVersionId = counterLocalService.increment();

		DDLRecordVersion recordVersion = ddlRecordVersionPersistence.create(
			recordVersionId);

		recordVersion.setGroupId(record.getGroupId());
		recordVersion.setCompanyId(record.getCompanyId());

		long versionUserId = record.getVersionUserId();

		if (versionUserId <= 0) {
			versionUserId = record.getUserId();
		}

		recordVersion.setUserId(versionUserId);

		String versionUserName = GetterUtil.getString(
			record.getVersionUserName(), record.getUserName());

		recordVersion.setUserName(versionUserName);

		recordVersion.setCreateDate(record.getModifiedDate());
		recordVersion.setDDMStorageId(ddmStorageId);
		recordVersion.setRecordSetId(record.getRecordSetId());
		recordVersion.setRecordId(record.getRecordId());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(record.getModifiedDate());

		ddlRecordVersionPersistence.update(recordVersion);

		return recordVersion;
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

	protected Fields toFields(
		long ddmStructureId, Map<String, Serializable> fieldsMap) {

		Fields fields = new Fields();

		for (String name : fieldsMap.keySet()) {
			String value = String.valueOf(fieldsMap.get(name));

			Field field = new Field(ddmStructureId, name, value);

			fields.put(field);
		}

		return fields;
	}

	protected void updateRecordVersion(
			User user, DDLRecordVersion recordVersion, String version,
			int displayIndex, int status, ServiceContext serviceContext)
		throws SystemException {

		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(serviceContext.getModifiedDate(null));

		ddlRecordVersionPersistence.update(recordVersion);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDLRecordLocalServiceImpl.class);

}