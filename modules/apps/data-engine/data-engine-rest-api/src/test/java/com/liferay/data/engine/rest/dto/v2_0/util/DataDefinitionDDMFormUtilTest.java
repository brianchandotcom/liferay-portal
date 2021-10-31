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

package com.liferay.data.engine.rest.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.util.SettingsDDMFormFieldsUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mateus Santana
 */
@PrepareForTest({LocaleUtil.class, SettingsDDMFormFieldsUtil.class})
@RunWith(PowerMockRunner.class)
public class DataDefinitionDDMFormUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLocaleUtil();
		_setUpSettingsDDMFormFieldsUtil();
	}

	@Test
	public void testToDDMFormEquals() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(LocaleUtil.BRAZIL, LocaleUtil.US), LocaleUtil.US);

		Locale defaultLocale = ddmForm.getDefaultLocale();

		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setIndexType("text");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label1"
							).put(
								"pt_BR", "rótulo1"
							).build(),
							defaultLocale));
					setLocalizable(true);
					setName("name1");
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "enter a text"
							).put(
								"pt_BR", "insira um texto"
							).build(),
							defaultLocale));
					setReadOnly(true);
					setRepeatable(true);
					setRequired(true);
					setShowLabel(true);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip1"
							).put(
								"pt_BR", "ajuda1"
							).build(),
							defaultLocale));
					setType("text");
				}
			});
		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setDDMFormFieldOptions(
						new DDMFormFieldOptions() {
							{
								addOptionLabel(
									"valor", LocaleUtil.BRAZIL, "rótulo");
								addOptionLabel("value", LocaleUtil.US, "label");
								addOptionReference("valor", "referência");
								addOptionReference("value", "reference");
								setDefaultLocale(LocaleUtil.US);
							}
						});
					setIndexType("keyword");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label2"
							).put(
								"pt_BR", "rótulo2"
							).build(),
							defaultLocale));
					setName("name2");
					setLocalizable(false);
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", new Object[] {"select an option"}
							).put(
								"pt_BR", new Object[] {"selecione uma opção"}
							).build(),
							defaultLocale));
					setReadOnly(false);
					setRepeatable(false);
					setRequired(false);
					setShowLabel(false);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip2"
							).put(
								"pt_BR", "ajuda2"
							).build(),
							defaultLocale));
					setType("select");
				}
			});

		Assert.assertEquals(
			ddmForm,
			DataDefinitionDDMFormUtil.toDDMForm(
				new DataDefinition() {
					{
						setAvailableLanguageIds(
							new String[] {"en_US", "pt_BR"});
						setDataDefinitionFields(_getDataDefinitionFields());
						setDefaultLanguageId("en_US");
					}
				},
				null));
	}

	@Test
	public void testToDDMFormWithEmptyDataDefinition() {
		DDMForm ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
			new DataDefinition(), null);

		Assert.assertTrue(SetUtil.isEmpty(ddmForm.getAvailableLocales()));
		Assert.assertTrue(ListUtil.isEmpty(ddmForm.getDDMFormFields()));
		Assert.assertNull(LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));
	}

	@Test
	public void testToDDMFormWithNullDataDefinition() {
		Assert.assertEquals(
			new DDMForm(), DataDefinitionDDMFormUtil.toDDMForm(null, null));
	}

	private DataDefinitionField[] _getDataDefinitionFields() {
		return new DataDefinitionField[] {
			new DataDefinitionField() {
				{
					setDefaultValue(
						HashMapBuilder.<String, Object>put(
							"en_US", "enter a text"
						).put(
							"pt_BR", "insira um texto"
						).build());
					setFieldType("text");
					setIndexType(IndexType.TEXT);
					setLabel(
						HashMapBuilder.<String, Object>put(
							"en_US", "label1"
						).put(
							"pt_BR", "rótulo1"
						).build());
					setLocalizable(true);
					setName("name1");
					setReadOnly(true);
					setRepeatable(true);
					setRequired(true);
					setShowLabel(true);
					setTip(
						HashMapBuilder.<String, Object>put(
							"en_US", "tip1"
						).put(
							"pt_BR", "ajuda1"
						).build());
				}
			},
			new DataDefinitionField() {
				{
					setCustomProperties(
						HashMapBuilder.<String, Object>put(
							"options",
							HashMapBuilder.<String, Object>put(
								"en_US",
								Collections.singletonList(
									JSONUtil.put(
										"label", "label"
									).put(
										"reference", "reference"
									).put(
										"value", "value"
									))
							).put(
								"pt_BR",
								new Map[] {
									HashMapBuilder.<String, Object>put(
										"label", "rótulo"
									).put(
										"reference", "referência"
									).put(
										"value", "valor"
									).build()
								}
							).build()
						).build());
					setDefaultValue(
						HashMapBuilder.<String, Object>put(
							"en_US", new Object[] {"select an option"}
						).put(
							"pt_BR", new Object[] {"selecione uma opção"}
						).build());
					setFieldType("select");
					setIndexType(IndexType.KEYWORD);
					setLabel(
						HashMapBuilder.<String, Object>put(
							"en_US", "label2"
						).put(
							"pt_BR", "rótulo2"
						).build());
					setName("name2");
					setLocalizable(false);
					setReadOnly(false);
					setRepeatable(false);
					setRequired(false);
					setShowLabel(false);
					setTip(
						HashMapBuilder.<String, Object>put(
							"en_US", "tip2"
						).put(
							"pt_BR", "ajuda2"
						).build());
				}
			}
		};
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);
	}

	private void _setUpSettingsDDMFormFieldsUtil() {
		mockStatic(SettingsDDMFormFieldsUtil.class);

		when(
			SettingsDDMFormFieldsUtil.getSettingsDDMFormFields(
				Matchers.any(DDMFormFieldTypeServicesTracker.class),
				Matchers.eq("select"))
		).thenReturn(
			HashMapBuilder.put(
				"options",
				() -> {
					DDMFormField ddmFormField = new DDMFormField();

					ddmFormField.setDataType("ddm-options");

					return ddmFormField;
				}
			).build()
		);
	}

}