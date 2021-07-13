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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.upgrade.v7_4_x.util.CompanyTable;
import com.liferay.portal.upgrade.v7_4_x.util.ContactTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Drew Brokke
 */
public class UpgradeAccount extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addColumnToCompanyTable("userId", "LONG");
		_addColumnToCompanyTable("userName", "VARCHAR(75) null");
		_addColumnToCompanyTable("createDate", "DATE null");
		_addColumnToCompanyTable("modifiedDate", "DATE null");
		_addColumnToCompanyTable("name", "VARCHAR(75) null");
		_addColumnToCompanyTable("legalName", "VARCHAR(75) null");
		_addColumnToCompanyTable("legalId", "VARCHAR(75) null");
		_addColumnToCompanyTable("legalType", "VARCHAR(75) null");
		_addColumnToCompanyTable("sicCode", "VARCHAR(75) null");
		_addColumnToCompanyTable("tickerSymbol", "VARCHAR(75) null");
		_addColumnToCompanyTable("industry", "VARCHAR(75) null");
		_addColumnToCompanyTable("type_", "VARCHAR(75) null");
		_addColumnToCompanyTable("size_", "VARCHAR(75) null");

		if (hasColumn("Company", "accountId")) {
			alter(CompanyTable.class, new AlterTableDropColumn("accountId"));
		}

		if (hasColumn("Contact", "accountId")) {
			alter(ContactTable.class, new AlterTableDropColumn("accountId"));
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ListType set type_ = ? where type_ = ?")) {

			for (String typeName :
					new String[] {
						"address", "emailAddress", "phone", "website"
					}) {

				preparedStatement.setString(
					1, Company.class.getName() + "." + typeName);
				preparedStatement.setString(
					2, _CLASS_NAME_ACCOUNT + "." + typeName);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}

		long accountClassNameId = PortalUtil.getClassNameId(
			_CLASS_NAME_ACCOUNT);
		long companyClassNameId = PortalUtil.getClassNameId(Company.class);

		String updateCompanySQL = StringBundler.concat(
			"update Company set userId = ?, userName = ?, createDate = ?, ",
			"modifiedDate = ?, name = ?, legalName = ?, legalId = ?, ",
			"legalType = ?, sicCode = ?, tickerSymbol = ?, industry = ?, ",
			"type_ = ?, size_ = ? where companyId = ?");

		try (Statement selectAccountsStatement = connection.createStatement();
			PreparedStatement updateCompanyPreparedStatement =
				connection.prepareStatement(updateCompanySQL);
			PreparedStatement updateAddressPreparedStatement =
				connection.prepareStatement(
					_getUpdateClassNameIdClassPKSQL("Address"));
			PreparedStatement updateEmailAddressPreparedStatement =
				connection.prepareStatement(
					_getUpdateClassNameIdClassPKSQL("EmailAddress"));
			PreparedStatement updatePhonePreparedStatement =
				connection.prepareStatement(
					_getUpdateClassNameIdClassPKSQL("Phone"));
			PreparedStatement updateWebsitePreparedStatement =
				connection.prepareStatement(
					_getUpdateClassNameIdClassPKSQL("Website"))) {

			try (ResultSet resultSet = selectAccountsStatement.executeQuery(
					"select * from Account_")) {

				while (resultSet.next()) {
					updateCompanyPreparedStatement.setLong(
						1, resultSet.getLong("userId"));
					updateCompanyPreparedStatement.setString(
						2, resultSet.getString("userName"));
					updateCompanyPreparedStatement.setDate(
						3, resultSet.getDate("createDate"));
					updateCompanyPreparedStatement.setDate(
						4, resultSet.getDate("modifiedDate"));
					updateCompanyPreparedStatement.setString(
						5, resultSet.getString("name"));
					updateCompanyPreparedStatement.setString(
						6, resultSet.getString("legalName"));
					updateCompanyPreparedStatement.setString(
						7, resultSet.getString("legalId"));
					updateCompanyPreparedStatement.setString(
						8, resultSet.getString("legalType"));
					updateCompanyPreparedStatement.setString(
						9, resultSet.getString("sicCode"));
					updateCompanyPreparedStatement.setString(
						10, resultSet.getString("tickerSymbol"));
					updateCompanyPreparedStatement.setString(
						11, resultSet.getString("industry"));
					updateCompanyPreparedStatement.setString(
						12, resultSet.getString("type_"));
					updateCompanyPreparedStatement.setString(
						13, resultSet.getString("size_"));
					updateCompanyPreparedStatement.setLong(
						14, resultSet.getLong("companyId"));

					updateCompanyPreparedStatement.addBatch();

					for (PreparedStatement preparedStatement :
							new PreparedStatement[] {
								updateAddressPreparedStatement,
								updateEmailAddressPreparedStatement,
								updatePhonePreparedStatement,
								updateWebsitePreparedStatement
							}) {

						preparedStatement.setLong(1, companyClassNameId);
						preparedStatement.setLong(
							2, resultSet.getLong("companyId"));
						preparedStatement.setLong(3, accountClassNameId);
						preparedStatement.setLong(
							4, resultSet.getLong("accountId"));

						preparedStatement.addBatch();
					}
				}
			}

			updateCompanyPreparedStatement.executeBatch();

			updateAddressPreparedStatement.executeBatch();
			updateEmailAddressPreparedStatement.executeBatch();
			updatePhonePreparedStatement.executeBatch();
			updateWebsitePreparedStatement.executeBatch();
		}

		runSQL(
			"delete from ClassName_ where value = '" + _CLASS_NAME_ACCOUNT +
				"'");

		runSQL("drop table Account_");
	}

	private void _addColumnToCompanyTable(String columnName, String columnType)
		throws Exception {

		if (!hasColumn("Company", columnName)) {
			alter(
				CompanyTable.class,
				new AlterTableAddColumn(columnName, columnType));
		}
	}

	private String _getUpdateClassNameIdClassPKSQL(String tableName) {
		return StringBundler.concat(
			"update ", tableName, " set classNameId = ?, classPK = ? where ",
			"classNameId = ? and classPK = ?");
	}

	private static final String _CLASS_NAME_ACCOUNT =
		"com.liferay.portal.kernel.model.Account";

}