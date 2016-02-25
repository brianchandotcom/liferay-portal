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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Encryptor;

import java.security.Key;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Tomas Polesovsky
 */
public class UpgradeCompany extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String keyAlgorithm = Encryptor.KEY_ALGORITHM;

		if (keyAlgorithm.equals("DES")) {
			return;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select companyId, key_ from Company");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				String key = rs.getString("key_");

				upgradeKey(companyId, key);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
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

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"update Company set key_ = ? where companyId = ?");

			ps.setString(1, Base64.objectToString(Encryptor.generateKey()));
			ps.setLong(2, companyId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

}