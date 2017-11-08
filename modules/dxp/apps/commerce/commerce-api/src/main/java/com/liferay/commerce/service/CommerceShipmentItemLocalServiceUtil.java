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
 * Provides the local service utility for CommerceShipmentItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceShipmentItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemLocalService
 * @see com.liferay.commerce.service.base.CommerceShipmentItemLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShipmentItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShipmentItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShipmentItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce shipment item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was added
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return getService().addCommerceShipmentItem(commerceShipmentItem);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShipmentItem(commerceShipmentId,
			commerceOrderItemId, quantity, serviceContext);
	}

	/**
	* Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	*
	* @param commerceShipmentItemId the primary key for the new commerce shipment item
	* @return the new commerce shipment item
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem createCommerceShipmentItem(
		long commerceShipmentItemId) {
		return getService().createCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	* Deletes the commerce shipment item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was removed
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem deleteCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return getService().deleteCommerceShipmentItem(commerceShipmentItem);
	}

	/**
	* Deletes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item that was removed
	* @throws PortalException if a commerce shipment item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem deleteCommerceShipmentItem(
		long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceShipmentItem(commerceShipmentItemId);
	}

	public static void deleteCommerceShipmentItems(long groupId,
		long commerceShipment) {
		getService().deleteCommerceShipmentItems(groupId, commerceShipment);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {
		return getService().fetchCommerceShipmentItem(commerceShipmentItemId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce shipment item with the primary key.
	*
	* @param commerceShipmentItemId the primary key of the commerce shipment item
	* @return the commerce shipment item
	* @throws PortalException if a commerce shipment item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem getCommerceShipmentItem(
		long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceShipmentItem(commerceShipmentItemId);
	}

	/**
	* Returns a range of all the commerce shipment items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce shipment items
	* @param end the upper bound of the range of commerce shipment items (not inclusive)
	* @return the range of commerce shipment items
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		int start, int end) {
		return getService().getCommerceShipmentItems(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		long groupId, long commerceShipmentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		return getService()
				   .getCommerceShipmentItems(groupId, commerceShipmentId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce shipment items.
	*
	* @return the number of commerce shipment items
	*/
	public static int getCommerceShipmentItemsCount() {
		return getService().getCommerceShipmentItemsCount();
	}

	public static int getCommerceShipmentItemsCount(long groupId,
		long commerceShipmentId) {
		return getService()
				   .getCommerceShipmentItemsCount(groupId, commerceShipmentId);
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

	/**
	* Updates the commerce shipment item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceShipmentItem the commerce shipment item
	* @return the commerce shipment item that was updated
	*/
	public static com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		com.liferay.commerce.model.CommerceShipmentItem commerceShipmentItem) {
		return getService().updateCommerceShipmentItem(commerceShipmentItem);
	}

	public static com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShipmentItem(commerceShipmentItemId, quantity);
	}

	public static CommerceShipmentItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShipmentItemLocalService, CommerceShipmentItemLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceShipmentItemLocalService.class);
}