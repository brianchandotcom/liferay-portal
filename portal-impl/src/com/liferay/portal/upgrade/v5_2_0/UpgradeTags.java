/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchTagException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class UpgradeTags extends UpgradeProcess {

	protected void addEntry(
			long entryId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			long parentEntryId, String name, long vocabularyId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into TagsEntry (entryId, groupId, companyId, userId, " +
					"userName, createDate, modifiedDate, parentEntryId, " +
						"name, vocabularyId) values (?, ?, ?, ?, ?, ?, ?, ?, " +
							"?, ?)");

			ps.setLong(1, entryId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setTimestamp(7, modifiedDate);
			ps.setLong(8, parentEntryId);
			ps.setString(9, name);
			ps.setLong(10, vocabularyId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		addResources(
			companyId, "com.liferay.portlet.tags.model.TagsEntry",
			String.valueOf(entryId));
	}

	protected void addProperty(
			long propertyId, long companyId, long userId, String userName,
			Timestamp createDate, Timestamp modifiedDate, long entryId,
			String key, String value)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into TagsProperty (propertyId, companyId, userId, " +
					"userName, createDate, modifiedDate, entryId, key_, " +
						"value) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, propertyId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, entryId);
			ps.setString(8, key);
			ps.setString(9, value);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addResource(long resourceCodeId, String primKey)
		throws Exception {

		long resourceId = CounterLocalServiceUtil.increment(
			"com.liferay.portal.model.Resource");

		StringBundler sb = new StringBundler(8);

		sb.append("insert into Resource_ (resourceId, codeId, primKey) ");
		sb.append("values (");
		sb.append(resourceId);
		sb.append(", ");
		sb.append(resourceCodeId);
		sb.append(", '");
		sb.append(primKey);
		sb.append("')");

		runSQL(sb.toString());
	}

	protected void addResourceCode(
			long resourceCodeId, long companyId, String resourceName)
		throws Exception {

		StringBundler sb = new StringBundler(10);

		sb.append("insert into ResourceCode (codeId, companyId, name, scope) ");
		sb.append("values (");
		sb.append(resourceCodeId);
		sb.append(", ");
		sb.append(companyId);
		sb.append(", '");
		sb.append(resourceName);
		sb.append("', ");
		sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
		sb.append(")");

		runSQL(sb.toString());
	}

	protected void addResourcePermission(
			long companyId, long roleId, String resourceName, String primKey)
		throws Exception {

		StringBundler sb = new StringBundler(15);

		sb.append("insert into ResourcePermission (resourcePermissionId, ");
		sb.append("companyId, name, scope, primKey, roleId, actionIds) ");
		sb.append("values (");
		sb.append(increment());
		sb.append(", ");
		sb.append(companyId);
		sb.append(", '");
		sb.append(resourceName);
		sb.append("', ");
		sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
		sb.append(", '");
		sb.append(primKey);
		sb.append("', ");
		sb.append(roleId);
		sb.append(", 0)");

		runSQL(sb.toString());
	}

	protected void addResources(
			long companyId, String resourceName, String primKey)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(
					companyId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL);

			try {
				ResourceLocalServiceUtil.getResource(
					companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
					primKey);
			}
			catch (NoSuchResourceException nsre) {
				addResource(resourceCode.getCodeId(), primKey);
			}
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			try {
				Role role = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.OWNER);

				addResourcePermission(
					companyId, role.getRoleId(), resourceName, primKey);
			}
			catch (NoSuchRoleException nsre) {
			}
		}
	}

	protected long addVocabulary(
			long vocabularyId, long groupId, long companyId, long userId,
			String userName, String name)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("insert into TagsVocabulary (vocabularyId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, name, description, folksonomy) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, vocabularyId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, now);
			ps.setTimestamp(7, now);
			ps.setString(8, name);
			ps.setString(9, StringPool.BLANK);
			ps.setBoolean(10, true);

			ps.executeUpdate();

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		addResources(
			companyId, "com.liferay.portlet.tags.model.TagsVocabulary",
			String.valueOf(vocabularyId));

		return vocabularyId;
	}

	protected long copyEntry(long groupId, long entryId) throws Exception {
		String key = groupId + StringPool.UNDERLINE + entryId;

		Long newEntryId = _entryIdsMap.get(key);

		if (newEntryId != null) {
			return newEntryId.longValue();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsEntry where entryId = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentEntryId = rs.getLong("parentEntryId");
				String name = rs.getString("name");
				long vocabularyId = rs.getLong("vocabularyId");

				newEntryId = increment();

				addEntry(
					newEntryId, groupId, companyId, userId, userName,
					createDate, modifiedDate, parentEntryId, name,
					vocabularyId);

				copyProperties(entryId, newEntryId);

				_entryIdsMap.put(key, newEntryId);

				return newEntryId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		throw new NoSuchTagException(
			"No AssetTag exists with the primary key " + entryId);
	}

	protected void copyProperties(long entryId, long newEntryId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsProperty where entryId = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String key = rs.getString("key_");
				String value = rs.getString("value");

				long newPropertyId = increment();

				addProperty(
					newPropertyId, companyId, userId, userName, createDate,
					modifiedDate, newEntryId, key, value);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select entryId from TagsEntry where groupId = 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				long entryId = rs.getLong("entryId");

				runSQL(
					"delete from TagsAssets_TagsEntries where entryId = " +
						entryId);

				runSQL("delete from TagsProperty where entryId = " + entryId);
			}

			runSQL("delete from TagsEntry where groupId = 0");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateGroupIds();
		updateCategories();
		updateAssets();
	}

	protected long getVocabularyId(
			long groupId, long companyId, long userId, String userName,
			String name)
		throws Exception {

		name = name.trim();

		if (Validator.isNull(name) ||
			ArrayUtil.contains(_DEFAULT_CATEGORY_PROPERTY_VALUES, name)) {

			name = _DEFAULT_TAGS_VOCABULARY;
		}

		String key = groupId + StringPool.UNDERLINE + name;

		Long vocabularyId = _vocabularyIdsMap.get(key);

		if (vocabularyId != null) {
			return vocabularyId.longValue();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select vocabularyId from TagsVocabulary where groupId = ? " +
					"and name = ?");

			ps.setLong(1, groupId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				vocabularyId = rs.getLong("vocabularyId");
			}
			else {
				long newVocabularyId = increment();

				vocabularyId = addVocabulary(
					newVocabularyId, groupId, companyId, userId, userName,
					name);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		_vocabularyIdsMap.put(key, vocabularyId);

		return vocabularyId.longValue();
	}

	protected void updateAssets() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select resourcePrimKey from JournalArticle where approved " +
					"= ?");

			ps.setBoolean(1, false);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				runSQL(
					"update TagsAsset set visible = FALSE where classPK = " +
						resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCategories() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select TE.entryId, TE.groupId, TE.companyId, ");
			sb.append("TE.userId, TE.userName, TP.propertyId, TP.value from ");
			sb.append("TagsEntry TE, TagsProperty TP where TE.entryId = ");
			sb.append("TP.entryId and TE.vocabularyId <= 0 and TP.key_ = ");
			sb.append("'category'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long entryId = srs.getLong("TE.entryId");
				long groupId = srs.getLong("TE.groupId");
				long companyId = srs.getLong("TE.companyId");
				long userId = srs.getLong("TE.userId");
				String userName = srs.getString("TE.userName");
				long propertyId = srs.getLong("TP.propertyId");
				String value = srs.getString("TP.value");

				long vocabularyId = getVocabularyId(
					groupId, companyId, userId, userName, value);

				runSQL(
					"update TagsEntry set vocabularyId = " + vocabularyId +
						" where entryId = " + entryId);

				runSQL(
					"delete from TagsProperty where propertyId = " +
						propertyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select TA.assetId, TA.groupId, TA_TE.entryId from " +
					"TagsAssets_TagsEntries TA_TE inner join TagsAsset TA on " +
						"TA.assetId = TA_TE.assetId",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long assetId = srs.getLong("TA.assetId");
				long groupId = srs.getLong("TA.groupId");
				long entryId = srs.getLong("TA_TE.entryId");

				long newEntryId = copyEntry(groupId, entryId);

				runSQL(
					"insert into TagsAssets_TagsEntries (assetId, entryId) " +
						"values (" + assetId + ", " + newEntryId + ")");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		deleteEntries();
	}

	private static final String[] _DEFAULT_CATEGORY_PROPERTY_VALUES = {
		"undefined", "no category", "category"
	};

	private static final String _DEFAULT_TAGS_VOCABULARY = "Default Tag Set";

	private Map<String, Long> _entryIdsMap = new HashMap<String, Long>();
	private Map<String, Long> _vocabularyIdsMap = new HashMap<String, Long>();

}