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

package com.liferay.commerce.cart.service;

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
	public com.liferay.commerce.cart.model.CommerceCart addCommerceCart(
		com.liferay.commerce.cart.model.CommerceCart commerceCart) {
		return _commerceCartLocalService.addCommerceCart(commerceCart);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart addCommerceCart(
		long cartUserId, java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.addCommerceCart(cartUserId, name,
			type, serviceContext);
	}

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param CommerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart createCommerceCart(
		long CommerceCartId) {
		return _commerceCartLocalService.createCommerceCart(CommerceCartId);
	}

	/**
	* Deletes the commerce cart from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCart the commerce cart
	* @return the commerce cart that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart deleteCommerceCart(
		com.liferay.commerce.cart.model.CommerceCart commerceCart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deleteCommerceCart(commerceCart);
	}

	/**
	* Deletes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CommerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws PortalException if a commerce cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart deleteCommerceCart(
		long CommerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deleteCommerceCart(CommerceCartId);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		long CommerceCartId) {
		return _commerceCartLocalService.fetchCommerceCart(CommerceCartId);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		long groupId, long userId, int type) {
		return _commerceCartLocalService.fetchCommerceCart(groupId, userId, type);
	}

	/**
	* Returns the commerce cart matching the UUID and group.
	*
	* @param uuid the commerce cart's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchCommerceCartByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceCartLocalService.fetchCommerceCartByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the commerce cart with the primary key.
	*
	* @param CommerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws PortalException if a commerce cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart getCommerceCart(
		long CommerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getCommerceCart(CommerceCartId);
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
	public com.liferay.commerce.cart.model.CommerceCart getCommerceCartByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getCommerceCartByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart getUserCurrentCommerceCart(
		int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getUserCurrentCommerceCart(type,
			serviceContext);
	}

	/**
	* Updates the commerce cart in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCart the commerce cart
	* @return the commerce cart that was updated
	*/
	@Override
	public com.liferay.commerce.cart.model.CommerceCart updateCommerceCart(
		com.liferay.commerce.cart.model.CommerceCart commerceCart) {
		return _commerceCartLocalService.updateCommerceCart(commerceCart);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart updateCommerceCart(
		long commerceCartId, long cartUserId, java.lang.String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.updateCommerceCart(commerceCartId,
			cartUserId, name, type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceCartLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceCartLocalService.dynamicQuery();
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
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartLocalService.getPersistedModel(primaryKeyObj);
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
	public int getCommerceCartsCount(int type) {
		return _commerceCartLocalService.getCommerceCartsCount(type);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCarts(
		int start, int end) {
		return _commerceCartLocalService.getCommerceCarts(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCarts(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCart> orderByComparator) {
		return _commerceCartLocalService.getCommerceCarts(type, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce carts matching the UUID and company.
	*
	* @param uuid the UUID of the commerce carts
	* @param companyId the primary key of the company
	* @return the matching commerce carts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCartsByUuidAndCompanyId(
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
	public java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCartsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCart> orderByComparator) {
		return _commerceCartLocalService.getCommerceCartsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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