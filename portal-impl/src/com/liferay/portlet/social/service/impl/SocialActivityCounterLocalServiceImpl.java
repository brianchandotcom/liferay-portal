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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
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
import com.liferay.portlet.social.service.base.SocialActivityCounterLocalServiceBaseImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinderUtil;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

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
			long companyId, long groupId, long classNameId, long classPK,
			int ownerType, String counterName, int currentValue, int totalValue)
		throws SystemException {

		long activityCounterId = counterLocalService.increment();

		SocialActivityCounter activityCounter =
			socialActivityCounterPersistence.create(activityCounterId);

		activityCounter.setGroupId(groupId);
		activityCounter.setCompanyId(companyId);
		activityCounter.setClassNameId(classNameId);
		activityCounter.setClassPK(classPK);
		activityCounter.setName(counterName);
		activityCounter.setOwnerType(ownerType);
		activityCounter.setCurrentValue(currentValue);
		activityCounter.setTotalValue(totalValue);
		activityCounter.setStartPeriod(
			SocialCounterPeriodUtil.getStartPeriod());
		activityCounter.setEndPeriod(-1);

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

		if (activityDefinition == null ||
			!activityDefinition.isCounterEnabled()) {

			return;
		}

		// Activity Handler

		if (activityDefinition.getActivityProcessor() != null) {
			activityDefinition.getActivityProcessor().processActivity(activity);
		}

		// Counters

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityDefinition.getActivityCounterDefinitions()) {

			if ((activityCounterDefinition.getIncrement() != 0) &&
				(checkLimit(user, activity, activityCounterDefinition))) {

				incrementCounter(
					activity.getGroupId(), user, activity.getAssetEntry(),
					activityCounterDefinition);
			}
		}

		// Achievements

		for (SocialAchievement achievement :
				activityDefinition.getAchievements()) {

			achievement.processActivity(activity);
		}

		// Activity counter

		incrementCounter(
			activity.getGroupId(), activity.getClassNameId(),
			activity.getClassPK(), SocialActivityCounterConstants.TYPE_ASSET,
			SocialActivityCounterConstants.NAME_ASSET_ACTIVITY, 1);

		incrementCounter(
			activity.getGroupId(),
			PortalUtil.getClassNameId(User.class.getName()),
			activity.getUserId(), SocialActivityCounterConstants.TYPE_ACTOR,
			SocialActivityCounterConstants.NAME_USER_ACTIVITY, 1);
	}

	public void deleteActivityCounters(AssetEntry assetEntry)
		throws SystemException {

		if (assetEntry == null) {
			return;
		}

		// Counters

		SocialActivityCounter lastPopularityCounter = fetchLastCounter(
			assetEntry.getGroupId(), assetEntry.getClassNameId(),
			assetEntry.getClassPK(), SocialActivityCounterConstants.TYPE_ASSET,
			SocialActivityCounterConstants.NAME_POPULARITY);

		SocialActivityCounter lastContributionCounter = fetchLastCounter(
			assetEntry.getGroupId(),
			PortalUtil.getClassNameId(User.class.getName()),
			assetEntry.getUserId(), SocialActivityCounterConstants.TYPE_CREATOR,
			SocialActivityCounterConstants.NAME_CONTRIBUTION);

		if ((lastContributionCounter != null) &&
			(lastPopularityCounter != null)) {

			int startPeriod = SocialCounterPeriodUtil.getStartPeriod();

			if (lastContributionCounter.getStartPeriod() != startPeriod) {
				lastContributionCounter = createNewCounterPeriod(
					lastContributionCounter);
			}

			lastContributionCounter.setTotalValue(
				lastContributionCounter.getTotalValue() -
					lastPopularityCounter.getTotalValue());

			if (lastPopularityCounter.getStartPeriod() == startPeriod) {
				lastContributionCounter.setCurrentValue(
					lastContributionCounter.getCurrentValue() -
						lastPopularityCounter.getCurrentValue());
			}

			socialActivityCounterPersistence.update(
				lastContributionCounter, false);
		}

		deleteActivityCounters(
			assetEntry.getClassNameId(), assetEntry.getClassPK());

		// Limits

		socialActivityLimitPersistence.removeByC_C(
			assetEntry.getClassNameId(), assetEntry.getClassPK());
	}

	public void deleteActivityCounters(long classNameId, long classPK)
		throws SystemException {

		socialActivityCounterPersistence.removeByC_C(classNameId, classPK);
	}

	public SocialActivityCounter fetchLastCounter(
			long groupId, long classNameId, long classPK, int ownerType,
			String counterName)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, counterName, ownerType, -1);
	}

	public SocialActivityCounter findLastCounter(
			long groupId, long classNameId, long classPK, int ownerType,
			String counterName)
		throws NoSuchActivityCounterException, SystemException {

		return socialActivityCounterPersistence.findByG_C_C_N_O_E(
			groupId, classNameId, classPK, counterName, ownerType, -1);
	}

	public List<SocialActivityCounter> getCounterDistribution(
			long groupId, String counterName, int offset,
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

		return getCounterDistribution(
			groupId, counterName, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getCounterDistribution(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		List<SocialActivityCounter> activityCounters =
			socialActivityCounterFinder.findCounterDistributionByNameAndPeriod(
				groupId, counterName, startPeriod, endPeriod);

		return activityCounters;
	}

	public List<SocialActivityCounter> getCounters(
			long groupId, String counterName, int offset,
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

		return getCounters(groupId, counterName, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getCounters(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		return socialActivityCounterFinder.findCountersByNameAndPeriod(
			groupId, counterName, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getTagsByCounter(
			long groupId, String counterName, int offset,
			boolean includeCurrentPeriod)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(-offset);
		int endPeriod = -1;

		if (!includeCurrentPeriod) {
			endPeriod = SocialCounterPeriodUtil.getStartPeriod() - 1;
		}

		return getTagsByCounter(groupId, counterName, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getTagsByCounter(
			long groupId, String counterName, int startPeriod, int endPeriod)
		throws SystemException {

		return socialActivityCounterFinder.findTagsByCounter(
			groupId, counterName, startPeriod, endPeriod);
	}

	public Map<Long, List<SocialActivityCounter>> getTopUsersByCounters(
			long groupId, String[] orderBy, String[] counterNames,
			int start, int end)
		throws SystemException {

		Map<Long, List<SocialActivityCounter>> topUserCounters =
			new HashMap<Long, List<SocialActivityCounter>>();

		List<Long> userIds =
			SocialActivityCounterFinderUtil.findTopUsersByCounters(
				groupId, orderBy, start, end);

		if (userIds.isEmpty()) {
			return topUserCounters;
		}

		List<SocialActivityCounter> activityCounters =
			SocialActivityCounterFinderUtil.findUserStatsByCounters(
				groupId, userIds, counterNames, -1, -1);

		for (SocialActivityCounter activityCounter : activityCounters) {
			long userId = activityCounter.getClassPK();

			if (!topUserCounters.containsKey(userId)) {
				topUserCounters.put(
					userId, new ArrayList<SocialActivityCounter>());
			}

			List<SocialActivityCounter> userCounters = topUserCounters.get(
				userId);

			userCounters.add(activityCounter);
		}

		return topUserCounters;
	}

	public int getTopUsersByCountersCount(long groupId, String[] orderBy)
		throws SystemException {

		return SocialActivityCounterFinderUtil.countTopUsersByCounters(
			groupId, orderBy);
	}

	public void incrementUserAchievementsCounter(long groupId, long userId)
		throws PortalException, SystemException {

		incrementCounter(
			groupId, PortalUtil.getClassNameId(User.class.getName()), userId,
			SocialActivityCounterConstants.TYPE_ACTOR,
			SocialActivityCounterConstants.NAME_USER_ACHIEVEMENT, 1);
	}

	protected boolean checkLimit(
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

	protected SocialActivityCounter createActivityCounter(
			long companyId, long groupId, long classNameId,	long classPK,
			int ownerType, String counterName, int overallValue)
		throws SystemException {

		SocialActivityCounter activityCounter = null;

		try {
			activityCounter =
				getSocialActivityCounterLocalService().addActivityCounter(
					companyId, groupId, classNameId, classPK, ownerType,
					counterName, 0, overallValue);
		}
		catch (SystemException se) {
			activityCounter = fetchLastCounter(
				groupId, classNameId, classPK, ownerType, counterName);
		}

		return activityCounter;
	}

	protected SocialActivityCounter createNewCounterPeriod(
			SocialActivityCounter oldCounter)
		throws SystemException {

		if (oldCounter == null) {
			return null;
		}

		oldCounter.setEndPeriod(SocialCounterPeriodUtil.getStartPeriod() - 1);

		socialActivityCounterPersistence.update(oldCounter, false);

		return createActivityCounter(
			oldCounter.getCompanyId(), oldCounter.getGroupId(),
			oldCounter.getClassNameId(), oldCounter.getClassPK(),
			oldCounter.getOwnerType(), oldCounter.getName(),
			oldCounter.getTotalValue());
	}

	protected void incrementCounter(
			long groupId, long classNameId, long classPK, int ownerType,
			String counterName, int increment)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = fetchLastCounter(
			groupId, classNameId, classPK, ownerType, counterName);

		if (activityCounter == null) {
			Group group = groupPersistence.findByPrimaryKey(groupId);

			activityCounter = createActivityCounter(
				group.getCompanyId(), groupId, classNameId, classPK, ownerType,
				counterName, 0);
		}

		if (!activityCounter.isActivePeriod()) {
			activityCounter = createNewCounterPeriod(activityCounter);
		}

		activityCounter.setCurrentValue(
			activityCounter.getCurrentValue() + increment);
		activityCounter.setTotalValue(
			activityCounter.getTotalValue() + increment);

		socialActivityCounterPersistence.update(activityCounter, false);
	}

	protected void incrementCounter(
			long groupId, User user, AssetEntry assetEntry,
			SocialActivityCounterDefinition activityCounterDefinition)
		throws PortalException, SystemException {

		int ownerType = activityCounterDefinition.getOwnerType();
		long userClassNameId = PortalUtil.getClassNameId(User.class.getName());

		if (ownerType == SocialActivityCounterConstants.TYPE_ACTOR) {
			incrementCounter(
				groupId, userClassNameId, user.getUserId(), ownerType,
				activityCounterDefinition.getName(),
				activityCounterDefinition.getIncrement());
		}
		else if (ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			incrementCounter(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK(),
				ownerType, activityCounterDefinition.getName(),
				activityCounterDefinition.getIncrement());
		}
		else {
			incrementCounter(
				groupId, userClassNameId, assetEntry.getUserId(), ownerType,
				activityCounterDefinition.getName(),
				activityCounterDefinition.getIncrement());
		}
	}

}