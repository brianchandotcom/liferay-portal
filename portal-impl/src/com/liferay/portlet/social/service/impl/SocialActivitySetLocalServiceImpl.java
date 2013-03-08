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
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.base.SocialActivitySetLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class SocialActivitySetLocalServiceImpl
	extends SocialActivitySetLocalServiceBaseImpl {

	public SocialActivitySet addActivitySet(long activityId)
		throws PortalException, SystemException {

		// Activity set

		SocialActivity activity = socialActivityPersistence.findByPrimaryKey(
			activityId);

		long activitySetId = counterLocalService.increment();

		SocialActivitySet activitySet = socialActivitySetPersistence.create(
			activitySetId);

		activitySet.setGroupId(activity.getGroupId());
		activitySet.setCompanyId(activity.getCompanyId());
		activitySet.setUserId(activity.getUserId());
		activitySet.setCreateDate(activity.getCreateDate());
		activitySet.setModifiedDate(activity.getCreateDate());
		activitySet.setClassName(activity.getClassName());
		activitySet.setClassPK(activity.getClassPK());
		activitySet.setType(activity.getType());
		activitySet.setActivityCount(0);

		socialActivitySetPersistence.update(activitySet);

		// Activity

		activity.setActivitySetId(activitySetId);

		socialActivityPersistence.update(activity);

		return activitySet;
	}

	public SocialActivitySet decrementActivityCount(long activitySetId)
		throws PortalException, SystemException {

		if (activitySetId == 0) {
			return null;
		}

		SocialActivitySet socialActivitySet =
			socialActivitySetPersistence.findByPrimaryKey(activitySetId);

		if (socialActivitySet.getActivityCount() == 1) {
			socialActivitySetPersistence.remove(activitySetId);

			return null;
		}

		socialActivitySet.setActivityCount(
			socialActivitySet.getActivityCount() - 1);

		return socialActivitySetPersistence.update(socialActivitySet);
	}

	public void decrementActivityCount(long classNameId, long classPK)
		throws PortalException, SystemException {

		List<SocialActivity> activities = socialActivityPersistence.findByC_C(
			classNameId, classPK);

		for (SocialActivity activity : activities) {
			decrementActivityCount(activity.getActivitySetId());
		}
	}

	public SocialActivitySet incrementActivityCount(
			long activitySetId, long activityId)
		throws PortalException, SystemException {

		SocialActivitySet socialActivitySet =
			socialActivitySetPersistence.findByPrimaryKey(activitySetId);

		socialActivitySet.setActivityCount(
			socialActivitySet.getActivityCount() + 1);

		SocialActivity activity = socialActivityPersistence.findByPrimaryKey(
			activityId);

		socialActivitySet.setModifiedDate(activity.getCreateDate());

		return socialActivitySetPersistence.update(socialActivitySet);
	}

}