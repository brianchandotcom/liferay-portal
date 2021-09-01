/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

		if (hasColumn("MFAFIDO2CredentialEntry", "credentialKey")) {
			alterColumnType(
				"MFAFIDO2CredentialEntry", "credentialKey", "TEXT null");
		}

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