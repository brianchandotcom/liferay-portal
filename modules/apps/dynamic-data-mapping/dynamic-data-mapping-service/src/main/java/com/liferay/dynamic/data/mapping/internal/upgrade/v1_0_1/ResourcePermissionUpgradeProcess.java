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
public class ResourcePermissionUpgradeProcess extends UpgradeProcess {

	public ResourcePermissionUpgradeProcess(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateResourcePermissions(DDMStructure.class.getName());

		_updateResourcePermissions(DDMTemplate.class.getName());
	}

	private String _getNewCompositeModelName(String ddmModelClassName) {
		return _resourceActions.getCompositeModelName(
			ddmModelClassName, _CLASS_NAME);
	}

	private String _getOldCompositeModelName(String ddmModelClassName) {
		return _CLASS_NAME + StringPool.DASH + ddmModelClassName;
	}

	private void _updateResourcePermissions(String ddmModelClassName)
		throws Exception {

		String newCompositeModelName = _getNewCompositeModelName(
			ddmModelClassName);
		String oldCompositeModelName = _getOldCompositeModelName(
			ddmModelClassName);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"update ResourcePermission set name = ? where name = ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"update ResourcePermission set primKey = ? where primKey = " +
					"?")) {

			preparedStatement1.setString(1, newCompositeModelName);
			preparedStatement1.setString(2, oldCompositeModelName);

			preparedStatement1.executeUpdate();

			preparedStatement2.setString(1, newCompositeModelName);
			preparedStatement2.setString(2, oldCompositeModelName);

			preparedStatement2.executeUpdate();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private final ResourceActions _resourceActions;

}