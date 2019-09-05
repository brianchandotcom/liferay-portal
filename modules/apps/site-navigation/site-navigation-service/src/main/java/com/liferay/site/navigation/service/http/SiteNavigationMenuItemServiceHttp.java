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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.site.navigation.service.SiteNavigationMenuItemServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SiteNavigationMenuItemServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteNavigationMenuItemServiceSoap
 * @generated
 */
public class SiteNavigationMenuItemServiceHttp {

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			addSiteNavigationMenuItem(
				HttpPrincipal httpPrincipal, long groupId,
				long siteNavigationMenuId, long parentSiteNavigationMenuItemId,
				String type, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"addSiteNavigationMenuItem",
				_addSiteNavigationMenuItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, siteNavigationMenuId,
				parentSiteNavigationMenuItemId, type, typeSettings,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			deleteSiteNavigationMenuItem(
				HttpPrincipal httpPrincipal, long siteNavigationMenuItemId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"deleteSiteNavigationMenuItem",
				_deleteSiteNavigationMenuItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteSiteNavigationMenuItems(
			HttpPrincipal httpPrincipal, long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"deleteSiteNavigationMenuItems",
				_deleteSiteNavigationMenuItemsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItems(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"getSiteNavigationMenuItems",
				_getSiteNavigationMenuItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			updateSiteNavigationMenuItem(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId,
				long parentSiteNavigationMenuItemId, int order)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"updateSiteNavigationMenuItem",
				_updateSiteNavigationMenuItemParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, parentSiteNavigationMenuItemId,
				order);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			updateSiteNavigationMenuItem(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"updateSiteNavigationMenuItem",
				_updateSiteNavigationMenuItemParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, typeSettings, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuItemServiceHttp.class);

	private static final Class<?>[] _addSiteNavigationMenuItemParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[]
		_deleteSiteNavigationMenuItemParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_deleteSiteNavigationMenuItemsParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getSiteNavigationMenuItemsParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateSiteNavigationMenuItemParameterTypes4 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_updateSiteNavigationMenuItemParameterTypes5 = new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}