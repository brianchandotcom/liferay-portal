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
 * Provides a wrapper for {@link CommerceInventoryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryLocalService
 * @generated
 */
@ProviderType
public class CommerceInventoryLocalServiceWrapper
	implements CommerceInventoryLocalService,
		ServiceWrapper<CommerceInventoryLocalService> {
	public CommerceInventoryLocalServiceWrapper(
		CommerceInventoryLocalService commerceInventoryLocalService) {
		_commerceInventoryLocalService = commerceInventoryLocalService;
	}

	/**
	* Adds the commerce inventory to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was added
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return _commerceInventoryLocalService.addCommerceInventory(commerceInventory);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		long cpDefinitionId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.addCommerceInventory(cpDefinitionId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	/**
	* Creates a new commerce inventory with the primary key. Does not add the commerce inventory to the database.
	*
	* @param commerceInventoryId the primary key for the new commerce inventory
	* @return the new commerce inventory
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory createCommerceInventory(
		long commerceInventoryId) {
		return _commerceInventoryLocalService.createCommerceInventory(commerceInventoryId);
	}

	/**
	* Deletes the commerce inventory from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was removed
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory deleteCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return _commerceInventoryLocalService.deleteCommerceInventory(commerceInventory);
	}

	/**
	* Deletes the commerce inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory that was removed
	* @throws PortalException if a commerce inventory with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory deleteCommerceInventory(
		long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.deleteCommerceInventory(commerceInventoryId);
	}

	@Override
	public void deleteCommerceInventoryByCPDefinitionId(long cpDefinitionId) {
		_commerceInventoryLocalService.deleteCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _commerceInventoryLocalService.dynamicQuery();
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
		return _commerceInventoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceInventoryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _commerceInventoryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _commerceInventoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _commerceInventoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory fetchCommerceInventory(
		long commerceInventoryId) {
		return _commerceInventoryLocalService.fetchCommerceInventory(commerceInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the commerce inventory matching the UUID and group.
	*
	* @param uuid the commerce inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _commerceInventoryLocalService.fetchCommerceInventoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _commerceInventoryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @return the range of commerce inventories
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventories(
		int start, int end) {
		return _commerceInventoryLocalService.getCommerceInventories(start, end);
	}

	/**
	* Returns all the commerce inventories matching the UUID and company.
	*
	* @param uuid the UUID of the commerce inventories
	* @param companyId the primary key of the company
	* @return the matching commerce inventories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _commerceInventoryLocalService.getCommerceInventoriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of commerce inventories matching the UUID and company.
	*
	* @param uuid the UUID of the commerce inventories
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce inventories
	* @param end the upper bound of the range of commerce inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce inventories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceInventory> orderByComparator) {
		return _commerceInventoryLocalService.getCommerceInventoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce inventories.
	*
	* @return the number of commerce inventories
	*/
	@Override
	public int getCommerceInventoriesCount() {
		return _commerceInventoryLocalService.getCommerceInventoriesCount();
	}

	/**
	* Returns the commerce inventory with the primary key.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory
	* @throws PortalException if a commerce inventory with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory getCommerceInventory(
		long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.getCommerceInventory(commerceInventoryId);
	}

	/**
	* Returns the commerce inventory matching the UUID and group.
	*
	* @param uuid the commerce inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce inventory
	* @throws PortalException if a matching commerce inventory could not be found
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory getCommerceInventoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.getCommerceInventoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _commerceInventoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _commerceInventoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceInventoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was updated
	*/
	@Override
	public com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return _commerceInventoryLocalService.updateCommerceInventory(commerceInventory);
	}

	@Override
	public com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		long commerceInventoryId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceInventoryLocalService.updateCommerceInventory(commerceInventoryId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	@Override
	public CommerceInventoryLocalService getWrappedService() {
		return _commerceInventoryLocalService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryLocalService commerceInventoryLocalService) {
		_commerceInventoryLocalService = commerceInventoryLocalService;
	}

	private CommerceInventoryLocalService _commerceInventoryLocalService;
}