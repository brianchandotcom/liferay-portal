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

package com.liferay.oauth2.provider.service;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for OAuth2Authorization. This utility wraps
 * <code>com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationService
 * @generated
 */
public class OAuth2AuthorizationServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<OAuth2Authorization> getApplicationOAuth2Authorizations(
			long oAuth2ApplicationId, int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		return getService().getApplicationOAuth2Authorizations(
			oAuth2ApplicationId, start, end, orderByComparator);
	}

	public static int getApplicationOAuth2AuthorizationsCount(
			long oAuth2ApplicationId)
		throws PortalException {

		return getService().getApplicationOAuth2AuthorizationsCount(
			oAuth2ApplicationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<OAuth2Authorization> getUserOAuth2Authorizations(
			int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		return getService().getUserOAuth2Authorizations(
			start, end, orderByComparator);
	}

	public static int getUserOAuth2AuthorizationsCount()
		throws PortalException {

		return getService().getUserOAuth2AuthorizationsCount();
	}

	public static void revokeAllOAuth2Authorizations(long oAuth2ApplicationId)
		throws PortalException {

		getService().revokeAllOAuth2Authorizations(oAuth2ApplicationId);
	}

	public static void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws PortalException {

		getService().revokeOAuth2Authorization(oAuth2AuthorizationId);
	}

	public static OAuth2AuthorizationService getService() {
		return _service;
	}

	public static void setService(OAuth2AuthorizationService service) {
		_service = service;
	}

	private static volatile OAuth2AuthorizationService _service;

}