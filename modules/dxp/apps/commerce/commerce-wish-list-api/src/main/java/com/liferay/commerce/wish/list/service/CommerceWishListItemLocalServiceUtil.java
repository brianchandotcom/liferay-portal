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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceWishListItem. This utility wraps
 * {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItemLocalService
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListItemLocalServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWishListItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce wish list item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItem the commerce wish list item
	* @return the commerce wish list item that was added
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem addCommerceWishListItem(
		com.liferay.commerce.wish.list.model.CommerceWishListItem commerceWishListItem) {
		return getService().addCommerceWishListItem(commerceWishListItem);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem addCommerceWishListItem(
		long commerceWishListId, long cpDefinitionId, long cpInstanceId,
		String json,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWishListItem(commerceWishListId, cpDefinitionId,
			cpInstanceId, json, serviceContext);
	}

	/**
	* Creates a new commerce wish list item with the primary key. Does not add the commerce wish list item to the database.
	*
	* @param commerceWishListItemId the primary key for the new commerce wish list item
	* @return the new commerce wish list item
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem createCommerceWishListItem(
		long commerceWishListItemId) {
		return getService().createCommerceWishListItem(commerceWishListItemId);
	}

	/**
	* Deletes the commerce wish list item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItem the commerce wish list item
	* @return the commerce wish list item that was removed
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem deleteCommerceWishListItem(
		com.liferay.commerce.wish.list.model.CommerceWishListItem commerceWishListItem) {
		return getService().deleteCommerceWishListItem(commerceWishListItem);
	}

	/**
	* Deletes the commerce wish list item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item that was removed
	* @throws PortalException if a commerce wish list item with the primary key could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem deleteCommerceWishListItem(
		long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceWishListItem(commerceWishListItemId);
	}

	public static void deleteCommerceWishListItems(long commerceWishListId) {
		getService().deleteCommerceWishListItems(commerceWishListId);
	}

	public static void deleteCommerceWishListItemsByCPDefinitionId(
		long cpDefinitionId) {
		getService().deleteCommerceWishListItemsByCPDefinitionId(cpDefinitionId);
	}

	public static void deleteCommerceWishListItemsByCPInstanceId(
		long cpInstanceId) {
		getService().deleteCommerceWishListItemsByCPInstanceId(cpInstanceId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.wish.list.model.CommerceWishListItem fetchCommerceWishListItem(
		long commerceWishListItemId) {
		return getService().fetchCommerceWishListItem(commerceWishListItemId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce wish list item with the primary key.
	*
	* @param commerceWishListItemId the primary key of the commerce wish list item
	* @return the commerce wish list item
	* @throws PortalException if a commerce wish list item with the primary key could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem getCommerceWishListItem(
		long commerceWishListItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListItem(commerceWishListItemId);
	}

	/**
	* Returns a range of all the commerce wish list items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish list items
	* @param end the upper bound of the range of commerce wish list items (not inclusive)
	* @return the range of commerce wish list items
	*/
	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem> getCommerceWishListItems(
		int start, int end) {
		return getService().getCommerceWishListItems(start, end);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishListItem> getCommerceWishListItems(
		long commerceWishListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishListItem> orderByComparator) {
		return getService()
				   .getCommerceWishListItems(commerceWishListId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of commerce wish list items.
	*
	* @return the number of commerce wish list items
	*/
	public static int getCommerceWishListItemsCount() {
		return getService().getCommerceWishListItemsCount();
	}

	public static int getCommerceWishListItemsCount(long commerceWishListId) {
		return getService().getCommerceWishListItemsCount(commerceWishListId);
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
	* Updates the commerce wish list item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListItem the commerce wish list item
	* @return the commerce wish list item that was updated
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishListItem updateCommerceWishListItem(
		com.liferay.commerce.wish.list.model.CommerceWishListItem commerceWishListItem) {
		return getService().updateCommerceWishListItem(commerceWishListItem);
	}

	public static CommerceWishListItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWishListItemLocalService, CommerceWishListItemLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWishListItemLocalService.class);

		ServiceTracker<CommerceWishListItemLocalService, CommerceWishListItemLocalService> serviceTracker =
			new ServiceTracker<CommerceWishListItemLocalService, CommerceWishListItemLocalService>(bundle.getBundleContext(),
				CommerceWishListItemLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}