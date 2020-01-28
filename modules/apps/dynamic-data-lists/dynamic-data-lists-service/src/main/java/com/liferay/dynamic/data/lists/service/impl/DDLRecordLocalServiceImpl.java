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

package com.liferay.dynamic.data.lists.service.impl;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.lists.exception.NoSuchRecordException;
import com.liferay.dynamic.data.lists.exception.RecordGroupIdException;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService;
import com.liferay.dynamic.data.lists.service.base.DDLRecordLocalServiceBaseImpl;
import com.liferay.dynamic.data.lists.util.DDL;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the local service for accessing, adding, deleting, and updating
 * dynamic data lists (DDL) records.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.lists.model.DDLRecord",
	service = AopService.class
)
public class DDLRecordLocalServiceImpl extends DDLRecordLocalServiceBaseImpl {

	/**
	 * Adds a record referencing the record set.
	 *
	 * @param  userId the primary key of the record's creator/owner
	 * @param  groupId the primary key of the record's group
	 * @param  recordSetId the primary key of the record set
	 * @param  displayIndex the index position in which the record is displayed
	 *         in the spreadsheet view
	 * @param  ddmFormValues the record values. See <code>DDMFormValues</code>
	 *         in the <code>dynamic.data.mapping.api</code> module.
	 * @param  serviceContext the service context to be applied. This can set
	 *         the UUID, guest permissions, and group permissions for the
	 *         record.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userLocalService.getUser(userId);

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		validate(groupId, recordSet);

		long recordId = counterLocalService.increment();

		DDLRecord record = ddlRecordPersistence.create(recordId);

		record.setUuid(serviceContext.getUuid());
		record.setGroupId(groupId);
		record.setCompanyId(user.getCompanyId());
		record.setUserId(user.getUserId());
		record.setUserName(user.getFullName());
		record.setVersionUserId(user.getUserId());
		record.setVersionUserName(user.getFullName());

		long ddmStorageId = storageEngine.create(
			recordSet.getCompanyId(), recordSet.getDDMStructureId(),
			ddmFormValues, serviceContext);

		record.setDDMStorageId(ddmStorageId);

		record.setRecordSetId(recordSetId);
		record.setRecordSetVersion(recordSet.getVersion());
		record.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		record.setDisplayIndex(displayIndex);

		record = ddlRecordPersistence.update(record);

		// Record version

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_DRAFT);

		DDLRecordVersion recordVersion = addRecordVersion(
			user, record, ddmStorageId, DDLRecordConstants.VERSION_DEFAULT,
			displayIndex, status);

		// Asset

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		// Workflow

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), groupId, userId,
				getWorkflowAssetClassName(recordSet),
				recordVersion.getRecordVersionId(), recordVersion,
				serviceContext);
		}

		return record;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord addRecord(
			long userId, long groupId, long ddmStorageId, long ddlRecordSetId,
			ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userLocalService.getUser(userId);

		DDLRecordSet ddlRecordSet = ddlRecordSetPersistence.findByPrimaryKey(
			ddlRecordSetId);

		validate(groupId, ddlRecordSet);

		long recordId = counterLocalService.increment();

		DDLRecord record = ddlRecordPersistence.create(recordId);

		record.setUuid(serviceContext.getUuid());
		record.setGroupId(groupId);
		record.setCompanyId(user.getCompanyId());
		record.setUserId(user.getUserId());
		record.setUserName(user.getFullName());
		record.setVersionUserId(user.getUserId());
		record.setVersionUserName(user.getFullName());
		record.setDDMStorageId(ddmStorageId);
		record.setRecordSetId(ddlRecordSetId);
		record.setRecordSetVersion(ddlRecordSet.getVersion());
		record.setVersion(DDLRecordConstants.VERSION_DEFAULT);
		record.setDisplayIndex(0);

		record = ddlRecordPersistence.update(record);

		// Record version

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		addRecordVersion(
			user, record, ddmStorageId, DDLRecordConstants.VERSION_DEFAULT, 0,
			status);

		return record;
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param  record the record to be deleted
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public DDLRecord deleteRecord(DDLRecord record) throws PortalException {

		// Record

		ddlRecordPersistence.remove(record);

		// Record Versions

		List<DDLRecordVersion> recordVersions =
			ddlRecordVersionPersistence.findByRecordId(record.getRecordId());

		for (DDLRecordVersion recordVersion : recordVersions) {
			ddlRecordVersionPersistence.remove(recordVersion);

			// Dynamic data mapping storage

			storageEngine.deleteByClass(recordVersion.getDDMStorageId());

			// Workflow

			deleteWorkflowInstanceLink(
				record.getCompanyId(), record.getGroupId(),
				recordVersion.getPrimaryKey());
		}

		// Asset

		deleteAssetEntry(record.getRecordId());

		// Ratings

		deleteRatingsStats(record.getRecordId());

		return record;
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param  recordId the primary key of the record to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteRecord(long recordId) throws PortalException {
		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		ddlRecordLocalService.deleteRecord(record);
	}

	/**
	 * Deletes all the record set's records.
	 *
	 * @param  recordSetId the primary key of the record set from which to
	 *         delete records
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteRecords(long recordSetId) throws PortalException {
		List<DDLRecord> records = ddlRecordPersistence.findByRecordSetId(
			recordSetId);

		for (DDLRecord record : records) {
			ddlRecordLocalService.deleteRecord(record);
		}
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param  recordId the primary key of the record
	 * @return the record with the ID, or <code>null</code> if a matching record
	 *         could not be found
	 */
	@Override
	public DDLRecord fetchRecord(long recordId) {
		return ddlRecordPersistence.fetchByPrimaryKey(recordId);
	}

