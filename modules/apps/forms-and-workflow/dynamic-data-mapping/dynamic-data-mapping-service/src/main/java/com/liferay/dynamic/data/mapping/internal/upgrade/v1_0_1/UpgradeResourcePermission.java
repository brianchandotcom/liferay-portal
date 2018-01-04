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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_1;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	public UpgradeResourcePermission(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateResourcePermissions(DDMStructure.class.getName());

		updateResourcePermissions(DDMTemplate.class.getName());
	}

	protected String getNewCompositeModelName(String ddmModelClassName) {
		return _resourceActions.getCompositeModelName(
			ddmModelClassName, _CLASS_NAME);
	}

	protected String getOldCompositeModelName(String ddmModelClassName) {
		return _CLASS_NAME + StringPool.DASH + ddmModelClassName;
	}

	protected void updateResourcePermissions(String ddmModelClassName)
		throws Exception {

		String newCompositeModelName = getNewCompositeModelName(
			ddmModelClassName);
		String oldCompositeModelName = getOldCompositeModelName(
			ddmModelClassName);

		try (PreparedStatement ps = connection.prepareStatement(
				"update ResourcePermission set name = ? where name = ?");
			PreparedStatement ps1 = connection.prepareStatement(
				"update ResourcePermission set primKey = ? where primKey = " +
					"?")) {

			ps.setString(1, newCompositeModelName);
			ps.setString(2, oldCompositeModelName);

			ps.executeUpdate();

			ps1.setString(1, newCompositeModelName);
			ps1.setString(2, oldCompositeModelName);

			ps1.executeUpdate();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private final ResourceActions _resourceActions;

}