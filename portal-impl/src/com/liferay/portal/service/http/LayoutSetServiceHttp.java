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

package com.liferay.portal.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.LayoutSetServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link LayoutSetServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @see LayoutSetServiceSoap
 * @see HttpPrincipal
 * @see LayoutSetServiceUtil
 * @generated
 */
@ProviderType
public class LayoutSetServiceHttp {
	public static void updateLayoutSetPrototypeLinkEnabled(
		HttpPrincipal httpPrincipal, long groupId,
		boolean layoutSetPrototypeLinkEnabled,
		java.lang.String layoutSetPrototypeUuid)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLayoutSetPrototypeLinkEnabled",
					_updateLayoutSetPrototypeLinkEnabledParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutSetPrototypeLinkEnabled, layoutSetPrototypeUuid);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateLogo(HttpPrincipal httpPrincipal, long groupId,
		boolean logo, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLogo", _updateLogoParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					logo, bytes);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateLogo(HttpPrincipal httpPrincipal, long groupId,
		boolean logo, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLogo", _updateLogoParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					logo, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateLogo(HttpPrincipal httpPrincipal, long groupId,
		boolean logo, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLogo", _updateLogoParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					logo, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateLogo(HttpPrincipal httpPrincipal, long groupId,
		boolean logo, java.io.InputStream inputStream, boolean cleanUpStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLogo", _updateLogoParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					logo, inputStream, cleanUpStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutSet updateLookAndFeel(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateLookAndFeel", _updateLookAndFeelParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					themeId, colorSchemeId, css, wapTheme);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutSet updateSettings(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String settings)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateSettings", _updateSettingsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					settings);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.LayoutSet updateVirtualHost(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutSetServiceUtil.class,
					"updateVirtualHost", _updateVirtualHostParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					virtualHost);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.model.LayoutSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetServiceHttp.class);
	private static final Class<?>[] _updateLayoutSetPrototypeLinkEnabledParameterTypes0 =
		new Class[] { long.class, boolean.class, java.lang.String.class };
	private static final Class<?>[] _updateLogoParameterTypes1 = new Class[] {
			long.class, boolean.class, byte[].class
		};
	private static final Class<?>[] _updateLogoParameterTypes2 = new Class[] {
			long.class, boolean.class, java.io.File.class
		};
	private static final Class<?>[] _updateLogoParameterTypes3 = new Class[] {
			long.class, boolean.class, java.io.InputStream.class
		};
	private static final Class<?>[] _updateLogoParameterTypes4 = new Class[] {
			long.class, boolean.class, java.io.InputStream.class, boolean.class
		};
	private static final Class<?>[] _updateLookAndFeelParameterTypes5 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _updateSettingsParameterTypes6 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _updateVirtualHostParameterTypes7 = new Class[] {
			long.class, java.lang.String.class
		};
}