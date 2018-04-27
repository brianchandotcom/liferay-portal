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
 * Provides a wrapper for {@link CPDefinitionInventoryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryLocalService
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryLocalServiceWrapper
	implements CPDefinitionInventoryLocalService,
		ServiceWrapper<CPDefinitionInventoryLocalService> {
	public CPDefinitionInventoryLocalServiceWrapper(
		CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService) {
		_cpDefinitionInventoryLocalService = cpDefinitionInventoryLocalService;
	}

	/**
	* Adds the cp definition inventory to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was added
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory addCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return _cpDefinitionInventoryLocalService.addCPDefinitionInventory(cpDefinitionInventory);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory addCPDefinitionInventory(
		long cpDefinitionId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.addCPDefinitionInventory(cpDefinitionId,
			cpDefinitionInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minOrderQuantity, maxOrderQuantity, allowedOrderQuantities,
			multipleOrderQuantity, serviceContext);
	}

	/**
	* Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	*
	* @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	* @return the new cp definition inventory
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory createCPDefinitionInventory(
		long CPDefinitionInventoryId) {
		return _cpDefinitionInventoryLocalService.createCPDefinitionInventory(CPDefinitionInventoryId);
	}

	/**
	* Deletes the cp definition inventory from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was removed
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory deleteCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return _cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(cpDefinitionInventory);
	}

	/**
	* Deletes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory that was removed
	* @throws PortalException if a cp definition inventory with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory deleteCPDefinitionInventory(
		long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(CPDefinitionInventoryId);
	}

	@Override
	public void deleteCPDefinitionInventoryByCPDefinitionId(long cpDefinitionId) {
		_cpDefinitionInventoryLocalService.deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cpDefinitionInventoryLocalService.dynamicQuery();
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
		return _cpDefinitionInventoryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionInventoryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _cpDefinitionInventoryLocalService.dynamicQuery(dynamicQuery,
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
		return _cpDefinitionInventoryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _cpDefinitionInventoryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventory(
		long CPDefinitionInventoryId) {
		return _cpDefinitionInventoryLocalService.fetchCPDefinitionInventory(CPDefinitionInventoryId);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the cp definition inventory matching the UUID and group.
	*
	* @param uuid the cp definition inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId) {
		return _cpDefinitionInventoryLocalService.fetchCPDefinitionInventoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cpDefinitionInventoryLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the cp definition inventories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @return the range of cp definition inventories
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventories(
		int start, int end) {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventories(start,
			end);
	}

	/**
	* Returns all the cp definition inventories matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition inventories
	* @param companyId the primary key of the company
	* @return the matching cp definition inventories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventoriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventoriesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of cp definition inventories matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition inventories
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition inventories
	* @param end the upper bound of the range of cp definition inventories (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition inventories, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventoriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CPDefinitionInventory> orderByComparator) {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition inventories.
	*
	* @return the number of cp definition inventories
	*/
	@Override
	public int getCPDefinitionInventoriesCount() {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventoriesCount();
	}

	/**
	* Returns the cp definition inventory with the primary key.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory
	* @throws PortalException if a cp definition inventory with the primary key could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory getCPDefinitionInventory(
		long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventory(CPDefinitionInventoryId);
	}

	/**
	* Returns the cp definition inventory matching the UUID and group.
	*
	* @param uuid the cp definition inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition inventory
	* @throws PortalException if a matching cp definition inventory could not be found
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory getCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.getCPDefinitionInventoryByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _cpDefinitionInventoryLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cpDefinitionInventoryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionInventoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp definition inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was updated
	*/
	@Override
	public com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(cpDefinitionInventory);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(cpDefinitionInventoryId,
			cpDefinitionInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minOrderQuantity, maxOrderQuantity, allowedOrderQuantities,
			multipleOrderQuantity, serviceContext);
	}

	@Override
	public CPDefinitionInventoryLocalService getWrappedService() {
		return _cpDefinitionInventoryLocalService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService) {
		_cpDefinitionInventoryLocalService = cpDefinitionInventoryLocalService;
	}

	private CPDefinitionInventoryLocalService _cpDefinitionInventoryLocalService;
}