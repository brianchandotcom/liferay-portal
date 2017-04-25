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

package com.liferay.portal.security.wedeploy.auth.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthTokenConstants;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;
import com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthTokenLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Supritha Sundaram
 */
public class WeDeployAuthTokenLocalServiceImpl
	extends WeDeployAuthTokenLocalServiceBaseImpl {

	public WeDeployAuthToken addWeDeployAuthToken(
			long userId, String token, int type,
			long companyId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date date = new Date();

		long weDeployAuthTokenId = counterLocalService.increment();

		WeDeployAuthToken weDeployAuthToken =
			weDeployAuthTokenPersistence.create(weDeployAuthTokenId);

		weDeployAuthToken.setCompanyId(user.getCompanyId());
		weDeployAuthToken.setToken(token);
		weDeployAuthToken.setUserId(user.getUserId());
		weDeployAuthToken.setUserName(user.getFullName());
		weDeployAuthToken.setCreateDate(serviceContext.getCreateDate(date));
		weDeployAuthToken.setModifiedDate(serviceContext.getModifiedDate(date));
		weDeployAuthToken.setType(type);

		weDeployAuthTokenPersistence.update(weDeployAuthToken);

		// Resources

		resourceLocalService.addModelResources(
			weDeployAuthToken, serviceContext);

		return weDeployAuthToken;
	}

	public WeDeployAuthToken addWeDeployAuthorizationCode(
			String clientId, long userId, long companyId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String token = randomizeToken(clientId);

		WeDeployAuthToken weDeployAuthToken= addWeDeployAuthToken(
			userId, token, WeDeployAuthTokenConstants.TOKEN_TYPE_REQUEST,
			companyId, serviceContext);

		return weDeployAuthToken;
	}

	public WeDeployAuthToken addWeDeployAccessToken(
			String clientId, String clientSecret, String authorizationCode,
			long userId, long companyId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String token = randomizeToken(clientId.concat(authorizationCode));

		WeDeployAuthToken weDeployAuthToken= addWeDeployAuthToken(
			userId, token, WeDeployAuthTokenConstants.TOKEN_TYPE_ACCESS,
			companyId, new ServiceContext());

		return weDeployAuthToken;
	}

	private static String randomizeToken(String token) {
		return DigesterUtil.digestHex(
			Digester.MD5, token, PwdGenerator.getPassword());
	}

}