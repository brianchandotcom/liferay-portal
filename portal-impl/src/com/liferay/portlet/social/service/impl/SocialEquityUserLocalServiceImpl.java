/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialEquityUserLocalServiceImpl
	extends SocialEquityUserLocalServiceBaseImpl {

	public void deleteSocialEquityUser(User user) throws SystemException {
		socialEquityUserPersistence.removeByUserId(user.getUserId());
	}

	public SocialEquityValue getContributionEquity(long userId)
		throws SystemException {

		return getContributionEquity(userId, 0);
	}

	public SocialEquityValue getContributionEquity(long userId, long groupId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("contributionK"));
		projectionList.add(ProjectionFactoryUtil.sum("contributionB"));

		return getEquityValue(userId, groupId, projectionList);
	}

	public SocialEquityValue getParticipationEquity(long userId)
		throws SystemException {

		return getParticipationEquity(userId, 0);
	}

	public SocialEquityValue getParticipationEquity(long userId, long groupId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("participationK"));
		projectionList.add(ProjectionFactoryUtil.sum("participationB"));

		return getEquityValue(userId, groupId, projectionList);
	}

	public int getRank(long groupId, long userId) throws SystemException {
		SocialEquityUser equityUser = socialEquityUserPersistence.fetchByG_U(
			groupId, userId);

		if (equityUser == null) {
			return 0;
		}

		return equityUser.getRank();
	}

	public List<SocialEquityUser> getRankedEquityUsers(
			long groupId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return socialEquityUserPersistence.findByGroupRanked(
			groupId, start, end, orderByComparator);
	}

	public int getRankedEquityUsersCount(long groupId) throws SystemException {
		return socialEquityUserPersistence.countByGroupRanked(groupId);
	}

	protected SocialEquityValue getEquityValue(
			long userId, long groupId, ProjectionList projectionList)
		throws SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialEquityUser.class, PortalClassLoaderUtil.getClassLoader());

		dynamicQuery.setProjection(projectionList);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", userId));

		if (groupId > 0) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", groupId));
		}

		List<?> results = dynamicQuery(dynamicQuery);

		Object[] values = (Object[])results.get(0);

		SocialEquityValue socialEquityValue = null;

		if (values[0] != null) {
			socialEquityValue = new SocialEquityValue(
				(Double)values[0], (Double)values[1]);
		}
		else {
			socialEquityValue = new SocialEquityValue(0, 0);
		}

		return socialEquityValue;
	}

}