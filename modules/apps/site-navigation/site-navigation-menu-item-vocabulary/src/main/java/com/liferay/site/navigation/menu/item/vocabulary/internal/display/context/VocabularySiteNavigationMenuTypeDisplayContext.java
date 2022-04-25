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

package com.liferay.site.navigation.menu.item.vocabulary.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.vocabulary.item.selector.AssetVocabularyItemSelectorReturnType;
import com.liferay.asset.vocabulary.item.selector.criterion.AssetVocabularyItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class VocabularySiteNavigationMenuTypeDisplayContext {

	public VocabularySiteNavigationMenuTypeDisplayContext(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		_httpServletRequest = httpServletRequest;
		_itemSelector = itemSelector;
		_siteNavigationMenuItem = siteNavigationMenuItem;

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_vocabulary = AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
			GetterUtil.getLong(typeSettingsUnicodeProperties.get("classPK")));
	}

	public Map<String, Object> getVocabularyContextualSidebarContext()
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"categories",
			() -> {
				if (_vocabulary == null) {
					return "0";
				}

				return String.valueOf(_vocabulary.getCategoriesCount());
			}
		).put(
			"chooseVocabularyProps", _getChooseVocabularyButtonContext()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale())
		).put(
			"hasCategories",
			() -> {
				if (_vocabulary == null) {
					return false;
				}

				return _vocabulary.getCategoriesCount() > 0;
			}
		).put(
			"locales",
			JSONUtil.toJSONArray(
				LanguageUtil.getAvailableLocales(
					_themeDisplay.getSiteGroupId()),
				locale -> {
					String w3cLanguageId = LocaleUtil.toW3cLanguageId(locale);

					return JSONUtil.put(
						"id", LocaleUtil.toLanguageId(locale)
					).put(
						"label", w3cLanguageId
					).put(
						"symbol", StringUtil.toLowerCase(w3cLanguageId)
					);
				})
		).put(
			"localizedNames",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				return JSONFactoryUtil.createJSONObject(
					typeSettingsUnicodeProperties.getProperty(
						"localizedNames", "{}"));
			}
		).put(
			"namespace", _liferayPortletResponse.getNamespace()
		).put(
			"showVocabularyLevel",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				return GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.get("showVocabularyLevel"));
			}
		).put(
			"site",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				long groupId = GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("groupId"));

				if (groupId == _themeDisplay.getCompanyGroupId()) {
					return LanguageUtil.get(_httpServletRequest, "global");
				}

				Group group = GroupLocalServiceUtil.getGroup(groupId);

				return group.getDescriptiveName(_themeDisplay.getLocale());
			}
		).put(
			"useCustomName",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				return GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.get("useCustomName"));
			}
		).put(
			"vocabulary",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				return HashMapBuilder.<String, Object>put(
					"classPK",
					GetterUtil.getLong(
						typeSettingsUnicodeProperties.get("classPK"))
				).put(
					"groupId",
					GetterUtil.getLong(
						typeSettingsUnicodeProperties.get("groupId"))
				).put(
					"title",
					() -> {
						if (_vocabulary != null) {
							return _vocabulary.getTitle(
								_themeDisplay.getLocale());
						}

						return typeSettingsUnicodeProperties.get("title");
					}
				).put(
					"type", "type"
				).put(
					"uuid", typeSettingsUnicodeProperties.get("uuid")
				).build();
			}
		).build();
	}

	private Map<String, Object> _getChooseVocabularyButtonContext() {
		return HashMapBuilder.<String, Object>put(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectVocabulary"
		).put(
			"getVocabularyDetailsURL",
			() -> {
				LiferayPortletURL itemDetailsURL =
					(LiferayPortletURL)ResourceURLBuilder.createResourceURL(
						_liferayPortletResponse
					).setResourceID(
						"/navigation_menu/get_vocabulary_details"
					).buildResourceURL();

				itemDetailsURL.setCopyCurrentRenderParameters(false);

				return itemDetailsURL.toString();
			}
		).put(
			"modalTitle",
			LanguageUtil.format(
				_themeDisplay.getLocale(), "select-x", "vocabulary")
		).put(
			"vocabularySelectorURL",
			() -> {
				AssetVocabularyItemSelectorCriterion
					assetVocabularyItemSelectorCriterion =
						new AssetVocabularyItemSelectorCriterion();

				assetVocabularyItemSelectorCriterion.
					setDesiredItemSelectorReturnTypes(
						new AssetVocabularyItemSelectorReturnType());
				assetVocabularyItemSelectorCriterion.
					setIncludeAncestorSiteAndDepotGroupIds(true);
				assetVocabularyItemSelectorCriterion.
					setIncludeInternalVocabularies(false);

				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						_httpServletRequest);

				return PortletURLBuilder.create(
					_itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory,
						_liferayPortletResponse.getNamespace() +
							"selectVocabulary",
						assetVocabularyItemSelectorCriterion)
				).buildString();
			}
		).build();
	}

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final ThemeDisplay _themeDisplay;
	private final AssetVocabulary _vocabulary;

}