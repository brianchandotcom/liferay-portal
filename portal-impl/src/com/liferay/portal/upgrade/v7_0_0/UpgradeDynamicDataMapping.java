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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v7_0_0.util.DDMContentTable;
import com.liferay.portal.upgrade.v7_0_0.util.DDMFormValuesXSDDeserializer;
import com.liferay.portal.upgrade.v7_0_0.util.DDMStructureTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	protected void addStructureVersion(
			long structureVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long structureId,
			String name, String description, String definition,
			String storageType, int type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("insert into DDMStructureVersion (structureVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("structureId, version, name, description, definition, ");
			sb.append("storageType, type_) values (?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, structureVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, structureId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, definition);
			ps.setString(12, storageType);
			ps.setInt(13, type);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addStructureVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String definition = rs.getString("definition");
				String storageType = rs.getString("storageType");
				int type = rs.getInt("type_");

				addStructureVersion(
					increment(), groupId, companyId, userId, userName,
					modifiedDate, structureId, name, description, definition,
					storageType, type);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void addTemplateVersion(
			long templateVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long templateId, String name,
			String description, String language, String script)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DDMTemplateVersion (templateVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("templateId, version, name, description, language, ");
			sb.append("script) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, templateVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setLong(7, templateId);
			ps.setString(8, DDMStructureConstants.VERSION_DEFAULT);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, language);
			ps.setString(12, script);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping template version " +
					"with template ID " + templateId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addTemplateVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMTemplate");

			rs = ps.executeQuery();

			while (rs.next()) {
				long templateId = rs.getLong("templateId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String language = rs.getString("language");
				String script = rs.getString("script");

				addTemplateVersion(
					increment(), groupId, companyId, userId, userName,
					modifiedDate, templateId, name, description, language,
					script);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void convertDDLRecord(
			Connection con, long ddmStructureId, long ddlRecordSetId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(2);

			sb.append("select DDMStorageId from DDLRecord where ");
			sb.append("recordSetId=?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, ddlRecordSetId);
			rs = ps.executeQuery();

			while (rs.next()) {
				long ddmContentId = rs.getLong("ddmStorageId");

				convertDDMContent(con, ddmStructureId, ddmContentId);
			}
		}
		finally {
			DataAccess.cleanUp(rs);
			DataAccess.cleanUp(ps);
		}
	}

	protected void convertDDLRecordSet(Connection con, long structureId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(2);

			sb.append("select classPK from DDMStructureLink where ");
			sb.append("classNameId=? and structureId=?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			long classNameId = PortalUtil.getClassNameId(
				DDLRecordSet.class.getName());

			ps.setLong(1, classNameId);
			ps.setLong(2, structureId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long ddlRecordSetId = rs.getLong("classPK");

				convertDDLRecord(con, structureId, ddlRecordSetId);
			}
		}
		finally {
			DataAccess.cleanUp(rs);
			DataAccess.cleanUp(ps);
		}
	}

	protected void convertDDMContent(
			Connection con, long ddmStructureId, long ddmContentId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			DDMForm ddmForm = fetchDDMForm(ddmStructureId);

			String ddmContentData = fetchDDMContentData(ddmContentId);

			DDMFormValues ddmFormValues =
				DDMFormValuesXSDDeserializer.deserialize(
					ddmForm, ddmContentData);

			String sql = "update DDMContent set data_=? where contentId=?";

			ps = con.prepareStatement(sql);

			ps.setString(
				1, DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues));
			ps.setLong(2, ddmContentId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void convertDDMStructureStorageType(
			Connection connection, long structureId)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(2);

			sb.append("update DDMStructure set storageType=\"json\" ");
			sb.append("where structureId=? ");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, structureId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void convertDDMStructureVersionStorageType(
			Connection connection, long structureId)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(2);

			sb.append("update DDMStructureVersion set storageType=\"json\" ");
			sb.append("where structureId=? ");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, structureId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void convertToJSONStorage() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DDMStructure");

			rs = ps.executeQuery();

			while (rs.next()) {
				long structureId = rs.getLong("structureId");

				StorageType storageType = StorageType.parse(
					rs.getString("storageType"));

				if (storageType.equals(StorageType.XML)) {
					convertToJSONStorage(structureId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void convertToJSONStorage(long structureId) throws Exception {
		Connection con = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			con.setAutoCommit(false);

			convertDDLRecordSet(con, structureId);

			convertDDMStructureStorageType(con, structureId);

			convertDDMStructureVersionStorageType(con, structureId);

			con.commit();
		}
		catch (Exception e) {
			_log.error(
				"Unable to upgrade dynamic data mapping structure version " +
					"with structure ID " + structureId);

			throw e;
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_name DDMContent xml data_ TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMContentTable.TABLE_NAME, DDMContentTable.TABLE_COLUMNS,
				DDMContentTable.TABLE_SQL_CREATE,
				DDMContentTable.TABLE_SQL_ADD_INDEXES);
		}

		try {
			runSQL("alter_column_name DDMStructure xsd definition TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMStructureTable.TABLE_NAME, DDMStructureTable.TABLE_COLUMNS,
				DDMStructureTable.TABLE_SQL_CREATE,
				DDMStructureTable.TABLE_SQL_ADD_INDEXES);
		}

		convertToJSONStorage();
		addStructureVersions();
		addTemplateVersions();
	}

	protected String fetchDDMContentData(long ddmContentId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = "select data_ from DDMContent where contentId=?";

			ps = con.prepareStatement(sql);

			ps.setLong(1, ddmContentId);

			rs = ps.executeQuery();

			rs.first();

			return rs.getString("data_");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected DDMForm fetchDDMForm(long ddmStructureId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(2);

			sb.append("select parentStructureId, definition from DDMStructure");
			sb.append(" where structureId=?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, ddmStructureId);

			rs = ps.executeQuery();

			rs.first();

			String definition = rs.getString("definition");

			DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
				definition);

			long parentStructureId = rs.getLong("parentStructureId");

			if (parentStructureId != 0) {
				DDMForm parentDDMForm = fetchDDMForm(parentStructureId);

				List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

				ddmFormFields.addAll(parentDDMForm.getDDMFormFields());
			}

			return ddmForm;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataMapping.class);

}