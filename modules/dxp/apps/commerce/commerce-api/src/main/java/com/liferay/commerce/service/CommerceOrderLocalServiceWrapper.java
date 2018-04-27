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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceOrderLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderLocalService
 * @generated
 */
@ProviderType
public class CommerceOrderLocalServiceWrapper
	implements CommerceOrderLocalService,
		ServiceWrapper<CommerceOrderLocalService> {
	public CommerceOrderLocalServiceWrapper(
		CommerceOrderLocalService commerceOrderLocalService) {
		_commerceOrderLocalService = commerceOrderLocalService;
	}

	/**
	* Adds the commerce order to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder addCommerceOrder(
		com.liferay.commerce.model.CommerceOrder commerceOrder) {
		return _commerceOrderLocalService.addCommerceOrder(commerceOrder);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder addOrganizationCommerceOrder(
		long groupId, long userId, long siteGroupId, long orderOrganizationId,
		long shippingAddressId, String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.addOrganizationCommerceOrder(groupId,
			userId, siteGroupId, orderOrganizationId, shippingAddressId,
			purchaseOrderNumber);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.addUserCommerceOrder(groupId, userId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId, long userId, long orderUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.addUserCommerceOrder(groupId, userId,
			orderUserId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder approveCommerceOrder(
		long userId, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.approveCommerceOrder(userId,
			commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder cancelCommerceOrderPayment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.cancelCommerceOrderPayment(commerceOrderId,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder checkoutCommerceOrder(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.checkoutCommerceOrder(commerceOrderId,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder completeCommerceOrderPayment(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.completeCommerceOrderPayment(commerceOrderId,
			serviceContext);
	}

	/**
	* Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	*
	* @param commerceOrderId the primary key for the new commerce order
	* @return the new commerce order
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder createCommerceOrder(
		long commerceOrderId) {
		return _commerceOrderLocalService.createCommerceOrder(commerceOrderId);
	}

	/**
	* Deletes the commerce order from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder deleteCommerceOrder(
		com.liferay.commerce.model.CommerceOrder commerceOrder)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
	}

	/**
	* Deletes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order that was removed
	* @throws PortalException if a commerce order with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder deleteCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.deleteCommerceOrder(commerceOrderId);
	}

	@Override
	public void deleteCommerceOrders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderLocalService.deleteCommerceOrders(groupId);
	}

	@Override
	public void deleteCommerceOrders(long userId, java.util.Date date,
		int status) {
		_commerceOrderLocalService.deleteCommerceOrders(userId, date, status);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceOrderLocalService.dynamicQuery();
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
		return _commerceOrderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceOrderLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _commerceOrderLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceOrderLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder executeWorkflowTransition(
		long userId, long commerceOrderId, long workflowTaskId,
		String transitionName, String comment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.executeWorkflowTransition(userId,
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long commerceOrderId) {
		return _commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long groupId, long userId, int orderStatus) {
		return _commerceOrderLocalService.fetchCommerceOrder(groupId, userId,
			orderStatus);
	}

	/**
	* Returns the commerce order matching the UUID and group.
	*
	* @param uuid the commerce order's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder fetchCommerceOrderByUuidAndGroupId(
		String uuid, long groupId) {
		return _commerceOrderLocalService.fetchCommerceOrderByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceOrderLocalService.getActionableDynamicQuery();
	}

	@Override
	public int[] getAvailableOrderStatuses(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.getAvailableOrderStatuses(commerceOrderId);
	}

	/**
	* Returns the commerce order with the primary key.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order
	* @throws PortalException if a commerce order with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder getCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.getCommerceOrder(commerceOrderId);
	}

	/**
	* Returns the commerce order matching the UUID and group.
	*
	* @param uuid the commerce order's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce order
	* @throws PortalException if a matching commerce order could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder getCommerceOrderByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of commerce orders
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		int start, int end) {
		return _commerceOrderLocalService.getCommerceOrders(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long groupId, long orderUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator) {
		return _commerceOrderLocalService.getCommerceOrders(groupId,
			orderUserId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrdersByBillingAddress(
		long billingAddressId) {
		return _commerceOrderLocalService.getCommerceOrdersByBillingAddress(billingAddressId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrdersByShippingAddress(
		long shippingAddressId) {
		return _commerceOrderLocalService.getCommerceOrdersByShippingAddress(shippingAddressId);
	}

	/**
	* Returns all the commerce orders matching the UUID and company.
	*
	* @param uuid the UUID of the commerce orders
	* @param companyId the primary key of the company
	* @return the matching commerce orders, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrdersByUuidAndCompanyId(
		String uuid, long companyId) {
		return _commerceOrderLocalService.getCommerceOrdersByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce orders matching the UUID and company.
	*
	* @param uuid the UUID of the commerce orders
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce orders, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrdersByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator) {
		return _commerceOrderLocalService.getCommerceOrdersByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce orders.
	*
	* @return the number of commerce orders
	*/
	@Override
	public int getCommerceOrdersCount() {
		return _commerceOrderLocalService.getCommerceOrdersCount();
	}

	@Override
	public int getCommerceOrdersCount(long groupId, long orderUserId) {
		return _commerceOrderLocalService.getCommerceOrdersCount(groupId,
			orderUserId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceOrderLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceOrderLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceOrderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void mergeGuestCommerceOrder(long guestCommerceOrderId,
		long userCommerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceOrderLocalService.mergeGuestCommerceOrder(guestCommerceOrderId,
			userCommerceOrderId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder reorderCommerceOrder(
		long userId, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.reorderCommerceOrder(userId,
			commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder resetCommerceOrderShipping(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.resetCommerceOrderShipping(commerceOrderId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrder> searchCommerceOrders(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.searchCommerceOrders(searchContext);
	}

	@Override
	public long searchCommerceOrdersCount(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.searchCommerceOrdersCount(searchContext);
	}

	@Override
	public String startCommerceOrderPayment(long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.startCommerceOrderPayment(commerceOrderId,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder submitCommerceOrder(
		long userId, long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.submitCommerceOrder(userId,
			commerceOrderId);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateBillingAddress(
		long commerceOrderId, String name, String description, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateBillingAddress(commerceOrderId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, serviceContext);
	}

	/**
	* Updates the commerce order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
		com.liferay.commerce.model.CommerceOrder commerceOrder) {
		return _commerceOrderLocalService.updateCommerceOrder(commerceOrder);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
		long commerceOrderId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		String shippingOptionName, String purchaseOrderNumber,
		java.math.BigDecimal subtotal, java.math.BigDecimal shippingPrice,
		java.math.BigDecimal total, String advanceStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateCommerceOrder(commerceOrderId,
			billingAddressId, shippingAddressId, commercePaymentMethodId,
			commerceShippingMethodId, shippingOptionName, purchaseOrderNumber,
			subtotal, shippingPrice, total, advanceStatus);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateOrderStatus(
		long commerceOrderId, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateOrderStatus(commerceOrderId,
			orderStatus);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updatePaymentStatus(
		long commerceOrderId, int paymentStatus,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updatePaymentStatus(commerceOrderId,
			paymentStatus, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updatePurchaseOrderNumber(
		long commerceOrderId, String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updatePurchaseOrderNumber(commerceOrderId,
			purchaseOrderNumber);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateShippingAddress(
		long commerceOrderId, String name, String description, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateShippingAddress(commerceOrderId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateStatus(long userId,
		long commerceOrderId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateStatus(userId, commerceOrderId,
			status, serviceContext, workflowContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceOrder updateUser(
		long commerceOrderId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceOrderLocalService.updateUser(commerceOrderId, userId);
	}

	@Override
	public CommerceOrderLocalService getWrappedService() {
		return _commerceOrderLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceOrderLocalService commerceOrderLocalService) {
		_commerceOrderLocalService = commerceOrderLocalService;
	}

	private CommerceOrderLocalService _commerceOrderLocalService;
}