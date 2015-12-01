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

package com.liferay.dynamic.data.lists.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataLists extends UpgradeProcess {

	protected void addDDMContent(
			String uuid_, long contentId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String name, String description, String xml)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(4);

			sb.append("insert into DDMContent (uuid_, contentId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, name, description, xml) values (?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setString(1, uuid_);
			ps.setLong(2, contentId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, modifiedDate);
			ps.setString(9, name);
			ps.setString(10, description);
			ps.setString(11, xml);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error("Unable to add dynamic data mapping content ", e);

			throw e;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addDynamicContentElements(
		Element dynamicElementElement, String name, String data) {

		Map<Locale, String> localizationMap =
			LocalizationUtil.getLocalizationMap(data);

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			String[] values = StringUtil.split(entry.getValue());

			if (name.startsWith(StringPool.UNDERLINE)) {
				values = new String[] {entry.getValue()};
			}

			for (String value : values) {
				Element dynamicContentElement =
					dynamicElementElement.addElement("dynamic-content");

				dynamicContentElement.addAttribute(
					"language-id", LanguageUtil.getLanguageId(entry.getKey()));
				dynamicContentElement.addCDATA(value.trim());
			}
		}
	}

	protected void deleteExpandoData(Set<Long> expandoRowIds) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select tableId from ExpandoRow where " +
					getExpandoRowIds(expandoRowIds) + " group by tableId");

			int parameterIndex = 1;

			for (long expandoRowId : expandoRowIds) {
				ps.setLong(parameterIndex++, expandoRowId);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				long tableId = rs.getLong("tableId");

				runSQL("delete from ExpandoTable where tableId = " + tableId);

				runSQL("delete from ExpandoRow where tableId = " + tableId);

				runSQL("delete from ExpandoColumn where tableId = " + tableId);

				runSQL("delete from ExpandoValue where tableId = " + tableId);
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		setUpClassNameIds();

		upgradeRecordVersions();
	}

	protected String getExpandoRowIds(Set<Long> expandoRowIds) {
		StringBundler sb = new StringBundler((expandoRowIds.size() * 2) + 1);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < expandoRowIds.size(); i++) {
			sb.append("rowId_ = ?");

			if ((i + 1) < expandoRowIds.size()) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected Map<String, String> getExpandoValuesMap(long expandoRowId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(4);

			sb.append("select ExpandoColumn.name, ExpandoValue.data_ from ");
			sb.append("ExpandoValue inner join ExpandoColumn on ");
			sb.append("ExpandoColumn.columnId = ExpandoValue.columnId where ");
			sb.append("rowId_ = ?");

			ps = connection.prepareStatement(sb.toString());

			ps.setLong(1, expandoRowId);

			rs = ps.executeQuery();

			Map<String, String> fieldsMap = new HashMap<>();

			while (rs.next()) {
				String name = rs.getString("name");
				String data_ = rs.getString("data_");

				fieldsMap.put(name, data_);
			}

			return fieldsMap;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected String getUpgradeRecordVersionsSQL() {
		StringBundler sb = new StringBundler(5);

		sb.append("select DDLRecordVersion.* from DDLRecordVersion inner ");
		sb.append("join DDLRecordSet on DDLRecordVersion.recordSetId = ");
		sb.append("DDLRecordSet.recordSetId inner join DDMStructure on ");
		sb.append("DDLRecordSet.ddmStructureId = DDMStructure.structureId ");
		sb.append("where DDMStructure.storageType = 'expando'");

		return sb.toString();
	}

	protected void setUpClassNameIds() {
		_ddmContentClassNameId = PortalUtil.getClassNameId(DDMContent.class);

		_expandoStorageAdapterClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.dynamicdatamapping.ExpandoStorageAdapter");
	}

	protected String toXML(Map<String, String> expandoValuesMap) {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		for (Map.Entry<String, String> entry : expandoValuesMap.entrySet()) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			String name = entry.getKey();
			String data = entry.getValue();

			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute(
				"default-language-id",
				LocalizationUtil.getDefaultLanguageId(data));

			addDynamicContentElements(dynamicElementElement, name, data);
		}

		return document.asXML();
	}

	protected void updateDDMStorageLink(
			long oldClassNameId, long oldClassPK, long newClassNameId,
			long newClassPK)
		throws Exception {

		runSQL(
			"update DDMStorageLink set classNameId = " + newClassNameId + ", " +
				"classPK = " + newClassPK + " where classNameId = " +
					oldClassNameId + " and classPK = " + oldClassPK);
	}

	protected void updateDDMStructureStorageType() throws Exception {
		runSQL(
			"update DDMStructure set storageType = 'xml' where storageType = " +
				"'expando'");
	}

	protected void updateRecordDDMStorageId(
			long recordId, String version, long ddmContentId)
		throws Exception {

		runSQL(
			"update DDLRecord set ddmStorageId = " + ddmContentId +
				" where recordId = " + recordId + " and version = '" +
					version + "'");
	}

	protected void updateRecordVersionDDMStorageId(
			long recordVersionId, long DDMStorageId)
		throws Exception {

		runSQL(
			"update DDLRecordVersion set ddmStorageId = " + DDMStorageId +
				" where recordVersionId = " + recordVersionId);
	}

	protected void upgradeRecordVersions() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = getUpgradeRecordVersionsSQL();

			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();

			Set<Long> ddmStorageIds = new HashSet<>();

			while (rs.next()) {
				long recordVersionId = rs.getLong("recordVersionId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				long ddmStorageId = rs.getLong("DDMStorageId");
				long recordId = rs.getLong("recordId");
				String version = rs.getString("version");

				long ddmContentId = increment();

				Map<String, String> expandoValuesMap = getExpandoValuesMap(
					ddmStorageId);

				String xml = toXML(expandoValuesMap);

				addDDMContent(
					PortalUUIDUtil.generate(), ddmContentId, groupId, companyId,
					userId, userName, createDate, createDate,
					DDMStorageLink.class.getName(), null, xml);

				updateRecordVersionDDMStorageId(recordVersionId, ddmContentId);

				updateRecordDDMStorageId(recordId, version, ddmContentId);

				updateDDMStorageLink(
					_expandoStorageAdapterClassNameId, ddmStorageId,
					_ddmContentClassNameId, ddmContentId);

				ddmStorageIds.add(ddmStorageId);
			}

			if (ddmStorageIds.isEmpty()) {
				return;
			}

			updateDDMStructureStorageType();

			deleteExpandoData(ddmStorageIds);
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDynamicDataLists.class);

	private long _ddmContentClassNameId;
	private long _expandoStorageAdapterClassNameId;

}