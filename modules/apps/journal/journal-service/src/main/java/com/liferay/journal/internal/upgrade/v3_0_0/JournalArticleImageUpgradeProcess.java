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