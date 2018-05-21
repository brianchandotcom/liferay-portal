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

import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.base.OAuthUserLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
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

		oAuthUserPersistence.update(oAuthUser);

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
		OrderByComparator orderByComparator) {

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
		long userId, int start, int end, OrderByComparator orderByComparator) {

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

		oAuthUserPersistence.update(oAuthUser);

		return oAuthUser;
	}

	protected void validate(long oAuthApplicationId) throws PortalException {
		oAuthApplicationPersistence.findByPrimaryKey(oAuthApplicationId);
	}

}