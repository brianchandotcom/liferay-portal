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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceVirtualOrderItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemLocalService
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemLocalServiceWrapper
	implements CommerceVirtualOrderItemLocalService,
		ServiceWrapper<CommerceVirtualOrderItemLocalService> {
	public CommerceVirtualOrderItemLocalServiceWrapper(
		CommerceVirtualOrderItemLocalService commerceVirtualOrderItemLocalService) {
		_commerceVirtualOrderItemLocalService = commerceVirtualOrderItemLocalService;
	}

	/**
	* Adds the commerce virtual order item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was added
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem commerceVirtualOrderItem) {
		return _commerceVirtualOrderItemLocalService.addCommerceVirtualOrderItem(commerceVirtualOrderItem);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		long commerceOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.addCommerceVirtualOrderItem(commerceOrderItemId,
			fileEntryId, url, activationStatus, duration, usages, maxUsages,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		long commerceOrderItemId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.addCommerceVirtualOrderItem(commerceOrderItemId,
			serviceContext);
	}

	/**
	* Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	*
	* @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	* @return the new commerce virtual order item
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem createCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) {
		return _commerceVirtualOrderItemLocalService.createCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	/**
	* Deletes the commerce virtual order item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was removed
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
		com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem commerceVirtualOrderItem) {
		return _commerceVirtualOrderItemLocalService.deleteCommerceVirtualOrderItem(commerceVirtualOrderItem);
	}

	/**
	* Deletes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	* @return the commerce virtual order item that was removed
	* @throws PortalException if a commerce virtual order item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.deleteCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceVirtualOrderItemLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceVirtualOrderItemLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _commerceVirtualOrderItemLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceVirtualOrderItemLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) {
		return _commerceVirtualOrderItemLocalService.fetchCommerceVirtualOrderItem(commerceVirtualOrderItemId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem fetchCommerceVirtualOrderItemByCommerceOrderItemId(
		long commerceOrderItemId) {
		return _commerceVirtualOrderItemLocalService.fetchCommerceVirtualOrderItemByCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	* Returns the commerce virtual order item matching the UUID and group.
	*
	* @param uuid the commerce virtual order item's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem fetchCommerceVirtualOrderItemByUuidAndGroupId(
		String uuid, long groupId) {
		return _commerceVirtualOrderItemLocalService.fetchCommerceVirtualOrderItemByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceVirtualOrderItemLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce virtual order item with the primary key.
	*
	* @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	* @return the commerce virtual order item
	* @throws PortalException if a commerce virtual order item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem getCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItem(commerceVirtualOrderItemId);
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
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem getCommerceVirtualOrderItemByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItemByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce virtual order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce virtual order items
	* @param end the upper bound of the range of commerce virtual order items (not inclusive)
	* @return the range of commerce virtual order items
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
		int start, int end) {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItems(start,
			end);
	}

	/**
	* Returns all the commerce virtual order items matching the UUID and company.
	*
	* @param uuid the UUID of the commerce virtual order items
	* @param companyId the primary key of the company
	* @return the matching commerce virtual order items, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> getCommerceVirtualOrderItemsByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItemsByUuidAndCompanyId(uuid,
			companyId);
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
	public java.util.List<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> getCommerceVirtualOrderItemsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> orderByComparator) {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItemsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce virtual order items.
	*
	* @return the number of commerce virtual order items
	*/
	@Override
	public int getCommerceVirtualOrderItemsCount() {
		return _commerceVirtualOrderItemLocalService.getCommerceVirtualOrderItemsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceVirtualOrderItemLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public java.io.File getFile(long commerceVirtualOrderItemId)
		throws Exception {
		return _commerceVirtualOrderItemLocalService.getFile(commerceVirtualOrderItemId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceVirtualOrderItemLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> getOrganizationCommerceVirtualOrderItems(
		long groupId, long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> orderByComparator) {
		return _commerceVirtualOrderItemLocalService.getOrganizationCommerceVirtualOrderItems(groupId,
			organizationId, start, end, orderByComparator);
	}

	@Override
	public int getOrganizationCommerceVirtualOrderItemsCount(long groupId,
		long organizationId) {
		return _commerceVirtualOrderItemLocalService.getOrganizationCommerceVirtualOrderItemsCount(groupId,
			organizationId);
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

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> getUserCommerceVirtualOrderItems(
		long groupId, long user, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem> orderByComparator) {
		return _commerceVirtualOrderItemLocalService.getUserCommerceVirtualOrderItems(groupId,
			user, start, end, orderByComparator);
	}

	@Override
	public int getUserCommerceVirtualOrderItemsCount(long groupId, long userId) {
		return _commerceVirtualOrderItemLocalService.getUserCommerceVirtualOrderItemsCount(groupId,
			userId);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem incrementCommerceVirtualOrderItemUsages(
		long commerceVirtualOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.incrementCommerceVirtualOrderItemUsages(commerceVirtualOrderItemId);
	}

	@Override
	public void setActive(long commerceVirtualOrderItemId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceVirtualOrderItemLocalService.setActive(commerceVirtualOrderItemId,
			active);
	}

	/**
	* Updates the commerce virtual order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was updated
	*/
	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem commerceVirtualOrderItem) {
		return _commerceVirtualOrderItemLocalService.updateCommerceVirtualOrderItem(commerceVirtualOrderItem);
	}

	@Override
	public com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItemLocalService.updateCommerceVirtualOrderItem(commerceVirtualOrderItemId,
			fileEntryId, url, activationStatus, duration, usages, maxUsages,
			active);
	}

	@Override
	public CommerceVirtualOrderItemLocalService getWrappedService() {
		return _commerceVirtualOrderItemLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceVirtualOrderItemLocalService commerceVirtualOrderItemLocalService) {
		_commerceVirtualOrderItemLocalService = commerceVirtualOrderItemLocalService;
	}

	private CommerceVirtualOrderItemLocalService _commerceVirtualOrderItemLocalService;
}