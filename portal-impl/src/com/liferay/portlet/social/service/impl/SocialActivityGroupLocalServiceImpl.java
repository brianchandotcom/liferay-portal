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
import com.liferay.portlet.social.model.SocialActivityGroup;
import com.liferay.portlet.social.service.base.SocialActivityGroupLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the social activity group local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.social.service.SocialActivityGroupLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.base.SocialActivityGroupLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.SocialActivityGroupLocalServiceUtil
 */
public class SocialActivityGroupLocalServiceImpl
	extends SocialActivityGroupLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.social.service.SocialActivityGroupLocalServiceUtil} to access the social activity group local service.
	 */

	public void addActivityGroup(
			long userId, long groupId, String className, long classPK, int type,
			SocialActivity activity)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		long createDate = activity.getCreateDate();

		long activityGroupId = counterLocalService.increment();

		SocialActivityGroup activityGroup =
			socialActivityGroupPersistence.create(activityGroupId);

		activityGroup.setGroupId(groupId);
		activityGroup.setCompanyId(user.getCompanyId());
		activityGroup.setUserId(userId);
		activityGroup.setCreateDate(createDate);
		activityGroup.setModifiedDate(createDate);
		activityGroup.setClassName(className);
		activityGroup.setClassPK(classPK);
		activityGroup.setType(type);

		socialActivityGroupLocalService.addSocialActivityGroup(activityGroup);

		// Mapping

		socialActivityPersistence.addSocialActivityGroup(
			activity.getActivityId(), activityGroup);
	}

	public void addGroupActivity(
			SocialActivityGroup activityGroup, SocialActivity activity)
		throws SystemException {

		activityGroup.setModifiedDate(activity.getCreateDate());

		socialActivityGroupLocalService.updateSocialActivityGroup(
			activityGroup);

		// Mapping

		socialActivityPersistence.addSocialActivityGroup(
			activity.getActivityId(), activityGroup);
	}

	public List<SocialActivityGroup> getActivityGroups(
			long classNameId, long classPK, int start, int end)
		throws SystemException {

		return socialActivityGroupPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	public List<SocialActivityGroup> getActivityGroups(
			long groupId, long userId, long classNameId, int type, int start,
			int end)
		throws SystemException {

		return socialActivityGroupPersistence.findByG_U_C_T(
			groupId, userId, classNameId, type, start, end);
	}

	public List<SocialActivityGroup> getActivityGroups(
			long groupId, long userId, long classNameId, long classPK,
			int start, int end)
		throws SystemException {

		return socialActivityGroupPersistence.findByG_U_C_C(
			groupId, userId, classNameId, classPK, start, end);
	}

	public List<SocialActivityGroup> getActivityGroups(
			long groupId, long userId, long classNameId, long classPK, int type,
			int start, int end)
		throws SystemException {

		return socialActivityGroupPersistence.findByG_U_C_C_T(
			groupId, userId, classNameId, classPK, type, start, end);
	}

}