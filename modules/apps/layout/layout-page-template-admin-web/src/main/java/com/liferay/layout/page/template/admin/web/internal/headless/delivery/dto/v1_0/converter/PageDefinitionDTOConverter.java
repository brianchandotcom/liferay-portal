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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionDTOConverter {

	public static PageDefinition toDTO(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout) {

		return toDTO(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, true, true);
	}

	public static PageDefinition toDTO(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		return toDTO(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, saveInlineContent,
			saveMappingConfiguration, 0);
	}

	public static PageDefinition toDTO(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		return new PageDefinition() {
			{
				pageElement = PageElementDTOConverter.toDTO(
					fragmentCollectionContributorTracker,
					fragmentEntryConfigurationParser, fragmentRendererTracker,
					infoDisplayContributorTracker, layout, saveInlineContent,
					saveMappingConfiguration);
				settings = _toSettings(layout);
			}
		};
	}

	private static Settings _toSettings(Layout layout) {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		return new Settings() {
			{
				setColorSchemeName(
					() -> {
						ColorScheme colorScheme = null;

						try {
							colorScheme = layout.getColorScheme();
						}
						catch (PortalException portalException) {
							if (_log.isWarnEnabled()) {
								_log.warn(portalException, portalException);
							}
						}

						if (colorScheme == null) {
							return null;
						}

						return colorScheme.getName();
					});
				setCss(
					() -> {
						if (Validator.isNull(layout.getCss())) {
							return null;
						}

						return layout.getCss();
					});
				setJavascript(
					() -> {
						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.equals("javascript")) {
								return entry.getValue();
							}
						}

						return null;
					});
				setMasterPage(
					() -> {
						LayoutPageTemplateEntry layoutPageTemplateEntry =
							LayoutPageTemplateEntryLocalServiceUtil.
								fetchLayoutPageTemplateEntryByPlid(
									layout.getMasterLayoutPlid());

						if (layoutPageTemplateEntry == null) {
							return null;
						}

						return new MasterPage() {
							{
								key =
									layoutPageTemplateEntry.
										getLayoutPageTemplateEntryKey();
							}
						};
					});
				setThemeName(
					() -> {
						Theme theme = layout.getTheme();

						if (theme == null) {
							return null;
						}

						return theme.getName();
					});
				setThemeSettings(
					() -> {
						UnicodeProperties themeSettingsUnicodeProperties =
							new UnicodeProperties();

						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.startsWith("lfr-theme:")) {
								themeSettingsUnicodeProperties.setProperty(
									key, entry.getValue());
							}
						}

						if (themeSettingsUnicodeProperties.isEmpty()) {
							return null;
						}

						return themeSettingsUnicodeProperties;
					});
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageDefinitionDTOConverter.class);

}