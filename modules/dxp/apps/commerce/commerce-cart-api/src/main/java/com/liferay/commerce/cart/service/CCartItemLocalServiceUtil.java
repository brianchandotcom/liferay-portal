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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CCartItem. This utility wraps
 * {@link com.liferay.commerce.cart.service.impl.CCartItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CCartItemLocalService
 * @see com.liferay.commerce.cart.service.base.CCartItemLocalServiceBaseImpl
 * @see com.liferay.commerce.cart.service.impl.CCartItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CCartItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.cart.service.impl.CCartItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the c cart item to the database. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was added
	*/
	public static com.liferay.commerce.cart.model.CCartItem addCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem) {
		return getService().addCCartItem(cCartItem);
	}

	/**
	* Creates a new c cart item with the primary key. Does not add the c cart item to the database.
	*
	* @param CCartItemId the primary key for the new c cart item
	* @return the new c cart item
	*/
	public static com.liferay.commerce.cart.model.CCartItem createCCartItem(
		long CCartItemId) {
		return getService().createCCartItem(CCartItemId);
	}

	/**
	* Deletes the c cart item from the database. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was removed
	*/
	public static com.liferay.commerce.cart.model.CCartItem deleteCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem) {
		return getService().deleteCCartItem(cCartItem);
	}

	/**
	* Deletes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item that was removed
	* @throws PortalException if a c cart item with the primary key could not be found
	*/
	public static com.liferay.commerce.cart.model.CCartItem deleteCCartItem(
		long CCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCCartItem(CCartItemId);
	}

	public static com.liferay.commerce.cart.model.CCartItem fetchCCartItem(
		long CCartItemId) {
		return getService().fetchCCartItem(CCartItemId);
	}

	/**
	* Returns the c cart item matching the UUID and group.
	*
	* @param uuid the c cart item's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public static com.liferay.commerce.cart.model.CCartItem fetchCCartItemByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchCCartItemByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the c cart item with the primary key.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item
	* @throws PortalException if a c cart item with the primary key could not be found
	*/
	public static com.liferay.commerce.cart.model.CCartItem getCCartItem(
		long CCartItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCCartItem(CCartItemId);
	}

	/**
	* Returns the c cart item matching the UUID and group.
	*
	* @param uuid the c cart item's UUID
	* @param groupId the primary key of the group
	* @return the matching c cart item
	* @throws PortalException if a matching c cart item could not be found
	*/
	public static com.liferay.commerce.cart.model.CCartItem getCCartItemByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCCartItemByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the c cart item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cCartItem the c cart item
	* @return the c cart item that was updated
	*/
	public static com.liferay.commerce.cart.model.CCartItem updateCCartItem(
		com.liferay.commerce.cart.model.CCartItem cCartItem) {
		return getService().updateCCartItem(cCartItem);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of c cart items.
	*
	* @return the number of c cart items
	*/
	public static int getCCartItemsCount() {
		return getService().getCCartItemsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cart.model.impl.CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public static java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItems(
		int start, int end) {
		return getService().getCCartItems(start, end);
	}

	/**
	* Returns all the c cart items matching the UUID and company.
	*
	* @param uuid the UUID of the c cart items
	* @param companyId the primary key of the company
	* @return the matching c cart items, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItemsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getCCartItemsByUuidAndCompanyId(uuid, companyId);
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
	public static java.util.List<com.liferay.commerce.cart.model.CCartItem> getCCartItemsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CCartItem> orderByComparator) {
		return getService()
				   .getCCartItemsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
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

	public static CCartItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CCartItemLocalService, CCartItemLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CCartItemLocalService.class);
}