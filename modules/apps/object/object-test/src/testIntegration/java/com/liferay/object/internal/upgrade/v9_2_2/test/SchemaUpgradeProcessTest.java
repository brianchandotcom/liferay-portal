/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class SchemaUpgradeProcessTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();
	}

	@Before
	public void setUp() throws Exception {
		Company company = CompanyLocalServiceUtil.addCompany(
			null, _VIRTUAL_HOSTNAME, _VIRTUAL_HOSTNAME, _VIRTUAL_HOSTNAME, 0,
			true, true, null, null, null, null, null, null);

		_partitionName = DBPartitionUtil.getPartitionName(
			company.getCompanyId());
	}

	@Test
	public void testUpgrade() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				TestPropsValues.getCompanyId(), User.class.getSimpleName());

		String dbTableName = StringBundler.concat(
			objectDefinition.getDBTableName(), "x_",
			PortalInstancePool.getDefaultCompanyId());

		_createView(dbTableName);

		objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"a" + RandomTestUtil.randomString()
				).build()));

		_createView(objectDefinition.getDBTableName());
		_createView(objectDefinition.getExtensionDBTableName());

		List<String> viewNames = _getViewNames();

		Assert.assertTrue(
			viewNames.contains(StringUtil.toLowerCase(dbTableName)));
		Assert.assertTrue(
			viewNames.contains(
				StringUtil.toLowerCase(objectDefinition.getDBTableName())));
		Assert.assertTrue(
			viewNames.contains(
				StringUtil.toLowerCase(
					objectDefinition.getExtensionDBTableName())));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		viewNames = _getViewNames();

		Assert.assertFalse(
			viewNames.contains(StringUtil.toLowerCase(dbTableName)));
		Assert.assertFalse(
			viewNames.contains(
				StringUtil.toLowerCase(objectDefinition.getDBTableName())));
		Assert.assertFalse(
			viewNames.contains(
				StringUtil.toLowerCase(
					objectDefinition.getExtensionDBTableName())));
	}

	private void _createView(String tableName) throws Exception {
		String defaultPartitionName = DBPartitionUtil.getPartitionName(
			PortalInstancePool.getDefaultCompanyId());

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				StringBundler.concat(
					"create or replace view ", _partitionName,
					StringPool.PERIOD, tableName, " as select * from ",
					defaultPartitionName, StringPool.PERIOD, tableName));
		}
	}

	private List<String> _getViewNames() throws Exception {
		List<String> viewNames = new ArrayList<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		ResultSet resultSet = databaseMetaData.getTables(
			_partitionName, null, null, new String[] {"VIEW"});

		while (resultSet.next()) {
			viewNames.add(
				StringUtil.toLowerCase(resultSet.getString("TABLE_NAME")));
		}

		return viewNames;
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v9_2_2.SchemaUpgradeProcess";

	private static final String _VIRTUAL_HOSTNAME =
		RandomTestUtil.randomString() + ".localtest.me";

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private String _partitionName;

}