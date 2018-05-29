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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthUserService}.
 *
 * @author Ivica Cardic
 * @see OAuthUserService
 * @generated
 */
@ProviderType
public class OAuthUserServiceWrapper implements OAuthUserService,
	ServiceWrapper<OAuthUserService> {
	public OAuthUserServiceWrapper(OAuthUserService oAuthUserService) {
		_oAuthUserService = oAuthUserService;
	}

	@Override
	public com.liferay.oauth.model.OAuthUser addOAuthUser(String consumerKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserService.addOAuthUser(consumerKey, serviceContext);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserService.deleteOAuthUser(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthUserService.getOSGiServiceIdentifier();
	}

	@Override
	public OAuthUserService getWrappedService() {
		return _oAuthUserService;
	}

	@Override
	public void setWrappedService(OAuthUserService oAuthUserService) {
		_oAuthUserService = oAuthUserService;
	}

	private OAuthUserService _oAuthUserService;
}