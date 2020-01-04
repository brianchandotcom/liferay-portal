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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeCompanyIdPrimaryKey;
import com.liferay.portal.kernel.upgrade.UpgradePrimaryKey;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeCompanyIdPrimaryKeyTest
	extends BaseUpgradePrimaryKeyTestCase {

	@Test
	public void testAddColumnAsPrimaryKeyAfterCompanyId() throws Exception {
		UpgradeCompanyIdPrimaryKey upgradePrimaryKeyCompanyId =
			new UpgradeCompanyIdPrimaryKey(getTableName());

		upgradePrimaryKeyCompanyId.upgrade();

		UpgradePrimaryKey upgradePrimaryKey = new UpgradePrimaryKey(
			"longColumn LONG not null", getTableName());

		upgradePrimaryKey.upgrade();

		_validateCompanyIdPrimaryKey();
	}

	@Test
	public void testAddCompanyIdAsPrimaryKey() throws Exception {
		db.runSQL("alter table " + getTableName() + " drop column companyId");

		UpgradeCompanyIdPrimaryKey upgradePrimaryKeyCompanyId =
			new UpgradeCompanyIdPrimaryKey(getTableName());

		upgradePrimaryKeyCompanyId.upgrade();

		_validateCompanyIdPrimaryKey();
	}

	@Test
	public void testAddExistingCompanyIdAsPrimaryKey() throws Exception {
		UpgradeCompanyIdPrimaryKey upgradePrimaryKeyCompanyId =
			new UpgradeCompanyIdPrimaryKey(getTableName());

		upgradePrimaryKeyCompanyId.upgrade();

		_validateCompanyIdPrimaryKey();
	}

	@Override
	protected String getSqlCreateTable() {
		return StringBundler.concat(
			"create table ", getTableName(), " (id LONG not null primary key, ",
			"companyId LONG null)");
	}

	@Override
	protected String getTableName() {
		return "UpgradeCompanyIdPrimaryKeyTest";
	}

	private void _validateCompanyIdPrimaryKey() throws Exception {
		List<String> primaryKeyColumnNames = getPrimaryKeyColumnNames();

		Assert.assertTrue(
			"CompanyId must be part of the primary key",
			primaryKeyColumnNames.contains(
				dbInspector.normalizeName("companyId")));

		String lastPrimaryKeyColumn = primaryKeyColumnNames.get(
			primaryKeyColumnNames.size() - 1);

		Assert.assertTrue(
			"CompanyId must be always the last column in the primary key",
			lastPrimaryKeyColumn.equals("companyId"));
	}

}