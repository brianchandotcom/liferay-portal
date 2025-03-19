/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_2_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.rule.CompanyProviderClassTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Paulo Albuquerque
 */
@RunWith(Arquillian.class)
public class SchemaUpgradeProcessTest extends BaseDBPartitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"),
			new LiferayIntegrationTestRule() {
				{
					skipTestRule(CompanyProviderClassTestRule.INSTANCE);
				}
			},
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();
	}

	@After
	public void tearDown() throws Exception {
		DB db = DBManagerUtil.getDB();

		for (String viewName : _viewNames) {
			db.runSQL("drop view if exists " + viewName);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				PortalInstancePool.getDefaultCompanyId(),
				User.class.getSimpleName());

		String dbTableName = StringBundler.concat(
			objectDefinition.getDBTableName(), "x_",
			PortalInstancePool.getDefaultCompanyId());

		_createView(dbTableName);

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).name(
					"a" + RandomTestUtil.randomString()
				).build()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			objectDefinition.getUserId());

		_createView(_objectDefinition.getDBTableName());
		_createView(_objectDefinition.getExtensionDBTableName());

		List<String> viewNames = _getViewNames();

		Assert.assertTrue(
			viewNames.contains(StringUtil.toLowerCase(dbTableName)));
		Assert.assertTrue(
			viewNames.contains(
				StringUtil.toLowerCase(_objectDefinition.getDBTableName())));
		Assert.assertTrue(
			viewNames.contains(
				StringUtil.toLowerCase(
					_objectDefinition.getExtensionDBTableName())));

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		viewNames = _getViewNames();

		Assert.assertFalse(
			viewNames.contains(StringUtil.toLowerCase(dbTableName)));
		Assert.assertFalse(
			viewNames.contains(
				StringUtil.toLowerCase(_objectDefinition.getDBTableName())));
		Assert.assertFalse(
			viewNames.contains(
				StringUtil.toLowerCase(
					_objectDefinition.getExtensionDBTableName())));
	}

	private void _createView(String tableName) throws Exception {
		String partitionName = DBPartitionUtil.getPartitionName(
			TestPropsValues.getCompanyId());

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				StringBundler.concat(
					"create or replace view ", partitionName, StringPool.PERIOD,
					tableName, " as select * from ", tableName));
		}

		_viewNames.add(tableName);
	}

	private List<String> _getViewNames() throws Exception {
		List<String> viewNames = new ArrayList<>();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			ResultSet resultSet = databaseMetaData.getTables(
				dbInspector.getCatalog(), dbInspector.getSchema(), null,
				new String[] {"VIEW"});

			while (resultSet.next()) {
				viewNames.add(
					StringUtil.toLowerCase(resultSet.getString("TABLE_NAME")));
			}
		}

		return viewNames;
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v9_2_2.SchemaUpgradeProcess";

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final List<String> _viewNames = new ArrayList<>();

}