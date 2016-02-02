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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class UpgradeKernelPackageTest extends UpgradeKernelPackage {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getUpgradeOptimizedConnection();

		runSQL("insert into Counter values('" + _OLD_CLASS_NAME + "', 10)");

		runSQL(
			"insert into ClassName_ values(0, " + increment(ClassName.class) +
				", 'PREFIX_" + _OLD_CLASS_NAME + "')");

		StringBundler sb = new StringBundler(8);

		sb.append("insert into ResourceBlock values(0, ");
		sb.append(increment(ResourceBlock.class));
		sb.append(", ");
		sb.append(TestPropsValues.getCompanyId());
		sb.append(", ");
		sb.append(TestPropsValues.getGroupId());
		sb.append(", '");
		sb.append(_OLD_CLASS_NAME);
		sb.append("_POSTFIX', 'HASH', 1)");

		runSQL(sb.toString());

		sb = new StringBundler(9);

		sb.append("insert into ResourcePermission values(0, ");
		sb.append(increment(ResourcePermission.class));
		sb.append(", ");
		sb.append(TestPropsValues.getCompanyId());
		sb.append(", 'PREFIX_");
		sb.append(_OLD_CLASS_NAME);
		sb.append("_POSTFIX', ");
		sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
		sb.append(", 'PRIM_KEY', 2, 3, 4, 5, [$TRUE$])");

		runSQL(sb.toString());
	}

	@After
	public void tearDown() throws Exception {
		for (String className : getClassNames()[0]) {
			runSQL("delete from Counter where name like '%" + className + "%'");

			runSQL(
				"delete from ClassName_ where value like '%" + className +
					"%'");

			runSQL(
				"delete from ResourceBlock where name like '%" + className +
					"%'");

			runSQL(
				"delete from ResourcePermission where name like '%" +
					className + "%'");
		}

		connection.close();
	}

	@Test
	public void testUpgradeClassName() throws Exception {
		assertUpgradeSuccessful("ClassName_", "value");
	}

	@Test
	public void testUpgradeCounter() throws Exception {
		assertUpgradeSuccessful("Counter", "name");
	}

	@Test
	public void testUpgradeResourceBlock() throws Exception {
		assertUpgradeSuccessful("ResourceBlock", "name");
	}

	@Test
	public void testUpgradeResourcePermission() throws Exception {
		assertUpgradeSuccessful("ResourcePermission", "name");
	}

	protected void assertUpgradeSuccessful(String tableName, String columnName)
		throws Exception {

		StringBundler oldSelectSB = new StringBundler(9);

		oldSelectSB.append("select ");
		oldSelectSB.append(columnName);
		oldSelectSB.append(" from ");
		oldSelectSB.append(tableName);
		oldSelectSB.append(" where ");
		oldSelectSB.append(columnName);
		oldSelectSB.append(" like '%");
		oldSelectSB.append(_OLD_CLASS_NAME);
		oldSelectSB.append("%'");

		String oldValue = null;

		try (PreparedStatement ps = connection.prepareStatement(
				oldSelectSB.toString());
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(
				"Table " + tableName + " and column " + columnName +
					" does not contain value " + _OLD_CLASS_NAME,
				rs.next());

			oldValue = rs.getString(columnName);
		}

		upgradeTable(tableName, columnName);

		String newValue = StringUtil.replace(
			oldValue, _OLD_CLASS_NAME, _NEW_CLASS_NAME);

		StringBundler newSelectSB = new StringBundler(9);

		newSelectSB.append("select ");
		newSelectSB.append(columnName);
		newSelectSB.append(" from ");
		newSelectSB.append(tableName);
		newSelectSB.append(" where ");
		newSelectSB.append(columnName);
		newSelectSB.append(" = '");
		newSelectSB.append(newValue);
		newSelectSB.append("'");

		try (PreparedStatement ps = connection.prepareStatement(
				newSelectSB.toString());
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(
				"Table " + tableName + " and column " + columnName +
					" does not contain value " + newValue,
				rs.next());
		}
	}

	@Override
	protected String[][] getClassNames() {
		return new String[][] {{_OLD_CLASS_NAME, _NEW_CLASS_NAME}};
	}

	protected long increment(Class<?> clazz) throws Exception {
		return CounterLocalServiceUtil.increment(clazz.getName());
	}

	private static final String _NEW_CLASS_NAME =
		"com.liferay.class.path.kernel.Test";

	private static final String _OLD_CLASS_NAME =
		"com.liferay.portlet.classpath.Test";

}