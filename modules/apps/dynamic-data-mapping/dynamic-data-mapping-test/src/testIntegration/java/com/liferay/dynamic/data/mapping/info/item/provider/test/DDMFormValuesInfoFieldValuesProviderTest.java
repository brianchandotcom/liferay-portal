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

package com.liferay.dynamic.data.mapping.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMFormValuesInfoFieldValuesProvider;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Calendar;
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
public class DDMFormValuesInfoFieldValuesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
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

		JournalArticle journalArticle = _addJournalArticle(
			ddmFormField,
			JSONUtil.putAll(
				expectedKey1, expectedKey2
			).toString());

		_assertGetInfoFieldValues(
			ddmFormField.getName(), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value,
				HashMapBuilder.put(
					expectedKey1, expectedLabel1
				).put(
					expectedKey2, expectedLabel2
				).build()));
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

		JournalArticle journalArticle = _addJournalArticle(
			ddmFormField, expectedKey);

		_assertGetInfoFieldValues(
			ddmFormField.getName(), journalArticle,
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

		JournalArticle journalArticle = _addJournalArticle(
			ddmFormField, StringPool.BLANK);

		_assertGetInfoFieldValues(
			ddmFormField.getName(), journalArticle,
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

		JournalArticle journalArticle = _addJournalArticle(
			ddmFormField,
			JSONUtil.put(
				expectedKey
			).toString());

		_assertGetInfoFieldValues(
			ddmFormField.getName(), journalArticle,
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

		JournalArticle journalArticle = _addJournalArticle(
			ddmFormField, StringPool.BLANK);

		_assertGetInfoFieldValues(
			ddmFormField.getName(), journalArticle,
			value -> _assertExpectedKeyLocalizedLabelPairs(
				value, Collections.emptyMap()));
	}

	private JournalArticle _addJournalArticle(
			DDMFormField ddmFormField, String stringValue)
		throws Exception {

		DDMForm ddmForm = _createDDMForm(ddmFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm,
			LocaleUtil.US);

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure,
			_createDDMFormValues(
				ddmForm, _getDDMFormFieldValue(ddmFormField, stringValue)));

		String content = _journalConverter.getContent(
			ddmStructure, fields, _group.getGroupId());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		User user = TestPropsValues.getUser();

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		return _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.US),
			RandomTestUtil.randomLocaleStringMap(LocaleUtil.US),
			HashMapBuilder.put(
				LocaleUtil.US,
				FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString(
						LayoutFriendlyURLRandomizerBumper.INSTANCE))
			).build(),
			content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), null, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null, null,
			null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
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
			String ddmFormFieldName, JournalArticle journalArticle,
			UnsafeConsumer<Object, Exception> assertValueUnsafeConsumer)
		throws Exception {

		List<InfoFieldValue<InfoLocalizedValue<Object>>> infoFieldValues =
			_ddmFormValuesInfoFieldValuesProvider.getInfoFieldValues(
				journalArticle,
				_ddmBeanTranslator.translate(
					journalArticle.getDDMFormValues()));

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<InfoLocalizedValue<Object>> infoFieldValue =
			infoFieldValues.get(0);

		InfoField infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(ddmFormFieldName, infoField.getName());

		assertValueUnsafeConsumer.accept(
			infoFieldValue.getValue(LocaleUtil.US));
	}

	private DDMForm _createDDMForm(DDMFormField ddmFormField) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setDefaultLocale(LocaleUtil.US);
		ddmForm.addAvailableLocale(LocaleUtil.US);

		ddmForm.addDDMFormField(ddmFormField);

		return ddmForm;
	}

	private DDMFormField _createDDMFormField(
		boolean multiple, Map<String, String> optionsMap, String type) {

		DDMFormField ddmFormField = new DDMFormField(
			RandomTestUtil.randomString(10), type);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);
		ddmFormField.setMultiple(multiple);

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.US, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(label);

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

	private DDMFormValues _createDDMFormValues(
		DDMForm ddmForm, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(LocaleUtil.US);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		return ddmFormValues;
	}

	private DDMFormFieldValue _getDDMFormFieldValue(
		DDMFormField ddmFormField, String stringValue) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(ddmFormField.getName());

		Value value = new LocalizedValue(LocaleUtil.US);

		value.addString(LocaleUtil.US, stringValue);

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	@Inject
	private DDMBeanTranslator _ddmBeanTranslator;

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

}