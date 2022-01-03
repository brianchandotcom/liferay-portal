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
import com.liferay.portal.kernel.util.PortalUtil;
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
public class ContentTargetingUpgradeProcess extends UpgradeProcess {

	public ContentTargetingUpgradeProcess(
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

		_upgradeContentTargetingUserSegments();
		_deleteContentTargetingData();
	}

	private void _deleteContentTargetingData() throws Exception {
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

	private void _dropTable(String tableName) throws Exception {
		if (hasTable(tableName)) {
			runSQL("drop table " + tableName);
		}
	}

	private String _getCriteria(long userSegmentId) throws Exception {
		Criteria criteria = new Criteria();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, ruleKey, typeSettings from " +
					"CT_RuleInstance where userSegmentId = ?")) {

			preparedStatement.setLong(1, userSegmentId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String ruleKey = resultSet.getString("ruleKey");

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

					long companyId = resultSet.getLong("companyId");
					String typeSettings = resultSet.getString("typeSettings");

					ruleConverter.convert(companyId, criteria, typeSettings);
				}
			}
		}

		return CriteriaSerializer.serialize(criteria);
	}

	private void _upgradeContentTargetingUserSegments() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from CT_UserSegment");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			ServiceContext serviceContext = new ServiceContext();

			while (resultSet.next()) {
				long userSegmentId = resultSet.getLong("userSegmentId");

				if (_log.isInfoEnabled()) {
					_log.info(
						"Upgrading Content Targeting User Segment " +
							userSegmentId);
				}

				Map<Locale, String> nameMap =
					LocalizationUtil.getLocalizationMap(
						resultSet.getString("name"));
				Map<Locale, String> descriptionMap =
					LocalizationUtil.getLocalizationMap(
						resultSet.getString("description"));

				serviceContext.setScopeGroupId(resultSet.getLong("groupId"));
				serviceContext.setUserId(
					PortalUtil.getValidUserId(
						resultSet.getLong("companyId"),
						resultSet.getLong("userId")));

				_segmentsEntryLocalService.addSegmentsEntry(
					"ct_" + userSegmentId, nameMap, descriptionMap, true,
					_getCriteria(userSegmentId),
					SegmentsEntryConstants.SOURCE_DEFAULT, User.class.getName(),
					serviceContext);
			}
		}
	}

	private static final String _CT_PACKAGE_NAME =
		"com.liferay.content.targeting";

	private static final Log _log = LogFactoryUtil.getLog(
		ContentTargetingUpgradeProcess.class);

	private final RuleConverterRegistry _ruleConverterRegistry;
	private final SegmentsEntryLocalService _segmentsEntryLocalService;

}