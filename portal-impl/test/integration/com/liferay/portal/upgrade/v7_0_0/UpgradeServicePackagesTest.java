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
public class UpgradeServicePackagesTest extends UpgradeServicePackages {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getUpgradeOptimizedConnection();

		runSQL("insert into Counter values('" + _OLD_CLASS_NAME + "', 10)");

		long classNameId = CounterLocalServiceUtil.increment(
			ClassName.class.getName());

		runSQL(
			"insert into ClassName_ values(0, " + classNameId +
				", 'PREFIX_" + _OLD_CLASS_NAME + "')");

		long resourceBlockId = CounterLocalServiceUtil.increment(
			ResourceBlock.class.getName());

		runSQL(
			"insert into ResourceBlock values(0, " + resourceBlockId +
				", " + TestPropsValues.getCompanyId() + ", " +
					TestPropsValues.getGroupId() + ", '" + _OLD_CLASS_NAME +
						"_POSTFIX', 'HASH', 1)");

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		runSQL(
			"insert into ResourcePermission values(0, " +
				resourcePermissionId + ", " + TestPropsValues.getCompanyId() +
					", 'PREFIX_" + _OLD_CLASS_NAME + "_POSTFIX', " +
						ResourceConstants.SCOPE_INDIVIDUAL +
							", 'PRIM_KEY', 2, 3, 4, 5, [$TRUE$])");
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

		StringBundler oldNameSB = new StringBundler(9);

		oldNameSB.append("select ");
		oldNameSB.append(columnName);
		oldNameSB.append(" from ");
		oldNameSB.append(tableName);
		oldNameSB.append(" where ");
		oldNameSB.append(columnName);
		oldNameSB.append(" like '%");
		oldNameSB.append(_OLD_CLASS_NAME);
		oldNameSB.append("%'");

		String oldName = null;

		try (PreparedStatement ps = connection.prepareStatement(
				oldNameSB.toString());
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(
				tableName + "does not have a row whose " + columnName +
					"'s value contains " + _OLD_CLASS_NAME,
				rs.next());

			oldName = rs.getString(columnName);
		}

		upgradeTable(tableName, columnName);

		String newName = StringUtil.replace(
			oldName, _OLD_CLASS_NAME, _NEW_CLASS_NAME);

		StringBundler newNameSB = new StringBundler(9);

		newNameSB.append("select ");
		newNameSB.append(columnName);
		newNameSB.append(" from ");
		newNameSB.append(tableName);
		newNameSB.append(" where ");
		newNameSB.append(columnName);
		newNameSB.append(" = '");
		newNameSB.append(newName);
		newNameSB.append("'");

		try (PreparedStatement ps = connection.prepareStatement(
				newNameSB.toString());
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(
				tableName + "does not have a row whose " + columnName +
					"'s value is " + newName,
				rs.next());
		}
	}

	@Override
	protected String[][] getClassNames() {
		return new String[][] {{_OLD_CLASS_NAME, _NEW_CLASS_NAME}};
	}

	private static final String _NEW_CLASS_NAME =
		"com.liferay.class.path.kernel.Test";

	private static final String _OLD_CLASS_NAME =
		"com.liferay.portlet.classpath.Test";

}