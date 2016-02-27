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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Sergio Sanchez
 * @author Zsolt Berentey
 */
public class UpgradeSocial extends UpgradeProcess {

	protected void addActivity(
			long activityId, long groupId, long companyId, long userId,
			Timestamp createDate, long mirrorActivityId, long classNameId,
			long classPK, int type, String extraData, long receiverUserId)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into SocialActivity (activityId, groupId, ");
		sb.append("companyId, userId, createDate, mirrorActivityId, ");
		sb.append("classNameId, classPK, type_, extraData, ");
		sb.append("receiverUserId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?)");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, activityId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setLong(5, createDate.getTime());
			ps.setLong(6, mirrorActivityId);
			ps.setLong(7, classNameId);
			ps.setLong(8, classPK);
			ps.setInt(9, type);
			ps.setString(10, extraData);
			ps.setLong(11, receiverUserId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add activity " + activityId, e);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateDLFileVersionActivities();
		updateJournalActivities();
		updateSOSocialActivities();
		updateWikiPageActivities();
	}

	protected Timestamp getUniqueModifiedDate(
		Set<String> keys, long groupId, long userId, Timestamp modifiedDate,
		long classNameId, long resourcePrimKey, double type) {

		while (true) {
			StringBundler sb = new StringBundler(11);

			sb.append(groupId);
			sb.append(StringPool.DASH);
			sb.append(userId);
			sb.append(StringPool.DASH);
			sb.append(modifiedDate);
			sb.append(StringPool.DASH);
			sb.append(classNameId);
			sb.append(StringPool.DASH);
			sb.append(resourcePrimKey);
			sb.append(StringPool.DASH);
			sb.append(type);

			String key = sb.toString();

			modifiedDate = new Timestamp(modifiedDate.getTime() + 1);

			if (!keys.contains(key)) {
				keys.add(key);

				return modifiedDate;
			}
		}
	}

	protected void updateDLFileVersionActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.documentlibrary.model.DLFolder");

			runSQL(
				"delete from SocialActivity where classNameId = " +
					classNameId);

			Set<String> keys = new HashSet<>();

			try (PreparedStatement ps = connection.prepareStatement(
					"select groupId, companyId, userId, modifiedDate, " +
						"fileEntryId, title, version from DLFileVersion " +
							"where status = ?")) {

				ps.setInt(1, WorkflowConstants.STATUS_APPROVED);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long groupId = rs.getLong("groupId");
						long companyId = rs.getLong("companyId");
						long userId = rs.getLong("userId");
						Timestamp modifiedDate = rs.getTimestamp(
							"modifiedDate");
						long fileEntryId = rs.getLong("fileEntryId");
						String title = rs.getString("title");
						double version = rs.getDouble("version");

						int type = DLActivityKeys.ADD_FILE_ENTRY;

						if (version > 1.0) {
							type = DLActivityKeys.UPDATE_FILE_ENTRY;
						}

						modifiedDate = getUniqueModifiedDate(
							keys, groupId, userId, modifiedDate, classNameId,
							fileEntryId, type);

						JSONObject extraDataJSONObject =
							JSONFactoryUtil.createJSONObject();

						extraDataJSONObject.put("title", title);

						addActivity(
							increment(), groupId, companyId, userId,
							modifiedDate, 0, classNameId, fileEntryId, type,
							extraDataJSONObject.toString(), 0);
					}
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
				sb.append(" set classPK = (select resourcePrimKey ");
				sb.append("from JournalArticle where id_ = ");
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

			try (PreparedStatement ps = connection.prepareStatement(
					"select activityId, activitySetId from SO_SocialActivity");
				ResultSet rs = ps.executeQuery()) {

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

			runSQL("drop table SO_SocialActivity");
		}
	}

	protected void updateWikiPageActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.wiki.model.WikiPage");

			runSQL(
				"delete from SocialActivity where classNameId = " +
					classNameId);

			try (PreparedStatement ps = connection.prepareStatement(
					"select groupId, companyId, userId, modifiedDate, " +
						"resourcePrimKey, version from WikiPage");
				ResultSet rs = ps.executeQuery()) {

				Set<String> keys = new HashSet<>();

				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					long resourcePrimKey = rs.getLong("resourcePrimKey");
					double version = rs.getDouble("version");

					int type = 1;

					if (version > 1.0) {
						type = 2;
					}

					modifiedDate = getUniqueModifiedDate(
						keys, groupId, userId, modifiedDate, classNameId,
						resourcePrimKey, type);

					JSONObject extraDataJSONObject =
						JSONFactoryUtil.createJSONObject();

					extraDataJSONObject.put("version", version);

					addActivity(
						increment(), groupId, companyId, userId, modifiedDate,
						0, classNameId, resourcePrimKey, type,
						extraDataJSONObject.toString(), 0);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

}