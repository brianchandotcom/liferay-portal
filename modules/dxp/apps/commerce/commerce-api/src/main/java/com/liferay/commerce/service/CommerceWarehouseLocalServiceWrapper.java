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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceWarehouseLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseLocalService
 * @generated
 */
@ProviderType
public class CommerceWarehouseLocalServiceWrapper
	implements CommerceWarehouseLocalService,
		ServiceWrapper<CommerceWarehouseLocalService> {
	public CommerceWarehouseLocalServiceWrapper(
		CommerceWarehouseLocalService commerceWarehouseLocalService) {
		_commerceWarehouseLocalService = commerceWarehouseLocalService;
	}

	/**
	* Adds the commerce warehouse to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return _commerceWarehouseLocalService.addCommerceWarehouse(commerceWarehouse);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.addCommerceWarehouse(name,
			description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	/**
	* Creates a new commerce warehouse with the primary key. Does not add the commerce warehouse to the database.
	*
	* @param commerceWarehouseId the primary key for the new commerce warehouse
	* @return the new commerce warehouse
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse createCommerceWarehouse(
		long commerceWarehouseId) {
		return _commerceWarehouseLocalService.createCommerceWarehouse(commerceWarehouseId);
	}

	/**
	* Deletes the commerce warehouse from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse deleteCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return _commerceWarehouseLocalService.deleteCommerceWarehouse(commerceWarehouse);
	}

	/**
	* Deletes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse that was removed
	* @throws PortalException if a commerce warehouse with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse deleteCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.deleteCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public void deleteCommerceWarehouses(long groupId) {
		_commerceWarehouseLocalService.deleteCommerceWarehouses(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceWarehouseLocalService.dynamicQuery();
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
		return _commerceWarehouseLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _commerceWarehouseLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _commerceWarehouseLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _commerceWarehouseLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceWarehouseLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse fetchCommerceWarehouse(
		long commerceWarehouseId) {
		return _commerceWarehouseLocalService.fetchCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse geolocateCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.geolocateCommerceWarehouse(commerceWarehouseId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceWarehouseLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce warehouse with the primary key.
	*
	* @param commerceWarehouseId the primary key of the commerce warehouse
	* @return the commerce warehouse
	* @throws PortalException if a commerce warehouse with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.getCommerceWarehouse(commerceWarehouseId);
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
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		int start, int end) {
		return _commerceWarehouseLocalService.getCommerceWarehouses(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long cpInstanceId, int quantity, int start, int end) {
		return _commerceWarehouseLocalService.getCommerceWarehouses(cpInstanceId,
			quantity, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return _commerceWarehouseLocalService.getCommerceWarehouses(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return _commerceWarehouseLocalService.getCommerceWarehouses(groupId,
			commerceCountryId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce warehouses.
	*
	* @return the number of commerce warehouses
	*/
	@Override
	public int getCommerceWarehousesCount() {
		return _commerceWarehouseLocalService.getCommerceWarehousesCount();
	}

	@Override
	public int getCommerceWarehousesCount(long groupId, long commerceCountryId) {
		return _commerceWarehouseLocalService.getCommerceWarehousesCount(groupId,
			commerceCountryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceWarehouseLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceWarehouseLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		long groupId, java.lang.String keywords, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return _commerceWarehouseLocalService.search(groupId, keywords,
			commerceCountryId, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long groupId, java.lang.String keywords,
		long commerceCountryId) {
		return _commerceWarehouseLocalService.searchCount(groupId, keywords,
			commerceCountryId);
	}

	/**
	* Updates the commerce warehouse in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouse the commerce warehouse
	* @return the commerce warehouse that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		com.liferay.commerce.model.CommerceWarehouse commerceWarehouse) {
		return _commerceWarehouseLocalService.updateCommerceWarehouse(commerceWarehouse);
	}

	@Override
	public com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceWarehouseLocalService.updateCommerceWarehouse(commerceWarehouseId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	@Override
	public CommerceWarehouseLocalService getWrappedService() {
		return _commerceWarehouseLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceWarehouseLocalService commerceWarehouseLocalService) {
		_commerceWarehouseLocalService = commerceWarehouseLocalService;
	}

	private CommerceWarehouseLocalService _commerceWarehouseLocalService;
}