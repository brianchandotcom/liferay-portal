/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marta Medio
 */
public class MFAFIDO2CredentialUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		dropIndexes("MFAFIDO2CredentialEntry", "credentialKey");

		alterColumnType(
			"MFAFIDO2CredentialEntry", "credentialKey", "TEXT null");

		if (!hasColumn("MFAFIDO2CredentialEntry", "credentialKeyHash")) {
			alterTableAddColumn(
				"MFAFIDO2CredentialEntry", "credentialKeyHash", "LONG");

			_updateCredentialKeys();
		}
	}

	private void _updateCredentialKey(
			String credentialKey, int credentialKeyHash)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update MFAFIDO2CredentialEntry set credentialKeyHash = ? " +
					"where credentialKey = ?")) {

			preparedStatement.setLong(1, credentialKeyHash);
			preparedStatement.setString(2, credentialKey);

			preparedStatement.execute();
		}
	}

	private void _updateCredentialKeys() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select MFAFIDO2CredentialEntry.credentialKey from " +
					"MFAFIDO2CredentialEntry");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String credentialKey = resultSet.getString("credentialKey");

				if (Validator.isNull(credentialKey)) {
					continue;
				}

				_updateCredentialKey(credentialKey, credentialKey.hashCode());
			}
		}
	}

}