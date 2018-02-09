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
 * Provides a wrapper for {@link CommerceCartLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartLocalService
 * @generated
 */
@ProviderType
public class CommerceCartLocalServiceWrapper implements CommerceCartLocalService,
	ServiceWrapper<CommerceCartLocalService> {
	public CommerceCartLocalServiceWrapper(
		CommerceCartLocalService commerceCartLocalService) {
		_commerceCartLocalService = commerceCartLocalService;
	}

	/**
	* Adds the commerce cart to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCart the commerce cart
	* @return the commerce cart that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart addCommerceCart(
		com.liferay.commerce.model.CommerceCart commerceCart) {
		return _commerceCartLocalService.addCommerceCart(commerceCart);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart addCommerceCart(
		java.lang.String name, boolean defaultCart,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.addCommerceCart(name, defaultCart,
			serviceContext);
	}

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param commerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart createCommerceCart(
		long commerceCartId) {
		return _commerceCartLocalService.createCommerceCart(commerceCartId);
	}

	/**
	* Deletes the commerce cart from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCart the commerce cart
	* @return the commerce cart that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart deleteCommerceCart(
		com.liferay.commerce.model.CommerceCart commerceCart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deleteCommerceCart(commerceCart);
	}

	/**
	* Deletes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws PortalException if a commerce cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart deleteCommerceCart(
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deleteCommerceCart(commerceCartId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCartLocalService.dynamicQuery();
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
		return _commerceCartLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCartLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceCartLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _commerceCartLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceCartLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart fetchCommerceCart(
		long commerceCartId) {
		return _commerceCartLocalService.fetchCommerceCart(commerceCartId);
	}

	/**
	* Returns the commerce cart matching the UUID and group.
	*
	* @param uuid the commerce cart's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart fetchCommerceCartByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceCartLocalService.fetchCommerceCartByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart fetchDefaultCommerceCart(
		long groupId, long userId, boolean defaultCart) {
		return _commerceCartLocalService.fetchDefaultCommerceCart(groupId,
			userId, defaultCart);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceCartLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the commerce cart with the primary key.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws PortalException if a commerce cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart getCommerceCart(
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getCommerceCart(commerceCartId);
	}

	/**
	* Returns the commerce cart matching the UUID and group.
	*
	* @param uuid the commerce cart's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce cart
	* @throws PortalException if a matching commerce cart could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart getCommerceCartByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getCommerceCartByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCarts(
		int start, int end) {
		return _commerceCartLocalService.getCommerceCarts(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCarts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCart> orderByComparator) {
		return _commerceCartLocalService.getCommerceCarts(groupId, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCartsByBillingAddress(
		long billingAddressId) {
		return _commerceCartLocalService.getCommerceCartsByBillingAddress(billingAddressId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCartsByShippingAddress(
		long shippingAddressId) {
		return _commerceCartLocalService.getCommerceCartsByShippingAddress(shippingAddressId);
	}

	/**
	* Returns all the commerce carts matching the UUID and company.
	*
	* @param uuid the UUID of the commerce carts
	* @param companyId the primary key of the company
	* @return the matching commerce carts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCartsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceCartLocalService.getCommerceCartsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce carts matching the UUID and company.
	*
	* @param uuid the UUID of the commerce carts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce carts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCart> getCommerceCartsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCart> orderByComparator) {
		return _commerceCartLocalService.getCommerceCartsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce carts.
	*
	* @return the number of commerce carts
	*/
	@Override
	public int getCommerceCartsCount() {
		return _commerceCartLocalService.getCommerceCartsCount();
	}

	@Override
	public int getCommerceCartsCount(long groupId) {
		return _commerceCartLocalService.getCommerceCartsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceCartLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceCartLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCartLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void mergeGuestCommerceCart(long guestCommerceCartId,
		long userCommerceCartId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartLocalService.mergeGuestCommerceCart(guestCommerceCartId,
			userCommerceCartId, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart resetCommerceCartShipping(
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.resetCommerceCartShipping(commerceCartId);
	}

	/**
	* Updates the commerce cart in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCart the commerce cart
	* @return the commerce cart that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceCart updateCommerceCart(
		com.liferay.commerce.model.CommerceCart commerceCart) {
		return _commerceCartLocalService.updateCommerceCart(commerceCart);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateCommerceCart(
		long commerceCartId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName, double shippingPrice)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.updateCommerceCart(commerceCartId,
			billingAddressId, shippingAddressId, commercePaymentMethodId,
			commerceShippingMethodId, shippingOptionName, shippingPrice);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateCommerceCart(
		long commerceCartId, java.lang.String name, boolean defaultCart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.updateCommerceCart(commerceCartId,
			name, defaultCart);
	}

	@Override
	public com.liferay.commerce.model.CommerceCart updateUser(
		long commerceCartId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.updateUser(commerceCartId, userId);
	}

	@Override
	public CommerceCartLocalService getWrappedService() {
		return _commerceCartLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceCartLocalService commerceCartLocalService) {
		_commerceCartLocalService = commerceCartLocalService;
	}

	private CommerceCartLocalService _commerceCartLocalService;
}