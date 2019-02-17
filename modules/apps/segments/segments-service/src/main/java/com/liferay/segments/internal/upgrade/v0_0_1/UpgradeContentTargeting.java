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

package com.liferay.segments.internal.upgrade.v0_0_1;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.internal.upgrade.v0_0_1.util.RuleConverter;
import com.liferay.segments.internal.upgrade.v0_0_1.util.RuleConverterRegistry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eduardo García
 */
public class UpgradeContentTargeting extends UpgradeProcess {

	public UpgradeContentTargeting(
		CounterLocalService counterLocalService,
		RuleConverterRegistry ruleConverterRegistry) {

		_counterLocalService = counterLocalService;
		_ruleConverterRegistry = ruleConverterRegistry;
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
					String typeSettings = rs.getString("typeSettings");

					RuleConverter ruleConverter =
						_ruleConverterRegistry.getRuleConverter(ruleKey);

					long companyId = rs.getLong("companyId");

					ruleConverter.convert(companyId, criteria, typeSettings);
				}
			}
		}

		return CriteriaSerializer.serialize(criteria);
	}

	protected void upgradeContentTargetingUserSegments() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("insert into SegmentsEntry (segmentsEntryId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("name, description, active_, criteria, key_, type_) values ");
		sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from CT_UserSegment");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long userSegmentId = rs.getLong("userSegmentId");

				if (_log.isInfoEnabled()) {
					_log.info(
						"Upgrading Content Targeting User Segment " +
							userSegmentId);
				}

				ps2.setLong(1, _counterLocalService.increment());
				ps2.setLong(2, rs.getLong("groupId"));
				ps2.setLong(3, rs.getLong("companyId"));
				ps2.setLong(4, rs.getLong("userId"));
				ps2.setString(5, rs.getString("userName"));
				ps2.setTimestamp(6, rs.getTimestamp("createDate"));
				ps2.setTimestamp(7, rs.getTimestamp("modifiedDate"));
				ps2.setString(8, rs.getString("name"));
				ps2.setString(9, rs.getString("description"));
				ps2.setBoolean(10, true);
				ps2.setString(11, getCriteria(userSegmentId));
				ps2.setString(12, "CT." + userSegmentId);
				ps2.setString(13, User.class.getName());

				ps2.addBatch();
			}

			ps2.executeBatch();
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

	private final CounterLocalService _counterLocalService;
	private final RuleConverterRegistry _ruleConverterRegistry;

}