/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v12_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Victor Kammerer
 */
@RunWith(Arquillian.class)
public class ObjectEntryPicklistDefaultValueUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {

		// Picklist object field

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				false, Collections.emptyList(), new ServiceContext());

		String listTypeEntryKey = RandomTestUtil.randomString();

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.addListTypeEntry(
				null, TestPropsValues.getUserId(),
				_listTypeDefinition.getListTypeDefinitionId(), listTypeEntryKey,
				LocalizedMapUtil.getLocalizedMap(listTypeEntryKey),
				_listTypeDefinition.isSystem());

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectField picklistObjectField1 = _addPicklistObjectField(
			listTypeEntry, RandomTestUtil.randomBoolean(), false);

		ObjectEntry objectEntry1 = _addObjectEntry(picklistObjectField1, null);

		// Picklist object field marked as state

		ObjectField picklistObjectField2 = _addPicklistObjectField(
			listTypeEntry, true, true);

		ObjectEntry objectEntry2 = _addObjectEntry(picklistObjectField2, null);
		ObjectEntry objectEntry3 = _addObjectEntry(picklistObjectField2, "");

		String value = RandomTestUtil.randomString();

		ObjectEntry objectEntry4 = _addObjectEntry(picklistObjectField2, value);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		_assertObjectEntryValue(
			StringPool.BLANK, objectEntry1.getObjectEntryId(),
			picklistObjectField1.getName());
		_assertObjectEntryValue(
			listTypeEntryKey, objectEntry2.getObjectEntryId(),
			picklistObjectField2.getName());
		_assertObjectEntryValue(
			listTypeEntryKey, objectEntry3.getObjectEntryId(),
			picklistObjectField2.getName());
		_assertObjectEntryValue(
			value, objectEntry4.getObjectEntryId(),
			picklistObjectField2.getName());
	}

	private ObjectEntry _addObjectEntry(ObjectField objectField, String value)
		throws Exception {

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			0, _objectDefinition.getObjectDefinitionId(), new HashMap<>());

		_updateObjectEntryValue(
			objectEntry.getObjectEntryId(), objectField, value);

		return objectEntry;
	}

	private ObjectField _addPicklistObjectField(
			ListTypeEntry listTypeEntry, boolean required, boolean state)
		throws Exception {

		return ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			new PicklistObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).listTypeDefinitionId(
				listTypeEntry.getListTypeDefinitionId()
			).name(
				"a" + RandomTestUtil.randomString()
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).objectFieldSettings(
				Arrays.asList(
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE
					).value(
						listTypeEntry.getKey()
					).build(),
					new ObjectFieldSettingBuilder(
					).name(
						ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE
					).value(
						ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE
					).build())
			).required(
				required
			).state(
				state
			).build());
	}

	private void _assertObjectEntryValue(
			String expectedValue, long objectEntryId, String objectFieldName)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		Assert.assertEquals(
			expectedValue,
			MapUtil.getString(objectEntry.getValues(), objectFieldName));
	}

	private void _updateObjectEntryValue(
			long objectEntryId, ObjectField objectField, String value)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", objectField.getDBTableName(), " set ",
							objectField.getDBColumnName(), " = ? where ",
							_objectDefinition.getPKObjectFieldDBColumnName(),
							" = ?"))) {

				preparedStatement.setString(1, value);
				preparedStatement.setLong(2, objectEntryId);

				preparedStatement.executeUpdate();
			}
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v12_1_1." +
			"ObjectEntryPicklistDefaultValueUpgradeProcess";

	@DeleteAfterTestRun
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}