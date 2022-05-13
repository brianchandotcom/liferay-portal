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

package com.liferay.oauth.client.persistence.service.http;

import com.liferay.oauth.client.persistence.service.OAuthClientAuthServerServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OAuthClientAuthServerServiceUtil</code> service
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
public class OAuthClientAuthServerServiceHttp {

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				addOAuthClientAuthServer(
					HttpPrincipal httpPrincipal, long userId,
					String discoveryEndpoint, String metadataJSON, String type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"addOAuthClientAuthServer",
				_addOAuthClientAuthServerParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, discoveryEndpoint, metadataJSON, type);

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

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientAuthServer)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				deleteOAuthClientAuthServer(
					HttpPrincipal httpPrincipal, long oAuthClientAuthServerId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"deleteOAuthClientAuthServer",
				_deleteOAuthClientAuthServerParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthClientAuthServerId);

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

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientAuthServer)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				deleteOAuthClientAuthServer(
					HttpPrincipal httpPrincipal, long companyId, String issuer)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"deleteOAuthClientAuthServer",
				_deleteOAuthClientAuthServerParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, issuer);

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

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientAuthServer)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(
				HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getCompanyOAuthClientAuthServers",
				_getCompanyOAuthClientAuthServersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientAuthServer>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getCompanyOAuthClientAuthServers(
				HttpPrincipal httpPrincipal, long companyId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getCompanyOAuthClientAuthServers",
				_getCompanyOAuthClientAuthServersParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientAuthServer>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				getOAuthClientAuthServer(
					HttpPrincipal httpPrincipal, long companyId, String issuer)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getOAuthClientAuthServer",
				_getOAuthClientAuthServerParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, issuer);

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

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientAuthServer)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getTypeOAuthClientAuthServers(
				HttpPrincipal httpPrincipal, long companyId, String type) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getTypeOAuthClientAuthServers",
				_getTypeOAuthClientAuthServersParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, type);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientAuthServer>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(
				HttpPrincipal httpPrincipal, long userId) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getUserOAuthClientAuthServers",
				_getUserOAuthClientAuthServersParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientAuthServer>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientAuthServer>
			getUserOAuthClientAuthServers(
				HttpPrincipal httpPrincipal, long userId, int start, int end) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"getUserOAuthClientAuthServers",
				_getUserOAuthClientAuthServersParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientAuthServer>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientAuthServer
				updateOAuthClientAuthServer(
					HttpPrincipal httpPrincipal, long oAuthClientAuthServerId,
					String discoveryEndpoint, String metadataJSON, String type)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientAuthServerServiceUtil.class,
				"updateOAuthClientAuthServer",
				_updateOAuthClientAuthServerParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthClientAuthServerId, discoveryEndpoint,
				metadataJSON, type);

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

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientAuthServer)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuthClientAuthServerServiceHttp.class);

	private static final Class<?>[] _addOAuthClientAuthServerParameterTypes0 =
		new Class[] {long.class, String.class, String.class, String.class};
	private static final Class<?>[]
		_deleteOAuthClientAuthServerParameterTypes1 = new Class[] {long.class};
	private static final Class<?>[]
		_deleteOAuthClientAuthServerParameterTypes2 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCompanyOAuthClientAuthServersParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCompanyOAuthClientAuthServersParameterTypes4 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[] _getOAuthClientAuthServerParameterTypes5 =
		new Class[] {long.class, String.class};
	private static final Class<?>[]
		_getTypeOAuthClientAuthServersParameterTypes6 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getUserOAuthClientAuthServersParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getUserOAuthClientAuthServersParameterTypes8 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_updateOAuthClientAuthServerParameterTypes9 = new Class[] {
			long.class, String.class, String.class, String.class
		};

}