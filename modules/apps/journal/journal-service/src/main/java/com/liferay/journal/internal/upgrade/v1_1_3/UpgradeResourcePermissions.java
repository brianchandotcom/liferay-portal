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
public class UpgradeResourcePermissions extends UpgradeProcess {

	public UpgradeResourcePermissions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String modelResource = _resourceActions.getCompositeModelName(
			DDMStructure.class.getName(), JournalArticle.class.getName());

		updateResourcePermissions(
			"com.liferay.journal.model.JournalStructure", modelResource);

		modelResource = _resourceActions.getCompositeModelName(
			DDMTemplate.class.getName(), JournalArticle.class.getName());

		updateResourcePermissions(
			"com.liferay.journal.model.JournalTemplate", modelResource);
	}

	protected void updateResourcePermissions(
		String oldClassName, String newClassName) {

		try (PreparedStatement ps = connection.prepareStatement(
				"update ResourcePermission set name = ? where name = ?")) {

			ps.setString(1, newClassName);
			ps.setString(2, oldClassName);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle, sqle);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeResourcePermissions.class);

	private final ResourceActions _resourceActions;

}