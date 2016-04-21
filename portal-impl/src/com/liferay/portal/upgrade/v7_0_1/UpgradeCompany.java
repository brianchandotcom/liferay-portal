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

package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.util.Encryptor;

import java.security.Key;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Mika Koivisto
 */
public class UpgradeCompany extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateCompanyKey();
	}

	protected void updateCompanyKey() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select companyId, key_ from Company");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String companyId = rs.getString("companyId");
				String keyString = rs.getString("key_");

				Key key = (Key)Base64.stringToObject(keyString);

				keyString = Encryptor.serializeKey(key);

				runSQL(
					"update Company set key_ = '" + keyString +
						"' where companyId = " + companyId);
			}
		}
	}

}