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

package com.liferay.layout.page.template.internal.headless.delivery.dto.v1_0;

import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageDefinitionConverter;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.structure.LayoutStructureItemExportHelper;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.structure.LayoutStructureItemExportHelperTracker;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageDefinitionConverter.class)
public class PageDefinitionConverterImpl implements PageDefinitionConverter {

	@Override
	public PageDefinition toPageDefinition(Layout layout) {
		return toPageDefinition(layout, true, true);
	}

	@Override
	public PageDefinition toPageDefinition(
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		return new PageDefinition() {
			{
				pageElement = _toPageElement(
					layout, saveInlineContent, saveMappingConfiguration, 0);
				settings = _toSettings(layout);
			}
		};
	}

	@Override
	public PageElement toPageElement(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					_toPageElement(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration,
						segmentsExperienceId));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private PageElement _toPageElement(
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0L));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<PageElement> mainPageElements = new ArrayList<>();

		for (String childItemId :
				mainLayoutStructureItem.getChildrenItemIds()) {

			mainPageElements.add(
				toPageElement(
					layout.getGroupId(), layoutStructure,
					layoutStructure.getLayoutStructureItem(childItemId),
					saveInlineContent, saveMappingConfiguration,
					segmentsExperienceId));
		}

		PageElement pageElement = _toPageElement(
			layout.getGroupId(), mainLayoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!mainPageElements.isEmpty()) {
			pageElement.setPageElements(
				mainPageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private PageElement _toPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemExportHelper layoutStructureItemExportHelper =
			_layoutStructureItemExportHelperTracker.
				getLayoutStructureItemExportHelper(clazz.getName());

		if (layoutStructureItemExportHelper == null) {
			return null;
		}

		return layoutStructureItemExportHelper.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
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
		PageDefinitionConverterImpl.class);

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemExportHelperTracker
		_layoutStructureItemExportHelperTracker;

	@Reference
	private Portal _portal;

}