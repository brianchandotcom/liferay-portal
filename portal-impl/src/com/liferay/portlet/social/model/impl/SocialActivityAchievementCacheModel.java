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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.social.model.SocialActivityAchievement;

import java.util.Date;

/**
 * The cache model class for representing SocialActivityAchievement in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievement
 * @generated
 */
public class SocialActivityAchievementCacheModel implements CacheModel<SocialActivityAchievement> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activityAchievementId=");
		sb.append(activityAchievementId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", unlockDate=");
		sb.append(unlockDate);
		sb.append(", unlockedBy=");
		sb.append(unlockedBy);
		sb.append(", firstUnlock=");
		sb.append(firstUnlock);
		sb.append("}");

		return sb.toString();
	}

	public SocialActivityAchievement toEntityModel() {
		SocialActivityAchievementImpl socialActivityAchievementImpl = new SocialActivityAchievementImpl();

		socialActivityAchievementImpl.setActivityAchievementId(activityAchievementId);
		socialActivityAchievementImpl.setGroupId(groupId);
		socialActivityAchievementImpl.setCompanyId(companyId);

		if (name == null) {
			socialActivityAchievementImpl.setName(StringPool.BLANK);
		}
		else {
			socialActivityAchievementImpl.setName(name);
		}

		if (unlockDate == Long.MIN_VALUE) {
			socialActivityAchievementImpl.setUnlockDate(null);
		}
		else {
			socialActivityAchievementImpl.setUnlockDate(new Date(unlockDate));
		}

		socialActivityAchievementImpl.setUnlockedBy(unlockedBy);
		socialActivityAchievementImpl.setFirstUnlock(firstUnlock);

		socialActivityAchievementImpl.resetOriginalValues();

		return socialActivityAchievementImpl;
	}

	public long activityAchievementId;
	public long groupId;
	public long companyId;
	public String name;
	public long unlockDate;
	public long unlockedBy;
	public boolean firstUnlock;
}