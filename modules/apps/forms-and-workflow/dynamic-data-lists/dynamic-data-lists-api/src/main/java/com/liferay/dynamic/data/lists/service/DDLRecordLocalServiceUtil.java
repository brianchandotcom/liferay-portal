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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDLRecord. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalService
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was added
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord addDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return getService().addDDLRecord(ddlRecord);
	}

	/**
	* Adds a record referencing the record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is displayed
	in the spreadsheet view
	* @param ddmFormValues the record values. See <code>DDMFormValues</code>
	in the <code>dynamic.data.mapping.api</code> module.
	* @param serviceContext the service context to be applied. This can set
	the UUID, guest permissions, and group permissions for the
	record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, displayIndex,
			ddmFormValues, serviceContext);
	}

	/**
	* Adds a record that's based on the fields object and that references the
	* record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view.
	* @param fields the record values. See the dynamic-data-mapping-api
	module's Fields class for more information.
	* @param serviceContext the service context to be applied. This can
	set the UUID, guest permissions, and group permissions for
	the record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addRecord(long, long, int,
	DDMFormValues, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, displayIndex,
			fields, serviceContext);
	}

	/**
	* Adds a record that's based on the fields map and that references the
	* record set.
	*
	* @param userId the primary key of the record's creator/owner
	* @param groupId the primary key of the record's group
	* @param recordSetId the primary key of the record set
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view
	* @param fieldsMap the record values. The fieldsMap is a map of field
	names and their serializable values.
	* @param serviceContext the service context to be applied. This can
	set the UUID, guest permissions, and group permissions for
	the record.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #addRecord(long, long, int,
	DDMFormValues, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, displayIndex,
			fieldsMap, serviceContext);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord createDDLRecord(
		long recordId) {
		return getService().createDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was removed
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord deleteDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return getService().deleteDDLRecord(ddlRecord);
	}

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws PortalException if a d d l record with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord deleteDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDLRecord(recordId);
	}

	/**
	* Deletes the record and its resources.
	*
	* @param record the record to be deleted
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord deleteRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRecord(record);
	}

	/**
	* Disassociates the locale from the record.
	*
	* @param recordId the primary key of the record
	* @param locale the locale of the record values to be removed
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the affected record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, boolean,
	int, DDMFormValues, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRecordLocale(recordId, locale, serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecord fetchDDLRecord(
		long recordId) {
		return getService().fetchDDLRecord(recordId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord fetchDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDDLRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the record with the ID.
	*
	* @param recordId the primary key of the record
	* @return the record with the ID, or <code>null</code> if a matching record
	could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord fetchRecord(
		long recordId) {
		return getService().fetchRecord(recordId);
	}

	/**
	* Returns the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord getDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecord(recordId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record
	* @throws PortalException if a matching d d l record could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord getDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the record with the ID.
	*
	* @param recordId the primary key of the record
	* @return the record with the ID
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecord(recordId);
	}

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was updated
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return getService().updateDDLRecord(ddlRecord);
	}

	/**
	* Updates the record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param majorVersion whether this update is a major change. A major
	change increments the record's major version number.
	* @param displayIndex the index position in which the record is displayed
	in the spreadsheet view
	* @param ddmFormValues the record values. See <code>DDMFormValues</code>
	in the <code>dynamic.data.mapping.api</code> module.
	* @param serviceContext the service context to be applied. This can set
	the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecord(userId, recordId, majorVersion, displayIndex,
			ddmFormValues, serviceContext);
	}

	/**
	* Updates a record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param majorVersion whether this update is a major change. A major
	change increments the record's major version number
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view.
	* @param fields the record values. See <code>Fields</code> in the
	<code>dynamic.data.mapping.api</code> module.
	* @param mergeFields whether to merge the new fields with the existing
	ones; otherwise replace the existing fields
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, long,
	boolean, int, DDMFormValues, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecord(userId, recordId, majorVersion, displayIndex,
			fields, mergeFields, serviceContext);
	}

	/**
	* Updates a record, replacing its display index and values.
	*
	* @param userId the primary key of the user updating the record
	* @param recordId the primary key of the record
	* @param displayIndex the index position in which the record is
	displayed in the spreadsheet view
	* @param fieldsMap the record values. The fieldsMap is a map of field
	names and its Serializable values.
	* @param mergeFields whether to merge the new fields with the existing
	ones; otherwise replace the existing fields
	* @param serviceContext the service context to be applied. This can
	set the record modified date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	* @deprecated As of 7.0.0, replaced by {@link #updateRecord(long, long,
	boolean, int, DDMFormValues, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecord(userId, recordId, displayIndex, fieldsMap,
			mergeFields, serviceContext);
	}

	/**
	* Updates the workflow status of the record version.
	*
	* @param userId the primary key of the user updating the record's workflow
	status
	* @param recordVersionId the primary key of the record version
	* @param status
	* @param serviceContext the service context to be applied. This can set
	the modification date and status date.
	* @return the record
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecord updateStatus(
		long userId, long recordVersionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, recordVersionId, status, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	DDLRecordVersionLocalService#getLatestRecordVersion(long)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestRecordVersion(recordId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long, String)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordVersion(recordId, version);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long)}
	*/
	@Deprecated
	public static com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordVersion(recordVersionId);
	}

	/**
	* Returns the DDM form values object associated with the record storage ID
	* See <code>DDLRecord#getDDMFormValues</code> in the
	* <code>com.liferay.dynamic.data.lists.api</code> module.
	*
	* @param ddmStorageId the storage ID associated with the record
	* @return the DDM form values
	* @throws StorageException
	*/
	public static com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues(
		long ddmStorageId)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {
		return getService().getDDMFormValues(ddmStorageId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Searches for records documents indexed by the search engine.
	*
	* @param searchContext the search context to be applied for searching
	documents. For more information, see <code>SearchContext</code>
	in the <code>portal-kernel</code> module.
	* @return BaseModelSearchResult containing the list of records that matched
	the search criteria
	*/
	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.dynamic.data.lists.model.DDLRecord> searchDDLRecords(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().searchDDLRecords(searchContext);
	}

	/**
	* Returns hits to all the records indexed by the search engine matching the
	* search context.
	*
	* @param searchContext the search context to be applied for searching
	records. For more information, see <code>SearchContext</code> in
	the <code>portal-kernel</code> module.
	* @return the hits of the records that matched the search criteria.
	*/
	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	/**
	* Returns the number of records matching the company, workflow status, and
	* scope.
	*
	* @param companyId the primary key of the record's company
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param scope the record's scope. For more information search the
	dynamic-data-lists-api module's DDLRecordSetConstants class for
	constants starting with the "SCOPE_" prefix.
	* @return the number of matching records
	*/
	public static int getCompanyRecordsCount(long companyId, int status,
		int scope) {
		return getService().getCompanyRecordsCount(companyId, status, scope);
	}

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	*/
	public static int getDDLRecordsCount() {
		return getService().getDDLRecordsCount();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersionsCount(
	long)}
	*/
	@Deprecated
	public static int getRecordVersionsCount(long recordId) {
		return getService().getRecordVersionsCount(recordId);
	}

	/**
	* Returns the number of records matching the record set ID and workflow
	* status.
	*
	* @param recordSetId the record's record set ID
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @return the number of matching records
	*/
	public static int getRecordsCount(long recordSetId, int status) {
		return getService().getRecordsCount(recordSetId, status);
	}

	public static java.lang.Long[] getMinAndMaxCompanyRecordIds(
		long companyId, int status, int scope) {
		return getService()
				   .getMinAndMaxCompanyRecordIds(companyId, status, scope);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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
	* @param companyId the primary key of the record's company
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param scope the record's scope. For more information search the
	dynamic-data-lists-api module's DDLRecordSetConstants class for
	constants starting with the "SCOPE_" prefix.
	* @param start the lower bound of the range of records to return
	* @param end the upper bound of the range of records to return (not
	inclusive)
	* @param orderByComparator the comparator to order the records
	* @return the range of matching records ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getCompanyRecords(
		long companyId, int status, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return getService()
				   .getCompanyRecords(companyId, status, scope, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecords(
		int start, int end) {
		return getService().getDDLRecords(start, end);
	}

	/**
	* Returns all the d d l records matching the UUID and company.
	*
	* @param uuid the UUID of the d d l records
	* @param companyId the primary key of the company
	* @return the matching d d l records, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getDDLRecordsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of d d l records matching the UUID and company.
	*
	* @param uuid the UUID of the d d l records
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d l records, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return getService()
				   .getDDLRecordsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getMinAndMaxCompanyRecords(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId) {
		return getService()
				   .getMinAndMaxCompanyRecords(companyId, status, scope,
			minRecordId, maxRecordId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersions(
	long, int, int, OrderByComparator)}
	*/
	@Deprecated
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordVersion> orderByComparator) {
		return getService()
				   .getRecordVersions(recordId, start, end, orderByComparator);
	}

	/**
	* Returns all the records matching the record set ID
	*
	* @param recordSetId the record's record set ID
	* @return the matching records
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId) {
		return getService().getRecords(recordSetId);
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
	* @param recordSetId the record's record set ID
	* @param status the record's workflow status. For more information search
	the portal kernel's WorkflowConstants class for constants
	starting with the "STATUS_" prefix.
	* @param start the lower bound of the range of records to return
	* @param end the upper bound of the range of records to return (not
	inclusive)
	* @param orderByComparator the comparator to order the records
	* @return the range of matching records ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return getService()
				   .getRecords(recordSetId, status, start, end,
			orderByComparator);
	}

	/**
	* Returns all the records matching the record set ID and user ID.
	*
	* @param recordSetId the record's record set ID
	* @param userId the user ID the records belong to
	* @return the list of matching records ordered by the comparator
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId, long userId) {
		return getService().getRecords(recordSetId, userId);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	/**
	* Deletes the record and its resources.
	*
	* @param recordId the primary key of the record to be deleted
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecord(recordId);
	}

	/**
	* Deletes all the record set's records.
	*
	* @param recordSetId the primary key of the record set from which to
	delete records
	* @throws PortalException if a portal exception occurred
	*/
	public static void deleteRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecords(recordSetId);
	}

	/**
	* Reverts the record to the given version.
	*
	* @param userId the primary key of the user who is reverting the record
	* @param recordId the primary key of the record
	* @param version the version to revert to
	* @param serviceContext the service context to be applied. This can set
	the record modified date.
	* @throws PortalException if a portal exception occurred
	*/
	public static void revertRecord(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().revertRecord(userId, recordId, version, serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	String, ServiceContext)}
	*/
	@Deprecated
	public static void revertRecordVersion(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.revertRecordVersion(userId, recordId, version, serviceContext);
	}

	/**
	* Updates the record's asset with new asset categories, tag names, and link
	* entries, removing and adding them as necessary.
	*
	* @param userId the primary key of the user updating the record's asset
	* @param record the record
	* @param recordVersion the record version
	* @param assetCategoryIds the primary keys of the new asset categories
	* @param assetTagNames the new asset tag names
	* @param locale the locale to apply to the asset
	* @param priority the new priority
	* @throws PortalException if a portal exception occurred
	*/
	public static void updateAsset(long userId,
		com.liferay.dynamic.data.lists.model.DDLRecord record,
		com.liferay.dynamic.data.lists.model.DDLRecordVersion recordVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.util.Locale locale, java.lang.Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateAsset(userId, record, recordVersion, assetCategoryIds,
			assetTagNames, locale, priority);
	}

	public static DDLRecordLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordLocalService, DDLRecordLocalService> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordLocalService.class);
}