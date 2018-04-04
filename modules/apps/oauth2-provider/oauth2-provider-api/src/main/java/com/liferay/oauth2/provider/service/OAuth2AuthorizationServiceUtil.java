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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for OAuth2Authorization. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationService
 * @see com.liferay.oauth2.provider.service.base.OAuth2AuthorizationServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2AuthorizationServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2AuthorizationServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2Authorization> getApplicationOAuth2Authorizations(
		long oAuth2ApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getApplicationOAuth2Authorizations(oAuth2ApplicationId,
			start, end, orderByComparator);
	}

	public static int getApplicationOAuth2AuthorizationsCount(
		long oAuth2ApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getApplicationOAuth2AuthorizationsCount(oAuth2ApplicationId);
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
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Authorization> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserOAuth2Authorizations(start, end, orderByComparator);
	}

	public static int getUserOAuth2AuthorizationsCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserOAuth2AuthorizationsCount();
	}

	public static void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().revokeOAuth2Authorization(oAuth2AuthorizationId);
	}

	public static OAuth2AuthorizationService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2AuthorizationService, OAuth2AuthorizationService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuth2AuthorizationService.class);

		ServiceTracker<OAuth2AuthorizationService, OAuth2AuthorizationService> serviceTracker =
			new ServiceTracker<OAuth2AuthorizationService, OAuth2AuthorizationService>(bundle.getBundleContext(),
				OAuth2AuthorizationService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}