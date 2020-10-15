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

package com.liferay.commerce.inventory.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceInventoryWarehouseItem. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseItemLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItemLocalService
 * @generated
 */
public class CommerceInventoryWarehouseItemLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseItemLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce inventory warehouse item to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseItem the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was added
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			addCommerceInventoryWarehouseItem(
				com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem) {

		return getService().addCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItem);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					long userId, long commerceInventoryWarehouseId, String sku,
					int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouseId, sku, quantity);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceInventoryWarehouseItem(String, long, long,
	 String, int)}
	 */
	@Deprecated
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					long userId, long commerceInventoryWarehouseId,
					String externalReferenceCode, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouseId, externalReferenceCode, sku,
			quantity);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				addCommerceInventoryWarehouseItem(
					String externalReferenceCode, long userId,
					long commerceInventoryWarehouseId, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceInventoryWarehouseItem(
			externalReferenceCode, userId, commerceInventoryWarehouseId, sku,
			quantity);
	}

	public static int countItemsByCompanyId(long companyId, String sku) {
		return getService().countItemsByCompanyId(companyId, sku);
	}

	/**
	 * Creates a new commerce inventory warehouse item with the primary key. Does not add the commerce inventory warehouse item to the database.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key for the new commerce inventory warehouse item
	 * @return the new commerce inventory warehouse item
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			createCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId) {

		return getService().createCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce inventory warehouse item from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseItem the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was removed
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			deleteCommerceInventoryWarehouseItem(
				com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem) {

		return getService().deleteCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItem);
	}

	/**
	 * Deletes the commerce inventory warehouse item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was removed
	 * @throws PortalException if a commerce inventory warehouse item with the primary key could not be found
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				deleteCommerceInventoryWarehouseItem(
					long commerceInventoryWarehouseItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	public static void deleteCommerceInventoryWarehouseItems(
		long commerceInventoryWarehouseId) {

		getService().deleteCommerceInventoryWarehouseItems(
			commerceInventoryWarehouseId);
	}

	public static void deleteCommerceInventoryWarehouseItems(
		long companyId, String sku) {

		getService().deleteCommerceInventoryWarehouseItems(companyId, sku);
	}

	public static void deleteCommerceInventoryWarehouseItemsByCompanyId(
		long companyId) {

		getService().deleteCommerceInventoryWarehouseItemsByCompanyId(
			companyId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseItemId) {

		return getService().fetchCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItem(
				long commerceInventoryWarehouseId, String sku) {

		return getService().fetchCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId, sku);
	}

	/**
	 * Returns the commerce inventory warehouse item with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce inventory warehouse item's external reference code
	 * @return the matching commerce inventory warehouse item, or <code>null</code> if a matching commerce inventory warehouse item could not be found
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			fetchCommerceInventoryWarehouseItemByReferenceCode(
				long companyId, String externalReferenceCode) {

		return getService().fetchCommerceInventoryWarehouseItemByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce inventory warehouse item with the primary key.
	 *
	 * @param commerceInventoryWarehouseItemId the primary key of the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item
	 * @throws PortalException if a commerce inventory warehouse item with the primary key could not be found
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItem(
					long commerceInventoryWarehouseItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItemId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #getCommerceInventoryWarehouseItemByReferenceCode(String,
	 long)}
	 */
	@Deprecated
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItemByReferenceCode(
					long companyId, String externalReferenceCode)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceInventoryWarehouseItemByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				getCommerceInventoryWarehouseItemByReferenceCode(
					String externalReferenceCode, long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceInventoryWarehouseItemByReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns a range of all the commerce inventory warehouse items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.inventory.model.impl.CommerceInventoryWarehouseItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory warehouse items
	 * @param end the upper bound of the range of commerce inventory warehouse items (not inclusive)
	 * @return the range of commerce inventory warehouse items
	 */
	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(int start, int end) {

		return getService().getCommerceInventoryWarehouseItems(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long commerceInventoryWarehouseId, int start, int end) {

		return getService().getCommerceInventoryWarehouseItems(
			commerceInventoryWarehouseId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItems(
				long companyId, String sku, int start, int end) {

		return getService().getCommerceInventoryWarehouseItems(
			companyId, sku, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyId(
				long companyId, int start, int end) {

		return getService().getCommerceInventoryWarehouseItemsByCompanyId(
			companyId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
				long companyId, String sku, int start, int end) {

		return getService().getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
			companyId, sku, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			getCommerceInventoryWarehouseItemsByModifiedDate(
				long companyId, java.util.Date startDate,
				java.util.Date endDate, int start, int end) {

		return getService().getCommerceInventoryWarehouseItemsByModifiedDate(
			companyId, startDate, endDate, start, end);
	}

	/**
	 * Returns the number of commerce inventory warehouse items.
	 *
	 * @return the number of commerce inventory warehouse items
	 */
	public static int getCommerceInventoryWarehouseItemsCount() {
		return getService().getCommerceInventoryWarehouseItemsCount();
	}

	public static int getCommerceInventoryWarehouseItemsCount(
		long commerceInventoryWarehouseId) {

		return getService().getCommerceInventoryWarehouseItemsCount(
			commerceInventoryWarehouseId);
	}

	public static int getCommerceInventoryWarehouseItemsCount(
		long companyId, String sku) {

		return getService().getCommerceInventoryWarehouseItemsCount(
			companyId, sku);
	}

	public static int getCommerceInventoryWarehouseItemsCountByCompanyId(
		long companyId) {

		return getService().getCommerceInventoryWarehouseItemsCountByCompanyId(
			companyId);
	}

	public static int getCommerceInventoryWarehouseItemsCountByModifiedDate(
		long companyId, java.util.Date startDate, java.util.Date endDate) {

		return getService().
			getCommerceInventoryWarehouseItemsCountByModifiedDate(
				companyId, startDate, endDate);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.commerce.inventory.model.CIWarehouseItem>
			getItemsByCompanyId(
				long companyId, String sku, int start, int end) {

		return getService().getItemsByCompanyId(companyId, sku, start, end);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static int getStockQuantity(
		long companyId, long groupId, String sku) {

		return getService().getStockQuantity(companyId, groupId, sku);
	}

	public static int getStockQuantity(long companyId, String sku) {
		return getService().getStockQuantity(companyId, sku);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				increaseCommerceInventoryWarehouseItemQuantity(
					long userId, long commerceInventoryWarehouseItemId,
					int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().increaseCommerceInventoryWarehouseItemQuantity(
			userId, commerceInventoryWarehouseItemId, quantity);
	}

	public static void moveQuantitiesBetweenWarehouses(
			long userId, long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().moveQuantitiesBetweenWarehouses(
			userId, fromCommerceInventoryWarehouseId,
			toCommerceInventoryWarehouseId, sku, quantity);
	}

	/**
	 * Updates the commerce inventory warehouse item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceInventoryWarehouseItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceInventoryWarehouseItem the commerce inventory warehouse item
	 * @return the commerce inventory warehouse item that was updated
	 */
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
			updateCommerceInventoryWarehouseItem(
				com.liferay.commerce.inventory.model.
					CommerceInventoryWarehouseItem
						commerceInventoryWarehouseItem) {

		return getService().updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseItem);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				updateCommerceInventoryWarehouseItem(
					long userId, long commerceInventoryWarehouseItemId,
					int quantity, int reservedQuantity, long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouseItemId, quantity,
			reservedQuantity, mvccVersion);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				updateCommerceInventoryWarehouseItem(
					long userId, long commerceInventoryWarehouseItemId,
					int quantity, long mvccVersion)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouseItemId, quantity, mvccVersion);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommerceInventoryWarehouseItem(String,
	 long, long, long, String, int)}
	 */
	@Deprecated
	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				upsertCommerceInventoryWarehouseItem(
					long companyId, long userId,
					long commerceInventoryWarehouseId,
					String externalReferenceCode, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceInventoryWarehouseItem(
			companyId, userId, commerceInventoryWarehouseId,
			externalReferenceCode, sku, quantity);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				upsertCommerceInventoryWarehouseItem(
					long userId, long commerceInventoryWarehouseId, String sku,
					int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceInventoryWarehouseItem(
			userId, commerceInventoryWarehouseId, sku, quantity);
	}

	public static
		com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem
				upsertCommerceInventoryWarehouseItem(
					String externalReferenceCode, long companyId, long userId,
					long commerceInventoryWarehouseId, String sku, int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommerceInventoryWarehouseItem(
			externalReferenceCode, companyId, userId,
			commerceInventoryWarehouseId, sku, quantity);
	}

	public static CommerceInventoryWarehouseItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceInventoryWarehouseItemLocalService,
		 CommerceInventoryWarehouseItemLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceInventoryWarehouseItemLocalService.class);

		ServiceTracker
			<CommerceInventoryWarehouseItemLocalService,
			 CommerceInventoryWarehouseItemLocalService> serviceTracker =
				new ServiceTracker
					<CommerceInventoryWarehouseItemLocalService,
					 CommerceInventoryWarehouseItemLocalService>(
						 bundle.getBundleContext(),
						 CommerceInventoryWarehouseItemLocalService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}