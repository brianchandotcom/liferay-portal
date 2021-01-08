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

package com.liferay.oauth.service.impl;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.service.base.OAuthApplicationServiceBaseImpl;
import com.liferay.oauth.service.permission.OAuthApplicationPermission;
import com.liferay.oauth.service.permission.OAuthPermission;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthActionKeys;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=oauth",
		"json.web.service.context.path=OAuthApplication"
	},
	service = AopService.class
)
public class OAuthApplicationServiceImpl
	extends OAuthApplicationServiceBaseImpl {

	@Override
	public OAuthApplication addOAuthApplication(
			String name, String description, int accessLevel,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException {

		OAuthPermission.check(
			getPermissionChecker(), OAuthActionKeys.ADD_APPLICATION);

		String consumerKey = serviceContext.getUuid();

		if (Validator.isNull(consumerKey)) {
			consumerKey = PortalUUIDUtil.generate();
		}

		return oAuthApplicationLocalService.addOAuthApplication(
			getUserId(), name, description, _oAuth.randomizeToken(consumerKey),
			accessLevel, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	@Override
	public void deleteLogo(long oAuthApplicationId) throws PortalException {
		OAuthApplicationPermission.check(
			getPermissionChecker(), oAuthApplicationId, OAuthActionKeys.UPDATE);

		oAuthApplicationLocalService.deleteLogo(oAuthApplicationId);
	}

	@Override
	public OAuthApplication deleteOAuthApplication(long oAuthApplicationId)
		throws PortalException {

		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);

		OAuthApplicationPermission.check(
			getPermissionChecker(), oAuthApplication, OAuthActionKeys.DELETE);

		return oAuthApplicationLocalService.deleteOAuthApplication(
			oAuthApplication);
	}

	@Override
	public OAuthApplication updateLogo(
			long oAuthApplicationId, InputStream inputStream)
		throws PortalException {

		OAuthApplicationPermission.check(
			getPermissionChecker(), oAuthApplicationId, OAuthActionKeys.UPDATE);

		return oAuthApplicationLocalService.updateLogo(
			oAuthApplicationId, inputStream);
	}

	@Override
	public OAuthApplication updateOAuthApplication(
			long oAuthApplicationId, String name, String description,
			boolean shareableAccessToken, String callbackURI, String websiteURL,
			ServiceContext serviceContext)
		throws PortalException {

		OAuthApplicationPermission.check(
			getPermissionChecker(), oAuthApplicationId, OAuthActionKeys.UPDATE);

		return oAuthApplicationLocalService.updateOAuthApplication(
			oAuthApplicationId, name, description, shareableAccessToken,
			callbackURI, websiteURL, serviceContext);
	}

	@Reference
	private OAuth _oAuth;

}