/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.service.http;

import com.liferay.oauth.service.OAuthApplicationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OAuthApplicationServiceUtil</code> service
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
 * @author Ivica Cardic
 * @see OAuthApplicationServiceSoap
 * @generated
 */
public class OAuthApplicationServiceHttp {

	public static com.liferay.oauth.model.OAuthApplication addOAuthApplication(
			HttpPrincipal httpPrincipal, String name, String description,
			int accessLevel, boolean shareableAccessToken, String callbackURI,
			String websiteURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthApplicationServiceUtil.class, "addOAuthApplication",
				_addOAuthApplicationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, description, accessLevel, shareableAccessToken,
				callbackURI, websiteURL, serviceContext);

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

			return (com.liferay.oauth.model.OAuthApplication)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteLogo(
			HttpPrincipal httpPrincipal, long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthApplicationServiceUtil.class, "deleteLogo",
				_deleteLogoParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthApplicationId);

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

	public static com.liferay.oauth.model.OAuthApplication
			deleteOAuthApplication(
				HttpPrincipal httpPrincipal, long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthApplicationServiceUtil.class, "deleteOAuthApplication",
				_deleteOAuthApplicationParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthApplicationId);

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

			return (com.liferay.oauth.model.OAuthApplication)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth.model.OAuthApplication updateLogo(
			HttpPrincipal httpPrincipal, long oAuthApplicationId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthApplicationServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthApplicationId, inputStream);

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

			return (com.liferay.oauth.model.OAuthApplication)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth.model.OAuthApplication
			updateOAuthApplication(
				HttpPrincipal httpPrincipal, long oAuthApplicationId,
				String name, String description, boolean shareableAccessToken,
				String callbackURI, String websiteURL,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthApplicationServiceUtil.class, "updateOAuthApplication",
				_updateOAuthApplicationParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthApplicationId, name, description,
				shareableAccessToken, callbackURI, websiteURL, serviceContext);

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

			return (com.liferay.oauth.model.OAuthApplication)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuthApplicationServiceHttp.class);

	private static final Class<?>[] _addOAuthApplicationParameterTypes0 =
		new Class[] {
			String.class, String.class, int.class, boolean.class, String.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLogoParameterTypes1 = new Class[] {
		long.class
	};
	private static final Class<?>[] _deleteOAuthApplicationParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _updateLogoParameterTypes3 = new Class[] {
		long.class, java.io.InputStream.class
	};
	private static final Class<?>[] _updateOAuthApplicationParameterTypes4 =
		new Class[] {
			long.class, String.class, String.class, boolean.class, String.class,
			String.class, com.liferay.portal.kernel.service.ServiceContext.class
		};

}