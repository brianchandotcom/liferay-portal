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
		String consumerKey,
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
	public static String getOSGiServiceIdentifier() {
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