/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mateus Santana
 */
public class DataDefinitionDDMFormUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(Mockito.mock(Portal.class));

		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any())
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));

		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
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
					setLocalizable(false);
					setName("name2");
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
						availableLanguageIds = new String[] {"en_US", "pt_BR"};
						dataDefinitionFields = _getDataDefinitionFields();
						defaultLanguageId = "en_US";
					}
				},
				_ddmFormFieldTypeServicesRegistry));
	}

	@Test
	public void testToDDMFormWithEmptyDataDefinition() {
		DDMForm ddmForm = DataDefinitionDDMFormUtil.toDDMForm(
			new DataDefinition(), null);

		Assert.assertTrue(SetUtil.isEmpty(ddmForm.getAvailableLocales()));
		Assert.assertTrue(ListUtil.isEmpty(ddmForm.getDDMFormFields()));
		Assert.assertEquals(
			"en_US", LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()));
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
					defaultValue = HashMapBuilder.<String, Object>put(
						"en_US", "enter a text"
					).put(
						"pt_BR", "insira um texto"
					).build();
					fieldType = "text";
					indexType = IndexType.TEXT;
					label = HashMapBuilder.<String, Object>put(
						"en_US", "label1"
					).put(
						"pt_BR", "rótulo1"
					).build();
					localizable = true;
					name = "name1";
					readOnly = true;
					repeatable = true;
					required = true;
					showLabel = true;
					tip = HashMapBuilder.<String, Object>put(
						"en_US", "tip1"
					).put(
						"pt_BR", "ajuda1"
					).build();
				}
			},
			new DataDefinitionField() {
				{
					customProperties = HashMapBuilder.<String, Object>put(
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
					).build();
					defaultValue = HashMapBuilder.<String, Object>put(
						"en_US", new Object[] {"select an option"}
					).put(
						"pt_BR", new Object[] {"selecione uma opção"}
					).build();
					fieldType = "select";
					indexType = IndexType.KEYWORD;
					label = HashMapBuilder.<String, Object>put(
						"en_US", "label2"
					).put(
						"pt_BR", "rótulo2"
					).build();
					localizable = false;
					name = "name2";
					readOnly = false;
					repeatable = false;
					required = false;
					showLabel = false;
					tip = HashMapBuilder.<String, Object>put(
						"en_US", "tip2"
					).put(
						"pt_BR", "ajuda2"
					).build();
				}
			}
		};
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		_whenLanguageIsAvailableLocale(LocaleUtil.BRAZIL);
		_whenLanguageIsAvailableLocale(LocaleUtil.US);

		languageUtil.setLanguage(_language);
	}

	private void _setUpSettingsDDMFormFieldsUtil() {
		DDMFormFieldType ddmFormFieldType = Mockito.mock(
			DDMFormFieldType.class);

		Mockito.doReturn(
			ddmFormFieldType
		).when(
			_ddmFormFieldTypeServicesRegistry
		).getDDMFormFieldType(
			"select"
		);

		Mockito.doReturn(
			TestTypeSettings.class
		).when(
			ddmFormFieldType
		).getDDMFormFieldTypeSettings();
	}

	private void _whenLanguageIsAvailableLocale(Locale locale) {
		Mockito.when(
			_language.isAvailableLocale(Mockito.eq(locale))
		).thenReturn(
			true
		);

		Mockito.when(
			_language.isAvailableLocale(
				Mockito.eq(LocaleUtil.toLanguageId(locale)))
		).thenReturn(
			true
		);
	}

	private final DDMFormFieldTypeServicesRegistry
		_ddmFormFieldTypeServicesRegistry = Mockito.mock(
			DDMFormFieldTypeServicesRegistry.class);
	private final Language _language = Mockito.mock(Language.class);

	@com.liferay.dynamic.data.mapping.annotations.DDMForm
	private interface TestTypeSettings extends DDMFormFieldTypeSettings {

		@com.liferay.dynamic.data.mapping.annotations.DDMFormField(
			dataType = "ddm-options"
		)
		public String options();

	}

}