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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalService
 * @generated
 */
@ProviderType
public class DDLRecordLocalServiceWrapper implements DDLRecordLocalService,
	ServiceWrapper<DDLRecordLocalService> {
	public DDLRecordLocalServiceWrapper(
		DDLRecordLocalService ddlRecordLocalService) {
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	/**
	* Adds the d d l record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was added
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return _ddlRecordLocalService.addDDLRecord(ddlRecord);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.addRecord(userId, groupId, recordSetId,
			displayIndex, ddmFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.addRecord(userId, groupId, recordSetId,
			displayIndex, fields, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.addRecord(userId, groupId, recordSetId,
			displayIndex, fieldsMap, serviceContext);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord createDDLRecord(
		long recordId) {
		return _ddlRecordLocalService.createDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was removed
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return _ddlRecordLocalService.deleteDDLRecord(ddlRecord);
	}

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws PortalException if a d d l record with the primary key could not be found
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
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.deleteRecord(record);
	}

	@Override
	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordLocalService.deleteRecord(recordId);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.deleteRecordLocale(recordId, locale,
			serviceContext);
	}

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddlRecordLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _ddlRecordLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord fetchDDLRecord(
		long recordId) {
		return _ddlRecordLocalService.fetchDDLRecord(recordId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord fetchDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _ddlRecordLocalService.fetchDDLRecordByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord fetchRecord(
		long recordId) {
		return _ddlRecordLocalService.fetchRecord(recordId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddlRecordLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getCompanyRecords(
		long companyId, int status, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return _ddlRecordLocalService.getCompanyRecords(companyId, status,
			scope, start, end, orderByComparator);
	}

	@Override
	public int getCompanyRecordsCount(long companyId, int status, int scope) {
		return _ddlRecordLocalService.getCompanyRecordsCount(companyId, status,
			scope);
	}

	/**
	* Returns the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getDDLRecord(recordId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record
	* @throws PortalException if a matching d d l record could not be found
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getDDLRecordByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecords(
		int start, int end) {
		return _ddlRecordLocalService.getDDLRecords(start, end);
	}

	/**
	* Returns all the d d l records matching the UUID and company.
	*
	* @param uuid the UUID of the d d l records
	* @param companyId the primary key of the company
	* @return the matching d d l records, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _ddlRecordLocalService.getDDLRecordsByUuidAndCompanyId(uuid,
			companyId);
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
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getDDLRecordsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return _ddlRecordLocalService.getDDLRecordsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	*/
	@Override
	public int getDDLRecordsCount() {
		return _ddlRecordLocalService.getDDLRecordsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _ddlRecordLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddlRecordLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getLatestRecordVersion(
	long)}
	*/
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getLatestRecordVersion(recordId);
	}

	@Override
	public java.lang.Long[] getMinAndMaxCompanyRecordIds(long companyId,
		int status, int scope) {
		return _ddlRecordLocalService.getMinAndMaxCompanyRecordIds(companyId,
			status, scope);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getMinAndMaxCompanyRecords(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId) {
		return _ddlRecordLocalService.getMinAndMaxCompanyRecords(companyId,
			status, scope, minRecordId, maxRecordId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddlRecordLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getRecord(recordId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long, String)}
	*/
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getRecordVersion(recordId, version);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersion(
	long)}
	*/
	@Deprecated
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.getRecordVersion(recordVersionId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersions(
	long, int, int, OrderByComparator)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordVersion> orderByComparator) {
		return _ddlRecordLocalService.getRecordVersions(recordId, start, end,
			orderByComparator);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	com.liferay.dynamic.data.lists.service.DDLRecordVersionLocalService#getRecordVersionsCount(
	long)}
	*/
	@Deprecated
	@Override
	public int getRecordVersionsCount(long recordId) {
		return _ddlRecordLocalService.getRecordVersionsCount(recordId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId) {
		return _ddlRecordLocalService.getRecords(recordSetId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecord> orderByComparator) {
		return _ddlRecordLocalService.getRecords(recordSetId, status, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords(
		long recordSetId, long userId) {
		return _ddlRecordLocalService.getRecords(recordSetId, userId);
	}

	@Override
	public int getRecordsCount(long recordSetId, int status) {
		return _ddlRecordLocalService.getRecordsCount(recordSetId, status);
	}

	@Override
	public void revertRecord(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordLocalService.revertRecord(userId, recordId, version,
			serviceContext);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #revertRecord(long, long,
	String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public void revertRecordVersion(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordLocalService.revertRecordVersion(userId, recordId, version,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _ddlRecordLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.dynamic.data.lists.model.DDLRecord> searchDDLRecords(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _ddlRecordLocalService.searchDDLRecords(searchContext);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.dynamic.data.lists.model.DDLRecord record,
		com.liferay.dynamic.data.lists.model.DDLRecordVersion recordVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddlRecordLocalService.updateAsset(userId, record, recordVersion,
			assetCategoryIds, assetTagNames, locale);
	}

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was updated
	*/
	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateDDLRecord(
		com.liferay.dynamic.data.lists.model.DDLRecord ddlRecord) {
		return _ddlRecordLocalService.updateDDLRecord(ddlRecord);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.updateRecord(userId, recordId,
			displayIndex, fieldsMap, mergeFields, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.updateRecord(userId, recordId,
			majorVersion, displayIndex, ddmFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.dynamic.data.mapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.updateRecord(userId, recordId,
			majorVersion, displayIndex, fields, mergeFields, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecord updateStatus(
		long userId, long recordVersionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordLocalService.updateStatus(userId, recordVersionId,
			status, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDLRecordLocalService getWrappedDDLRecordLocalService() {
		return _ddlRecordLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {
		_ddlRecordLocalService = ddlRecordLocalService;
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