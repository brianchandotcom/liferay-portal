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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceWarehouseItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceWarehouseItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseItemLocalService
 * @see com.liferay.commerce.service.base.CommerceWarehouseItemLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce warehouse item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItem the commerce warehouse item
	* @return the commerce warehouse item that was added
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem addCommerceWarehouseItem(
		com.liferay.commerce.model.CommerceWarehouseItem commerceWarehouseItem) {
		return getService().addCommerceWarehouseItem(commerceWarehouseItem);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem addCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouseItem(commerceWarehouseId, cpInstanceId,
			quantity, serviceContext);
	}

	/**
	* Creates a new commerce warehouse item with the primary key. Does not add the commerce warehouse item to the database.
	*
	* @param commerceWarehouseItemId the primary key for the new commerce warehouse item
	* @return the new commerce warehouse item
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem createCommerceWarehouseItem(
		long commerceWarehouseItemId) {
		return getService().createCommerceWarehouseItem(commerceWarehouseItemId);
	}

	/**
	* Deletes the commerce warehouse item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItem the commerce warehouse item
	* @return the commerce warehouse item that was removed
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem deleteCommerceWarehouseItem(
		com.liferay.commerce.model.CommerceWarehouseItem commerceWarehouseItem) {
		return getService().deleteCommerceWarehouseItem(commerceWarehouseItem);
	}

	/**
	* Deletes the commerce warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item that was removed
	* @throws PortalException if a commerce warehouse item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem deleteCommerceWarehouseItem(
		long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static void deleteCommerceWarehouseItems(long commerceWarehouseId) {
		getService().deleteCommerceWarehouseItems(commerceWarehouseId);
	}

	public static void deleteCommerceWarehouseItemsByCPInstanceId(
		long cpInstanceId) {
		getService().deleteCommerceWarehouseItemsByCPInstanceId(cpInstanceId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceWarehouseItem fetchCommerceWarehouseItem(
		long commerceWarehouseItemId) {
		return getService().fetchCommerceWarehouseItem(commerceWarehouseItemId);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem fetchCommerceWarehouseItem(
		long commerceWarehouseId, long cpInstanceId) {
		return getService()
				   .fetchCommerceWarehouseItem(commerceWarehouseId, cpInstanceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce warehouse item with the primary key.
	*
	* @param commerceWarehouseItemId the primary key of the commerce warehouse item
	* @return the commerce warehouse item
	* @throws PortalException if a commerce warehouse item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem getCommerceWarehouseItem(
		long commerceWarehouseItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouseItem(commerceWarehouseItemId);
	}

	/**
	* Returns a range of all the commerce warehouse items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceWarehouseItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce warehouse items
	* @param end the upper bound of the range of commerce warehouse items (not inclusive)
	* @return the range of commerce warehouse items
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		int start, int end) {
		return getService().getCommerceWarehouseItems(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId) {
		return getService().getCommerceWarehouseItems(cpInstanceId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouseItem> orderByComparator) {
		return getService()
				   .getCommerceWarehouseItems(cpInstanceId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouseItem> getCommerceWarehouseItemsByCommerceWarehouseId(
		long commerceWarehouseId) {
		return getService()
				   .getCommerceWarehouseItemsByCommerceWarehouseId(commerceWarehouseId);
	}

	/**
	* Returns the number of commerce warehouse items.
	*
	* @return the number of commerce warehouse items
	*/
	public static int getCommerceWarehouseItemsCount() {
		return getService().getCommerceWarehouseItemsCount();
	}

	public static int getCommerceWarehouseItemsCount(long cpInstanceId) {
		return getService().getCommerceWarehouseItemsCount(cpInstanceId);
	}

	public static int getCPInstanceQuantity(long cpInstanceId) {
		return getService().getCPInstanceQuantity(cpInstanceId);
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
	* Updates the commerce warehouse item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWarehouseItem the commerce warehouse item
	* @return the commerce warehouse item that was updated
	*/
	public static com.liferay.commerce.model.CommerceWarehouseItem updateCommerceWarehouseItem(
		com.liferay.commerce.model.CommerceWarehouseItem commerceWarehouseItem) {
		return getService().updateCommerceWarehouseItem(commerceWarehouseItem);
	}

	public static com.liferay.commerce.model.CommerceWarehouseItem updateCommerceWarehouseItem(
		long commerceWarehouseItemId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouseItem(commerceWarehouseItemId,
			quantity, serviceContext);
	}

	public static CommerceWarehouseItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseItemLocalService, CommerceWarehouseItemLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWarehouseItemLocalService.class);

		ServiceTracker<CommerceWarehouseItemLocalService, CommerceWarehouseItemLocalService> serviceTracker =
			new ServiceTracker<CommerceWarehouseItemLocalService, CommerceWarehouseItemLocalService>(bundle.getBundleContext(),
				CommerceWarehouseItemLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}