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
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.base.SocialActivityServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the social activity remote service.
 *
 * @author Zsolt Berentey
 */
public class SocialActivityServiceImpl extends SocialActivityServiceBaseImpl {

	@Override
	public List<SocialActivity> getActivities(
			long classNameId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getActivities(
				classNameId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public List<SocialActivity> getActivities(
			long mirrorActivityId, long classNameId, long classPK, int start,
			int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getActivities(
				mirrorActivityId, classNameId, classPK, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public List<SocialActivity> getActivities(
			long mirrorActivityId, String className, long classPK, int start,
			int end)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<SocialActivity> activities =
			socialActivityLocalService.getActivities(
				mirrorActivityId, classNameId, classPK, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public List<SocialActivity> getActivities(
			String className, int start, int end)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<SocialActivity> activities =
			socialActivityLocalService.getActivities(
				classNameId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getActivitiesCount(long classNameId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getActivitiesCount(classNameId);
	}

	@Override
	public int getActivitiesCount(
			long mirrorActivityId, long classNameId, long classPK)
		throws PortalException, SystemException {

		return socialActivityLocalService.getActivitiesCount(
			mirrorActivityId, classNameId, classPK);
	}

	@Override
	public int getActivitiesCount(
			long mirrorActivityId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(mirrorActivityId, classNameId, classPK);
	}

	@Override
	public int getActivitiesCount(String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(classNameId);
	}

	@Override
	public SocialActivity getActivity(long activityId)
		throws PortalException, SystemException {

		SocialActivity activity = socialActivityLocalService.getActivity(
			activityId);

		List<SocialActivityInterpreter> activityInterpreters =
			socialActivityInterpreterLocalService.getActivityInterpreters(
				StringPool.BLANK);

		if (!hasPermission(activity, activityInterpreters)) {
			throw new PrincipalException();
		}

		return activity;
	}

	@Override
	public List<SocialActivity> getActivitySetActivities(
			long activitySetId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getActivitySetActivities(
				activitySetId, start, end);

		return filterActivities(activities, start, end);
	}

	@Override
	public List<SocialActivity> getGroupActivities(
			long groupId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getGroupActivities(
				groupId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getGroupActivitiesCount(long groupId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getGroupActivitiesCount(groupId);
	}

	@Override
	public List<SocialActivity> getGroupUsersActivities(
			long groupId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getGroupUsersActivities(
				groupId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getGroupUsersActivitiesCount(long groupId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getGroupUsersActivitiesCount(groupId);
	}

	@Override
	public SocialActivity getMirrorActivity(long mirrorActivityId)
		throws PortalException, SystemException {

		SocialActivity activity = socialActivityLocalService.getMirrorActivity(
			mirrorActivityId);

		List<SocialActivityInterpreter> activityInterpreters =
			socialActivityInterpreterLocalService.getActivityInterpreters(
				StringPool.BLANK);

		if (!hasPermission(activity, activityInterpreters)) {
			throw new PrincipalException();
		}

		return activity;
	}

	@Override
	public List<SocialActivity> getOrganizationActivities(
			long organizationId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getOrganizationActivities(
				organizationId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getOrganizationActivitiesCount(long organizationId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getOrganizationActivitiesCount(
			organizationId);
	}

	@Override
	public List<SocialActivity> getOrganizationUsersActivities(
			long organizationId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getOrganizationUsersActivities(
				organizationId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getOrganizationUsersActivitiesCount(long organizationId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getOrganizationUsersActivitiesCount(
			organizationId);
	}

	@Override
	public List<SocialActivity> getRelationActivities(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getRelationActivities(
				userId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public List<SocialActivity> getRelationActivities(
			long userId, int type, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getRelationActivities(
				userId, type, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getRelationActivitiesCount(long userId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getRelationActivitiesCount(userId);
	}

	@Override
	public int getRelationActivitiesCount(long userId, int type)
		throws PortalException, SystemException {

		return socialActivityLocalService.getRelationActivitiesCount(
			userId, type);
	}

	@Override
	public List<SocialActivity> getUserActivities(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getUserActivities(
				userId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getUserActivitiesCount(long userId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getUserActivitiesCount(userId);
	}

	@Override
	public List<SocialActivity> getUserGroupsActivities(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getUserGroupsActivities(
				userId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getUserGroupsActivitiesCount(long userId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getUserGroupsActivitiesCount(userId);
	}

	@Override
	public List<SocialActivity> getUserGroupsAndOrganizationsActivities(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getUserGroupsAndOrganizationsActivities(
				userId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws PortalException, SystemException {

		return socialActivityLocalService.
			getUserGroupsAndOrganizationsActivitiesCount(userId);
	}

	@Override
	public List<SocialActivity> getUserOrganizationsActivities(
			long userId, int start, int end)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityLocalService.getUserOrganizationsActivities(
				userId, 0,
				end + PropsValues.SOCIAL_ACTIVITY_FILTER_SEARCH_LIMIT);

		return filterActivities(activities, start, end);
	}

	@Override
	public int getUserOrganizationsActivitiesCount(long userId)
		throws PortalException, SystemException {

		return socialActivityLocalService.getUserOrganizationsActivitiesCount(
			userId);
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