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
 * Provides the remote service utility for OAuth2Token. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2TokenServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenService
 * @see com.liferay.oauth2.provider.service.base.OAuth2TokenServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2TokenServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2TokenServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2TokenServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link OAuth2TokenServiceUtil} to access the o auth2 token remote service.
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuth2Token(oAuth2TokenId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static OAuth2TokenService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2TokenService, OAuth2TokenService> _serviceTracker =
		ServiceTrackerFactory.open(OAuth2TokenService.class);
}