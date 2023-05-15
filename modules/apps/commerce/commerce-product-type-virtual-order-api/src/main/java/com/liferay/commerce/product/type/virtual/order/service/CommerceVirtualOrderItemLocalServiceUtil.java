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

package com.liferay.commerce.product.type.virtual.order.service;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for CommerceVirtualOrderItem. This utility wraps
 * <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemLocalService
 * @generated
 */
public class CommerceVirtualOrderItemLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce virtual order item to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceVirtualOrderItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 * @return the commerce virtual order item that was added
	 */
	public static CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return getService().addCommerceVirtualOrderItem(
			commerceVirtualOrderItem);
	}

	public static CommerceVirtualOrderItem addCommerceVirtualOrderItem(
			long commerceOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceVirtualOrderItem(
			commerceOrderItemId, fileEntryId, url, activationStatus, duration,
			usages, maxUsages, serviceContext);
	}

	public static CommerceVirtualOrderItem addCommerceVirtualOrderItem(
			long commerceOrderItemId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceVirtualOrderItem(
			commerceOrderItemId, serviceContext);
	}

	public static void checkCommerceVirtualOrderItems() throws PortalException {
		getService().checkCommerceVirtualOrderItems();
	}

	/**
	 * Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	 *
	 * @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	 * @return the new commerce virtual order item
	 */
	public static CommerceVirtualOrderItem createCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) {

		return getService().createCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce virtual order item from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceVirtualOrderItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 */
	public static CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return getService().deleteCommerceVirtualOrderItem(
			commerceVirtualOrderItem);
	}

	/**
	 * Deletes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceVirtualOrderItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item that was removed
	 * @throws PortalException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId)
		throws PortalException {

		return getService().deleteCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	public static void deleteCommerceVirtualOrderItemByCommerceOrderItemId(
		long commerceOrderItemId) {

		getService().deleteCommerceVirtualOrderItemByCommerceOrderItemId(
			commerceOrderItemId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) {

		return getService().fetchCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	public static CommerceVirtualOrderItem
		fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			long commerceOrderItemId) {

		return getService().fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			commerceOrderItemId);
	}

	public static CommerceVirtualOrderItem
		fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			long commerceOrderItemId, boolean useFinderCache) {

		return getService().fetchCommerceVirtualOrderItemByCommerceOrderItemId(
			commerceOrderItemId, useFinderCache);
	}

	/**
	 * Returns the commerce virtual order item matching the UUID and group.
	 *
	 * @param uuid the commerce virtual order item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem
		fetchCommerceVirtualOrderItemByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchCommerceVirtualOrderItemByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce virtual order item with the primary key.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws PortalException if a commerce virtual order item with the primary key could not be found
	 */
	public static CommerceVirtualOrderItem getCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId)
		throws PortalException {

		return getService().getCommerceVirtualOrderItem(
			commerceVirtualOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item matching the UUID and group.
	 *
	 * @param uuid the commerce virtual order item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce virtual order item
	 * @throws PortalException if a matching commerce virtual order item could not be found
	 */
	public static CommerceVirtualOrderItem
			getCommerceVirtualOrderItemByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException {

		return getService().getCommerceVirtualOrderItemByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the commerce virtual order items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @return the range of commerce virtual order items
	 */
	public static List<CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
		int start, int end) {

		return getService().getCommerceVirtualOrderItems(start, end);
	}

	public static List<CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
		long groupId, long commerceAccountId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getService().getCommerceVirtualOrderItems(
			groupId, commerceAccountId, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce virtual order items matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce virtual order items
	 * @param companyId the primary key of the company
	 * @return the matching commerce virtual order items, or an empty list if no matches were found
	 */
	public static List<CommerceVirtualOrderItem>
		getCommerceVirtualOrderItemsByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().getCommerceVirtualOrderItemsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of commerce virtual order items matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce virtual order items
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of commerce virtual order items
	 * @param end the upper bound of the range of commerce virtual order items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching commerce virtual order items, or an empty list if no matches were found
	 */
	public static List<CommerceVirtualOrderItem>
		getCommerceVirtualOrderItemsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CommerceVirtualOrderItem> orderByComparator) {

		return getService().getCommerceVirtualOrderItemsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce virtual order items.
	 *
	 * @return the number of commerce virtual order items
	 */
	public static int getCommerceVirtualOrderItemsCount() {
		return getService().getCommerceVirtualOrderItemsCount();
	}

	public static int getCommerceVirtualOrderItemsCount(
		long groupId, long commerceAccountId) {

		return getService().getCommerceVirtualOrderItemsCount(
			groupId, commerceAccountId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {

		return getService().getFile(commerceVirtualOrderItemId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static CommerceVirtualOrderItem
			incrementCommerceVirtualOrderItemUsages(
				long commerceVirtualOrderItemId)
		throws PortalException {

		return getService().incrementCommerceVirtualOrderItemUsages(
			commerceVirtualOrderItemId);
	}

	public static void setActive(
			long commerceVirtualOrderItemId, boolean active)
		throws PortalException {

		getService().setActive(commerceVirtualOrderItemId, active);
	}

	/**
	 * Updates the commerce virtual order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceVirtualOrderItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceVirtualOrderItem the commerce virtual order item
	 * @return the commerce virtual order item that was updated
	 */
	public static CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return getService().updateCommerceVirtualOrderItem(
			commerceVirtualOrderItem);
	}

	public static CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId, long fileEntryId, String url,
			int activationStatus, long duration, int usages, int maxUsages,
			boolean active)
		throws PortalException {

		return getService().updateCommerceVirtualOrderItem(
			commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
			duration, usages, maxUsages, active);
	}

	public static CommerceVirtualOrderItem updateCommerceVirtualOrderItemDates(
			long commerceVirtualOrderItemId)
		throws PortalException {

		return getService().updateCommerceVirtualOrderItemDates(
			commerceVirtualOrderItemId);
	}

	public static CommerceVirtualOrderItemLocalService getService() {
		return _service;
	}

	public static void setService(
		CommerceVirtualOrderItemLocalService service) {

		_service = service;
	}

	private static volatile CommerceVirtualOrderItemLocalService _service;

}