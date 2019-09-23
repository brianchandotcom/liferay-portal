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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WeDeployAuthAppService}.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppService
 * @generated
 */
public class WeDeployAuthAppServiceWrapper
	implements ServiceWrapper<WeDeployAuthAppService>, WeDeployAuthAppService {

	public WeDeployAuthAppServiceWrapper(
		WeDeployAuthAppService weDeployAuthAppService) {

		_weDeployAuthAppService = weDeployAuthAppService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WeDeployAuthAppServiceUtil} to access the we deploy auth app remote service. Add custom service methods to <code>com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp
			addWeDeployAuthApp(
				String name, String redirectURI,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthAppService.addWeDeployAuthApp(
			name, redirectURI, serviceContext);
	}

	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp
			deleteWeDeployAuthApp(long weDeployAuthAppId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthAppService.deleteWeDeployAuthApp(weDeployAuthAppId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _weDeployAuthAppService.getOSGiServiceIdentifier();
	}

	@Override
	public WeDeployAuthAppService getWrappedService() {
		return _weDeployAuthAppService;
	}

	@Override
	public void setWrappedService(
		WeDeployAuthAppService weDeployAuthAppService) {

		_weDeployAuthAppService = weDeployAuthAppService;
	}

	private WeDeployAuthAppService _weDeployAuthAppService;

}