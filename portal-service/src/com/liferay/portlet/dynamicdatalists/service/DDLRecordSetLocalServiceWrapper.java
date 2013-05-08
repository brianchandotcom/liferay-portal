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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordSetLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLRecordSetLocalService
 * @generated
 */
public class DDLRecordSetLocalServiceWrapper implements DDLRecordSetLocalService,
	ServiceWrapper<DDLRecordSetLocalService> {
	public DDLRecordSetLocalServiceWrapper(
		DDLRecordSetLocalService ddlRecordSetLocalService) {
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	/**
	* Adds the d d l record set to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet addDDLRecordSet(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet ddlRecordSet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.addDDLRecordSet(ddlRecordSet);
	}

	/**
	* Creates a new d d l record set with the primary key. Does not add the d d l record set to the database.
	*
	* @param recordSetId the primary key for the new d d l record set
	* @return the new d d l record set
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet createDDLRecordSet(
		long recordSetId) {
		return _ddlRecordSetLocalService.createDDLRecordSet(recordSetId);
	}

	/**
	* Deletes the d d l record set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set that was removed
	* @throws PortalException if a d d l record set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet deleteDDLRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.deleteDDLRecordSet(recordSetId);
	}

	/**
	* Deletes the d d l record set from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet deleteDDLRecordSet(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet ddlRecordSet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.deleteDDLRecordSet(ddlRecordSet);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddlRecordSetLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet fetchDDLRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.fetchDDLRecordSet(recordSetId);
	}

	/**
	* Returns the d d l record set with the primary key.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set
	* @throws PortalException if a d d l record set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getDDLRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getDDLRecordSet(recordSetId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the d d l record set matching the UUID and group.
	*
	* @param uuid the d d l record set's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record set
	* @throws PortalException if a matching d d l record set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getDDLRecordSetByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getDDLRecordSetByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of d d l record sets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> getDDLRecordSets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getDDLRecordSets(start, end);
	}

	/**
	* Returns the number of d d l record sets.
	*
	* @return the number of d d l record sets
	* @throws SystemException if a system exception occurred
	*/
	public int getDDLRecordSetsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getDDLRecordSetsCount();
	}

	/**
	* Updates the d d l record set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateDDLRecordSet(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet ddlRecordSet)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.updateDDLRecordSet(ddlRecordSet);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddlRecordSetLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddlRecordSetLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet addRecordSet(
		long userId, long groupId, long ddmStructureId,
		java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.addRecordSet(userId, groupId,
			ddmStructureId, recordSetKey, nameMap, descriptionMap,
			minDisplayRows, scope, serviceContext);
	}

	public void addRecordSetResources(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet recordSet,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.addRecordSetResources(recordSet,
			addGroupPermissions, addGuestPermissions);
	}

	public void addRecordSetResources(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet recordSet,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.addRecordSetResources(recordSet,
			groupPermissions, guestPermissions);
	}

	public void deleteRecordSet(
		com.liferay.portlet.dynamicdatalists.model.DDLRecordSet recordSet)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.deleteRecordSet(recordSet);
	}

	public void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.deleteRecordSet(recordSetId);
	}

	public void deleteRecordSet(long groupId, java.lang.String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.deleteRecordSet(groupId, recordSetKey);
	}

	public void deleteRecordSets(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlRecordSetLocalService.deleteRecordSets(groupId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet fetchRecordSet(
		long groupId, java.lang.String recordSetKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.fetchRecordSet(groupId, recordSetKey);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getRecordSet(recordSetId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet(
		long groupId, java.lang.String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getRecordSet(groupId, recordSetKey);
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> getRecordSets(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getRecordSets(groupId);
	}

	public int getRecordSetsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.getRecordSetsCount(groupId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.search(companyId, groupId, keywords,
			scope, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.search(companyId, groupId, name,
			description, scope, andOperator, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.searchCount(companyId, groupId,
			keywords, scope);
	}

	public int searchCount(long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.searchCount(companyId, groupId, name,
			description, scope, andOperator);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.updateMinDisplayRows(recordSetId,
			minDisplayRows, serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.updateRecordSet(recordSetId,
			ddmStructureId, nameMap, descriptionMap, minDisplayRows,
			serviceContext);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlRecordSetLocalService.updateRecordSet(groupId,
			ddmStructureId, recordSetKey, nameMap, descriptionMap,
			minDisplayRows, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public DDLRecordSetLocalService getWrappedDDLRecordSetLocalService() {
		return _ddlRecordSetLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	public DDLRecordSetLocalService getWrappedService() {
		return _ddlRecordSetLocalService;
	}

	public void setWrappedService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {
		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	private DDLRecordSetLocalService _ddlRecordSetLocalService;
}