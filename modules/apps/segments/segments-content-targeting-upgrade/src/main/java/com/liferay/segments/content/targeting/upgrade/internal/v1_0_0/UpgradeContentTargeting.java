/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.segments.content.targeting.upgrade.internal.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.content.targeting.upgrade.internal.v1_0_0.util.RuleConverter;
import com.liferay.segments.content.targeting.upgrade.internal.v1_0_0.util.RuleConverterRegistry;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo García
 */
public class UpgradeContentTargeting extends UpgradeProcess {

	public UpgradeContentTargeting(
		RuleConverterRegistry ruleConverterRegistry,
		SegmentsEntryLocalService segmentsEntryLocalService) {

		_ruleConverterRegistry = ruleConverterRegistry;
		_segmentsEntryLocalService = segmentsEntryLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		if (!hasTable("CT_UserSegment")) {
			return;
		}

		upgradeContentTargetingUserSegments();
		deleteContentTargetingData();
	}

	protected void deleteContentTargetingData() throws Exception {
		runSQL(
			"delete from ClassName_ where value like '" + _CT_PACKAGE_NAME +
				"%'");

		runSQL(
			"delete from Release_ where servletContextName like '" +
				_CT_PACKAGE_NAME + "%'");

		runSQL(
			"delete from ResourceAction where name like '" + _CT_PACKAGE_NAME +
				"%'");

		runSQL(
			"delete from ResourcePermission where name like '" +
				_CT_PACKAGE_NAME + "%'");

		runSQL("delete from ServiceComponent where buildNamespace like 'CT%'");

		_dropTable("CT_AU_AnonymousUser");
		_dropTable("CT_Analytics_AnalyticsEvent");
		_dropTable("CT_Analytics_AnalyticsReferrer");
		_dropTable("CT_AnonymousUserUserSegment");
		_dropTable("CT_CCR_CampaignContent");
		_dropTable("CT_CTA_CTAction");
		_dropTable("CT_CTA_CTActionTotal");
		_dropTable("CT_Campaign");
		_dropTable("CT_Campaigns_UserSegments");
		_dropTable("CT_ChannelInstance");
		_dropTable("CT_ReportInstance");
		_dropTable("CT_RuleInstance");
		_dropTable("CT_ScorePoints_ScorePoint");
		_dropTable("CT_Tactic");
		_dropTable("CT_Tactics_UserSegments");
		_dropTable("CT_TrackingActionInstance");
		_dropTable("CT_USCR_UserSegmentContent");
		_dropTable("CT_UserSegment");
		_dropTable("CT_Visited_ContentVisited");
		_dropTable("CT_Visited_PageVisited");
	}

	protected String getCriteria(long userSegmentId) throws Exception {
		Criteria criteria = new Criteria();

		try (PreparedStatement ps = connection.prepareStatement(
				"select companyId, ruleKey, typeSettings from " +
					"CT_RuleInstance where userSegmentId = ?")) {

			ps.setLong(1, userSegmentId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String ruleKey = rs.getString("ruleKey");

					RuleConverter ruleConverter =
						_ruleConverterRegistry.getRuleConverter(ruleKey);

					if (ruleConverter == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to perform automated update of rule " +
									ruleKey);
						}

						continue;
					}

					long companyId = rs.getLong("companyId");
					String typeSettings = rs.getString("typeSettings");

					ruleConverter.convert(companyId, criteria, typeSettings);
				}
			}
		}

		return CriteriaSerializer.serialize(criteria);
	}

	protected void upgradeContentTargetingUserSegments() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from CT_UserSegment");
			ResultSet rs = ps1.executeQuery()) {

			ServiceContext serviceContext = new ServiceContext();

			while (rs.next()) {
				long userSegmentId = rs.getLong("userSegmentId");

				if (_log.isInfoEnabled()) {
					_log.info(
						"Upgrading Content Targeting User Segment " +
							userSegmentId);
				}

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(rs.getString("name"));
				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(
						rs.getString("description"));

				serviceContext.setScopeGroupId(rs.getLong("groupId"));
				serviceContext.setUserId(rs.getLong("userId"));

				_segmentsEntryLocalService.addSegmentsEntry(
					"ct_" + userSegmentId, nameMap, descriptionMap, true,
					getCriteria(userSegmentId),
					SegmentsEntryConstants.SOURCE_DEFAULT, User.class.getName(),
					serviceContext);
			}
		}
	}

	private void _dropTable(String tableName) throws Exception {
		if (hasTable(tableName)) {
			runSQL("drop table " + tableName);
		}
	}

	private static final String _CT_PACKAGE_NAME =
		"com.liferay.content.targeting";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeContentTargeting.class);

	private final RuleConverterRegistry _ruleConverterRegistry;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;

}