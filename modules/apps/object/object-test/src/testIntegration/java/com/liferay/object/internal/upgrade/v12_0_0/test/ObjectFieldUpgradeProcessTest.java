/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v12_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.Connection;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class ObjectFieldUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_db = DBManagerUtil.getDB();
	}

	@Before
	public void setUp() throws Exception {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false, Collections.emptyList(), new ServiceContext());

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				new MultiselectPicklistObjectFieldBuilder(
				).externalReferenceCode(
					_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_1
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					listTypeDefinition.getListTypeDefinitionId()
				).name(
					"a" + RandomTestUtil.randomString()
				).build(),
				new MultiselectPicklistObjectFieldBuilder(
				).externalReferenceCode(
					_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_2
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).listTypeDefinitionId(
					listTypeDefinition.getListTypeDefinitionId()
				).localized(
					true
				).name(
					"a" + RandomTestUtil.randomString()
				).build()));

		ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			new MultiselectPicklistObjectFieldBuilder(
			).externalReferenceCode(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_3
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).listTypeDefinitionId(
				listTypeDefinition.getListTypeDefinitionId()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());
	}

	@Test
	public void testUpgrade() throws Exception {
		_updateObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_1,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName());
		_updateObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_2,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName() + "_l");
		_updateObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_3,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName() + "_x");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		_assertObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_1,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName());
		_assertObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_2,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName() + "_l");
		_assertObjectFieldDBType(
			_objectFieldLocalService.getObjectField(
				_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_3,
				_objectDefinition.getObjectDefinitionId()),
			_objectDefinition.getDBTableName() + "_x");
	}

	private void _assertObjectFieldDBType(
			ObjectField objectField, String dbTableName)
		throws Exception {

		Assert.assertEquals(
			ObjectFieldConstants.DB_TYPE_CLOB, objectField.getDBType());

		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			Assert.assertTrue(
				dbInspector.hasColumnType(
					dbTableName, objectField.getDBColumnName(), "TEXT null"));
		}
	}

	private void _updateObjectFieldDBType(
			ObjectField objectField, String dbTableName)
		throws Exception {

		_db.runSQL(
			StringBundler.concat(
				"update ObjectField set dbType = '",
				ObjectFieldConstants.DB_TYPE_STRING, "' where objectFieldId = ",
				objectField.getObjectFieldId()));

		_db.runSQLTemplate(
			StringBundler.concat(
				"alter_column_type ", dbTableName, " ",
				objectField.getDBColumnName(), " VARCHAR(5000) null"),
			true);
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v12_0_0.ObjectFieldUpgradeProcess";

	private static final String _OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_1 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_2 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_EXTERNAL_REFERENCE_CODE_3 =
		RandomTestUtil.randomString();

	private static DB _db;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}