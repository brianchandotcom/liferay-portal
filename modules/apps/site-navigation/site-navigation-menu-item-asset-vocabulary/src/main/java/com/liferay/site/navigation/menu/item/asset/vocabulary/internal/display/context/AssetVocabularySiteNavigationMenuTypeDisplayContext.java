/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.asset.vocabulary.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.url.builder.ResourceURLBuilder;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.util.SiteNavigationMenuItemTypeRegistryUtil;

import jakarta.portlet.PortletResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetVocabularySiteNavigationMenuTypeDisplayContext {

	public AssetVocabularySiteNavigationMenuTypeDisplayContext(
		HttpServletRequest httpServletRequest,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		_httpServletRequest = httpServletRequest;
		_siteNavigationMenuItem = siteNavigationMenuItem;

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_RESPONSE));
		_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.fastLoad(
			siteNavigationMenuItem.getTypeSettings()
		).build();
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = null;

		String scopeExternalReferenceCode = _typeSettingsUnicodeProperties.get(
			"scopeExternalReferenceCode");

		if (Validator.isNull(scopeExternalReferenceCode)) {
			group = _themeDisplay.getScopeGroup();
		}
		else {
			group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				scopeExternalReferenceCode, _themeDisplay.getCompanyId());
		}

		if (group == null) {
			_assetVocabulary = null;
		}
		else {
			_assetVocabulary =
				AssetVocabularyLocalServiceUtil.
					fetchAssetVocabularyByExternalReferenceCode(
						_typeSettingsUnicodeProperties.get(
							"externalReferenceCode"),
						group.getGroupId());
		}
	}

	public Map<String, Object> getAssetVocabularyContextualSidebarContext()
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"assetVocabulary",
			() -> HashMapBuilder.<String, Object>put(
				"externalReferenceCode",
				_typeSettingsUnicodeProperties.get("externalReferenceCode")
			).put(
				"scopeExternalReferenceCode",
				GetterUtil.getString(
					_typeSettingsUnicodeProperties.get(
						"scopeExternalReferenceCode"))
			).put(
				"title",
				() -> {
					if (_assetVocabulary != null) {
						return _assetVocabulary.getTitle(
							_themeDisplay.getLocale());
					}

					return _typeSettingsUnicodeProperties.get("title");
				}
			).put(
				"type", "asset-vocabulary"
			).build()
		).put(
			"chooseAssetVocabularyProps",
			_getChooseAssetVocabularyButtonContext()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale())
		).put(
			"hasModel",
			() -> {
				SiteNavigationMenuItemType siteNavigationMenuItemType =
					SiteNavigationMenuItemTypeRegistryUtil.
						getSiteNavigationMenuItemType(
							_siteNavigationMenuItem.getType());

				return siteNavigationMenuItemType.hasModel(
					_siteNavigationMenuItem.getCompanyId(),
					_siteNavigationMenuItem.getGroupId(),
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build());
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
			() -> JSONFactoryUtil.createJSONObject(
				_typeSettingsUnicodeProperties.getProperty(
					"localizedNames", "{}"))
		).put(
			"namespace", _liferayPortletResponse.getNamespace()
		).put(
			"numberOfCategories",
			() -> {
				if (_assetVocabulary == null) {
					return 0;
				}

				return _assetVocabulary.getCategoriesCount();
			}
		).put(
			"showAssetVocabularyLevel",
			() -> GetterUtil.getBoolean(
				_typeSettingsUnicodeProperties.get("showAssetVocabularyLevel"))
		).put(
			"siteName",
			() -> {
				Group group = GroupLocalServiceUtil.getGroup(
					_siteNavigationMenuItem.getGroupId());

				String scopeExternalReferenceCode =
					_typeSettingsUnicodeProperties.get(
						"scopeExternalReferenceCode");

				if (scopeExternalReferenceCode != null) {
					group =
						GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
							scopeExternalReferenceCode,
							_themeDisplay.getCompanyId());
				}

				if (group == null) {
					return LanguageUtil.format(
						_httpServletRequest, "unable-to-find-x", "site");
				}

				if (group.getGroupId() == _themeDisplay.getCompanyGroupId()) {
					return LanguageUtil.get(_httpServletRequest, "global");
				}

				return group.getDescriptiveName(_themeDisplay.getLocale());
			}
		).put(
			"useCustomName",
			() -> GetterUtil.getBoolean(
				_typeSettingsUnicodeProperties.get("useCustomName"))
		).build();
	}

	private Map<String, Object> _getChooseAssetVocabularyButtonContext()
		throws Exception {

		long companyGroupId = _themeDisplay.getCompanyGroupId();
		long scopeGroupId = _themeDisplay.getScopeGroupId();

		Group companyGroup = GroupLocalServiceUtil.fetchGroup(companyGroupId);
		Group scopeGroup = _themeDisplay.getScopeGroup();

		String companyExternalReferenceCode;

		if (companyGroup == null) {
			companyExternalReferenceCode = StringPool.BLANK;
		}
		else {
			companyExternalReferenceCode = GetterUtil.getString(
				companyGroup.getExternalReferenceCode());
		}

		return HashMapBuilder.<String, Object>put(
			"assetLibraries", _getConnectedAssetLibrariesJSONArray(scopeGroupId)
		).put(
			"companyExternalReferenceCode", companyExternalReferenceCode
		).put(
			"companyGroupId", String.valueOf(companyGroupId)
		).put(
			"currentSiteId", String.valueOf(scopeGroupId)
		).put(
			"getAssetVocabularyDetailsURL",
			() -> {
				LiferayPortletURL itemDetailsURL =
					(LiferayPortletURL)ResourceURLBuilder.createResourceURL(
						_liferayPortletResponse
					).setResourceID(
						"/navigation_menu/get_asset_vocabulary_details"
					).buildResourceURL();

				itemDetailsURL.setCopyCurrentRenderParameters(false);

				return itemDetailsURL.toString();
			}
		).put(
			"siteExternalReferenceCode",
			GetterUtil.getString(scopeGroup.getExternalReferenceCode())
		).put(
			"siteName", scopeGroup.getDescriptiveName(_themeDisplay.getLocale())
		).build();
	}

	private JSONArray _getConnectedAssetLibrariesJSONArray(long scopeGroupId) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			List<DepotEntry> depotEntries =
				DepotEntryLocalServiceUtil.getGroupConnectedDepotEntries(
					scopeGroupId, DepotConstants.TYPE_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			for (DepotEntry depotEntry : depotEntries) {
				Group depotGroup = GroupLocalServiceUtil.fetchGroup(
					depotEntry.getGroupId());

				if (depotGroup == null) {
					continue;
				}

				jsonArray.put(
					JSONUtil.put(
						"externalReferenceCode",
						GetterUtil.getString(
							depotGroup.getExternalReferenceCode())
					).put(
						"id", String.valueOf(depotGroup.getGroupId())
					).put(
						"name",
						depotGroup.getDescriptiveName(_themeDisplay.getLocale())
					));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to enumerate connected asset libraries for group " +
						scopeGroupId,
					exception);
			}
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularySiteNavigationMenuTypeDisplayContext.class);

	private final AssetVocabulary _assetVocabulary;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final ThemeDisplay _themeDisplay;
	private final UnicodeProperties _typeSettingsUnicodeProperties;

}