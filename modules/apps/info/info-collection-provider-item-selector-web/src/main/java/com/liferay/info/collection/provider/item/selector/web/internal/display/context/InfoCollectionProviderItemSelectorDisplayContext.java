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

package com.liferay.info.collection.provider.item.selector.web.internal.display.context;

import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorDisplayContext {

	public InfoCollectionProviderItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest,
		List<InfoCollectionProvider<?>> infoCollectionProviders,
		InfoItemServiceTracker infoItemServiceTracker,
		String itemSelectedEventName, Language language,
		PortletURL portletURL) {

		_httpServletRequest = httpServletRequest;
		_infoCollectionProviders = infoCollectionProviders;
		_infoItemServiceTracker = infoItemServiceTracker;
		_itemSelectedEventName = itemSelectedEventName;
		_language = language;
		_portletURL = portletURL;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public List<InfoCollectionProvider<?>> getInfoCollectionProviders() {
		return _infoCollectionProviders;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getPayload(InfoCollectionProvider<?> infoCollectionProvider) {
		return JSONUtil.put(
			"itemSubtype",
			() -> {
				if (infoCollectionProvider instanceof
						SingleFormVariationInfoCollectionProvider) {

					SingleFormVariationInfoCollectionProvider<?>
						singleFormVariationInfoCollectionProvider =
							(SingleFormVariationInfoCollectionProvider<?>)
								infoCollectionProvider;

					return singleFormVariationInfoCollectionProvider.
						getFormVariationKey();
				}

				return null;
			}
		).put(
			"itemType", infoCollectionProvider.getCollectionItemClassName()
		).put(
			"key", infoCollectionProvider.getKey()
		).put(
			"title",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return infoCollectionProvider.getLabel(
					themeDisplay.getLocale());
			}
		).toString();
	}

	public String getReturnType() {
		ItemSelectorReturnType itemSelectorReturnType =
			new InfoListProviderItemSelectorReturnType();

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		return itemSelectorReturnTypeClass.getName();
	}

	public SearchContainer<InfoCollectionProvider<?>> getSearchContainer() {
		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		SearchContainer<InfoCollectionProvider<?>> searchContainer =
			new SearchContainer<>(
				portletRequest, _portletURL, null,
				_language.get(
					resourceBundle, "there-are-no-info-collection-providers"));

		List<InfoCollectionProvider<?>> infoCollectionProviders =
			new ArrayList<>(_infoCollectionProviders);

		String itemType = ParamUtil.getString(_httpServletRequest, "itemType");

		if (Validator.isNotNull(itemType)) {
			infoCollectionProviders = ListUtil.filter(
				infoCollectionProviders,
				infoCollectionProvider -> {
					if (Objects.equals(
							infoCollectionProvider.getCollectionItemClassName(),
							itemType)) {

						return true;
					}

					return false;
				});
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			infoCollectionProviders = ListUtil.filter(
				infoCollectionProviders,
				infoCollectionProvider -> {
					String label = StringUtil.toLowerCase(
						infoCollectionProvider.getLabel(
							themeDisplay.getLocale()));

					if (label.contains(StringUtil.toLowerCase(keywords))) {
						return true;
					}

					return false;
				});
		}

		searchContainer.setResultsAndTotal(infoCollectionProviders);

		return searchContainer;
	}

	public String getSubtitle(
		InfoCollectionProvider<?> infoCollectionProvider, Locale locale) {

		String className = infoCollectionProvider.getCollectionItemClassName();

		if (Validator.isNull(className) ||
			!(infoCollectionProvider instanceof
				SingleFormVariationInfoCollectionProvider)) {

			return StringPool.BLANK;
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className);

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SingleFormVariationInfoCollectionProvider<?>
			singleFormVariationInfoCollectionProvider =
				(SingleFormVariationInfoCollectionProvider<?>)
					infoCollectionProvider;

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				themeDisplay.getScopeGroupId(),
				singleFormVariationInfoCollectionProvider.
					getFormVariationKey());

		if (infoItemFormVariation == null) {
			return StringPool.BLANK;
		}

		return infoItemFormVariation.getLabel(locale);
	}

	public String getTitle(
		InfoCollectionProvider<?> infoCollectionProvider, Locale locale) {

		String className = infoCollectionProvider.getCollectionItemClassName();

		if (Validator.isNull(className)) {
			return StringPool.BLANK;
		}

		return ResourceActionsUtil.getModelResource(locale, className);
	}

	public boolean isDescriptiveDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isIconDisplayStyle() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	public boolean supportsFilters(
		InfoCollectionProvider<?> infoCollectionProvider) {

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final List<InfoCollectionProvider<?>> _infoCollectionProviders;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final String _itemSelectedEventName;
	private final Language _language;
	private final PortletURL _portletURL;

}