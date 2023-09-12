/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.dynamic.data.mapping.form.builder.rule.DDMFormRuleDeserializer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcela Cunha
 */
public class DataLayoutUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
	}

	@Test
	public void testToDDMFormLayoutEquals() throws Exception {
		Locale locale = LocaleUtil.US;

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(locale), locale);

		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setIndexType("text");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label1"
							).build(),
							locale));
					setLocalizable(true);
					setName("textName");
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "enter a text"
							).build(),
							locale));
					setReadOnly(true);
					setRepeatable(true);
					setRequired(true);
					setShowLabel(true);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip1"
							).build(),
							locale));
					setType("text");
				}
			});

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);
		ddmFormLayout.setPaginationMode("wizard");

		ddmFormLayout.addDDMFormLayoutPage(
			new DDMFormLayoutPage() {
				{
					setDDMFormLayoutRows(
						new ArrayList<DDMFormLayoutRow>() {
							{
								add(
									new DDMFormLayoutRow() {
										{
											setDDMFormLayoutColumns(
												new ArrayList
													<DDMFormLayoutColumn>() {

													{
														add(
															new DDMFormLayoutColumn(
																DDMFormLayoutColumn.FULL,
																"textName"));
													}
												});
										}
									});
							}
						});
					setDescription(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "Description"
							).build(),
							LocaleUtil.US));
					setTitle(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "Title"
							).build(),
							LocaleUtil.US));
				}
			});

		DataLayout dataLayout = new DataLayout();

		dataLayout.setDataLayoutPages(
			new DataLayoutPage[] {
				new DataLayoutPage() {
					{
						dataLayoutRows = new DataLayoutRow[] {
							new DataLayoutRow() {
								{
									dataLayoutColumns = new DataLayoutColumn[] {
										new DataLayoutColumn() {
											{
												columnSize = 12;
												fieldNames = new String[] {
													"textName"
												};
											}
										}
									};
								}
							}
						};
						description = HashMapBuilder.<String, Object>put(
							"en_US", "Description"
						).build();
						title = HashMapBuilder.<String, Object>put(
							"en_US", "Title"
						).build();
					}
				}
			});
		dataLayout.setPaginationMode("wizard");

		DDMFormRuleDeserializer ddmFormRuleDeserializer = Mockito.mock(
			DDMFormRuleDeserializer.class);

		Mockito.when(
			ddmFormRuleDeserializer.deserialize(Mockito.any(), Mockito.any())
		).thenReturn(
			new ArrayList<>()
		);

		Assert.assertEquals(
			ddmFormLayout,
			DataLayoutUtil.toDDMFormLayout(
				dataLayout, ddmForm,
				Mockito.mock(DDMFormFieldTypeServicesRegistry.class),
				ddmFormRuleDeserializer));
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		Mockito.when(
			_language.getAvailableLocales()
		).thenReturn(
			SetUtil.fromArray(LocaleUtil.US)
		);

		Mockito.when(
			_language.isAvailableLocale(Mockito.eq(LocaleUtil.US))
		).thenReturn(
			true
		);

		Mockito.when(
			_language.isAvailableLocale(
				Mockito.eq(LocaleUtil.toLanguageId(LocaleUtil.US)))
		).thenReturn(
			true
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private final Language _language = Mockito.mock(Language.class);

}