	/**
	 * Returns an ordered range of all the records matching the company,
	 * workflow status, and scope.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the record's company
	 * @param  status the record's workflow status. For more information search
	 *         the portal kernel's WorkflowConstants class for constants
	 *         starting with the "STATUS_" prefix.
	 * @param  scope the record's scope. For more information search the
	 *         dynamic-data-lists-api module's DDLRecordSetConstants class for
	 *         constants starting with the "SCOPE_" prefix.
	 * @param  start the lower bound of the range of records to return
	 * @param  end the upper bound of the range of records to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the records
	 * @return the range of matching records ordered by the comparator
	 */
	@Override
	public List<DDLRecord> getCompanyRecords(
		long companyId, int status, int scope, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return ddlRecordFinder.findByC_S_S(
			companyId, status, scope, start, end, orderByComparator);
	}

	/**
	 * Returns the number of records matching the company, workflow status, and
	 * scope.
	 *
	 * @param  companyId the primary key of the record's company
	 * @param  status the record's workflow status. For more information search
	 *         the portal kernel's WorkflowConstants class for constants
	 *         starting with the "STATUS_" prefix.
	 * @param  scope the record's scope. For more information search the
	 *         dynamic-data-lists-api module's DDLRecordSetConstants class for
	 *         constants starting with the "SCOPE_" prefix.
	 * @return the number of matching records
	 */
	@Override
	public int getCompanyRecordsCount(long companyId, int status, int scope) {
		return ddlRecordFinder.countByC_S_S(companyId, status, scope);
	}

	/**
	 * Returns the DDM form values object associated with the record storage ID
	 * See <code>DDLRecord#getDDMFormValues</code> in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module.
	 *
	 * @param  ddmStorageId the storage ID associated with the record
	 * @return the DDM form values
	 */
	@Override
	public DDMFormValues getDDMFormValues(long ddmStorageId)
		throws StorageException {

		return storageEngine.getDDMFormValues(ddmStorageId);
	}

	@Override
	public Long[] getMinAndMaxCompanyRecordIds(
		long companyId, int status, int scope) {

		return ddlRecordFinder.findByC_S_S_MinAndMax(companyId, status, scope);
	}

