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
 * Provides a wrapper for {@link CCartItemLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CCartItemLocalService
 * @generated
 */
@ProviderType
public class CCartItemLocalServiceWrapper implements CCartItemLocalService,
	ServiceWrapper<CCartItemLocalService> {
	public CCartItemLocalServiceWrapper(
		CCartItemLocalService cCartItemLocalService) {
		_cCartItemLocalService = cCartItemLocalService;
	}

	/**
	* Adds the c cart item to the database. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was added
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem addCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem) {
		return _cCartItemLocalService.addCCartItem(cCartItem);
	}

	@Override
	public com.liferay.commerce.cart.model.CCartItem addCCartItem(
		long cCartId, long cpDefinitionId, long cpInstanceId, int quantity,
		java.lang.String json, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.addCCartItem(cCartId, cpDefinitionId,
			cpInstanceId, quantity, json, type, serviceContext);
	}

	/**
	* Creates a new c cart item with the primary key. Does not add the c cart item to the database.
	*
	* @param CCartItemId the primary key for the new c cart item
	* @return the new c cart item
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem createCCartItem(
		long CCartItemId) {
		return _cCartItemLocalService.createCCartItem(CCartItemId);
	}

	/**
	* Deletes the c cart item from the database. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem deleteCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.deleteCCartItem(cCartItem);
	}

	/**
	* Deletes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item that was removed
	* @throws PortalException if a c cart item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem deleteCCartItem(
		long CCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.deleteCCartItem(CCartItemId);
	}

	@Override
	public com.liferay.commerce.cart.model.CCartItem fetchCCartItem(
		long CCartItemId) {
		return _cCartItemLocalService.fetchCCartItem(CCartItemId);
	}

	/**
	* Returns the c cart item matching the UUID and group.
	*
	* @param uuid the c cart item's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem fetchCCartItemByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _cCartItemLocalService.fetchCCartItemByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the c cart item with the primary key.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item
	* @throws PortalException if a c cart item with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem getCCartItem(
		long CCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.getCCartItem(CCartItemId);
	}

	/**
	* Returns the c cart item matching the UUID and group.
	*
	* @param uuid the c cart item's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart item
	* @throws PortalException if a matching c cart item could not be found
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem getCCartItemByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.getCCartItemByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the c cart item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was updated
	*/
	@Override
	public com.liferay.commerce.cart.model.CCartItem updateCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem) {
		return _cCartItemLocalService.updateCCartItem(cCartItem);
	}

	@Override
	public com.liferay.commerce.cart.model.CCartItem updateCCartItem(
		long cCartItemId, int quantity, java.lang.String json)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.updateCCartItem(cCartItemId, quantity,
			json);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cCartItemLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cCartItemLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cCartItemLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cCartItemLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cCartItemLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of c cart items.
	*
	* @return the number of c cart items
	*/
	@Override
	public int getCCartItemsCount() {
		return _cCartItemLocalService.getCCartItemsCount();
	}

	@Override
	public int getCCartItemsCount(long cCartId) {
		return _cCartItemLocalService.getCCartItemsCount(cCartId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cCartItemLocalService.getOSGiServiceIdentifier();
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
		return _cCartItemLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cCartItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cCartItemLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of c cart items
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItems(
		int start, int end) {
		return _cCartItemLocalService.getCCartItems(start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItems(
		long cCartId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CCartItem> orderByComparator) {
		return _cCartItemLocalService.getCCartItems(cCartId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the c cart items matching the UUID and company.
	*
	* @param uuid the UUID of the c cart items
	* @param companyId the primary key of the company
	* @return the matching c cart items, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItemsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _cCartItemLocalService.getCCartItemsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of c cart items matching the UUID and company.
	*
	* @param uuid the UUID of the c cart items
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching c cart items, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItemsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CCartItem> orderByComparator) {
		return _cCartItemLocalService.getCCartItemsByUuidAndCompanyId(uuid,
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
		return _cCartItemLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cCartItemLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public CCartItemLocalService getWrappedService() {
		return _cCartItemLocalService;
	}

	@Override
	public void setWrappedService(CCartItemLocalService cCartItemLocalService) {
		_cCartItemLocalService = cCartItemLocalService;
	}

	private CCartItemLocalService _cCartItemLocalService;
}