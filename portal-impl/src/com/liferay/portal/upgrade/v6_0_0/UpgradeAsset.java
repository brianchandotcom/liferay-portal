/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class UpgradeAsset extends UpgradeProcess {

	protected void addCategory(
			long entryId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			long parentCategoryId, String name, long vocabularyId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into AssetCategory (uuid_, categoryId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, parentCategoryId, name, vocabularyId) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, entryId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setLong(9, parentCategoryId);
			ps.setString(10, name);
			ps.setLong(11, vocabularyId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addEntry(
			long assetId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			long classNameId, long classPK, boolean visible,
			Timestamp startDate, Timestamp endDate, Timestamp publishDate,
			Timestamp expirationDate, String mimeType, String title,
			String description, String summary, String url, int height,
			int width, double priority, int viewCount)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("insert into AssetEntry (entryId, groupId, companyId, ");
			sb.append("userId, userName, createDate, modifiedDate, ");
			sb.append("classNameId, classPK, visible, startDate, endDate, ");
			sb.append("publishDate, expirationDate, mimeType, title, ");
			sb.append("description, summary, url, height, width, priority, ");
			sb.append("viewCount) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, assetId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setTimestamp(7, modifiedDate);
			ps.setLong(8, classNameId);
			ps.setLong(9, classPK);
			ps.setBoolean(10, visible);
			ps.setTimestamp(11, startDate);
			ps.setTimestamp(12, endDate);
			ps.setTimestamp(13, publishDate);
			ps.setTimestamp(14, expirationDate);
			ps.setString(15, mimeType);
			ps.setString(16, title);
			ps.setString(17, description);
			ps.setString(18, summary);
			ps.setString(19, url);
			ps.setInt(20, height);
			ps.setInt(21, width);
			ps.setDouble(22, priority);
			ps.setInt(23, viewCount);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addProperty(
			String tableName, String pkName, String assocationPKName,
			long propertyId, long companyId, long userId, String userName,
			Timestamp createDate, Timestamp modifiedDate, long categoryId,
			String key, String value)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("insert into ");
			sb.append(tableName);
			sb.append(" (");
			sb.append(pkName);
			sb.append(", companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, ");
			sb.append(assocationPKName);
			sb.append(", key_, value) values (?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, propertyId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, categoryId);
			ps.setString(8, key);
			ps.setString(9, value);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addTag(
			long entryId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(3);

			sb.append("insert into AssetTag (tagId, groupId, companyId, ");
			sb.append("userId, userName, createDate, modifiedDate, name) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, entryId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setTimestamp(7, modifiedDate);
			ps.setString(8, name);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addVocabulary(
			long vocabularyId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			String name, String description)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into AssetVocabulary (uuid_, vocabularyId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, name, description) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, vocabularyId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setString(9, name);
			ps.setString(10, description);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void copyAssociations(
			long tagsEntryId, String tableName, String pkName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from TagsAssets_TagsEntries where entryId = ?");

			ps.setLong(1, tagsEntryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long tagsAssetId = rs.getLong("assetId");

				runSQL(
					"insert into " + tableName + " (entryId, " + pkName +
						") values (" + tagsAssetId + ", " + tagsEntryId + ")");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void copyEntriesToCategories(long vocabularyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from TagsEntry where vocabularyId = ?");

			ps.setLong(1, vocabularyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long entryId = rs.getLong("entryId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentCategoryId = rs.getLong("parentEntryId");
				String name = rs.getString("name");

				addCategory(
					entryId, groupId, companyId, userId, userName, createDate,
					modifiedDate, parentCategoryId, name, vocabularyId);

				copyAssociations(
					entryId, "AssetEntries_AssetCategories", "categoryId");

				copyProperties(
					entryId, "AssetCategoryProperty", "categoryPropertyId",
					"categoryId");

				updateCategoryResource(companyId, entryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void copyProperties(
			long categoryId, String tableName, String pkName,
			String assocationPKName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from TagsProperty where entryId = ?");

			ps.setLong(1, categoryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long propertyId = rs.getLong("propertyId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String key = rs.getString("key_");
				String value = rs.getString("value");

				addProperty(
					tableName, pkName, assocationPKName, propertyId, companyId,
					userId, userName, createDate, modifiedDate, categoryId, key,
					value);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateAssetEntries();
		updateAssetCategories();
		updateAssetTags();
		updateResources();
	}

	protected void updateAssetCategories() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select * from TagsVocabulary where folksonomy = ?");

			ps.setBoolean(1, false);

			rs = ps.executeQuery();

			while (rs.next()) {
				long vocabularyId = rs.getLong("vocabularyId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");

				addVocabulary(
					vocabularyId, groupId, companyId, userId, userName,
					createDate, modifiedDate, name, description);

				copyEntriesToCategories(vocabularyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from TagsAsset");

			rs = ps.executeQuery();

			while (rs.next()) {
				long assetId = rs.getLong("assetId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				boolean visible = rs.getBoolean("visible");
				Timestamp startDate = rs.getTimestamp("startDate");
				Timestamp endDate = rs.getTimestamp("endDate");
				Timestamp publishDate = rs.getTimestamp("publishDate");
				Timestamp expirationDate = rs.getTimestamp("expirationDate");
				String mimeType = rs.getString("mimeType");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String summary = rs.getString("summary");
				String url = rs.getString("url");
				int height = rs.getInt("height");
				int width = rs.getInt("width");
				double priority = rs.getDouble("priority");
				int viewCount = rs.getInt("viewCount");

				addEntry(
					assetId, groupId, companyId, userId, userName, createDate,
					modifiedDate, classNameId, classPK, visible, startDate,
					endDate, publishDate, expirationDate, mimeType, title,
					description, summary, url, height, width, priority,
					viewCount);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetTags() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select TE.* from TagsEntry TE inner join TagsVocabulary TV " +
					"on TE.vocabularyId = TV.vocabularyId where " +
						"TV.folksonomy = ?");

			ps.setBoolean(1, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				long entryId = rs.getLong("entryId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");

				addTag(
					entryId, groupId, companyId, userId, userName, createDate,
					modifiedDate, name);

				copyAssociations(entryId, "AssetEntries_AssetTags", "tagId");

				copyProperties(
					entryId, "AssetTagProperty", "tagPropertyId", "tagId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		updateAssetTagsCount();
	}

	protected void updateAssetTagsCount() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("update AssetTag set assetCount = (select count(*) from ");
		sb.append("AssetEntry inner join AssetEntries_AssetTags on ");
		sb.append("AssetEntry.entryId = AssetEntries_AssetTags.entryId ");
		sb.append("where AssetEntry.visible = TRUE and AssetTag.tagId = ");
		sb.append("AssetEntries_AssetTags.tagId)");

		runSQL(sb.toString());
	}

	protected void updateCategoryResource(long companyId, long categoryId)
		throws Exception {

		String oldName = "com.liferay.portlet.tags.model.TagsEntry";
		String newName = AssetCategory.class.getName();

		runSQL(
			"update ResourcePermission set name = '" + newName + "' where " +
				"companyId = " + companyId + " and name = '" + oldName +
					"' and scope = " + ResourceConstants.SCOPE_INDIVIDUAL +
						" and primKey = '" + categoryId + "';");
	}

	protected void updateResources() throws Exception {
		updateResources(
			"com.liferay.portlet.tags", "com.liferay.portlet.asset"
		);

		updateResources(
			"com.liferay.portlet.tags.model.TagsEntry", AssetTag.class.getName()
		);

		updateResources(
			"com.liferay.portlet.tags.model.TagsAsset",
			AssetEntry.class.getName()
		);

		updateResources(
			"com.liferay.portlet.tags.model.TagsVocabulary",
			AssetVocabulary.class.getName()
		);
	}

	protected void updateResources(String oldCodeName, String newCodeName)
		throws Exception {

		runSQL(
			"update ResourceAction set name = '" + newCodeName + "' where" +
				" name = '" + oldCodeName + "';");

		runSQL(
			"update ResourcePermission set name = '" + newCodeName + "' where" +
				" name = '" + oldCodeName + "';");
	}

}