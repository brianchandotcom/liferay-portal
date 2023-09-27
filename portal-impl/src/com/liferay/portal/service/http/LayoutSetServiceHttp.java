/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.LayoutSetServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>LayoutSetServiceUtil</code> service
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
public class LayoutSetServiceHttp {

	public static void updateFaviconFileEntryId(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			long faviconFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateFaviconFileEntryId",
				_updateFaviconFileEntryIdParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, faviconFileEntryId);

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

	public static void updateLayoutSetPrototypeLinkEnabled(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class,
				"updateLayoutSetPrototypeLinkEnabled",
				_updateLayoutSetPrototypeLinkEnabledParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout,
				layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);

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

	public static void updateLogo(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean hasLogo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, hasLogo, bytes);

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

	public static void updateLogo(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean hasLogo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, hasLogo, file);

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

	public static void updateLogo(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean hasLogo, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, hasLogo, inputStream);

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

	public static void updateLogo(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			boolean hasLogo, java.io.InputStream inputStream,
			boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, hasLogo, inputStream,
				cleanUpStream);

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

	public static com.liferay.portal.kernel.model.LayoutSet updateLookAndFeel(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			String themeId, String colorSchemeId, String css)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateLookAndFeel",
				_updateLookAndFeelParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, themeId, colorSchemeId, css);

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

			return (com.liferay.portal.kernel.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateSettings(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			String settings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateSettings",
				_updateSettingsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, settings);

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

			return (com.liferay.portal.kernel.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.LayoutSet updateVirtualHosts(
			HttpPrincipal httpPrincipal, long groupId, boolean privateLayout,
			java.util.TreeMap<String, String> virtualHostnames)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				LayoutSetServiceUtil.class, "updateVirtualHosts",
				_updateVirtualHostsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, privateLayout, virtualHostnames);

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

			return (com.liferay.portal.kernel.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetServiceHttp.class);

	private static final Class<?>[] _updateFaviconFileEntryIdParameterTypes0 =
		new Class[] {long.class, boolean.class, long.class};
	private static final Class<?>[]
		_updateLayoutSetPrototypeLinkEnabledParameterTypes1 = new Class[] {
			long.class, boolean.class, boolean.class, String.class
		};
	private static final Class<?>[] _updateLogoParameterTypes2 = new Class[] {
		long.class, boolean.class, boolean.class, byte[].class
	};
	private static final Class<?>[] _updateLogoParameterTypes3 = new Class[] {
		long.class, boolean.class, boolean.class, java.io.File.class
	};
	private static final Class<?>[] _updateLogoParameterTypes4 = new Class[] {
		long.class, boolean.class, boolean.class, java.io.InputStream.class
	};
	private static final Class<?>[] _updateLogoParameterTypes5 = new Class[] {
		long.class, boolean.class, boolean.class, java.io.InputStream.class,
		boolean.class
	};
	private static final Class<?>[] _updateLookAndFeelParameterTypes6 =
		new Class[] {
			long.class, boolean.class, String.class, String.class, String.class
		};
	private static final Class<?>[] _updateSettingsParameterTypes7 =
		new Class[] {long.class, boolean.class, String.class};
	private static final Class<?>[] _updateVirtualHostsParameterTypes8 =
		new Class[] {long.class, boolean.class, java.util.TreeMap.class};

}