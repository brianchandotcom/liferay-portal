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

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
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
import com.liferay.portlet.social.service.SocialActivityCounterLocalService;
import com.liferay.portlet.social.service.base.SocialActivityCounterLocalServiceBaseImpl;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterFinderUtil;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 * @author Shuyang Zhou
 */
public class SocialActivityCounterLocalServiceImpl
	extends SocialActivityCounterLocalServiceBaseImpl {

	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod)
		throws PortalException, SystemException {

		return addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, currentValue,
			totalValue, startPeriod, endPeriod, 0, 0);
	}

	public SocialActivityCounter addActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod, long previousActivityCounterId, int periodLength)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = null;

		String lockKey = getLockKey(
			groupId, classNameId, classPK, name, ownerType);

		Lock lock = null;

		while (true) {
			try {
				lock = lockLocalService.lock(
					SocialActivityCounter.class.getName(), lockKey, lockKey,
					false);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to acquire activity counter lock. Retrying.");
				}

				continue;
			}

			if (lock.isNew()) {
				try {
					DB db = DBFactoryUtil.getDB();

					String dbType = db.getType();

					if (dbType.equals(DB.TYPE_HYPERSONIC)) {

						// LPS-25408

						activityCounter = createActivityCounter(
							groupId, classNameId, classPK, name, ownerType,
							currentValue, totalValue, startPeriod, endPeriod,
							previousActivityCounterId, periodLength);
					}
					else {
						activityCounter =
							socialActivityCounterLocalService.
								createActivityCounter(
									groupId, classNameId, classPK, name,
									ownerType, currentValue, totalValue,
									startPeriod, endPeriod,
									previousActivityCounterId, periodLength);

					}
				}
				finally {
					lockLocalService.unlock(
						SocialActivityCounter.class.getName(), lockKey, lockKey,
						false);
				}

				break;
			}

			Date createDate = lock.getCreateDate();

			if ((System.currentTimeMillis() - createDate.getTime()) >=
					PropsValues.SOCIAL_ACTIVITY_COUNTER_LOCK_TIMEOUT) {

				lockLocalService.unlock(
					SocialActivityCounter.class.getName(), lockKey,
					lock.getOwner(), false);

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Forcibly removed lock " + lock + ". See " +
							PropsKeys.SOCIAL_ACTIVITY_COUNTER_LOCK_TIMEOUT);
				}
			}
			else {
				try {
					Thread.sleep(
						PropsValues.SOCIAL_ACTIVITY_COUNTER_LOCK_RETRY_DELAY);
				}
				catch (InterruptedException ie) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Interrupted while waiting to reacquire lock", ie);
					}
				}
			}
		}

		return activityCounter;
	}

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

		if ((activityDefinition == null) || !activityDefinition.isEnabled()) {
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

		for (SocialActivityCounterDefinition activityCounterDefinition :
				activityDefinition.getActivityCounterDefinitions()) {

			if (addActivityCounter(
					user, assetEntryUser, activityCounterDefinition) &&
				checkActivityLimit(user, activity, activityCounterDefinition)) {

				incrementActivityCounter(
					activity.getGroupId(), user, activity.getAssetEntry(),
					activityCounterDefinition);
			}
		}

		for (SocialAchievement achievement :
				activityDefinition.getAchievements()) {

			achievement.processActivity(activity);
		}

		if (!user.isDefaultUser() && user.isActive()) {
			incrementActivityCounter(
				activity.getGroupId(),
				PortalUtil.getClassNameId(User.class.getName()),
				activity.getUserId(),
				SocialActivityCounterConstants.NAME_USER_ACTIVITIES,
				SocialActivityCounterConstants.TYPE_ACTOR, 1,
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
		}

		if (!assetEntryUser.isDefaultUser() && assetEntryUser.isActive()) {
			incrementActivityCounter(
				activity.getGroupId(), activity.getClassNameId(),
				activity.getClassPK(),
				SocialActivityCounterConstants.NAME_ASSET_ACTIVITIES,
				SocialActivityCounterConstants.TYPE_ASSET, 1,
				SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
		}
	}

	/**
	 * @deprecated {@link #createActivityCounter(long, long, long, String, int,
	 *             int, int, int, int, long, int)}
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter createActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod)
		throws PortalException, SystemException {

		return createActivityCounter(
			groupId, classNameId, classPK, name, ownerType, currentValue,
			totalValue, startPeriod, endPeriod, 0, 0);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SocialActivityCounter createActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int currentValue, int totalValue, int startPeriod,
			int endPeriod, long previousActivityCounterId, int periodLength)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = null;

		if (previousActivityCounterId != 0) {
			activityCounter =
				socialActivityCounterPersistence.findByPrimaryKey(
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

			socialActivityCounterPersistence.update(activityCounter, false);
		}

		activityCounter =
			socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
				groupId, classNameId, classPK, name, ownerType, endPeriod,
				false);

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
		activityCounter.setCurrentValue(currentValue);
		activityCounter.setTotalValue(totalValue);
		activityCounter.setStartPeriod(startPeriod);
		activityCounter.setEndPeriod(endPeriod);
		activityCounter.setActive(true);

		socialActivityCounterPersistence.update(activityCounter, false);

		return activityCounter;
	}

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

	public void disableActivityCounters(long classNameId, long classPK)
		throws PortalException, SystemException {

		String className = PortalUtil.getClassName(classNameId);

		disableActivityCounters(className, classPK);
	}

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

				socialActivityCounterPersistence.update(activityCounter, false);
			}
		}

		clearFinderCache();
	}

	public void enableActivityCounters(long classNameId, long classPK)
		throws PortalException, SystemException {

		String className = PortalUtil.getClassName(classNameId);

		enableActivityCounters(className, classPK);
	}

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

				socialActivityCounterPersistence.update(activityCounter, false);
			}
		}

		clearFinderCache();
	}

	public SocialActivityCounter fetchActivityCounterByEndPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int endPeriod)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType, endPeriod);
	}

	public SocialActivityCounter fetchActivityCounterByStartPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_S(
			groupId, classNameId, classPK, name, ownerType, startPeriod);
	}

	public SocialActivityCounter fetchLatestActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType)
		throws SystemException {

		return socialActivityCounterPersistence.fetchByG_C_C_N_O_E(
			groupId, classNameId, classPK, name, ownerType,
			SocialActivityCounterConstants.END_PERIOD_UNDEFINED);
	}

	public List<SocialActivityCounter> getOffsetActivityCounters(
			long groupId, String name, int startOffset, int endOffset)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getPeriodActivityCounters(groupId, name, startPeriod, endPeriod);
	}

	public List<SocialActivityCounter> getOffsetDistributionActivityCounters(
			long groupId, String name, int startOffset, int endOffset)
		throws SystemException {

		int startPeriod = SocialCounterPeriodUtil.getStartPeriod(startOffset);
		int endPeriod = SocialCounterPeriodUtil.getEndPeriod(endOffset);

		return getPeriodDistributionActivityCounters(
			groupId, name, startPeriod, endPeriod);
	}

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

	public List<SocialActivityCounter> getPeriodDistributionActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		int offset = SocialCounterPeriodUtil.getOffset(endPeriod);

		int periodLength = SocialCounterPeriodUtil.getPeriodLength(offset);

		return socialActivityCounterFinder.findAC_ByG_N_S_E_2(
			groupId, name, startPeriod, endPeriod, periodLength);
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

	public int getUserActivityCountersCount(long groupId, String[] rankingNames)
		throws SystemException {

		return SocialActivityCounterFinderUtil.countU_ByG_N(
			groupId, rankingNames);
	}

	public void incrementUserAchievementCounter(long userId, long groupId)
		throws PortalException, SystemException {

		incrementActivityCounter(
			groupId, PortalUtil.getClassNameId(User.class.getName()), userId,
			SocialActivityCounterConstants.NAME_USER_ACHIEVEMENTS,
			SocialActivityCounterConstants.TYPE_ACTOR, 1,
			SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM);
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
			latestContributionActivityCounter, false);
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
			socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
				activity.getGroupId(), user.getUserId(),
				activity.getClassNameId(), classPK, activity.getType(),
				activityCounterDefinition.getName());

		if (activityLimit == null) {
			try {
				activityLimit =
					socialActivityLimitLocalService.addActivityLimit(
						user.getUserId(), activity.getGroupId(),
						activity.getClassNameId(), classPK, activity.getType(),
						activityCounterDefinition.getName(),
						activityCounterDefinition.getLimitPeriod());
			}
			catch (SystemException se) {
				activityLimit =
					socialActivityLimitPersistence.fetchByG_U_C_C_A_A(
						activity.getGroupId(), user.getUserId(),
						activity.getClassNameId(), classPK, activity.getType(),
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

	protected void clearFinderCache() {
		PortalCache portalCache = MultiVMPoolUtil.getCache(
			SocialActivityCounterFinder.class.getName());

		portalCache.removeAll();
	}

	protected String getLockKey(
		long groupId, long classNameId, long classPK, String name,
		int ownerType) {

		StringBundler sb = new StringBundler(7);

		sb.append(StringUtil.toHexString(groupId));
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(classNameId));
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(classPK));
		sb.append(StringPool.POUND);
		sb.append(name);

		return sb.toString();
	}

	protected void incrementActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int increment, int periodLength)
		throws PortalException, SystemException {

		SocialActivityCounter activityCounter = fetchLatestActivityCounter(
			groupId, classNameId, classPK, name, ownerType);

		if (activityCounter == null) {
			activityCounter = addActivityCounter(
				groupId, classNameId, classPK, name, ownerType, 0, 0,
				SocialCounterPeriodUtil.getStartPeriod(),
				SocialActivityCounterConstants.END_PERIOD_UNDEFINED);

			if (periodLength > 0) {
				activityCounter.setStartPeriod(
					SocialCounterPeriodUtil.getActivityDay());
			}
		}

		if (!activityCounter.isActivePeriod(periodLength)) {
			activityCounter = addActivityCounter(
				activityCounter.getGroupId(), activityCounter.getClassNameId(),
				activityCounter.getClassPK(), activityCounter.getName(),
				activityCounter.getOwnerType(), 0,
				activityCounter.getTotalValue(),
				SocialCounterPeriodUtil.getStartPeriod(),
				SocialActivityCounterConstants.END_PERIOD_UNDEFINED,
				activityCounter.getActivityCounterId(), periodLength);
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
				activityCounterDefinition.getIncrement(),
				activityCounterDefinition.getPeriodLength());
		}
		else if (ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			incrementActivityCounter(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK(),
				activityCounterDefinition.getName(), ownerType,
				activityCounterDefinition.getIncrement(),
				activityCounterDefinition.getPeriodLength());
		}
		else {
			incrementActivityCounter(
				groupId, userClassNameId, assetEntry.getUserId(),
				activityCounterDefinition.getName(), ownerType,
				activityCounterDefinition.getIncrement(),
				activityCounterDefinition.getPeriodLength());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialActivityCounterLocalService.class);

}