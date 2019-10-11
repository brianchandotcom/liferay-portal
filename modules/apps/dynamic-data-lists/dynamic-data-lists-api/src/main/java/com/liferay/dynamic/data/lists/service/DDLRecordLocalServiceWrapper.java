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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalService
 * @generated
 */
public class DDLRecordLocalServiceWrapper
	implements DDLRecordLocalService, ServiceWrapper<DDLRecordLocalService> {

	public DDLRecordLocalServiceWrapper(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	/**
	 * Adds the ddl record to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was added
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {

		return _ddlRecordLocalService.addDDLRecord(ddlRecord);
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
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.addRecord(
			userId, groupId, recordSetId, displayIndex, ddmFormValues,
			serviceContext);
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #addRecord(long, long, int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.addRecord(
			userId, groupId, recordSetId, displayIndex, fields, serviceContext);
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #addRecord(long, long, int, DDMFormValues, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long userId, long groupId, long recordSetId, int displayIndex,
			java.util.Map<String, java.io.Serializable> fieldsMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.addRecord(
			userId, groupId, recordSetId, displayIndex, fieldsMap,
			serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
			long userId, long groupId, long ddmStorageId, long ddlRecordSetId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.addRecord(
			userId, groupId, ddmStorageId, ddlRecordSetId, serviceContext);
	}

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord createDDLRecord(
		long recordId) {

		return _ddlRecordLocalService.createDDLRecord(recordId);
	}

	/**
	 * Deletes the ddl record from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was removed
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {

		return _ddlRecordLocalService.deleteDDLRecord(ddlRecord);
	}

	/**
	 * Deletes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws PortalException if a ddl record with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteDDLRecord(
			long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.deleteDDLRecord(recordId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param record the record to be deleted
	 * @return the record
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecord(
			com.liferay.dynamic.data.lists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.deleteRecord(record);
	}

	/**
	 * Deletes the record and its resources.
	 *
	 * @param recordId the primary key of the record to be deleted
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordLocalService.deleteRecord(recordId);
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
			long recordId, java.util.Locale locale,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.deleteRecordLocale(
			recordId, locale, serviceContext);
	}

	/**
	 * Deletes all the record set's records.
	 *
	 * @param recordSetId the primary key of the record set from which to
	 delete records
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordLocalService.deleteRecords(recordSetId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddlRecordLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _ddlRecordLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _ddlRecordLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _ddlRecordLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _ddlRecordLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _ddlRecordLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord fetchDDLRecord(
		long recordId) {

		return _ddlRecordLocalService.fetchDDLRecord(recordId);
	}

	/**
	 * Returns the ddl record matching the UUID and group.
	 *
	 * @param uuid the ddl record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord
		fetchDDLRecordByUuidAndGroupId(String uuid, long groupId) {

		return _ddlRecordLocalService.fetchDDLRecordByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the record with the ID, or <code>null</code> if a matching record
	 could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord fetchRecord(
		long recordId) {

		return _ddlRecordLocalService.fetchRecord(recordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddlRecordLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getCompanyRecords(
			long companyId, int status, int scope, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord>
					orderByComparator) {

		return _ddlRecordLocalService.getCompanyRecords(
			companyId, status, scope, start, end, orderByComparator);
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
	@Override
	public int getCompanyRecordsCount(long companyId, int status, int scope) {
		return _ddlRecordLocalService.getCompanyRecordsCount(
			companyId, status, scope);
	}

	/**
	 * Returns the ddl record with the primary key.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws PortalException if a ddl record with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getDDLRecord(
			long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getDDLRecord(recordId);
	}

	/**
	 * Returns the ddl record matching the UUID and group.
	 *
	 * @param uuid the ddl record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record
	 * @throws PortalException if a matching ddl record could not be found
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord
			getDDLRecordByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getDDLRecordByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of ddl records
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getDDLRecords(int start, int end) {

		return _ddlRecordLocalService.getDDLRecords(start, end);
	}

	/**
	 * Returns all the ddl records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl records
	 * @param companyId the primary key of the company
	 * @return the matching ddl records, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getDDLRecordsByUuidAndCompanyId(String uuid, long companyId) {

		return _ddlRecordLocalService.getDDLRecordsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of ddl records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl records
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddl records, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getDDLRecordsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord>
					orderByComparator) {

		return _ddlRecordLocalService.getDDLRecordsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	@Override
	public int getDDLRecordsCount() {
		return _ddlRecordLocalService.getDDLRecordsCount();
	}

	/**
	 * Returns the DDM form values object associated with the record storage ID
	 * See <code>DDLRecord#getDDMFormValues</code> in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module.
	 *
	 * @param ddmStorageId the storage ID associated with the record
	 * @return the DDM form values
	 */
	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues(long ddmStorageId)
		throws com.liferay.dynamic.data.mapping.exception.StorageException {

		return _ddlRecordLocalService.getDDMFormValues(ddmStorageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _ddlRecordLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddlRecordLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 DDLRecordVersionLocalService#getLatestRecordVersion(long)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getLatestRecordVersion(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getLatestRecordVersion(recordId);
	}

	@Override
	public Long[] getMinAndMaxCompanyRecordIds(
		long companyId, int status, int scope) {

		return _ddlRecordLocalService.getMinAndMaxCompanyRecordIds(
			companyId, status, scope);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getMinAndMaxCompanyRecords(
			long companyId, int status, int scope, long minRecordId,
			long maxRecordId) {

		return _ddlRecordLocalService.getMinAndMaxCompanyRecords(
			companyId, status, scope, minRecordId, maxRecordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddlRecordLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the record with the ID.
	 *
	 * @param recordId the primary key of the record
	 * @return the record with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
			long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getRecord(recordId);
	}

	/**
	 * Returns all the records matching the record set ID
	 *
	 * @param recordSetId the record's record set ID
	 * @return the matching records
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getRecords(long recordSetId) {

		return _ddlRecordLocalService.getRecords(recordSetId);
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
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getRecords(
			long recordSetId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord>
					orderByComparator) {

		return _ddlRecordLocalService.getRecords(
			recordSetId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getRecords(
			long recordSetId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord> obc) {

		return _ddlRecordLocalService.getRecords(recordSetId, start, end, obc);
	}

	/**
	 * Returns all the records matching the record set ID and user ID.
	 *
	 * @param recordSetId the record's record set ID
	 * @param userId the user ID the records belong to
	 * @return the list of matching records ordered by the comparator
	 */
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getRecords(long recordSetId, long userId) {

		return _ddlRecordLocalService.getRecords(recordSetId, userId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		getRecords(
			long recordSetId, long userId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord> obc) {

		return _ddlRecordLocalService.getRecords(
			recordSetId, userId, start, end, obc);
	}

	@Override
	public int getRecordsCount(long recordSetId) {
		return _ddlRecordLocalService.getRecordsCount(recordSetId);
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
	@Override
	public int getRecordsCount(long recordSetId, int status) {
		return _ddlRecordLocalService.getRecordsCount(recordSetId, status);
	}

	@Override
	public int getRecordsCount(long recordSetId, long userId) {
		return _ddlRecordLocalService.getRecordsCount(recordSetId, userId);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 DDLRecordVersionLocalService#getRecordVersion(
	 long)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getRecordVersion(recordVersionId);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 DDLRecordVersionLocalService#getRecordVersion(
	 long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion
			getRecordVersion(long recordId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.getRecordVersion(recordId, version);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 DDLRecordVersionLocalService#getRecordVersions(
	 long, int, int, OrderByComparator)}
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
		getRecordVersions(
			long recordId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordVersion>
					orderByComparator) {

		return _ddlRecordLocalService.getRecordVersions(
			recordId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 DDLRecordVersionLocalService#getRecordVersionsCount(
	 long)}
	 */
	@Deprecated
	@Override
	public int getRecordVersionsCount(long recordId) {
		return _ddlRecordLocalService.getRecordVersionsCount(recordId);
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
	@Override
	public void revertRecord(
			long userId, long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordLocalService.revertRecord(
			userId, recordId, version, serviceContext);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #revertRecord(long, long, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void revertRecordVersion(
			long userId, long recordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordLocalService.revertRecordVersion(
			userId, recordId, version, serviceContext);
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
	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {

		return _ddlRecordLocalService.search(searchContext);
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
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.dynamic.data.lists.model.DDLRecord> searchDDLRecords(
			com.liferay.portal.kernel.search.SearchContext searchContext) {

		return _ddlRecordLocalService.searchDDLRecords(searchContext);
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
	@Override
	public void updateAsset(
			long userId, com.liferay.dynamic.data.lists.model.DDLRecord record,
			com.liferay.dynamic.data.lists.model.DDLRecordVersion recordVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			java.util.Locale locale, Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddlRecordLocalService.updateAsset(
			userId, record, recordVersion, assetCategoryIds, assetTagNames,
			locale, priority);
	}

	/**
	 * Updates the ddl record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was updated
	 */
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {

		return _ddlRecordLocalService.updateDDLRecord(ddlRecord);
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
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.updateRecord(
			userId, recordId, majorVersion, displayIndex, ddmFormValues,
			serviceContext);
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long userId, long recordId, boolean majorVersion, int displayIndex,
			com.liferay.dynamic.data.mapping.storage.Fields fields,
			boolean mergeFields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.updateRecord(
			userId, recordId, majorVersion, displayIndex, fields, mergeFields,
			serviceContext);
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
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #updateRecord(long, long, boolean, int, DDMFormValues,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long userId, long recordId, int displayIndex,
			java.util.Map<String, java.io.Serializable> fieldsMap,
			boolean mergeFields,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.updateRecord(
			userId, recordId, displayIndex, fieldsMap, mergeFields,
			serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
			long userId, long recordId, long ddmStorageId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.updateRecord(
			userId, recordId, ddmStorageId, serviceContext);
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
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateStatus(
			long userId, long recordVersionId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddlRecordLocalService.updateStatus(
			userId, recordVersionId, status, serviceContext);
	}

	@Override
	public DDLRecordLocalService getWrappedService() {
		return _ddlRecordLocalService;
	}

	@Override
	public void setWrappedService(DDLRecordLocalService ddlRecordLocalService) {
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	private DDLRecordLocalService _ddlRecordLocalService;

}