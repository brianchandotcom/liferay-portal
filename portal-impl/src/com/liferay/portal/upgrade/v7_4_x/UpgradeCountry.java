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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Albert Lee
 */
public class UpgradeCountry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQLTemplate("update-7.3.0-7.4.0-country.sql", false);

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select countryId from Country where uuid_ is null");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update Country set uuid_ = ? where countryId = " +
								"?"));
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, rs.getLong(1));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}

		long defaultCompanyId = 0;
		long defaultUserId = 0;

		String sql = StringBundler.concat(
			"select User_.companyId, User_.userId from User_ join Company on ",
			"User_.companyId = Company.companyId where User_.defaultUser = ",
			"[$TRUE$] and Company.webId = ",
			StringUtil.quote(PropsValues.COMPANY_DEFAULT_WEB_ID));

		try (PreparedStatement ps = connection.prepareStatement(
				SQLTransformer.transform(sql));
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				defaultCompanyId = rs.getLong(1);
				defaultUserId = rs.getLong(2);
			}
		}

		String defaultLanguageId = StringUtil.quote(
			UpgradeProcessUtil.getDefaultLanguageId(defaultCompanyId));

		runSQL(
			"update Country set defaultLanguageId = " + defaultLanguageId +
				" where defaultLanguageId is null");

		if (defaultCompanyId > 0) {
			runSQL(
				"update Country set companyId = " + defaultCompanyId +
					" where (companyId is null or companyId = 0)");
		}

		if (defaultUserId > 0) {
			runSQL(
				"update Country set userId = " + defaultUserId +
					" where (userId is null or userId = 0)");
		}

		runSQL(
			"update Country set billingAllowed = [$TRUE$] where " +
				"billingAllowed is null");
		runSQL(
			"update Country set groupFilterEnabled = [$FALSE$] where " +
				"groupFilterEnabled is null");
		runSQL(
			"update Country set shippingAllowed = [$TRUE$] where " +
				"shippingAllowed is null");
		runSQL(
			"update Country set subjectToVAT = [$FALSE$] where subjectToVAT " +
				"is null");
	}

}