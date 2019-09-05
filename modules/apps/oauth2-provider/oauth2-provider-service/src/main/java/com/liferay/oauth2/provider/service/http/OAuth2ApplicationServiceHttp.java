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
 * @see OAuth2ApplicationServiceSoap
 * @generated
 */
public class OAuth2ApplicationServiceHttp {

	public static com.liferay.oauth2.provider.model.OAuth2Application
			addOAuth2Application(
				HttpPrincipal httpPrincipal,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				long clientCredentialUserId, String clientId, int clientProfile,
				String clientSecret, String description,
				java.util.List<String> featuresList, String homePageURL,
				long iconFileEntryId, String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				java.util.List<String> scopeAliasesList,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "addOAuth2Application",
				_addOAuth2ApplicationParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, allowedGrantTypesList, clientCredentialUserId,
				clientId, clientProfile, clientSecret, description,
				featuresList, homePageURL, iconFileEntryId, name,
				privacyPolicyURL, redirectURIsList, scopeAliasesList,
				serviceContext);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			addOAuth2Application(
				HttpPrincipal httpPrincipal,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String name,
				String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				java.util.List<String> scopeAliasesList,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "addOAuth2Application",
				_addOAuth2ApplicationParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, allowedGrantTypesList, clientId, clientProfile,
				clientSecret, description, featuresList, homePageURL,
				iconFileEntryId, name, privacyPolicyURL, redirectURIsList,
				scopeAliasesList, serviceContext);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			deleteOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "deleteOAuth2Application",
				_deleteOAuth2ApplicationParameterTypes2);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			fetchOAuth2Application(
				HttpPrincipal httpPrincipal, long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "fetchOAuth2Application",
				_fetchOAuth2ApplicationParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, clientId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "getOAuth2Application",
				_getOAuth2ApplicationParameterTypes4);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			getOAuth2Application(
				HttpPrincipal httpPrincipal, long companyId, String clientId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "getOAuth2Application",
				_getOAuth2ApplicationParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, clientId);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getOAuth2ApplicationsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.oauth2.provider.model.OAuth2Application>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getOAuth2ApplicationsCount(
		HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class,
				"getOAuth2ApplicationsCount",
				_getOAuth2ApplicationsCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
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

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateIcon(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateIcon",
				_updateIconParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, inputStream);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				long clientCredentialUserId, String clientId, int clientProfile,
				String clientSecret, String description,
				java.util.List<String> featuresList, String homePageURL,
				long iconFileEntryId, String name, String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				long auth2ApplicationScopeAliasesId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateOAuth2Application",
				_updateOAuth2ApplicationParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, allowedGrantTypesList,
				clientCredentialUserId, clientId, clientProfile, clientSecret,
				description, featuresList, homePageURL, iconFileEntryId, name,
				privacyPolicyURL, redirectURIsList,
				auth2ApplicationScopeAliasesId, serviceContext);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2Application
			updateOAuth2Application(
				HttpPrincipal httpPrincipal, long oAuth2ApplicationId,
				java.util.List<com.liferay.oauth2.provider.constants.GrantType>
					allowedGrantTypesList,
				String clientId, int clientProfile, String clientSecret,
				String description, java.util.List<String> featuresList,
				String homePageURL, long iconFileEntryId, String name,
				String privacyPolicyURL,
				java.util.List<String> redirectURIsList,
				long auth2ApplicationScopeAliasesId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuth2ApplicationServiceUtil.class, "updateOAuth2Application",
				_updateOAuth2ApplicationParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, allowedGrantTypesList, clientId,
				clientProfile, clientSecret, description, featuresList,
				homePageURL, iconFileEntryId, name, privacyPolicyURL,
				redirectURIsList, auth2ApplicationScopeAliasesId,
				serviceContext);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_updateScopeAliasesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuth2ApplicationId, scopeAliasesList);

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

			return (com.liferay.oauth2.provider.model.OAuth2Application)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuth2ApplicationServiceHttp.class);

	private static final Class<?>[] _addOAuth2ApplicationParameterTypes0 =
		new Class[] {
			java.util.List.class, long.class, String.class, int.class,
			String.class, String.class, java.util.List.class, String.class,
			long.class, String.class, String.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addOAuth2ApplicationParameterTypes1 =
		new Class[] {
			java.util.List.class, String.class, int.class, String.class,
			String.class, java.util.List.class, String.class, long.class,
			String.class, String.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteOAuth2ApplicationParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchOAuth2ApplicationParameterTypes3 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getOAuth2ApplicationParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _getOAuth2ApplicationParameterTypes5 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getOAuth2ApplicationsParameterTypes6 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getOAuth2ApplicationsCountParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _updateIconParameterTypes8 = new Class[] {
		long.class, java.io.InputStream.class
	};
	private static final Class<?>[] _updateOAuth2ApplicationParameterTypes9 =
		new Class[] {
			long.class, java.util.List.class, long.class, String.class,
			int.class, String.class, String.class, java.util.List.class,
			String.class, long.class, String.class, String.class,
			java.util.List.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateOAuth2ApplicationParameterTypes10 =
		new Class[] {
			long.class, java.util.List.class, String.class, int.class,
			String.class, String.class, java.util.List.class, String.class,
			long.class, String.class, String.class, java.util.List.class,
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateScopeAliasesParameterTypes11 =
		new Class[] {long.class, java.util.List.class};

}