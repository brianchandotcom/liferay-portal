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
 * Provides a wrapper for {@link CommerceCartItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartItemLocalService
 * @generated
 */
@ProviderType
public class CommerceCartItemLocalServiceWrapper
	implements CommerceCartItemLocalService,
		ServiceWrapper<CommerceCartItemLocalService> {
	public CommerceCartItemLocalServiceWrapper(
		CommerceCartItemLocalService commerceCartItemLocalService) {
		_commerceCartItemLocalService = commerceCartItemLocalService;
	}

	/**
	* Adds the commerce cart item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem addCommerceCartItem(
		com.liferay.commerce.model.CommerceCartItem commerceCartItem) {
		return _commerceCartItemLocalService.addCommerceCartItem(commerceCartItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceCartItem addCommerceCartItem(
		long commerceCartId, long cpDefinitionId, long cpInstanceId,
		int quantity, java.lang.String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.addCommerceCartItem(commerceCartId,
			cpDefinitionId, cpInstanceId, quantity, json, serviceContext);
	}

	/**
	* Creates a new commerce cart item with the primary key. Does not add the commerce cart item to the database.
	*
	* @param commerceCartItemId the primary key for the new commerce cart item
	* @return the new commerce cart item
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem createCommerceCartItem(
		long commerceCartItemId) {
		return _commerceCartItemLocalService.createCommerceCartItem(commerceCartItemId);
	}

	/**
	* Deletes the commerce cart item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem deleteCommerceCartItem(
		com.liferay.commerce.model.CommerceCartItem commerceCartItem)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.deleteCommerceCartItem(commerceCartItem);
	}

	/**
	* Deletes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item that was removed
	* @throws PortalException if a commerce cart item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem deleteCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.deleteCommerceCartItem(commerceCartItemId);
	}

	@Override
	public void deleteCommerceCartItems(long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartItemLocalService.deleteCommerceCartItems(commerceCartId);
	}

	@Override
	public void deleteCommerceCartItemsByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartItemLocalService.deleteCommerceCartItemsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public void deleteCommerceCartItemsByCPInstanceId(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartItemLocalService.deleteCommerceCartItemsByCPInstanceId(cpInstanceId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCartItemLocalService.dynamicQuery();
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
		return _commerceCartItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCartItemLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCartItemLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceCartItemLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceCartItemLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceCartItem fetchCommerceCartItem(
		long commerceCartItemId) {
		return _commerceCartItemLocalService.fetchCommerceCartItem(commerceCartItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceCartItemLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce cart item with the primary key.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item
	* @throws PortalException if a commerce cart item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem getCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.getCommerceCartItem(commerceCartItemId);
	}

	/**
	* Returns a range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of commerce cart items
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCartItem> getCommerceCartItems(
		int start, int end) {
		return _commerceCartItemLocalService.getCommerceCartItems(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {
		return _commerceCartItemLocalService.getCommerceCartItems(commerceCartId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCartItem> orderByComparator) {
		return _commerceCartItemLocalService.getCommerceCartItems(commerceCartId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce cart items.
	*
	* @return the number of commerce cart items
	*/
	@Override
	public int getCommerceCartItemsCount() {
		return _commerceCartItemLocalService.getCommerceCartItemsCount();
	}

	@Override
	public int getCommerceCartItemsCount(long commerceCartId) {
		return _commerceCartItemLocalService.getCommerceCartItemsCount(commerceCartId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) {
		return _commerceCartItemLocalService.getCPInstanceQuantity(cpInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceCartItemLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCartItemLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce cart item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceCartItem updateCommerceCartItem(
		com.liferay.commerce.model.CommerceCartItem commerceCartItem) {
		return _commerceCartItemLocalService.updateCommerceCartItem(commerceCartItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceCartItem updateCommerceCartItem(
		long commerceCartItemId, int quantity, java.lang.String json)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartItemLocalService.updateCommerceCartItem(commerceCartItemId,
			quantity, json);
	}

	@Override
	public void validate(long cpDefinitionId, long cpInstanceId,
		long commerceCartId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartItemLocalService.validate(cpDefinitionId, cpInstanceId,
			commerceCartId, quantity);
	}

	@Override
	public CommerceCartItemLocalService getWrappedService() {
		return _commerceCartItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceCartItemLocalService commerceCartItemLocalService) {
		_commerceCartItemLocalService = commerceCartItemLocalService;
	}

	private CommerceCartItemLocalService _commerceCartItemLocalService;
}