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

package com.liferay.commerce.cart.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceCartItem. This utility wraps
 * {@link com.liferay.commerce.cart.service.impl.CommerceCartItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceCartItemLocalService
 * @see com.liferay.commerce.cart.service.base.CommerceCartItemLocalServiceBaseImpl
 * @see com.liferay.commerce.cart.service.impl.CommerceCartItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCartItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.cart.service.impl.CommerceCartItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce cart item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was added
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem addCommerceCartItem(
		com.liferay.commerce.cart.model.CommerceCartItem commerceCartItem) {
		return getService().addCommerceCartItem(commerceCartItem);
	}

	public static com.liferay.commerce.cart.model.CommerceCartItem addCommerceCartItem(
		long commerceCartId, long cpDefinitionId, long cpInstanceId,
		int quantity, java.lang.String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceCartItem(commerceCartId, cpDefinitionId,
			cpInstanceId, quantity, json, serviceContext);
	}

	/**
	* Creates a new commerce cart item with the primary key. Does not add the commerce cart item to the database.
	*
	* @param commerceCartItemId the primary key for the new commerce cart item
	* @return the new commerce cart item
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem createCommerceCartItem(
		long commerceCartItemId) {
		return getService().createCommerceCartItem(commerceCartItemId);
	}

	/**
	* Deletes the commerce cart item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem deleteCommerceCartItem(
		com.liferay.commerce.cart.model.CommerceCartItem commerceCartItem)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceCartItem(commerceCartItem);
	}

	/**
	* Deletes the commerce cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item that was removed
	* @throws PortalException if a commerce cart item with the primary key could not be found
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem deleteCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceCartItem(commerceCartItemId);
	}

	public static com.liferay.commerce.cart.model.CommerceCartItem fetchCommerceCartItem(
		long commerceCartItemId) {
		return getService().fetchCommerceCartItem(commerceCartItemId);
	}

	/**
	* Returns the commerce cart item with the primary key.
	*
	* @param commerceCartItemId the primary key of the commerce cart item
	* @return the commerce cart item
	* @throws PortalException if a commerce cart item with the primary key could not be found
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem getCommerceCartItem(
		long commerceCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceCartItem(commerceCartItemId);
	}

	/**
	* Updates the commerce cart item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCartItem the commerce cart item
	* @return the commerce cart item that was updated
	*/
	public static com.liferay.commerce.cart.model.CommerceCartItem updateCommerceCartItem(
		com.liferay.commerce.cart.model.CommerceCartItem commerceCartItem) {
		return getService().updateCommerceCartItem(commerceCartItem);
	}

	public static com.liferay.commerce.cart.model.CommerceCartItem updateCommerceCartItem(
		long commerceCartItemId, int quantity, java.lang.String json)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceCartItem(commerceCartItemId, quantity, json);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of commerce cart items.
	*
	* @return the number of commerce cart items
	*/
	public static int getCommerceCartItemsCount() {
		return getService().getCommerceCartItemsCount();
	}

	public static int getCommerceCartItemsCount(long commerceCartId) {
		return getService().getCommerceCartItemsCount(commerceCartId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cart items
	* @param end the upper bound of the range of commerce cart items (not inclusive)
	* @return the range of commerce cart items
	*/
	public static java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		int start, int end) {
		return getService().getCommerceCartItems(start, end);
	}

	public static java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {
		return getService().getCommerceCartItems(commerceCartId, start, end);
	}

	public static java.util.List<com.liferay.commerce.cart.model.CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCartItem> orderByComparator) {
		return getService()
				   .getCommerceCartItems(commerceCartId, start, end,
			orderByComparator);
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

	public static void deleteCommerceCartItems(long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceCartItems(commerceCartId);
	}

	public static void validate(long cpDefinitionId, long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(cpDefinitionId, cpInstanceId);
	}

	public static CommerceCartItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartItemLocalService, CommerceCartItemLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartItemLocalService.class);
}