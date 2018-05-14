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

package com.liferay.commerce.cloud.client.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCloudForecastOrderLocalService}.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrderLocalService
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderLocalServiceWrapper
	implements CommerceCloudForecastOrderLocalService,
		ServiceWrapper<CommerceCloudForecastOrderLocalService> {
	public CommerceCloudForecastOrderLocalServiceWrapper(
		CommerceCloudForecastOrderLocalService commerceCloudForecastOrderLocalService) {
		_commerceCloudForecastOrderLocalService = commerceCloudForecastOrderLocalService;
	}

	/**
	* Adds the commerce cloud forecast order to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was added
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder addCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return _commerceCloudForecastOrderLocalService.addCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder addCommerceCloudForecastOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.addCommerceCloudForecastOrder(commerceOrderId);
	}

	@Override
	public void checkCommerceCloudForecastOrders() {
		_commerceCloudForecastOrderLocalService.checkCommerceCloudForecastOrders();
	}

	/**
	* Creates a new commerce cloud forecast order with the primary key. Does not add the commerce cloud forecast order to the database.
	*
	* @param commerceCloudForecastOrderId the primary key for the new commerce cloud forecast order
	* @return the new commerce cloud forecast order
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder createCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId) {
		return _commerceCloudForecastOrderLocalService.createCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	/**
	* Deletes the commerce cloud forecast order from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return _commerceCloudForecastOrderLocalService.deleteCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	/**
	* Deletes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	* @throws PortalException if a commerce cloud forecast order with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.deleteCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrderByCommerceOrderId(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.deleteCommerceCloudForecastOrderByCommerceOrderId(commerceOrderId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCloudForecastOrderLocalService.dynamicQuery();
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
		return _commerceCloudForecastOrderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCloudForecastOrderLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCloudForecastOrderLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceCloudForecastOrderLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceCloudForecastOrderLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder fetchCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId) {
		return _commerceCloudForecastOrderLocalService.fetchCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceCloudForecastOrderLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce cloud forecast order with the primary key.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order
	* @throws PortalException if a commerce cloud forecast order with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder getCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.getCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	/**
	* Returns a range of all the commerce cloud forecast orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @return the range of commerce cloud forecast orders
	*/
	@Override
	public java.util.List<com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder> getCommerceCloudForecastOrders(
		int start, int end) {
		return _commerceCloudForecastOrderLocalService.getCommerceCloudForecastOrders(start,
			end);
	}

	/**
	* Returns the number of commerce cloud forecast orders.
	*
	* @return the number of commerce cloud forecast orders
	*/
	@Override
	public int getCommerceCloudForecastOrdersCount() {
		return _commerceCloudForecastOrderLocalService.getCommerceCloudForecastOrdersCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceCloudForecastOrderLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCloudForecastOrderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCloudForecastOrderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce cloud forecast order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was updated
	*/
	@Override
	public com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder updateCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return _commerceCloudForecastOrderLocalService.updateCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	@Override
	public CommerceCloudForecastOrderLocalService getWrappedService() {
		return _commerceCloudForecastOrderLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceCloudForecastOrderLocalService commerceCloudForecastOrderLocalService) {
		_commerceCloudForecastOrderLocalService = commerceCloudForecastOrderLocalService;
	}

	private CommerceCloudForecastOrderLocalService _commerceCloudForecastOrderLocalService;
}