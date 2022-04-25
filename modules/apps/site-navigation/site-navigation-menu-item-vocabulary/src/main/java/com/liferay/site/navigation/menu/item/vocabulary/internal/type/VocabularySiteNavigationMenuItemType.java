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
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.Locale;
import java.util.Map;

import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeContext;
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
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "vocabulary");
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

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}