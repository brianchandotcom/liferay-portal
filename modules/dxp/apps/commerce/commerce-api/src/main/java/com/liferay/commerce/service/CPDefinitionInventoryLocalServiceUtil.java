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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPDefinitionInventory. This utility wraps
 * {@link com.liferay.commerce.service.impl.CPDefinitionInventoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventoryLocalService
 * @see com.liferay.commerce.service.base.CPDefinitionInventoryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDefinitionInventoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDefinitionInventoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp definition inventory to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was added
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory addCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return getService().addCPDefinitionInventory(cpDefinitionInventory);
	}

	public static com.liferay.commerce.model.CPDefinitionInventory addCPDefinitionInventory(
		long cpDefinitionId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionInventory(cpDefinitionId,
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
	public static com.liferay.commerce.model.CPDefinitionInventory createCPDefinitionInventory(
		long CPDefinitionInventoryId) {
		return getService().createCPDefinitionInventory(CPDefinitionInventoryId);
	}

	/**
	* Deletes the cp definition inventory from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was removed
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory deleteCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return getService().deleteCPDefinitionInventory(cpDefinitionInventory);
	}

	/**
	* Deletes the cp definition inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory that was removed
	* @throws PortalException if a cp definition inventory with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory deleteCPDefinitionInventory(
		long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionInventory(CPDefinitionInventoryId);
	}

	public static void deleteCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId) {
		getService().deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventory(
		long CPDefinitionInventoryId) {
		return getService().fetchCPDefinitionInventory(CPDefinitionInventoryId);
	}

	public static com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the cp definition inventory matching the UUID and group.
	*
	* @param uuid the cp definition inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition inventory, or <code>null</code> if a matching cp definition inventory could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory fetchCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId) {
		return getService()
				   .fetchCPDefinitionInventoryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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
	public static java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventories(
		int start, int end) {
		return getService().getCPDefinitionInventories(start, end);
	}

	/**
	* Returns all the cp definition inventories matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition inventories
	* @param companyId the primary key of the company
	* @return the matching cp definition inventories, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventoriesByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCPDefinitionInventoriesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.commerce.model.CPDefinitionInventory> getCPDefinitionInventoriesByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CPDefinitionInventory> orderByComparator) {
		return getService()
				   .getCPDefinitionInventoriesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of cp definition inventories.
	*
	* @return the number of cp definition inventories
	*/
	public static int getCPDefinitionInventoriesCount() {
		return getService().getCPDefinitionInventoriesCount();
	}

	/**
	* Returns the cp definition inventory with the primary key.
	*
	* @param CPDefinitionInventoryId the primary key of the cp definition inventory
	* @return the cp definition inventory
	* @throws PortalException if a cp definition inventory with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory getCPDefinitionInventory(
		long CPDefinitionInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionInventory(CPDefinitionInventoryId);
	}

	/**
	* Returns the cp definition inventory matching the UUID and group.
	*
	* @param uuid the cp definition inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition inventory
	* @throws PortalException if a matching cp definition inventory could not be found
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory getCPDefinitionInventoryByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionInventoryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the cp definition inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionInventory the cp definition inventory
	* @return the cp definition inventory that was updated
	*/
	public static com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		com.liferay.commerce.model.CPDefinitionInventory cpDefinitionInventory) {
		return getService().updateCPDefinitionInventory(cpDefinitionInventory);
	}

	public static com.liferay.commerce.model.CPDefinitionInventory updateCPDefinitionInventory(
		long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionInventory(cpDefinitionInventoryId,
			cpDefinitionInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minOrderQuantity, maxOrderQuantity, allowedOrderQuantities,
			multipleOrderQuantity, serviceContext);
	}

	public static CPDefinitionInventoryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionInventoryLocalService, CPDefinitionInventoryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionInventoryLocalService.class);

		ServiceTracker<CPDefinitionInventoryLocalService, CPDefinitionInventoryLocalService> serviceTracker =
			new ServiceTracker<CPDefinitionInventoryLocalService, CPDefinitionInventoryLocalService>(bundle.getBundleContext(),
				CPDefinitionInventoryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}