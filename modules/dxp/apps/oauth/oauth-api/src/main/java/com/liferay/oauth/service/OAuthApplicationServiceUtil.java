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
 * Provides the remote service utility for OAuthApplication. This utility wraps
 * {@link com.liferay.oauth.service.impl.OAuthApplicationServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationService
 * @see com.liferay.oauth.service.base.OAuthApplicationServiceBaseImpl
 * @see com.liferay.oauth.service.impl.OAuthApplicationServiceImpl
 * @generated
 */
@ProviderType
public class OAuthApplicationServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth.service.impl.OAuthApplicationServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.oauth.model.OAuthApplication addOAuthApplication(
		String name, String description, int accessLevel,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOAuthApplication(name, description, accessLevel,
			shareableAccessToken, callbackURI, websiteURL, serviceContext);
	}

	public static void deleteLogo(long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLogo(oAuthApplicationId);
	}

	public static com.liferay.oauth.model.OAuthApplication deleteOAuthApplication(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuthApplication(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.oauth.model.OAuthApplication updateLogo(
		long oAuthApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateLogo(oAuthApplicationId, inputStream);
	}

	public static com.liferay.oauth.model.OAuthApplication updateOAuthApplication(
		long oAuthApplicationId, String name, String description,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOAuthApplication(oAuthApplicationId, name,
			description, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	public static OAuthApplicationService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuthApplicationService, OAuthApplicationService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuthApplicationService.class);

		ServiceTracker<OAuthApplicationService, OAuthApplicationService> serviceTracker =
			new ServiceTracker<OAuthApplicationService, OAuthApplicationService>(bundle.getBundleContext(),
				OAuthApplicationService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}