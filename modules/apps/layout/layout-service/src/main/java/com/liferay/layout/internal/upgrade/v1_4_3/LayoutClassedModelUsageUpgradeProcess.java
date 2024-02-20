/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_3;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;

/**
 * @author Gergely Szalay
 */
public class LayoutClassedModelUsageUpgradeProcess extends UpgradeProcess {

	public LayoutClassedModelUsageUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_dlFileEntryClassNameId = classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());
		_fileEntryClassNameId = classNameLocalService.getClassNameId(
			FileEntry.class.getName());
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutClassedModelUsage set classNameId = ? " +
						"where classNameId = ?")) {

			preparedStatement.setLong(1, _fileEntryClassNameId);
			preparedStatement.setLong(2, _dlFileEntryClassNameId);
			preparedStatement.executeUpdate();
		}
	}

	private final long _dlFileEntryClassNameId;
	private final long _fileEntryClassNameId;

}