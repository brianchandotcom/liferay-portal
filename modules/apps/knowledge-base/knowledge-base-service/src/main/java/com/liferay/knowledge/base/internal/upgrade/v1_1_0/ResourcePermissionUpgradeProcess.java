/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Peter Shin
 */
public class ResourcePermissionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (_hasResourcePermission("com.liferay.knowledgebase.model.Article")) {
			_updateKBArticleResourcePermissions();
		}

		if (_hasResourcePermission(
				"com.liferay.knowledgebase.model.Template")) {

			_updateKBTemplateResourcePermissions();
		}
	}

	private boolean _hasResourcePermission(String name) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from ResourcePermission where name = ?")) {

			preparedStatement.setString(1, name);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private void _updateKBArticleResourcePermissions() throws Exception {
		runSQL(
			"update ResourcePermission set name = " +
				"'com.liferay.knowledgebase.model.KBArticle' where name = " +
					"'com.liferay.knowledgebase.model.Article'");
	}

	private void _updateKBTemplateResourcePermissions() throws Exception {
		runSQL(
			"update ResourcePermission set name = " +
				"'com.liferay.knowledgebase.model.KBTemplate' where name = " +
					"'com.liferay.knowledgebase.model.Template'");
	}

}