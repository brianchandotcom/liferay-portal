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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShipmentItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemLocalService
 * @generated
 */
public class CommerceShipmentItemLocalServiceWrapper
	implements CommerceShipmentItemLocalService,
			   ServiceWrapper<CommerceShipmentItemLocalService> {

	public CommerceShipmentItemLocalServiceWrapper() {
		this(null);
	}

	public CommerceShipmentItemLocalServiceWrapper(
		CommerceShipmentItemLocalService commerceShipmentItemLocalService) {

		_commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	/**
	 * Adds the commerce shipment item to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was added
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		addCommerceShipmentItem(
			com.liferay.commerce.model.CommerceShipmentItem
				commerceShipmentItem) {

		return _commerceShipmentItemLocalService.addCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			addCommerceShipmentItem(
				String externalReferenceCode, long commerceShipmentId,
				long commerceOrderItemId, long commerceInventoryWarehouseId,
				int quantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.addCommerceShipmentItem(
			externalReferenceCode, commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			addDeliverySubscriptionCommerceShipmentItem(
				long groupId, long userId, long commerceShipmentId,
				long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.
			addDeliverySubscriptionCommerceShipmentItem(
				groupId, userId, commerceShipmentId, commerceOrderItemId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			addOrUpdateCommerceShipmentItem(
				String externalReferenceCode, long commerceShipmentId,
				long commerceOrderItemId, long commerceInventoryWarehouseId,
				int quantity,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.
			addOrUpdateCommerceShipmentItem(
				externalReferenceCode, commerceShipmentId, commerceOrderItemId,
				commerceInventoryWarehouseId, quantity, serviceContext);
	}

	/**
	 * Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	 *
	 * @param commerceShipmentItemId the primary key for the new commerce shipment item
	 * @return the new commerce shipment item
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		createCommerceShipmentItem(long commerceShipmentItemId) {

		return _commerceShipmentItemLocalService.createCommerceShipmentItem(
			commerceShipmentItemId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the commerce shipment item from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was removed
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		deleteCommerceShipmentItem(
			com.liferay.commerce.model.CommerceShipmentItem
				commerceShipmentItem) {

		return _commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			deleteCommerceShipmentItem(
				com.liferay.commerce.model.CommerceShipmentItem
					commerceShipmentItem,
				boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem, restoreStockQuantity);
	}

	/**
	 * Deletes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item that was removed
	 * @throws PortalException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItemId);
	}

	@Override
	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItemId, restoreStockQuantity);
	}

	@Override
	public void deleteCommerceShipmentItems(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShipmentItemLocalService.deleteCommerceShipmentItems(
			commerceShipmentId, restoreStockQuantity);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceShipmentItemLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceShipmentItemLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceShipmentItemLocalService.dynamicQuery();
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

		return _commerceShipmentItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
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

		return _commerceShipmentItemLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
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

		return _commerceShipmentItemLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _commerceShipmentItemLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _commerceShipmentItemLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		fetchCommerceShipmentItem(long commerceShipmentItemId) {

		return _commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentItemId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		fetchCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId) {

		return _commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	/**
	 * Returns the commerce shipment item with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment item's external reference code
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		fetchCommerceShipmentItemByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceShipmentItemLocalService.
			fetchCommerceShipmentItemByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceShipmentItemByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		fetchCommerceShipmentItemByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _commerceShipmentItemLocalService.
			fetchCommerceShipmentItemByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceShipmentItemLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce shipment item with the primary key.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item
	 * @throws PortalException if a commerce shipment item with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			getCommerceShipmentItem(long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.getCommerceShipmentItem(
			commerceShipmentItemId);
	}

	/**
	 * Returns the commerce shipment item with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment item's external reference code
	 * @return the matching commerce shipment item
	 * @throws PortalException if a matching commerce shipment item could not be found
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			getCommerceShipmentItemByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.
			getCommerceShipmentItemByExternalReferenceCode(
				companyId, externalReferenceCode);
	}

	/**
	 * Returns a range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of commerce shipment items
	 */
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
		getCommerceShipmentItems(int start, int end) {

		return _commerceShipmentItemLocalService.getCommerceShipmentItems(
			start, end);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
		getCommerceShipmentItems(long commerceOrderItemId) {

		return _commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceOrderItemId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
		getCommerceShipmentItems(
			long commerceShipmentId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceShipmentItem>
					orderByComparator) {

		return _commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
		getCommerceShipmentItems(
			long commerceShipmentId, long commerceOrderItemId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.commerce.model.CommerceShipmentItem>
					orderByComparator) {

		return _commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, commerceOrderItemId, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceShipmentItem>
		getCommerceShipmentItemsByCommerceOrderItemId(
			long commerceOrderItemId) {

		return _commerceShipmentItemLocalService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Returns the number of commerce shipment items.
	 *
	 * @return the number of commerce shipment items
	 */
	@Override
	public int getCommerceShipmentItemsCount() {
		return _commerceShipmentItemLocalService.
			getCommerceShipmentItemsCount();
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return _commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
		long commerceOrderItemId) {

		return _commerceShipmentItemLocalService.
			getCommerceShipmentItemsCountByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
		long commerceShipmentId, long commerceOrderItemId) {

		return _commerceShipmentItemLocalService.
			getCommerceShipmentOrderItemsQuantity(
				commerceShipmentId, commerceOrderItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceShipmentItemLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShipmentItemLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the commerce shipment item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was updated
	 */
	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
		updateCommerceShipmentItem(
			com.liferay.commerce.model.CommerceShipmentItem
				commerceShipmentItem) {

		return _commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItem(
				long commerceShipmentItemId, long commerceInventoryWarehouseId,
				int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity);
	}

	@Override
	public com.liferay.commerce.model.CommerceShipmentItem
			updateCommerceShipmentItemExternalReferenceCode(
				String externalReferenceCode, long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShipmentItemLocalService.
			updateCommerceShipmentItemExternalReferenceCode(
				externalReferenceCode, commerceShipmentItemId);
	}

	@Override
	public CommerceShipmentItemLocalService getWrappedService() {
		return _commerceShipmentItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceShipmentItemLocalService commerceShipmentItemLocalService) {

		_commerceShipmentItemLocalService = commerceShipmentItemLocalService;
	}

	private CommerceShipmentItemLocalService _commerceShipmentItemLocalService;

}