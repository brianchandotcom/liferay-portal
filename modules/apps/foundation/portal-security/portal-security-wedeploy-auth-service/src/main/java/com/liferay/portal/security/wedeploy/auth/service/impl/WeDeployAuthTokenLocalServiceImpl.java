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

	@Override
	public WeDeployAuthToken addAccessWeDeployAuthToken(
			long userId, String clientId, String clientSecret,
			String authorizationToken, int type, ServiceContext serviceContext)
		throws PortalException {

		validate(clientId, clientSecret, authorizationToken, type);

		String token = DigesterUtil.digestHex(
			Digester.MD5, clientId.concat(authorizationToken),
			PwdGenerator.getPassword());

		return addWeDeployAuthToken(
			userId, clientId, token, WeDeployAuthTokenConstants.TYPE_ACCESS,
			new ServiceContext());
	}

	@Override
	public WeDeployAuthToken addAuthorizationWeDeployAuthToken(
			long userId, String clientId, ServiceContext serviceContext)
		throws PortalException {

		String token = DigesterUtil.digestHex(
			Digester.MD5, clientId, PwdGenerator.getPassword());

		return addWeDeployAuthToken(
			userId, clientId, token,
			WeDeployAuthTokenConstants.TYPE_AUTHORIZATION,
			new ServiceContext());
	}

	@Override
	public WeDeployAuthToken addWeDeployAuthToken(
			long userId, String clientId, String token, int type,
			ServiceContext serviceContext)
		throws PortalException {

		// WeDeploy auth token

		User user = userLocalService.fetchUserById(userId);
		Date date = new Date();

		long weDeployAuthTokenId = counterLocalService.increment();

		WeDeployAuthToken weDeployAuthToken =
			weDeployAuthTokenPersistence.create(weDeployAuthTokenId);

		weDeployAuthToken.setCompanyId(user.getCompanyId());
		weDeployAuthToken.setUserId(user.getUserId());
		weDeployAuthToken.setUserName(user.getFullName());
		weDeployAuthToken.setCreateDate(serviceContext.getCreateDate(date));
		weDeployAuthToken.setModifiedDate(serviceContext.getModifiedDate(date));
		weDeployAuthToken.setClientId(clientId);
		weDeployAuthToken.setToken(token);
		weDeployAuthToken.setType(type);

		weDeployAuthTokenPersistence.update(weDeployAuthToken);

		// Resources

		resourceLocalService.addModelResources(
			weDeployAuthToken, serviceContext);

		return weDeployAuthToken;
	}

	protected void validate(
			String clientId, String clientSecret, String authorizationToken,
			int type)
		throws PortalException {

		weDeployAuthAppPersistence.findByCI_CS(clientId, clientSecret);

		weDeployAuthTokenPersistence.findByCI_T_T(
			clientId, authorizationToken, type);
	}

}