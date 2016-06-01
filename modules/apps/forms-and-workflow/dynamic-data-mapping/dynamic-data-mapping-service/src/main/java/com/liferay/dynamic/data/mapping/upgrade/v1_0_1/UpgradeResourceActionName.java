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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_1;

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class UpgradeResourceActionName extends UpgradeProcess {

	public UpgradeResourceActionName(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String oldName =
			_JOURNAL_ARTICLE_CLASS_NAME + StringPool.DASH +
				DDMStructure.class.getName();

		String newName = _resourceActions.getCompositeModelName(
			DDMStructure.class.getName(), _JOURNAL_ARTICLE_CLASS_NAME);

		updateResourceActionName(oldName, newName);

		oldName =
			_JOURNAL_ARTICLE_CLASS_NAME + StringPool.DASH +
				DDMTemplate.class.getName();

		newName = _resourceActions.getCompositeModelName(
			DDMTemplate.class.getName(), _JOURNAL_ARTICLE_CLASS_NAME);

		updateResourceActionName(oldName, newName);
	}

	protected void updateResourceActionName(String oldName, String newName)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update ResourceAction set name = ? where name = ?")) {

			ps.setString(1, newName);
			ps.setString(2, oldName);

			ps.executeUpdate();
		}
	}

	private static final String _JOURNAL_ARTICLE_CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private final ResourceActions _resourceActions;

}