	@Override
	public List<DDLRecord> getMinAndMaxCompanyRecords(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId) {

		return ddlRecordFinder.findByC_S_S_MinAndMax(
			companyId, status, scope, minRecordId, maxRecordId);
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param  recordId the primary key of the record
	 * @return the record with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public DDLRecord getRecord(long recordId) throws PortalException {
		return ddlRecordPersistence.findByPrimaryKey(recordId);
	}

	/**
	 * Returns all the records matching the record set ID
	 *
	 * @param  recordSetId the record's record set ID
	 * @return the matching records
	 */
	@Override
	public List<DDLRecord> getRecords(long recordSetId) {
		return ddlRecordPersistence.findByRecordSetId(recordSetId);
	}

	/**
	 * Returns an ordered range of all the records matching the record set ID
	 * and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param  recordSetId the record's record set ID
	 * @param  status the record's workflow status. For more information search
	 *         the portal kernel's WorkflowConstants class for constants
	 *         starting with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of records to return
	 * @param  end the upper bound of the range of records to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the records
	 * @return the range of matching records ordered by the comparator
	 */
	@Override
	public List<DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return ddlRecordFinder.findByR_S(
			recordSetId, status, start, end, orderByComparator);
	}

	@Override
	public List<DDLRecord> getRecords(
		long recordSetId, int start, int end,
		OrderByComparator<DDLRecord> obc) {

		return ddlRecordPersistence.findByRecordSetId(
			recordSetId, start, end, obc);
	}

	/**
	 * Returns all the records matching the record set ID and user ID.
	 *
	 * @param  recordSetId the record's record set ID
	 * @param  userId the user ID the records belong to
	 * @return the list of matching records ordered by the comparator
	 */
	@Override
	public List<DDLRecord> getRecords(long recordSetId, long userId) {
		return ddlRecordPersistence.findByR_U(recordSetId, userId);
	}

	@Override
	public List<DDLRecord> getRecords(
		long recordSetId, long userId, int start, int end,
		OrderByComparator<DDLRecord> obc) {

		return ddlRecordPersistence.findByR_U(
			recordSetId, userId, start, end, obc);
	}

	@Override
	public int getRecordsCount(long recordSetId) {
		return ddlRecordPersistence.countByRecordSetId(recordSetId);
	}

	/**
	 * Returns the number of records matching the record set ID and workflow
	 * status.
	 *
	 * @param  recordSetId the record's record set ID
	 * @param  status the record's workflow status. For more information search
	 *         the portal kernel's WorkflowConstants class for constants
	 *         starting with the "STATUS_" prefix.
	 * @return the number of matching records
	 */
	@Override
	public int getRecordsCount(long recordSetId, int status) {
		return ddlRecordFinder.countByR_S(recordSetId, status);
	}

	@Override
	public int getRecordsCount(long recordSetId, long userId) {
		return ddlRecordPersistence.countByR_U(recordSetId, userId);
	}

