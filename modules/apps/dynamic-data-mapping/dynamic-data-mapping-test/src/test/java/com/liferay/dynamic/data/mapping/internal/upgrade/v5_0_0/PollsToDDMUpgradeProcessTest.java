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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_0_0;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.Set;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class PollsToDDMUpgradeProcessTest extends BaseDDMTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormLayoutJSONSerializer();
		setUpDDMFormValuesJSONSerializer();
		setUpJSONFactoryUtil();
		setUpLanguageUtil(
			HashMapBuilder.put(
				"radio-field-type-label", "Single Selection"
			).build());
		setUpLocaleUtil();
		_setUpLocalizationUtil();
		_setUpPollsToDDMUpgradeProcess();
	}

	@Test
	public void testCreateDDDMFormFieldOptions() throws Exception {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions(
			LocaleUtil.US);

		_pollsToDDMUpgradeProcess.createDDDMFormFieldOptions(
			ddmFormFieldOptions,
			_getPollsChoiceDescription("Option 1", "Option 1 PT"), "a",
			"Option1");
		_pollsToDDMUpgradeProcess.createDDDMFormFieldOptions(
			ddmFormFieldOptions,
			_getPollsChoiceDescription("Option 2", "Option 2 PT"), "b",
			"Option2");

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertEquals(optionsValues.toString(), 2, optionsValues.size());
		Assert.assertThat(
			optionsValues, CoreMatchers.hasItems("Option1", "Option2"));

		LocalizedValue optionLabels = ddmFormFieldOptions.getOptionLabels(
			"Option1");

		Assert.assertEquals(
			"a. Option 1 PT", optionLabels.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"a. Option 1", optionLabels.getString(LocaleUtil.US));

		optionLabels = ddmFormFieldOptions.getOptionLabels("Option2");

		Assert.assertEquals(
			"b. Option 2 PT", optionLabels.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"b. Option 2", optionLabels.getString(LocaleUtil.US));
	}

	@Test
	public void testGetDataJSONObject() throws Exception {
		DDMFormField ddmFormField = _pollsToDDMUpgradeProcess.getDDMFormField(
			new DDMFormFieldOptions(LocaleUtil.US));

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions(
			LocaleUtil.US);

		ddmFormFieldOptions.addOption("Option1");
		ddmFormFieldOptions.addOption("Option2");

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		ddmFormField.setName("SingleSelection");

		Assert.assertEquals(
			JSONUtil.put(
				"SingleSelection",
				JSONUtil.put(
					"type", "radio"
				).put(
					"values",
					JSONUtil.put(
						"Option1", 0
					).put(
						"Option2", 0
					)
				)
			).put(
				"totalItems", 0
			).toString(),
			String.valueOf(
				_pollsToDDMUpgradeProcess.getDataJSONObject(ddmFormField)));
	}

	@Test
	public void testGetDDMForm() throws Exception {
		DDMForm ddmForm = _pollsToDDMUpgradeProcess.getDDMForm(
			new DDMFormField("fieldName", "radio"));

		Assert.assertEquals(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US}),
			ddmForm.getAvailableLocales());
		Assert.assertEquals(LocaleUtil.US, ddmForm.getDefaultLocale());
	}

	@Test
	public void testGetDDMFormField() throws Exception {
		DDMFormField ddmFormField = _pollsToDDMUpgradeProcess.getDDMFormField(
			new DDMFormFieldOptions(LocaleUtil.US));

		Assert.assertEquals("string", ddmFormField.getDataType());

		LocalizedValue label = ddmFormField.getLabel();

		Assert.assertEquals("Single Selection", label.getString(LocaleUtil.US));

		Assert.assertEquals("radio", ddmFormField.getType());
		Assert.assertFalse((boolean)ddmFormField.getProperty("inline"));
		Assert.assertFalse(ddmFormField.isShowLabel());
		Assert.assertNotNull(ddmFormField.getDDMFormFieldOptions());

		String ddmFormFieldName = ddmFormField.getName();

		Assert.assertTrue(
			ddmFormFieldName.matches("^SingleSelection[\\d]{8}$"));

		Assert.assertTrue((boolean)ddmFormField.getProperty("visible"));
		Assert.assertTrue(ddmFormField.isLocalizable());
		Assert.assertTrue(ddmFormField.isRequired());
	}

	@Test
	public void testGetDDMFormLayoutDefinition() throws Exception {
		DDMFormField ddmFormField = _pollsToDDMUpgradeProcess.getDDMFormField(
			new DDMFormFieldOptions(LocaleUtil.US));

		ddmFormField.setName("SingleSelection");

		JSONAssert.assertEquals(
			read("ddm-form-layout-definition.json"),
			_pollsToDDMUpgradeProcess.getDDMFormLayoutDefinition(ddmFormField),
			false);
	}

	@Test
	public void testGetSerializedSettingsDDMFormValues() throws Exception {
		JSONAssert.assertEquals(
			read("ddm-form-instance-settings.json"),
			_pollsToDDMUpgradeProcess.getSerializedSettingsDDMFormValues(),
			false);
	}

	private String _getPollsChoiceDescription(
		String enChoice, String ptChoice) {

		StringBundler sb = new StringBundler(8);

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<root available-locales='en_US,pt_BR' ");
		sb.append("default-locale='en_US'>");
		sb.append("<Description language-id='en_US'>");
		sb.append(enChoice);
		sb.append("</Description><Description language-id='pt_BR'>");
		sb.append(ptChoice);
		sb.append("</Description></root>");

		return sb.toString();
	}

	private void _setUpLocalizationUtil() {
		LocalizationUtil localizationUtil = new LocalizationUtil();

		when(
			_localization.getAvailableLanguageIds(Matchers.anyString())
		).thenReturn(
			new String[] {"en_US", "pt_BR"}
		);

		when(
			_localization.getLocalization(
				Matchers.anyString(), Matchers.anyString())
		).then(
			new Answer<String>() {

				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] arguments = invocationOnMock.getArguments();

					String xml = (String)arguments[0];

					String languageIdAttribute =
						"language-id='" + (String)arguments[1] + "'>";

					String languageIdElement = xml.substring(
						xml.indexOf(languageIdAttribute) +
							languageIdAttribute.length());

					return languageIdElement.substring(
						0, languageIdElement.indexOf("</"));
				}

			}
		);

		localizationUtil.setLocalization(_localization);
	}

	private void _setUpPollsToDDMUpgradeProcess() throws Exception {
		MemberMatcher.field(
			PollsToDDMUpgradeProcess.class, "_availableLocales"
		).set(
			_pollsToDDMUpgradeProcess,
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US})
		);

		MemberMatcher.field(
			PollsToDDMUpgradeProcess.class, "_defaultLocale"
		).set(
			_pollsToDDMUpgradeProcess, LocaleUtil.US
		);
	}

	private static final PollsToDDMUpgradeProcess _pollsToDDMUpgradeProcess =
		new PollsToDDMUpgradeProcess(
			ddmFormLayoutJSONSerializer, null, ddmFormValuesJSONSerializer,
			null, null);

	@Mock
	private Localization _localization;

}