/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long groupId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
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
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long groupId, String keywords,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
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
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long[] groupIds, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "getSiteNavigationMenus",
				_getSiteNavigationMenusParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenu>
			getSiteNavigationMenus(
				HttpPrincipal httpPrincipal, long[] groupIds, String keywords,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenu>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class, "getSiteNavigationMenus",
				_getSiteNavigationMenusParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, keywords, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenu>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long groupId) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long groupId, String keywords) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getSiteNavigationMenusCount(
		HttpPrincipal httpPrincipal, long[] groupIds, String keywords) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuServiceUtil.class,
				"getSiteNavigationMenusCount",
				_getSiteNavigationMenusCountParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds, keywords);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateSiteNavigationMenuParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, type, auto, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateSiteNavigationMenuParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, name, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenu)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
	private static final Class<?>[] _getSiteNavigationMenusParameterTypes8 =
		new Class[] {
			long[].class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getSiteNavigationMenusParameterTypes9 =
		new Class[] {
			long[].class, String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes10 = new Class[] {long.class};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes11 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes12 = new Class[] {
			long[].class
		};
	private static final Class<?>[]
		_getSiteNavigationMenusCountParameterTypes13 = new Class[] {
			long[].class, String.class
		};
	private static final Class<?>[] _updateSiteNavigationMenuParameterTypes14 =
		new Class[] {
			long.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateSiteNavigationMenuParameterTypes15 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}