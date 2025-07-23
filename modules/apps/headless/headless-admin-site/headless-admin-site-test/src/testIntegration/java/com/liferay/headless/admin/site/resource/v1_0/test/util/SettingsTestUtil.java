/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.Settings;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
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

import java.util.HashMap;
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

	public static Settings getColorSchemeNameSettings(Settings settings) {
		if (settings.getColorSchemeName() != null) {
			settings.setColorSchemeName(() -> null);

			return new Settings() {
				{
					setColorSchemeName(() -> StringPool.BLANK);
				}
			};
		}

		settings.setColorSchemeName(() -> "01");
		settings.setThemeName(() -> "classic_WAR_classictheme");

		return new Settings() {
			{
				setColorSchemeName(() -> "01");
				setThemeName(() -> "classic_WAR_classictheme");
			}
		};
	}

	public static Settings getCssSettings(Settings settings) {
		if (settings.getCss() != null) {
			settings.setCss(() -> null);

			return new Settings() {
				{
					setCss(() -> StringPool.BLANK);
				}
			};
		}

		String css = RandomTestUtil.randomString();

		settings.setCss(() -> css);

		return new Settings() {
			{
				setCss(() -> css);
			}
		};
	}

	public static Settings getJavaScriptSettings(Settings settings) {
		if (settings.getJavascript() != null) {
			settings.setJavascript(() -> null);

			return new Settings() {
				{
					setJavascript(() -> StringPool.BLANK);
				}
			};
		}

		String javaScript = RandomTestUtil.randomString();

		settings.setJavascript(() -> javaScript);

		return new Settings() {
			{
				setJavascript(() -> javaScript);
			}
		};
	}

	public static Settings getMasterPageItemExternalReferenceSettings(
			ServiceContext serviceContext, Settings settings)
		throws Exception {

		if (settings.getMasterPageItemExternalReference() != null) {
			settings.setMasterPageItemExternalReference(() -> null);

			return new Settings() {
				{
					setMasterPageItemExternalReference(
						() -> new ItemExternalReference() {
							{
								setExternalReferenceCode(
									() -> StringPool.BLANK);
							}
						});
				}
			};
		}

		ItemExternalReference itemExternalReference =
			_getMasterPageItemExternalReference(serviceContext);

		settings.setMasterPageItemExternalReference(
			() -> itemExternalReference);

		return new Settings() {
			{
				setMasterPageItemExternalReference(() -> itemExternalReference);
			}
		};
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

	public static Settings getStyleBookItemExternalReferenceSettings(
			ServiceContext serviceContext, Settings settings)
		throws Exception {

		if (settings.getStyleBookItemExternalReference() != null) {
			settings.setStyleBookItemExternalReference(() -> null);

			return new Settings() {
				{
					setStyleBookItemExternalReference(
						() -> new ItemExternalReference() {
							{
								setExternalReferenceCode(
									() -> StringPool.BLANK);
							}
						});
				}
			};
		}

		ItemExternalReference itemExternalReference =
			_getStyleBookItemExternalReference(serviceContext);

		settings.setStyleBookItemExternalReference(() -> itemExternalReference);

		return new Settings() {
			{
				setStyleBookItemExternalReference(() -> itemExternalReference);
			}
		};
	}

	public static Settings getThemeNameSettings(Settings settings) {
		if (settings.getThemeName() != null) {
			settings.setColorSchemeName(() -> null);
			settings.setThemeName(() -> null);

			return new Settings() {
				{
					setColorSchemeName(() -> StringPool.BLANK);
					setThemeName(() -> StringPool.BLANK);
				}
			};
		}

		settings.setThemeName(() -> "classic_WAR_classictheme");

		return new Settings() {
			{
				setThemeName(() -> "classic_WAR_classictheme");
			}
		};
	}

	public static Settings getThemeSettingsSettings(Settings settings) {
		if (settings.getThemeSettings() != null) {
			settings.setThemeSettings(() -> null);

			return new Settings() {
				{
					setThemeSettings(() -> new HashMap<>());
				}
			};
		}

		Map<String, String> themSettings = TreeMapBuilder.put(
			"lfr-theme:" + RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		).put(
			"lfr-theme:" + RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		).build();

		settings.setThemeSettings(() -> themSettings);

		return new Settings() {
			{
				setThemeSettings(() -> themSettings);
			}
		};
	}

	public static Settings getModifiedSettings(
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

		return settings;
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