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

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio Sanchez
 * @author Zsolt Berentey
 * @author Daniel Sanz
 */
public class UpgradeSocial extends UpgradeProcess {

	protected String createExtraData(
			ExtraDataFactory extraDataFactory, long companyId, long groupId,
			long userId, long classNameId, long classPK, int type,
			String extraData)
		throws Exception {

		if (extraDataFactory == null) {
			return null;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				extraDataFactory.getSQL())) {

			extraDataFactory.setModelSQLParameters(
				preparedStatement, groupId, companyId, userId, classNameId,
				classPK, type, extraData);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					JSONObject extraDataJSONObject =
						extraDataFactory.createExtraDataJSONObject(
							resultSet, extraData);

					return extraDataJSONObject.toString();
				}

				return null;
			}
		}
	}

	protected Map<Long, String> createExtraDataMap(
			ExtraDataFactory extraDataFactory)
		throws Exception {

		Map<Long, String> extraDataMap = new HashMap<>();

		StringBundler sb = new StringBundler(4);

		sb.append("select activityId, groupId, companyId, userId, ");
		sb.append("classNameId, classPK, type_, extraData from ");
		sb.append("SocialActivity where ");
		sb.append(extraDataFactory.getActivitySQLWhereClause());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			extraDataFactory.setActivitySQLParameters(preparedStatement);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long activityId = resultSet.getLong("activityId");
					long classNameId = resultSet.getLong("classNameId");
					long classPK = resultSet.getLong("classPK");
					long companyId = resultSet.getLong("companyId");
					String extraData = resultSet.getString("extraData");
					long groupId = resultSet.getLong("groupId");
					int type = resultSet.getInt("type_");
					long userId = resultSet.getLong("userId");

					String newExtraData = createExtraData(
						extraDataFactory, groupId, companyId, userId,
						classNameId, classPK, type, extraData);

					if (newExtraData != null) {
						extraDataMap.put(activityId, newExtraData);
					}
				}
			}
		}

		return extraDataMap;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateJournalActivities();
		updateSOSocialActivities();

		updateActivities();
	}

	protected void updateActivities() throws Exception {
		ExtraDataFactory[] extraDataFactories = {
			new AddAssetCommentExtraDataFactory(),
			new AddMessageExtraDataFactory(), new BlogsEntryExtraDataFactory(),
			new BookmarksEntryExtraDataFactory(),
			new DLFileEntryExtraDataFactory(), new KBArticleExtraDataFactory(),
			new KBCommentExtraDataFactory(), new KBTemplateExtraDataFactory(),
			new WikiPageExtraDataFactory()
		};

		for (ExtraDataFactory extraDataFactory : extraDataFactories) {
			updateActivities(extraDataFactory);
		}
	}

	protected void updateActivities(ExtraDataFactory extraDataFactory)
		throws Exception {

		Map<Long, String> extraDataMap = createExtraDataMap(extraDataFactory);

		for (Map.Entry<Long, String> entry : extraDataMap.entrySet()) {
			long activityId = entry.getKey();
			String extraData = entry.getValue();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update SocialActivity set extraData = ? where " +
							"activityId = ?")) {

				preparedStatement.setString(1, extraData);
				preparedStatement.setLong(2, activityId);

				preparedStatement.executeUpdate();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to update activity " + activityId, e);
				}
			}
		}
	}

	protected void updateJournalActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.journal.model.JournalArticle");

			String[] tableNames = {"SocialActivity", "SocialActivityCounter"};

			for (String tableName : tableNames) {
				StringBundler sb = new StringBundler(7);

				sb.append("update ");
				sb.append(tableName);
				sb.append(" set classPK = (select resourcePrimKey from ");
				sb.append("JournalArticle where id_ = ");
				sb.append(tableName);
				sb.append(".classPK) where classNameId = ");
				sb.append(classNameId);

				runSQL(sb.toString());
			}
		}
	}

	protected void updateSOSocialActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasTable("SO_SocialActivity")) {
				return;
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select activityId, activitySetId from " +
							"SO_SocialActivity");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long activityId = resultSet.getLong("activityId");
					long activitySetId = resultSet.getLong("activitySetId");

					StringBundler sb = new StringBundler(4);

					sb.append("update SocialActivity set activitySetId = ");
					sb.append(activitySetId);
					sb.append(" where activityId = ");
					sb.append(activityId);

					runSQL(sb.toString());
				}
			}

			runSQL("drop table SO_SocialActivity");
		}
	}

	/**
	 * Defines the necessary methods to generate extra data from a set of
	 * social activities of any kind. Implementations have to focus on:
	 *   1) What is the set of social activities the Factory generates extra
	 *      data for. See #getActivitySQLWhereClause() and
	 *      #setActivityQueryParameters(),
	 *   2) How to obtain the model entities related to such activities. See
	 *      #getSQL() and #setModelSQLParameters(),
	 *   3) How to generate extra data from that model entity. See
	 *      #createExtraDataJSONObject()
	 *
	 * The ExtraData framework works with a list of ExtraData factories.
	 * For each one, a query to SocialActivity table is built and run (1). The
	 * extra data for each SocialActivity tuple returned by the query is
	 * generated by querying the appropriate entity as dictated by the Factory
	 * (2), then building the extra data from that entity tuple (3). The
	 * framework then updated the extra data in the original SocialActivity
	 * tuple, so that the Social Activity Interpreters can find what they need
	 * after the upgrade.
	 */
	protected interface ExtraDataFactory {

		/**
		 * Given a result from the #getSQL() and the original extra data in the
		 * SocialActivity tuple pointing to that entity, computes the extra data
		 * that will be persisted in the SocialActivity tuple as a result of the
		 * upgrade process.
		 *
		 * @return JSONObject containing the extra data
		 */
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException;

		/** Returns the class name of the entity which activities this Factory
		 * generates extra data for. Useful if Factory needs to filter
		 * activities by classNameId or refer to other Factory's class names
		 */
		public String getActivityClassName();

		/**
		 * Returns the "where" clause in social activity query to select the
		 * SocialActivity tuples this Factory will generate extra data for
		 */
		public String getActivitySQLWhereClause();

		/**
		 * Returns the SQL query on any model entity which the selected
		 * SocialActivity tuples refer to. Extra data will be generated from
		 * the entities returned by this query
		 */
		public String getSQL();

		/**
		 * Sets parameters required to run the activity query returned by
		 * #getActivitySQLWhereClause() in this Factory
		 */
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException;

		/**
		 * Sets parameters required to run the entity query returned by
		 * #getSQL() in this Factory, based on fields from the SocialActivity
		 * tuple
		 */
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException;

	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

	private class AddAssetCommentExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			long messageId = 0;

			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					extraData);

				messageId = jsonObject.getLong("messageId");
			}
			catch (JSONException jsone) {
			}

			extraDataJSONObject.put("messageId", messageId);

			extraDataJSONObject.put("title", resultSet.getString("subject"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return StringPool.BLANK;
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "type_ = ?";
		}

		@Override
		public String getSQL() {
			return "select subject from MBMessage where messageId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setInt(1, _TYPE_ADD_COMMENT);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			long messageId = 0;

			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
					extraData);

				messageId = jsonObject.getLong("messageId");
			}
			catch (JSONException jsone) {
			}

			preparedStatement.setLong(1, messageId);
		}

		private static final int _TYPE_ADD_COMMENT = 10005;

	};

	private class AddMessageExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("subject"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.messageboards.model.MBMessage";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select subject from MBMessage where messageId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_MESSAGE);
			preparedStatement.setInt(3, _REPLY_MESSAGE);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_MESSAGE = 1;

		private static final int _REPLY_MESSAGE = 2;

	};

	private class BlogsEntryExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.blogs.model.BlogsEntry";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select title from BlogsEntry where entryId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_ENTRY);
			preparedStatement.setInt(3, _UPDATE_ENTRY);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_ENTRY = 2;

		private static final int _UPDATE_ENTRY = 3;

	};

	private class BookmarksEntryExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("name"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.bookmarks.model.BookmarksEntry";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select name from BookmarksEntry where entryId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_ENTRY);
			preparedStatement.setInt(3, _UPDATE_ENTRY);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_ENTRY = 1;

		private static final int _UPDATE_ENTRY = 2;

	};

	private class DLFileEntryExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.documentlibrary.model.DLFileEntry";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ?";
		}

		@Override
		public String getSQL() {
			return "select title from DLFileEntry where companyId = ? and " +
				"groupId = ? and fileEntryId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, classPK);
		}

	};

	private class KBArticleExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBArticle";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select title from KBArticle where resourcePrimKey = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_KB_ARTICLE);
			preparedStatement.setInt(3, _UPDATE_KB_ARTICLE);
			preparedStatement.setInt(4, _MOVE_KB_ARTICLE);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_KB_ARTICLE = 1;

		private static final int _MOVE_KB_ARTICLE = 7;

		private static final int _UPDATE_KB_ARTICLE = 3;

	};

	private class KBCommentExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			long classNameId = resultSet.getLong("classNameId");
			long classPK = resultSet.getLong("classPK");

			ExtraDataFactory extraDataFactory = null;

			if (classNameId == PortalUtil.getClassNameId(
					_kbArticleExtraDataFactory.getActivityClassName())) {

				extraDataFactory = _kbArticleExtraDataFactory;
			}
			else if (classNameId == PortalUtil.getClassNameId(
						_kbTemplateExtraDataFactory.getActivityClassName())) {

				extraDataFactory = _kbTemplateExtraDataFactory;
			}

			if (extraDataFactory == null) {
				return null;
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						extraDataFactory.getSQL())) {

				preparedStatement.setLong(1, classPK);

				try (ResultSet curResultSet =
						preparedStatement.executeQuery()) {

					while (curResultSet.next()) {
						return extraDataFactory.createExtraDataJSONObject(
							curResultSet, StringPool.BLANK);
					}
				}
			}

			return null;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBComment";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select classNameId, classPK from KBComment where " +
				"kbCommentId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_KB_COMMENT);
			preparedStatement.setInt(3, _UPDATE_KB_COMMENT);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_KB_COMMENT = 5;

		private static final int _UPDATE_KB_COMMENT = 6;

		private final KBArticleExtraDataFactory _kbArticleExtraDataFactory =
			new KBArticleExtraDataFactory();
		private final KBTemplateExtraDataFactory _kbTemplateExtraDataFactory =
			new KBTemplateExtraDataFactory();

	};

	private class KBTemplateExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBTemplate";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select title from KBTemplate where kbTemplateId = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_KB_TEMPLATE);
			preparedStatement.setInt(3, _UPDATE_KB_TEMPLATE);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, classPK);
		}

		private static final int _ADD_KB_TEMPLATE = 2;

		private static final int _UPDATE_KB_TEMPLATE = 4;

	};

	private class WikiPageExtraDataFactory implements ExtraDataFactory {

		@Override
		public JSONObject createExtraDataJSONObject(
				ResultSet resultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", resultSet.getString("title"));
			extraDataJSONObject.put("version", resultSet.getDouble("version"));

			return extraDataJSONObject;
		}

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.wiki.model.WikiPage";
		}

		@Override
		public String getActivitySQLWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getSQL() {
			return "select title, version from WikiPage where companyId = ? " +
				"and groupId = ? and resourcePrimKey = ? and head = ?";
		}

		@Override
		public void setActivitySQLParameters(
				PreparedStatement preparedStatement)
			throws SQLException {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(getActivityClassName()));
			preparedStatement.setInt(2, _ADD_PAGE);
			preparedStatement.setInt(3, _UPDATE_PAGE);
		}

		@Override
		public void setModelSQLParameters(
				PreparedStatement preparedStatement, long companyId,
				long groupId, long userId, long classNameId, long classPK,
				int type, String extraData)
			throws SQLException {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, classPK);
			preparedStatement.setBoolean(4, true);
		}

		private static final int _ADD_PAGE = 1;

		private static final int _UPDATE_PAGE = 2;

	};

}