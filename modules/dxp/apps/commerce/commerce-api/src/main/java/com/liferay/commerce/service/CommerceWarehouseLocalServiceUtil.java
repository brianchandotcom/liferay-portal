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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceWarehouse. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceWarehouseLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseLocalService
 * @see com.liferay.commerce.service.base.CommerceWarehouseLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce warehouse to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was added
	*/
	public static com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return getService().addCommerceWarehouse(commerceWarehouse);
	}

	public static com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		java.lang.String name, java.lang.String description, boolean active,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouse(name, description, active, street1,
			street2, street3, city, zip, commerceRegionId, commerceCountryId,
			latitude, longitude, serviceContext);
	}

	/**
	* Creates a new commerce warehouse with the primary key. Does not add the commerce warehouse to the database.
	*
	* @param commerceWarehouseId the primary key for the new commerce warehouse
	* @return the new commerce warehouse
	*/
	public static com.liferay.commerce.model.CommerceWarehouse createCommerceWarehouse(
		long commerceWarehouseId) {
		return getService().createCommerceWarehouse(commerceWarehouseId);
	}

	/**
	* Deletes the commerce warehouse from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was removed
	*/
	public static com.liferay.commerce.model.CommerceWarehouse deleteCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return getService().deleteCommerceWarehouse(commerceWarehouse);
	}

	/**
	* Deletes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse that was removed
	* @throws PortalException if a commerce warehouse with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceWarehouse deleteCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceWarehouse(commerceWarehouseId);
	}

	public static void deleteCommerceWarehouses(long groupId) {
		getService().deleteCommerceWarehouses(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceWarehouse fetchCommerceWarehouse(
		long commerceWarehouseId) {
		return getService().fetchCommerceWarehouse(commerceWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse geolocateCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().geolocateCommerceWarehouse(commerceWarehouseId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce warehouse with the primary key.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse
	* @throws PortalException if a commerce warehouse with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouse(commerceWarehouseId);
	}

	/**
	* Returns a range of all the commerce warehouses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouses
	* @param end the upper bound of the range of commerce warehouses (not inclusive)
	* @return the range of commerce warehouses
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		int start, int end) {
		return getService().getCommerceWarehouses(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, boolean active, long commerceCountryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, active, commerceCountryId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long cpInstanceId, int start, int end) {
		return getService().getCommerceWarehouses(cpInstanceId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, commerceCountryId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of commerce warehouses.
	*
	* @return the number of commerce warehouses
	*/
	public static int getCommerceWarehousesCount() {
		return getService().getCommerceWarehousesCount();
	}

	public static int getCommerceWarehousesCount(long groupId, boolean active,
		long commerceCountryId) {
		return getService()
				   .getCommerceWarehousesCount(groupId, active,
			commerceCountryId);
	}

	public static int getCommerceWarehousesCount(long groupId,
		long commerceCountryId) {
		return getService()
				   .getCommerceWarehousesCount(groupId, commerceCountryId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse getDefaultCommerceWarehouse(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefaultCommerceWarehouse(serviceContext);
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

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		long groupId, java.lang.String keywords, boolean all,
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .search(groupId, keywords, all, commerceCountryId, start,
			end, orderByComparator);
	}

	public static int searchCount(long groupId, java.lang.String keywords,
		boolean all, long commerceCountryId) {
		return getService()
				   .searchCount(groupId, keywords, all, commerceCountryId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse setActive(
		long commerceWarehouseId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceWarehouseId, active);
	}

	/**
	* Updates the commerce warehouse in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was updated
	*/
	public static com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return getService().updateCommerceWarehouse(commerceWarehouse);
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String description, boolean active, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouse(commerceWarehouseId, name,
			description, active, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateDefaultCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDefaultCommerceWarehouse(commerceWarehouseId, name,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, latitude, longitude, serviceContext);
	}

	public static CommerceWarehouseLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseLocalService, CommerceWarehouseLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceWarehouseLocalService.class);
}