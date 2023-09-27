/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.http;

import com.liferay.oauth2.provider.service.OAuth2ApplicationServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OAuth2ApplicationServiceUtil</code> service
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
public class OAuth2ApplicationServiceHttp {

	public static com.liferay.oauth2.provider.model.OAuth2Application
			addOAuth2Application(
				HttpPrincipal httpPrincipal,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientAuthenticationMethod, long clientCredentialUserId,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String jwks,
				String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList, boolean rememberDevice,
				java.util.List<String> scopeAliasesList,
				boolean trustedApplication,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "addOAuth2Application",
				_addOAuth2ApplicationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, allowedGrantTypesList, clientAuthenticationMethod,
				clientCredentialUserId, clientId, clientProfile, clientSecret,
				description, featuresList, homePageURL, iconFileEntryId, jwks,
				name, privacyPolicyURL, redirectURIsList, rememberDevice,
				scopeAliasesList, trustedApplication, serviceContext);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			deleteOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "deleteOAuth2Application",
				_deleteOAuth2ApplicationParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			fetchOAuth2Application(
				HttpPrincipal httpPrincipal, long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "fetchOAuth2Application",
				_fetchOAuth2ApplicationParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, clientId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "getOAuth2Application",
				_getOAuth2ApplicationParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(
				HttpPrincipal httpPrincipal, long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "getOAuth2Application",
				_getOAuth2ApplicationParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, clientId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2Application>
			getOAuth2Applications(
				HttpPrincipal httpPrincipal, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2Application>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "getOAuth2Applications",
				_getOAuth2ApplicationsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth2.provider.model.OAuth2Application>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getOAuth2ApplicationsCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class,
				"getOAuth2ApplicationsCount",
				_getOAuth2ApplicationsCountParameterTypes6);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateIcon(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateIcon",
				_updateIconParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, inputStream);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				long oAuth2ApplicationScopeAliasesId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientAuthenticationMethod, long clientCredentialUserId,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String jwks,
				String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList, boolean rememberDevice,
				boolean trustedApplication)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateOAuth2Application",
				_updateOAuth2ApplicationParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, oAuth2ApplicationScopeAliasesId,
				allowedGrantTypesList, clientAuthenticationMethod,
				clientCredentialUserId, clientId, clientProfile, clientSecret,
				description, featuresList, homePageURL, iconFileEntryId, jwks,
				name, privacyPolicyURL, redirectURIsList, rememberDevice,
				trustedApplication);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateScopeAliases(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				java.util.List<String> scopeAliasesList)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateScopeAliases",
				_updateScopeAliasesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, scopeAliasesList);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationServiceHttp.class);

	private static final Class<?>[] _addOAuth2ApplicationParameterTypes0 =
		new Class[] {
			java.util.List.class, String.class, long.class, String.class,
			int.class, String.class, String.class, java.util.List.class,
			String.class, long.class, String.class, String.class, String.class,
			java.util.List.class, boolean.class, java.util.List.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteOAuth2ApplicationParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchOAuth2ApplicationParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getOAuth2ApplicationParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getOAuth2ApplicationParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getOAuth2ApplicationsParameterTypes5 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOAuth2ApplicationsCountParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _updateIconParameterTypes7 = new Class[] {
		long.class, java.io.InputStream.class
	};
	private static final Class<?>[] _updateOAuth2ApplicationParameterTypes8 =
		new Class[] {
			long.class, long.class, java.util.List.class, String.class,
			long.class, String.class, int.class, String.class, String.class,
			java.util.List.class, String.class, long.class, String.class,
			String.class, String.class, java.util.List.class, boolean.class,
			boolean.class
		};
	private static final Class<?>[] _updateScopeAliasesParameterTypes9 =
		new Class[] {long.class, java.util.List.class};

}