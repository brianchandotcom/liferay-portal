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

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.upgrade.v7_4_x.util.CountryTable;
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

		long defaultCompanyId = 0;
		long defaultUserId = 0;

		String sql = StringBundler.concat(
			"select User_.companyId, User_.userId from User_ join Company on ",
			"User_.companyId = Company.companyId where User_.defaultUser = 1 ",
			"and Company.webId = ",
			StringUtil.quote(PropsValues.COMPANY_DEFAULT_WEB_ID));

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				defaultCompanyId = rs.getLong(1);
				defaultUserId = rs.getLong(2);
			}
		}

		if (!hasColumn("Country", "defaultLanguageId")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn(
					"defaultLanguageId", "VARCHAR(75) null"));
		}

		String defaultLanguageId = StringUtil.quote(
			UpgradeProcessUtil.getDefaultLanguageId(defaultCompanyId));

		runSQL(
			"update Country set defaultLanguageId = " + defaultLanguageId +
				" where defaultLanguageId is null");

		if (!hasColumn("Country", "companyId")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("companyId", "LONG"));
		}

		if (defaultCompanyId > 0) {
			runSQL(
				"update Country set companyId = " + defaultCompanyId +
					" where companyId is null");
		}

		if (!hasColumn("Country", "userId")) {
			alter(
				CountryTable.class, new AlterTableAddColumn("userId", "LONG"));
		}

		if (defaultUserId > 0) {
			runSQL(
				"update Country set userId = " + defaultUserId +
					" where userId is null");
		}

		if (!hasColumn("Country", "userName")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("userName", "VARCHAR(75) null"));
		}

		if (!hasColumn("Country", "createDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("Country", "modifiedDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		if (!hasColumn("Country", "billingAllowed")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("billingAllowed", "BOOLEAN"));
		}

		runSQL(
			"update Country set billingAllowed = 1 where billingAllowed is " +
				"null");

		if (!hasColumn("Country", "groupFilterEnabled")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("groupFilterEnabled", "BOOLEAN"));
		}

		runSQL(
			"update Country set groupFilterEnabled = 0 where " +
				"groupFilterEnabled is null");

		if (!hasColumn("Country", "position")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("position", "DOUBLE"));
		}

		if (!hasColumn("Country", "shippingAllowed")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("shippingAllowed", "BOOLEAN"));
		}

		runSQL(
			"update Country set shippingAllowed = 1 where shippingAllowed is " +
				"null");

		if (!hasColumn("Country", "subjectToVAT")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("subjectToVAT", "BOOLEAN"));
		}

		runSQL(
			"update Country set subjectToVAT = 0 where subjectToVAT is null");

		if (!hasColumn("Country", "lastPublishDate")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE null"));
		}

		if (!hasColumn("Country", "uuid_")) {
			alter(
				CountryTable.class,
				new AlterTableAddColumn("uuid_", "VARCHAR(75) null"));
		}

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
	}

}