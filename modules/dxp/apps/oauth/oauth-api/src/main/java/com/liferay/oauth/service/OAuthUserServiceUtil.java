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

package com.liferay.oauth.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for OAuthUser. This utility wraps
 * {@link com.liferay.oauth.service.impl.OAuthUserServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ivica Cardic
 * @see OAuthUserService
 * @see com.liferay.oauth.service.base.OAuthUserServiceBaseImpl
 * @see com.liferay.oauth.service.impl.OAuthUserServiceImpl
 * @generated
 */
@ProviderType
public class OAuthUserServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth.service.impl.OAuthUserServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.oauth.model.OAuthUser addOAuthUser(
		java.lang.String consumerKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addOAuthUser(consumerKey, serviceContext);
	}

	public static com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuthUser(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static OAuthUserService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuthUserService, OAuthUserService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuthUserService.class);

		ServiceTracker<OAuthUserService, OAuthUserService> serviceTracker = new ServiceTracker<OAuthUserService, OAuthUserService>(bundle.getBundleContext(),
				OAuthUserService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}