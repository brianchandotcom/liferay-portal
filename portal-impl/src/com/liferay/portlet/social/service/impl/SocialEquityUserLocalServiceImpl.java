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
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityUserLocalServiceBaseImpl;

import java.util.List;

/**
 * The local service for dealing with user equity scores and ranking.
 *
 * <p>
 * This service provides methods for retrieving the participation and
 * contribution scores of users and provides some methods for dealing with user
 * ranking.
 * </p>
 *
 * @author Zsolt Berentey
 */
public class SocialEquityUserLocalServiceImpl
	extends SocialEquityUserLocalServiceBaseImpl {

	/**
	 * Removes ranking for the user with respect to all groups.
	 *
	 * <p>
	 * This method is called by the portal when a user is deactivated.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void clearRanks(long userId) throws SystemException {
		List<SocialEquityUser> equityUsers =
			socialEquityUserPersistence.findByUserId(userId);

		for (SocialEquityUser equityUser : equityUsers) {
			equityUser.setRank(0);

			socialEquityUserPersistence.update(equityUser, false);
		}
	}

	/**
	 * Removes the database rows for the user from the
	 * <code>SocialEquityUser</code> table.
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteSocialEquityUser(long userId) throws SystemException {
		socialEquityUserPersistence.removeByUserId(userId);
	}

	/**
	 * Returns the contribution equity score for the user.
	 *
	 * <p>
	 * This method should only be used if social equity is turned on for only
	 * one group, as it returns the contribution score for the first group it
	 * finds. The first group found can be different from one execution to the
	 * next.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @return the contribution equity score
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityValue getContributionEquity(long userId)
		throws SystemException {

		return getContributionEquity(userId, 0);
	}

	/**
	 * Returns the contribution equity score of the user with respect to the
	 * group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the contribution equity score
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityValue getContributionEquity(long userId, long groupId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("contributionK"));
		projectionList.add(ProjectionFactoryUtil.sum("contributionB"));

		return getEquityValue(userId, groupId, projectionList);
	}

	/**
	 * Returns the participation equity score for the user.
	 *
	 * <p>
	 * This method should only be used if social equity is turned on for only
	 * one group, as it returns the participation score for the first group it
	 * finds. The first group found can be different from one execution to the
	 * next.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @return the participation equity score
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityValue getParticipationEquity(long userId)
		throws SystemException {

		return getParticipationEquity(userId, 0);
	}

	/**
	 * Returns the participation equity score of the user with respect to the
	 * group.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @return the participation equity score
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquityValue getParticipationEquity(long userId, long groupId)
		throws SystemException {

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.sum("participationK"));
		projectionList.add(ProjectionFactoryUtil.sum("participationB"));

		return getEquityValue(userId, groupId, projectionList);
	}

	/**
	 * Returns the rank of the user in the group based on the user's personal
	 * equity.
	 *
	 * @param  groupId the primary key of the group
	 * @param  userId the primary key of the user
	 * @return the rank for the user in the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getRank(long groupId, long userId) throws SystemException {
		SocialEquityUser equityUser = socialEquityUserPersistence.fetchByG_U(
			groupId, userId);

		if (equityUser == null) {
			return 0;
		}

		return equityUser.getRank();
	}

	/**
	 * Returns an ordered range of all the social equity users in the group
	 * with rankings greater than zero. It is strongly suggested to use {@link
	 * com.liferay.portlet.social.util.comparator.SocialEquityUserRankComparator}
	 * as the ordering comparator.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the
	 * full result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @param  orderByComparator the comparator to order the social equity
	 *         users, such as {@link
	 *         com.liferay.portlet.social.util.comparator.SocialEquityUserRankComparator}
	 *         (optionally <code>null</code>)
	 * @return the ordered range of the social equity users
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquityUser> getRankedEquityUsers(
			long groupId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return socialEquityUserPersistence.findByGroupRanked(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of the social equity users in the group with rankings
	 * greater than zero.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of social equity users with rankings greater than
	 *         zero
	 * @throws SystemException if a system exception occurred
	 */
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