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

package com.liferay.site.navigation.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.site.navigation.service.SiteNavigationMenuItemServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link SiteNavigationMenuItemServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.site.navigation.model.SiteNavigationMenuItemSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.site.navigation.model.SiteNavigationMenuItem}, that is translated to a
 * {@link com.liferay.site.navigation.model.SiteNavigationMenuItemSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemServiceHttp
 * @see com.liferay.site.navigation.model.SiteNavigationMenuItemSoap
 * @see SiteNavigationMenuItemServiceUtil
 * @generated
 */
@ProviderType
public class SiteNavigationMenuItemServiceSoap {
	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap addSiteNavigationMenuItem(
		long groupId, long siteNavigationMenuId,
		long parentSiteNavigationMenuItemId, java.lang.String type,
		java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem returnValue =
				SiteNavigationMenuItemServiceUtil.addSiteNavigationMenuItem(groupId,
					siteNavigationMenuId, parentSiteNavigationMenuItemId, type,
					typeSettings, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap deleteSiteNavigationMenuItem(
		long siteNavigationMenuItemId) throws RemoteException {
		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem returnValue =
				SiteNavigationMenuItemServiceUtil.deleteSiteNavigationMenuItem(siteNavigationMenuItemId);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteSiteNavigationMenuItems(long siteNavigationMenuId)
		throws RemoteException {
		try {
			SiteNavigationMenuItemServiceUtil.deleteSiteNavigationMenuItems(siteNavigationMenuId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap[] getSiteNavigationMenuItems(
		long siteNavigationMenuId) throws RemoteException {
		try {
			java.util.List<com.liferay.site.navigation.model.SiteNavigationMenuItem> returnValue =
				SiteNavigationMenuItemServiceUtil.getSiteNavigationMenuItems(siteNavigationMenuId);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap updateSiteNavigationMenuItem(
		long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
		int order) throws RemoteException {
		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem returnValue =
				SiteNavigationMenuItemServiceUtil.updateSiteNavigationMenuItem(siteNavigationMenuId,
					parentSiteNavigationMenuItemId, order);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItemSoap updateSiteNavigationMenuItem(
		long siteNavigationMenuId, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.site.navigation.model.SiteNavigationMenuItem returnValue =
				SiteNavigationMenuItemServiceUtil.updateSiteNavigationMenuItem(siteNavigationMenuId,
					typeSettings, serviceContext);

			return com.liferay.site.navigation.model.SiteNavigationMenuItemSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SiteNavigationMenuItemServiceSoap.class);
}