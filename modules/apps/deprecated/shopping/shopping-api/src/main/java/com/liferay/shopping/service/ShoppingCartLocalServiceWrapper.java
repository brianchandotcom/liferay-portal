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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingCartLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartLocalService
 * @generated
 */
@ProviderType
public class ShoppingCartLocalServiceWrapper implements ShoppingCartLocalService,
	ServiceWrapper<ShoppingCartLocalService> {
	public ShoppingCartLocalServiceWrapper(
		ShoppingCartLocalService shoppingCartLocalService) {
		_shoppingCartLocalService = shoppingCartLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingCartLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingCartLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingCartLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the shopping cart to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return _shoppingCartLocalService.addShoppingCart(shoppingCart);
	}

	/**
	* Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	*
	* @param cartId the primary key for the new shopping cart
	* @return the new shopping cart
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return _shoppingCartLocalService.createShoppingCart(cartId);
	}

	/**
	* Deletes the shopping cart from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart deleteShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return _shoppingCartLocalService.deleteShoppingCart(shoppingCart);
	}

	/**
	* Deletes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart that was removed
	* @throws PortalException if a shopping cart with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart deleteShoppingCart(
		long cartId) throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.deleteShoppingCart(cartId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCart fetchShoppingCart(
		long cartId) {
		return _shoppingCartLocalService.fetchShoppingCart(cartId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCart getCart(long userId,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.getCart(userId, groupId);
	}

	/**
	* Returns the shopping cart with the primary key.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart
	* @throws PortalException if a shopping cart with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart getShoppingCart(long cartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.getShoppingCart(cartId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCart updateCart(long userId,
		long groupId, java.lang.String itemIds, java.lang.String couponCodes,
		int altShipping, boolean insure)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCartLocalService.updateCart(userId, groupId, itemIds,
			couponCodes, altShipping, insure);
	}

	/**
	* Updates the shopping cart in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return _shoppingCartLocalService.updateShoppingCart(shoppingCart);
	}

	/**
	* Returns the number of shopping carts.
	*
	* @return the number of shopping carts
	*/
	@Override
	public int getShoppingCartsCount() {
		return _shoppingCartLocalService.getShoppingCartsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingCartLocalService.getOSGiServiceIdentifier();
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
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingCartLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the shopping carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping carts
	* @param end the upper bound of the range of shopping carts (not inclusive)
	* @return the range of shopping carts
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end) {
		return _shoppingCartLocalService.getShoppingCarts(start, end);
	}

	@Override
	public java.util.Map<com.liferay.shopping.model.ShoppingCartItem, java.lang.Integer> getItems(
		long groupId, java.lang.String itemIds) {
		return _shoppingCartLocalService.getItems(groupId, itemIds);
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
		return _shoppingCartLocalService.dynamicQueryCount(dynamicQuery);
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
		return _shoppingCartLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteGroupCarts(long groupId) {
		_shoppingCartLocalService.deleteGroupCarts(groupId);
	}

	@Override
	public void deleteUserCarts(long userId) {
		_shoppingCartLocalService.deleteUserCarts(userId);
	}

	@Override
	public ShoppingCartLocalService getWrappedService() {
		return _shoppingCartLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingCartLocalService shoppingCartLocalService) {
		_shoppingCartLocalService = shoppingCartLocalService;
	}

	private ShoppingCartLocalService _shoppingCartLocalService;
}