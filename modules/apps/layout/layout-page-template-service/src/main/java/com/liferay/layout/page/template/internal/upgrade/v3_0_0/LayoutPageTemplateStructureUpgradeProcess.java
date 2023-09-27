/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author David Arques
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_upgradeLayoutPageTemplatesStructures();

		_alterTable();
	}

	private void _alterTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterTableDropColumn("LayoutPageTemplateStructure", "data_");
		}
	}

	private void _updateLayoutPageTemplateStructureRels(
		long groupId, long companyId, long userId, String userName,
		Timestamp createDate, long layoutPageTemplateStructureId, String data) {

		String sql = StringBundler.concat(
			"insert into LayoutPageTemplateStructureRel (uuid_, ",
			"lPageTemplateStructureRelId, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, ",
			"layoutPageTemplateStructureId, segmentsExperienceId, data_) ",
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(1, PortalUUIDUtil.generate());
			preparedStatement.setLong(2, increment());
			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, userId);
			preparedStatement.setString(6, userName);
			preparedStatement.setTimestamp(7, createDate);
			preparedStatement.setTimestamp(8, createDate);
			preparedStatement.setLong(9, layoutPageTemplateStructureId);
			preparedStatement.setLong(10, _SEGMENTS_EXPERIENCE_ID_DEFAULT);
			preparedStatement.setString(11, data);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _upgradeLayoutPageTemplatesStructures() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select layoutPageTemplateStructureId, groupId, ",
					"companyId, userId, userName, createDate, data_ from ",
					"LayoutPageTemplateStructure"))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long layoutPageTemplateStructureId = resultSet.getLong(
						"layoutPageTemplateStructureId");
					long groupId = resultSet.getLong("groupId");
					long companyId = resultSet.getLong("companyId");
					long userId = resultSet.getLong("userId");
					String userName = resultSet.getString("userName");
					Timestamp createDate = resultSet.getTimestamp("createDate");
					String data = resultSet.getString("data_");

					_updateLayoutPageTemplateStructureRels(
						groupId, companyId, userId, userName, createDate,
						layoutPageTemplateStructureId, data);
				}
			}
		}
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			LayoutPageTemplateStructureUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final long _SEGMENTS_EXPERIENCE_ID_DEFAULT = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureUpgradeProcess.class);

}