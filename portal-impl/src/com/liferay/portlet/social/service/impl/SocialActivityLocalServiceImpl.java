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
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.service.base.SocialActivityLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The social activity local service. This service provides the means to record
 * and list social activities in groups and organizations.
 *
 * <p>
 * Social activities are identified by their type and the type of asset they are
 * done on. Each activity records the exact time of the action as well as human
 * readable information needed for activity feeds.
 * </p>
 *
 * <p>
 * Most of the <i>get-</i> methods in this service order activities in
 * descending order by their execution times, so the most recent activities are
 * listed first.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialActivityLocalServiceImpl
	extends SocialActivityLocalServiceBaseImpl {

	/**
	 * Records an activity with the given time in the database.
	 *
	 * <p>
	 * This method records a social activity done on an asset, identified by its
	 * class name and class primary key, in the database. Additional information
	 * (such as the original message ID for a reply to a forum post) is passed
	 * in via the <code>extraData</code> in JSON format. For activities
	 * affecting another user, a mirror activity is generated that describes the
	 * action from the user's point of view. The target user's ID is passed in
	 * via the <code>receiverUserId</code>.
	 * </p>
	 *
	 * <p>
	 * Example for a mirrored activity:<br> When a user replies to a message
	 * boards post, the reply action is stored in the database with the
	 * <code>receiverUserId</code> being the ID of the author of the original
	 * message. The <code>extraData</code> contains the ID of the original
	 * message in JSON format. A mirror activity is generated with the values of
	 * the <code>userId</code> and the <code>receiverUserId</code> swapped. This
	 * mirror activity basically describes a "replied to" event.
	 * </p>
	 *
	 * <p>
	 * Mirror activities are most often used in relation to friend requests and
	 * activities.
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  groupId the primary key of the group
	 * @param  createDate the activity's date
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @param  type the activity's type
	 * @param  extraData any extra data regarding the activity
	 * @param  receiverUserId the primary key of the receiving user
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, int type, String extraData, long receiverUserId)
		throws PortalException, SystemException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		if (groupId > 0) {
			Group group = groupLocalService.getGroup(groupId);

			if (group.isLayout()) {
				Layout layout = layoutLocalService.getLayout(
					group.getClassPK());

				groupId = layout.getGroupId();
			}
		}

		SocialActivity activity = socialActivityPersistence.create(0);

		activity.setGroupId(groupId);
		activity.setCompanyId(user.getCompanyId());
		activity.setUserId(user.getUserId());
		activity.setCreateDate(createDate.getTime());
		activity.setMirrorActivityId(0);
		activity.setClassNameId(classNameId);
		activity.setClassPK(classPK);
		activity.setType(type);
		activity.setExtraData(extraData);
		activity.setReceiverUserId(receiverUserId);

		AssetEntry assetEntry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		activity.setAssetEntry(assetEntry);

		SocialActivity mirrorActivity = null;

		if ((receiverUserId > 0) && (userId != receiverUserId)) {
			mirrorActivity = socialActivityPersistence.create(0);

			mirrorActivity.setGroupId(groupId);
			mirrorActivity.setCompanyId(user.getCompanyId());
			mirrorActivity.setUserId(receiverUserId);
			mirrorActivity.setCreateDate(createDate.getTime());
			mirrorActivity.setClassNameId(classNameId);
			mirrorActivity.setClassPK(classPK);
			mirrorActivity.setType(type);
			mirrorActivity.setExtraData(extraData);
			mirrorActivity.setReceiverUserId(user.getUserId());
			mirrorActivity.setAssetEntry(assetEntry);
		}

		socialActivityLocalService.addActivity(activity, mirrorActivity);
	}

	/**
	 * Records an activity in the database, using a time based on the current
	 * time in an attempt to make the activity's time unique.
	 *
	 * @param  userId the primary key of the acting user
	 * @param  groupId the primary key of the group
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @param  type the activity's type
	 * @param  extraData any extra data regarding the activity
	 * @param  receiverUserId the primary key of the receiving user
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException, SystemException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		Date createDate = new Date();

		long classNameId = PortalUtil.getClassNameId(className);

		while (true) {
			SocialActivity socialActivity =
				socialActivityPersistence.fetchByG_U_CD_C_C_T_R(
					groupId, userId, createDate.getTime(), classNameId, classPK,
					type, receiverUserId);

			if (socialActivity != null) {
				createDate = new Date(createDate.getTime() + 1);
			}
			else {
				break;
			}
		}

		addActivity(
			userId, groupId, createDate, className, classPK, type, extraData,
			receiverUserId);
	}

	@Override
	@Async
	public void addActivity(
			SocialActivity activity, SocialActivity mirrorActivity)
		throws PortalException, SystemException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		if ((activity.getActivityId() > 0) ||
			((mirrorActivity != null) &&
			 (mirrorActivity.getActivityId() > 0))) {

			throw new PortalException(
				"Activity and mirror activity must not have primary keys set");
		}

		SocialActivityDefinition activityDefinition =
			socialActivitySettingLocalService.getActivityDefinition(
				activity.getGroupId(), activity.getClassName(),
				activity.getType());

		if (((activityDefinition == null) && (activity.getType() < 10000)) ||
			((activityDefinition != null) &&
			 activityDefinition.isLogActivity())) {

			long activityId = counterLocalService.increment(
				SocialActivity.class.getName());

			activity.setActivityId(activityId);

			socialActivityPersistence.update(activity);

			if (mirrorActivity != null) {
				long mirrorActivityId = counterLocalService.increment(
					SocialActivity.class.getName());

				mirrorActivity.setActivityId(mirrorActivityId);
				mirrorActivity.setMirrorActivityId(activity.getPrimaryKey());

				socialActivityPersistence.update(mirrorActivity);
			}

			if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
				socialActivityInterpreterLocalService.updateActivitySet(
					activity.getActivityId());
			}
		}

		socialActivityCounterLocalService.addActivityCounters(activity);
	}

	/**
	 * Records an activity in the database, but only if there isn't already an
	 * activity with the same parameters.
	 *
	 * <p>
	 * For the main functionality see {@link #addActivity(long, long, Date,
	 * String, long, int, String, long)}
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  groupId the primary key of the group
	 * @param  createDate the activity's date
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @param  type the activity's type
	 * @param  extraData any extra data regarding the activity
	 * @param  receiverUserId the primary key of the receiving user
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUniqueActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, int type, String extraData, long receiverUserId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		SocialActivity socialActivity =
			socialActivityPersistence.fetchByG_U_CD_C_C_T_R(
				groupId, userId, createDate.getTime(), classNameId, classPK,
				type, receiverUserId);

		if (socialActivity != null) {
			return;
		}

		addActivity(
			userId, groupId, createDate, className, classPK, type, extraData,
			receiverUserId);
	}

	/**
	 * Records an activity with the current time in the database, but only if
	 * there isn't one with the same parameters.
	 *
	 * <p>
	 * For the main functionality see {@link #addActivity(long, long, Date,
	 * String, long, int, String, long)}
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  groupId the primary key of the group
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @param  type the activity's type
	 * @param  extraData any extra data regarding the activity
	 * @param  receiverUserId the primary key of the receiving user
	 * @throws PortalException if the user or group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUniqueActivity(
			long userId, long groupId, String className, long classPK, int type,
			String extraData, long receiverUserId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		int count = socialActivityPersistence.countByG_U_C_C_T_R(
			groupId, userId, classNameId, classPK, type, receiverUserId);

		if (count > 0) {
			return;
		}

		addActivity(
			userId, groupId, new Date(), className, classPK, type, extraData,
			receiverUserId);
	}

	/**
	 * Removes stored activities for the asset.
	 *
	 * @param  assetEntry the asset from which to remove stored activities
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivities(AssetEntry assetEntry)
		throws PortalException, SystemException {

		if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
			socialActivitySetLocalService.decrementActivityCount(
				assetEntry.getClassNameId(), assetEntry.getClassPK());
		}

		socialActivityPersistence.removeByC_C(
			assetEntry.getClassNameId(), assetEntry.getClassPK());

		socialActivityCounterLocalService.deleteActivityCounters(assetEntry);
	}

	/**
	 * Removes stored activities for the asset identified by the class name and
	 * class primary key.
	 *
	 * @param  className the target asset's class name
	 * @param  classPK the primary key of the target asset
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivities(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
			socialActivitySetLocalService.decrementActivityCount(
				classNameId, classPK);
		}

		socialActivityPersistence.removeByC_C(classNameId, classPK);
	}

	/**
	 * Removes the stored activity from the database.
	 *
	 * @param  activityId the primary key of the stored activity
	 * @throws PortalException if the activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivity(long activityId)
		throws PortalException, SystemException {

		SocialActivity activity = socialActivityPersistence.findByPrimaryKey(
			activityId);

		deleteActivity(activity);
	}

	/**
	 * Removes the stored activity and its mirror activity from the database.
	 *
	 * @param  activity the activity to be removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivity(SocialActivity activity)
		throws PortalException, SystemException {

		if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
			socialActivitySetLocalService.decrementActivityCount(
				activity.getActivitySetId());
		}

		socialActivityPersistence.remove(activity);

		try {
			socialActivityPersistence.removeByMirrorActivityId(
				activity.getActivityId());
		}
		catch (NoSuchActivityException nsae) {
		}
	}

	/**
	 * Removes the user's stored activities from the database.
	 *
	 * <p>
	 * This method removes all activities where the user is either the actor or
	 * the receiver.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @throws PortalException if the user's activity counters could not be
	 *         deleted
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteUserActivities(long userId)
		throws PortalException, SystemException {

		List<SocialActivity> activities =
			socialActivityPersistence.findByUserId(
				userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity activity : activities) {
			if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
				socialActivitySetLocalService.decrementActivityCount(
					activity.getActivitySetId());
			}

			socialActivityPersistence.remove(activity);
		}

		activities = socialActivityPersistence.findByReceiverUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity activity : activities) {
			if (PropsValues.SOCIAL_ACTIVITY_SETS_ENABLED) {
				socialActivitySetLocalService.decrementActivityCount(
					activity.getActivitySetId());
			}

			socialActivityPersistence.remove(activity);
		}

		socialActivityCounterLocalService.deleteActivityCounters(
			User.class.getName(), userId);
	}

	@Override
	public SocialActivity fetchFirstActivity(
			String className, long classPK, int type)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return socialActivityPersistence.fetchByC_C_T_First(
			classNameId, classPK, type, null);
	}

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

}