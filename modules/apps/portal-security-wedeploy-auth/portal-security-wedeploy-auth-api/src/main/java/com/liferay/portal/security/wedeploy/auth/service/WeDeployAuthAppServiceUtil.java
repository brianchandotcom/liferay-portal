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

package com.liferay.portal.security.wedeploy.auth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for WeDeployAuthApp. This utility wraps
 * {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppService
 * @see com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppServiceBaseImpl
 * @see com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl
 * @generated
 */
@ProviderType
public class WeDeployAuthAppServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp addWeDeployAuthApp(
		java.lang.String name, java.lang.String redirectURI,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addWeDeployAuthApp(name, redirectURI, serviceContext);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp deleteWeDeployAuthApp(
		long weDeployAuthAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWeDeployAuthApp(weDeployAuthAppId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static WeDeployAuthAppService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WeDeployAuthAppService, WeDeployAuthAppService> _serviceTracker =
		ServiceTrackerFactory.open(WeDeployAuthAppService.class);
}