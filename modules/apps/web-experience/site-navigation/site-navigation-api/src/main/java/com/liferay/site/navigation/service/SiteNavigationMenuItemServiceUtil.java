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
 * Provides the remote service utility for SiteNavigationMenuItem. This utility wraps
 * {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuItemServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemService
 * @see com.liferay.site.navigation.service.base.SiteNavigationMenuItemServiceBaseImpl
 * @see com.liferay.site.navigation.service.impl.SiteNavigationMenuItemServiceImpl
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.site.navigation.service.impl.SiteNavigationMenuItemServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.site.navigation.model.SiteNavigationMenuItem addSiteNavigationMenuItem(
		long groupId, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, java.lang.String type,
		java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteNavigationMenuItem(groupId, siteNavigationMenuId,
			parentSiteNavigationMenuItemId, type, typeSettings, serviceContext);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem deleteSiteNavigationMenuItem(
		long siteNavigationMenuItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteSiteNavigationMenuItem(siteNavigationMenuItemId);
	}

	public static void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSiteNavigationMenuItems(siteNavigationMenuId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.site.navigation.model.SiteNavigationMenuItem> getSiteNavigationMenuItems(
		long siteNavigationMenuId) {
		return getService().getSiteNavigationMenuItems(siteNavigationMenuId);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int order) throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteNavigationMenuItem(siteNavigationMenuId,
			parentSiteNavigationMenuItemId, order);
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem updateSiteNavigationMenuItem(
		long siteNavigationMenuId, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteNavigationMenuItem(siteNavigationMenuId,
			typeSettings, serviceContext);
	}

	public static SiteNavigationMenuItemService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SiteNavigationMenuItemService, SiteNavigationMenuItemService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SiteNavigationMenuItemService.class);

		ServiceTracker<SiteNavigationMenuItemService, SiteNavigationMenuItemService> serviceTracker =
			new ServiceTracker<SiteNavigationMenuItemService, SiteNavigationMenuItemService>(bundle.getBundleContext(),
				SiteNavigationMenuItemService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}