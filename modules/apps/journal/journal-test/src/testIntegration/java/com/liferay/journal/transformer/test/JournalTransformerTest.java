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

package com.liferay.journal.transformer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.util.JournalTransformerListenerRegistry;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class JournalTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_transformMethod = JournalTestUtil.getJournalUtilTransformMethod();

		_group = GroupTestUtil.addGroup();

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
				_read("data_definition.json"), TestPropsValues.getUser());

		_journalArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _read("journal_content.xml"),
			dataDefinition.getDataDefinitionKey(), null);
	}

	@Test
	public void testFTLTransformation() throws Exception {
		Assert.assertEquals(
			"Joe Bloggs - print",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"${name.getData()} - ${viewMode}", null, Constants.PRINT));
	}

	@Test
	public void testLocaleTransformerListener() throws Exception {
		Assert.assertEquals(
			"Joe Bloggs",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"${name.getData()}", null, Constants.VIEW));

		Assert.assertEquals(
			"Joao da Silva",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.BRAZIL),
				_layoutDisplayPageProviderRegistry, null, false,
				"${name.getData()}", null, Constants.VIEW));

		Assert.assertEquals(
			"Joe Bloggs",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.FRENCH),
				_layoutDisplayPageProviderRegistry, null, false,
				"${name.getData()}", null, Constants.VIEW));
	}

	@Test
	public void testLocaleTransformerListenerNestedFieldWithNoTranslation()
		throws Exception {

		Assert.assertEquals(
			"2022-11-26",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"${birthday.getData()}", null, Constants.VIEW));

		Assert.assertEquals(
			"2022-11-26",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.BRAZIL),
				_layoutDisplayPageProviderRegistry, null, false,
				"${birthday.getData()}", null, Constants.VIEW));
	}

	@Test
	public void testRegexTransformerListener() throws Exception {
		initRegexTransformerListener();

		Assert.assertEquals(
			"Hello Joe Bloggs, Welcome to production.sample.com.",
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"Hello ${name.getData()}, Welcome to beta.sample.com.", null,
				Constants.VIEW));
	}

	@Test
	public void testTokensTransformerListener() throws Exception {
		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()),
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false, "@company_id@",
				null, Constants.VIEW));

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()),
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"@@company_id@@", null, Constants.VIEW));
	}

	@Test
	public void testTransformSelectDDMFormFieldType() throws Exception {
		Locale locale = _portal.getSiteDefaultLocale(
			TestPropsValues.getGroupId());

		DDMFormField ddmFormField = new DDMFormField(
			RandomTestUtil.randomString(10), DDMFormFieldTypeConstants.SELECT);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);

		LocalizedValue localizedValue = new LocalizedValue(locale);

		localizedValue.addString(locale, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(localizedValue);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		String expectedKey1 = RandomTestUtil.randomString(10);

		ddmFormFieldOptions.addOptionLabel(
			expectedKey1, locale, RandomTestUtil.randomString());

		String expectedKey2 = RandomTestUtil.randomString(10);

		ddmFormFieldOptions.addOptionLabel(
			expectedKey2, locale, RandomTestUtil.randomString());

		_testTransformDDMFormField(
			ddmFormField, expectedKey1,
			JSONUtil.put(
				expectedKey1
			).toString(),
			locale);

		ddmFormField.setMultiple(true);

		_testTransformDDMFormField(
			ddmFormField,
			JSONUtil.putAll(
				expectedKey1, expectedKey2
			).toString(),
			JSONUtil.putAll(
				expectedKey1, expectedKey2
			).toString(),
			locale);
	}

	@Test
	public void testViewCounterTransformerListener() throws Exception {
		Assert.assertEquals(
			StringBundler.concat(
				"<script type=\"text/javascript\">",
				"Liferay.Service('/assetentry/increment-view-counter',",
				"{userId:0, className:'",
				"com.liferay.journal.model.JournalArticle', classPK:",
				_journalArticle.getResourcePrimKey(), "});</script>"),
			_transformMethod.invoke(
				null, _journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(LocaleUtil.US),
				_layoutDisplayPageProviderRegistry, null, false,
				"@view_counter@", null, Constants.VIEW));
	}

	protected void initRegexTransformerListener() {
		TransformerListener transformerListener =
			_journalTransformerListenerRegistry.getTransformerListener(
				"com.liferay.journal.internal.transformer." +
					"RegexTransformerListener");

		CacheRegistryUtil.setActive(true);

		List<Pattern> patterns = new ArrayList<>();
		List<String> replacements = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			String regex = TestPropsUtil.get(
				"journal.transformer.regex.pattern." + i);
			String replacement = TestPropsUtil.get(
				"journal.transformer.regex.replacement." + i);

			if (Validator.isNull(regex) || Validator.isNull(replacement)) {
				break;
			}

			patterns.add(Pattern.compile(regex));
			replacements.add(replacement);
		}

		ReflectionTestUtil.setFieldValue(
			transformerListener, "_patterns", patterns);
		ReflectionTestUtil.setFieldValue(
			transformerListener, "_replacements", replacements);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _testTransformDDMFormField(
			DDMFormField ddmFormField, String expected, String fieldValue,
			Locale locale)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			ddmFormField, _ddmFormValuesToFieldsConverter, fieldValue,
			TestPropsValues.getGroupId(), _journalConverter);

		Assert.assertEquals(
			expected,
			_transformMethod.invoke(
				null, journalArticle, null, _journalHelper,
				LocaleUtil.toLanguageId(locale),
				_layoutDisplayPageProviderRegistry, null, false,
				"${" + ddmFormField.getName() + ".getData()}", null,
				Constants.VIEW));
	}

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private JournalHelper _journalHelper;

	@Inject
	private JournalTransformerListenerRegistry
		_journalTransformerListenerRegistry;

	@Inject
	private Language _language;

	@Inject
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Inject
	private Portal _portal;

	private Method _transformMethod;

}