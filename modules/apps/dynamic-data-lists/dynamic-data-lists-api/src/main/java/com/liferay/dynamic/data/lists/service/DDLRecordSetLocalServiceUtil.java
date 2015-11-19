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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDLRecordSet. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetLocalService
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordSetLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l record set to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was added
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet addDDLRecordSet(
		com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {
		return getService().addDDLRecordSet(ddlRecordSet);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet addRecordSet(
		long userId, long groupId, long ddmStructureId,
		java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows, int scope,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addRecordSet(userId, groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, scope, serviceContext);
	}

	public static void addRecordSetResources(
		com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addRecordSetResources(recordSet, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addRecordSetResources(
		com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addRecordSetResources(recordSet, groupPermissions, guestPermissions);
	}

	/**
	* Creates a new d d l record set with the primary key. Does not add the d d l record set to the database.
	*
	* @param recordSetId the primary key for the new d d l record set
	* @return the new d d l record set
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet createDDLRecordSet(
		long recordSetId) {
		return getService().createDDLRecordSet(recordSetId);
	}

	/**
	* Deletes the d d l record set from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was removed
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet deleteDDLRecordSet(
		com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {
		return getService().deleteDDLRecordSet(ddlRecordSet);
	}

	/**
	* Deletes the d d l record set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set that was removed
	* @throws PortalException if a d d l record set with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet deleteDDLRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDLRecordSet(recordSetId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteRecordSet(long groupId,
		java.lang.String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecordSet(groupId, recordSetKey);
	}

	public static void deleteRecordSet(
		com.liferay.dynamic.data.lists.model.DDLRecordSet recordSet)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecordSet(recordSet);
	}

	public static void deleteRecordSet(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecordSet(recordSetId);
	}

	public static void deleteRecordSets(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteRecordSets(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet fetchDDLRecordSet(
		long recordSetId) {
		return getService().fetchDDLRecordSet(recordSetId);
	}

	/**
	* Returns the d d l record set matching the UUID and group.
	*
	* @param uuid the d d l record set's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record set, or <code>null</code> if a matching d d l record set could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet fetchDDLRecordSetByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDDLRecordSetByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet fetchRecordSet(
		long groupId, java.lang.String recordSetKey) {
		return getService().fetchRecordSet(groupId, recordSetKey);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet fetchRecordSet(
		long recordSetId) {
		return getService().fetchRecordSet(recordSetId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the d d l record set with the primary key.
	*
	* @param recordSetId the primary key of the d d l record set
	* @return the d d l record set
	* @throws PortalException if a d d l record set with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet getDDLRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordSet(recordSetId);
	}

	/**
	* Returns the d d l record set matching the UUID and group.
	*
	* @param uuid the d d l record set's UUID
	* @param groupId the primary key of the group
	* @return the matching d d l record set
	* @throws PortalException if a matching d d l record set could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet getDDLRecordSetByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordSetByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d l record sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @return the range of d d l record sets
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getDDLRecordSets(
		int start, int end) {
		return getService().getDDLRecordSets(start, end);
	}

	/**
	* Returns all the d d l record sets matching the UUID and company.
	*
	* @param uuid the UUID of the d d l record sets
	* @param companyId the primary key of the company
	* @return the matching d d l record sets, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getDDLRecordSetsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of d d l record sets matching the UUID and company.
	*
	* @param uuid the UUID of the d d l record sets
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d l record sets
	* @param end the upper bound of the range of d d l record sets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d l record sets, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getDDLRecordSetsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return getService()
				   .getDDLRecordSetsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of d d l record sets.
	*
	* @return the number of d d l record sets
	*/
	public static int getDDLRecordSetsCount() {
		return getService().getDDLRecordSetsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet(
		long groupId, java.lang.String recordSetKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSet(groupId, recordSetKey);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSet(recordSetId);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
		long groupId) {
		return getService().getRecordSets(groupId);
	}

	public static int getRecordSetsCount(long groupId) {
		return getService().getRecordSetsCount(groupId);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String keywords, int scope,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return getService()
				   .search(companyId, groupId, keywords, scope, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
		long companyId, long groupId, java.lang.String name,
		java.lang.String description, int scope, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSet> orderByComparator) {
		return getService()
				   .search(companyId, groupId, name, description, scope,
			andOperator, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords, int scope) {
		return getService().searchCount(companyId, groupId, keywords, scope);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String name, java.lang.String description, int scope,
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupId, name, description, scope,
			andOperator);
	}

	/**
	* Updates the d d l record set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSet the d d l record set
	* @return the d d l record set that was updated
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateDDLRecordSet(
		com.liferay.dynamic.data.lists.model.DDLRecordSet ddlRecordSet) {
		return getService().updateDDLRecordSet(ddlRecordSet);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateMinDisplayRows(
		long recordSetId, int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMinDisplayRows(recordSetId, minDisplayRows,
			serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long groupId, long ddmStructureId, java.lang.String recordSetKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecordSet(groupId, ddmStructureId, recordSetKey,
			nameMap, descriptionMap, minDisplayRows, serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long recordSetId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int minDisplayRows,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRecordSet(recordSetId, ddmStructureId, nameMap,
			descriptionMap, minDisplayRows, serviceContext);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet updateRecordSet(
		long recordSetId, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateRecordSet(recordSetId, settings);
	}

	public static DDLRecordSetLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDLRecordSetLocalService service) {
	}

	private static ServiceTracker<DDLRecordSetLocalService, DDLRecordSetLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDLRecordSetLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDLRecordSetLocalService, DDLRecordSetLocalService>(bundle.getBundleContext(),
				DDLRecordSetLocalService.class, null);

		_serviceTracker.open();
	}
}