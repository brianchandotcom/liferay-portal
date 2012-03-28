/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d l record local service. This utility wraps {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLRecordLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl
 * @generated
 */
public class DDLRecordLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDLRecord(ddlRecord);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord createDDLRecord(
		long recordId) {
		return getService().createDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record that was removed
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord deleteDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord deleteDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDDLRecord(ddlRecord);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDDLRecord(recordId);
	}

	/**
	* Returns the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecord(recordId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the d d l record with the UUID in the group.
	*
	* @param uuid the UUID of d d l record
	* @param groupId the group id of the d d l record
	* @return the d d l record
	* @throws PortalException if a d d l record with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l records
	* @param end the upper bound of the range of d d l records (not inclusive)
	* @return the range of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getDDLRecords(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecords(start, end);
	}

	/**
	* Returns the number of d d l records.
	*
	* @return the number of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDLRecordsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecordsCount();
	}

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @return the d d l record that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLRecord(ddlRecord);
	}

	/**
	* Updates the d d l record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record
	* @param merge whether to merge the d d l record with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l record that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLRecord(ddlRecord, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, displayIndex,
			fields, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, displayIndex,
			fieldsMap, serviceContext);
	}

	public static void deleteRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecord(record);
	}

	public static void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecord(recordId);
	}

	public static void deleteRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecords(recordSetId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord fetchRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchRecord(recordId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getCompanyRecords(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyRecords(companyId, start, end, orderByComparator);
	}

	public static int getCompanyRecordsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyRecordsCount(companyId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getLatestRecordVersion(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestRecordVersion(recordId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecord(recordId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecords(recordSetId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRecords(recordSetId, status, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecords(recordSetId, userId);
	}

	public static int getRecordsCount(long recordSetId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecordsCount(recordSetId, status);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecordVersion(recordVersionId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecordVersion(recordId, version);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRecordVersions(recordId, start, end, orderByComparator);
	}

	public static int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecordVersionsCount(recordId);
	}

	public static void revertRecordVersion(long userId, long recordId,
		java.lang.String version,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.revertRecordVersion(userId, recordId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(searchContext);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record,
		com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion recordVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, record, recordVersion, assetCategoryIds,
			assetTagNames, locale);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId, boolean majorVersion, int displayIndex,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRecord(userId, recordId, majorVersion, displayIndex,
			fields, mergeFields, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId, int displayIndex,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRecord(userId, recordId, displayIndex, fieldsMap,
			mergeFields, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateStatus(
		long userId, long recordVersionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, recordVersionId, status, serviceContext);
	}

	public static DDLRecordLocalService getService() {
		if (_service == null) {
			_service = (DDLRecordLocalService)PortalBeanLocatorUtil.locate(DDLRecordLocalService.class.getName());

			ReferenceRegistry.registerReference(DDLRecordLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDLRecordLocalService.class);
		}

		return _service;
	}

	public void setService(DDLRecordLocalService service) {
		MethodCache.remove(DDLRecordLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDLRecordLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDLRecordLocalService.class);
	}

	private static DDLRecordLocalService _service;
}