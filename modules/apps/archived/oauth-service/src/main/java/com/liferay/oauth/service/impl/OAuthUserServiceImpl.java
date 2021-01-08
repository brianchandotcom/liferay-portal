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
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.base.OAuthUserServiceBaseImpl;
import com.liferay.oauth.service.permission.OAuthUserPermission;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthActionKeys;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	property = {
		"json.web.service.context.name=oauth",
		"json.web.service.context.path=OAuthUser"
	},
	service = AopService.class
)
public class OAuthUserServiceImpl extends OAuthUserServiceBaseImpl {

	@Override
	public OAuthUser addOAuthUser(
			String consumerKey, ServiceContext serviceContext)
		throws PortalException {

		OAuthApplication oAuthApplication =
			oAuthApplicationPersistence.findByConsumerKey(consumerKey);

		User user = getUser();

		OAuthUser oAuthUser = oAuthUserPersistence.fetchByU_OAI(
			user.getUserId(), oAuthApplication.getOAuthApplicationId());

		if (oAuthUser != null) {
			return oAuthUser;
		}

		String accessToken = _oAuth.randomizeToken(
			oAuthApplication.getConsumerKey());

		String accessSecret = _oAuth.randomizeToken(
			consumerKey.concat(accessToken));

		return oAuthUserLocalService.addOAuthUser(
			user.getUserId(), oAuthApplication.getOAuthApplicationId(),
			accessToken, accessSecret, serviceContext);
	}

	@Override
	public OAuthUser deleteOAuthUser(long oAuthApplicationId)
		throws PortalException {

		OAuthUser oAuthUser = oAuthUserPersistence.findByU_OAI(
			getUserId(), oAuthApplicationId);

		OAuthUserPermission.check(
			getPermissionChecker(), oAuthUser, OAuthActionKeys.DELETE);

		return oAuthUserLocalService.deleteOAuthUser(
			getUserId(), oAuthApplicationId);
	}

	@Reference
	private OAuth _oAuth;

}