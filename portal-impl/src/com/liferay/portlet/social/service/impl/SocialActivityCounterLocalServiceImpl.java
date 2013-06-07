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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.LockProtectedAction;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.SocialActivityProcessor;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.service.base.SocialActivityCounterLocalServiceBaseImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinder;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The social activity counter local service. This service is responsible for
 * creating and/or incrementing counters in response to an activity. It also
 * provides methods for querying activity counters within a time period.
 *
 * <p>
 * Under normal circumstances only the {@link
 * #addActivityCounters(SocialActivity)} should be called directly and even that
 * is usually not necessary as it is automatically called by the social activity
 * service.
 * </p>
 *
 * @author Zsolt Berentey
 * @author Shuyang Zhou
 */
public class SocialActivityCounterLocalServiceImpl
	extends SocialActivityCounterLocalServiceBaseImpl {

	/**
	 * Adds an activity counter with a default period length.
	 *
	 * <p>
	 * This method uses the lock service to guard against multiple threads
	 * trying to insert the same counter because this service is called
	 * asynchronously from the social activity service.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class this counter
	 *         belongs to
	 * @param  classPK the primary key of the entity this counter belongs to
	 * @param  name the counter's name
	 * @param  ownerType the counter's owner type. Acceptable values are
	 *         <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 *         <code>TYPE_CREATOR</code> defined in {@link
	 *         com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @param  currentValue the counter's current value (optionally
	 *         <code>0</code>)
	 * @param  totalValue the counter's total value (optionally <code>0</code>)
	 * @param  startPeriod the counter's start period
	 * @param  endPeriod the counter's end period
	 * @return the added activity counter
	 * @throws PortalException if the group or the previous activity counter
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	 *             long, long, String, int, int, long, int)}
	 */
	@Override
	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod)
		throws PortalException, SystemException {

		return addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, totalValue, 0, 0);
	}

	/**
	 * Adds an activity counter specifying a previous activity and period
	 * length.
	 *
	 * <p>
	 * This method uses the lock service to guard against multiple threads
	 * trying to insert the same counter because this service is called
	 * asynchronously from the social activity service.
	 * </p>
	 *
	 * @param      groupId the primary key of the group
	 * @param      classNameId the primary key of the entity's class this
	 *             counter belongs to
	 * @param      classPK the primary key of the entity this counter belongs to
	 * @param      name the counter name
	 * @param      ownerType the counter's owner type. Acceptable values are
	 *             <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 *             <code>TYPE_CREATOR</code> defined in {@link
	 *             com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @param      currentValue the current value of the counter (optionally
	 *             <code>0</code>)
	 * @param      totalValue the counter's total value (optionally
	 *             <code>0</code>)
	 * @param      startPeriod the counter's start period
	 * @param      endPeriod the counter's end period
	 * @param      previousActivityCounterId the primary key of the activity
	 *             counter for the previous time period (optionally
	 *             <code>0</code>, if this is the first)
	 * @param      periodLength the period length in days,
	 *             <code>PERIOD_LENGTH_INFINITE</code> for never ending counters
	 *             or <code>PERIOD_LENGTH_SYSTEM</code> for the period length
	 *             defined in <code>portal-ext.properties</code>. For more
	 *             information see {@link
	 *             com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @return     the added activity counter
	 * @throws     PortalException if the group or the previous activity counter
	 *             could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	 *             long, long, String, int, int, long, int)}
	 */
	@Override
	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod, long previousActivityCounterId, int periodLength)
		throws PortalException, SystemException {

		return addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, totalValue,
			previousActivityCounterId, periodLength);
	}

	/**
	 * Adds an activity counter specifying a previous activity and period
	 * length.
	 *
	 * <p>
	 * This method uses the lock service to guard against multiple threads
	 * trying to insert the same counter because this service is called
	 * asynchronously from the social activity service.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class this counter
	 *         belongs to
	 * @param  classPK the primary key of the entity this counter belongs to
	 * @param  name the counter name
	 * @param  ownerType the counter's owner type. Acceptable values are
	 *         <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 *         <code>TYPE_CREATOR</code> defined in {@link
	 *         com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @param  totalValue the counter's total value (optionally <code>0</code>)
	 * @param  previousActivityCounterId the primary key of the activity counter
	 *         for the previous time period (optionally <code>0</code>, if this
	 *         is the first)
	 * @param  periodLength the period length in days,
	 *         <code>PERIOD_LENGTH_INFINITE</code> for never ending counters or
	 *         <code>PERIOD_LENGTH_SYSTEM</code> for the period length defined
	 *         in <code>portal-ext.properties</code>. For more information see
	 *         {@link
	 *         com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @return the added activity counter
	 * @throws PortalException if the group or the previous activity counter
	 *         could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int totalValue, long previousActivityCounterId,
			int periodLength)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = null;

		if (previousActivityCounterId != 0) {
			activityCounter = socialActivityCounterPersistence.findByPrimaryKey(
				previousActivityCounterId);

			if (periodLength ==
					SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM) {

				activityCounter.setEndPeriod(
					SocialCounterPeriodUtil.getStartPeriod() - 1);
			}
			else {
				activityCounter.setEndPeriod(
					activityCounter.getStartPeriod() + periodLength - 1);
			}

			socialActivityCounterPersistence.update(activityCounter);
		}

		activityCounter = socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType,
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED, false);

		if (activityCounter != null) {
			return activityCounter;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long activityCounterId = counterLocalService.increment();

		activityCounter = socialActivityCounterPersistence.create(
			activityCounterId);

		activityCounter.setGroupId(groupId);
		activityCounter.setCompanyId(group.getCompanyId());
		activityCounter.setClassNameId(classNameId);
		activityCounter.setClassPK(classPK);
		activityCounter.setName(name);
		activityCounter.setOwnerType(ownerType);
		activityCounter.setTotalValue(totalValue);

		if (periodLength ==
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM) {

			activityCounter.setStartPeriod(
				SocialCounterPeriodUtil.getStartPeriod());
		}
		else {
			activityCounter.setStartPeriod(
				SocialCounterPeriodUtil.getActivityDay());
		}

		activityCounter.setEndPeriod(
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);
		activityCounter.setActive(true);

		socialActivityCounterPersistence.update(activityCounter);

		return activityCounter;
	}

	/**
	 * Adds or increments activity counters related to an activity.
	 *
	 * </p>
	 * This method is called asynchronously from the social activity service
	 * when the user performs an activity defined in
	 * </code>liferay-social.xml</code>.
	 * </p>
	 *
	 * <p>
	 * This method first calls the activity processor class, if there is one
	 * defined for the activity, checks for limits and increments all the
	 * counters that belong to the activity. Afterwards, it processes the
	 * activity with respect to achievement classes, if any. Lastly it
	 * increments the built-in <code>user.activities</code> and
	 * <code>asset.activities</code> counters.
	 * </p>
	 *
	 * @param  activity the social activity
	 * @throws PortalException if an expected group or expected previous
	 *         activity counters could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addActivityCounters(SocialActivity activity)
		throws PortalException, SystemException {

		if (!socialActivitySettingLocalService.isEnabled(
				activity.getGroupId(), activity.getClassNameId())) {

			return;
		}

		if (!socialActivitySettingLocalService.isEnabled(
				activity.getGroupId(), activity.getClassNameId(),
				activity.getClassPK())) {

			return;
		}

		User user = userPersistence.findByPrimaryKey(activity.getUserId());

		SocialActivityDefinition activityDefinition =
			socialActivitySettingLocalService.getActivityDefinition(
				activity.getGroupId(), activity.getClassName(),
				activity.getType());

		if ((activityDefinition == null) ||
			!activityDefinition.isCountersEnabled()) {

			return;
		}

		SocialActivityProcessor activityProcessor =
			activityDefinition.getActivityProcessor();

		if (activityProcessor != null) {
			activityProcessor.processActivity(activity);
		}

		AssetEntry assetEntry = activity.getAssetEntry();

		User assetEntryUser = userPersistence.findByPrimaryKey(
			assetEntry.getUserId());

		List<SocialActivityCounter> activityCounters =
			new ArrayList<SocialActivityCounter>();

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityDefinition.getActivityCounterDefinitions()) {

			if (addActivityCounter(
					user, assetEntryUser, activityCounterDefinition)) {

				activityCounters.add(
					getActivityCounter(
						activity.getGroupId(), user, activity,
						activityCounterDefinition));
			}
		}

		SocialActivityCounter assetActivitiesCounter = null;
		SocialActivityCounter userActivitiesCounter = null;

		if (!user.isDefaultUser() && user.isActive()) {
			userActivitiesCounter = getUserActivitiesCounter(activity);
		}

		if (!assetEntryUser.isDefaultUser() && assetEntryUser.isActive()) {
			assetActivitiesCounter = getAssetActivitiesCounter(activity);
		}

		for (SocialActivityCounter activityCounter : activityCounters) {
			SocialActivityCounterDefinition activityCounterDefinition =
				activityDefinition.getActivityCounterDefinition(
					activityCounter.getName());

			if (checkActivityLimit(user, activity, activityCounterDefinition)) {
				incrementActivityCounter(
					activityCounter, activityCounterDefinition);
			}
		}

		if (assetActivitiesCounter != null) {
			incrementActivityCounter(
				assetActivitiesCounter,
				SocialActivityCounterDefinition.ASSET_ACTIVITIES);
		}

		if (userActivitiesCounter != null) {
			incrementActivityCounter(
				userActivitiesCounter,
				SocialActivityCounterDefinition.USER_ACTIVITIES);
		}

		for (SocialAchievement achievement :
				activityDefinition.getAchievements()) {

			achievement.processActivity(activity);
		}
	}

	/**
	 * Creates an activity counter with a default period length, adding it into
	 * the database.
	 *
	 * @param      groupId the primary key of the group
	 * @param      classNameId the primary key of the entity's class this
	 *             counter belongs to
	 * @param      classPK the primary key of the entity this counter belongs to
	 * @param      name the counter's name
	 * @param      ownerType the counter's owner type. Acceptable values are
	 *             <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 *             <code>TYPE_CREATOR</code> defined in {@link
	 *             com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @param      currentValue the counter's current value (optionally
	 *             <code>0</code>)
	 * @param      totalValue the counter's total value (optionally
	 *             <code>0</code>)
	 * @param      startPeriod the counter's start period
	 * @param      endPeriod the counter's end period
	 * @return     the created activity counter
	 * @throws     PortalException if the group or a previous activity counter
	 *             could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	 *             long, long, String, int, int, long, int)}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter createActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod)
		throws PortalException, SystemException {

		return addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, totalValue, 0, 0);
	}

	/**
	 * Creates an activity counter, adding it into the database.
	 *
	 * <p>
	 * This method actually creates the counter in the database. It requires a
	 * new transaction so that other threads can find the new counter when the
	 * lock in the calling method is released.
	 * </p>
	 *
	 * @param      groupId the primary key of the group
	 * @param      classNameId the primary key of the entity's class this
	 *             counter belongs to
	 * @param      classPK the primary key of the entity this counter belongs to
	 * @param      name the counter's name
	 * @param      ownerType the counter's owner type. Acceptable values are
	 *             <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 *             <code>TYPE_CREATOR</code> defined in {@link
	 *             com.liferay.portlet.social.model.SocialActivityCounterConstants}.
	 * @param      currentValue the counter's current value (optionally
	 *             <code>0</code>)
	 * @param      totalValue the counter's total value of the counter
	 *             (optionally <code>0</code>)
	 * @param      startPeriod the counter's start period
	 * @param      endPeriod the counter's end period
	 * @param      previousActivityCounterId the primary key of the activity
	 *             counter for the previous time period (optionally
	 *             <code>0</code>, if this is the first)
	 * @param      periodLength the period length in days,
	 *             <code>PERIOD_LENGTH_INFINITE</code> for never ending counters
	 *             or <code>PERIOD_LENGTH_SYSTEM</code> for the period length
	 *             defined in <code>portal-ext.properties</code>. For more
	 *             information see {@link
	 *             com.liferay.portlet.social.model.SocialActivityConstants}.
	 * @return     the created activity counter
	 * @throws     PortalException if the group or the previous activity counter
	 *             could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated As of 6.2.0, replaced by {@link #addActivityCounter(long,
	 *             long, long, String, int, int, long, int)}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter createActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod, long previousActivityCounterId, int periodLength)
		throws PortalException, SystemException {

		return addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, totalValue,
			previousActivityCounterId, periodLength);
	}

	/**
	 * Deletes all activity counters, limits, and settings related to the asset.
	 *
	 * <p>
	 * This method subtracts the asset's popularity from the owner's
	 * contribution points. It also creates a new contribution period if the
	 * latest one does not belong to the current period.
	 * </p>
	 *
	 * @param  assetEntry the asset entry
	 * @throws PortalException if the new contribution counter could not be
	 *         created
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivityCounters(AssetEntry assetEntry)
		throws PortalException, SystemException {

		if (assetEntry == null) {
			return;
		}

		adjustUserContribution(assetEntry, false);

		socialActivityCounterPersistence.removeByC_C(
			assetEntry.getClassNameId(), assetEntry.getClassPK());

		socialActivityLimitPersistence.removeByC_C(
			assetEntry.getClassNameId(), assetEntry.getClassPK());

		socialActivitySettingLocalService.deleteActivitySetting(
			assetEntry.getGroupId(), assetEntry.getClassName(),
			assetEntry.getClassPK());

		clearFinderCache();
	}

	/**
	 * Deletes all activity counters, limits, and settings related to the entity
	 * identified by the class name ID and class primary key.
	 *
	 * @param  classNameId the primary key of the entity's class
	 * @param  classPK the primary key of the entity
	 * @throws PortalException if the entity is an asset and its owner's
	 *         contribution counter could not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivityCounters(long classNameId, long classPK)
		throws PortalException, SystemException {

		String className = PortalUtil.getClassName(classNameId);

		if (!className.equals(User.class.getName())) {
			AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
				className, classPK);

			deleteActivityCounters(assetEntry);
		}
		else {
			socialActivityCounterPersistence.removeByC_C(classNameId, classPK);

			socialActivityLimitPersistence.removeByUserId(classPK);
		}

		clearFinderCache();
	}

	/**
	 * Deletes all activity counters for the entity identified by the class name
	 * and class primary key.
	 *
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @throws PortalException if the entity is an asset and its owner's
	 *         contribution counter could not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void deleteActivityCounters(String className, long classPK)
		throws PortalException, SystemException {

		if (!className.equals(User.class.getName())) {
			AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
				className, classPK);

			deleteActivityCounters(assetEntry);
		}
		else {
			long classNameId = PortalUtil.getClassNameId(className);

			socialActivityCounterPersistence.removeByC_C(classNameId, classPK);

			socialActivityLimitPersistence.removeByUserId(classPK);
		}

		clearFinderCache();
	}

	/**
	 * Disables all the counters of an asset identified by the class name ID and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to disable all counters of assets
	 * put into the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param  classNameId the primary key of the asset's class
	 * @param  classPK the primary key of the asset
	 * @throws PortalException if the asset owner's contribution counter could
	 *         not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void disableActivityCounters(long classNameId, long classPK)
		throws PortalException, SystemException {

		String className = PortalUtil.getClassName(classNameId);

		disableActivityCounters(className, classPK);
	}

	/**
	 * Disables all the counters of an asset identified by the class name and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to disable all counters of assets
	 * put into the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param  className the asset's class name
	 * @param  classPK the primary key of the asset
	 * @throws PortalException if the asset owner's contribution counter could
	 *         not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void disableActivityCounters(String className, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry == null) {
			return;
		}

		List<SocialActivityCounter> activityCounters =
			socialActivityCounterPersistence.findByC_C(
				assetEntry.getClassNameId(), classPK);

		adjustUserContribution(assetEntry, false);

		for (SocialActivityCounter activityCounter : activityCounters) {
			if (activityCounter.isActive()) {
				activityCounter.setActive(false);

				socialActivityCounterPersistence.update(activityCounter);
			}
		}

		clearFinderCache();
	}

	/**
	 * Enables all activity counters of an asset identified by the class name ID
	 * and class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to enable all counters of assets
	 * restored from the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param  classNameId the primary key of the asset's class
	 * @param  classPK the primary key of the asset
	 * @throws PortalException if the asset owner's contribution counter could
	 *         not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void enableActivityCounters(long classNameId, long classPK)
		throws PortalException, SystemException {

		String className = PortalUtil.getClassName(classNameId);

		enableActivityCounters(className, classPK);
	}

	/**
	 * Enables all the counters of an asset identified by the class name and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to enable all counters of assets
	 * restored from the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param  className the asset's class name
	 * @param  classPK the primary key of the asset
	 * @throws PortalException if the asset owner's contribution counter could
	 *         not be updated
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void enableActivityCounters(String className, long classPK)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry == null) {
			return;
		}

		List<SocialActivityCounter> activityCounters =
			socialActivityCounterPersistence.findByC_C(
				assetEntry.getClassNameId(), classPK);

		adjustUserContribution(assetEntry, true);

		for (SocialActivityCounter activityCounter : activityCounters) {
			if (!activityCounter.isActive()) {
				activityCounter.setActive(true);

				socialActivityCounterPersistence.update(activityCounter);
			}
		}

		clearFinderCache();
	}

	/**
	 * Returns the activity counter with the given name, owner, and end period
	 * that belong to the given entity.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class
	 * @param  classPK the primary key of the entity
	 * @param  name the counter name
	 * @param  ownerType the owner type
	 * @param  endPeriod the end period, <code>-1</code> for the latest one
	 * @return the matching activity counter
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter fetchActivityCounterByEndPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int endPeriod)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType, endPeriod);
	}

	/**
	 * Returns the activity counter with the given name, owner, and start period
	 * that belong to the given entity.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class
	 * @param  classPK the primary key of the entity
	 * @param  name the counter name
	 * @param  ownerType the owner type
	 * @param  startPeriod the start period
	 * @return the matching activity counter
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter fetchActivityCounterByStartPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_S(
			groupId, classNameId, classPK, name, ownerType, startPeriod);
	}

	/**
	 * Returns the latest activity counter with the given name and owner that
	 * belong to the given entity.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class
	 * @param  classPK the primary key of the entity
	 * @param  name the counter name
	 * @param  ownerType the owner type
	 * @return the matching activity counter
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivityCounter fetchLatestActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType,
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);
	}

	/**
	 * Returns all the activity counters with the given name and period offsets.
	 *
	 * <p>
	 * The start and end offsets can belong to different periods. This method
	 * groups the counters by name and returns the sum of their current values.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the counter name
	 * @param  startOffset the offset for the start period
	 * @param  endOffset the offset for the end period
	 * @return the matching activity counters
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivityCounter> getOffsetActivityCounters(
			long groupId, String name, int startOffset, int endOffset)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getPeriodActivityCounters(groupId, name, startPeriod, endPeriod);
	}

	/**
	 * Returns the distribution of the activity counters with the given name and
	 * period offsets.
	 *
	 * <p>
	 * The start and end offsets can belong to different periods. This method
	 * groups the counters by their owner entity (usually some asset) and
	 * returns a counter for each entity class with the sum of the counters'
	 * current values.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the counter name
	 * @param  startOffset the offset for the start period
	 * @param  endOffset the offset for the end period
	 * @return the distribution of matching activity counters
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivityCounter> getOffsetDistributionActivityCounters(
			long groupId, String name, int startOffset, int endOffset)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getPeriodDistributionActivityCounters(
			groupId, name, startPeriod, endPeriod);
	}

	/**
	 * Returns all the activity counters with the given name and time period.
	 *
	 * <p>
	 * The start and end period values can belong to different periods. This
	 * method groups the counters by name and returns the sum of their current
	 * values.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the counter name
	 * @param  startPeriod the start period
	 * @param  endPeriod the end period
	 * @return the matching activity counters
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivityCounter> getPeriodActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		if (endPeriod == SocialActivityCounterConstants.END_PERIOD_UNDEFINED) {
			endPeriod = SocialCounterPeriodUtil.getEndPeriod();
		}

		int offset = SocialCounterPeriodUtil.getOffset(endPeriod);

		int periodLength = SocialCounterPeriodUtil.getPeriodLength(offset);

		return socialActivityCounterFinder.findAC_ByG_N_S_E_1(
			groupId, name, startPeriod, endPeriod, periodLength);
	}

	/**
	 * Returns the distribution of activity counters with the given name and
	 * time period.
	 *
	 * <p>
	 * The start and end period values can belong to different periods. This
	 * method groups the counters by their owner entity (usually some asset) and
	 * returns a counter for each entity class with the sum of the counters'
	 * current values.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the counter name
	 * @param  startPeriod the start period
	 * @param  endPeriod the end period
	 * @return the distribution of matching activity counters
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<SocialActivityCounter> getPeriodDistributionActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		int offset = SocialCounterPeriodUtil.getOffset(endPeriod);

		int periodLength = SocialCounterPeriodUtil.getPeriodLength(offset);

		return socialActivityCounterFinder.findAC_ByG_N_S_E_2(
			groupId, name, startPeriod, endPeriod, periodLength);
	}

	/**
	 * Returns the range of tuples that contain users and a list of activity
	 * counters.
	 *
	 * <p>
	 * The counters returned for each user are passed to this method in the
	 * selectedNames array. The method also accepts an array of counter names
	 * that are used to rank the users.
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
	 * @param  rankingNames the ranking counter names
	 * @param  selectedNames the counter names that will be returned with each
	 *         user
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of matching tuples
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Tuple> getUserActivityCounters(
			long groupId, String[] rankingNames, String[] selectedNames,
			int start, int end)
		throws SystemException {

		List<Long> userIds = socialActivityCounterFinder.findU_ByG_N(
			groupId, rankingNames, start, end);

		if (userIds.isEmpty()) {
			return Collections.emptyList();
		}

		Tuple[] userActivityCounters = new Tuple[userIds.size()];

		List<SocialActivityCounter> activityCounters =
			socialActivityCounterFinder.findAC_By_G_C_C_N_S_E(
				groupId, userIds, selectedNames, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		long userId = 0;
		Map<String, SocialActivityCounter> activityCountersMap = null;

		for (SocialActivityCounter activityCounter : activityCounters) {
			if (userId != activityCounter.getClassPK()) {
				userId = activityCounter.getClassPK();
				activityCountersMap =
					new HashMap<String, SocialActivityCounter>();

				Tuple userActivityCounter = new Tuple(
					userId, activityCountersMap);

				for (int i = 0; i < userIds.size(); i++) {
					long curUserId = userIds.get(i);

					if (userId == curUserId) {
						userActivityCounters[i] = userActivityCounter;

						break;
					}
				}
			}

			activityCountersMap.put(activityCounter.getName(), activityCounter);
		}

		return Arrays.asList(userActivityCounters);
	}

	/**
	 * Returns the number of users having a rank based on the given counters.
	 *
	 * @param  groupId the primary key of the group
	 * @param  rankingNames the ranking counter names
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserActivityCountersCount(long groupId, String[] rankingNames)
		throws SystemException {

		return socialActivityCounterFinder.countU_ByG_N(groupId, rankingNames);
	}

	/**
	 * Increments the <code>user.achievements</code> counter for a user.
	 *
	 * <p>
	 * This method should be used by an external achievement class when the
	 * users unlocks an achievement.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @throws PortalException if the group or an expected previous activity
	 *         counter could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void incrementUserAchievementCounter(long userId, long groupId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		SocialActivityCounter activityCounter =
			getActivityCounter(
				groupId, user, new SocialActivityImpl(),
				SocialActivityCounterDefinition.USER_ACHIEVEMENTS);

		incrementActivityCounter(
			activityCounter, SocialActivityCounterDefinition.USER_ACHIEVEMENTS);
	}

	protected boolean addActivityCounter(
		User user, User assetEntryUser,
		SocialActivityCounterDefinition activityCounterDefinition) {

		if ((user.isDefaultUser() || !user.isActive()) &&
			(activityCounterDefinition.getOwnerType() !=
				SocialActivityCounterConstants.TYPE_ASSET)) {

			return false;
		}

		if ((assetEntryUser.isDefaultUser() || !assetEntryUser.isActive()) &&
			(activityCounterDefinition.getOwnerType() !=
				SocialActivityCounterConstants.TYPE_ACTOR)) {

			return false;
		}

		if (!activityCounterDefinition.isEnabled() ||
			(activityCounterDefinition.getIncrement() == 0)) {

			return false;
		}

		String name = activityCounterDefinition.getName();

		if ((user.getUserId() == assetEntryUser.getUserId()) &&
			(name.equals(SocialActivityCounterConstants.NAME_CONTRIBUTION) ||
			 name.equals(SocialActivityCounterConstants.NAME_POPULARITY))) {

			return false;
		}

		return true;
	}

	protected void adjustUserContribution(AssetEntry assetEntry, boolean enable)
		throws PortalException, SystemException {

		if (assetEntry == null) {
			return;
		}

		SocialActivityCounter latestPopularityActivityCounter =
			fetchLatestActivityCounter(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK(),
				SocialActivityCounterConstants.NAME_POPULARITY,
				SocialActivityCounterConstants.TYPE_ASSET);

		if ((latestPopularityActivityCounter == null) ||
			(enable && latestPopularityActivityCounter.isActive()) ||
			(!enable && !latestPopularityActivityCounter.isActive())) {

			return;
		}

		int factor = -1;

		if (enable) {
			factor = 1;
		}

		SocialActivityCounter latestContributionActivityCounter =
			fetchLatestActivityCounter(
				assetEntry.getGroupId(),
				PortalUtil.getClassNameId(User.class.getName()),
				assetEntry.getUserId(),
				SocialActivityCounterConstants.NAME_CONTRIBUTION,
				SocialActivityCounterConstants.TYPE_CREATOR);

		if (latestContributionActivityCounter == null) {
			return;
		}

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod();

		if (latestContributionActivityCounter.getStartPeriod() != startPeriod) {
			latestContributionActivityCounter = addActivityCounter(
				latestContributionActivityCounter.getGroupId(),
				latestContributionActivityCounter.getClassNameId(),
				latestContributionActivityCounter.getClassPK(),
				latestContributionActivityCounter.getName(),
				latestContributionActivityCounter.getOwnerType(), 0,
				latestContributionActivityCounter.getTotalValue(),
				SocialCounterPeriodUtil.getStartPeriod(),
				SocialActivityCounterConstants.END_PERIOD_UNDEFINED,
				latestContributionActivityCounter.getActivityCounterId(),
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
		}

		if (latestPopularityActivityCounter.getStartPeriod() == startPeriod) {
			latestContributionActivityCounter.setCurrentValue(
				latestContributionActivityCounter.getCurrentValue() +
					(latestPopularityActivityCounter.getCurrentValue() *
						factor));
		}

		latestContributionActivityCounter.setTotalValue(
			latestContributionActivityCounter.getTotalValue() +
				(latestPopularityActivityCounter.getTotalValue() * factor));

		socialActivityCounterPersistence.update(
			latestContributionActivityCounter);
	}

	protected boolean checkActivityLimit(
			User user, SocialActivity activity,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		if (activityCounterDefinition.getLimitValue() == 0) {
			return true;
		}

		long classPK = activity.getClassPK();

		String name = activityCounterDefinition.getName();

		if (name.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
			classPK = 0;
		}

		SocialActivityLimit activityLimit =
			socialActivityLimitPersistence.findByG_U_C_C_A_A(
				activity.getGroupId(), user.getUserId(),
				activity.getClassNameId(), classPK, activity.getType(), name);

		int count = activityLimit.getCount(
			activityCounterDefinition.getLimitPeriod());

		if (count < activityCounterDefinition.getLimitValue()) {
			activityLimit.setCount(
				activityCounterDefinition.getLimitPeriod(), count + 1);

			socialActivityLimitPersistence.update(activityLimit);

			return true;
		}

		return false;
	}

	protected void clearFinderCache() {
		PortalCache<String, SocialActivityCounter> portalCache =
			MultiVMPoolUtil.getCache(
				SocialActivityCounterFinder.class.getName());

		portalCache.removeAll();
	}

	protected SocialActivityCounter createActivityCounter(
			final long groupId, final long classNameId, final long classPK,
			final String name, final int ownerType, final int totalValue,
			final long previousActivityCounterId, final int periodLength)
		throws PortalException, SystemException {

		String lockKey = StringUtil.merge(
			new Object[] {
				groupId, classNameId, classPK, name, ownerType
			},
			StringPool.POUND);

		LockProtectedAction<SocialActivityCounter> lockProtectedAction =
			new LockProtectedAction<SocialActivityCounter>(
				SocialActivityCounter.class, lockKey,
				PropsValues.SOCIAL_ACTIVITY_LOCK_TIMEOUT,
				PropsValues.SOCIAL_ACTIVITY_LOCK_RETRY_DELAY) {

			@Override
			protected SocialActivityCounter performProtectedAction()
				throws PortalException, SystemException {

				SocialActivityCounter activityCounter =
					socialActivityCounterLocalService.addActivityCounter(
						groupId, classNameId, classPK, name, ownerType,
						totalValue, previousActivityCounterId, periodLength);

				return activityCounter;
			}

		};

		lockProtectedAction.performAction();

		return lockProtectedAction.getReturnValue();
	}

	protected void createActivityLimit(
			final long groupId, final User user, final SocialActivity activity,
			final SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		final long classPK = getLimitClassPK(
			activity, activityCounterDefinition);

		String lockKey = StringUtil.merge(
			new Object[] {
				groupId, user.getUserId(), activity.getClassNameId(), classPK,
				activity.getType(), activityCounterDefinition.getName()
			},
			StringPool.POUND);

		LockProtectedAction<SocialActivityLimit> protectedAction =
			new LockProtectedAction<SocialActivityLimit>(
				SocialActivityLimit.class, lockKey,
				PropsValues.SOCIAL_ACTIVITY_LOCK_TIMEOUT,
				PropsValues.SOCIAL_ACTIVITY_LOCK_RETRY_DELAY) {

			@Override
			protected SocialActivityLimit performProtectedAction()
				throws PortalException, SystemException {

				SocialActivityLimit activityLimit=
					socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
						groupId, user.getUserId(), activity.getClassNameId(),
						classPK, activity.getType(),
						activityCounterDefinition.getName());

				if (activityLimit == null) {
					activityLimit =
						socialActivityLimitLocalService.addActivityLimit(
							user.getUserId(), activity.getGroupId(),
							activity.getClassNameId(), classPK,
							activity.getType(),
							activityCounterDefinition.getName(),
							activityCounterDefinition.getLimitPeriod());
				}

				return activityLimit;
			}
		};

		protectedAction.performAction();

		socialActivityLimitPersistence.cacheResult(
			protectedAction.getReturnValue());
	}

	protected SocialActivityCounter getActivityCounter(
			final long groupId, final User user, final SocialActivity activity,
			final SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		int ownerType = activityCounterDefinition.getOwnerType();

		long classNameId = getClassNameId(activity.getAssetEntry(), ownerType);
		long classPK = getClassPK(user, activity.getAssetEntry(), ownerType);

		SocialActivityCounter activityCounter = fetchLatestActivityCounter(
			groupId, classNameId, classPK, activityCounterDefinition.getName(),
			ownerType);

		if (activityCounter == null) {
			activityCounter = createActivityCounter(
				groupId, classNameId, classPK,
				activityCounterDefinition.getName(), ownerType, 0, 0,
				activityCounterDefinition.getPeriodLength());
		}
		else if (!activityCounter.isActivePeriod(
					activityCounterDefinition.getPeriodLength())) {

			activityCounter = createActivityCounter(
				groupId, classNameId, classPK,
				activityCounterDefinition.getName(), ownerType,
				activityCounter.getTotalValue(),
				activityCounter.getActivityCounterId(),
				activityCounterDefinition.getPeriodLength());
		}

		if (activityCounterDefinition.getLimitValue() > 0) {
			SocialActivityLimit activityLimit=
				socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
					groupId, user.getUserId(), activity.getClassNameId(),
					getLimitClassPK(activity, activityCounterDefinition),
					activity.getType(), activityCounterDefinition.getName());

			if (activityLimit == null) {
				createActivityLimit(
					groupId, user, activity, activityCounterDefinition);
			}
		}

		return activityCounter;
	}

	protected SocialActivityCounter getAssetActivitiesCounter(
			SocialActivity activity)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(activity.getUserId());

		return getActivityCounter(
			activity.getGroupId(), user, activity,
			SocialActivityCounterDefinition.ASSET_ACTIVITIES);
	}

	protected long getClassNameId(AssetEntry assetEntry, int ownerType) {
		if (ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			return assetEntry.getClassNameId();
		}

		return PortalUtil.getClassNameId(User.class.getName());
	}

	protected long getClassPK(User user, AssetEntry assetEntry, int ownerType) {
		if (ownerType == SocialActivityCounterConstants.TYPE_ACTOR) {
			return user.getUserId();
		}

		if (ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			return assetEntry.getClassPK();
		}
		else {
			return assetEntry.getUserId();
		}
	}

	protected long getLimitClassPK(
		SocialActivity activity,
		SocialActivityCounterDefinition activityCounterDefinition) {

		String name = activityCounterDefinition.getName();

		if (name.equals(SocialActivityCounterConstants.NAME_PARTICIPATION)) {
			return 0;
		}
		else {
			return activity.getClassPK();
		}
	}

	protected SocialActivityCounter getUserActivitiesCounter(
			SocialActivity activity)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(activity.getUserId());

		return getActivityCounter(
			activity.getGroupId(), user, activity,
			SocialActivityCounterDefinition.USER_ACTIVITIES);
	}

	protected void incrementActivityCounter(
			SocialActivityCounter activityCounter,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		activityCounter.setCurrentValue(
			activityCounter.getCurrentValue() +
				activityCounterDefinition.getIncrement());

		activityCounter.setTotalValue(
			activityCounter.getTotalValue() +
				activityCounterDefinition.getIncrement());

		socialActivityCounterPersistence.update(activityCounter);

		socialActivityCounterPersistence.clearCache(activityCounter);
	}

}