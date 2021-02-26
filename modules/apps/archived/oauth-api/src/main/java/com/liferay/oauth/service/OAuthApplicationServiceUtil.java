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

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

/**
 * Provides the remote service utility for OAuthApplication. This utility wraps
 * <code>com.liferay.oauth.service.impl.OAuthApplicationServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationService
 * @generated
 */
public class OAuthApplicationServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.service.impl.OAuthApplicationServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthApplication addOAuthApplication(
			String name, String description, int accessLevel,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOAuthApplication(
			name, description, accessLevel, shareableAccessToken, callbackURI,
			websiteURL, serviceContext);
	}

	public static void deleteLogo(long oAuthApplicationId)
		throws PortalException {

		getService().deleteLogo(oAuthApplicationId);
	}

	public static OAuthApplication deleteOAuthApplication(
			long oAuthApplicationId)
		throws PortalException {

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

	public static OAuthApplication updateLogo(
			long oAuthApplicationId, InputStream inputStream)
		throws PortalException {

		return getService().updateLogo(oAuthApplicationId, inputStream);
	}

	public static OAuthApplication updateOAuthApplication(
			long oAuthApplicationId, String name, String description,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateOAuthApplication(
			oAuthApplicationId, name, description, shareableAccessToken,
			callbackURI, websiteURL, serviceContext);
	}

	public static OAuthApplicationService getService() {
		return _service;
	}

	private static volatile OAuthApplicationService _service;

}