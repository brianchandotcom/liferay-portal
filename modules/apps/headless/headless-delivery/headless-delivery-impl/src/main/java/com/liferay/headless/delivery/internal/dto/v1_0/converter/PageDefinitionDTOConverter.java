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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(service = PageDefinitionDTOConverter.class)
public class PageDefinitionDTOConverter {

	public PageDefinition toDTO(Layout layout) {
		return toDTO(layout, true, true);
	}

	public PageDefinition toDTO(
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0L));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		return new PageDefinition() {
			{
				pageElement = _pageElementDTOConverter.toDTO(
					layout, mainLayoutStructureItem.getItemId(),
					saveInlineContent, saveMappingConfiguration, 0);
				settings = _toSettings(layout);
			}
		};
	}

	private Settings _toSettings(Layout layout) {
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
							_layoutPageTemplateEntryLocalService.
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

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private PageElementDTOConverter _pageElementDTOConverter;

}