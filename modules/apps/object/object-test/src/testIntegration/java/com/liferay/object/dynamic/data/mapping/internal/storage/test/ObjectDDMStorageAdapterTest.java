/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.internal.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
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
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.MultiselectPicklistObjectFieldBuilder;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
							LocaleUtil.US, "ListTypeEntry2"))),
				new ServiceContext());

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			ListUtil.fromArray(
				new AttachmentObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).name(
					"attachmentObjectField"
				).objectFieldSettings(
					ListUtil.fromArray(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_ACCEPTED_FILE_EXTENSIONS
						).value(
							"txt"
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_FILE_SOURCE
						).value(
							ObjectFieldSettingConstants.
								VALUE_USER_COMPUTER_TO_DOCS_AND_MEDIA
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
						).value(
							"100"
						).build())
				).build(),
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
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testGetAndSave() throws Exception {
		DDMStorageAdapter objectDDMStorageAdapter =
			_ddmStorageAdapterRegistry.getDDMStorageAdapter("object");

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		ddmForm.addDDMFormField(
			_createDDMFormField(
				"string", "multiselectDDMFormField",
				DDMFormFieldTypeConstants.SELECT,
				"multiselectPicklistObjectField"));
		ddmForm.addDDMFormField(
			_createDDMFormField(
				"string", "selectDDMFormField",
				DDMFormFieldTypeConstants.SELECT, "picklistObjectField"));
		ddmForm.addDDMFormField(
			_createDDMFormField(
				"document-library", "uploadDDMFormField1",
				DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
				"attachmentObjectField"));
		ddmForm.addDDMFormField(
			_createDDMFormField(
				"document-library", "uploadDDMFormField2",
				DDMFormFieldTypeConstants.DOCUMENT_LIBRARY,
				"attachmentObjectField"));

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

		FileEntry fileEntry = _addFileEntry();

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"uploadDDMFormField1",
				DDMFormValuesTestUtil.createLocalizedValue(
					"{\"fileEntryId\":" + fileEntry.getFileEntryId() + "}",
					LocaleUtil.US)));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"uploadDDMFormField2",
				DDMFormValuesTestUtil.createLocalizedValue(
					"{\"fileEntryId\":" + fileEntry.getFileEntryId() + "}",
					LocaleUtil.US)));

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

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			MapUtil.getLong(objectEntry.getValues(), "attachmentObjectField"));

		Assert.assertEquals(
			_objectDefinition.getClassName(), dlFileEntry.getClassName());
		Assert.assertEquals(
			objectEntry.getObjectEntryId(), dlFileEntry.getClassPK());

		AssertUtils.assertFailure(
			NoSuchFileEntryException.class,
			"No DLFileEntry exists with the primary key " +
				fileEntry.getFileEntryId(),
			() -> _dlFileEntryLocalService.getDLFileEntry(
				fileEntry.getFileEntryId()));

		DDMStorageAdapterGetResponse ddmStorageAdapterGetResponse =
			objectDDMStorageAdapter.get(
				DDMStorageAdapterGetRequest.Builder.newBuilder(
					objectEntry.getObjectEntryId(), ddmForm
				).build());

		_assertDDMFormFieldValue(
			ddmStorageAdapterGetResponse.getDDMFormValues(),
			"multiselectDDMFormField",
			DDMFormValuesTestUtil.createLocalizedValue(
				"[\"ListTypeEntry1\",\"ListTypeEntry2\"]", LocaleUtil.US));
		_assertDDMFormFieldValue(
			ddmStorageAdapterGetResponse.getDDMFormValues(),
			"selectDDMFormField",
			DDMFormValuesTestUtil.createLocalizedValue(
				"[\"ListTypeEntry1\"]", LocaleUtil.US));
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

	private FileEntry _addFileEntry() throws Exception {
		String content = RandomTestUtil.randomString();
		Folder folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(content.getBytes()), 0, null, null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertDDMFormFieldValue(
		DDMFormValues ddmFormValues, String ddmFormFieldName,
		Value expectedValue) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormFieldName);

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Assert.assertEquals(expectedValue, ddmFormFieldValue.getValue());
	}

	private DDMFormField _createDDMFormField(
		String dataType, String ddmFormFieldName, String ddmFormFieldType,
		String objectFieldName) {

		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			ddmFormFieldName, RandomTestUtil.randomString(), ddmFormFieldType,
			dataType, true, false, false);

		if (Objects.equals(
				ddmFormFieldType, DDMFormFieldTypeConstants.SELECT)) {

			DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

			_adDDMFormFieldOption(
				ddmFormFieldOptions, "Option1", "ListTypeEntry1");
			_adDDMFormFieldOption(
				ddmFormFieldOptions, "Option2", "ListTypeEntry2");

			ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
		}

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

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

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