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
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalTransformerListenerRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
		_ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		_transformMethod = JournalTestUtil.getJournalUtilTransformMethod();
	}

	@Test
	public void testFTLTransformation() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String script = "${name.getData()} - ${viewMode}";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.PRINT, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals("Joe Bloggs - print", content);
	}

	@Test
	public void testLocaleTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Joao da Silva"
			).put(
				LocaleUtil.US, "Joe Bloggs"
			).build(),
			_language.getLanguageId(LocaleUtil.US));

		String script = "${name.getData()}";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals("Joe Bloggs", content);

		content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "pt_BR",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals("Joao da Silva", content);

		content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "fr_CA",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testLocaleTransformerListenerNestedFieldWithNoTranslation()
		throws Exception {

		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			HashMapBuilder.put(
				LocaleUtil.US, "Joe Bloggs"
			).build(),
			_language.getLanguageId(LocaleUtil.US));

		Document document = UnsecureSAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		Attribute availableLocalesAttribute = rootElement.attribute(
			"available-locales");

		availableLocalesAttribute.setValue("en_US,pt_BR");

		Element dynamicElement = (Element)document.selectSingleNode(
			"//dynamic-element");

		dynamicElement.addElement("nestedElement");

		String script = "${name.getData()}";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US", document, null, script,
			false, new HashMap<>());

		Assert.assertEquals("Joe Bloggs", content);

		content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "pt_BR", document, null, script,
			false, new HashMap<>());

		Assert.assertEquals("Joe Bloggs", content);
	}

	@Test
	public void testRegexTransformerListener() throws Exception {
		initRegexTransformerListener();

		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent(
			"name", "Joe Bloggs");

		String script = "Hello ${name.getData()}, Welcome to beta.sample.com.";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals(
			"Hello Joe Bloggs, Welcome to production.sample.com.", content);
	}

	@Test
	public void testTokensTransformerListener() throws Exception {
		Map<String, String> tokens = getTokens();

		String xml = DDMStructureTestUtil.getSampleStructuredContent();

		String script = "@company_id@";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()), content);

		script = "@@company_id@@";

		content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals(
			String.valueOf(TestPropsValues.getCompanyId()), content);
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
		Map<String, String> tokens = getTokens();

		tokens.put("article_resource_pk", "1");

		String xml = DDMStructureTestUtil.getSampleStructuredContent();

		String script = "@view_counter@";

		String content = (String)_transformMethod.invoke(
			null, null, tokens, Constants.VIEW, "en_US",
			UnsecureSAXReaderUtil.read(xml), null, script, false,
			new HashMap<>());

		Assert.assertEquals(
			StringBundler.concat(
				"<script type=\"text/javascript\">",
				"Liferay.Service('/assetentry/increment-view-counter',",
				"{userId:0, className:'",
				"com.liferay.journal.model.JournalArticle', classPK:1});",
				"</script>"),
			content);
	}

	protected Map<String, String> getTokens() throws Exception {
		return HashMapBuilder.put(
			TemplateConstants.CLASS_NAME_ID,
			String.valueOf(
				_classNameLocalService.getClassNameId(
					DDMStructure.class.getName()))
		).put(
			"article_group_id", String.valueOf(TestPropsValues.getGroupId())
		).put(
			"company_id", String.valueOf(TestPropsValues.getCompanyId())
		).put(
			"ddm_structure_id", String.valueOf(_ddmStructure.getStructureId())
		).build();
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

	private void _testTransformDDMFormField(
			DDMFormField ddmFormField, String expected, String fieldValue,
			Locale locale)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			ddmFormField, _ddmFormValuesToFieldsConverter, fieldValue,
			TestPropsValues.getGroupId(), _journalConverter);

		Assert.assertEquals(
			expected,
			(String)_transformMethod.invoke(
				null, null,
				HashMapBuilder.put(
					TemplateConstants.CLASS_NAME_ID,
					String.valueOf(
						_classNameLocalService.getClassNameId(
							DDMStructure.class.getName()))
				).put(
					"article_group_id",
					String.valueOf(journalArticle.getGroupId())
				).put(
					"company_id", String.valueOf(journalArticle.getCompanyId())
				).put(
					"ddm_structure_id",
					() -> {
						DDMStructure ddmStructure =
							journalArticle.getDDMStructure();

						return String.valueOf(ddmStructure.getStructureId());
					}
				).build(),
				Constants.VIEW, LocaleUtil.toLanguageId(locale),
				journalArticle.getDocument(), null,
				"${" + ddmFormField.getName() + ".getData()}", false,
				new HashMap<>()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private JournalTransformerListenerRegistry
		_journalTransformerListenerRegistry;

	@Inject
	private Language _language;

	@Inject
	private Portal _portal;

	private Method _transformMethod;

}