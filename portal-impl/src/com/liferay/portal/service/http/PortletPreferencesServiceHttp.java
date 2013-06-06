/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.PortletPreferencesServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portal.service.PortletPreferencesServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @see PortletPreferencesServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portal.service.PortletPreferencesServiceUtil
 * @generated
 */
public class PortletPreferencesServiceHttp {
	public static void deleteArchivedPreferences(HttpPrincipal httpPrincipal,
		long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PortletPreferencesServiceUtil.class,
					"deleteArchivedPreferences",
					_deleteArchivedPreferencesParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					portletItemId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreArchivedPreferences(HttpPrincipal httpPrincipal,
		long groupId, com.liferay.portal.model.Layout layout,
		java.lang.String portletId, long portletItemId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PortletPreferencesServiceUtil.class,
					"restoreArchivedPreferences",
					_restoreArchivedPreferencesParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layout, portletId, portletItemId, preferences);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreArchivedPreferences(HttpPrincipal httpPrincipal,
		long groupId, com.liferay.portal.model.Layout layout,
		java.lang.String portletId,
		com.liferay.portal.model.PortletItem portletItem,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PortletPreferencesServiceUtil.class,
					"restoreArchivedPreferences",
					_restoreArchivedPreferencesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layout, portletId, portletItem, preferences);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreArchivedPreferences(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String name,
		com.liferay.portal.model.Layout layout, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PortletPreferencesServiceUtil.class,
					"restoreArchivedPreferences",
					_restoreArchivedPreferencesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					name, layout, portletId, preferences);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateArchivePreferences(HttpPrincipal httpPrincipal,
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(PortletPreferencesServiceUtil.class,
					"updateArchivePreferences",
					_updateArchivePreferencesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, name, portletId, preferences);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletPreferencesServiceHttp.class);
	private static final Class<?>[] _deleteArchivedPreferencesParameterTypes0 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreArchivedPreferencesParameterTypes1 = new Class[] {
			long.class, com.liferay.portal.model.Layout.class,
			java.lang.String.class, long.class,
			javax.portlet.PortletPreferences.class
		};
	private static final Class<?>[] _restoreArchivedPreferencesParameterTypes2 = new Class[] {
			long.class, com.liferay.portal.model.Layout.class,
			java.lang.String.class, com.liferay.portal.model.PortletItem.class,
			javax.portlet.PortletPreferences.class
		};
	private static final Class<?>[] _restoreArchivedPreferencesParameterTypes3 = new Class[] {
			long.class, java.lang.String.class,
			com.liferay.portal.model.Layout.class, java.lang.String.class,
			javax.portlet.PortletPreferences.class
		};
	private static final Class<?>[] _updateArchivePreferencesParameterTypes4 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class, javax.portlet.PortletPreferences.class
		};
}