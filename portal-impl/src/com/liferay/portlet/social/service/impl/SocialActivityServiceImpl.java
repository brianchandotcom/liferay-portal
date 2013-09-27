/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityServiceImpl extends SocialActivityServiceBaseImpl {

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  classNameId the target asset's class name ID
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getActivities(
			long classNameId, int start, int end)
		throws SystemException {

		return socialActivityPersistence.findByClassNameId(
			classNameId, start, end);
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  mirrorActivityId the primary key of the mirror activity
	 * @param  classNameId the target asset's class name ID
	 * @param  classPK the primary key of the target asset
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getActivities(
			long mirrorActivityId, long classNameId, long classPK, int start,
			int end)
		throws SystemException {

		return socialActivityPersistence.findByM_C_C(
			mirrorActivityId, classNameId, classPK, start, end);
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name and the class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  mirrorActivityId the primary key of the mirror activity
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getActivities(
			long mirrorActivityId, String className, long classPK, int start,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivities(
			mirrorActivityId, classNameId, classPK, start, end);
	}

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  className the target asset's class name
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getActivities(
			String className, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivities(classNameId, start, end);
	}

	/**
	 * Returns the number of activities done on assets identified by the class
	 * name ID.
	 *
	 * @param  classNameId the target asset's class name ID
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getActivitiesCount(long classNameId) throws SystemException {
		return socialActivityPersistence.countByClassNameId(classNameId);
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param  mirrorActivityId the primary key of the mirror activity
	 * @param  classNameId the target asset's class name ID
	 * @param  classPK the primary key of the target asset
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getActivitiesCount(
			long mirrorActivityId, long classNameId, long classPK)
		throws SystemException {

		return socialActivityPersistence.countByM_C_C(
			mirrorActivityId, classNameId, classPK);
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param  mirrorActivityId the primary key of the mirror activity
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getActivitiesCount(
			long mirrorActivityId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(mirrorActivityId, classNameId, classPK);
	}

	/**
	 * Returns the number of activities done on assets identified by class name.
	 *
	 * @param  className the target asset's class name
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getActivitiesCount(String className) throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(classNameId);
	}

	/**
	 * Returns the activity identified by its primary key.
	 *
	 * @param  activityId the primary key of the activity
	 * @return Returns the activity
	 * @throws PortalException if the activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivity getActivity(long activityId)
		throws PortalException, SystemException {

		return socialActivityPersistence.findByPrimaryKey(activityId);
	}

	@Override
	public List<SocialActivity> getActivitySetActivities(
			long activitySetId, int start, int end)
		throws SystemException {

		return socialActivityPersistence.findByActivitySetId(
			activitySetId, start, end);
	}

	/**
	 * Returns a range of all the activities done in the group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getGroupActivities(
			long groupId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns the number of activities done in the group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getGroupActivitiesCount(long groupId) throws SystemException {
		return socialActivityFinder.countByGroupId(groupId);
	}

	/**
	 * Returns a range of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getGroupUsersActivities(
			long groupId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByGroupUsers(groupId, start, end);
	}

	/**
	 * Returns the number of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getGroupUsersActivitiesCount(long groupId)
		throws SystemException {

		return socialActivityFinder.countByGroupUsers(groupId);
	}

	/**
	 * Returns the activity that has the mirror activity.
	 *
	 * @param  mirrorActivityId the primary key of the mirror activity
	 * @return Returns the mirror activity
	 * @throws PortalException if the mirror activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivity getMirrorActivity(long mirrorActivityId)
		throws PortalException, SystemException {

		return socialActivityPersistence.findByMirrorActivityId(
			mirrorActivityId);
	}

	/**
	 * Returns a range of all the activities done in the organization. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getOrganizationActivities(
			long organizationId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByOrganizationId(
			organizationId, start, end);
	}

	/**
	 * Returns the number of activities done in the organization. This method
	 * only counts activities without mirrors.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getOrganizationActivitiesCount(long organizationId)
		throws SystemException {

		return socialActivityFinder.countByOrganizationId(organizationId);
	}

	/**
	 * Returns a range of all the activities done by users of the organization.
	 * This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getOrganizationUsersActivities(
			long organizationId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByOrganizationUsers(
			organizationId, start, end);
	}

	/**
	 * Returns the number of activities done by users of the organization. This
	 * method only counts activities without mirrors.
	 *
	 * @param  organizationId the primary key of the organization
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getOrganizationUsersActivitiesCount(long organizationId)
		throws SystemException {

		return socialActivityFinder.countByOrganizationUsers(organizationId);
	}

	/**
	 * Returns a range of all the activities done by users in a relationship
	 * with the user identified by the user ID.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <>0</code> refers
	 * to the first result in the set. Setting both <code>start</code> and
	 * <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getRelationActivities(
			long userId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByRelation(userId, start, end);
	}

	/**
	 * Returns a range of all the activities done by users in a relationship of
	 * type <code>type</code> with the user identified by <code>userId</code>.
	 * This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relationship type
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getRelationActivities(
			long userId, int type, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByRelationType(
			userId, type, start, end);
	}

	/**
	 * Returns the number of activities done by users in a relationship with the
	 * user identified by userId.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getRelationActivitiesCount(long userId) throws SystemException {
		return socialActivityFinder.countByRelation(userId);
	}

	/**
	 * Returns the number of activities done by users in a relationship of type
	 * <code>type</code> with the user identified by <code>userId</code>. This
	 * method only counts activities without mirrors.
	 *
	 * @param  userId the primary key of the user
	 * @param  type the relationship type
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getRelationActivitiesCount(long userId, int type)
		throws SystemException {

		return socialActivityFinder.countByRelationType(userId, type);
	}

	/**
	 * Returns a range of all the activities done by the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getUserActivities(
			long userId, int start, int end)
		throws SystemException {

		return socialActivityPersistence.findByUserId(userId, start, end);
	}

	/**
	 * Returns the number of activities done by the user.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserActivitiesCount(long userId) throws SystemException {
		return socialActivityPersistence.countByUserId(userId);
	}

	/**
	 * Returns a range of all the activities done in the user's groups. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getUserGroupsActivities(
			long userId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByUserGroups(userId, start, end);
	}

	/**
	 * Returns the number of activities done in user's groups. This method only
	 * counts activities without mirrors.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserGroupsActivitiesCount(long userId)
		throws SystemException {

		return socialActivityFinder.countByUserGroups(userId);
	}

	/**
	 * Returns a range of all the activities done in the user's groups and
	 * organizations. This method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getUserGroupsAndOrganizationsActivities(
			long userId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByUserGroupsAndOrganizations(
			userId, start, end);
	}

	/**
	 * Returns the number of activities done in user's groups and organizations.
	 * This method only counts activities without mirrors.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws SystemException {

		return socialActivityFinder.countByUserGroupsAndOrganizations(userId);
	}

	/**
	 * Returns a range of all activities done in the user's organizations. This
	 * method only finds activities without mirrors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivity> getUserOrganizationsActivities(
			long userId, int start, int end)
		throws SystemException {

		return socialActivityFinder.findByUserOrganizations(userId, start, end);
	}

	/**
	 * Returns the number of activities done in the user's organizations. This
	 * method only counts activities without mirrors.
	 *
	 * @param  userId the primary key of the user
	 * @return the number of matching activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserOrganizationsActivitiesCount(long userId)
		throws SystemException {

		return socialActivityFinder.countByUserOrganizations(userId);
	}

	protected List<SocialActivity> filterActivities(
			List<SocialActivity> activities, int start, int end)
		throws PortalException {

		List<SocialActivityInterpreter> activityInterpreters =
			socialActivityInterpreterLocalService.getActivityInterpreters(
				StringPool.BLANK);

		List<SocialActivity> filteredActivities =
			new ArrayList<SocialActivity>();

		for (SocialActivity activity : activities) {
			if (hasPermission(activity, activityInterpreters)) {
				filteredActivities.add(activity);
			}

			if ((end != QueryUtil.ALL_POS) &&
				(filteredActivities.size() > end)) {

				break;
			}
		}

		if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
			if (end > filteredActivities.size()) {
				end = filteredActivities.size();
			}

			if (start > filteredActivities.size()) {
				start = filteredActivities.size();
			}

			filteredActivities = filteredActivities.subList(start, end);
		}

		return filteredActivities;
	}

	protected boolean hasPermission(
			SocialActivity activity,
			List<SocialActivityInterpreter> activityInterpreters)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		PermissionChecker permissionChecker = getPermissionChecker();

		for (int i = 0; i < activityInterpreters.size(); i++) {
			SocialActivityInterpreterImpl activityInterpreter =
				(SocialActivityInterpreterImpl)activityInterpreters.get(i);

			if (activityInterpreter.hasClassName(activity.getClassName())) {
				try {
					if (activityInterpreter.hasPermission(
							permissionChecker, activity, ActionKeys.VIEW,
							serviceContext)) {

						return true;
					}
				}
				catch (Exception e) {
				}
			}
		}

		return false;
	}

}