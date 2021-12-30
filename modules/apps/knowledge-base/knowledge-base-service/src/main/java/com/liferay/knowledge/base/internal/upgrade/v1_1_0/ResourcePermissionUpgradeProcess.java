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