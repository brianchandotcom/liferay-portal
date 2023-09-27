/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v1_1_3;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Jürgen Kappler
 */
public class ResourcePermissionsUpgradeProcess extends UpgradeProcess {

	public ResourcePermissionsUpgradeProcess(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String modelResource = _resourceActions.getCompositeModelName(
			DDMStructure.class.getName(), JournalArticle.class.getName());

		_updateResourcePermissions(
			"com.liferay.journal.model.JournalStructure", modelResource);

		modelResource = _resourceActions.getCompositeModelName(
			DDMTemplate.class.getName(), JournalArticle.class.getName());

		_updateResourcePermissions(
			"com.liferay.journal.model.JournalTemplate", modelResource);
	}

	private void _updateResourcePermissions(
		String oldClassName, String newClassName) {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourcePermission set name = ? where name = ?")) {

			preparedStatement.setString(1, newClassName);
			preparedStatement.setString(2, oldClassName);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqlException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionsUpgradeProcess.class);

	private final ResourceActions _resourceActions;

}