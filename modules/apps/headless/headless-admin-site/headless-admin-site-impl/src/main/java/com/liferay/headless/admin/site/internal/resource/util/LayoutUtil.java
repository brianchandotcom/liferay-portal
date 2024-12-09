/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.headless.admin.site.dto.v1_0.Settings;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutUtil {

	public static Layout updateLayout(
			ContentPageSpecification contentPageSpecification, Layout layout,
			ServiceContext serviceContext)
		throws Exception {

		updateLayout(
			layout, contentPageSpecification.getSettings(), serviceContext);

		_updatePageExperiences(
			layout, contentPageSpecification.getPageExperiences());

		return LayoutLocalServiceUtil.updateStatus(
			serviceContext.getUserId(), layout.getPlid(),
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	public static Layout updateLayout(
			Layout layout, Settings settings, ServiceContext serviceContext)
		throws Exception {

		layout = _updateLookAndFeel(layout, settings);

		return LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), layout.getNameMap(),
			layout.getTitleMap(), layout.getDescriptionMap(),
			layout.getKeywordsMap(), layout.getRobotsMap(), layout.getType(),
			layout.isHidden(), layout.getFriendlyURLMap(),
			layout.getIconImage(), null, _getStyleBookEntryId(layout, settings),
			_getFaviconFileEntryId(layout, settings),
			_getMasterLayoutPlid(layout, settings), serviceContext);
	}

	private static void _addChildPageElements(
		LayoutStructure layoutStructure, PageElement pageElement) {

		if (ArrayUtil.isEmpty(pageElement.getPageElements())) {
			return;
		}

		for (PageElement childPageElement : pageElement.getPageElements()) {
			if ((childPageElement.getParentExternalReferenceCode() != null) &&
				!Objects.equals(
					childPageElement.getParentExternalReferenceCode(),
					pageElement.getExternalReferenceCode())) {

				throw new UnsupportedOperationException();
			}

			layoutStructure.addLayoutStructureItem(
				childPageElement.getExternalReferenceCode(),
				_externalToInternalValuesMap.get(childPageElement.getType()),
				pageElement.getExternalReferenceCode(),
				GetterUtil.getInteger(childPageElement.getPosition(), -1));

			_addChildPageElements(layoutStructure, childPageElement);
		}
	}

	private static long _getFaviconFileEntryId(Layout layout, Settings settings)
		throws Exception {

		if ((settings == null) || (settings.getFavIcon() == null) ||
			!(settings.getFavIcon() instanceof ItemExternalReference)) {

			return 0;
		}

		ItemExternalReference itemExternalReference =
			(ItemExternalReference)settings.getFavIcon();

		if (Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		long groupId = layout.getGroupId();

		Scope scope = itemExternalReference.getScope();

		if (scope != null) {
			groupId = GroupUtil.getGroupId(
				true, true, layout.getCompanyId(),
				scope.getExternalReferenceCode());
		}

		DLFileEntry dlFileEntry =
			DLFileEntryServiceUtil.fetchFileEntryByExternalReferenceCode(
				groupId, itemExternalReference.getExternalReferenceCode());

		if (dlFileEntry == null) {
			throw new UnsupportedOperationException();
		}

		return dlFileEntry.getFileEntryId();
	}

	private static long _getMasterLayoutPlid(Layout layout, Settings settings)
		throws Exception {

		if (settings == null) {
			return 0;
		}

		ItemExternalReference itemExternalReference =
			settings.getMasterPageItemExternalReference();

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if ((layoutPageTemplateEntry != null) &&
			Objects.equals(
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(),
					layout.getGroupId());

		if ((layoutPageTemplateEntry == null) ||
			!Objects.equals(
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
				layoutPageTemplateEntry.getType())) {

			throw new UnsupportedOperationException();
		}

		return layoutPageTemplateEntry.getPlid();
	}

	private static long _getStyleBookEntryId(Layout layout, Settings settings)
		throws Exception {

		if (settings == null) {
			return 0;
		}

		ItemExternalReference itemExternalReference =
			settings.getStyleBookItemExternalReference();

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		StyleBookEntry styleBookEntry =
			StyleBookEntryServiceUtil.getStyleBookEntryByExternalReferenceCode(
				itemExternalReference.getExternalReferenceCode(),
				layout.getGroupId());

		return styleBookEntry.getStyleBookEntryId();
	}

	private static void _updateLayoutPageTemplateStructureData(
			Layout layout, PageExperience pageExperience,
			SegmentsExperience segmentsExperience)
		throws Exception {

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceId()));

		LayoutStructure newLayoutStructure = new LayoutStructure();

		newLayoutStructure.addRootLayoutStructureItem(
			layoutStructure.getMainItemId());

		for (PageElement pageElement : pageExperience.getPageElements()) {
			newLayoutStructure.addLayoutStructureItem(
				pageElement.getExternalReferenceCode(),
				_externalToInternalValuesMap.get(pageElement.getType()),
				layoutStructure.getMainItemId(),
				GetterUtil.getInteger(pageElement.getPosition(), -1));

			_addChildPageElements(newLayoutStructure, pageElement);
		}

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructureData(
				layoutPageTemplateStructure.getGroupId(),
				layoutPageTemplateStructure.getPlid(),
				segmentsExperience.getSegmentsExperienceId(),
				newLayoutStructure.toString());
	}

	private static Layout _updateLookAndFeel(Layout layout, Settings settings)
		throws Exception {

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		unicodeProperties.setProperty("javascript", settings.getJavascript());

		for (String key : ListUtil.fromCollection(unicodeProperties.keySet())) {
			if (key.startsWith("lfr-theme:")) {
				unicodeProperties.remove(key);
			}
		}

		if (MapUtil.isEmpty(settings.getThemeSettings())) {
			unicodeProperties.putAll(
				(Map<String, ? extends String>)settings.getThemeSettings());
		}

		layout = LayoutServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			unicodeProperties.toString());

		Theme theme = null;
		String themeId = null;

		if (Validator.isNotNull(settings.getThemeName())) {
			for (Theme curTheme :
					ThemeLocalServiceUtil.getThemes(layout.getCompanyId())) {

				if (!Objects.equals(
						curTheme.getName(), settings.getThemeName())) {

					continue;
				}

				theme = curTheme;
				themeId = curTheme.getThemeId();

				break;
			}

			if (themeId == null) {
				throw new UnsupportedOperationException();
			}
		}

		String colorSchemeId = null;

		if (Validator.isNotNull(settings.getColorSchemeName())) {
			if (theme == null) {
				throw new UnsupportedOperationException();
			}

			for (ColorScheme colorScheme : theme.getColorSchemes()) {
				if (!Objects.equals(
						colorScheme.getName(), settings.getColorSchemeName())) {

					continue;
				}

				colorSchemeId = colorScheme.getColorSchemeId();

				break;
			}

			if (colorSchemeId == null) {
				ColorScheme colorScheme =
					ColorSchemeFactoryUtil.getDefaultRegularColorScheme();

				if (Objects.equals(
						colorScheme.getName(), settings.getColorSchemeName())) {

					colorSchemeId = colorScheme.getColorSchemeId();
				}
			}

			if (colorSchemeId == null) {
				throw new UnsupportedOperationException();
			}
		}

		return LayoutServiceUtil.updateLookAndFeel(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			themeId, colorSchemeId, settings.getCss());
	}

	private static void _updatePageExperiences(
			Layout layout, PageExperience[] pageExperiences)
		throws Exception {

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceServiceUtil.getSegmentsExperiences(
				layout.getGroupId(), layout.getPlid(), true);

		if (pageExperiences.length != segmentsExperiences.size()) {
			throw new UnsupportedOperationException();
		}

		Map<String, SegmentsExperience> segmentsExperiencesMap =
			new HashMap<>();

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperiencesMap.put(
				segmentsExperience.getExternalReferenceCode(),
				segmentsExperience);
		}

		for (PageExperience pageExperience : pageExperiences) {
			_updateLayoutPageTemplateStructureData(
				layout, pageExperience,
				segmentsExperiencesMap.get(
					pageExperience.getExternalReferenceCode()));
		}
	}

	private static final Map<PageElement.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			PageElement.Type.COLLECTION,
			LayoutDataItemTypeConstants.TYPE_COLLECTION
		).put(
			PageElement.Type.COLLECTION_ITEM,
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM
		).put(
			PageElement.Type.COLUMN, LayoutDataItemTypeConstants.TYPE_COLUMN
		).put(
			PageElement.Type.CONTAINER,
			LayoutDataItemTypeConstants.TYPE_CONTAINER
		).put(
			PageElement.Type.DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE
		).put(
			PageElement.Type.FORM, LayoutDataItemTypeConstants.TYPE_FORM
		).put(
			PageElement.Type.FRAGMENT, LayoutDataItemTypeConstants.TYPE_FRAGMENT
		).put(
			PageElement.Type.FRAGMENT_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE
		).put(
			PageElement.Type.ROW, LayoutDataItemTypeConstants.TYPE_ROW
		).build();

}