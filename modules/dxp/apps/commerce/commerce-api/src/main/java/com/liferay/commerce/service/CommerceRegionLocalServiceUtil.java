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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceRegion. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceRegionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionLocalService
 * @see com.liferay.commerce.service.base.CommerceRegionLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceRegionLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceRegionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceRegionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce region to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was added
	*/
	public static com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion) {
		return getService().addCommerceRegion(commerceRegion);
	}

	public static com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		long commerceCountryId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceRegion(commerceCountryId, name, code, priority,
			active, serviceContext);
	}

	/**
	* Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	*
	* @param commerceRegionId the primary key for the new commerce region
	* @return the new commerce region
	*/
	public static com.liferay.commerce.model.CommerceRegion createCommerceRegion(
		long commerceRegionId) {
		return getService().createCommerceRegion(commerceRegionId);
	}

	/**
	* Deletes the commerce region from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommerceRegion deleteCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceRegion(commerceRegion);
	}

	/**
	* Deletes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region that was removed
	* @throws PortalException if a commerce region with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceRegion deleteCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceRegion(commerceRegionId);
	}

	public static void deleteCommerceRegions(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceRegions(commerceCountryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceRegion fetchCommerceRegion(
		long commerceRegionId) {
		return getService().fetchCommerceRegion(commerceRegionId);
	}

	/**
	* Returns the commerce region matching the UUID and group.
	*
	* @param uuid the commerce region's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	*/
	public static com.liferay.commerce.model.CommerceRegion fetchCommerceRegionByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchCommerceRegionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce region with the primary key.
	*
	* @param commerceRegionId the primary key of the commerce region
	* @return the commerce region
	* @throws PortalException if a commerce region with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceRegion getCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceRegion(commerceRegionId);
	}

	/**
	* Returns the commerce region matching the UUID and group.
	*
	* @param uuid the commerce region's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce region
	* @throws PortalException if a matching commerce region could not be found
	*/
	public static com.liferay.commerce.model.CommerceRegion getCommerceRegionByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceRegionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the commerce regions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceRegionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @return the range of commerce regions
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		int start, int end) {
		return getService().getCommerceRegions(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {
		return getService().getCommerceRegions(commerceCountryId, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return getService()
				   .getCommerceRegions(commerceCountryId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return getService()
				   .getCommerceRegions(commerceCountryId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce regions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce regions
	* @param companyId the primary key of the company
	* @return the matching commerce regions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegionsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService().getCommerceRegionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of commerce regions matching the UUID and company.
	*
	* @param uuid the UUID of the commerce regions
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce regions
	* @param end the upper bound of the range of commerce regions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce regions, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return getService()
				   .getCommerceRegionsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce regions.
	*
	* @return the number of commerce regions
	*/
	public static int getCommerceRegionsCount() {
		return getService().getCommerceRegionsCount();
	}

	public static int getCommerceRegionsCount(long commerceCountryId) {
		return getService().getCommerceRegionsCount(commerceCountryId);
	}

	public static int getCommerceRegionsCount(long commerceCountryId,
		boolean active) {
		return getService().getCommerceRegionsCount(commerceCountryId, active);
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce region in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceRegion the commerce region
	* @return the commerce region that was updated
	*/
	public static com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		com.liferay.commerce.model.CommerceRegion commerceRegion) {
		return getService().updateCommerceRegion(commerceRegion);
	}

	public static com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		long commerceRegionId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceRegion(commerceRegionId, name, code,
			priority, active, serviceContext);
	}

	public static CommerceRegionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceRegionLocalService, CommerceRegionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceRegionLocalService.class);

		ServiceTracker<CommerceRegionLocalService, CommerceRegionLocalService> serviceTracker =
			new ServiceTracker<CommerceRegionLocalService, CommerceRegionLocalService>(bundle.getBundleContext(),
				CommerceRegionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}