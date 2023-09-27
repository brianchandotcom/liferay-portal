/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_1_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.test.BaseUpgradeDBColumnSizeTestCase;
import com.liferay.portal.upgrade.v7_1_x.UpgradeDB2;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UpgradeDB2Test extends BaseUpgradeDBColumnSizeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DB db = DBManagerUtil.getDB();

		Assume.assumeTrue(db.getDBType() == DBType.DB2);
	}

	@Override
	protected String getCreateTestTableSQL() {
		return "create table TestTable (testTableId int not null primary " +
			"key, testValue varchar(750) null)";
	}

	@Override
	protected int getInitialSize() {
		return 750;
	}

	@Override
	protected String getTypeName() {
		return "varchar";
	}

	@Override
	protected UpgradeDB2 getUpgradeProcess() {
		return new UpgradeDB2();
	}

}