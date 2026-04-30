/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v3_5_2;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Anthony Chu
 */
public class CETConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from Configuration_ where configurationId like " +
					"'com.liferay.client.extension.type.configuration." +
						"CETConfiguration~%'")) {

			int deletedCount = preparedStatement.executeUpdate();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleted " + deletedCount +
						" persisted CET configurations");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CETConfigurationUpgradeProcess.class);

}