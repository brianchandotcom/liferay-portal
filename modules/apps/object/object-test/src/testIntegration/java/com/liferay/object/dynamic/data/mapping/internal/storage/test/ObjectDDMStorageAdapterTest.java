/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.internal.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class ObjectDDMStorageAdapterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false,
				ListUtil.fromArray(
					ListTypeEntryUtil.createListTypeEntry(
						"ListTypeEntry1",
						Collections.singletonMap(
							LocaleUtil.US, "ListTypeEntry1")),
					ListTypeEntryUtil.createListTypeEntry(
						"ListTypeEntry2",
						Collections.singletonMap(
							LocaleUtil.US, "ListTypeEntry2"))));

		_objectDefinition = ObjectDefinitionTestUtil.addCustomObjectDefinition(
			ListUtil.fromArray(
				new MultiselectPicklistObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).name(
					"multiselectPicklistObjectField"
				).build(),
				new PicklistObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).listTypeDefinitionId(
					_listTypeDefinition.getListTypeDefinitionId()
				).name(
					"picklistObjectField"
				).build()));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testSave() throws Exception {
		DDMStorageAdapter objectDDMStorageAdapter =
			_ddmStorageAdapterRegistry.getDDMStorageAdapter("object");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			_createDDMFormField(
				"multiselectDDMFormField", "multiselectPicklistObjectField"));
		ddmForm.addDDMFormField(
			_createDDMFormField("selectDDMFormField", "picklistObjectField"));

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"multiselectDDMFormField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"[\"Option1\", \"Option2\"]", LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"selectDDMFormField",
				DDMFormValuesTestUtil.createLocalizedValue(
					"[\"Option1\"]", LocaleUtil.US)));

		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
			objectDDMStorageAdapter.save(
				DDMStorageAdapterSaveRequest.Builder.newBuilder(
					TestPropsValues.getUserId(), ddmFormValues
				).withDDMFormInstance(
					DDMFormInstanceTestUtil.addDDMFormInstance(
						ddmForm, _group, _createSettingsDDMFormValues(),
						TestPropsValues.getUserId())
				).withGroupId(
					_group.getGroupId()
				).build());

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			ddmStorageAdapterSaveResponse.getPrimaryKey());

		Assert.assertEquals(
			"ListTypeEntry1, ListTypeEntry2",
			MapUtil.getString(
				objectEntry.getValues(), "multiselectPicklistObjectField"));
		Assert.assertEquals(
			"ListTypeEntry1",
			MapUtil.getString(objectEntry.getValues(), "picklistObjectField"));
	}

	private void _adDDMFormFieldOption(
		DDMFormFieldOptions ddmFormFieldOptions, String ddmFormFieldOptionValue,
		String listTypeEntryKey) {

		ddmFormFieldOptions.addOption(ddmFormFieldOptionValue);
		ddmFormFieldOptions.addOptionLabel(
			ddmFormFieldOptionValue, LocaleUtil.US,
			RandomTestUtil.randomString());
		ddmFormFieldOptions.addOptionReference(
			ddmFormFieldOptionValue, listTypeEntryKey);
	}

	private DDMFormField _createDDMFormField(
		String ddmFormFieldName, String objectFieldName) {

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			ddmFormFieldName, RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.SELECT, "string", true, false, false);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		_adDDMFormFieldOption(ddmFormFieldOptions, "Option1", "ListTypeEntry1");
		_adDDMFormFieldOption(ddmFormFieldOptions, "Option2", "ListTypeEntry2");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setProperty(
			"objectFieldName", "[\"" + objectFieldName + "\"]");

		return ddmFormField;
	}

	private DDMFormValues _createSettingsDDMFormValues() {
		DDMForm ddmForm = DDMFormFactory.create(DDMFormInstanceSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"objectDefinitionId",
				String.valueOf(_objectDefinition.getObjectDefinitionId())));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"storageType", "object"));

		return ddmFormValues;
	}

	@Inject
	private static DDMStorageAdapterRegistry _ddmStorageAdapterRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}