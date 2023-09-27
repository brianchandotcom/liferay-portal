/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v3_0_0;

import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Pavel Savinov
 */
public class JournalArticleImageUpgradeProcess extends UpgradeProcess {

	public JournalArticleImageUpgradeProcess(
		ImageLocalService imageLocalService) {

		_imageLocalService = imageLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("JournalArticleImage")) {
			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet resultSet1 = statement.executeQuery(
				"select articleImageId from JournalArticleImage")) {

			while (resultSet1.next()) {
				long articleImageId = resultSet1.getLong(1);

				_imageLocalService.deleteImage(articleImageId);
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("JournalArticleImage")
		};
	}

	private final ImageLocalService _imageLocalService;

}