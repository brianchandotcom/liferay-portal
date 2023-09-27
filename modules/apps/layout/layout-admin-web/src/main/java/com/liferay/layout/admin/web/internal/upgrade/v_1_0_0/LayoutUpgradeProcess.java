/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateLayouts();
	}

	private void _updateLayout(long plid, String typeSettings)
		throws Exception {

		if (Validator.isNull(typeSettings)) {
			return;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).load(
				typeSettings
			).build();

		typeSettingsUnicodeProperties.setProperty(
			"embeddedLayoutURL", typeSettingsUnicodeProperties.remove("url"));

		_updateTypeSettings(plid, typeSettingsUnicodeProperties.toString());
	}

	private void _updateLayouts() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select plid, typeSettings from Layout where type_ = ?")) {

			preparedStatement.setString(1, "embedded");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long plid = resultSet.getLong("plid");
					String typeSettings = resultSet.getString("typeSettings");

					_updateLayout(plid, typeSettings);
				}
			}
		}
	}

	private void _updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Layout set typeSettings = ? where plid = ?")) {

			preparedStatement.setString(1, typeSettings);
			preparedStatement.setLong(2, plid);

			preparedStatement.executeUpdate();
		}
	}

}