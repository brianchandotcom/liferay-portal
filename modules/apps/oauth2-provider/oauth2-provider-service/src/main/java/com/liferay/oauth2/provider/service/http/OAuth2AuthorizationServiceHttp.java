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

package com.liferay.oauth2.provider.service.http;

import com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OAuth2AuthorizationServiceUtil</code> service
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
 * @see OAuth2AuthorizationServiceSoap
 * @generated
 */
public class OAuth2AuthorizationServiceHttp {

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2Authorization>
				getApplicationOAuth2Authorizations(
					HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.oauth2.provider.model.OAuth2Authorization>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2AuthorizationServiceUtil.class,
				"getApplicationOAuth2Authorizations",
				_getApplicationOAuth2AuthorizationsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, start, end, orderByComparator);

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

			return (java.util.List
				<com.liferay.oauth2.provider.model.OAuth2Authorization>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getApplicationOAuth2AuthorizationsCount(
			HttpPrincipal httpPrincipal, long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2AuthorizationServiceUtil.class,
				"getApplicationOAuth2AuthorizationsCount",
				_getApplicationOAuth2AuthorizationsCountParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2Authorization>
				getUserOAuth2Authorizations(
					HttpPrincipal httpPrincipal, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.oauth2.provider.model.OAuth2Authorization>
							orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2AuthorizationServiceUtil.class,
				"getUserOAuth2Authorizations",
				_getUserOAuth2AuthorizationsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end, orderByComparator);

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

			return (java.util.List
				<com.liferay.oauth2.provider.model.OAuth2Authorization>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getUserOAuth2AuthorizationsCount(
			HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2AuthorizationServiceUtil.class,
				"getUserOAuth2AuthorizationsCount",
				_getUserOAuth2AuthorizationsCountParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revokeOAuth2Authorization(
			HttpPrincipal httpPrincipal, long oAuth2AuthorizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2AuthorizationServiceUtil.class,
				"revokeOAuth2Authorization",
				_revokeOAuth2AuthorizationParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2AuthorizationId);

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

	private static Log _log = LogFactoryUtil.getLog(
		OAuth2AuthorizationServiceHttp.class);

	private static final Class<?>[]
		_getApplicationOAuth2AuthorizationsParameterTypes0 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getApplicationOAuth2AuthorizationsCountParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getUserOAuth2AuthorizationsParameterTypes2 = new Class[] {
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getUserOAuth2AuthorizationsCountParameterTypes3 = new Class[] {};
	private static final Class<?>[] _revokeOAuth2AuthorizationParameterTypes4 =
		new Class[] {long.class};

}