/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.client.dto.v1_0.Settings;
import com.liferay.headless.admin.site.client.dto.v1_0.StyleBook;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageSpecification;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageSpecificationResourceTest
	extends BasePageSpecificationResourceTestCase {

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodePageSpecification()
		throws Exception {

		_testGetSiteSiteByExternalReferenceCodePageSpecification(
			LayoutTestUtil.addTypePortletLayout(testGroup));
	}

	private void _assertPageSpecificationSetting(
			Layout layout, Settings settings)
		throws Exception {

		if (Validator.isNull(layout.getThemeId()) ||
			Validator.isNull(layout.getThemeId())) {

			Assert.assertTrue(Validator.isNull(settings.getColorSchemeName()));
		}
		else {
			ColorScheme colorScheme = _themeLocalService.getColorScheme(
				layout.getCompanyId(), layout.getThemeId(),
				layout.getColorSchemeId());

			Assert.assertEquals(
				colorScheme.getName(), settings.getColorSchemeName());
		}

		if (Validator.isNull(layout.getCss())) {
			Assert.assertTrue(Validator.isNull(settings.getCss()));
		}
		else {
			Assert.assertEquals(layout.getCss(), settings.getCss());
		}

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		Assert.assertEquals(
			unicodeProperties.getProperty("javascript", null),
			settings.getJavascript());

		if (layout.getMasterLayoutPlid() == 0) {
			Assert.assertNull(settings.getMasterPageExternalReferenceCode());
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			Assert.assertEquals(
				layoutPageTemplateEntry.getExternalReferenceCode(),
				settings.getMasterPageExternalReferenceCode());
		}

		if (layout.getStyleBookEntryId() == 0) {
			Assert.assertNull(settings.getStyleBook());
		}
		else {
			StyleBookEntry styleBookEntry =
				_styleBookEntryLocalService.getStyleBookEntry(
					layout.getStyleBookEntryId());

			StyleBook styleBook = settings.getStyleBook();

			Assert.assertEquals(
				styleBookEntry.getStyleBookEntryKey(), styleBook.getKey());
			Assert.assertEquals(styleBookEntry.getName(), styleBook.getName());
		}

		if (Validator.isNull(layout.getThemeId())) {
			Assert.assertTrue(Validator.isNull(settings.getThemeName()));
		}
		else {
			Theme theme = _themeLocalService.getTheme(
				layout.getCompanyId(), layout.getThemeId());

			Assert.assertEquals(theme.getName(), settings.getThemeName());
		}

		UnicodeProperties themeSettingsUnicodeProperties =
			_getThemeSettingsUnicodeProperties(unicodeProperties);

		if (themeSettingsUnicodeProperties.size() == 0) {
			Assert.assertNull(settings.getThemeSettings());
		}
		else {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				(String)settings.getThemeSettings());

			Assert.assertEquals(
				themeSettingsUnicodeProperties.size(), jsonObject.length());

			for (Map.Entry<String, String> entry :
					themeSettingsUnicodeProperties.entrySet()) {

				Assert.assertEquals(
					entry.getValue(), jsonObject.getString(entry.getKey()));
			}
		}
	}

	private void _assertWidgetPageSpecification(
		WidgetPageSpecification widgetPageSpecification) {

		Assert.assertEquals(
			PageSpecification.Type.WIDGET_PAGE_SPECIFICATION,
			widgetPageSpecification.getType());

		Assert.assertNull(widgetPageSpecification.getWidgetPageSections());
	}

	private UnicodeProperties _getThemeSettingsUnicodeProperties(
		UnicodeProperties unicodeProperties) {

		UnicodeProperties themeSettingsUnicodeProperties =
			new UnicodeProperties();

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			String key = entry.getKey();

			if (key.startsWith("lfr-theme:")) {
				themeSettingsUnicodeProperties.setProperty(
					key, entry.getValue());
			}
		}

		return themeSettingsUnicodeProperties;
	}

	private void _testGetSiteSiteByExternalReferenceCodePageSpecification(
			Layout layout)
		throws Exception {

		PageSpecification pageSpecification =
			pageSpecificationResource.
				getSiteSiteByExternalReferenceCodePageSpecification(
					testGroup.getExternalReferenceCode(),
					layout.getExternalReferenceCode());

		Assert.assertEquals(
			layout.getExternalReferenceCode(),
			pageSpecification.getExternalReferenceCode());

		_assertPageSpecificationSetting(
			layout, pageSpecification.getSettings());

		if (layout.isDraftLayout()) {
			Assert.assertEquals(
				PageSpecification.Status.DRAFT, pageSpecification.getStatus());
		}
		else {
			Assert.assertEquals(
				PageSpecification.Status.APPROVED,
				pageSpecification.getStatus());
		}

		_assertWidgetPageSpecification(
			(WidgetPageSpecification)pageSpecification);
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Inject
	private ThemeLocalService _themeLocalService;

}