	/**
	 * Reverts the record to the given version.
	 *
	 * @param  userId the primary key of the user who is reverting the record
	 * @param  recordId the primary key of the record
	 * @param  version the version to revert to
	 * @param  serviceContext the service context to be applied. This can set
	 *         the record modified date.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void revertRecord(
			long userId, long recordId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDLRecordVersion recordVersion =
			_ddlRecordVersionLocalService.getRecordVersion(recordId, version);

		if (!recordVersion.isApproved()) {
			return;
		}

		DDMFormValues ddmFormValues = storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		serviceContext.setCommand(Constants.REVERT);

		ddlRecordLocalService.updateRecord(
			userId, recordId, true, recordVersion.getDisplayIndex(),
			ddmFormValues, serviceContext);
	}

	/**
	 * Returns hits to all the records indexed by the search engine matching the
	 * search context.
	 *
	 * @param  searchContext the search context to be applied for searching
	 *         records. For more information, see <code>SearchContext</code> in
	 *         the <code>portal-kernel</code> module.
	 * @return the hits of the records that matched the search criteria.
	 */
	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<DDLRecord> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DDLRecord.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	/**
	 * Searches for records documents indexed by the search engine.
	 *
	 * @param  searchContext the search context to be applied for searching
	 *         documents. For more information, see <code>SearchContext</code>
	 *         in the <code>portal-kernel</code> module.
	 * @return BaseModelSearchResult containing the list of records that matched
	 *         the search criteria
	 */
	@Override
	public BaseModelSearchResult<DDLRecord> searchDDLRecords(
		SearchContext searchContext) {

		try {
			Indexer<DDLRecord> indexer = getDDLRecordIndexer();

			Hits hits = indexer.search(searchContext, DDL.SELECTED_FIELD_NAMES);

			return new BaseModelSearchResult<>(
				getRecords(hits), hits.getLength());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	/**
	 * Updates the record's asset with new asset categories, tag names, and link
	 * entries, removing and adding them as necessary.
	 *
	 * @param  userId the primary key of the user updating the record's asset
	 * @param  record the record
	 * @param  recordVersion the record version
	 * @param  assetCategoryIds the primary keys of the new asset categories
	 * @param  assetTagNames the new asset tag names
	 * @param  locale the locale to apply to the asset
	 * @param  priority the new priority
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, DDLRecord record, DDLRecordVersion recordVersion,
			long[] assetCategoryIds, String[] assetTagNames, Locale locale,
			Double priority)
		throws PortalException {

		DDLRecordSet recordSet = record.getRecordSet();

		int scope = recordSet.getScope();

		if ((scope != DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) &&
			(scope != DDLRecordSetConstants.SCOPE_FORMS) &&
			(scope != DDLRecordSetConstants.SCOPE_KALEO_FORMS)) {

			return;
		}

		boolean addDraftAssetEntry = false;
		boolean visible = true;

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

			visible = false;
		}

		if (scope == DDLRecordSetConstants.SCOPE_FORMS) {
			visible = false;
		}

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		String ddmStructureName = ddmStructure.getName(locale);

		String recordSetName = recordSet.getName(locale);

		String title = LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, recordSetName}, false);

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(),
				DDLRecordConstants.getClassName(scope),
				recordVersion.getRecordVersionId(), record.getUuid(), 0,
				assetCategoryIds, assetTagNames, true, false, null, null, null,
				null, ContentTypes.TEXT_HTML, title, null, StringPool.BLANK,
				null, null, 0, 0, priority);
		}
		else {
			Date publishDate = null;

			if (visible) {
				publishDate = record.getCreateDate();
			}

			assetEntryLocalService.updateEntry(
				userId, record.getGroupId(), record.getCreateDate(),
				record.getModifiedDate(),
				DDLRecordConstants.getClassName(scope), record.getRecordId(),
				record.getUuid(), 0, assetCategoryIds, assetTagNames, true,
				visible, null, null, publishDate, null, ContentTypes.TEXT_HTML,
				title, null, StringPool.BLANK, null, null, 0, 0, priority);
		}
	}

	/**
	 * Updates the record, replacing its display index and values.
	 *
	 * @param  userId the primary key of the user updating the record
	 * @param  recordId the primary key of the record
	 * @param  majorVersion whether this update is a major change. A major
	 *         change increments the record's major version number.
	 * @param  displayIndex the index position in which the record is displayed
	 *         in the spreadsheet view
	 * @param  ddmFormValues the record values. See <code>DDMFormValues</code>
	 *         in the <code>dynamic.data.mapping.api</code> module.
	 * @param  serviceContext the service context to be applied. This can set
	 *         the record modified date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userLocalService.getUser(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));

		record = ddlRecordPersistence.update(record);

		// Record version

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		if (recordVersion.isApproved()) {
			DDLRecordSet recordSet = record.getRecordSet();

			long ddmStorageId = storageEngine.create(
				recordSet.getCompanyId(), recordSet.getDDMStructureId(),
				ddmFormValues, serviceContext);

			String version = getNextVersion(
				recordVersion.getVersion(), majorVersion,
				serviceContext.getWorkflowAction());

			recordVersion = addRecordVersion(
				user, record, ddmStorageId, version, displayIndex,
				WorkflowConstants.STATUS_DRAFT);
		}
		else {
			storageEngine.update(
				recordVersion.getDDMStorageId(), ddmFormValues, serviceContext);

			updateRecordVersion(
				user, recordVersion, recordVersion.getVersion(), displayIndex,
				recordVersion.getStatus(), serviceContext);
		}

		// Asset

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		if (isKeepRecordVersionLabel(
				record.getRecordVersion(), recordVersion, serviceContext)) {

			ddlRecordVersionPersistence.remove(recordVersion);

			// Dynamic data mapping storage

			storageEngine.deleteByClass(recordVersion.getDDMStorageId());

			return record;
		}

		// Workflow

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				user.getCompanyId(), record.getGroupId(), userId,
				getWorkflowAssetClassName(record.getRecordSet()),
				recordVersion.getRecordVersionId(), recordVersion,
				serviceContext);
		}

		return record;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord updateRecord(
			long userId, long recordId, long ddmStorageId,
			ServiceContext serviceContext)
		throws PortalException {

		// Record

		User user = userLocalService.getUser(userId);

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(recordId);

		record.setModifiedDate(serviceContext.getModifiedDate(null));
		record.setDDMStorageId(ddmStorageId);

		// Record version

		DDLRecordVersion ddlRecordVersion = record.getLatestRecordVersion();

		String version = getNextVersion(
			ddlRecordVersion.getVersion(), true,
			serviceContext.getWorkflowAction());

		record.setVersion(version);

		record = ddlRecordPersistence.update(record);

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		addRecordVersion(user, record, ddmStorageId, version, 0, status);

		return record;
	}

	/**
	 * Updates the workflow status of the record version.
	 *
	 * @param  userId the primary key of the user updating the record's workflow
	 *         status
	 * @param  recordVersionId the primary key of the record version
	 * @param  status
	 * @param  serviceContext the service context to be applied. This can set
	 *         the modification date and status date.
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord updateStatus(
			long userId, long recordVersionId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Record version

		User user = userLocalService.getUser(userId);

		DDLRecordVersion recordVersion =
			ddlRecordVersionPersistence.findByPrimaryKey(recordVersionId);

		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(new Date());

		recordVersion = ddlRecordVersionPersistence.update(recordVersion);

		// Record

		DDLRecord record = ddlRecordPersistence.findByPrimaryKey(
			recordVersion.getRecordId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			int compare = DLUtil.compareVersions(
				record.getVersion(), recordVersion.getVersion());

			if (compare <= 0) {
				record.setDDMStorageId(recordVersion.getDDMStorageId());
				record.setRecordSetId(recordVersion.getRecordSetId());
				record.setVersion(recordVersion.getVersion());
				record.setDisplayIndex(recordVersion.getDisplayIndex());
				record.setVersion(recordVersion.getVersion());
				record.setVersionUserId(recordVersion.getUserId());
				record.setVersionUserName(recordVersion.getUserName());

				record = ddlRecordPersistence.update(record);
			}
		}
		else {
			if (Objects.equals(
					record.getVersion(), recordVersion.getVersion())) {

				String newVersion = DDLRecordConstants.VERSION_DEFAULT;

				List<DDLRecordVersion> approvedRecordVersions =
					ddlRecordVersionPersistence.findByR_S(
						record.getRecordId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedRecordVersions.isEmpty()) {
					DDLRecordVersion approvedRecordVersion =
						approvedRecordVersions.get(0);

					newVersion = approvedRecordVersion.getVersion();
				}

				record.setVersion(newVersion);

				record = ddlRecordPersistence.update(record);
			}
		}

		// Asset

		updateAsset(
			userId, record, recordVersion, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		return record;
	}

	protected DDLRecordVersion addRecordVersion(
		User user, DDLRecord record, long ddmStorageId, String version,
		int displayIndex, int status) {

		long recordVersionId = counterLocalService.increment();

		DDLRecordVersion recordVersion = ddlRecordVersionPersistence.create(
			recordVersionId);

		recordVersion.setGroupId(record.getGroupId());
		recordVersion.setCompanyId(record.getCompanyId());
		recordVersion.setUserId(user.getUserId());
		recordVersion.setUserName(user.getFullName());
		recordVersion.setCreateDate(record.getModifiedDate());
		recordVersion.setDDMStorageId(ddmStorageId);
		recordVersion.setRecordSetId(record.getRecordSetId());
		recordVersion.setRecordSetVersion(record.getRecordSetVersion());
		recordVersion.setRecordId(record.getRecordId());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(record.getModifiedDate());

		return ddlRecordVersionPersistence.update(recordVersion);
	}

	protected void deleteAssetEntry(long recordId) throws PortalException {
		assetEntryLocalService.deleteEntry(
			DDLFormRecord.class.getName(), recordId);

		assetEntryLocalService.deleteEntry(DDLRecord.class.getName(), recordId);
	}

	protected void deleteRatingsStats(long recordId) throws PortalException {
		ratingsStatsLocalService.deleteStats(
			DDLFormRecord.class.getName(), recordId);

		ratingsStatsLocalService.deleteStats(
			DDLRecord.class.getName(), recordId);
	}

	protected void deleteWorkflowInstanceLink(
			long companyId, long groupId, long recordVersionId)
		throws PortalException {

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDLFormRecord.class.getName(), recordVersionId);

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			companyId, groupId, DDLRecord.class.getName(), recordVersionId);
	}

	protected Indexer<DDLRecord> getDDLRecordIndexer() {
		return indexerRegistry.nullSafeGetIndexer(DDLRecord.class);
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

	protected List<DDLRecord> getRecords(Hits hits) throws PortalException {
		List<DDLRecord> records = new ArrayList<>();

		for (Document document : hits.toList()) {
			long recordId = GetterUtil.getLong(
				document.get(
					com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK));

			try {
				records.add(getRecord(recordId));
			}
			catch (NoSuchRecordException noSuchRecordException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"DDL record index is stale and contains record " +
							recordId,
						noSuchRecordException);
				}

				long companyId = GetterUtil.getLong(
					document.get(
						com.liferay.portal.kernel.search.Field.COMPANY_ID));

				Indexer<DDLRecord> indexer = getDDLRecordIndexer();

				indexer.delete(companyId, document.getUID());
			}
		}

		return records;
	}

	protected String getWorkflowAssetClassName(DDLRecordSet recordSet) {
		if (recordSet.getScope() == DDLRecordSetConstants.SCOPE_FORMS) {
			return DDLFormRecord.class.getName();
		}

		return DDLRecord.class.getName();
	}

	protected boolean isKeepRecordVersionLabel(
			DDLRecordVersion lastRecordVersion,
			DDLRecordVersion latestRecordVersion, ServiceContext serviceContext)
		throws PortalException {

		if (Objects.equals(serviceContext.getCommand(), Constants.REVERT)) {
			return false;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			return false;
		}

		if (Objects.equals(
				lastRecordVersion.getVersion(),
				latestRecordVersion.getVersion())) {

			return false;
		}

		DDMFormValues lastDDMFormValues = storageEngine.getDDMFormValues(
			lastRecordVersion.getDDMStorageId());
		DDMFormValues latestDDMFormValues = storageEngine.getDDMFormValues(
			latestRecordVersion.getDDMStorageId());

		if (!lastDDMFormValues.equals(latestDDMFormValues)) {
			return false;
		}

		ExpandoBridge lastExpandoBridge = lastRecordVersion.getExpandoBridge();
		ExpandoBridge latestExpandoBridge =
			latestRecordVersion.getExpandoBridge();

		Map<String, Serializable> lastAttributes =
			lastExpandoBridge.getAttributes();
		Map<String, Serializable> latestAttributes =
			latestExpandoBridge.getAttributes();

		if (!lastAttributes.equals(latestAttributes)) {
			return false;
		}

		return true;
	}

	protected Fields toFields(
		long ddmStructureId, Map<String, Serializable> fieldsMap, Locale locale,
		Locale defaultLocale) {

		Fields fields = new Fields();

		for (Map.Entry<String, Serializable> entry : fieldsMap.entrySet()) {
			Field field = new Field();

			field.setDDMStructureId(ddmStructureId);
			field.setName(entry.getKey());

			Serializable value = entry.getValue();

			List<Serializable> serializableValues = _getSerializableValues(
				value);

			if (serializableValues != null) {
				field.addValues(locale, serializableValues);

				if (!locale.equals(defaultLocale)) {
					field.addValues(defaultLocale, serializableValues);
				}
			}
			else {
				field.addValue(locale, value);

				if (!locale.equals(defaultLocale)) {
					field.addValue(defaultLocale, value);
				}
			}

			field.setDefaultLocale(defaultLocale);

			fields.put(field);
		}

		Field fieldsDisplayField = fields.get(_FIELDS_DISPLAY_NAME);

		if (fieldsDisplayField == null) {
			StringBundler fieldsDisplayFieldSB = new StringBundler(
				fieldsMap.size() * 4 - 1);

			for (String fieldName : fields.getNames()) {
				fieldsDisplayFieldSB.append(fieldName);
				fieldsDisplayFieldSB.append(_INSTANCE_SEPARATOR);
				fieldsDisplayFieldSB.append(StringUtil.randomString());
				fieldsDisplayFieldSB.append(StringPool.COMMA);
			}

			if (fieldsDisplayFieldSB.index() > 0) {
				fieldsDisplayFieldSB.setIndex(fieldsDisplayFieldSB.index() - 1);
			}

			fieldsDisplayField = new Field(
				ddmStructureId, _FIELDS_DISPLAY_NAME,
				fieldsDisplayFieldSB.toString());

			fields.put(fieldsDisplayField);
		}

		return fields;
	}

	protected void updateRecordVersion(
		User user, DDLRecordVersion recordVersion, String version,
		int displayIndex, int status, ServiceContext serviceContext) {

		recordVersion.setUserId(user.getUserId());
		recordVersion.setUserName(user.getFullName());
		recordVersion.setVersion(version);
		recordVersion.setDisplayIndex(displayIndex);
		recordVersion.setStatus(status);
		recordVersion.setStatusByUserId(user.getUserId());
		recordVersion.setStatusByUserName(user.getFullName());
		recordVersion.setStatusDate(serviceContext.getModifiedDate(null));

		ddlRecordVersionPersistence.update(recordVersion);
	}

	protected void validate(long groupId, DDLRecordSet recordSet)
		throws PortalException {

		if (recordSet.getGroupId() != groupId) {
			throw new RecordGroupIdException(
				"Record group ID is not the same as the record set group ID");
		}
	}

	@Reference
	protected DDM ddm;

	@Reference
	protected DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected StorageEngine storageEngine;

	private List<Serializable> _getSerializableValues(Serializable value) {
		List<Serializable> serializableValues = null;

		if (value instanceof Collection) {
			Collection<Serializable> values = (Collection<Serializable>)value;

			serializableValues = new ArrayList<>(values);
		}
		else if (value instanceof Serializable[]) {
			Serializable[] values = (Serializable[])value;

			serializableValues = ListUtil.fromArray(values);
		}
		else if (value instanceof boolean[]) {
			boolean[] values = (boolean[])value;

			serializableValues = new ArrayList<>(values.length);

			for (boolean serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}
		else if (value instanceof double[]) {
			double[] values = (double[])value;

			serializableValues = new ArrayList<>(values.length);

			for (double serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}
		else if (value instanceof float[]) {
			float[] values = (float[])value;

			serializableValues = new ArrayList<>(values.length);

			for (float serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}
		else if (value instanceof int[]) {
			int[] values = (int[])value;

			serializableValues = new ArrayList<>(values.length);

			for (int serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}
		else if (value instanceof long[]) {
			long[] values = (long[])value;

			serializableValues = new ArrayList<>(values.length);

			for (long serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}
		else if (value instanceof short[]) {
			short[] values = (short[])value;

			serializableValues = new ArrayList<>(values.length);

			for (short serializableValue : values) {
				serializableValues.add(serializableValue);
			}
		}

		return serializableValues;
	}

	private static final String _FIELDS_DISPLAY_NAME = "_fieldsDisplay";

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordLocalServiceImpl.class);

	@Reference
	private DDLRecordVersionLocalService _ddlRecordVersionLocalService;

}