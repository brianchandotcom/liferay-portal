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
public class ResourceActionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (_hasResourceAction("com.liferay.knowledgebase.model.Article")) {
			_updateKBArticleResourceActions();
		}

		if (_hasResourceAction("com.liferay.knowledgebase.model.Template")) {
			_updateKBTemplateResourceActions();
		}
	}

	private boolean _hasResourceAction(String name) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from ResourceAction where name = ?")) {

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

	private void _updateKBArticleResourceActions() throws Exception {
		runSQL(
			"delete from ResourceAction where name = " +
				"'com.liferay.knowledgebase.model.KBArticle'");

		runSQL(
			"update ResourceAction set name = " +
				"'com.liferay.knowledgebase.model.KBArticle' where name = " +
					"'com.liferay.knowledgebase.model.Article'");

		runSQL(
			"update ResourceAction set actionId = 'MOVE_KB_ARTICLE' where " +
				"name = 'com.liferay.knowledgebase.model.KBArticle' and " +
					"actionId = 'MOVE'");
	}

	private void _updateKBTemplateResourceActions() throws Exception {
		runSQL(
			"delete from ResourceAction where name = " +
				"'com.liferay.knowledgebase.model.KBTemplate'");

		runSQL(
			"update ResourceAction set name = " +
				"'com.liferay.knowledgebase.model.KBTemplate' where name = " +
					"'com.liferay.knowledgebase.model.Template'");
	}

}