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

package com.liferay.info.collection.provider.item.selector.web.internal;

import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class RelatedInfoItemCollectionProviderItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor
		<RelatedInfoItemCollectionProvider<?, ?>> {

	public RelatedInfoItemCollectionProviderItemSelectorViewDescriptor(
		RelatedInfoItemCollectionProviderItemSelectorCriterion
			relatedInfoItemCollectionProviderItemSelectorCriterion,
		Language language, HttpServletRequest httpServletRequest,
		PortletURL portletURL,
		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviders) {

		_relatedInfoItemCollectionProviderItemSelectorCriterion =
			relatedInfoItemCollectionProviderItemSelectorCriterion;
		_language = language;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
		_relatedInfoItemCollectionProviders =
			relatedInfoItemCollectionProviders;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider) {

		return new RelatedInfoItemCollectionProviderItemDescriptor(
			_httpServletRequest, relatedInfoItemCollectionProvider);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new RelatedInfoItemCollectionProviderItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		Set<String> keysSet = new HashSet<>();

		for (RelatedInfoItemCollectionProvider
				relatedInfoItemCollectionProvider :
					_relatedInfoItemCollectionProviders) {

			keysSet.add(
				relatedInfoItemCollectionProvider.getCollectionItemClassName());
		}

		Stream<String> keysSetSorted = keysSet.stream(
		).sorted(
			new StringComparator(false, true)
		);

		return keysSetSorted.toArray(String[]::new);
	}

	public SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
			getSearchContainer()
		throws PortalException {

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

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			relatedInfoItemCollectionProviders = ListUtil.filter(
				relatedInfoItemCollectionProviders,
				relatedInfoItemCollectionProvider -> {
					if (Objects.equals(
							relatedInfoItemCollectionProvider.
								getCollectionItemClassName(),
							orderByCol)) {

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

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final PortletURL _portletURL;
	private final RelatedInfoItemCollectionProviderItemSelectorCriterion
		_relatedInfoItemCollectionProviderItemSelectorCriterion;
	private final List<RelatedInfoItemCollectionProvider<?, ?>>
		_relatedInfoItemCollectionProviders;
	private final ThemeDisplay _themeDisplay;

}