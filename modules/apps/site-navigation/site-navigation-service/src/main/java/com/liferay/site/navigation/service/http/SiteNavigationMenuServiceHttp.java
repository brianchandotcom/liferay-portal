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
import com.liferay.site.navigation.service.SiteNavigationMenuServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SiteNavigationMenuServiceUtil</code> service
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
 * @see SiteNavigationMenuServiceSoap
 * @generated
 */
public class SiteNavigationMenuServiceHttp {

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long groupId, String name,
				int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "addSiteNavigationMenu",
				_addSiteNavigationMenuParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, type, auto, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long groupId, String name,
				int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "addSiteNavigationMenu",
				_addSiteNavigationMenuParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, type, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			addSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long groupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "addSiteNavigationMenu",
				_addSiteNavigationMenuParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, name, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			deleteSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "deleteSiteNavigationMenu",
				_deleteSiteNavigationMenuParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			fetchSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "fetchSiteNavigationMenu",
				_fetchSiteNavigationMenuParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "getSiteNavigationMenus",
				_getSiteNavigationMenusParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "getSiteNavigationMenus",
				_getSiteNavigationMenusParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long groupId, String keywords,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "getSiteNavigationMenus",
				_getSiteNavigationMenusParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, keywords, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long groupId, String keywords) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			updateSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId,
				int type, boolean auto,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "updateSiteNavigationMenu",
				_updateSiteNavigationMenuParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, type, auto, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenu
			updateSiteNavigationMenu(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId,
				String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "updateSiteNavigationMenu",
				_updateSiteNavigationMenuParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, name, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuServiceHttp.class);

	private static final Class<?>[] _addSiteNavigationMenuParameterTypes0 =
		new Class[] {
			long.class, String.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addSiteNavigationMenuParameterTypes1 =
		new Class[] {
			long.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addSiteNavigationMenuParameterTypes2 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSiteNavigationMenuParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchSiteNavigationMenuParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getSiteNavigationMenusParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getSiteNavigationMenusParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getSiteNavigationMenusParameterTypes7 =
		new Class[] {
			long.class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes8 = new Class[] {long.class};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes9 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _updateSiteNavigationMenuParameterTypes10 =
		new Class[] {
			long.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateSiteNavigationMenuParameterTypes11 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}