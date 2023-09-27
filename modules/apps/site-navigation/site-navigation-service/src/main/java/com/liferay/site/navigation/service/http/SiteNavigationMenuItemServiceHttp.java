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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.site.navigation.model.SiteNavigationMenuItem
			deleteSiteNavigationMenuItem(
				HttpPrincipal httpPrincipal, long siteNavigationMenuItemId,
				boolean deleteChildren)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"deleteSiteNavigationMenuItem",
				_deleteSiteNavigationMenuItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuItemId, deleteChildren);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteSiteNavigationMenuItems(
			HttpPrincipal httpPrincipal, long siteNavigationMenuId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"deleteSiteNavigationMenuItems",
				_deleteSiteNavigationMenuItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
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
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<Long> getParentSiteNavigationMenuItemIds(
		HttpPrincipal httpPrincipal, long siteNavigationMenuId,
		String typeSettingsKeyword) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"getParentSiteNavigationMenuItemIds",
				_getParentSiteNavigationMenuItemIdsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, typeSettingsKeyword);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<Long>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_getSiteNavigationMenuItemsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
				getSiteNavigationMenuItems(
					HttpPrincipal httpPrincipal, long siteNavigationMenuId,
					long parentSiteNavigationMenuItemId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"getSiteNavigationMenuItems",
				_getSiteNavigationMenuItemsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId,
				parentSiteNavigationMenuItemId);

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

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.site.navigation.model.SiteNavigationMenuItem>
			getSiteNavigationMenuItems(
				HttpPrincipal httpPrincipal, long siteNavigationMenuId,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.site.navigation.model.SiteNavigationMenuItem>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				SiteNavigationMenuItemServiceUtil.class,
				"getSiteNavigationMenuItems",
				_getSiteNavigationMenuItemsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.site.navigation.model.SiteNavigationMenuItem>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateSiteNavigationMenuItemParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, parentSiteNavigationMenuItemId,
				order);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
				_updateSiteNavigationMenuItemParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, siteNavigationMenuId, typeSettings, serviceContext);

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

			return (com.liferay.site.navigation.model.SiteNavigationMenuItem)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
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
		_deleteSiteNavigationMenuItemParameterTypes2 = new Class[] {
			long.class, boolean.class
		};
	private static final Class<?>[]
		_deleteSiteNavigationMenuItemsParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getParentSiteNavigationMenuItemIdsParameterTypes4 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[] _getSiteNavigationMenuItemsParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getSiteNavigationMenuItemsParameterTypes6 =
		new Class[] {long.class, long.class};
	private static final Class<?>[] _getSiteNavigationMenuItemsParameterTypes7 =
		new Class[] {
			long.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_updateSiteNavigationMenuItemParameterTypes8 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[]
		_updateSiteNavigationMenuItemParameterTypes9 = new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}