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

package com.liferay.portlet.dynamicdatalists.service;

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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord addDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.addDDLRecord(ddlRecord);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord createDDLRecord(
		long recordId) {
		return _ddlRecordLocalService.createDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord deleteDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.deleteDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord deleteDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.deleteDDLRecord(ddlRecord);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.fetchDDLRecord(recordId);
	}

	/**
	* Returns the d d l record with the matching UUID and company.
	*
	* @param uuid the d d l record's UUID
	* @param companyId the primary key of the company
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchDDLRecordByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.fetchDDLRecordByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record, or <code>null</code> if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.fetchDDLRecordByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getDDLRecord(recordId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the d d l record with the matching UUID and company.
	*
	* @param uuid the d d l record's UUID
	* @param companyId the primary key of the company
	* @return the matching d d l record
	* @throws PortalException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecordByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getDDLRecordByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the d d l record matching the UUID and group.
	*
	* @param uuid the d d l record's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record
	* @throws PortalException if a matching d d l record could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getDDLRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getDDLRecords(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getDDLRecords(start, end);
	}

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getDDLRecordsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getDDLRecordsCount();
	}

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.updateDDLRecord(ddlRecord);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddlRecordLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddlRecordLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.addRecord(userId, groupId, recordSetId,
			displayIndex, fields, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.addRecord(userId, groupId, recordSetId,
			displayIndex, fieldsMap, serviceContext);
	}

	@Override
	public void deleteRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordLocalService.deleteRecord(record);
	}

	@Override
	public void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordLocalService.deleteRecord(recordId);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord deleteRecordLocale(
		long recordId, java.util.Locale locale,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.deleteRecordLocale(recordId, locale,
			serviceContext);
	}

	@Override
	public void deleteRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordLocalService.deleteRecords(recordSetId);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.fetchRecord(recordId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getCompanyRecords(
		long companyId, int status, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getCompanyRecords(companyId, status,
			scope, start, end, orderByComparator);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getCompanyRecords(long, int,
	int, int, int, OrderByComparator)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getCompanyRecords(
		long companyId, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getCompanyRecords(companyId, scope,
			start, end, orderByComparator);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getCompanyRecordsCount(long,
	int, int)}
	*/
	@Deprecated
	@Override
	public int getCompanyRecordsCount(long companyId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getCompanyRecordsCount(companyId, scope);
	}

	@Override
	public int getCompanyRecordsCount(long companyId, int status, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getCompanyRecordsCount(companyId, status,
			scope);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getLatestRecordVersion(recordId);
	}

	@Override
	public java.lang.Long[] getMinAndMaxCompanyRecordIds(long companyId,
		int status, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getMinAndMaxCompanyRecordIds(companyId,
			status, scope);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getMinAndMaxCompanyRecords(
		long companyId, int status, int scope, long minRecordId,
		long maxRecordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getMinAndMaxCompanyRecords(companyId,
			status, scope, minRecordId, maxRecordId);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecord(recordId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecords(recordSetId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecords(recordSetId, status, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecords(recordSetId, userId);
	}

	@Override
	public int getRecordsCount(long recordSetId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecordsCount(recordSetId, status);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecordVersion(recordVersionId);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecordVersion(recordId, version);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecordVersions(recordId, start, end,
			orderByComparator);
	}

	@Override
	public int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.getRecordVersionsCount(recordId);
	}

	@Override
	public void revertRecordVersion(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordLocalService.revertRecordVersion(userId, recordId, version,
			serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portlet.dynamicdatalists.model.DDLRecord> searchDDLRecords(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.searchDDLRecords(searchContext);
	}

	@Override
	public void updateAsset(long userId,
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record,
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion recordVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordLocalService.updateAsset(userId, record, recordVersion,
			assetCategoryIds, assetTagNames, locale);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.updateRecord(userId, recordId,
			majorVersion, displayIndex, fields, mergeFields, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordLocalService.updateRecord(userId, recordId,
			displayIndex, fieldsMap, mergeFields, serviceContext);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecord updateStatus(
		long userId, long recordVersionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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