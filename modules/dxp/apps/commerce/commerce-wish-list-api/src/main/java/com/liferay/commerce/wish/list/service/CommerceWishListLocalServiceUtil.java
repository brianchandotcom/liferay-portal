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

package com.liferay.commerce.wish.list.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceWishList. This utility wraps
 * {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListLocalService
 * @see com.liferay.commerce.wish.list.service.base.CommerceWishListLocalServiceBaseImpl
 * @see com.liferay.commerce.wish.list.service.impl.CommerceWishListLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWishListLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.wish.list.service.impl.CommerceWishListLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce wish list to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was added
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList addCommerceWishList(
		com.liferay.commerce.wish.list.model.CommerceWishList commerceWishList) {
		return getService().addCommerceWishList(commerceWishList);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishList addCommerceWishList(
		String name, boolean defaultWishList,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWishList(name, defaultWishList, serviceContext);
	}

	/**
	* Creates a new commerce wish list with the primary key. Does not add the commerce wish list to the database.
	*
	* @param commerceWishListId the primary key for the new commerce wish list
	* @return the new commerce wish list
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList createCommerceWishList(
		long commerceWishListId) {
		return getService().createCommerceWishList(commerceWishListId);
	}

	/**
	* Deletes the commerce wish list from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was removed
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList deleteCommerceWishList(
		com.liferay.commerce.wish.list.model.CommerceWishList commerceWishList) {
		return getService().deleteCommerceWishList(commerceWishList);
	}

	/**
	* Deletes the commerce wish list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list that was removed
	* @throws PortalException if a commerce wish list with the primary key could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList deleteCommerceWishList(
		long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceWishList(commerceWishListId);
	}

	public static void deleteCommerceWishLists(long userId, java.util.Date date) {
		getService().deleteCommerceWishLists(userId, date);
	}

	public static void deleteCommerceWishListsByGroupId(long groupId) {
		getService().deleteCommerceWishListsByGroupId(groupId);
	}

	public static void deleteCommerceWishListsByUserId(long userId) {
		getService().deleteCommerceWishListsByUserId(userId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.wish.list.model.CommerceWishList fetchCommerceWishList(
		long commerceWishListId) {
		return getService().fetchCommerceWishList(commerceWishListId);
	}

	/**
	* Returns the commerce wish list matching the UUID and group.
	*
	* @param uuid the commerce wish list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList fetchCommerceWishListByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchCommerceWishListByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce wish list with the primary key.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list
	* @throws PortalException if a commerce wish list with the primary key could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList getCommerceWishList(
		long commerceWishListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishList(commerceWishListId);
	}

	/**
	* Returns the commerce wish list matching the UUID and group.
	*
	* @param uuid the commerce wish list's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce wish list
	* @throws PortalException if a matching commerce wish list could not be found
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList getCommerceWishListByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWishListByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the commerce wish lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of commerce wish lists
	*/
	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishLists(
		int start, int end) {
		return getService().getCommerceWishLists(start, end);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishLists(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator) {
		return getService()
				   .getCommerceWishLists(groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishLists(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator) {
		return getService()
				   .getCommerceWishLists(groupId, userId, start, end,
			orderByComparator);
	}

	/**
	* Returns all the commerce wish lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce wish lists
	* @param companyId the primary key of the company
	* @return the matching commerce wish lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishListsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService()
				   .getCommerceWishListsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of commerce wish lists matching the UUID and company.
	*
	* @param uuid the UUID of the commerce wish lists
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce wish lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.commerce.wish.list.model.CommerceWishList> getCommerceWishListsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.wish.list.model.CommerceWishList> orderByComparator) {
		return getService()
				   .getCommerceWishListsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce wish lists.
	*
	* @return the number of commerce wish lists
	*/
	public static int getCommerceWishListsCount() {
		return getService().getCommerceWishListsCount();
	}

	public static int getCommerceWishListsCount(long groupId) {
		return getService().getCommerceWishListsCount(groupId);
	}

	public static int getCommerceWishListsCount(long groupId, long userId) {
		return getService().getCommerceWishListsCount(groupId, userId);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishList getDefaultCommerceWishList(
		long groupId, long userId, String guestUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDefaultCommerceWishList(groupId, userId, guestUuid);
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
	* Updates the commerce wish list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceWishList the commerce wish list
	* @return the commerce wish list that was updated
	*/
	public static com.liferay.commerce.wish.list.model.CommerceWishList updateCommerceWishList(
		com.liferay.commerce.wish.list.model.CommerceWishList commerceWishList) {
		return getService().updateCommerceWishList(commerceWishList);
	}

	public static com.liferay.commerce.wish.list.model.CommerceWishList updateCommerceWishList(
		long commerceWishListId, String name, boolean defaultWishList)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWishList(commerceWishListId, name,
			defaultWishList);
	}

	public static CommerceWishListLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWishListLocalService, CommerceWishListLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWishListLocalService.class);

		ServiceTracker<CommerceWishListLocalService, CommerceWishListLocalService> serviceTracker =
			new ServiceTracker<CommerceWishListLocalService, CommerceWishListLocalService>(bundle.getBundleContext(),
				CommerceWishListLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}