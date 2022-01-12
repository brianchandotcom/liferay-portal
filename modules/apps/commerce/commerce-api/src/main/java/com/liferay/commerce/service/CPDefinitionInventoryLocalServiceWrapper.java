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

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

/**
 * Provides a wrapper for {@link CPDefinitionInventoryLocalService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryLocalService
 * @generated
 */
public class CPDefinitionInventoryLocalServiceWrapper
	implements CPDefinitionInventoryLocalService,
			   ServiceWrapper<CPDefinitionInventoryLocalService> {

	public CPDefinitionInventoryLocalServiceWrapper() {
		this(null);
	}

	public CPDefinitionInventoryLocalServiceWrapper(
		CPDefinitionInventoryLocalService cpDefinitionInventoryLocalService) {

		_cpDefinitionInventoryLocalService = cpDefinitionInventoryLocalService;
	}

	/**
	 * Adds the cp definition inventory to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was added
	 */
	@Override
	public CPDefinitionInventory addCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		return _cpDefinitionInventoryLocalService.addCPDefinitionInventory(
			cpDefinitionInventory);
	}

	@Override
	public CPDefinitionInventory addCPDefinitionInventory(
			long userId, long cpDefinitionId,
			String cpDefinitionInventoryEngine, String lowStockActivity,
			boolean displayAvailability, boolean displayStockQuantity,
			int minStockQuantity, boolean backOrders, int minOrderQuantity,
			int maxOrderQuantity, String allowedOrderQuantities,
			int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.addCPDefinitionInventory(
			userId, cpDefinitionId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	@Override
	public void cloneCPDefinitionInventory(
		long cpDefinitionId, long newCPDefinitionId) {

		_cpDefinitionInventoryLocalService.cloneCPDefinitionInventory(
			cpDefinitionId, newCPDefinitionId);
	}

	/**
	 * Creates a new cp definition inventory with the primary key. Does not add the cp definition inventory to the database.
	 *
	 * @param CPDefinitionInventoryId the primary key for the new cp definition inventory
	 * @return the new cp definition inventory
	 */
	@Override
	public CPDefinitionInventory createCPDefinitionInventory(
		long CPDefinitionInventoryId) {

		return _cpDefinitionInventoryLocalService.createCPDefinitionInventory(
			CPDefinitionInventoryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the cp definition inventory from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was removed
	 */
	@Override
	public CPDefinitionInventory deleteCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		return _cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(
			cpDefinitionInventory);
	}

	/**
	 * Deletes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory that was removed
	 * @throws PortalException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory deleteCPDefinitionInventory(
			long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(
			CPDefinitionInventoryId);
	}

	@Override
	public void deleteCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId) {

		_cpDefinitionInventoryLocalService.
			deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _cpDefinitionInventoryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _cpDefinitionInventoryLocalService.dslQueryCount(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
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

		return _cpDefinitionInventoryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
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

		return _cpDefinitionInventoryLocalService.dynamicQuery(
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

		return _cpDefinitionInventoryLocalService.dynamicQueryCount(
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

		return _cpDefinitionInventoryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventory(
		long CPDefinitionInventoryId) {

		return _cpDefinitionInventoryLocalService.fetchCPDefinitionInventory(
			CPDefinitionInventoryId);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId) {

		return _cpDefinitionInventoryLocalService.
			fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the cp definition inventory matching the UUID and group.
	 *
	 * @param uuid the cp definition inventory's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	 */
	@Override
	public CPDefinitionInventory fetchCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId) {

		return _cpDefinitionInventoryLocalService.
			fetchCPDefinitionInventoryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _cpDefinitionInventoryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the cp definition inventories.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CPDefinitionInventoryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition inventories
	 * @param end the upper bound of the range of cp definition inventories (not inclusive)
	 * @return the range of cp definition inventories
	 */
	@Override
	public java.util.List<CPDefinitionInventory> getCPDefinitionInventories(
		int start, int end) {

		return _cpDefinitionInventoryLocalService.getCPDefinitionInventories(
			start, end);
	}

	/**
	 * Returns all the cp definition inventories matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp definition inventories
	 * @param companyId the primary key of the company
	 * @return the matching cp definition inventories, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<CPDefinitionInventory>
		getCPDefinitionInventoriesByUuidAndCompanyId(
			String uuid, long companyId) {

		return _cpDefinitionInventoryLocalService.
			getCPDefinitionInventoriesByUuidAndCompanyId(uuid, companyId);
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
	public java.util.List<CPDefinitionInventory>
		getCPDefinitionInventoriesByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionInventory> orderByComparator) {

		return _cpDefinitionInventoryLocalService.
			getCPDefinitionInventoriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of cp definition inventories.
	 *
	 * @return the number of cp definition inventories
	 */
	@Override
	public int getCPDefinitionInventoriesCount() {
		return _cpDefinitionInventoryLocalService.
			getCPDefinitionInventoriesCount();
	}

	/**
	 * Returns the cp definition inventory with the primary key.
	 *
	 * @param CPDefinitionInventoryId the primary key of the cp definition inventory
	 * @return the cp definition inventory
	 * @throws PortalException if a cp definition inventory with the primary key could not be found
	 */
	@Override
	public CPDefinitionInventory getCPDefinitionInventory(
			long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.getCPDefinitionInventory(
			CPDefinitionInventoryId);
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
	public CPDefinitionInventory getCPDefinitionInventoryByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.
			getCPDefinitionInventoryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _cpDefinitionInventoryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _cpDefinitionInventoryLocalService.
			getIndexableActionableDynamicQuery();
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

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the cp definition inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionInventoryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionInventory the cp definition inventory
	 * @return the cp definition inventory that was updated
	 */
	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
		CPDefinitionInventory cpDefinitionInventory) {

		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
			cpDefinitionInventory);
	}

	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minOrderQuantity, int maxOrderQuantity,
			String allowedOrderQuantities, int multipleOrderQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
			cpDefinitionInventoryId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity);
	}

	@Override
	public CTPersistence<CPDefinitionInventory> getCTPersistence() {
		return _cpDefinitionInventoryLocalService.getCTPersistence();
	}

	@Override
	public Class<CPDefinitionInventory> getModelClass() {
		return _cpDefinitionInventoryLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPDefinitionInventory>, R, E>
				updateUnsafeFunction)
		throws E {

		return _cpDefinitionInventoryLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
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

	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}