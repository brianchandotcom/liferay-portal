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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPGroup. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPGroupLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPGroupLocalService
 * @see com.liferay.commerce.product.service.base.CPGroupLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPGroupLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPGroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPGroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp group to the database. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was added
	*/
	public static com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return getService().addCPGroup(cpGroup);
	}

	public static com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCPGroup(serviceContext);
	}

	/**
	* Creates a new cp group with the primary key. Does not add the cp group to the database.
	*
	* @param CPGroupId the primary key for the new cp group
	* @return the new cp group
	*/
	public static com.liferay.commerce.product.model.CPGroup createCPGroup(
		long CPGroupId) {
		return getService().createCPGroup(CPGroupId);
	}

	/**
	* Deletes the cp group from the database. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was removed
	*/
	public static com.liferay.commerce.product.model.CPGroup deleteCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return getService().deleteCPGroup(cpGroup);
	}

	/**
	* Deletes the cp group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group that was removed
	* @throws PortalException if a cp group with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPGroup deleteCPGroup(
		long CPGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPGroup(CPGroupId);
	}

	public static com.liferay.commerce.product.model.CPGroup deleteCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPGroupByGroupId(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPGroup fetchCPGroup(
		long CPGroupId) {
		return getService().fetchCPGroup(CPGroupId);
	}

	public static com.liferay.commerce.product.model.CPGroup fetchCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPGroupByGroupId(groupId);
	}

	/**
	* Returns the cp group matching the UUID and group.
	*
	* @param uuid the cp group's UUID
	* @param groupId the primary key of the group
	* @return the matching cp group, or <code>null</code> if a matching cp group could not be found
	*/
	public static com.liferay.commerce.product.model.CPGroup fetchCPGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCPGroupByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp group with the primary key.
	*
	* @param CPGroupId the primary key of the cp group
	* @return the cp group
	* @throws PortalException if a cp group with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPGroup getCPGroup(
		long CPGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPGroup(CPGroupId);
	}

	public static com.liferay.commerce.product.model.CPGroup getCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPGroupByGroupId(groupId);
	}

	/**
	* Returns the cp group matching the UUID and group.
	*
	* @param uuid the cp group's UUID
	* @param groupId the primary key of the group
	* @return the matching cp group
	* @throws PortalException if a matching cp group could not be found
	*/
	public static com.liferay.commerce.product.model.CPGroup getCPGroupByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPGroupByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the cp groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @return the range of cp groups
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPGroup> getCPGroups(
		int start, int end) {
		return getService().getCPGroups(start, end);
	}

	/**
	* Returns all the cp groups matching the UUID and company.
	*
	* @param uuid the UUID of the cp groups
	* @param companyId the primary key of the company
	* @return the matching cp groups, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPGroup> getCPGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getCPGroupsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of cp groups matching the UUID and company.
	*
	* @param uuid the UUID of the cp groups
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp groups
	* @param end the upper bound of the range of cp groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp groups, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPGroup> getCPGroupsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPGroup> orderByComparator) {
		return getService()
				   .getCPGroupsByUuidAndCompanyId(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of cp groups.
	*
	* @return the number of cp groups
	*/
	public static int getCPGroupsCount() {
		return getService().getCPGroupsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpGroup the cp group
	* @return the cp group that was updated
	*/
	public static com.liferay.commerce.product.model.CPGroup updateCPGroup(
		com.liferay.commerce.product.model.CPGroup cpGroup) {
		return getService().updateCPGroup(cpGroup);
	}

	public static CPGroupLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPGroupLocalService, CPGroupLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CPGroupLocalService.class);
}