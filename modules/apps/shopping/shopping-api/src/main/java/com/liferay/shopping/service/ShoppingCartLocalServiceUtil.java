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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ShoppingCart. This utility wraps
 * {@link com.liferay.shopping.service.impl.ShoppingCartLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCartLocalService
 * @see com.liferay.shopping.service.base.ShoppingCartLocalServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingCartLocalServiceImpl
 * @generated
 */
@ProviderType
public class ShoppingCartLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingCartLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the shopping cart to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was added
	*/
	public static com.liferay.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return getService().addShoppingCart(shoppingCart);
	}

	/**
	* Creates a new shopping cart with the primary key. Does not add the shopping cart to the database.
	*
	* @param cartId the primary key for the new shopping cart
	* @return the new shopping cart
	*/
	public static com.liferay.shopping.model.ShoppingCart createShoppingCart(
		long cartId) {
		return getService().createShoppingCart(cartId);
	}

	public static void deleteGroupCarts(long groupId) {
		getService().deleteGroupCarts(groupId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart that was removed
	* @throws PortalException if a shopping cart with the primary key could not be found
	*/
	public static com.liferay.shopping.model.ShoppingCart deleteShoppingCart(
		long cartId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteShoppingCart(cartId);
	}

	/**
	* Deletes the shopping cart from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was removed
	*/
	public static com.liferay.shopping.model.ShoppingCart deleteShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return getService().deleteShoppingCart(shoppingCart);
	}

	public static void deleteUserCarts(long userId) {
		getService().deleteUserCarts(userId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.shopping.model.ShoppingCart fetchShoppingCart(
		long cartId) {
		return getService().fetchShoppingCart(cartId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.shopping.model.ShoppingCart getCart(long userId,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCart(userId, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.Map<com.liferay.shopping.model.ShoppingCartItem, java.lang.Integer> getItems(
		long groupId, java.lang.String itemIds) {
		return getService().getItems(groupId, itemIds);
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

	/**
	* Returns the shopping cart with the primary key.
	*
	* @param cartId the primary key of the shopping cart
	* @return the shopping cart
	* @throws PortalException if a shopping cart with the primary key could not be found
	*/
	public static com.liferay.shopping.model.ShoppingCart getShoppingCart(
		long cartId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getShoppingCart(cartId);
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
	public static java.util.List<com.liferay.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end) {
		return getService().getShoppingCarts(start, end);
	}

	/**
	* Returns the number of shopping carts.
	*
	* @return the number of shopping carts
	*/
	public static int getShoppingCartsCount() {
		return getService().getShoppingCartsCount();
	}

	public static com.liferay.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCart(userId, groupId, itemIds, couponCodes,
			altShipping, insure);
	}

	/**
	* Updates the shopping cart in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingCart the shopping cart
	* @return the shopping cart that was updated
	*/
	public static com.liferay.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.shopping.model.ShoppingCart shoppingCart) {
		return getService().updateShoppingCart(shoppingCart);
	}

	public static ShoppingCartLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ShoppingCartLocalService service) {
	}

	private static ServiceTracker<ShoppingCartLocalService, ShoppingCartLocalService> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingCartLocalService.class);
}