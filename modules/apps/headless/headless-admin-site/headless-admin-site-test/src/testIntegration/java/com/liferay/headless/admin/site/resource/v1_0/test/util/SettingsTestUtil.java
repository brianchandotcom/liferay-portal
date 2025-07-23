/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.Settings;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Lourdes Fernández Besada
 */
public class SettingsTestUtil {

	public static void assertPageSpecificationSetting(
			Layout layout, Settings settings)
		throws Exception {

		if (Validator.isNull(layout.getColorSchemeId())) {
			Assert.assertTrue(Validator.isNull(settings.getColorSchemeName()));
		}
		else {
			Assert.assertEquals(
				layout.getColorSchemeId(), settings.getColorSchemeName());
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

		ItemExternalReference masterPageItemExternalReference =
			settings.getMasterPageItemExternalReference();

		if (layout.getMasterLayoutPlid() == 0) {
			Assert.assertNull(masterPageItemExternalReference);
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			Assert.assertEquals(
				layoutPageTemplateEntry.getExternalReferenceCode(),
				masterPageItemExternalReference.getExternalReferenceCode());
		}

		ItemExternalReference styleBookItemExternalReference =
			settings.getStyleBookItemExternalReference();

		if (layout.getStyleBookEntryId() == 0) {
			Assert.assertNull(styleBookItemExternalReference);
		}
		else {
			StyleBookEntry styleBookEntry =
				StyleBookEntryLocalServiceUtil.getStyleBookEntry(
					layout.getStyleBookEntryId());

			Assert.assertEquals(
				styleBookEntry.getExternalReferenceCode(),
				styleBookItemExternalReference.getExternalReferenceCode());
		}

		if (Validator.isNull(layout.getThemeId())) {
			Assert.assertTrue(Validator.isNull(settings.getThemeName()));
		}
		else {
			Assert.assertEquals(layout.getThemeId(), settings.getThemeName());
		}

		UnicodeProperties themeSettingsUnicodeProperties =
			_getThemeSettingsUnicodeProperties(unicodeProperties);

		if (themeSettingsUnicodeProperties.isEmpty()) {
			Assert.assertNull(settings.getThemeSettings());
		}
		else {
			Map<String, String> themeSettings = settings.getThemeSettings();

			Assert.assertEquals(
				MapUtil.toString(themeSettings),
				themeSettingsUnicodeProperties.size(), themeSettings.size());

			for (Map.Entry<String, String> entry :
					themeSettingsUnicodeProperties.entrySet()) {

				Assert.assertEquals(
					entry.getValue(), themeSettings.get(entry.getKey()));
			}
		}
	}

	public static void assertSettings(
		Settings expectedSettings, Settings actualSettings) {

		if (expectedSettings == null) {
			Assert.assertNull(actualSettings);

			return;
		}

		Assert.assertEquals(
			expectedSettings.getColorSchemeName(),
			actualSettings.getColorSchemeName());
		Assert.assertEquals(expectedSettings.getCss(), actualSettings.getCss());
		Assert.assertEquals(
			expectedSettings.getJavascript(), actualSettings.getJavascript());

		Assert.assertTrue(
			Objects.deepEquals(
				expectedSettings.getMasterPageItemExternalReference(),
				actualSettings.getMasterPageItemExternalReference()));

		Assert.assertTrue(
			Objects.deepEquals(
				expectedSettings.getStyleBookItemExternalReference(),
				actualSettings.getStyleBookItemExternalReference()));

		Assert.assertEquals(
			expectedSettings.getThemeName(), actualSettings.getThemeName());

		Map<String, String> themeSettings = expectedSettings.getThemeSettings();
		Map<String, String> curThemeSettings =
			actualSettings.getThemeSettings();

		if (MapUtil.isEmpty(themeSettings)) {
			Assert.assertTrue(
				MapUtil.toString(curThemeSettings),
				MapUtil.isEmpty(curThemeSettings));

			return;
		}

		Assert.assertEquals(
			MapUtil.toString(curThemeSettings), themeSettings,
			curThemeSettings);
	}

	public static void modifySettings(
			ServiceContext serviceContext, Settings settings)
		throws Exception {

		if (Validator.isNotNull(settings.getJavascript())) {
			settings.setJavascript(() -> null);
		}
		else {
			settings.setJavascript(RandomTestUtil::randomString);
		}

		if (settings.getMasterPageItemExternalReference() != null) {
			settings.setMasterPageItemExternalReference(() -> null);
		}
		else {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryTestUtil.
					getMasterLayoutPageTemplateEntry(
						serviceContext, WorkflowConstants.STATUS_APPROVED);

			settings.setMasterPageItemExternalReference(
				() -> new ItemExternalReference() {
					{
						setExternalReferenceCode(
							layoutPageTemplateEntry::getExternalReferenceCode);
					}
				});
		}

		if (settings.getStyleBookItemExternalReference() != null) {
			settings.setStyleBookItemExternalReference(() -> null);
		}
		else {
			StyleBookEntry styleBookEntry =
				StyleBookEntryLocalServiceUtil.addStyleBookEntry(
					null, TestPropsValues.getUserId(),
					serviceContext.getScopeGroupId(), false, null,
					RandomTestUtil.randomString(), null,
					RandomTestUtil.randomString(), serviceContext);

			settings.setStyleBookItemExternalReference(
				() -> new ItemExternalReference() {
					{
						setExternalReferenceCode(
							styleBookEntry::getExternalReferenceCode);
					}
				});
		}

		if (Validator.isNotNull(settings.getThemeName())) {
			settings.setColorSchemeName(() -> null);
			settings.setThemeName(() -> null);
		}
		else {
			if (RandomTestUtil.randomBoolean()) {
				settings.setColorSchemeName("01");
			}

			settings.setThemeName("classic_WAR_classictheme");
		}

		if (Validator.isNotNull(settings.getThemeSettings())) {
			settings.setThemeSettings(() -> null);
		}
		else {
			settings.setThemeSettings(
				() -> HashMapBuilder.put(
					"lfr-theme:regular:show-maximize-minimize-application-" +
						"links",
					"true"
				).build());
		}
	}

	public static Settings getSettings(ServiceContext serviceContext) {
		return new Settings() {
			{
				setColorSchemeName(() -> "01");
				setCss(RandomTestUtil::randomString);
				setJavascript(RandomTestUtil::randomString);
				setMasterPageItemExternalReference(
					() -> _getMasterPageItemExternalReference(serviceContext));
				setStyleBookItemExternalReference(
					() -> _getStyleBookItemExternalReference(serviceContext));
				setThemeName(() -> "classic_WAR_classictheme");
				setThemeSettings(
					() -> TreeMapBuilder.put(
						"lfr-theme:" + RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					).put(
						"lfr-theme:" + RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					).build());
			}
		};
	}

	private static ItemExternalReference _getMasterPageItemExternalReference(
			ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryTestUtil.getMasterLayoutPageTemplateEntry(
				serviceContext, WorkflowConstants.STATUS_APPROVED);

		return new ItemExternalReference() {
			{
				setExternalReferenceCode(
					layoutPageTemplateEntry::getExternalReferenceCode);
			}
		};
	}

	private static ItemExternalReference _getStyleBookItemExternalReference(
			ServiceContext serviceContext)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			StyleBookEntryLocalServiceUtil.addStyleBookEntry(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), false, null,
				RandomTestUtil.randomString(), null,
				RandomTestUtil.randomString(), serviceContext);

		return new ItemExternalReference() {
			{
				setExternalReferenceCode(
					styleBookEntry::getExternalReferenceCode);
			}
		};
	}

	private static UnicodeProperties _getThemeSettingsUnicodeProperties(
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

}