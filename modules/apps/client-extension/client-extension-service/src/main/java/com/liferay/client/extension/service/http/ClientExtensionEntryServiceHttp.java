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

package com.liferay.client.extension.service.http;

import com.liferay.client.extension.service.ClientExtensionEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ClientExtensionEntryServiceUtil</code> service
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
public class ClientExtensionEntryServiceHttp {

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addCustomElementClientExtensionEntry(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping, boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addCustomElementClientExtensionEntry",
				_addCustomElementClientExtensionEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs,
				customElementUseESM, description, friendlyURLMapping,
				instanceable, nameMap, portletCategoryName, properties,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addIFrameClientExtensionEntry(
				HttpPrincipal httpPrincipal, String description,
				String friendlyURLMapping, String iFrameURL,
				boolean instanceable,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addIFrameClientExtensionEntry",
				_addIFrameClientExtensionEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, description, friendlyURLMapping, iFrameURL,
				instanceable, nameMap, portletCategoryName, properties,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addThemeCSSClientExtensionEntry(
				HttpPrincipal httpPrincipal, String clayURL, String description,
				String mainURL, java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addThemeCSSClientExtensionEntry",
				_addThemeCSSClientExtensionEntryParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clayURL, description, mainURL, nameMap,
				sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addThemeFaviconClientExtensionEntry(
				HttpPrincipal httpPrincipal, String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addThemeFaviconClientExtensionEntry",
				_addThemeFaviconClientExtensionEntryParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, description, nameMap, sourceCodeURL, url);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			addThemeJSClientExtensionEntry(
				HttpPrincipal httpPrincipal, String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL, String urls)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"addThemeJSClientExtensionEntry",
				_addThemeJSClientExtensionEntryParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, description, nameMap, sourceCodeURL, urls);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			deleteClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"deleteClientExtensionEntry",
				_deleteClientExtensionEntryParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			getClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"getClientExtensionEntry",
				_getClientExtensionEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateCustomElementClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String customElementCSSURLs,
				String customElementHTMLElementName, String customElementURLs,
				boolean customElementUseESM, String description,
				String friendlyURLMapping,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateCustomElementClientExtensionEntry",
				_updateCustomElementClientExtensionEntryParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, customElementCSSURLs,
				customElementHTMLElementName, customElementURLs,
				customElementUseESM, description, friendlyURLMapping, nameMap,
				portletCategoryName, properties, sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateIFrameClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String description, String friendlyURLMapping, String iFrameURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String portletCategoryName, String properties,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateIFrameClientExtensionEntry",
				_updateIFrameClientExtensionEntryParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, description,
				friendlyURLMapping, iFrameURL, nameMap, portletCategoryName,
				properties, sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateThemeCSSClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String clayURL, String description, String mainURL,
				java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateThemeCSSClientExtensionEntry",
				_updateThemeCSSClientExtensionEntryParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, clayURL, description,
				mainURL, nameMap, sourceCodeURL);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateThemeFaviconClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL, String url)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateThemeFaviconClientExtensionEntry",
				_updateThemeFaviconClientExtensionEntryParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, description, nameMap,
				sourceCodeURL, url);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.client.extension.model.ClientExtensionEntry
			updateThemeJSClientExtensionEntry(
				HttpPrincipal httpPrincipal, long clientExtensionEntryId,
				String description,
				java.util.Map<java.util.Locale, String> nameMap,
				String sourceCodeURL, String urls)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ClientExtensionEntryServiceUtil.class,
				"updateThemeJSClientExtensionEntry",
				_updateThemeJSClientExtensionEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, clientExtensionEntryId, description, nameMap,
				sourceCodeURL, urls);

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

			return (com.liferay.client.extension.model.ClientExtensionEntry)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClientExtensionEntryServiceHttp.class);

	private static final Class<?>[]
		_addCustomElementClientExtensionEntryParameterTypes0 = new Class[] {
			String.class, String.class, String.class, String.class,
			boolean.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_addIFrameClientExtensionEntryParameterTypes1 = new Class[] {
			String.class, String.class, String.class, boolean.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_addThemeCSSClientExtensionEntryParameterTypes2 = new Class[] {
			String.class, String.class, String.class, java.util.Map.class,
			String.class
		};
	private static final Class<?>[]
		_addThemeFaviconClientExtensionEntryParameterTypes3 = new Class[] {
			String.class, java.util.Map.class, String.class, String.class
		};
	private static final Class<?>[]
		_addThemeJSClientExtensionEntryParameterTypes4 = new Class[] {
			String.class, java.util.Map.class, String.class, String.class
		};
	private static final Class<?>[] _deleteClientExtensionEntryParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _getClientExtensionEntryParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[]
		_updateCustomElementClientExtensionEntryParameterTypes7 = new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			String.class, String.class, java.util.Map.class, String.class,
			String.class, String.class
		};
	private static final Class<?>[]
		_updateIFrameClientExtensionEntryParameterTypes8 = new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class, String.class, String.class
		};
	private static final Class<?>[]
		_updateThemeCSSClientExtensionEntryParameterTypes9 = new Class[] {
			long.class, String.class, String.class, String.class,
			java.util.Map.class, String.class
		};
	private static final Class<?>[]
		_updateThemeFaviconClientExtensionEntryParameterTypes10 = new Class[] {
			long.class, String.class, java.util.Map.class, String.class,
			String.class
		};
	private static final Class<?>[]
		_updateThemeJSClientExtensionEntryParameterTypes11 = new Class[] {
			long.class, String.class, java.util.Map.class, String.class,
			String.class
		};

}