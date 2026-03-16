/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v6_1_6;

import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Gergely Szalay
 */
public class JournalFolderRestrictionTypeUpgradeProcess extends UpgradeProcess {

	public JournalFolderRestrictionTypeUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String sql = StringBundler.concat(
			"update JournalFolder set restrictionType = ? where ",
			"restrictionType = ? and groupId in (select groupId from Group_ ",
			"where classNameId = ?)");
		long classNameId = _classNameLocalService.getClassNameId(
			LayoutSetPrototype.class);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setInt(
				1, JournalFolderConstants.RESTRICTION_TYPE_INHERIT);
			preparedStatement.setInt(
				2, JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW);
			preparedStatement.setLong(3, classNameId);

			preparedStatement.executeUpdate();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}