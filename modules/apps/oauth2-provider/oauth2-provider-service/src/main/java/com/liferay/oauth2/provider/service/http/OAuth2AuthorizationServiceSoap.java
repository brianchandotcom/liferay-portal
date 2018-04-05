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

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link OAuth2AuthorizationServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.oauth2.provider.model.OAuth2Authorization}, that is translated to a
 * {@link com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationServiceHttp
 * @see com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap
 * @see OAuth2AuthorizationServiceUtil
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationServiceSoap {
	public static com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap[] getApplicationOAuth2Authorizations(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> returnValue =
				OAuth2AuthorizationServiceUtil.getApplicationOAuth2Authorizations(oAuth2ApplicationId,
					start, end, orderByComparator);

			return com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getApplicationOAuth2AuthorizationsCount(
		long oAuth2ApplicationId) throws RemoteException {
		try {
			int returnValue = OAuth2AuthorizationServiceUtil.getApplicationOAuth2AuthorizationsCount(oAuth2ApplicationId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap[] getUserOAuth2Authorizations(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> returnValue =
				OAuth2AuthorizationServiceUtil.getUserOAuth2Authorizations(start,
					end, orderByComparator);

			return com.liferay.oauth2.provider.model.OAuth2AuthorizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getUserOAuth2AuthorizationsCount()
		throws RemoteException {
		try {
			int returnValue = OAuth2AuthorizationServiceUtil.getUserOAuth2AuthorizationsCount();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws RemoteException {
		try {
			OAuth2AuthorizationServiceUtil.revokeOAuth2Authorization(oAuth2AuthorizationId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OAuth2AuthorizationServiceSoap.class);
}