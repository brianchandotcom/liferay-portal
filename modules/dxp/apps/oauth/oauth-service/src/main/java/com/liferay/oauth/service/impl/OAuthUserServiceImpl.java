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
import com.liferay.oauth.util.OAuthActionKeys;
import com.liferay.oauth.util.OAuthUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
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

		String accessToken = OAuthUtil.randomizeToken(
			oAuthApplication.getConsumerKey());

		String accessSecret = OAuthUtil.randomizeToken(
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

}