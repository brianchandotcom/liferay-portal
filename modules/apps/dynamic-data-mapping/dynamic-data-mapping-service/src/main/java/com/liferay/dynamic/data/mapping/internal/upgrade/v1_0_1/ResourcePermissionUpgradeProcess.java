/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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