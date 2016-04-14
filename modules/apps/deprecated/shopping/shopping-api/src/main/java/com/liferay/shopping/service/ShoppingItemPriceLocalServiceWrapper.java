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
 * Provides a wrapper for {@link ShoppingItemPriceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemPriceLocalService
 * @generated
 */
@ProviderType
public class ShoppingItemPriceLocalServiceWrapper
	implements ShoppingItemPriceLocalService,
		ServiceWrapper<ShoppingItemPriceLocalService> {
	public ShoppingItemPriceLocalServiceWrapper(
		ShoppingItemPriceLocalService shoppingItemPriceLocalService) {
		_shoppingItemPriceLocalService = shoppingItemPriceLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingItemPriceLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingItemPriceLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingItemPriceLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemPriceLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemPriceLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the shopping item price to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemPrice the shopping item price
	* @return the shopping item price that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice addShoppingItemPrice(
		com.liferay.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		return _shoppingItemPriceLocalService.addShoppingItemPrice(shoppingItemPrice);
	}

	/**
	* Creates a new shopping item price with the primary key. Does not add the shopping item price to the database.
	*
	* @param itemPriceId the primary key for the new shopping item price
	* @return the new shopping item price
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice createShoppingItemPrice(
		long itemPriceId) {
		return _shoppingItemPriceLocalService.createShoppingItemPrice(itemPriceId);
	}

	/**
	* Deletes the shopping item price from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemPrice the shopping item price
	* @return the shopping item price that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice deleteShoppingItemPrice(
		com.liferay.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		return _shoppingItemPriceLocalService.deleteShoppingItemPrice(shoppingItemPrice);
	}

	/**
	* Deletes the shopping item price with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price that was removed
	* @throws PortalException if a shopping item price with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice deleteShoppingItemPrice(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemPriceLocalService.deleteShoppingItemPrice(itemPriceId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingItemPrice fetchShoppingItemPrice(
		long itemPriceId) {
		return _shoppingItemPriceLocalService.fetchShoppingItemPrice(itemPriceId);
	}

	/**
	* Returns the shopping item price with the primary key.
	*
	* @param itemPriceId the primary key of the shopping item price
	* @return the shopping item price
	* @throws PortalException if a shopping item price with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice getShoppingItemPrice(
		long itemPriceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemPriceLocalService.getShoppingItemPrice(itemPriceId);
	}

	/**
	* Updates the shopping item price in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingItemPrice the shopping item price
	* @return the shopping item price that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingItemPrice updateShoppingItemPrice(
		com.liferay.shopping.model.ShoppingItemPrice shoppingItemPrice) {
		return _shoppingItemPriceLocalService.updateShoppingItemPrice(shoppingItemPrice);
	}

	/**
	* Returns the number of shopping item prices.
	*
	* @return the number of shopping item prices
	*/
	@Override
	public int getShoppingItemPricesCount() {
		return _shoppingItemPriceLocalService.getShoppingItemPricesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingItemPriceLocalService.getOSGiServiceIdentifier();
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
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _shoppingItemPriceLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItemPrice> getItemPrices(
		long itemId) throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingItemPriceLocalService.getItemPrices(itemId);
	}

	/**
	* Returns a range of all the shopping item prices.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingItemPriceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping item prices
	* @param end the upper bound of the range of shopping item prices (not inclusive)
	* @return the range of shopping item prices
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingItemPrice> getShoppingItemPrices(
		int start, int end) {
		return _shoppingItemPriceLocalService.getShoppingItemPrices(start, end);
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
		return _shoppingItemPriceLocalService.dynamicQueryCount(dynamicQuery);
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
		return _shoppingItemPriceLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public ShoppingItemPriceLocalService getWrappedService() {
		return _shoppingItemPriceLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingItemPriceLocalService shoppingItemPriceLocalService) {
		_shoppingItemPriceLocalService = shoppingItemPriceLocalService;
	}

	private ShoppingItemPriceLocalService _shoppingItemPriceLocalService;
}