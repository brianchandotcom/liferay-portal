/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.search.location;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormLayoutTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcela Cunha
 */
public class SearchLocationDDMFormFieldTypeSettingsTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() {
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateSearchLocationDDMFormFieldTypeSettingsDDMForm() {
		DDMForm ddmForm = DDMFormFactory.create(
			SearchLocationDDMFormFieldTypeSettings.class);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField layoutDDMFormField = ddmFormFieldsMap.get("layout");

		Assert.assertNotNull(layoutDDMFormField);
		Assert.assertNotNull(layoutDDMFormField.getLabel());
		Assert.assertEquals("select", layoutDDMFormField.getType());
		Assert.assertNotNull(layoutDDMFormField.getPredefinedValue());
		Assert.assertEquals(
			"false", layoutDDMFormField.getProperty("showEmptyOption"));

		DDMFormField placeholderDDMFormField = ddmFormFieldsMap.get(
			"placeholder");

		Assert.assertNotNull(placeholderDDMFormField);
		Assert.assertEquals("string", placeholderDDMFormField.getDataType());
		Assert.assertEquals("text", placeholderDDMFormField.getType());

		DDMFormField redirectButtonDDMFormField = ddmFormFieldsMap.get(
			"redirectButton");

		Assert.assertNotNull(redirectButtonDDMFormField);
		Assert.assertEquals(
			"redirect_button", redirectButtonDDMFormField.getType());
		Assert.assertEquals(
			"/configuration_admin/view_configuration_screen",
			((Object[])redirectButtonDDMFormField.getProperty(
				"mvcRenderCommandName"))[0]);
		Assert.assertEquals(
			"configurationScreenKey=google-places-site-settings",
			((Object[])redirectButtonDDMFormField.getProperty("parameters"))
				[0]);
		Assert.assertEquals(
			ConfigurationAdminPortletKeys.SITE_SETTINGS,
			((Object[])redirectButtonDDMFormField.getProperty("portletId"))[0]);

		DDMFormField visibleFieldsDDMFormField = ddmFormFieldsMap.get(
			"visibleFields");

		Assert.assertNotNull(visibleFieldsDDMFormField);
		Assert.assertNotNull(visibleFieldsDDMFormField.getLabel());
		Assert.assertNotNull(
			visibleFieldsDDMFormField.getProperty("initialValue"));
		Assert.assertEquals(
			"multi_language_option_select",
			visibleFieldsDDMFormField.getType());

		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Assert.assertEquals(ddmFormRules.toString(), 2, ddmFormRules.size());

		DDMFormRule ddmFormRule = ddmFormRules.get(0);

		Assert.assertEquals("TRUE", ddmFormRule.getCondition());

		List<String> actions = ddmFormRule.getActions();

		Assert.assertEquals(actions.toString(), 9, actions.size());
		Assert.assertEquals(
			"setVisible('fieldReference', hasGooglePlacesAPIKey())",
			actions.get(0));
		Assert.assertEquals(
			"setVisible('label', hasGooglePlacesAPIKey())", actions.get(1));

		StringBundler sb = new StringBundler(5);

		sb.append("setVisible('layout', hasGooglePlacesAPIKey() AND (contains");
		sb.append("(getValue('visibleFields'), \"city\") OR contains(getValue");
		sb.append("('visibleFields'), \"country\") OR contains(getValue('");
		sb.append("visibleFields'), \"postal-code\") OR contains(getValue('");
		sb.append("visibleFields'), \"state\")))");

		Assert.assertEquals(sb.toString(), actions.get(2));

		Assert.assertEquals(
			"setVisible('placeholder', hasGooglePlacesAPIKey())",
			actions.get(3));
		Assert.assertEquals(
			"setVisible('redirectButton', NOT(hasGooglePlacesAPIKey()))",
			actions.get(4));
		Assert.assertEquals(
			"setVisible('required', hasGooglePlacesAPIKey())", actions.get(5));
		Assert.assertEquals(
			"setVisible('requiredErrorMessage', hasGooglePlacesAPIKey() AND " +
				"getValue('required'))",
			actions.get(6));
		Assert.assertEquals(
			"setVisible('tip', hasGooglePlacesAPIKey())", actions.get(7));
		Assert.assertEquals(
			"setVisible('visibleFields', hasGooglePlacesAPIKey())",
			actions.get(8));

		ddmFormRule = ddmFormRules.get(1);

		Assert.assertEquals(
			"NOT(hasGooglePlacesAPIKey())", ddmFormRule.getCondition());

		actions = ddmFormRule.getActions();

		Assert.assertEquals(actions.toString(), 1, actions.size());
		Assert.assertEquals("jumpPage(0, 2)", actions.get(0));
	}

	@Test
	public void testCreateSearchLocationDDMFormFieldTypeSettingsDDMFormLayout() {
		assertDDMFormLayout(
			DDMFormLayoutFactory.create(
				SearchLocationDDMFormFieldTypeSettings.class),
			DDMFormLayoutTestUtil.createDDMFormLayout(
				DDMFormLayout.TABBED_MODE,
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"label", "placeholder", "tip", "required",
					"requiredErrorMessage", "visibleFields", "layout",
					"redirectButton"),
				DDMFormLayoutTestUtil.createDDMFormLayoutPage(
					"dataType", "name", "fieldReference", "showLabel",
					"repeatable", "readOnly", "rulesActionDisabled",
					"rulesConditionDisabled")));
	}

	@Override
	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

}