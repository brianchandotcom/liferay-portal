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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio Sanchez
 * @author Zsolt Berentey
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateJournalActivities();
		updateSOSocialActivities();
		updateActivities();
	}

	protected Map<Long, String> generateExtraData(
			ExtraDataGenerator extraDataGenerator)
		throws Exception {

		Map<Long, String> extraDataMap = new HashMap<Long, String>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(4);

			sb.append("select activityId, groupId, companyId, userId, ");
			sb.append("classNameId, classPK, type_, extraData ");
			sb.append("from SocialActivity where ");
			sb.append(extraDataGenerator.getActivityQueryWhereClause());

			ps = connection.prepareStatement(sb.toString());

			extraDataGenerator.setActivityQueryParameters(ps);

			rs = ps.executeQuery();

			while (rs.next()) {
				long activityId = rs.getLong("activityId");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long companyId = rs.getLong("companyId");
				String extraData = rs.getString("extraData");
				long groupId = rs.getLong("groupId");
				int type = rs.getInt("type_");
				long userId = rs.getLong("userId");

				String newExtraData = generateExtraDataForActivity(
					extraDataGenerator, groupId, companyId, userId, classNameId,
					classPK, type, extraData);

				if (newExtraData != null) {
					extraDataMap.put(activityId, newExtraData);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
		return extraDataMap;
	}

	protected String generateExtraDataForActivity(
			ExtraDataGenerator extraDataGenerator, long companyId, long groupId,
			long userId, long classNameId, long classPK, int type,
			String extraData)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String result = null;

		try {
			if (extraDataGenerator != null) {
				ps = connection.prepareStatement(
					extraDataGenerator.getEntityQuery());

				extraDataGenerator.setEntityQueryParameters(
					ps, groupId, companyId, userId, classNameId, classPK, type,
					extraData);

				rs = ps.executeQuery();

				JSONObject extraDataJSONObject = null;

				while (rs.next()) {
					extraDataJSONObject =
						extraDataGenerator.getExtraData(rs, extraData);
				}

				result = extraDataJSONObject.toString();
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return result;
	}

	protected void updateActivities() throws Exception {
		for (ExtraDataGenerator extraDataGenerator : _extraDataGenerators) {
			updateActivities(extraDataGenerator);
		}
	}

	protected void updateActivities(ExtraDataGenerator extraDataGenerator)
			throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		Map<Long, String> extraDataMap = generateExtraData(extraDataGenerator);

		try {
			StringBundler sb = new StringBundler(2);

			sb.append("update SocialActivity set extraData = ? ");
			sb.append("where activityId = ?");

			String updateActivityQuery = sb.toString();

			for (Map.Entry<Long, String> entry : extraDataMap.entrySet()) {
				long activityId = entry.getKey();
				String extraData = entry.getValue();
				try {
					ps = connection.prepareStatement(updateActivityQuery);

					ps.setString(1, extraData);
					ps.setLong(2, activityId);

					ps.executeUpdate();
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to update activity " + activityId, e);
					}
				}
			}
		}

		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void updateJournalActivities() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.journal.model.JournalArticle");

		String[] tableNames = {"SocialActivity", "SocialActivityCounter"};

		for (String tableName : tableNames) {
			StringBundler sb = new StringBundler(7);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set classPK = (select resourcePrimKey ");
			sb.append("from JournalArticle where id_ = ");
			sb.append(tableName);
			sb.append(".classPK) where classNameId = ");
			sb.append(classNameId);

			runSQL(sb.toString());
		}
	}

	protected void updateSOSocialActivities() throws Exception {
		if (!hasTable("SO_SocialActivity")) {
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select activityId, activitySetId from SO_SocialActivity");

			rs = ps.executeQuery();

			while (rs.next()) {
				long activityId = rs.getLong("activityId");
				long activitySetId = rs.getLong("activitySetId");

				StringBundler sb = new StringBundler(4);

				sb.append("update SocialActivity set activitySetId = ");
				sb.append(activitySetId);
				sb.append(" where activityId = ");
				sb.append(activityId);

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		runSQL("drop table SO_SocialActivity");
	}

	/**
	 * Defines the necessary methods to generate extra data from a set of
	 * social activities of any kind. Implementors just have to focus on:
	 *   1) What is the set of social activities this generator will generate
	 *      extra data for (getActivityQueryWhereClause() and
	 *      setActivityQueryParameters())
	 *   2) How to obtain the model entities related to such activities
	 *      (getEntityQuery() and setEntityQueryParameters()),
	 *   3) How to generate extra data (getExtraData())
	 */
	protected interface ExtraDataGenerator {
		/**
		 * Returns the "where" clause in social activity query to select the
		 * SocialActivity tuples this generator will generate extra data for
		 */
		public String getActivityQueryWhereClause();

		/**
		 * Returns the SQL query on any model entity which the selected
		 * SocialActivity tuples refer to. Extra data will be generated from
		 * the entities returned by this query
		 */
		public String getEntityQuery();

		/**
		 * Given a result from the #getEntityQuery() and the original extra
		 * data in the SocialActivity tuple pointing to that entity, computes
		 * the extra data that will be persisted in the SocialActivity tuple as
		 * a result of the upgrade process.
		 *
		 * @return JSONObject containing the extra data
		 */
		public JSONObject getExtraData(
				ResultSet entityResultSet, String extraData)
			throws SQLException;

		/**
		 * Sets parameters required to run the activity query returned by
		 * #getActivityQueryWhereClause() in this generator
		 */
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException;

		/**
		 * Sets parameters required to run the entity query returned by
		 * #getEntityQueryWhereClause() in this generator, based on fields
		 * from the SocialActivity tuple
		 */
		public void setEntityQueryParameters(PreparedStatement ps,
				long companyId, long groupId, long userId, long classNameId,
				long classPK, int type, String extraData)
			throws SQLException;
	}

	/**
	 * Provides a partial implementation for ExtraDataGenerator which allows
	 * subclasses to just define some attributes and provide the method
	 * setEntityQueryParameters()
	 */
	protected static abstract class BaseExtraDataGenerator
		implements ExtraDataGenerator {

		public String ACTIVITY_CLASSNAME = "";

		public static final String ACTIVITY_CLASSNAMEID_CLAUSE =
			"classNameId = ?";

		/** maps each position in the activity query to a pair <type,
		 * string value of that type>. setActivityQueryParameters() will
		 * fill the prepared statement accordingly
		 */
		public Map<Integer, KeyValuePair> ACTIVITY_QUERY_PARAMS =
			new HashMap<Integer, KeyValuePair>();;

		public String ACTIVITY_QUERY_WHERE_CLAUSE = "";

		public static final String ACTIVITY_TYPE_CLAUSE = "type_ = ?";

		public String ENTITY_SELECT_CLAUSE = "";

		public String ENTITY_FROM_CLAUSE = "";

		public String ENTITY_WHERE_CLAUSE = "";

		/** maps each key in the extra data map JSON object to a pair
		 * <type, field name of that type in the result set>.
		 * getExtraData() will fill the JSON object from the result set
		 * accordingly
		 */
		public Map<String, KeyValuePair> EXTRA_DATA_MAP =
			new HashMap<String, KeyValuePair>();

		public String getActivityQueryWhereClause() {
			return ACTIVITY_QUERY_WHERE_CLAUSE;
		}

		public String getEntityQuery() {
			return "select " + ENTITY_SELECT_CLAUSE +
				" from " + ENTITY_FROM_CLAUSE +
				" where " + ENTITY_WHERE_CLAUSE;
		}

		public JSONObject getExtraData(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject result = JSONFactoryUtil.createJSONObject();

			if (EXTRA_DATA_MAP != null && EXTRA_DATA_MAP.size() > 0) {
				for (Map.Entry<String, KeyValuePair> entry :
						EXTRA_DATA_MAP.entrySet()) {

					String entryKey = entry.getKey();

					KeyValuePair entryValue = entry.getValue();

					String clazz = entryValue.getKey();

					if (clazz.equals(String.class.getName())) {
						result.put(entryKey,
							entityResultSet.getString(entryValue.getValue()));
					}
					else if (clazz.equals(Double.class.getName())) {
						result.put(entryKey,
							entityResultSet.getDouble(entryValue.getValue()));
					}
				}
			}

			return result;
		}

		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			if (ACTIVITY_QUERY_PARAMS != null &&
				ACTIVITY_QUERY_PARAMS.size() > 0) {

				for (Map.Entry<Integer, KeyValuePair> entry :
						ACTIVITY_QUERY_PARAMS.entrySet()) {

					Integer entryKey = entry.getKey();

					KeyValuePair entryValue = entry.getValue();

					String clazz = entryValue.getKey();

					if (clazz.equals(Long.class.getName())) {
						ps.setLong(entryKey,
							Long.valueOf(entryValue.getValue()));
					}
					else if (clazz.equals(Integer.class.getName())) {
						ps.setInt(entryKey,
							Integer.valueOf(entryValue.getValue()));
					}
				}
			}
		}
	}

	protected static ExtraDataGenerator _dlFileEntryExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.portlet.documentlibrary.model.DLFileEntry";

				ACTIVITY_QUERY_PARAMS.put(1,
					new KeyValuePair(Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_WHERE_CLAUSE = ACTIVITY_CLASSNAMEID_CLAUSE;

				ENTITY_SELECT_CLAUSE = "title";

				ENTITY_FROM_CLAUSE = "DLFileEntry";

				ENTITY_WHERE_CLAUSE =
					"companyId = ? and groupId = ? and fileEntryId = ?";

				EXTRA_DATA_MAP.put("title",
					new KeyValuePair(String.class.getName(), "title"));
			}

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, companyId);

				ps.setLong(2, groupId);

				ps.setLong(3, classPK);
			}
		};

	protected static ExtraDataGenerator _wikiPageExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				// use old classname as upgrade wiki hasn't taken place at yet
				ACTIVITY_CLASSNAME = "com.liferay.portlet.wiki.model.WikiPage";

				ACTIVITY_QUERY_PARAMS.put(1,
					new KeyValuePair(Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(2,
					new KeyValuePair(Integer.class.getName(),
						String.valueOf(ADD_PAGE)));

				ACTIVITY_QUERY_PARAMS.put(3,
					new KeyValuePair(Integer.class.getName(),
						String.valueOf(UPDATE_PAGE)));

				ACTIVITY_QUERY_WHERE_CLAUSE = ACTIVITY_CLASSNAMEID_CLAUSE +
					" and (" + ACTIVITY_TYPE_CLAUSE + " or " +
					ACTIVITY_TYPE_CLAUSE + ")";

				ENTITY_SELECT_CLAUSE="title, version";

				ENTITY_FROM_CLAUSE="WikiPage";

				ENTITY_WHERE_CLAUSE=
					"companyId = ? and groupId = ? and resourcePrimKey = ? " +
					"and head = true";

				EXTRA_DATA_MAP.put("title",
					new KeyValuePair(String.class.getName(), "title"));

				EXTRA_DATA_MAP.put("version",
					new KeyValuePair(Double.class.getName(), "version"));
			}

			// from WikiActivityKeys
			public static final int ADD_PAGE = 1;

			public static final int UPDATE_PAGE = 2;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, companyId);

				ps.setLong(2, groupId);

				ps.setLong(3, classPK);
			}
		};

	protected static List<ExtraDataGenerator> _extraDataGenerators =
		new ArrayList<ExtraDataGenerator>();

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

	static {
		_extraDataGenerators.add(_dlFileEntryExtraDataGenerator);
		_extraDataGenerators.add(_wikiPageExtraDataGenerator);
	}
}