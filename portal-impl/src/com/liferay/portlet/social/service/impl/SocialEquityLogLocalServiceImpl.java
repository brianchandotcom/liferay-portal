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

import com.liferay.ibm.icu.util.Calendar;
import com.liferay.ibm.icu.util.GregorianCalendar;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.SocialEquityIncrement;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.spring.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.NoSuchEquityAssetEntryException;
import com.liferay.portlet.social.model.SocialEquityAssetEntry;
import com.liferay.portlet.social.model.SocialEquityIncrementPayload;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.model.SocialEquityValue;
import com.liferay.portlet.social.service.base.SocialEquityLogLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * The Social Equity main service. All portlets should interact with this
 * service to use the Social Equity framework.
 *
 * <p>
 * This class is responsible for recording and removing social actions that
 * affect a user's social equity scores. It also contains methods for ranking
 * and cleanup.
 * </p>
 *
 * <p>
 * Social Equity actions always have an actor (the user executing the action)
 * and an asset (the target of the action). The asset can be identified by
 * either its primary key or a className/classPK pair.
 * </p>
 *
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquityLogLocalServiceImpl
	extends SocialEquityLogLocalServiceBaseImpl {

	/**
	 * Records the social equity action and adjusts social equity scores.
	 *
	 * @param      userId the primary key of the acting user
	 * @param      assetEntryId the primary key of the target asset entry
	 * @param      actionId the ID of the action
	 * @throws     PortalException if the asset entry could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated Replaced by {@link #addEquityLogs(long, long, String,
	 *             String)} to support the <code>extraData</code> parameter
	 */
	public void addEquityLogs(
			long userId, long assetEntryId, String actionId)
		throws PortalException, SystemException {

		addEquityLogs(userId, assetEntryId, actionId, StringPool.BLANK);
	}

	/**
	 * Records the social equity action and adjusts social equity scores based
	 * on the user's action done on the target asset entry.
	 *
	 * <p>
	 * The <code>extraData</code> parameter can contain further information
	 * about the action such as the file name for a download action. It is used
	 * to distinguish between otherwise equal actions, such as multiple
	 * downloads of message boards attachments.
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  assetEntryId the primary key of the target asset entry
	 * @param  actionId the ID of the action
	 * @param  extraData the extra data associated with the action
	 * @throws PortalException if the asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void addEquityLogs(
			long userId, long assetEntryId, String actionId, String extraData)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		AssetEntry assetEntry = assetEntryPersistence.findByPrimaryKey(
			assetEntryId);

		User assetEntryUser = null;

		try {
			assetEntryUser = userPersistence.findByPrimaryKey(
				assetEntry.getUserId());
		}
		catch (NoSuchUserException nsue) {
		}

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingLocalService.getEquitySettings(
				assetEntry.getGroupId(), assetEntry.getClassNameId(), actionId);

		for (SocialEquitySetting equitySetting : equitySettings) {
			if (isSocialEquityEnabled(
					assetEntry.getGroupId(), assetEntry.getClassName(),
					equitySetting.getType())) {

				addEquityLog(
					user, assetEntry, assetEntryUser, equitySetting, extraData);
			}
		}
	}

	/**
	 * Records the social equity action and adjusts social equity scores based
	 * on the user's action done on the target asset entry identified by the
	 * className/classPK pair.
	 *
	 * <p>
	 * The <code>extraData</code> parameter can contain further information
	 * about the action such as the file name for a download action. It is used
	 * to distinguish between otherwise equal actions, such as multiple
	 * downloads of message boards attachments.
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  className the class name of the target asset
	 * @param  classPK the primary key of the target asset (not the asset entry
	 *         referring to it)
	 * @param  actionId the ID of the action
	 * @param  extraData the extra data associated with the action
	 * @throws PortalException if the asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void addEquityLogs(
			long userId, String className, long classPK, String actionId,
			String extraData)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = null;

		try {
			assetEntry = assetEntryLocalService.getEntry(
				className, classPK);
		}
		catch (NoSuchEntryException nsee) {
			return;
		}

		addEquityLogs(userId, assetEntry.getEntryId(), actionId, extraData);
	}

	/**
	 * Inserts a new row for the asset entry into the
	 * <code>SocialEquityAssetEntry</code> table.
	 *
	 * <p>
	 * This method should not be used directly by portlets. It is made public
	 * so that it can be in its own transaction to safeguard against
	 * concurrency issues.
	 * </p>
	 *
	 * @param  assetEntry the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void addSocialEquityAssetEntry(AssetEntry assetEntry)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_ASSET_ENTRY);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$COMPANY_ID$]",
				"[$EQUITY_ASSET_ENTRY_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(assetEntry.getEntryId()),
				String.valueOf(assetEntry.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(assetEntry.getGroupId()),
				String.valueOf(assetEntry.getUserId())
			});

		runSQL(sql);
	}

	/**
	 * Inserts a new row for the user into the <code>SocialEquityUser</code>
	 * table.
	 *
	 * <p>
	 * This method should not be used directly by portlets. It is made public
	 * so that it can be in its own transaction to safeguard against
	 * concurrency issues.
	 * </p>
	 *
	 * @param  groupId the primary key of the group the user is currently
	 *         acting in
	 * @param  user the acting user
	 * @throws SystemException if a system exception occurred
	 */
	public void addSocialEquityUser(long groupId, User user)
		throws SystemException {

		String sql = CustomSQLUtil.get(_ADD_SOCIAL_EQUITY_USER);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$COMPANY_ID$]",
				"[$EQUITY_USER_ID$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(user.getCompanyId()),
				String.valueOf(counterLocalService.increment()),
				String.valueOf(groupId),
				String.valueOf(user.getUserId())
			});

		runSQL(sql);
	}

	/**
	 * This is a cleanup method to remove expired actions and any data
	 * associated with them.
	 *
	 * <p>
	 * <i>This method should normally only be called by the portal.</i>
	 * </p>
	 *
	 * <p>
	 * By default it is run by the scheduler once a day, but the frequency can
	 * be modified by overriding the
	 * <code>social.equity.equity.log.check.interval</code> property found in
	 * <code>portal.properties</code>.
	 * </p>
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void checkEquityLogs() throws SystemException {
		int validity = getEquityDate();

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		runCheckSQL(_CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ, validity);

		assetEntryPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_CQ, validity);
		runCheckSQL(_CHECK_SOCIAL_EQUITY_USER_PQ, validity);

		userPersistence.clearCache();

		runCheckSQL(_CHECK_SOCIAL_EQUITY_LOGS, validity);

		socialEquityLogPersistence.clearCache();

		updateRanks();
	}

	/**
	 * Removes all actions associated with the asset and adjusts equity scores
	 * accordingly.
	 *
	 * <p>
	 * This method is called by the <code>AssetEntry</code> service
	 * automatically when an asset entry is deleted.
	 * </p>
	 *
	 * @param  assetEntryId the primary key of the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void deactivateEquityLogs(long assetEntryId) throws SystemException {
		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		SocialEquityAssetEntry equityAssetEntry = null;

		try {
			equityAssetEntry =
				socialEquityAssetEntryPersistence.findByAssetEntryId(
					assetEntryId);

			socialEquityAssetEntryPersistence.removeByAssetEntryId(
				assetEntryId);
		}
		catch (NoSuchEquityAssetEntryException nseaee) {
			return;
		}

		User user = null;

		try {
			user = userPersistence.findByPrimaryKey(
				equityAssetEntry.getUserId());

			if (!user.isDefaultUser()) {
				SocialEquityValue equityValue = new SocialEquityValue(
					-equityAssetEntry.getInformationK(),
					-equityAssetEntry.getInformationB());

				SocialEquityIncrementPayload equityIncrementPayload =
					new SocialEquityIncrementPayload();

				equityIncrementPayload.setEquityValue(equityValue);
				equityIncrementPayload.setUser(user);

				incrementSocialEquityUser_CQ(
					equityAssetEntry.getGroupId(), user.getUserId(),
					equityIncrementPayload);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByAEI_T_A(
				assetEntryId, SocialEquitySettingConstants.TYPE_INFORMATION,
				true);

		for (SocialEquityLog equityLog : equityLogs) {
			equityLog.setActive(false);

			socialEquityLogPersistence.update(equityLog, false);
		}
	}

	/**
	 * Removes actions identified by the acting user, the action ID and the
	 * target asset's primary key.
	 *
	 * @param      userId the primary key of the acting user
	 * @param      assetEntryId the primary key of the target asset entry
	 * @param      actionId the ID of the action
	 * @throws     PortalException if the asset entry could not be found
	 * @throws     SystemException if a system exception occurred
	 * @deprecated Replaced by {@link #deactivateEquityLogs(long, String, long,
	 *             String, String)} to support the <code>extraData</code>
	 *             parameter
	 */
	public void deactivateEquityLogs(
			long userId, long assetEntryId, String actionId)
		throws PortalException, SystemException {

		AssetEntry assetEntry = assetEntryPersistence.findByPrimaryKey(
			assetEntryId);

		deactivateEquityLogs(
			userId, assetEntry.getClassName(), assetEntry.getClassPK(),
			actionId, StringPool.BLANK);
	}

	/**
	 * Removes actions identified by the acting user, the action ID and the
	 * target asset's className/classPK pair.
	 *
	 * @param      userId the primary key of the acting user
	 * @param      className the class name of the target asset
	 * @param      classPK the primary key of the target asset (not the asset
	 *             entry referring to it)
	 * @param      actionId the ID of the action
	 * @throws     PortalException if the asset entry cannot be retrieved
	 * @throws     SystemException if a system exception occurred
	 * @deprecated Replaced by {@link #deactivateEquityLogs(long, String, long,
	 *             String, String)} to support the <code>extraData</code>
	 *             parameter
	 */
	public void deactivateEquityLogs(
			long userId, String className, long classPK, String actionId)
		throws PortalException, SystemException {

		deactivateEquityLogs(
			userId, className, classPK, actionId, StringPool.BLANK);
	}

	/**
	 * Removes actions identified by the acting user, the action ID and the
	 * target asset's className/classPK pair.
	 *
	 * <p>
	 * The <code>extraData</code> parameter can be used to further identify the
	 * action.
	 * </p>
	 *
	 * @param  userId the primary key of the acting user
	 * @param  className the class name of the target asset
	 * @param  classPK the primary key of the target asset (not the asset entry
	 *         referring to it)
	 * @param  actionId the ID of the action
	 * @param  extraData the extra data associated with the action
	 * @throws PortalException if the asset entry cannot be retrieved
	 * @throws SystemException if a system exception occurred
	 */
	public void deactivateEquityLogs(
			long userId, String className, long classPK, String actionId,
			String extraData)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByU_AEI_AID_A_E(
				userId, assetEntry.getEntryId(), actionId, true, extraData);

		deactivateEquityLogs(equityLogs);
	}

	/**
	 * Removes actions identified by action ID done on an asset by any user.
	 *
	 * @param  className the class name of the target asset
	 * @param  classPK the primary key of the target asset (not the asset entry
	 *         referring to it)
	 * @param  actionId the ID of the action
	 * @param  extraData the extra data associated with the action
	 * @throws PortalException if the asset entry cannot be retrieved
	 * @throws SystemException if a system exception occurred
	 */
	public void deactivateEquityLogs(
			String className, long classPK, String actionId, String extraData)
		throws PortalException, SystemException {

		if (!PropsValues.SOCIAL_EQUITY_EQUITY_LOG_ENABLED) {
			return;
		}

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			className, classPK);

		List<SocialEquityLog> equityLogs =
			socialEquityLogPersistence.findByAEI_AID_A_E(
				assetEntry.getEntryId(), actionId, true, extraData);

		deactivateEquityLogs(equityLogs);
	}

	/**
	 * Removes all actions done by the user.
	 *
	 * <p>
	 * This method is called by the portal when a user is deleted.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	public void deactivateUserEquityLogs(long userId) throws SystemException {
		socialEquityLogPersistence.removeByUserId(userId);
	}

	/**
	 * Increments the information equity value of the asset by the number set
	 * in the equity payload.
	 *
	 * <p>
	 * This method is annotated with the <code>BufferedIncrement</code>
	 * annotation, which means that in case of heavy load, invocations of this
	 * method can be aggregated into one method call containing the sum of the
	 * individual increments.
	 * </p>
	 *
	 * <p>
	 * <i>This method should not be called directly by portlets. It is made
	 * public only to accommodate the <code>BufferedIncrement</code>
	 * annotation.</i>
	 * </p>
	 *
	 * @param  assetEntryId the primary key of the target asset entry
	 * @param  equityPayload the equity payload containing the increments
	 * @throws SystemException if a system exception occurred
	 */
	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityAssetEntry_IQ(
			long assetEntryId, SocialEquityIncrementPayload equityPayload)
		throws SystemException {

		final AssetEntry assetEntry = equityPayload.getAssetEntry();

		SocialEquityValue equityValue = equityPayload.getEquityValue();

		assetEntry.updateSocialInformationEquity(equityValue.getValue());

		int count = socialEquityAssetEntryPersistence.countByAssetEntryId(
			assetEntryId);

		if (count == 0) {
			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					public Void call() throws Exception {
						socialEquityLogLocalService.addSocialEquityAssetEntry(
							assetEntry);

						return null;
					}

				});
		}

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ASSET_ENTRY_ID$]",
				"[$INFORMATION_B$]",
				"[$INFORMATION_K$]"
			},
			new String[] {
				String.valueOf(assetEntryId),
				String.valueOf(equityValue.getB()),
				String.valueOf(equityValue.getK())
			});

		runSQL(sql);
	}

	/**
	 * Increments the contribution equity value of the user by the number set
	 * in the equity payload.
	 *
	 * <p>
	 * This method is annotated with the <code>BufferedIncrement</code>
	 * annotation, which means that in case of heavy load, invocations of this
	 * method can be aggregated into one method call containing the sum of the
	 * individual increments.
	 * </p>
	 *
	 * <P>
	 * <i>This method should not be called directly by portlets. It is made
	 * public only to accommodate the <code>BufferedIncrement</code>
	 * annotation.</i>
	 * </p>
	 *
	 * @param  groupId the primary key of the group in which the user is acting
	 * @param  userId the primary key of the acting user
	 * @param  equityPayload the equity payload containing the increments
	 * @throws SystemException if a system exception occurred
	 */
	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityUser_CQ(
			final long groupId, long userId,
			SocialEquityIncrementPayload equityPayload)
		throws SystemException {

		final User user = equityPayload.getUser();

		int count = socialEquityUserPersistence.countByG_U(groupId, userId);

		if (count == 0) {
			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					public Void call() throws Exception {
						socialEquityLogLocalService.addSocialEquityUser(
						groupId, user);

						return null;
					}

				});
		}

		SocialEquityValue equityValue = equityPayload.getEquityValue();

		user.updateSocialContributionEquity(groupId, equityValue.getValue());

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_CQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$CONTRIBUTION_B$]",
				"[$CONTRIBUTION_K$]",
				"[$GROUP_ID$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(equityValue.getB()),
				String.valueOf(equityValue.getK()),
				String.valueOf(groupId),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	/**
	 * Increments the participation equity value of the user by the number set
	 * in the equity payload.
	 *
	 * <p>
	 * This method is annotated with the <code>BufferedIncrement</code>
	 * annotation, which means that in case of heavy load, invocations of this
	 * method can be aggregated into one method call containing the sum of the
	 * individual increments.
	 * </p>
	 *
	 * <p>
	 * <i>This method should not be called directly by portlets. It is made
	 * public only to accommodate the <code>BufferedIncrement</code>
	 * annotation. </i>
	 * </p>
	 *
	 * @param  groupId the primary key of the group in which the user is acting
	 * @param  userId the primary key of the acting user
	 * @param  equityPayload the equity payload containing the increments
	 * @throws SystemException if a system exception occurred
	 */
	@BufferedIncrement(incrementClass = SocialEquityIncrement.class)
	public void incrementSocialEquityUser_PQ(
			final long groupId, long userId,
			SocialEquityIncrementPayload equityPayload)
		throws SystemException {

		final User user = equityPayload.getUser();

		int count = socialEquityUserPersistence.countByG_U(groupId, userId);

		if (count == 0) {
			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					public Void call() throws Exception {
						socialEquityLogLocalService.addSocialEquityUser(
							groupId, user);

						return null;
					}

				});
		}

		SocialEquityValue equityValue = equityPayload.getEquityValue();

		user.updateSocialParticipationEquity(groupId, equityValue.getValue());

		String sql = CustomSQLUtil.get(_UPDATE_SOCIAL_EQUITY_USER_PQ);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$GROUP_ID$]",
				"[$PARTICIPATION_B$]",
				"[$PARTICIPATION_K$]",
				"[$USER_ID$]"
			},
			new String[] {
				String.valueOf(groupId),
				String.valueOf(equityValue.getB()),
				String.valueOf(equityValue.getK()),
				String.valueOf(userId)
			});

		runSQL(sql);
	}

	/**
	 * Updates user ranking for all groups.
	 */
	public void updateRanks() {
		DataSource dataSource = socialEquityLogPersistence.getDataSource();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		UpdateRanksHandler updateRanksHandler = new UpdateRanksHandler(
			jdbcTemplate);

		String sql = CustomSQLUtil.get(_FIND_SOCIAL_EQUITY_USER);

		sql = StringUtil.replace(
			sql, "[$ACTION_DATE$]", String.valueOf(getEquityDate()));

		jdbcTemplate.query(sql, updateRanksHandler);

		updateRanksHandler.flush();
	}

	/**
	 * Updates user ranking for a group.
	 *
	 * @param groupId the primary key of the group
	 */
	public void updateRanks(long groupId) {
		DataSource dataSource = socialEquityLogPersistence.getDataSource();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		UpdateRanksHandler updateRanksHandler = new UpdateRanksHandler(
			jdbcTemplate);

		String sql = CustomSQLUtil.get(_FIND_SOCIAL_EQUITY_USER_BY_GROUP);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$ACTION_DATE$]",
				"[$GROUP_ID$]"
			},
			new String[] {
				String.valueOf(getEquityDate()),
				String.valueOf(groupId)
			});

		jdbcTemplate.query(sql, updateRanksHandler);

		updateRanksHandler.flush();
	}

	protected void addEquityLog(
			User user, AssetEntry assetEntry, User assetEntryUser,
			SocialEquitySetting equitySetting, String extraData)
		throws SystemException {

		if (!isAddEquityLog(user, assetEntry, equitySetting, extraData)) {
			return;
		}

		SocialEquityIncrementPayload equityIncrementPayload =
			new SocialEquityIncrementPayload();

		equityIncrementPayload.setAssetEntry(assetEntry);

		int actionDate = getEquityDate();

		double k = calculateK(
			equitySetting.getValue(), equitySetting.getLifespan());
		double b = calculateB(
			actionDate, equitySetting.getValue(), equitySetting.getLifespan());

		SocialEquityValue equityValue = new SocialEquityValue(k, b);

		equityIncrementPayload.setEquityValue(equityValue);

		equityIncrementPayload.setUser(user);

		if (equitySetting.getType() ==
				SocialEquitySettingConstants.TYPE_INFORMATION) {

			socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(
				assetEntry.getEntryId(), equityIncrementPayload);

			if ((assetEntryUser != null) && !assetEntryUser.isDefaultUser()) {
				socialEquityLogLocalService.incrementSocialEquityUser_CQ(
					assetEntry.getGroupId(), assetEntryUser.getUserId(),
					equityIncrementPayload);
			}
		}
		else if (equitySetting.getType() ==
					SocialEquitySettingConstants.TYPE_PARTICIPATION) {

			if (!user.isDefaultUser()) {
				socialEquityLogLocalService.incrementSocialEquityUser_PQ(
					assetEntry.getGroupId(), user.getUserId(),
					equityIncrementPayload);
			}
		}

		long equityLogId = counterLocalService.increment();

		SocialEquityLog equityLog = socialEquityLogPersistence.create(
			equityLogId);

		equityLog.setGroupId(assetEntry.getGroupId());
		equityLog.setCompanyId(user.getCompanyId());
		equityLog.setUserId(user.getUserId());
		equityLog.setAssetEntryId(assetEntry.getEntryId());
		equityLog.setActionId(equitySetting.getActionId());
		equityLog.setActionDate(actionDate);
		equityLog.setActive(true);
		equityLog.setExpiration(actionDate + equitySetting.getLifespan());
		equityLog.setType(equitySetting.getType());
		equityLog.setValue(equitySetting.getValue());
		equityLog.setExtraData(extraData);

		socialEquityLogPersistence.update(equityLog, false);
	}

	protected double calculateB(int actionDate, int value, int lifespan) {
		return calculateK(value, lifespan) * (actionDate + lifespan) * -1;
	}

	protected double calculateEquity(int actionDate, double k, double b) {
		return k * actionDate + b;
	}

	protected double calculateK(int value, int lifespan) {
		if (lifespan == 0) {
			return 0;
		}

		return ((double)value / lifespan) * -1;
	}

	protected void deactivateEquityLogs(List<SocialEquityLog> equityLogs)
		throws PortalException, SystemException {

		SocialEquityValue equityValue = new SocialEquityValue();

		for (SocialEquityLog equityLog : equityLogs) {
			AssetEntry assetEntry = assetEntryLocalService.getEntry(
				equityLog.getAssetEntryId());

			User user = userPersistence.findByPrimaryKey(
				assetEntry.getUserId());

			if (!isSocialEquityEnabled(
					assetEntry.getGroupId(), assetEntry.getClassName(),
					equityLog.getType())) {

				continue;
			}

			SocialEquityIncrementPayload equityIncrementPayload =
				new SocialEquityIncrementPayload();

			equityIncrementPayload.setAssetEntry(assetEntry);

			double k = calculateK(
				equityLog.getValue(),equityLog.getLifespan());
			double b = calculateB(
				equityLog.getActionDate(), equityLog.getValue(),
				equityLog.getLifespan());

			equityValue.setValue(0,0);
			equityValue.subtract(k,b);

			equityIncrementPayload.setEquityValue(equityValue);

			equityIncrementPayload.setUser(user);

			if (equityLog.getType() ==
					SocialEquitySettingConstants.TYPE_INFORMATION) {

				socialEquityLogLocalService.incrementSocialEquityAssetEntry_IQ(
					assetEntry.getEntryId(), equityIncrementPayload);

				socialEquityLogLocalService.incrementSocialEquityUser_CQ(
					assetEntry.getGroupId(), assetEntry.getUserId(),
					equityIncrementPayload);
			}
			else {
				socialEquityLogLocalService.incrementSocialEquityUser_PQ(
					equityLog.getGroupId(), equityLog.getUserId(),
					equityIncrementPayload);
			}

			socialEquityLogPersistence.remove(equityLog);
		}
	}

	protected int getEquityDate() {
		return getEquityDate(new Date());
	}

	protected int getEquityDate(Date date) {
		Calendar calendar = new GregorianCalendar(2010, Calendar.JANUARY, 1);

		return calendar.fieldDifference(date, Calendar.DATE);
	}

	protected boolean isAddEquityLog(
			User user, AssetEntry assetEntry, SocialEquitySetting equitySetting,
			String extraData)
		throws SystemException {

		if ((equitySetting.getDailyLimit() < 0) ||
			(equitySetting.getLifespan() == 0)) {

			return false;
		}

		String actionId = equitySetting.getActionId();
		int actionDate = getEquityDate();
		int type = equitySetting.getType();

		// Duplicate

		int count = socialEquityLogPersistence.countByU_AEI_AID_AD_A_T_E(
			user.getUserId(), assetEntry.getEntryId(), actionId, actionDate,
			true, type, extraData);

		if (count > 0) {
			return false;
		}

		// Unique because a user cannot have a duplicate active action on the
		// same entry

		if (equitySetting.isUniqueEntry()) {
			count = socialEquityLogPersistence.countByU_AEI_AID_A_E(
				user.getUserId(), assetEntry.getEntryId(), actionId, true,
				extraData);

			if (count > 0) {
				return false;
			}
		}

		// Daily limit

		if (equitySetting.getDailyLimit() == 0) {
			return true;
		}

		if (type == SocialEquitySettingConstants.TYPE_INFORMATION) {
			count = socialEquityLogPersistence.countByAEI_AID_AD_A_T_E(
				assetEntry.getEntryId(), actionId, actionDate, true, type,
				extraData);
		}
		else {
			count = socialEquityLogPersistence.countByU_AID_AD_A_T_E(
				user.getUserId(), actionId, actionDate, true, type, extraData);
		}

		if (count < equitySetting.getDailyLimit()) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isSocialEquityEnabled(
			long groupId, String className, int type)
		throws SystemException {

		if (!socialEquityGroupSettingLocalService.isEnabled(
				groupId, Group.class.getName(), type)) {

			return false;
		}

		return socialEquityGroupSettingLocalService.isEnabled(
			groupId, className, type);
	}

	protected void runCheckSQL(String sqlId, int validity)
		throws SystemException {

		String sql = CustomSQLUtil.get(sqlId);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$TYPE_INFORMATION$]",
				"[$TYPE_PARTICIPATION$]",
				"[$EXPIRATION$]"
			},
			new String[] {
				String.valueOf(SocialEquitySettingConstants.TYPE_INFORMATION),
				String.valueOf(SocialEquitySettingConstants.TYPE_PARTICIPATION),
				String.valueOf(validity)
			});

		runSQL(sql);
	}

	private static final String _ADD_SOCIAL_EQUITY_ASSET_ENTRY =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityAssetEntry";

	private static final String _ADD_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".addSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityAssetEntry_IQ";

	private static final String _CHECK_SOCIAL_EQUITY_LOGS =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityLogs";

	private static final String _CHECK_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser";

	private static final String _CHECK_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_CQ";

	private static final String _CHECK_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".checkSocialEquityUser_PQ";

	private static final String _FIND_SOCIAL_EQUITY_USER =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".findSocialEquityUser";

	private static final String _FIND_SOCIAL_EQUITY_USER_BY_GROUP =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".findSocialEquityUserByGroup";

	private static final String _UPDATE_SOCIAL_EQUITY_ASSET_ENTRY_IQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityAssetEntry_IQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_CQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_CQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_PQ =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUser_PQ";

	private static final String _UPDATE_SOCIAL_EQUITY_USER_RANK =
		SocialEquityLogLocalServiceImpl.class.getName() +
			".updateSocialEquityUserRank";

	private class UpdateRanksHandler implements RowCallbackHandler {

		public UpdateRanksHandler(JdbcTemplate jdbcTemplate) {
			_updateRanksSetter = new UpdateRanksSetter(jdbcTemplate);
		}

		public void flush() {
			_updateRanksSetter.flush();
		}

		public void processRow(ResultSet rs) throws SQLException {
			long equityUserId = rs.getLong("equityUserId");
			long groupId = rs.getLong("groupId");
			double equityValue = rs.getDouble("equityValue");
			int status = rs.getInt("status");

			if ((status != WorkflowConstants.STATUS_APPROVED) ||
				(equityValue == 0)) {

				_updateRanksSetter.add(equityUserId, 0);
			}
			else {
				if (groupId == _groupId) {
					if (equityValue == _equityValue) {
						_ties++;
					}
					else {
						_equityValue = equityValue;
						_rank = _rank + _ties + 1;
						_ties = 0;
					}
				}
				else {
					_groupId = groupId;
					_rank = 1;
					_ties = 0;
				}

				_updateRanksSetter.add(equityUserId, _rank);
			}
		}

		private double _equityValue;
		private long _groupId;
		private long _rank;
		private long _ties;
		private UpdateRanksSetter _updateRanksSetter;

	}

	private class UpdateRanksSetter implements BatchPreparedStatementSetter {

		public UpdateRanksSetter(JdbcTemplate jdbcTemplate) {
			_jdbcTemplate = jdbcTemplate;
		}

		public void add(long equityUserId, long rank) {
			_sqlParams.add(new Long[] {equityUserId, rank});

			if (_sqlParams.size() >= 100) {
				flush();
			}
		}

		public void flush() {
			try {
				if (getBatchSize() > 0) {
					_jdbcTemplate.batchUpdate(_sql, this);
				}
			}
			catch (DataAccessException dae) {
				throw dae;
			}
			finally {
				_sqlParams.clear();
			}
		}

		public int getBatchSize() {
			return _sqlParams.size();
		}

		public void setValues(PreparedStatement ps, int index)
			throws SQLException {

			Long[] sqlParams = _sqlParams.get(index);

			long equityUserId = sqlParams[0];
			long rank = sqlParams[1];

			ps.setLong(1, rank);
			ps.setLong(2, equityUserId);
			ps.setLong(3, rank);
		}

		private JdbcTemplate _jdbcTemplate;
		private String _sql = CustomSQLUtil.get(
			_UPDATE_SOCIAL_EQUITY_USER_RANK);
		private List<Long[]> _sqlParams = new ArrayList<Long[]>();

	}

}