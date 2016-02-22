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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
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
 * @author Daniel Sanz
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

		Map<Long, String> extraDataMap = new HashMap<>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select activityId, groupId, companyId, userId, ");
			sb.append("classNameId, classPK, type_, extraData ");
			sb.append("from SocialActivity where ");
			sb.append(extraDataGenerator.getActivityQueryWhereClause());

			ps = con.prepareStatement(sb.toString());

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
			DataAccess.cleanUp(con, ps, rs);
		}

		return extraDataMap;
	}

	protected String generateExtraDataForActivity(
			ExtraDataGenerator extraDataGenerator, long companyId, long groupId,
			long userId, long classNameId, long classPK, int type,
			String extraData)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String result = null;

		try {
			if (extraDataGenerator != null) {
				con = DataAccess.getUpgradeOptimizedConnection();

				ps = con.prepareStatement(extraDataGenerator.getEntityQuery());

				extraDataGenerator.setEntityQueryParameters(
					ps, groupId, companyId, userId, classNameId, classPK, type,
					extraData);

				rs = ps.executeQuery();

				JSONObject extraDataJSONObject = null;

				while (rs.next()) {
					extraDataJSONObject = extraDataGenerator.getExtraData(
						rs, extraData);
				}

				result = extraDataJSONObject.toString();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Map<Long, String> extraDataMap = generateExtraData(extraDataGenerator);

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("update SocialActivity set extraData = ? ");
			sb.append("where activityId = ?");

			String updateActivityQuery = sb.toString();

			for (Map.Entry<Long, String> entry : extraDataMap.entrySet()) {
				long activityId = entry.getKey();
				String extraData = entry.getValue();

				try {
					ps = con.prepareStatement(updateActivityQuery);

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
			DataAccess.cleanUp(con, ps, rs);
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

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
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
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("drop table SO_SocialActivity");
	}

	protected static abstract class BaseExtraDataGenerator
		implements ExtraDataGenerator {

		public static final String ACTIVITY_CLASSNAMEID_CLAUSE =
			"classNameId = ?";

		public static final String ACTIVITY_TYPE_CLAUSE = "type_ = ?";

		public String getActivityQueryWhereClause() {
			return ACTIVITY_QUERY_WHERE_CLAUSE;
		}

		public String getEntityQuery() {
			return "select " + ENTITY_SELECT_CLAUSE + " from " +
				ENTITY_FROM_CLAUSE + " where " + ENTITY_WHERE_CLAUSE;
		}

		public JSONObject getExtraData(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject result = JSONFactoryUtil.createJSONObject();

			if (MapUtil.isNotEmpty(EXTRA_DATA_MAP)) {
				for (Map.Entry<String, KeyValuePair> entry :
						EXTRA_DATA_MAP.entrySet()) {

					String entryKey = entry.getKey();

					KeyValuePair entryValue = entry.getValue();

					String clazz = entryValue.getKey();

					if (clazz.equals(String.class.getName())) {
						result.put(
							entryKey,
							entityResultSet.getString(entryValue.getValue()));
					}
					else if (clazz.equals(Double.class.getName())) {
						result.put(
							entryKey,
							entityResultSet.getDouble(entryValue.getValue()));
					}
				}
			}

			return result;
		}

		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			if (MapUtil.isNotEmpty(ACTIVITY_QUERY_PARAMS)) {
				for (Map.Entry<Integer, KeyValuePair> entry :
						ACTIVITY_QUERY_PARAMS.entrySet()) {

					Integer entryKey = entry.getKey();

					KeyValuePair entryValue = entry.getValue();

					String clazz = entryValue.getKey();

					if (clazz.equals(Long.class.getName())) {
						ps.setLong(
							entryKey, Long.valueOf(entryValue.getValue()));
					}
					else if (clazz.equals(Integer.class.getName())) {
						ps.setInt(
							entryKey, Integer.valueOf(entryValue.getValue()));
					}
				}
			}
		}

		public String ACTIVITY_CLASSNAME = "";

		public Map<Integer, KeyValuePair> ACTIVITY_QUERY_PARAMS =
			new HashMap<>();

		public String ACTIVITY_QUERY_WHERE_CLAUSE = "";

		public String ENTITY_FROM_CLAUSE = "";

		public String ENTITY_SELECT_CLAUSE = "";

		public String ENTITY_WHERE_CLAUSE = "";

		public Map<String, KeyValuePair> EXTRA_DATA_MAP = new HashMap<>();

	}

	protected interface ExtraDataGenerator {

		public String getActivityQueryWhereClause();

		public String getEntityQuery();

		public JSONObject getExtraData(
				ResultSet entityResultSet, String extraData)
			throws SQLException;

		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException;

		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException;

	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

	private static final BaseExtraDataGenerator
		_addAssetCommentExtraDataGenerator =
			new BaseExtraDataGenerator() {
				{
					ACTIVITY_QUERY_PARAMS.put(
						1,
						new KeyValuePair(
							Integer.class.getName(),
							String.valueOf(TYPE_ADD_COMMENT)));

					ACTIVITY_QUERY_WHERE_CLAUSE = ACTIVITY_TYPE_CLAUSE;

					ENTITY_SELECT_CLAUSE = "subject";

					ENTITY_FROM_CLAUSE = "MBMessage";

					ENTITY_WHERE_CLAUSE = "messageId = ?";

					EXTRA_DATA_MAP.put(
						"title",
						new KeyValuePair(String.class.getName(), "subject"));
				}

				public static final int TYPE_ADD_COMMENT = 10005;

				public void setEntityQueryParameters(
						PreparedStatement ps, long companyId, long groupId,
						long userId, long classNameId, long classPK, int type,
						String extraData)
					throws SQLException {

					long messageId = 0;

					try {
						JSONObject extraDataJson = JSONFactoryUtil.createJSONObject(
							extraData);

						messageId = extraDataJson.getLong("messageId");
					}
					catch (JSONException jsone) {
					}

					ps.setLong(1, messageId);
				}

				public JSONObject getExtraData(
						ResultSet entityResultSet, String extraData)
					throws SQLException {

					long messageId = 0;

					try {
						JSONObject extraDataJson = JSONFactoryUtil.createJSONObject(
							extraData);

						messageId = extraDataJson.getLong("messageId");
					}
					catch (JSONException jsone) {
					}

					JSONObject result = super.getExtraData(
						entityResultSet, extraData);

					result.put("messageId", messageId);

					return result;
				}

			};

	private static final BaseExtraDataGenerator _addMessageExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.portlet.messageboards.model.MBMessage";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(ADD_MESSAGE)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(REPLY_MESSAGE)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE =
					_addAssetCommentExtraDataGenerator.ENTITY_SELECT_CLAUSE;

				ENTITY_FROM_CLAUSE =
					_addAssetCommentExtraDataGenerator.ENTITY_FROM_CLAUSE;

				ENTITY_WHERE_CLAUSE =
					_addAssetCommentExtraDataGenerator.ENTITY_WHERE_CLAUSE;

				EXTRA_DATA_MAP.put(
					"title",
					new KeyValuePair(String.class.getName(), "subject"));
			}

			public static final int ADD_MESSAGE = 1;

			public static final int REPLY_MESSAGE = 2;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

		};

	private static final ExtraDataGenerator _blogsEntryExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.portlet.blogs.model.BlogsEntry";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(ADD_ENTRY)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(UPDATE_ENTRY)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE = "title";

				ENTITY_FROM_CLAUSE = "BlogsEntry";

				ENTITY_WHERE_CLAUSE = "entryId = ?";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "title"));
			}

			public static final int ADD_ENTRY = 2;

			public static final int UPDATE_ENTRY = 3;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

		};

	private static final ExtraDataGenerator _bookmarksEntryExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{

				ACTIVITY_CLASSNAME =
					"com.liferay.portlet.bookmarks.model.BookmarksEntry";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(ADD_ENTRY)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(UPDATE_ENTRY)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE = "name";

				ENTITY_FROM_CLAUSE = "BookmarksEntry";

				ENTITY_WHERE_CLAUSE = "entryId = ?";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "name"));
			}

			public static final int ADD_ENTRY = 1;

			public static final int UPDATE_ENTRY = 2;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

		};

	private static final ExtraDataGenerator _dlFileEntryExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.portlet.documentlibrary.model.DLFileEntry";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_WHERE_CLAUSE = ACTIVITY_CLASSNAMEID_CLAUSE;

				ENTITY_SELECT_CLAUSE = "title";

				ENTITY_FROM_CLAUSE = "DLFileEntry";

				ENTITY_WHERE_CLAUSE =
					"companyId = ? and groupId = ? and fileEntryId = ?";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "title"));
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

	private static final List<ExtraDataGenerator> _extraDataGenerators =
		new ArrayList<>();

	private static final BaseExtraDataGenerator _kbArticleExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.knowledgebase.model.KBArticle";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(ADD_KB_ARTICLE)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(UPDATE_KB_ARTICLE)));

				ACTIVITY_QUERY_PARAMS.put(
					4,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(MOVE_KB_ARTICLE)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							" or " + ACTIVITY_TYPE_CLAUSE + ")";

				ENTITY_SELECT_CLAUSE = "title";

				ENTITY_FROM_CLAUSE = "KBArticle";

				ENTITY_WHERE_CLAUSE = "resourceprimkey = ?";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "title"));
			}

			public static final int ADD_KB_ARTICLE = 1;

			public static final int MOVE_KB_ARTICLE = 7;

			public static final int UPDATE_KB_ARTICLE = 3;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

		};

	private static final ExtraDataGenerator _kbCommentExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.knowledgebase.model.KBComment";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(ADD_KB_COMMENT)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(UPDATE_KB_COMMENT)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE = "classnameid, classpk";

				ENTITY_FROM_CLAUSE = "KBComment";

				ENTITY_WHERE_CLAUSE = "kbcommentid = ?";
			}

			public static final int ADD_KB_COMMENT = 5;

			public static final int UPDATE_KB_COMMENT = 6;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

			public JSONObject getExtraData(
					ResultSet entityResultSet, String extraData)
				throws SQLException {

				JSONObject extraDataJSONObject = null;

				long classnameId = entityResultSet.getLong("classnameid");
				long classpk = entityResultSet.getLong("classpk");

				Connection con = null;
				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					con = DataAccess.getUpgradeOptimizedConnection();

					ExtraDataGenerator extraDataGenerator = null;

					if (classnameId == PortalUtil.getClassNameId(
							_kbArticleExtraDataGenerator.ACTIVITY_CLASSNAME)) {

						extraDataGenerator = _kbArticleExtraDataGenerator;
					}
					else if (classnameId == PortalUtil.getClassNameId(
								_kbTemplateExtraDataGenerator.
									ACTIVITY_CLASSNAME)) {

						extraDataGenerator = _kbTemplateExtraDataGenerator;
					}

					if (extraDataGenerator != null) {
						ps = con.prepareStatement(
							extraDataGenerator.getEntityQuery());

						ps.setLong(1, classpk);

						rs = ps.executeQuery();

						while (rs.next()) {
							extraDataJSONObject =
								extraDataGenerator.getExtraData(rs, "");
						}
					}
				}
				finally {
					DataAccess.cleanUp(con, ps, rs);
				}

				return extraDataJSONObject;
			}

		};

	private static final BaseExtraDataGenerator _kbTemplateExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{
				ACTIVITY_CLASSNAME =
					"com.liferay.knowledgebase.model.KBTemplate";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(ADD_KB_TEMPLATE)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(),
						String.valueOf(UPDATE_KB_TEMPLATE)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE = "title";

				ENTITY_FROM_CLAUSE = "KBTemplate";

				ENTITY_WHERE_CLAUSE = "kbtemplateid = ?";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "title"));
			}

			public static final int ADD_KB_TEMPLATE = 2;

			public static final int UPDATE_KB_TEMPLATE = 4;

			public void setEntityQueryParameters(
					PreparedStatement ps, long companyId, long groupId,
					long userId, long classNameId, long classPK, int type,
					String extraData)
				throws SQLException {

				ps.setLong(1, classPK);
			}

		};

	private static final ExtraDataGenerator _wikiPageExtraDataGenerator =
		new BaseExtraDataGenerator() {
			{

				ACTIVITY_CLASSNAME = "com.liferay.portlet.wiki.model.WikiPage";

				ACTIVITY_QUERY_PARAMS.put(
					1,
					new KeyValuePair(
						Long.class.getName(),
						String.valueOf(
							PortalUtil.getClassNameId(ACTIVITY_CLASSNAME))));

				ACTIVITY_QUERY_PARAMS.put(
					2,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(ADD_PAGE)));

				ACTIVITY_QUERY_PARAMS.put(
					3,
					new KeyValuePair(
						Integer.class.getName(), String.valueOf(UPDATE_PAGE)));

				ACTIVITY_QUERY_WHERE_CLAUSE =
					ACTIVITY_CLASSNAMEID_CLAUSE + " and (" +
						ACTIVITY_TYPE_CLAUSE + " or " + ACTIVITY_TYPE_CLAUSE +
							")";

				ENTITY_SELECT_CLAUSE = "title, version";

				ENTITY_FROM_CLAUSE = "WikiPage";

				ENTITY_WHERE_CLAUSE =
					"companyId = ? and groupId = ? and resourcePrimKey = ? " +
						"and head = true";

				EXTRA_DATA_MAP.put(
					"title", new KeyValuePair(String.class.getName(), "title"));

				EXTRA_DATA_MAP.put(
					"version",
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

	static {
		_extraDataGenerators.add(_addAssetCommentExtraDataGenerator);
		_extraDataGenerators.add(_addMessageExtraDataGenerator);
		_extraDataGenerators.add(_blogsEntryExtraDataGenerator);
		_extraDataGenerators.add(_bookmarksEntryExtraDataGenerator);
		_extraDataGenerators.add(_dlFileEntryExtraDataGenerator);
		_extraDataGenerators.add(_kbArticleExtraDataGenerator);
		_extraDataGenerators.add(_kbCommentExtraDataGenerator);
		_extraDataGenerators.add(_kbTemplateExtraDataGenerator);
		_extraDataGenerators.add(_wikiPageExtraDataGenerator);
	}

}