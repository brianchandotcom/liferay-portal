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

import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.base.OAuthUserLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(
	property = "model.class.name=com.liferay.oauth.model.OAuthUser",
	service = AopService.class
)
public class OAuthUserLocalServiceImpl extends OAuthUserLocalServiceBaseImpl {

	@Override
	public OAuthUser addOAuthUser(
			long userId, long oAuthApplicationId, String accessToken,
			String accessSecret, ServiceContext serviceContext)
		throws PortalException {

		// OAuth user

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		validate(oAuthApplicationId);

		long oAuthUserId = counterLocalService.increment();

		OAuthUser oAuthUser = oAuthUserPersistence.create(oAuthUserId);

		oAuthUser.setCompanyId(user.getCompanyId());
		oAuthUser.setUserId(user.getUserId());
		oAuthUser.setUserName(user.getFullName());
		oAuthUser.setCreateDate(serviceContext.getCreateDate(now));
		oAuthUser.setModifiedDate(serviceContext.getModifiedDate(now));
		oAuthUser.setOAuthApplicationId(oAuthApplicationId);
		oAuthUser.setAccessToken(accessToken);
		oAuthUser.setAccessSecret(accessSecret);

		oAuthUser = oAuthUserPersistence.update(oAuthUser);

		// Resources

		resourceLocalService.addModelResources(oAuthUser, serviceContext);

		return oAuthUser;
	}

	@Override
	public OAuthUser deleteOAuthUser(long userId, long oAuthApplicationId)
		throws PortalException {

		OAuthUser oAuthUser = oAuthUserPersistence.findByU_OAI(
			userId, oAuthApplicationId);

		return deleteOAuthUser(oAuthUser);
	}

	@Override
	public OAuthUser deleteOAuthUser(OAuthUser oAuthUser)
		throws PortalException {

		// OAuth user

		oAuthUserPersistence.remove(oAuthUser);

		// Resources

		resourceLocalService.deleteResource(
			oAuthUser, ResourceConstants.SCOPE_INDIVIDUAL);

		return oAuthUser;
	}

	@Override
	public OAuthUser fetchOAuthUser(long userId, long oAuthApplicationId) {
		return oAuthUserPersistence.fetchByU_OAI(userId, oAuthApplicationId);
	}

	@Override
	public OAuthUser fetchOAuthUser(String accessToken) {
		return oAuthUserPersistence.fetchByAccessToken(accessToken);
	}

	@Override
	public List<OAuthUser> getOAuthApplicationOAuthUsers(
		long oAuthApplicationId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return oAuthUserPersistence.findByOAuthApplicationId(
			oAuthApplicationId, start, end, orderByComparator);
	}

	@Override
	public int getOAuthApplicationOAuthUsersCount(long oAuthApplicationId) {
		return oAuthUserPersistence.countByOAuthApplicationId(
			oAuthApplicationId);
	}

	@Override
	public OAuthUser getOAuthUser(long userId, long oAuthApplicationId)
		throws PortalException {

		return oAuthUserPersistence.findByU_OAI(userId, oAuthApplicationId);
	}

	@Override
	public OAuthUser getOAuthUser(String accessToken) throws PortalException {
		return oAuthUserPersistence.findByAccessToken(accessToken);
	}

	@Override
	public List<OAuthUser> getUserOAuthUsers(
		long userId, int start, int end,
		OrderByComparator<OAuthUser> orderByComparator) {

		return oAuthUserPersistence.findByUserId(
			userId, start, end, orderByComparator);
	}

	@Override
	public int getUserOAuthUsersCount(long userId) {
		return oAuthUserPersistence.countByUserId(userId);
	}

	@Override
	public OAuthUser updateOAuthUser(
			long userId, long oAuthApplicationId, String accessToken,
			String accessSecret, ServiceContext serviceContext)
		throws PortalException {

		OAuthUser oAuthUser = oAuthUserPersistence.findByU_OAI(
			userId, oAuthApplicationId);

		oAuthUser.setAccessToken(accessToken);
		oAuthUser.setAccessSecret(accessSecret);

		return oAuthUserPersistence.update(oAuthUser);
	}

	protected void validate(long oAuthApplicationId) throws PortalException {
		oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);
	}

}