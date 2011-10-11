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
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchActivityStatsEntryException;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.SocialActivityStatsEntry;
import com.liferay.portlet.social.model.SocialActivityUserStats;
import com.liferay.portlet.social.service.base.SocialActivityStatsEntryLocalServiceBaseImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityStatsEntryFinderUtil;
import com.liferay.portlet.social.util.SocialStatsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityStatsEntryLocalServiceImpl
	extends SocialActivityStatsEntryLocalServiceBaseImpl {

	public void addActivityStats(SocialActivity activity)
		throws PortalException, SystemException {

		if (!socialActivitySettingLocalService.isModelEnabled(
				activity.getGroupId(), activity.getClassNameId())) {

			return;
		}

		User user = userPersistence.findByPrimaryKey(activity.getUserId());

		SocialActivityDefinition activityDefinition =
			socialActivitySettingLocalService.getActivityDefinition(
				activity.getGroupId(), activity.getClassName(),
				activity.getType());

		if (activityDefinition == null ||
			!activityDefinition.isStatsEnabled()) {

			return;
		}

		// Activity Handler

		if (activityDefinition.getActivityHandler() != null) {
			activityDefinition.getActivityHandler().processActivity(activity);
		}

		// Counters

		for (SocialActivityCounter counter : activityDefinition.getCounters()) {
			if (counter.getIncrement() != 0) {
				if (checkLimit(user, activity, counter)) {
					incrementCounter(
						activity.getGroupId(), user, activity.getAssetEntry(),
						counter);
				}
			}
		}

		// Achievements

		List<SocialAchievement> achievements =
			activityDefinition.getAchievements();

		for (SocialAchievement achievement : achievements) {
			achievement.processActivity(activity);
		}

		// Activity counter

		incrementStatEntry(
			activity.getGroupId(), activity.getClassNameId(),
			activity.getClassPK(), SocialActivityConstants.OWNER_TYPE_ASSET,
			SocialActivityConstants.STAT_ASSET_ACTIVITY, 1);

		incrementStatEntry(
			activity.getGroupId(),
			PortalUtil.getClassNameId(User.class.getName()),
			activity.getUserId(), SocialActivityConstants.OWNER_TYPE_ACTOR,
			SocialActivityConstants.STAT_USER_ACTIVITY, 1);
	}

	@Transactional(
		rollbackFor = SystemException.class,
		propagation = Propagation.REQUIRES_NEW)
	public SocialActivityStatsEntry addActivityStatsEntry(
			long companyId, long groupId, long classNameId, long classPK,
			int classType, String statName, int currentValue, int overallValue)
		throws SystemException {

		long id = counterLocalService.increment();

		SocialActivityStatsEntry newActivityStatsEntry =
			socialActivityStatsEntryPersistence.create(id);

		newActivityStatsEntry.setCompanyId(companyId);
		newActivityStatsEntry.setGroupId(groupId);
		newActivityStatsEntry.setClassPK(classPK);
		newActivityStatsEntry.setClassNameId(classNameId);
		newActivityStatsEntry.setClassType(classType);
		newActivityStatsEntry.setStatName(statName);
		newActivityStatsEntry.setCurrentValue(currentValue);
		newActivityStatsEntry.setOverallValue(overallValue);
		newActivityStatsEntry.setStatPeriodStart(
			SocialStatsUtil.getCurrentPeriodStart());
		newActivityStatsEntry.setStatPeriodEnd(-1);

		socialActivityStatsEntryPersistence.update(
			newActivityStatsEntry, false);

		return newActivityStatsEntry;
	}

	public void deleteActivityStats(long classNameId, long classPK)
		throws SystemException {

		AssetEntry assetEntry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (assetEntry != null) {
			socialActivityLimitPersistence.removeByCN_CP(
				assetEntry.getClassNameId(), assetEntry.getClassPK());

			SocialActivityStatsEntry lastAssetStatEntry = fetchLastEntry(
				assetEntry.getGroupId(),
				assetEntry.getClassNameId(),
				assetEntry.getClassPK(),
				SocialActivityConstants.OWNER_TYPE_ASSET,
				SocialActivityConstants.STAT_POPULARITY);

			SocialActivityStatsEntry lastContributionEntry = fetchLastEntry(
				assetEntry.getGroupId(),
				PortalUtil.getClassNameId(User.class.getName()),
				assetEntry.getUserId(),
				SocialActivityConstants.OWNER_TYPE_CREATOR,
				SocialActivityConstants.STAT_CONTRIBUTION);

			if (lastContributionEntry != null && lastAssetStatEntry != null) {
				if (lastContributionEntry.getStatPeriodStart()
						!= SocialStatsUtil.getCurrentPeriodStart()) {

					lastContributionEntry = createNewStatPeriod(
						lastContributionEntry);
				}

				lastContributionEntry.setOverallValue(
					lastContributionEntry.getOverallValue() -
						lastAssetStatEntry.getOverallValue());

				if (lastAssetStatEntry.getStatPeriodStart() ==
						SocialStatsUtil.getCurrentPeriodStart()) {

					lastContributionEntry.setCurrentValue(
						lastContributionEntry.getCurrentValue() -
							lastAssetStatEntry.getCurrentValue());
				}

				socialActivityStatsEntryPersistence.update(
					lastContributionEntry, false);
			}
		}

		socialActivityStatsEntryPersistence.removeByCN_CP(
			classNameId, classPK);
	}

	public SocialActivityStatsEntry fetchLastEntry(
			long groupId, long classNameId, long classPK, int classType,
			String statName)
		throws SystemException {

		return socialActivityStatsEntryPersistence.fetchByG_CN_CP_CT_SN_SPE(
			groupId, classNameId, classPK, classType, statName, -1);
	}

	public SocialActivityStatsEntry findLastEntry(
			long groupId, long classNameId, long classPK, int classType,
			String statName)
		throws NoSuchActivityStatsEntryException, SystemException {

		return socialActivityStatsEntryPersistence.findByG_CN_CP_CT_SN_SPE(
			groupId, classNameId, classPK, classType, statName, -1);
	}

	public List<SocialActivityStatsEntry> getStatsDistribution(
			long groupId, String statName, int lastPeriods,
			boolean includeCurrent)
		throws SystemException {

		if (includeCurrent) {
			lastPeriods = lastPeriods - 1;
		}

		int start = SocialStatsUtil.getStatPeriodStart(-lastPeriods);
		int end = -1;

		if (!includeCurrent) {
			end = SocialStatsUtil.getCurrentPeriodStart() - 1;
		}

		return getStatsDistribution(groupId, statName, start, end);
	}

	public List<SocialActivityStatsEntry> getStatsDistribution(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		List<SocialActivityStatsEntry> entries =
			socialActivityStatsEntryFinder.findStatsDistributionByNameAndPeriod(
				groupId, statName, statPeriodStart, statPeriodEnd);

		return entries;
	}

	public List<SocialActivityStatsEntry> getStatsEntries(
			long groupId, String statName, int lastPeriods,
			boolean includeCurrent)
		throws SystemException {

		if (includeCurrent) {
			lastPeriods = lastPeriods - 1;
		}

		int start = SocialStatsUtil.getStatPeriodStart(-lastPeriods);
		int end = -1;

		if (!includeCurrent) {
			end = SocialStatsUtil.getCurrentPeriodStart() - 1;
		}

		return getStatsEntries(groupId, statName, start, end);
	}

	public List<SocialActivityStatsEntry> getStatsEntries(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		List<SocialActivityStatsEntry> entries =
			new ArrayList<SocialActivityStatsEntry>();

		int currentPeriodStart = SocialStatsUtil.getCurrentPeriodStart();
		int start = statPeriodStart;

		if (start > currentPeriodStart) {
			start = currentPeriodStart;
		}

		if (statPeriodEnd >= currentPeriodStart || statPeriodEnd == -1) {
			if (start < currentPeriodStart) {
				entries.addAll(
					socialActivityStatsEntryFinder.findStatsByNameAndPeriod(
						groupId, statName, start, currentPeriodStart - 1));

				start = currentPeriodStart;
			}
		}

		entries.addAll(socialActivityStatsEntryFinder.findStatsByNameAndPeriod(
			groupId, statName, start, statPeriodEnd));

		return entries;
	}

	public List<SocialActivityStatsEntry> getTagsByCounter(
			long groupId, String statName, int lastPeriods,
			boolean includeCurrent)
		throws SystemException {

		if (includeCurrent) {
			lastPeriods = lastPeriods - 1;
		}

		int start = SocialStatsUtil.getStatPeriodStart(-lastPeriods);
		int end = -1;

		if (!includeCurrent) {
			end = SocialStatsUtil.getCurrentPeriodStart() - 1;
		}

		return getTagsByCounter(groupId, statName, start, end);
	}

	public List<SocialActivityStatsEntry> getTagsByCounter(
			long groupId, String statName, int statPeriodStart,
			int statPeriodEnd)
		throws SystemException {

		List<SocialActivityStatsEntry> entries =
			socialActivityStatsEntryFinder.findTagsByCounter(
				groupId, statName, statPeriodStart, statPeriodEnd);

		return entries;
	}

	public List<SocialActivityUserStats> getUserStatsByCounters(
			long groupId, String[] rankingCounters, String[] selectedCounters,
			int start, int end)
		throws SystemException {

		List<SocialActivityUserStats> userStatsList =
			new ArrayList<SocialActivityUserStats>();

		List<Long> userIds =
			SocialActivityStatsEntryFinderUtil.findTopUsersByCounters(
				groupId, rankingCounters, start, end);

		if (userIds.size() == 0) {
			return userStatsList;
		}

		List<SocialActivityStatsEntry> statsEntries =
			SocialActivityStatsEntryFinderUtil.findUserStatsByCounters(
				groupId, userIds, selectedCounters, -1, -1);

		long userId = 0;
		SocialActivityUserStats userStats = null;

		for (SocialActivityStatsEntry statEntry : statsEntries) {
			if (statEntry.getClassPK() != userId) {
				userId = statEntry.getClassPK();
				userStats = new SocialActivityUserStats();

				userStats.setUserId(userId);

				userStatsList.add(userStats);
			}

			userStats.addStat(
				new KeyValuePair(
					statEntry.getStatName(),
					String.valueOf(statEntry.getCurrentValue())));
		}

		return userStatsList;
	}

	public int getUserStatsByCountersCount(
			long groupId, String[] rankingCounters)
		throws SystemException {

		return SocialActivityStatsEntryFinderUtil.countTopUsersByCounters(
			groupId, rankingCounters);
	}

	public void incrementUserAchievementsCounter(long groupId, long userId)
		throws PortalException, SystemException {

		incrementStatEntry(
			groupId, PortalUtil.getClassNameId(User.class.getName()), userId,
			SocialActivityConstants.OWNER_TYPE_ACTOR,
			SocialActivityConstants.STAT_USER_ACHIEVEMENT, 1);
	}

	protected boolean checkLimit(
			User user, SocialActivity activity, SocialActivityCounter counter)
		throws PortalException, SystemException {

		if (counter.getLimit() == 0) {
			return true;
		}

		SocialActivityLimit activityLimit =
			socialActivityLimitPersistence.fetchByG_U_CN_CP_A_S(
				activity.getGroupId(), user.getUserId(),
				activity.getClassNameId(), activity.getClassPK(),
				activity.getType(), counter.getName());

		if (activityLimit == null) {
			try {
				Group group = groupPersistence.findByPrimaryKey(
					activity.getGroupId());

				activityLimit =
					socialActivityLimitLocalService.addActivityLimit(
						group.getCompanyId(), activity.getGroupId(),
						user.getUserId(), activity.getClassNameId(),
						activity.getClassPK(), activity.getType(),
						counter.getName(), counter.getLimitType());
			}
			catch (SystemException se) {
				activityLimit =
					socialActivityLimitPersistence.fetchByG_U_CN_CP_A_S(
						activity.getGroupId(), user.getUserId(),
						activity.getClassNameId(), activity.getClassPK(),
						activity.getType(), counter.getName());
			}
		}

		int actionCount = activityLimit.getCount(counter.getLimitType());

		if (actionCount >= counter.getLimit()) {
			return false;
		}

		activityLimit.setCount(counter.getLimitType(), actionCount + 1);

		socialActivityLimitPersistence.update(activityLimit, false);

		return true;
	}

	protected String createActionKey(String className, int activityKey) {
		return className + StringPool.UNDERLINE + activityKey;
	}

	protected SocialActivityStatsEntry createActivityStatEntry(
			final long companyId, final long groupId, final long classNameId,
			final long classPK, final int classType, final String statName,
			final int overallValue)
		throws SystemException {

		SocialActivityStatsEntry newEntry = null;

		try {
			newEntry =
				getSocialActivityStatsEntryLocalService().addActivityStatsEntry(
					companyId, groupId, classNameId, classPK, classType,
					statName, 0, overallValue);
		}
		catch (SystemException se) {
			newEntry = fetchLastEntry(
				groupId, classNameId, classPK, classType, statName);
		}

		return newEntry;
	}

	protected SocialActivityStatsEntry createNewStatPeriod(
			SocialActivityStatsEntry oldEntry)
		throws SystemException {

		int statPeriodStart = SocialStatsUtil.getCurrentPeriodStart();

		if (oldEntry != null) {
			oldEntry.setStatPeriodEnd(statPeriodStart - 1);

			socialActivityStatsEntryPersistence.update(oldEntry, false);

			return createActivityStatEntry(
				oldEntry.getCompanyId(), oldEntry.getGroupId(),
				oldEntry.getClassNameId(), oldEntry.getClassPK(),
				oldEntry.getClassType(), oldEntry.getStatName(),
				oldEntry.getOverallValue());
		}

		return null;
	}

	protected void incrementCounter(
			long groupId, User actor, AssetEntry assetEntry,
			SocialActivityCounter counter)
		throws PortalException, SystemException {

		int classType = counter.getOwnerType();
		long userClassNameId = PortalUtil.getClassNameId(User.class.getName());

		if (classType == SocialActivityConstants.OWNER_TYPE_ACTOR) {
			incrementStatEntry(
				groupId, userClassNameId, actor.getUserId(), classType,
				counter.getName(), counter.getIncrement());
		}
		else if (classType == SocialActivityConstants.OWNER_TYPE_ASSET) {
			incrementStatEntry(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK(),
				classType, counter.getName(), counter.getIncrement());
		}
		else {
			incrementStatEntry(
				groupId, userClassNameId, assetEntry.getUserId(), classType,
				counter.getName(), counter.getIncrement());
		}
	}

	protected void incrementStatEntry(
			long groupId, long classNameId, long classPK, int classType,
			String statName, int increment)
		throws PortalException, SystemException {

		SocialActivityStatsEntry statEntry = fetchLastEntry(
			groupId, classNameId, classPK, classType, statName);

		if (statEntry == null) {
			Group group = groupPersistence.findByPrimaryKey(groupId);

			statEntry = createActivityStatEntry(
				group.getCompanyId(), groupId, classNameId, classPK, classType,
				statName, 0);
		}

		if (!statEntry.isPeriodActive()) {
			statEntry = createNewStatPeriod(statEntry);
		}

		statEntry.setCurrentValue(statEntry.getCurrentValue() + increment);
		statEntry.setOverallValue(statEntry.getOverallValue() + increment);

		socialActivityStatsEntryPersistence.update(statEntry, false);
	}

}