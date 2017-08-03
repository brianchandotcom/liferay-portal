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