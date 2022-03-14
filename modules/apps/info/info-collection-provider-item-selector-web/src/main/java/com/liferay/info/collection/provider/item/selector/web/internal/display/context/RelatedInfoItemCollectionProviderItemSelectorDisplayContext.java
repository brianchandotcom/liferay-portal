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
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
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
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class RelatedInfoItemCollectionProviderItemSelectorDisplayContext {

	public RelatedInfoItemCollectionProviderItemSelectorDisplayContext(
		HttpServletRequest httpServletRequest, String itemSelectedEventName,
		Language language, PortletURL portletURL,
		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviders) {

		_httpServletRequest = httpServletRequest;
		_itemSelectedEventName = itemSelectedEventName;
		_language = language;
		_portletURL = portletURL;
		_relatedInfoItemCollectionProviders =
			relatedInfoItemCollectionProviders;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "icon");

		return _displayStyle;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getPayload(
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider) {

		return JSONUtil.put(
			"itemSubtype",
			() -> {
				if (relatedInfoItemCollectionProvider instanceof
						SingleFormVariationInfoCollectionProvider) {

					SingleFormVariationInfoCollectionProvider<?>
						singleFormVariationInfoCollectionProvider =
							(SingleFormVariationInfoCollectionProvider<?>)
								relatedInfoItemCollectionProvider;

					return singleFormVariationInfoCollectionProvider.
						getFormVariationKey();
				}

				return null;
			}
		).put(
			"itemType",
			relatedInfoItemCollectionProvider.getCollectionItemClassName()
		).put(
			"key", relatedInfoItemCollectionProvider.getKey()
		).put(
			"sourceItemType",
			relatedInfoItemCollectionProvider.getSourceItemClassName()
		).put(
			"title",
			() -> {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return relatedInfoItemCollectionProvider.getLabel(
					themeDisplay.getLocale());
			}
		).toString();
	}

	public List<RelatedInfoItemCollectionProvider<?, ?>>
		getRelatedInfoItemCollectionProviders() {

		return _relatedInfoItemCollectionProviders;
	}

	public String getReturnType() {
		ItemSelectorReturnType itemSelectorReturnType =
			new InfoListProviderItemSelectorReturnType();

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		return itemSelectorReturnTypeClass.getName();
	}

	public SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
		getSearchContainer() {

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
			searchContainer = new SearchContainer<>(
				portletRequest, _portletURL, null,
				_language.get(
					resourceBundle,
					"there-are-no-related-items-collection-providers"));

		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviders = new ArrayList<>(
				_relatedInfoItemCollectionProviders);

		String itemType = ParamUtil.getString(_httpServletRequest, "itemType");

		if (Validator.isNotNull(itemType)) {
			relatedInfoItemCollectionProviders = ListUtil.filter(
				relatedInfoItemCollectionProviders,
				relatedInfoItemCollectionProvider -> {
					if (Objects.equals(
							relatedInfoItemCollectionProvider.
								getCollectionItemClassName(),
							itemType)) {

						return true;
					}

					return false;
				});
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			relatedInfoItemCollectionProviders = ListUtil.filter(
				relatedInfoItemCollectionProviders,
				relatedInfoItemCollectionProvider -> {
					String label = StringUtil.toLowerCase(
						relatedInfoItemCollectionProvider.getLabel(
							themeDisplay.getLocale()));

					if (label.contains(StringUtil.toLowerCase(keywords))) {
						return true;
					}

					return false;
				});
		}

		searchContainer.setResultsAndTotal(relatedInfoItemCollectionProviders);

		return searchContainer;
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
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider) {

		if (relatedInfoItemCollectionProvider instanceof
				FilteredInfoCollectionProvider) {

			return true;
		}

		return false;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final Language _language;
	private final PortletURL _portletURL;
	private final List<RelatedInfoItemCollectionProvider<?, ?>>
		_relatedInfoItemCollectionProviders;

}