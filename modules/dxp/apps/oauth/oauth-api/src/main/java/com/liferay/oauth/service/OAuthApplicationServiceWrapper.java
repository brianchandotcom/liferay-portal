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
 * Provides a wrapper for {@link OAuthApplicationService}.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationService
 * @generated
 */
@ProviderType
public class OAuthApplicationServiceWrapper implements OAuthApplicationService,
	ServiceWrapper<OAuthApplicationService> {
	public OAuthApplicationServiceWrapper(
		OAuthApplicationService oAuthApplicationService) {
		_oAuthApplicationService = oAuthApplicationService;
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication addOAuthApplication(
		String name, String description, int accessLevel,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.addOAuthApplication(name, description,
			accessLevel, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	@Override
	public void deleteLogo(long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_oAuthApplicationService.deleteLogo(oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication deleteOAuthApplication(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.deleteOAuthApplication(oAuthApplicationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthApplicationService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateLogo(
		long oAuthApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.updateLogo(oAuthApplicationId,
			inputStream);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateOAuthApplication(
		long oAuthApplicationId, String name, String description,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationService.updateOAuthApplication(oAuthApplicationId,
			name, description, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	@Override
	public OAuthApplicationService getWrappedService() {
		return _oAuthApplicationService;
	}

	@Override
	public void setWrappedService(
		OAuthApplicationService oAuthApplicationService) {
		_oAuthApplicationService = oAuthApplicationService;
	}

	private OAuthApplicationService _oAuthApplicationService;
}