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
 * Provides a wrapper for {@link CommerceOrderItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemLocalService
 * @generated
 */
@ProviderType
public class CommerceOrderItemLocalServiceWrapper
	implements CommerceOrderItemLocalService,
		ServiceWrapper<CommerceOrderItemLocalService> {
	public CommerceOrderItemLocalServiceWrapper(
		CommerceOrderItemLocalService commerceOrderItemLocalService) {
		_commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	/**
	* Adds the commerce order item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem) {
		return _commerceOrderItemLocalService.addCommerceOrderItem(commerceOrderItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		long commerceOrderId, long cpInstanceId, int quantity,
		int shippedQuantity, String json, java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.addCommerceOrderItem(commerceOrderId,
			cpInstanceId, quantity, shippedQuantity, json, price,
			commerceContext, serviceContext);
	}

	/**
	* Creates a new commerce order item with the primary key. Does not add the commerce order item to the database.
	*
	* @param commerceOrderItemId the primary key for the new commerce order item
	* @return the new commerce order item
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem createCommerceOrderItem(
		long commerceOrderItemId) {
		return _commerceOrderItemLocalService.createCommerceOrderItem(commerceOrderItemId);
	}

	/**
	* Deletes the commerce order item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem deleteCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.deleteCommerceOrderItem(commerceOrderItem);
	}

	/**
	* Deletes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item that was removed
	* @throws PortalException if a commerce order item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem deleteCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.deleteCommerceOrderItem(commerceOrderItemId);
	}

	@Override
	public void deleteCommerceOrderItems(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderItemLocalService.deleteCommerceOrderItems(commerceOrderId);
	}

	@Override
	public void deleteCommerceOrderItemsByCPInstanceId(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderItemLocalService.deleteCommerceOrderItemsByCPInstanceId(cpInstanceId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderItemLocalService.dynamicQuery();
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
		return _commerceOrderItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderItemLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderItemLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceOrderItemLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceOrderItemLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem fetchCommerceOrderItem(
		long commerceOrderItemId) {
		return _commerceOrderItemLocalService.fetchCommerceOrderItem(commerceOrderItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceOrderItemLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce order item with the primary key.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item
	* @throws PortalException if a commerce order item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.getCommerceOrderItem(commerceOrderItemId);
	}

	/**
	* Returns a range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of commerce order items
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		int start, int end) {
		return _commerceOrderItemLocalService.getCommerceOrderItems(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end) {
		return _commerceOrderItemLocalService.getCommerceOrderItems(commerceOrderId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrderItem> orderByComparator) {
		return _commerceOrderItemLocalService.getCommerceOrderItems(commerceOrderId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceWarehouseId, long commerceAddressId, int start, int end) {
		return _commerceOrderItemLocalService.getCommerceOrderItems(commerceWarehouseId,
			commerceAddressId, start, end);
	}

	/**
	* Returns the number of commerce order items.
	*
	* @return the number of commerce order items
	*/
	@Override
	public int getCommerceOrderItemsCount() {
		return _commerceOrderItemLocalService.getCommerceOrderItemsCount();
	}

	@Override
	public int getCommerceOrderItemsCount(long commerceOrderId) {
		return _commerceOrderItemLocalService.getCommerceOrderItemsCount(commerceOrderId);
	}

	@Override
	public int getCommerceOrderItemsQuantity(long commerceOrderId) {
		return _commerceOrderItemLocalService.getCommerceOrderItemsQuantity(commerceOrderId);
	}

	@Override
	public int getCommerceWarehouseItemQuantity(long commerceOrderItemId,
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.getCommerceWarehouseItemQuantity(commerceOrderItemId,
			commerceWarehouseId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId, int orderStatus) {
		return _commerceOrderItemLocalService.getCPInstanceQuantity(cpInstanceId,
			orderStatus);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceOrderItemLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderItemLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem incrementShippedQuantity(
		long commerceOrderItemId, int shippedQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.incrementShippedQuantity(commerceOrderItemId,
			shippedQuantity);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.search(commerceOrderId, keywords,
			start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String sku, String name, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.search(commerceOrderId, sku,
			name, andOperator, start, end, sort);
	}

	/**
	* Updates the commerce order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem) {
		return _commerceOrderItemLocalService.updateCommerceOrderItem(commerceOrderItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.updateCommerceOrderItem(commerceOrderItemId,
			quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity, String json,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderItemLocalService.updateCommerceOrderItem(commerceOrderItemId,
			quantity, json, price);
	}

	@Override
	public CommerceOrderItemLocalService getWrappedService() {
		return _commerceOrderItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderItemLocalService commerceOrderItemLocalService) {
		_commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	private CommerceOrderItemLocalService _commerceOrderItemLocalService;
}