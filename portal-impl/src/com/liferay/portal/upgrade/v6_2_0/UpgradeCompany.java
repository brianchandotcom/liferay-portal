/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.security.Key;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Tomas Polesovsky
 */
public class UpgradeCompany extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String keyAlgorithm = _KEY_ALGORITHM;

		if (keyAlgorithm.equals("DES")) {
			return;
		}

		upgradeKey();
	}

	protected void upgradeKey() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, key_ from Company");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				String key = resultSet.getString("key_");

				upgradeKey(companyId, key);
			}
		}
	}

	protected void upgradeKey(long companyId, String key) throws Exception {
		Key keyObj = null;

		if (Validator.isNotNull(key)) {
			keyObj = (Key)Base64.stringToObjectSilent(key);
		}

		if (keyObj != null) {
			String algorithm = keyObj.getAlgorithm();

			if (!algorithm.equals("DES")) {
				return;
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Company set key_ = ? where companyId = ?")) {

			preparedStatement.setString(
				1, Base64.objectToString(EncryptorUtil.generateKey()));
			preparedStatement.setLong(2, companyId);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _KEY_ALGORITHM = StringUtil.toUpperCase(
		GetterUtil.getString(
			PropsUtil.get(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM)));

}