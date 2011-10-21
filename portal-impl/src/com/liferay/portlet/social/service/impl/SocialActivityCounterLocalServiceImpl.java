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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.SocialActivityProcessor;
import com.liferay.portlet.social.service.base.SocialActivityCounterLocalServiceBaseImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinderUtil;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityCounterLocalServiceImpl
	extends SocialActivityCounterLocalServiceBaseImpl {

	@Transactional(
		rollbackFor = SystemException.class,
		propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		long activityCounterId = counterLocalService.increment();

		SocialActivityCounter activityCounter =
			socialActivityCounterPersistence.create(activityCounterId);

		activityCounter.setGroupId(groupId);
		activityCounter.setCompanyId(group.getCompanyId());
		activityCounter.setClassNameId(classNameId);
		activityCounter.setClassPK(classPK);
		activityCounter.setName(name);
		activityCounter.setOwnerType(ownerType);
		activityCounter.setCurrentValue(currentValue);
		activityCounter.setTotalValue(totalValue);
		activityCounter.setStartPeriod(
			SocialCounterPeriodUtil.getStartPeriod());
		activityCounter.setEndPeriod(
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);

		socialActivityCounterPersistence.update(
			activityCounter, false);

		return activityCounter;
	}

	public void addActivityCounters(SocialActivity activity)
		throws PortalException, SystemException {

		if (!socialActivitySettingLocalService.isEnabled(
				activity.getGroupId(), activity.getClassNameId())) {

			return;
		}

		User user = userPersistence.findByPrimaryKey(activity.getUserId());

		SocialActivityDefinition activityDefinition =
			socialActivitySettingLocalService.getActivityDefinition(
				activity.getGroupId(), activity.getClassName(),
				activity.getType());

		if ((activityDefinition == null) ||
			!activityDefinition.isCounterEnabled()) {

			return;
		}

		SocialActivityProcessor activityProcessor =
			activityDefinition.getActivityProcessor();

		if (activityProcessor != null) {
			activityProcessor.processActivity(activity);
		}

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityDefinition.getActivityCounterDefinitions()) {

			if ((activityCounterDefinition.getIncrement() != 0) &&
				checkActivityLimit(
					user, activity, activityCounterDefinition)) {

				incrementActivityCounter(
					activity.getGroupId(), user, activity.getAssetEntry(),
					activityCounterDefinition);
			}
		}

		for (SocialAchievement achievement :
				activityDefinition.getAchievements()) {

			achievement.processActivity(activity);
		}

		incrementActivityCounter(
			activity.getGroupId(),
			PortalUtil.getClassNameId(User.class.getName()),
			activity.getUserId(),
			SocialActivityCounterConstants.NAME_USER_ACTIVITY,
			SocialActivityCounterConstants.TYPE_ACTOR, 1);

		incrementActivityCounter(
			activity.getGroupId(), activity.getClassNameId(),
			activity.getClassPK(),
			SocialActivityCounterConstants.NAME_ASSET_ACTIVITY,
			SocialActivityCounterConstants.TYPE_ASSET, 1);
	}

	public void deleteActivityCounters(AssetEntry assetEntry)
		throws PortalException, SystemException {

		if (assetEntry == null) {
			return;
		}

		SocialActivityCounter latestContributionActivityCounter =
			fetchLatestActivityCounter(
				assetEntry.getGroupId(),
				PortalUtil.getClassNameId(User.class.getName()),
				assetEntry.getUserId(),
				SocialActivityCounterConstants.NAME_CONTRIBUTION,
				SocialActivityCounterConstants.TYPE_CREATOR);

		SocialActivityCounter latestPopularityActivityCounter =
			fetchLatestActivityCounter(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK(),
				SocialActivityCounterConstants.NAME_POPULARITY,
				SocialActivityCounterConstants.TYPE_ASSET);

		if ((latestContributionActivityCounter != null) &&
			(latestPopularityActivityCounter != null)) {

			int startPeriod = SocialCounterPeriodUtil.getStartPeriod();

			if (latestContributionActivityCounter.getStartPeriod() !=
					startPeriod) {

				latestContributionActivityCounter = addNewPeriod(
					latestContributionActivityCounter);
			}

			if (latestPopularityActivityCounter.getStartPeriod() ==
					startPeriod) {

				latestContributionActivityCounter.setCurrentValue(
					latestContributionActivityCounter.getCurrentValue() -
						latestPopularityActivityCounter.getCurrentValue());
			}

			latestContributionActivityCounter.setTotalValue(
				latestContributionActivityCounter.getTotalValue() -
					latestPopularityActivityCounter.getTotalValue());

			socialActivityCounterPersistence.update(
				latestContributionActivityCounter, false);
		}

		deleteActivityCounters(
			assetEntry.getClassNameId(), assetEntry.getClassPK());

		socialActivityLimitPersistence.removeByC_C(
			assetEntry.getClassNameId(), assetEntry.getClassPK());
	}

	public void deleteActivityCounters(long classNameId, long classPK)
		throws SystemException {

		socialActivityCounterPersistence.removeByC_C(classNameId, classPK);
	}

	public SocialActivityCounter fetchLatestActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType,
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);
	}

	public List<SocialActivityCounter> getActivityCounterDistribution(
			long groupId, String name, int offset,
			boolean includeCurrentPeriod)
		throws SystemException {

		if (includeCurrentPeriod) {
			offset = offset - 1;
		}

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(-offset);

		int endPeriod = SocialActivityCounterConstants.END_PERIOD_UNDEFINED;

		if (!includeCurrentPeriod) {
			endPeriod = SocialCounterPeriodUtil.getStartPeriod() - 1;
		}

		return getActivityCounterDistribution(
			groupId, name, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getActivityCounterDistribution(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		return socialActivityCounterFinder.findAC_ByG_N_S_E_2(
			groupId, name, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getActivityCounters(
			long groupId, String name, int offset,
			boolean includeCurrentPeriod)
		throws SystemException {

		if (includeCurrentPeriod) {
			offset = offset - 1;
		}

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(-offset);

		int endPeriod = -1;

		if (!includeCurrentPeriod) {
			endPeriod = SocialCounterPeriodUtil.getStartPeriod() - 1;
		}

		return getActivityCounters(groupId, name, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		return socialActivityCounterFinder.findAC_ByG_N_S_E_1(
			groupId, name, startPeriod, endPeriod);
	}

	public SocialActivityCounter getLatestActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType)
		throws NoSuchActivityCounterException, SystemException {

		return socialActivityCounterPersistence.findByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType,
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);
	}

	public int getUserActivityCounters(long groupId, String[] rankingNames)
		throws SystemException {

		return SocialActivityCounterFinderUtil.countU_ByG_N(
			groupId, rankingNames);
	}

	public List<Tuple> getUserActivityCounters(
			long groupId, String[] rankingNames, String[] selectedNames,
			int start, int end)
		throws SystemException {

		List<Long> userIds = socialActivityCounterFinder.findU_ByG_N(
			groupId, rankingNames, start, end);

		if (userIds.isEmpty()) {
			return Collections.emptyList();
		}

		List<Tuple> userActivityCounters = new ArrayList<Tuple>();

		List<SocialActivityCounter> activityCounters =
			SocialActivityCounterFinderUtil.findAC_By_G_C_C_N_S_E(
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

				userActivityCounters.add(userActivityCounter);
			}

			activityCountersMap.put(activityCounter.getName(), activityCounter);
		}

		return userActivityCounters;
	}

	public void incrementUserAchievementCounter(long userId, long groupId)
		throws PortalException, SystemException {

		incrementActivityCounter(
			groupId, PortalUtil.getClassNameId(User.class.getName()), userId,
			SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT,
			SocialActivityCounterConstants.TYPE_ACTOR, 1);
	}

	protected SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int overallValue)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = null;

		try {
			activityCounter = addActivityCounter(
				groupId, classNameId, classPK, name, ownerType, 0,
				overallValue);
		}
		catch (SystemException se) {
			activityCounter = fetchLatestActivityCounter(
				groupId, classNameId, classPK, name, ownerType);
		}

		return activityCounter;
	}

	protected SocialActivityCounter addNewPeriod(
			SocialActivityCounter activityCounter)
		throws PortalException, SystemException {

		if (activityCounter == null) {
			return null;
		}

		activityCounter.setEndPeriod(
			SocialCounterPeriodUtil.getStartPeriod() - 1);

		socialActivityCounterPersistence.update(activityCounter, false);

		return addActivityCounter(
			activityCounter.getGroupId(), activityCounter.getClassNameId(),
			activityCounter.getClassPK(), activityCounter.getName(),
			activityCounter.getOwnerType(), activityCounter.getTotalValue());
	}

	protected boolean checkActivityLimit(
			User user, SocialActivity activity,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		if (activityCounterDefinition.getLimitValue() == 0) {
			return true;
		}

		SocialActivityLimit activityLimit =
			socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
				activity.getGroupId(), user.getUserId(),
				activity.getClassNameId(), activity.getClassPK(),
				activity.getType(), activityCounterDefinition.getName());

		if (activityLimit == null) {
			try {
				activityLimit =
					socialActivityLimitLocalService.addActivityLimit(
						user.getUserId(), activity.getGroupId(),
						activity.getClassNameId(), activity.getClassPK(),
						activity.getType(), activityCounterDefinition.getName(),
						activityCounterDefinition.getLimitPeriod());
			}
			catch (SystemException se) {
				activityLimit =
					socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
						activity.getGroupId(), user.getUserId(),
						activity.getClassNameId(), activity.getClassPK(),
						activity.getType(),
						activityCounterDefinition.getName());

				if (activityLimit == null) {
					throw se;
				}
			}
		}

		int count = activityLimit.getCount(
			activityCounterDefinition.getLimitPeriod());

		if (count < activityCounterDefinition.getLimitValue()) {
			activityLimit.setCount(
				activityCounterDefinition.getLimitPeriod(), count + 1);

			socialActivityLimitPersistence.update(activityLimit, false);

			return true;
		}

		return false;
	}

	protected void incrementActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int increment)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = fetchLatestActivityCounter(
			groupId, classNameId, classPK, name, ownerType);

		if (activityCounter == null) {
			activityCounter = addActivityCounter(
				groupId, classNameId, classPK, name, ownerType, 0);
		}

		if (!activityCounter.isActivePeriod()) {
			activityCounter = addNewPeriod(activityCounter);
		}

		activityCounter.setCurrentValue(
			activityCounter.getCurrentValue() + increment);
		activityCounter.setTotalValue(
			activityCounter.getTotalValue() + increment);

		socialActivityCounterPersistence.update(activityCounter, false);
	}

	protected void incrementActivityCounter(
			long groupId, User user, AssetEntry assetEntry,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		int ownerType = activityCounterDefinition.getOwnerType();
		long userClassNameId = PortalUtil.getClassNameId(User.class.getName());

		if (ownerType == SocialActivityCounterConstants.TYPE_ACTOR) {
			incrementActivityCounter(
				groupId, userClassNameId, user.getUserId(),
				activityCounterDefinition.getName(), ownerType,
				activityCounterDefinition.getIncrement());
		}
		else if (ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			incrementActivityCounter(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK(),
				activityCounterDefinition.getName(), ownerType,
				activityCounterDefinition.getIncrement());
		}
		else {
			incrementActivityCounter(
				groupId, userClassNameId, assetEntry.getUserId(),
				activityCounterDefinition.getName(), ownerType,
				activityCounterDefinition.getIncrement());
		}
	}

}