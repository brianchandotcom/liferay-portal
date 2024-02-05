/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.rankings.index.Ranking;

import java.sql.PreparedStatement;

/**
 * @author Almir Ferreira
 */
public class RenameRankingUpgradeProcess extends UpgradeProcess {

	public RenameRankingUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("JSONStorageEntry")) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update JSONStorageEntry set classNameId = ? where " +
					"classNameId = ?")) {

			ClassName className = _classNameLocalService.fetchClassName(
				"com.liferay.portal.search.tuning.rankings.web.internal." +
					"index.Ranking");

			if (className != null) {
				_classNameLocalService.deleteClassName(className);

				ClassName newClassName = _classNameLocalService.getClassName(
					Ranking.class.getName());

				preparedStatement.setLong(1, newClassName.getClassNameId());

				preparedStatement.setLong(2, className.getClassNameId());

				preparedStatement.executeUpdate();
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}