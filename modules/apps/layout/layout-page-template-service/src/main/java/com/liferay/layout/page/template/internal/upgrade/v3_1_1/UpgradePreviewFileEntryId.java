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

package com.liferay.layout.page.template.internal.upgrade.v3_1_1;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Rubén Pulido
 */
public class UpgradePreviewFileEntryId extends UpgradeProcess {

	public UpgradePreviewFileEntryId() {
	}

	@Override
	protected void doUpgrade() throws UpgradeException {
		try {
			_insertRepository();
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle, sqle);
			}

			throw new UpgradeException(sqle);
		}
	}

	private void _insertRepository() throws SQLException {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select mvccVersion, groupId, companyId, userId,  ");
		sb1.append("userName, classNameId, name, description, typeSettings, ");
		sb1.append("dlFolderId, lastPublishDate from Repository where ");
		sb1.append("portletId = '");
		sb1.append(LayoutAdminPortletKeys.GROUP_PAGES);
		sb1.append("'");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into Repository (mvccVersion, uuid_, ");
		sb2.append("repositoryId, groupId, companyId, userId, userName, ");
		sb2.append("createDate, modifiedDate, classNameId, name,  ");
		sb2.append("description, portletId, typeSettings, dlFolderId,  ");
		sb2.append("lastPublishDate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		sb2.append("?, ?, ?, ?, ?, ?)");

		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sb1.toString());
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(sb2.toString()))) {

			while (rs.next()) {
				long mvccVersion = rs.getLong("mvccVersion");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				long classNameId = rs.getLong("classNameId");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String typeSettings = rs.getString("typeSettings");
				long dlFolderId = rs.getLong("dlFolderId");
				Timestamp lastPublishDate = rs.getTimestamp("lastPublishDate");

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				ps.setLong(1, mvccVersion);
				ps.setString(2, PortalUUIDUtil.generate());
				ps.setLong(3, increment());
				ps.setLong(4, groupId);
				ps.setLong(5, companyId);
				ps.setLong(6, userId);
				ps.setString(7, userName);
				ps.setTimestamp(8, timestamp);
				ps.setTimestamp(9, timestamp);
				ps.setLong(10, classNameId);
				ps.setString(11, name);
				ps.setString(12, description);
				ps.setString(
					13,
					LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);
				ps.setString(14, typeSettings);
				ps.setLong(15, dlFolderId);
				ps.setTimestamp(16, lastPublishDate);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePreviewFileEntryId.class);

}