/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link CommerceVirtualOrderItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemLocalService
 * @generated
 */
public class CommerceVirtualOrderItemLocalServiceWrapper
	implements CommerceVirtualOrderItemLocalService,
			   ServiceWrapper<CommerceVirtualOrderItemLocalService> {

	public CommerceVirtualOrderItemLocalServiceWrapper() {
		this(null);
	}

	public CommerceVirtualOrderItemLocalServiceWrapper(
		CommerceVirtualOrderItemLocalService
			commerceVirtualOrderItemLocalService) {

		_commerceVirtualOrderItemLocalService =
			commerceVirtualOrderItemLocalService;
	}

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
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem addCommerceVirtualOrderItem(
			com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return _commerceVirtualOrderItemLocalService.
			addCommerceVirtualOrderItem(commerceVirtualOrderItem);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem addCommerceVirtualOrderItem(
				long commerceOrderItemId, long fileEntryId, String url,
				int activationStatus, long duration, int usages, int maxUsages,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			addCommerceVirtualOrderItem(
				commerceOrderItemId, fileEntryId, url, activationStatus,
				duration, usages, maxUsages, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem addCommerceVirtualOrderItem(
				long commerceOrderItemId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			addCommerceVirtualOrderItem(commerceOrderItemId, serviceContext);
	}

	@Override
	public void checkCommerceVirtualOrderItems()
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceVirtualOrderItemLocalService.checkCommerceVirtualOrderItems();
	}

	/**
	 * Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	 *
	 * @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	 * @return the new commerce virtual order item
	 */
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem createCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId) {

		return _commerceVirtualOrderItemLocalService.
			createCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.createPersistedModel(
			primaryKeyObj);
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
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
			com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return _commerceVirtualOrderItemLocalService.
			deleteCommerceVirtualOrderItem(commerceVirtualOrderItem);
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
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
				long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			deleteCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	@Override
	public void deleteCommerceVirtualOrderItemByCommerceOrderItemId(
		long commerceOrderItemId) {

		_commerceVirtualOrderItemLocalService.
			deleteCommerceVirtualOrderItemByCommerceOrderItemId(
				commerceOrderItemId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _commerceVirtualOrderItemLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _commerceVirtualOrderItemLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceVirtualOrderItemLocalService.dynamicQuery();
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

		return _commerceVirtualOrderItemLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _commerceVirtualOrderItemLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _commerceVirtualOrderItemLocalService.dynamicQuery(
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

		return _commerceVirtualOrderItemLocalService.dynamicQueryCount(
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

		return _commerceVirtualOrderItemLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
			long commerceVirtualOrderItemId) {

		return _commerceVirtualOrderItemLocalService.
			fetchCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				long commerceOrderItemId) {

		return _commerceVirtualOrderItemLocalService.
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				long commerceOrderItemId, boolean useFinderCache) {

		return _commerceVirtualOrderItemLocalService.
			fetchCommerceVirtualOrderItemByCommerceOrderItemId(
				commerceOrderItemId, useFinderCache);
	}

	/**
	 * Returns the commerce virtual order item matching the UUID and group.
	 *
	 * @param uuid the commerce virtual order item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	 */
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem fetchCommerceVirtualOrderItemByUuidAndGroupId(
			String uuid, long groupId) {

		return _commerceVirtualOrderItemLocalService.
			fetchCommerceVirtualOrderItemByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _commerceVirtualOrderItemLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce virtual order item with the primary key.
	 *
	 * @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	 * @return the commerce virtual order item
	 * @throws PortalException if a commerce virtual order item with the primary key could not be found
	 */
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem getCommerceVirtualOrderItem(
				long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	/**
	 * Returns the commerce virtual order item matching the UUID and group.
	 *
	 * @param uuid the commerce virtual order item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching commerce virtual order item
	 * @throws PortalException if a matching commerce virtual order item could not be found
	 */
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem getCommerceVirtualOrderItemByUuidAndGroupId(
				String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItemByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
				int start, int end) {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItems(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
				long groupId, long commerceAccountId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.type.virtual.order.model.
						CommerceVirtualOrderItem> orderByComparator) {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItems(
				groupId, commerceAccountId, start, end, orderByComparator);
	}

	/**
	 * Returns all the commerce virtual order items matching the UUID and company.
	 *
	 * @param uuid the UUID of the commerce virtual order items
	 * @param companyId the primary key of the company
	 * @return the matching commerce virtual order items, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem>
				getCommerceVirtualOrderItemsByUuidAndCompanyId(
					String uuid, long companyId) {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItemsByUuidAndCompanyId(uuid, companyId);
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
	@Override
	public java.util.List
		<com.liferay.commerce.product.type.virtual.order.model.
			CommerceVirtualOrderItem>
				getCommerceVirtualOrderItemsByUuidAndCompanyId(
					String uuid, long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.type.virtual.order.model.
							CommerceVirtualOrderItem> orderByComparator) {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItemsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of commerce virtual order items.
	 *
	 * @return the number of commerce virtual order items
	 */
	@Override
	public int getCommerceVirtualOrderItemsCount() {
		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItemsCount();
	}

	@Override
	public int getCommerceVirtualOrderItemsCount(
		long groupId, long commerceAccountId) {

		return _commerceVirtualOrderItemLocalService.
			getCommerceVirtualOrderItemsCount(groupId, commerceAccountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _commerceVirtualOrderItemLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {

		return _commerceVirtualOrderItemLocalService.getFile(
			commerceVirtualOrderItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _commerceVirtualOrderItemLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceVirtualOrderItemLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem incrementCommerceVirtualOrderItemUsages(
				long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			incrementCommerceVirtualOrderItemUsages(commerceVirtualOrderItemId);
	}

	@Override
	public void setActive(long commerceVirtualOrderItemId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceVirtualOrderItemLocalService.setActive(
			commerceVirtualOrderItemId, active);
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
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
			com.liferay.commerce.product.type.virtual.order.model.
				CommerceVirtualOrderItem commerceVirtualOrderItem) {

		return _commerceVirtualOrderItemLocalService.
			updateCommerceVirtualOrderItem(commerceVirtualOrderItem);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
				long commerceVirtualOrderItemId, long fileEntryId, String url,
				int activationStatus, long duration, int usages, int maxUsages,
				boolean active)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			updateCommerceVirtualOrderItem(
				commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
				duration, usages, maxUsages, active);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.
		CommerceVirtualOrderItem updateCommerceVirtualOrderItemDates(
				long commerceVirtualOrderItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceVirtualOrderItemLocalService.
			updateCommerceVirtualOrderItemDates(commerceVirtualOrderItemId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceVirtualOrderItemLocalService.getBasePersistence();
	}

	@Override
	public CommerceVirtualOrderItemLocalService getWrappedService() {
		return _commerceVirtualOrderItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceVirtualOrderItemLocalService
			commerceVirtualOrderItemLocalService) {

		_commerceVirtualOrderItemLocalService =
			commerceVirtualOrderItemLocalService;
	}

	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

}