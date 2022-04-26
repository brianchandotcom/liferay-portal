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

package com.liferay.site.navigation.menu.item.asset.vocabulary.internal.type;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.vocabulary.item.selector.AssetVocabularyItemSelectorReturnType;
import com.liferay.asset.vocabulary.item.selector.criterion.AssetVocabularyItemSelectorCriterion;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.menu.item.asset.vocabulary.internal.constants.AssetVocabularySiteNavigationMenuTypeConstants;
import com.liferay.site.navigation.menu.item.asset.vocabulary.internal.display.context.AssetVocabularySiteNavigationMenuTypeDisplayContext;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
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
		"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY
	},
	service = SiteNavigationMenuItemType.class
)
public class AssetVocabularySiteNavigationMenuItemType
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

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(vocabularyId);

		if (assetVocabulary == null) {
			return false;
		}

		siteNavigationMenuItemElement.addAttribute(
			"vocabulary-id", String.valueOf(vocabularyId));

		portletDataContext.addReferenceElement(
			siteNavigationMenuItem, siteNavigationMenuItemElement,
			assetVocabulary, PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			false);

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
			"/navigation_menu" +
				"/add_asset_vocabulary_type_site_navigation_menu_items"
		).setParameter(
			"siteNavigationMenuItemType",
			SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY
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
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.ASSET_VOCABULARY;
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

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(newClassPK);

		if (assetVocabulary == null) {
			return false;
		}

		importedSiteNavigationMenuItem.setTypeSettings(
			UnicodePropertiesBuilder.fastLoad(
				siteNavigationMenuItem.getTypeSettings()
			).put(
				"classPK", String.valueOf(newClassPK)
			).put(
				"groupId", String.valueOf(assetVocabulary.getGroupId())
			).put(
				"title", assetVocabulary.getTitle(LocaleUtil.getSiteDefault())
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
			AssetVocabularySiteNavigationMenuTypeConstants.
				ASSET_VOCABULARY_SITE_NAVIGATION_MENU_TYPE_DISPLAY_CONTEXT,
			new AssetVocabularySiteNavigationMenuTypeDisplayContext(
				httpServletRequest, _itemSelector, siteNavigationMenuItem));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_asset_vocabulary_type.jsp");
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.asset.vocabulary)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}