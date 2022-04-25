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

package com.liferay.site.navigation.menu.item.vocabulary.internal.type;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.vocabulary.item.selector.AssetVocabularyItemSelectorReturnType;
import com.liferay.asset.vocabulary.item.selector.criterion.AssetVocabularyItemSelectorCriterion;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.menu.item.vocabulary.internal.constants.VocabularySiteNavigationMenuTypeConstants;
import com.liferay.site.navigation.menu.item.vocabulary.internal.display.context.VocabularySiteNavigationMenuTypeDisplayContext;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeContext;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=600",
		"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.VOCABULARY
	},
	service = SiteNavigationMenuItemType.class
)
public class VocabularySiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public boolean exportData(
		PortletDataContext portletDataContext,
		Element siteNavigationMenuItemElement,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		long vocabularyId = GetterUtil.getLong(
			typeSettingsUnicodeProperties.get("classPK"));

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(vocabularyId);

		if (vocabulary == null) {
			return false;
		}

		siteNavigationMenuItemElement.addAttribute(
			"vocabulary-id", String.valueOf(vocabularyId));

		portletDataContext.addReferenceElement(
			siteNavigationMenuItem, siteNavigationMenuItemElement, vocabulary,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		return true;
	}

	@Override
	public String getAddTitle(Locale locale) {
		return LanguageUtil.format(locale, "select-x", "vocabularies");
	}

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			"/navigation_menu/add_vocabularies_type_site_navigation_menu_items"
		).setParameter(
			"siteNavigationMenuItemType",
			SiteNavigationMenuItemTypeConstants.VOCABULARY
		).buildPortletURL();
	}

	@Override
	public String getIcon() {
		return "vocabulary";
	}

	@Override
	public String getItemSelectorURL(HttpServletRequest httpServletRequest) {
		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		AssetVocabularyItemSelectorCriterion
			assetVocabularyItemSelectorCriterion =
				new AssetVocabularyItemSelectorCriterion();

		assetVocabularyItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new AssetVocabularyItemSelectorReturnType());
		assetVocabularyItemSelectorCriterion.
			setIncludeAncestorSiteAndDepotGroupIds(true);
		assetVocabularyItemSelectorCriterion.setIncludeInternalVocabularies(
			false);
		assetVocabularyItemSelectorCriterion.setMultiSelection(
			isMultiSelection());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
				renderResponse.getNamespace() + "selectItem",
				assetVocabularyItemSelectorCriterion)
		).setParameter(
			"multipleSelection", isMultiSelection()
		).buildString();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "vocabulary");
	}

	@Override
	public String getName(String typeSettings) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		return typeSettingsUnicodeProperties.get("title");
	}

	@Override
	public String getStatusIcon(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		int numCategories =
			_assetCategoryLocalService.getVocabularyCategoriesCount(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK")));

		if (numCategories > 0) {
			return SiteNavigationMenuItemType.super.getStatusIcon(
				siteNavigationMenuItem);
		}

		return "warning-full";
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).build();

		String defaultTitle = typeSettingsUnicodeProperties.getProperty(
			"title");

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK")));

		String defaultLanguageId = typeSettingsUnicodeProperties.getProperty(
			Field.DEFAULT_LANGUAGE_ID,
			LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale()));

		if (vocabulary != null) {
			defaultTitle = vocabulary.getTitle(defaultLanguageId);
		}

		if (!GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.get("useCustomName"))) {

			return defaultTitle;
		}

		String localizedNames = typeSettingsUnicodeProperties.getProperty(
			"localizedNames", "{}");

		try {
			JSONObject localizedNamesJSONObject =
				JSONFactoryUtil.createJSONObject(localizedNames);

			return localizedNamesJSONObject.getString(
				LocaleUtil.toLanguageId(locale),
				localizedNamesJSONObject.getString(
					defaultLanguageId, defaultTitle));
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to get localizedNamesJSONObject from localizedNames: " +
					localizedNames,
				jsonException);
		}

		return defaultTitle;
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.VOCABULARY;
	}

	@Override
	public boolean importData(
		PortletDataContext portletDataContext,
		SiteNavigationMenuItem siteNavigationMenuItem,
		SiteNavigationMenuItem importedSiteNavigationMenuItem) {

		Element element = portletDataContext.getImportDataElement(
			siteNavigationMenuItem);

		long classPK = GetterUtil.getLong(
			element.attributeValue("vocabulary-id"));

		if (classPK <= 0) {
			return false;
		}

		long newClassPK = MapUtil.getLong(
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class.getName()),
			classPK, classPK);

		AssetVocabulary vocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(newClassPK);

		if (vocabulary == null) {
			return false;
		}

		importedSiteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).put(
				"classPK", String.valueOf(newClassPK)
			).put(
				"groupId", String.valueOf(vocabulary.getGroupId())
			).put(
				"title", vocabulary.getTitle(LocaleUtil.getSiteDefault())
			).put(
				"type", "vocabulary"
			).buildString());

		return true;
	}

	@Override
	public boolean isAvailable(
		SiteNavigationMenuItemTypeContext siteNavigationMenuItemTypeContext) {

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-146502"))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean isItemSelector() {
		return true;
	}

	@Override
	public boolean isMultiSelection() {
		return true;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {
	}

	@Override
	public void renderEditPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		httpServletRequest.setAttribute(
			VocabularySiteNavigationMenuTypeConstants.
				VOCABULARY_SITE_NAVIGATION_MENU_TYPE_DISPLAY_CONTEXT,
			new VocabularySiteNavigationMenuTypeDisplayContext(
				httpServletRequest, _itemSelector, siteNavigationMenuItem));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_vocabulary.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VocabularySiteNavigationMenuItemType.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.vocabulary)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}