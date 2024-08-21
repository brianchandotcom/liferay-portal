/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RepeatableFieldInfoItemCollectionProvider;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class DLFileEntryRepeatableFieldsInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRepeatableFieldInfoItemCollectionProviderWithRepeatableField()
		throws Exception {

		FileEntry fileEntry = _addFileEntry();

		InfoPage<RepeatableInfoFieldValue> infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(
					fileEntry,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS)));

		Assert.assertEquals(4, infoPage.getTotalCount());

		List<? extends RepeatableInfoFieldValue> pageItems =
			infoPage.getPageItems();

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "one");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "two");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(2), "three");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(3), "four");
	}

	@Test
	public void testRepeatableFieldInfoItemCollectionProviderWithWithCustomCollectionQuery()
		throws Exception {

		FileEntry fileEntry = _addFileEntry();

		InfoPage<RepeatableInfoFieldValue> infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(fileEntry, Pagination.of(2, 0)));

		Assert.assertEquals(4, infoPage.getTotalCount());

		List<? extends RepeatableInfoFieldValue> pageItems =
			infoPage.getPageItems();

		Assert.assertEquals(pageItems.toString(), 2, pageItems.size());

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "one");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "two");

		infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(fileEntry, Pagination.of(4, 2)));

		Assert.assertEquals(4, infoPage.getTotalCount());

		pageItems = infoPage.getPageItems();

		Assert.assertEquals(pageItems.toString(), 2, pageItems.size());

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "three");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "four");
	}

	private FileEntry _addFileEntry() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(DLFileEntryMetadata.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(DLFileEntryMetadata.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(
				_readFileToString("structure_with_repeatable_field.json")),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.addFileEntryType(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				ddmStructure.getStructureId(), null,
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				serviceContext);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringUtil.randomString(), RandomTestUtil.randomString(),
			dlFileEntryType.getFileEntryTypeId(),
			HashMapBuilder.put(
				ddmStructure.getStructureKey(), _getDDMFormValues()
			).build(),
			null, new UnsyncByteArrayInputStream(new byte[0]), 0, null, null,
			null, serviceContext);

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				dlFileEntry.getFileEntryId(), true);

		_dlAppHelperLocalService.updateAsset(
			TestPropsValues.getUserId(), new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(dlFileVersion), serviceContext);

		return _dlAppLocalService.getFileEntryByExternalReferenceCode(
			dlFileEntry.getExternalReferenceCode(), _group.getGroupId());
	}

	private void _assertInfoItemFieldValue(
		String fieldName, Locale locale,
		RepeatableInfoFieldValue repeatableInfoFieldValue, String value) {

		InfoItemFieldValues infoItemFieldValues =
			repeatableInfoFieldValue.getInfoItemFieldValues();

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(fieldName);

		Assert.assertEquals(value, infoFieldValue.getValue(locale));
	}

	private DDMFormField _createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setFieldReference(name);
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		String name, String valueString, Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setInstanceId(StringUtil.randomString());

		Value value = new LocalizedValue(locale);

		value.addString(locale, valueString);

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	private com.liferay.dynamic.data.mapping.model.DDMForm _deserialize(
		String content) {

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private CollectionQuery _getCollectionQuery(
		FileEntry fileEntry, Pagination pagination) {

		CollectionQuery collectionQuery = new CollectionQuery();

		collectionQuery.setConfiguration(
			HashMapBuilder.put(
				"fieldNames", new String[] {"Text1"}
			).build());

		collectionQuery.setPagination(pagination);
		collectionQuery.setRelatedItemObject(fileEntry);

		return collectionQuery;
	}

	private DDMFormValues _getDDMFormValues() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(LocaleUtil.US);
		ddmForm.setDefaultLocale(LocaleUtil.US);

		DDMFormField booleanDDMFormField = _createDDMFormField(
			"Text1", "Text1", DDMFormFieldType.TEXT, "text", true, true, false);

		ddmForm.addDDMFormField(booleanDDMFormField);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue("Text1", "one", LocaleUtil.US));
		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue("Text1", "two", LocaleUtil.US));
		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue("Text1", "three", LocaleUtil.US));
		ddmFormValues.addDDMFormFieldValue(
			_createDDMFormFieldValue("Text1", "four", LocaleUtil.US));

		return ddmFormValues;
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getResourceAsStream("dependencies/" + fileName));
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	@Inject
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.document.library.web.internal.info.collection.provider.DLFileEntryRepeatableFieldsInfoCollectionProvider"
	)
	private RepeatableFieldInfoItemCollectionProvider
		_repeatableFieldInfoItemCollectionProvider;

}