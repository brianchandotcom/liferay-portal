/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.content.type.DataDefinitionContentType;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMFormValuesInfoFieldValuesProvider;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class DDMFormValuesInfoFieldValuesProviderTest
	extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);
	}

	@Test
	public void testGetInfoFieldValuesCheckboxMultipleDDMFormFieldType()
		throws Exception {

		String expectedKey1 = RandomTestUtil.randomString(10);
		String expectedKey2 = RandomTestUtil.randomString(10);
		String expectedLabel1 = RandomTestUtil.randomString();
		String expectedLabel2 = RandomTestUtil.randomString();

		DDMFormField ddmFormField = _createDDMFormField(
			true,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				expectedKey1, expectedLabel1
			).put(
				expectedKey2, expectedLabel2
			).build(),
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter,
			JSONUtil.putAll(
				expectedKey1, expectedKey2
			).toString(),
			_group.getGroupId(), _journalConverter);

		_assertGetInfoFieldValues(
			Collections.singletonList(ddmFormField), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value,
				HashMapBuilder.put(
					expectedKey1, expectedLabel1
				).put(
					expectedKey2, expectedLabel2
				).build()));
	}

	@Test
	public void testGetInfoFieldValuesFieldSetDDMFormFieldType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		JournalFolder journalFolder = _journalFolderLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DataDefinitionContentType.class),
				_group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(DataDefinitionContentType.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			read("test-ddm-structure-definition-with-nested-fields.json"),
			StorageType.DEFAULT.getValue());

		journalFolder = _journalFolderLocalService.updateFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			journalFolder.getFolderId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new long[] {ddmStructure.getStructureId()}, 1, false,
			serviceContext);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class));

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			journalFolder.getFolderId(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			read("ddm-structure-fieldset-field.xsd"),
			ddmStructure.getStructureId(), ddmTemplate.getTemplateKey(),
			serviceContext);

		_assertGetInfoFieldValues(
			ddmStructure.getDDMFormFields(false), journalArticle,
			Assert::assertNull);
	}

	@Test
	public void testGetInfoFieldValuesRadioDDMFormFieldType() throws Exception {
		String expectedLabel = RandomTestUtil.randomString();
		String expectedKey = RandomTestUtil.randomString(10);

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				expectedKey, expectedLabel
			).build(),
			DDMFormFieldTypeConstants.RADIO);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, expectedKey, _group.getGroupId(),
			_journalConverter);

		_assertGetInfoFieldValues(
			Collections.singletonList(ddmFormField), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value,
				HashMapBuilder.put(
					expectedKey, expectedLabel
				).build()));
	}

	@Test
	public void testGetInfoFieldValuesRadioDDMFormFieldTypeNoSelection()
		throws Exception {

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).build(),
			DDMFormFieldTypeConstants.RADIO);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, StringPool.BLANK,
			_group.getGroupId(), _journalConverter);

		_assertGetInfoFieldValues(
			Collections.singletonList(ddmFormField), journalArticle,
			value -> Assert.assertNull(value));
	}

	@Test
	public void testGetInfoFieldValuesSelectDDMFormFieldType()
		throws Exception {

		String expectedLabel = RandomTestUtil.randomString();
		String expectedKey = RandomTestUtil.randomString(10);

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				expectedKey, expectedLabel
			).build(),
			DDMFormFieldTypeConstants.SELECT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter,
			JSONUtil.put(
				expectedKey
			).toString(),
			_group.getGroupId(), _journalConverter);

		_assertGetInfoFieldValues(
			Collections.singletonList(ddmFormField), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value,
				HashMapBuilder.put(
					expectedKey, expectedLabel
				).build()));
	}

	@Test
	public void testGetInfoFieldValuesSelectDDMFormFieldTypeNoSelection()
		throws Exception {

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).build(),
			DDMFormFieldTypeConstants.SELECT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, StringPool.BLANK,
			_group.getGroupId(), _journalConverter);

		_assertGetInfoFieldValues(
			Collections.singletonList(ddmFormField), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value, Collections.emptyMap()));
	}

	private void _assertExpectedKeyLocalizedLabelPairs(
		Object actual, Map<String, String> expectedKeyLabelMap) {

		Assert.assertTrue(actual instanceof List);

		List<KeyLocalizedLabelPair> actualKeyLocalizedLabelPairs =
			(List<KeyLocalizedLabelPair>)actual;

		Assert.assertEquals(
			actualKeyLocalizedLabelPairs.toString(), expectedKeyLabelMap.size(),
			actualKeyLocalizedLabelPairs.size());

		for (KeyLocalizedLabelPair actualKeyLocalizedLabelPair :
				actualKeyLocalizedLabelPairs) {

			Assert.assertTrue(
				expectedKeyLabelMap.containsKey(
					actualKeyLocalizedLabelPair.getKey()));
			Assert.assertEquals(
				expectedKeyLabelMap.get(actualKeyLocalizedLabelPair.getKey()),
				actualKeyLocalizedLabelPair.getLabel(LocaleUtil.US));
		}
	}

	private void _assertGetInfoFieldValues(
			List<DDMFormField> ddmFormFields, JournalArticle journalArticle,
			UnsafeConsumer<Object, Exception> assertValueUnsafeConsumer)
		throws Exception {

		List<InfoFieldValue<InfoLocalizedValue<Object>>> infoFieldValues =
			_ddmFormValuesInfoFieldValuesProvider.getInfoFieldValues(
				journalArticle, journalArticle.getDDMFormValues());

		Assert.assertEquals(
			infoFieldValues.toString(), ddmFormFields.size(),
			infoFieldValues.size());

		for (int i = 0; i < ddmFormFields.size(); i++) {
			DDMFormField ddmFormField = ddmFormFields.get(i);

			InfoFieldValue<InfoLocalizedValue<Object>> infoFieldValue =
				infoFieldValues.get(i);

			InfoField infoField = infoFieldValue.getInfoField();

			Assert.assertEquals(ddmFormField.getName(), infoField.getName());

			assertValueUnsafeConsumer.accept(
				infoFieldValue.getValue(LocaleUtil.US));
		}
	}

	private DDMFormField _createDDMFormField(
		boolean multiple, Map<String, String> optionsMap, String type) {

		DDMFormField ddmFormField = new DDMFormField("name", type);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);
		ddmFormField.setMultiple(multiple);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(
			LocaleUtil.US, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(localizedValue);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (Map.Entry<String, String> entry : optionsMap.entrySet()) {
			String optionKey = entry.getKey();
			String optionLabel = entry.getValue();

			ddmFormFieldOptions.addOptionLabel(
				optionKey, LocaleUtil.US, optionLabel);
		}

		return ddmFormField;
	}

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private DDMFormValuesInfoFieldValuesProvider
		_ddmFormValuesInfoFieldValuesProvider;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private JournalFolderLocalService _journalFolderLocalService;

}