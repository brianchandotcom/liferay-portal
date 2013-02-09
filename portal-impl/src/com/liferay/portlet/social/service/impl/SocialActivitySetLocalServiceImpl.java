/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.User;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.base.SocialActivitySetLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class SocialActivitySetLocalServiceImpl
		extends SocialActivitySetLocalServiceBaseImpl {

	public void addActivitySet(
			long userId, long groupId, String className, long classPK, int type,
			SocialActivity activity)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long createDate = activity.getCreateDate();

		long activitySetId = counterLocalService.increment();

		SocialActivitySet activitySet = socialActivitySetPersistence.create(
			activitySetId);

		activitySet.setGroupId(groupId);
		activitySet.setCompanyId(user.getCompanyId());
		activitySet.setUserId(userId);
		activitySet.setCreateDate(createDate);
		activitySet.setModifiedDate(createDate);
		activitySet.setClassName(className);
		activitySet.setClassPK(classPK);
		activitySet.setType(type);
		activitySet.setActivityCount(1);

		socialActivitySetLocalService.addSocialActivitySet(activitySet);

		activity.setActivitySetId(activitySetId);

		socialActivityLocalService.updateSocialActivity(activity);
	}

	public List<SocialActivitySet> getActivitySets(
			long classNameId, long classPK, int start, int end)
		throws SystemException {

		return socialActivitySetPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	public List<SocialActivitySet> getActivitySets(
			long groupId, long userId, long classNameId, int type, int start,
			int end)
		throws SystemException {

		return socialActivitySetPersistence.findByG_U_C_T(
			groupId, userId, classNameId, type, start, end);
	}

	public List<SocialActivitySet> getActivitySets(
			long groupId, long userId, long classNameId, long classPK,
			int start, int end)
		throws SystemException {

		return socialActivitySetPersistence.findByG_U_C_C(
			groupId, userId, classNameId, classPK, start, end);
	}

	public List<SocialActivitySet> getActivitySets(
			long groupId, long userId, long classNameId, long classPK, int type,
			int start, int end)
		throws SystemException {

		return socialActivitySetPersistence.findByG_U_C_C_T(
			groupId, userId, classNameId, classPK, type, start, end);
	}

}