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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityAchievement;
import com.liferay.portlet.social.service.base.SocialActivityAchievementLocalServiceBaseImpl;

import java.util.Date;
public class SocialActivityAchievementLocalServiceImpl
	extends SocialActivityAchievementLocalServiceBaseImpl {

	public void unlockAchievement(
			long groupId, long userId, SocialAchievement achievement)
		throws PortalException, SystemException {

		boolean firstUnlocker = false;

		int count = socialActivityAchievementPersistence.countByG_N(
			groupId, achievement.getName());

		if (count == 0) {
			firstUnlocker = true;
		}

		SocialActivityAchievement activityAchievement =
			socialActivityAchievementPersistence.fetchByG_N_UB(
				groupId, achievement.getName(), userId);

		if (activityAchievement != null) {
			return;
		}

		Group group = groupLocalService.getGroup(groupId);

		long activityAchievementId = counterLocalService.increment();

		activityAchievement = socialActivityAchievementPersistence.create(
			activityAchievementId);

		activityAchievement.setCompanyId(group.getCompanyId());
		activityAchievement.setGroupId(groupId);
		activityAchievement.setName(achievement.getName());
		activityAchievement.setUnlockDate(new Date());
		activityAchievement.setUnlockedBy(userId);
		activityAchievement.setFirstUnlock(firstUnlocker);

		socialActivityAchievementPersistence.update(activityAchievement, false);

		socialActivityStatsEntryLocalService.incrementUserAchievementsCounter(
			groupId, userId);
	}

}