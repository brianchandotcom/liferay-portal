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
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.related.models.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
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
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectField objectField1 = _addPicklistObjectField(
			ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE, listTypeEntry,
			_objectDefinition1, false, false);
		ObjectField objectField2 = _addPicklistObjectField(
			ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE, listTypeEntry,
			_objectDefinition1, true, true);

		ObjectEntry objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			0, _objectDefinition1.getObjectDefinitionId(), new HashMap<>());

		_setColumnValue(
			_objectDefinition1, objectEntry1.getObjectEntryId(), objectField1,
			null);
		_setColumnValue(
			_objectDefinition1, objectEntry1.getObjectEntryId(), objectField2,
			null);

		ObjectEntry objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			0, _objectDefinition1.getObjectDefinitionId(), new HashMap<>());

		_setColumnValue(
			_objectDefinition1, objectEntry2.getObjectEntryId(), objectField2,
			"");

		String objectFieldValue = RandomTestUtil.randomString();

		ObjectEntry objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			0, _objectDefinition1.getObjectDefinitionId(), new HashMap<>());

		_setColumnValue(
			_objectDefinition1, objectEntry3.getObjectEntryId(), objectField2,
			objectFieldValue);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition();

		ObjectField objectField3 = _addPicklistObjectField(
			ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE, listTypeEntry,
			_objectDefinition2, true, true);

		ObjectEntry objectEntry4 = ObjectEntryTestUtil.addObjectEntry(
			0, _objectDefinition2.getObjectDefinitionId(), new HashMap<>());

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField3.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);

		objectFieldSetting.setValue(
			ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER);

		_objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSetting);

		_setColumnValue(
			_objectDefinition2, objectEntry4.getObjectEntryId(), objectField3,
			null);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		Map<String, Serializable> values1 = _objectEntryLocalService.getValues(
			objectEntry1.getObjectEntryId());

		Assert.assertEquals(
			StringPool.BLANK, values1.get(objectField1.getName()));
		Assert.assertEquals(
			listTypeEntryKey, values1.get(objectField2.getName()));

		Map<String, Serializable> values2 = _objectEntryLocalService.getValues(
			objectEntry2.getObjectEntryId());

		Assert.assertEquals(
			listTypeEntryKey, values2.get(objectField2.getName()));

		Map<String, Serializable> values3 = _objectEntryLocalService.getValues(
			objectEntry3.getObjectEntryId());

		Assert.assertEquals(
			objectFieldValue, values3.get(objectField2.getName()));

		Map<String, Serializable> values4 = _objectEntryLocalService.getValues(
			objectEntry4.getObjectEntryId());

		Assert.assertEquals(
			StringPool.BLANK, values4.get(objectField3.getName()));
	}

	private ObjectField _addPicklistObjectField(
			String defaultValueType, ListTypeEntry listTypeEntry,
			ObjectDefinition objectDefinition, boolean required, boolean state)
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
				objectDefinition.getObjectDefinitionId()
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
						defaultValueType
					).build())
			).required(
				required
			).state(
				state
			).build());
	}

	private void _setColumnValue(
			ObjectDefinition objectDefinition, long objectEntryId,
			ObjectField objectField, String value)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", objectField.getDBTableName(), " set ",
							objectField.getDBColumnName(), " = ? where ",
							objectDefinition.getPKObjectFieldDBColumnName(),
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
	private ObjectDefinition _objectDefinition1;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition2;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}