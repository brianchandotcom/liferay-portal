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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for OAuth2Authorization. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationLocalService
 * @see com.liferay.oauth2.provider.service.base.OAuth2AuthorizationLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getOAuth2Authorizations(
		long companyId, long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator) {
		return getService()
				   .getOAuth2Authorizations(companyId, applicationId, start,
			end, orderByComparator);
	}

	public static int getOAuth2AuthorizationsCount(long companyId,
		long applicationId) {
		return getService()
				   .getOAuth2AuthorizationsCount(companyId, applicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getUserOAuth2Authorizations(
		long companyId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator) {
		return getService()
				   .getUserOAuth2Authorizations(companyId, userId, start, end,
			orderByComparator);
	}

	public static int getUserOAuth2AuthorizationsCount(long companyId,
		long userId) {
		return getService().getUserOAuth2AuthorizationsCount(companyId, userId);
	}

	public static boolean revokeOAuth2Authorization(long oAuth2AccessTokenId,
		long oAuth2RefreshTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .revokeOAuth2Authorization(oAuth2AccessTokenId,
			oAuth2RefreshTokenId);
	}

	public static OAuth2AuthorizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2AuthorizationLocalService, OAuth2AuthorizationLocalService> _serviceTracker =
		ServiceTrackerFactory.open(OAuth2AuthorizationLocalService.class);
}