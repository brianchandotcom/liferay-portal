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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Supritha Sundaram
 */
public class WeDeployAuthAppLocalServiceImpl
	extends WeDeployAuthAppLocalServiceBaseImpl {
	
	public WeDeployAuthApp addWeDeployAuthApp(
			long userId, String name, long companyId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date date = new Date();

		long weDeployAuthAppId = counterLocalService.increment();

		WeDeployAuthApp weDeployAuthApp =
			weDeployAuthAppPersistence.create(weDeployAuthAppId);

		weDeployAuthApp.setCompanyId(user.getCompanyId());
		weDeployAuthApp.setUserId(user.getUserId());
		weDeployAuthApp.setUserName(user.getFullName());
		weDeployAuthApp.setCreateDate(serviceContext.getCreateDate(date));
		weDeployAuthApp.setModifiedDate(serviceContext.getModifiedDate(date));
		weDeployAuthApp.setName(name);

		String clientId = PortalUUIDUtil.generate();

		weDeployAuthApp.setClientId(clientId);

		weDeployAuthApp.setClientSecret(randomizeToken(clientId));

		weDeployAuthAppPersistence.update(weDeployAuthApp);

		// Resources

		resourceLocalService.addModelResources(weDeployAuthApp, serviceContext);

		return weDeployAuthApp;
	}

	private String randomizeToken(String token) {
		return DigesterUtil.digestHex(
			Digester.MD5, token, PwdGenerator.getPassword());
	}

}