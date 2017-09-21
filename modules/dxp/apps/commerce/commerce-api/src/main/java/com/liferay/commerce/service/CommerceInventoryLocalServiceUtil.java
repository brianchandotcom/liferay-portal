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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceInventory. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceInventoryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryLocalService
 * @see com.liferay.commerce.service.base.CommerceInventoryLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceInventoryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceInventoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceInventoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce inventory to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was added
	*/
	public static com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return getService().addCommerceInventory(commerceInventory);
	}

	public static com.liferay.commerce.model.CommerceInventory addCommerceInventory(
		long cpDefinitionId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceInventory(cpDefinitionId,
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
	public static com.liferay.commerce.model.CommerceInventory createCommerceInventory(
		long commerceInventoryId) {
		return getService().createCommerceInventory(commerceInventoryId);
	}

	/**
	* Deletes the commerce inventory from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was removed
	*/
	public static com.liferay.commerce.model.CommerceInventory deleteCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return getService().deleteCommerceInventory(commerceInventory);
	}

	/**
	* Deletes the commerce inventory with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory that was removed
	* @throws PortalException if a commerce inventory with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceInventory deleteCommerceInventory(
		long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceInventory(commerceInventoryId);
	}

	public static void deleteCommerceInventoryByCPDefinitionId(
		long cpDefinitionId) {
		getService().deleteCommerceInventoryByCPDefinitionId(cpDefinitionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceInventoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventory(
		long commerceInventoryId) {
		return getService().fetchCommerceInventory(commerceInventoryId);
	}

	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByCPDefinitionId(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the commerce inventory matching the UUID and group.
	*
	* @param uuid the commerce inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce inventory, or <code>null</code> if a matching commerce inventory could not be found
	*/
	public static com.liferay.commerce.model.CommerceInventory fetchCommerceInventoryByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCommerceInventoryByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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
	public static java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventories(
		int start, int end) {
		return getService().getCommerceInventories(start, end);
	}

	/**
	* Returns all the commerce inventories matching the UUID and company.
	*
	* @param uuid the UUID of the commerce inventories
	* @param companyId the primary key of the company
	* @return the matching commerce inventories, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getCommerceInventoriesByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.commerce.model.CommerceInventory> getCommerceInventoriesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceInventory> orderByComparator) {
		return getService()
				   .getCommerceInventoriesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce inventories.
	*
	* @return the number of commerce inventories
	*/
	public static int getCommerceInventoriesCount() {
		return getService().getCommerceInventoriesCount();
	}

	/**
	* Returns the commerce inventory with the primary key.
	*
	* @param commerceInventoryId the primary key of the commerce inventory
	* @return the commerce inventory
	* @throws PortalException if a commerce inventory with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceInventory getCommerceInventory(
		long commerceInventoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceInventory(commerceInventoryId);
	}

	/**
	* Returns the commerce inventory matching the UUID and group.
	*
	* @param uuid the commerce inventory's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce inventory
	* @throws PortalException if a matching commerce inventory could not be found
	*/
	public static com.liferay.commerce.model.CommerceInventory getCommerceInventoryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceInventoryByUuidAndGroupId(uuid, groupId);
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
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce inventory in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceInventory the commerce inventory
	* @return the commerce inventory that was updated
	*/
	public static com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		com.liferay.commerce.model.CommerceInventory commerceInventory) {
		return getService().updateCommerceInventory(commerceInventory);
	}

	public static com.liferay.commerce.model.CommerceInventory updateCommerceInventory(
		long commerceInventoryId, java.lang.String commerceInventoryEngine,
		java.lang.String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceInventory(commerceInventoryId,
			commerceInventoryEngine, lowStockActivity, displayAvailability,
			displayStockQuantity, minStockQuantity, backOrders,
			minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	public static CommerceInventoryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceInventoryLocalService, CommerceInventoryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceInventoryLocalService.class);
}