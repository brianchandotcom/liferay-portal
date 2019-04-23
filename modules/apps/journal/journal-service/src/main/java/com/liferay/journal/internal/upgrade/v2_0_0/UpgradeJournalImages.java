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

package com.liferay.journal.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Pavel Savinov
 */
public class UpgradeJournalImages extends UpgradeProcess {

	public UpgradeJournalImages(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("JournalArticleImage")) {
			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(
				"select articleImageId from JournalArticleImage")) {

			while (rs1.next()) {
				long articleImageId = rs1.getLong(1);

				_imageLocalService.deleteImage(articleImageId);
			}
		}

		runSQL(connection, "drop table JournalArticleImage");

		if (_log.isDebugEnabled()) {
			_log.debug("Deleted table JournalArticleImage");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeJournalImages.class);

	private final ImageLocalService _imageLocalService;

}