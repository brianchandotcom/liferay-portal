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
 * Provides a wrapper for {@link CCartLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CCartLocalService
 * @generated
 */
@ProviderType
public class CCartLocalServiceWrapper implements CCartLocalService,
	ServiceWrapper<CCartLocalService> {
	public CCartLocalServiceWrapper(CCartLocalService cCartLocalService) {
		_cCartLocalService = cCartLocalService;
	}

	/**
	* Adds the c cart to the database. Also notifies the appropriate model listeners.
	*
	* @param cCart the c cart
	* @return the c cart that was added
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart addCCart(
		com.liferay.commerce.cart.model.CCart cCart) {
		return _cCartLocalService.addCCart(cCart);
	}

	@Override
	public com.liferay.commerce.cart.model.CCart addCCart(long cartUserId,
		java.lang.String title, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.addCCart(cartUserId, title, type,
			serviceContext);
	}

	/**
	* Creates a new c cart with the primary key. Does not add the c cart to the database.
	*
	* @param CCartId the primary key for the new c cart
	* @return the new c cart
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart createCCart(long CCartId) {
		return _cCartLocalService.createCCart(CCartId);
	}

	/**
	* Deletes the c cart from the database. Also notifies the appropriate model listeners.
	*
	* @param cCart the c cart
	* @return the c cart that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart deleteCCart(
		com.liferay.commerce.cart.model.CCart cCart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.deleteCCart(cCart);
	}

	/**
	* Deletes the c cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart that was removed
	* @throws PortalException if a c cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart deleteCCart(long CCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.deleteCCart(CCartId);
	}

	@Override
	public com.liferay.commerce.cart.model.CCart fetchCCart(long CCartId) {
		return _cCartLocalService.fetchCCart(CCartId);
	}

	/**
	* Returns the c cart matching the UUID and group.
	*
	* @param uuid the c cart's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart fetchCCartByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cCartLocalService.fetchCCartByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the c cart with the primary key.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart
	* @throws PortalException if a c cart with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart getCCart(long CCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.getCCart(CCartId);
	}

	/**
	* Returns the c cart matching the UUID and group.
	*
	* @param uuid the c cart's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart
	* @throws PortalException if a matching c cart could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart getCCartByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.getCCartByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the c cart in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cCart the c cart
	* @return the c cart that was updated
	*/
	@Override
	public com.liferay.commerce.cart.model.CCart updateCCart(
		com.liferay.commerce.cart.model.CCart cCart) {
		return _cCartLocalService.updateCCart(cCart);
	}

	@Override
	public com.liferay.commerce.cart.model.CCart updateCCart(long cCartId,
		long cartUserId, java.lang.String title, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.updateCCart(cCartId, cartUserId, title, type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cCartLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cCartLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cCartLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cCartLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of c carts.
	*
	* @return the number of c carts
	*/
	@Override
	public int getCCartsCount() {
		return _cCartLocalService.getCCartsCount();
	}

	@Override
	public int getCCartsCount(int type) {
		return _cCartLocalService.getCCartsCount(type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cCartLocalService.getOSGiServiceIdentifier();
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
		return _cCartLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cCartLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cCartLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of c carts
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCart> getCCarts(
		int start, int end) {
		return _cCartLocalService.getCCarts(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCart> getCCarts(
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CCart> orderByComparator) {
		return _cCartLocalService.getCCarts(type, start, end, orderByComparator);
	}

	/**
	* Returns all the c carts matching the UUID and company.
	*
	* @param uuid the UUID of the c carts
	* @param companyId the primary key of the company
	* @return the matching c carts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCart> getCCartsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cCartLocalService.getCCartsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of c carts matching the UUID and company.
	*
	* @param uuid the UUID of the c carts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching c carts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCart> getCCartsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CCart> orderByComparator) {
		return _cCartLocalService.getCCartsByUuidAndCompanyId(uuid, companyId,
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
		return _cCartLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cCartLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public CCartLocalService getWrappedService() {
		return _cCartLocalService;
	}

	@Override
	public void setWrappedService(CCartLocalService cCartLocalService) {
		_cCartLocalService = cCartLocalService;
	}

	private CCartLocalService _cCartLocalService;
}