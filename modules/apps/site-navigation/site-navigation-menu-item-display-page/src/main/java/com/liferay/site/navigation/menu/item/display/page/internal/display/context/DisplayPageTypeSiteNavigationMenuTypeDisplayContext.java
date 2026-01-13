/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.display.page.internal.display.context;

import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
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
import com.liferay.site.navigation.menu.item.display.page.internal.type.DisplayPageTypeContext;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.util.SiteNavigationMenuItemTypeRegistryUtil;

import jakarta.portlet.PortletResponse;
import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class DisplayPageTypeSiteNavigationMenuTypeDisplayContext {

	public DisplayPageTypeSiteNavigationMenuTypeDisplayContext(
		DisplayPageTypeContext displayPageTypeContext,
		HttpServletRequest httpServletRequest,
		InfoItemServiceRegistry infoItemServiceRegistry,
		ItemSelector itemSelector,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		_displayPageTypeContext = displayPageTypeContext;
		_httpServletRequest = httpServletRequest;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_itemSelector = itemSelector;
		_siteNavigationMenuItem = siteNavigationMenuItem;

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_RESPONSE);

		_liferayPortletResponse = PortalUtil.getLiferayPortletResponse(
			portletResponse);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getChooseInfoItemButtonContext() {
		return HashMapBuilder.<String, Object>put(
			"eventName",
			_liferayPortletResponse.getNamespace() + "selectInfoItem"
		).put(
			"getItemDetailsURL", getItemDetailsURL()
		).put(
			"itemSelectorURL",
			() -> {
				InfoItemItemSelectorCriterion itemSelectorCriterion =
					new InfoItemItemSelectorCriterion();

				itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
					new InfoItemItemSelectorReturnType());
				itemSelectorCriterion.setItemType(
					_displayPageTypeContext.getClassName());

				RequestBackedPortletURLFactory requestBackedPortletURLFactory =
					RequestBackedPortletURLFactoryUtil.create(
						_httpServletRequest);

				PortletURL infoItemSelectorURL =
					_itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory,
						_liferayPortletResponse.getNamespace() +
							"selectInfoItem",
						itemSelectorCriterion);

				if (infoItemSelectorURL == null) {
					return StringPool.BLANK;
				}

				return infoItemSelectorURL.toString();
			}
		).put(
			"modalTitle",
			LanguageUtil.format(
				_themeDisplay.getLocale(), "select-x",
				_displayPageTypeContext.getLabel(_themeDisplay.getLocale()))
		).build();
	}

	public String getClassName() {
		if (_className != null) {
			return _className;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_className = typeSettingsUnicodeProperties.get("className");

		return _className;
	}

	public Map<String, Object> getDisplayPageItemContextualSidebarContext()
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"chooseItemProps", getChooseInfoItemButtonContext()
		).put(
			"defaultLanguageId",
			LocaleUtil.toLanguageId(LocaleUtil.getMostRelevantLocale())
		).put(
			"hasDisplayPage",
			() -> {
				String scopeExternalReferenceCode =
					getScopeExternalReferenceCode();

				Group group =
					GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
						scopeExternalReferenceCode,
						_siteNavigationMenuItem.getCompanyId());

				if ((scopeExternalReferenceCode != null) && (group == null)) {
					return false;
				}

				InfoItemIdentifier infoItemIdentifier =
					new ERCInfoItemIdentifier(
						getExternalReferenceCode(), scopeExternalReferenceCode);

				return AssetDisplayPageUtil.hasAssetDisplayPage(
					_themeDisplay.getSiteGroupId(),
					new InfoItemReference(
						_displayPageTypeContext.getClassName(),
						infoItemIdentifier));
			}
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
			"item",
			HashMapBuilder.<String, Object>put(
				"className", getClassName()
			).put(
				"data", _getDataJSONArray()
			).put(
				"externalReferenceCode", getExternalReferenceCode()
			).put(
				"scopeExternalReferenceCode", getScopeExternalReferenceCode()
			).put(
				"title", getTitle()
			).put(
				"type", getType()
			).build()
		).put(
			"itemSubtype", getItemSubtype()
		).put(
			"itemType", getItemType()
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
			"useCustomName",
			() -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.fastLoad(
						_siteNavigationMenuItem.getTypeSettings()
					).build();

				return GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.get("useCustomName"));
			}
		).build();
	}

	public String getExternalReferenceCode() {
		if (_externalReferenceCode != null) {
			return _externalReferenceCode;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_externalReferenceCode = typeSettingsUnicodeProperties.get(
			"externalReferenceCode");

		return _externalReferenceCode;
	}

	public String getItemDetailsURL() {
		LiferayPortletURL itemDetailsURL =
			(LiferayPortletURL)ResourceURLBuilder.createResourceURL(
				_liferayPortletResponse
			).setResourceID(
				"/navigation_menu/get_item_details"
			).buildResourceURL();

		itemDetailsURL.setCopyCurrentRenderParameters(false);

		return itemDetailsURL.toString();
	}

	public String getItemSubtype() {
		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_displayPageTypeContext.getInfoItemFormVariationsProvider();

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider();

		if (layoutDisplayPageObjectProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutDisplayPageObjectProvider.getGroupId(),
				String.valueOf(
					layoutDisplayPageObjectProvider.getClassTypeId()));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getItemType() {
		return _displayPageTypeContext.getLabel(_themeDisplay.getLocale());
	}

	public String getOriginalTitle() {
		if (Validator.isNotNull(_originalTitle)) {
			return _originalTitle;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider();

		if (layoutDisplayPageObjectProvider == null) {
			UnicodeProperties typeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.fastLoad(
					_siteNavigationMenuItem.getTypeSettings()
				).build();

			_originalTitle = typeSettingsUnicodeProperties.getProperty("title");
		}
		else {
			_originalTitle = layoutDisplayPageObjectProvider.getTitle(
				_themeDisplay.getLocale());
		}

		return _originalTitle;
	}

	public String getScopeExternalReferenceCode() {
		if (_scopeExternalReferenceCode != null) {
			return _scopeExternalReferenceCode;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_scopeExternalReferenceCode = typeSettingsUnicodeProperties.get(
			"scopeExternalReferenceCode");

		return _scopeExternalReferenceCode;
	}

	public String getTitle() {
		if (Validator.isNotNull(_title)) {
			return _title;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_title = typeSettingsUnicodeProperties.get("title");

		return _title;
	}

	public String getType() {
		if (Validator.isNotNull(_type)) {
			return _type;
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				_siteNavigationMenuItem.getTypeSettings()
			).build();

		_type = typeSettingsUnicodeProperties.get("type");

		return _type;
	}

	private JSONArray _getDataJSONArray() throws Exception {
		LayoutDisplayPageInfoItemFieldValuesProvider<Object>
			layoutDisplayPageInfoItemFieldValuesProvider =
				(LayoutDisplayPageInfoItemFieldValuesProvider<Object>)
					_displayPageTypeContext.
						getLayoutDisplayPageInfoItemFieldValuesProvider();

		if (layoutDisplayPageInfoItemFieldValuesProvider == null) {
			return JSONFactoryUtil.createJSONArray();
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider();

		if (layoutDisplayPageObjectProvider == null) {
			return JSONFactoryUtil.createJSONArray();
		}

		InfoItemFieldValues infoItemFieldValues =
			layoutDisplayPageInfoItemFieldValuesProvider.getInfoItemFieldValues(
				layoutDisplayPageObjectProvider.getDisplayObject());

		return JSONUtil.toJSONArray(
			infoItemFieldValues.getInfoFieldValues(),
			infoFieldValue -> JSONUtil.put(
				"title",
				() -> {
					InfoField<?> infoField = infoFieldValue.getInfoField();

					return infoField.getLabel(_themeDisplay.getLocale());
				}
			).put(
				"value", infoFieldValue.getValue(_themeDisplay.getLocale())
			));
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider() {

		if (_layoutDisplayPageObjectProvider != null) {
			return _layoutDisplayPageObjectProvider;
		}

		_layoutDisplayPageObjectProvider =
			_displayPageTypeContext.getLayoutDisplayPageObjectProvider(
				getExternalReferenceCode(),
				_siteNavigationMenuItem.getGroupId(),
				getScopeExternalReferenceCode());

		return _layoutDisplayPageObjectProvider;
	}

	private String _className;
	private final DisplayPageTypeContext _displayPageTypeContext;
	private String _externalReferenceCode;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final ItemSelector _itemSelector;
	private LayoutDisplayPageObjectProvider<?> _layoutDisplayPageObjectProvider;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _originalTitle;
	private String _scopeExternalReferenceCode;
	private final SiteNavigationMenuItem _siteNavigationMenuItem;
	private final ThemeDisplay _themeDisplay;
	private String _title;
	private String _type;

}