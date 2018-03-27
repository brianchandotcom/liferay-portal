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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SiteNavigationMenu. This utility wraps
 * {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuLocalService
 * @see com.liferay.site.navigation.service.base.SiteNavigationMenuLocalServiceBaseImpl
 * @see com.liferay.site.navigation.service.impl.SiteNavigationMenuLocalServiceImpl
 * @generated
 */
@ProviderType
public class SiteNavigationMenuLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenu addDefaultSiteNavigationMenu(
		long userId, long groupId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDefaultSiteNavigationMenu(userId, groupId, serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name, int type,
		boolean auto,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteNavigationMenu(userId, groupId, name, type, auto,
			serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteNavigationMenu(userId, groupId, name, type,
			serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		long userId, long groupId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteNavigationMenu(userId, groupId, name, serviceContext);
	}

	/**
	* Adds the site navigation menu to the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was added
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu addSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu) {
		return getService().addSiteNavigationMenu(siteNavigationMenu);
	}

	/**
	* Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	*
	* @param siteNavigationMenuId the primary key for the new site navigation menu
	* @return the new site navigation menu
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu createSiteNavigationMenu(
		long siteNavigationMenuId) {
		return getService().createSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu that was removed
	* @throws PortalException if a site navigation menu with the primary key could not be found
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu deleteSiteNavigationMenu(
		long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* Deletes the site navigation menu from the database. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was removed
	* @throws PortalException
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu deleteSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSiteNavigationMenu(siteNavigationMenu);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.site.navigation.model.SiteNavigationMenu fetchPrimarySiteNavigationMenu(
		long groupId) {
		return getService().fetchPrimarySiteNavigationMenu(groupId);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu fetchSiteNavigationMenu(
		long siteNavigationMenuId) {
		return getService().fetchSiteNavigationMenu(siteNavigationMenuId);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu fetchSiteNavigationMenu(
		long groupId, int type) {
		return getService().fetchSiteNavigationMenu(groupId, type);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getAutoSiteNavigationMenus(
		long groupId) {
		return getService().getAutoSiteNavigationMenus(groupId);
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
	* Returns the site navigation menu with the primary key.
	*
	* @param siteNavigationMenuId the primary key of the site navigation menu
	* @return the site navigation menu
	* @throws PortalException if a site navigation menu with the primary key could not be found
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu getSiteNavigationMenu(
		long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSiteNavigationMenu(siteNavigationMenuId);
	}

	/**
	* Returns a range of all the site navigation menus.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site navigation menus
	* @param end the upper bound of the range of site navigation menus (not inclusive)
	* @return the range of site navigation menus
	*/
	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		int start, int end) {
		return getService().getSiteNavigationMenus(start, end);
	}

	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId) {
		return getService().getSiteNavigationMenus(groupId);
	}

	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getSiteNavigationMenus(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getSiteNavigationMenus(groupId, keywords, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of site navigation menus.
	*
	* @return the number of site navigation menus
	*/
	public static int getSiteNavigationMenusCount() {
		return getService().getSiteNavigationMenusCount();
	}

	public static int getSiteNavigationMenusCount(long groupId) {
		return getService().getSiteNavigationMenusCount(groupId);
	}

	public static int getSiteNavigationMenusCount(long groupId,
		java.lang.String keywords) {
		return getService().getSiteNavigationMenusCount(groupId, keywords);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		long userId, long siteNavigationMenuId, int type, boolean auto,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteNavigationMenu(userId, siteNavigationMenuId,
			type, auto, serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		long userId, long siteNavigationMenuId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteNavigationMenu(userId, siteNavigationMenuId,
			name, serviceContext);
	}

	/**
	* Updates the site navigation menu in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param siteNavigationMenu the site navigation menu
	* @return the site navigation menu that was updated
	*/
	public static com.liferay.site.navigation.model.SiteNavigationMenu updateSiteNavigationMenu(
		com.liferay.site.navigation.model.SiteNavigationMenu siteNavigationMenu) {
		return getService().updateSiteNavigationMenu(siteNavigationMenu);
	}

	public static SiteNavigationMenuLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SiteNavigationMenuLocalService, SiteNavigationMenuLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteNavigationMenuLocalService.class);

		ServiceTracker<SiteNavigationMenuLocalService, SiteNavigationMenuLocalService> serviceTracker =
			new ServiceTracker<SiteNavigationMenuLocalService, SiteNavigationMenuLocalService>(bundle.getBundleContext(),
				SiteNavigationMenuLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}