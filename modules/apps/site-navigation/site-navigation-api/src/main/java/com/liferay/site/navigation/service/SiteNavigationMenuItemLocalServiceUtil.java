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

package com.liferay.site.navigation.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SiteNavigationMenuItem. This utility wraps
 * <code>com.liferay.site.navigation.service.impl.SiteNavigationMenuItemLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemLocalService
 * @generated
 */
public class SiteNavigationMenuItemLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.site.navigation.service.impl.SiteNavigationMenuItemLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SiteNavigationMenuItemLocalServiceUtil} to access the site navigation menu item local service. Add custom service methods to <code>com.liferay.site.navigation.service.impl.SiteNavigationMenuItemLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			addSiteNavigationMenuItem(
				long userId, long groupId, long siteNavigationMenuId,
				long parentSiteNavigationMenuItemId, String type, int order,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSiteNavigationMenuItem(
			userId, groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, order, typeSettings,
			serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			addSiteNavigationMenuItem(
				long userId, long groupId, long siteNavigationMenuId,
				long parentSiteNavigationMenuItemId, String type,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSiteNavigationMenuItem(
			userId, groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, typeSettings, serviceContext);
	}

	/**
	 * Adds the site navigation menu item to the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuItem the site navigation menu item
	 * @return the site navigation menu item that was added
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		addSiteNavigationMenuItem(
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				siteNavigationMenuItem) {

		return getService().addSiteNavigationMenuItem(siteNavigationMenuItem);
	}

	/**
	 * Creates a new site navigation menu item with the primary key. Does not add the site navigation menu item to the database.
	 *
	 * @param siteNavigationMenuItemId the primary key for the new site navigation menu item
	 * @return the new site navigation menu item
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		createSiteNavigationMenuItem(long siteNavigationMenuItemId) {

		return getService().createSiteNavigationMenuItem(
			siteNavigationMenuItemId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the site navigation menu item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item that was removed
	 * @throws PortalException if a site navigation menu item with the primary key could not be found
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			deleteSiteNavigationMenuItem(long siteNavigationMenuItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSiteNavigationMenuItem(
			siteNavigationMenuItemId);
	}

	/**
	 * Deletes the site navigation menu item from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuItem the site navigation menu item
	 * @return the site navigation menu item that was removed
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		deleteSiteNavigationMenuItem(
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				siteNavigationMenuItem) {

		return getService().deleteSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	public static void deleteSiteNavigationMenuItems(
		long siteNavigationMenuId) {

		getService().deleteSiteNavigationMenuItems(siteNavigationMenuId);
	}

	public static void deleteSiteNavigationMenuItemsByGroupId(long groupId) {
		getService().deleteSiteNavigationMenuItemsByGroupId(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		fetchSiteNavigationMenuItem(long siteNavigationMenuItemId) {

		return getService().fetchSiteNavigationMenuItem(
			siteNavigationMenuItemId);
	}

	/**
	 * Returns the site navigation menu item matching the UUID and group.
	 *
	 * @param uuid the site navigation menu item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site navigation menu item, or <code>null</code> if a matching site navigation menu item could not be found
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		fetchSiteNavigationMenuItemByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchSiteNavigationMenuItemByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the site navigation menu item with the primary key.
	 *
	 * @param siteNavigationMenuItemId the primary key of the site navigation menu item
	 * @return the site navigation menu item
	 * @throws PortalException if a site navigation menu item with the primary key could not be found
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			getSiteNavigationMenuItem(long siteNavigationMenuItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSiteNavigationMenuItem(siteNavigationMenuItemId);
	}

	/**
	 * Returns the site navigation menu item matching the UUID and group.
	 *
	 * @param uuid the site navigation menu item's UUID
	 * @param groupId the primary key of the group
	 * @return the matching site navigation menu item
	 * @throws PortalException if a matching site navigation menu item could not be found
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			getSiteNavigationMenuItemByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSiteNavigationMenuItemByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the site navigation menu items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.site.navigation.model.impl.SiteNavigationMenuItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @return the range of site navigation menu items
	 */
	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItems(int start, int end) {

		return getService().getSiteNavigationMenuItems(start, end);
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItems(long siteNavigationMenuId) {

		return getService().getSiteNavigationMenuItems(siteNavigationMenuId);
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItems(
				long siteNavigationMenuId,
				long parentSiteNavigationMenuItemId) {

		return getService().getSiteNavigationMenuItems(
			siteNavigationMenuId, parentSiteNavigationMenuItemId);
	}

	/**
	 * Returns all the site navigation menu items matching the UUID and company.
	 *
	 * @param uuid the UUID of the site navigation menu items
	 * @param companyId the primary key of the company
	 * @return the matching site navigation menu items, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItemsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getSiteNavigationMenuItemsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of site navigation menu items matching the UUID and company.
	 *
	 * @param uuid the UUID of the site navigation menu items
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of site navigation menu items
	 * @param end the upper bound of the range of site navigation menu items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching site navigation menu items, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItemsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenuItem>
						orderByComparator) {

		return getService().getSiteNavigationMenuItemsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of site navigation menu items.
	 *
	 * @return the number of site navigation menu items
	 */
	public static int getSiteNavigationMenuItemsCount() {
		return getService().getSiteNavigationMenuItemsCount();
	}

	public static int getSiteNavigationMenuItemsCount(
		long siteNavigationMenuId) {

		return getService().getSiteNavigationMenuItemsCount(
			siteNavigationMenuId);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			updateSiteNavigationMenuItem(
				long siteNavigationMenuItemId,
				long parentSiteNavigationMenuItemId, int order)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSiteNavigationMenuItem(
			siteNavigationMenuItemId, parentSiteNavigationMenuItemId, order);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			updateSiteNavigationMenuItem(
				long userId, long siteNavigationMenuItemId, long groupId,
				long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
				String type, int order, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSiteNavigationMenuItem(
			userId, siteNavigationMenuItemId, groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, order, typeSettings);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			updateSiteNavigationMenuItem(
				long userId, long siteNavigationMenuItemId, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSiteNavigationMenuItem(
			userId, siteNavigationMenuItemId, typeSettings, serviceContext);
	}

	/**
	 * Updates the site navigation menu item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuItem the site navigation menu item
	 * @return the site navigation menu item that was updated
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
		updateSiteNavigationMenuItem(
			com.liferay.site.navigation.model.SiteNavigationMenuItem
				siteNavigationMenuItem) {

		return getService().updateSiteNavigationMenuItem(
			siteNavigationMenuItem);
	}

	public static SiteNavigationMenuItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SiteNavigationMenuItemLocalService, SiteNavigationMenuItemLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SiteNavigationMenuItemLocalService.class);

		ServiceTracker
			<SiteNavigationMenuItemLocalService,
			 SiteNavigationMenuItemLocalService> serviceTracker =
				new ServiceTracker
					<SiteNavigationMenuItemLocalService,
					 SiteNavigationMenuItemLocalService>(
						 bundle.getBundleContext(),
						 SiteNavigationMenuItemLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}