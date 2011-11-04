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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.util.SocialCounterPeriodUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zsolt Berentey
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		buildMapping();

		migrateSocialEquityGroupSettings();

		migrateSocialEquitySettings();

		migrateSocialEquityLogs();
	}

	protected void migrateSocialEquityLogs() throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			long userClassNameId = PortalUtil.getClassNameId(User.class);

			con = DataAccess.getConnection();

			st = con.createStatement();

			rs = st.executeQuery(_GET_EQUITY_LOGS);

			while (rs.next()) {
				AssetEntry assetEntry =
					AssetEntryLocalServiceUtil.fetchAssetEntry(
						rs.getLong("assetEntryId"));

				if (assetEntry == null) {
					continue;
				}

				String actionId = rs.getString("actionId");

				if (actionId.equals("SUBSCRIBE")) {
					String className = assetEntry.getClassName();

					if (className.equals(MBThread.class.getName())) {
						MBThread thread = MBThreadLocalServiceUtil.fetchThread(
							assetEntry.getClassPK());

						assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
							MBMessage.class.getName(),
							thread.getRootMessageId());
					}
				}

				Date actionDate = SocialCounterPeriodUtil.getDate(
					rs.getInt("actionDate") - 365);

				int startPeriod = SocialCounterPeriodUtil.getStartPeriod(
					actionDate.getTime());
				int endPeriod = SocialCounterPeriodUtil.getEndPeriod(
					actionDate.getTime());

				if (endPeriod == SocialCounterPeriodUtil.getEndPeriod()) {
					endPeriod =
						SocialActivityCounterConstants.END_PERIOD_UNDEFINED;
				}

				String name =
					SocialActivityCounterConstants.NAME_PARTICIPATION;
				int ownerType =
					SocialActivityCounterConstants.TYPE_ACTOR;
				long classNameId = userClassNameId;
				long classPK = rs.getLong("userId");

				if (rs.getInt("type_") == 1) {
					name = SocialActivityCounterConstants.NAME_CONTRIBUTION;
					ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
					long activityCounterId =
						CounterLocalServiceUtil.increment();

					updateActivityCounter(
						activityCounterId, assetEntry.getGroupId(),
						assetEntry.getCompanyId(), userClassNameId,
						assetEntry.getUserId(), name, ownerType, startPeriod,
						endPeriod, rs.getInt("value"));

					name = SocialActivityCounterConstants.NAME_POPULARITY;
					ownerType = SocialActivityCounterConstants.TYPE_ASSET;
					classNameId = assetEntry.getClassNameId();
					classPK = assetEntry.getClassPK();
				}

				updateActivityCounter(
					rs.getLong("equityLogId"), assetEntry.getGroupId(),
					assetEntry.getCompanyId(), classNameId, classPK, name,
					ownerType, startPeriod, endPeriod, rs.getInt("value"));
			}

			runSQL(
				"update SocialActivityCounter set endPeriod = -1 " +
				"where name in ('contribution','popularity','participation') " +
				"and startPeriod = ( "+
					" select max(startPeriod) " +
					" from SocialActivityCounter sac " +
					" where sac.groupId = SocialActivityCounter.groupId " +
					" and sac.classNameId = SocialActivityCounter.classNameId" +
					" and sac.classPK = SocialActivityCounter.classPK " +
					" and sac.name = SocialActivityCounter.name)");
		}
		finally {
			DataAccess.cleanUp(con, st, rs);
		}
	}

	protected void migrateSocialEquityGroupSettings()
		throws Exception {

		List<Object[]> results =
			runQuery(_GET_EQUITY_GROUP_SETTINGS, new Object[0]);

		for (Object[] array : results) {
			String[] args = new String[] {
				String.valueOf(CounterLocalServiceUtil.increment()),
				array[0].toString(),
				array[1].toString(),
				array[2].toString(),
				"0",
				"'enabled'",
				array[3].toString()
			};

			runSQL(
				"insert into SocialActivitySetting " +
				"(activitySettingId, groupId, companyId, classNameId," +
				" activityType, name, value) " +
				"values ("+ StringUtil.merge(args)+")");
		}

		long mBMessageClassNameId = PortalUtil.getClassNameId(MBMessage.class);
		long mBThreadClassNameId = PortalUtil.getClassNameId(MBThread.class);

		runSQL(
			"update SocialActivitySetting " +
			"set value = 'true' " +
			"where classNameId = " + mBMessageClassNameId +
			" and activityType = 0 and name = 'enabled' " +
			" and exists (" +
				"select 1 from SocialActivitySetting sas " +
				"where sas.groupId = SocialActivitySetting.groupId " +
				"and sas.activityType = 0 and sas.name = 'enabled' " +
				"and value = 'true' " +
				"and sas.classNameId in (" + mBMessageClassNameId + "," +
					mBThreadClassNameId + ")" +
				")");

		runSQL(
			"delete from SocialActivitySetting " +
			"where classNameId = " + mBThreadClassNameId);
	}

	protected void migrateSocialEquitySettings()
		throws Exception {

		List<Object[]> results =
			runQuery(_GET_EQUITY_SETTINGS, new Object[0]);

		for (Object[] array : results) {
			Tuple activity = _mapping.get(
				encodeMappingKey(
					GetterUtil.getLong(array[2]), array[3].toString()));

			if (activity == null) {
				_log.warn("Unknown social equity setting '" + array[3] + "' " +
					"was found. If this is an action defined in plugin " +
					"please go to social activity in control panel and " +
					"configure it from there.");

				continue;
			}

			for (String counter :
					_typeMapping[GetterUtil.getInteger(array[6])]) {

				String[] parts = StringUtil.split(counter, "/");

				String[] args = new String[] {
					String.valueOf(CounterLocalServiceUtil.increment()),
					array[0].toString(),
					array[1].toString(),
					activity.getObject(0).toString(),
					activity.getObject(1).toString(),
					StringUtil.quote(parts[0]),
					StringUtil.quote(
						toJSON(true, GetterUtil.getInteger(array[4]),
							SocialActivityCounterDefinition.LIMIT_PERIOD_DAY,
							GetterUtil.getInteger(parts[1]),
							GetterUtil.getInteger(array[7])))
				};

				runSQL(
					"insert into SocialActivitySetting " +
					"(activitySettingId, groupId, companyId, classNameId," +
					" activityType, name, value) " +
					"values (" + StringUtil.merge(args) + ")");
			}
		}
	}

	protected Object[] getActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod, int endPeriod)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_ACTIVITY_COUNTER);

			ps.setLong(1, groupId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);
			ps.setString(4, name);
			ps.setInt(5, ownerType);
			ps.setInt(6, startPeriod);
			ps.setInt(7, endPeriod);

			rs = ps.executeQuery();

			if (rs.next()) {
				return new Object[] {
					rs.getLong("activityCounterId"),
					rs.getInt("totalValue")
				};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected int getTotalValueForActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_TOTAL_VALUE_FOR_ACTIVITY_COUNTER);

			ps.setLong(1, groupId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);
			ps.setString(4, name);
			ps.setInt(5, ownerType);
			ps.setInt(6, startPeriod);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("totalValue");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateActivityCounter(
			long activityCounterId, long groupId, long companyId,
			long classNameId, long classPK, String name, int ownerType,
			int startPeriod, int endPeriod, int increment)
		throws Exception {

		Object[] array = getActivityCounter(
			groupId, classNameId, classPK, name, ownerType, startPeriod,
			endPeriod);

		if (array == null) {
			int totalValue = getTotalValueForActivityCounter(
				groupId, classNameId, classPK, name, ownerType, startPeriod);

			String[] values = new String[] {
				String.valueOf(activityCounterId),
				String.valueOf(groupId),
				String.valueOf(companyId),
				String.valueOf(classNameId),
				String.valueOf(classPK),
				StringUtil.quote(name),
				String.valueOf(ownerType),
				String.valueOf(increment),
				String.valueOf(totalValue + increment),
				"0",
				String.valueOf(startPeriod),
				String.valueOf(endPeriod)
			};

			runSQL(
				"insert into SocialActivityCounter " +
					"(activityCounterId, groupId, companyId, classNameId," +
					" classPK, name, ownerType, currentValue, totalValue," +
					" graceValue, startPeriod, endPeriod)" +
				"values (" + StringUtil.merge(values) + ")");
		}
		else {
			runSQL(
				"update SocialActivityCounter " +
					"set currentValue = currentValue + " + increment +
					", totalValue = totalValue + " + increment +
				" where activityCounterId = " +
				GetterUtil.getLong(array[0]));
		}
	}

	private void buildMapping() {
		for (String mappingString : _mappingStrings) {
			String[] array = StringUtil.split(mappingString, StringPool.COLON);

			String[] socialEquityParts = StringUtil.split(
				array[0], StringPool.SLASH);
			String[] socialAcitvityParts = StringUtil.split(
				array[1], StringPool.SLASH);

			long equityClassNameId =
				PortalUtil.getClassNameId(socialEquityParts[0]);
			long activityClassNameId =
				PortalUtil.getClassNameId(socialAcitvityParts[0]);
			int activityType = GetterUtil.getInteger(socialAcitvityParts[1]);

			_mapping.put(
				encodeMappingKey(equityClassNameId, socialEquityParts[1]),
				new Tuple(activityClassNameId, activityType));
		}
	}

	private String toJSON(
		boolean enabled, int limitValue, int limitPeriod, int ownerType,
		int value) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("enabled", enabled);
		jsonObject.put("limitPeriod", limitPeriod);
		jsonObject.put("limitValue", limitValue);
		jsonObject.put("ownerType", ownerType);
		jsonObject.put("value", value);

		return jsonObject.toString();
	}

	private String encodeMappingKey(long classNameId, String actionId) {

		StringBundler sb = new StringBundler(3);

		sb.append(classNameId);
		sb.append(StringPool.SLASH);
		sb.append(actionId);

		return sb.toString();
	}

	private List<Object[]> runQuery(String sql, Object[] params)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1,  params[i]);
			}

			rs = ps.executeQuery();

			List<Object[]> results = new ArrayList<Object[]>();

			while (rs.next()) {
				Object[] array = new Object[rs.getMetaData().getColumnCount()];

				for (int i = 0; i < array.length; i++) {
					array[i] = rs.getObject(i + 1);
				}

				results.add(array);
			}

			return results;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _GET_ACTIVITY_COUNTER =
		"select activityCounterId, totalValue from SocialActivityCounter " +
		" where groupId = ? and classNameId = ? and classPK = ? and " +
		" name = ? and ownerType = ? and startPeriod = ? and endPeriod = ?";

	private static final String _GET_EQUITY_LOGS =
		"select AssetEntry.classNameId, AssetEntry.classPK, SocialEquityLog.*" +
		" from SocialEquityLog, AssetEntry"+
		" where SocialEquityLog.assetEntryId = AssetEntry.entryId" +
		" order by actionDate";

	private static final String _GET_EQUITY_GROUP_SETTINGS =
		"select groupId, companyId, classNameId, enabled " +
		" from SocialEquityGroupSetting where type_ = 1";

	private static final String _GET_EQUITY_SETTINGS =
		"select groupId, companyId, classNameId, actionId, dailyLimit," +
		" lifespan, type_, value from SocialEquitySetting";

	private static final String _GET_TOTAL_VALUE_FOR_ACTIVITY_COUNTER =
		"select max(totalValue) as totalValue from SocialActivityCounter " +
		" where groupId = ? and classNameId = ? and classPK = ? and " +
		" name = ? and ownerType = ? and startPeriod < ?";

	private static Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

	private static final String[] _mappingStrings = new String[] {
		"com.liferay.portlet.blogs.model.BlogsEntry/ADD_DISCUSSION:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/10005",
		"com.liferay.portlet.blogs.model.BlogsEntry/ADD_ENTRY:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/2",
		"com.liferay.portlet.blogs.model.BlogsEntry/ADD_VOTE:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/10004",
		"com.liferay.portlet.blogs.model.BlogsEntry/SUBSCRIBE:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/10002",
		"com.liferay.portlet.blogs.model.BlogsEntry/UPDATE:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/3",
		"com.liferay.portlet.blogs.model.BlogsEntry/VIEW:" +
			"com.liferay.portlet.blogs.model.BlogsEntry/10001",
		"com.liferay.portlet.journal.JournalArticle/ADD_ARTICLE:" +
			"com.liferay.portlet.journal.JournalArticle/1",
		"com.liferay.portlet.journal.JournalArticle/ADD_DISCUSSION:" +
			"com.liferay.portlet.journal.JournalArticle/10005",
		"com.liferay.portlet.journal.JournalArticle/UPDATE:" +
			"com.liferay.portlet.journal.JournalArticle/2",
		"com.liferay.portlet.journal.JournalArticle/VIEW:" +
			"com.liferay.portlet.journal.JournalArticle/10001",
		"com.liferay.portlet.messageboards.model.MBCategory/SUBSCRIBE:" +
			"com.liferay.portlet.messageboards.model.MBCategory/10002",
		"com.liferay.portlet.messageboards.model.MBThread/SUBSCRIBE:" +
			"com.liferay.portlet.messageboards.model.MBMessage/10002",
		"com.liferay.portlet.messageboards.model.MBMessage/ADD_MESSAGE:" +
			"com.liferay.portlet.messageboards.model.MBMessage/1",
		"com.liferay.portlet.messageboards.model.MBMessage/ADD_VOTE:" +
			"com.liferay.portlet.messageboards.model.MBMessage/10004",
		"com.liferay.portlet.messageboards.model.MBMessage/REPLY_TO_MESSAGE:" +
			"com.liferay.portlet.messageboards.model.MBMessage/2",
		"com.liferay.portlet.messageboards.model.MBMessage/VIEW:" +
			"com.liferay.portlet.messageboards.model.MBMessage/10001",
		"com.liferay.portlet.wiki.model.WikiNode/SUBSCRIBE:" +
			"com.liferay.portlet.wiki.model.WikiNode/10002",
		"com.liferay.portlet.wiki.model.WikiPage/ADD_ATTACHMENT:" +
			"com.liferay.portlet.wiki.model.WikiPage/10006",
		"com.liferay.portlet.wiki.model.WikiPage/ADD_DISCUSSION:" +
			"com.liferay.portlet.wiki.model.WikiPage/10005",
		"com.liferay.portlet.wiki.model.WikiPage/ADD_PAGE:" +
			"com.liferay.portlet.wiki.model.WikiPage/1",
		"com.liferay.portlet.wiki.model.WikiPage/SUBSCRIBE:" +
			"com.liferay.portlet.wiki.model.WikiPage/10002",
		"com.liferay.portlet.wiki.model.WikiPage/UPDATE:" +
			"com.liferay.portlet.wiki.model.WikiPage/2",
		"com.liferay.portlet.wiki.model.WikiPage/VIEW:" +
			"com.liferay.portlet.wiki.model.WikiPage/10001"
	};

	private static final String[][] _typeMapping = new String[][] {
		null,
		new String[] {
			SocialActivityCounterConstants.NAME_CONTRIBUTION + "/" +
				SocialActivityCounterConstants.TYPE_CREATOR,
			SocialActivityCounterConstants.NAME_POPULARITY + "/" +
				SocialActivityCounterConstants.TYPE_ASSET
		},
		new String[] {
			SocialActivityCounterConstants.NAME_PARTICIPATION + "/" +
				SocialActivityCounterConstants.TYPE_ACTOR
		}
	};

	private Map<String, Tuple> _mapping = new HashMap<String, Tuple>();

}