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

package com.liferay.info.collection.provider.item.selector.web.internal.item.selector;

import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Diego Hu
 */
public class RelatedInfoItemCollectionProviderItemSelectorViewDescriptor
	extends BaseItemSelectorViewDescriptor
		<RelatedInfoItemCollectionProvider<?, ?>> {

	public RelatedInfoItemCollectionProviderItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviders) {

		super(
			httpServletRequest, portletURL, relatedInfoItemCollectionProviders);
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider) {

		return new RelatedInfoItemCollectionProviderItemDescriptor(
			httpServletRequest, relatedInfoItemCollectionProvider);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoListProviderItemSelectorReturnType();
	}

	public SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
		getSearchContainer() {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<RelatedInfoItemCollectionProvider<?, ?>>
			searchContainer = new SearchContainer<>(
				portletRequest, portletURL, null,
				"there-are-no-related-items-collection-providers");

		List<RelatedInfoItemCollectionProvider<?, ?>>
			relatedInfoItemCollectionProviderList = new ArrayList<>(
				infoCollectionProviders);

		if (Validator.isNotNull(getSelectedItemType())) {
			relatedInfoItemCollectionProviderList = ListUtil.filter(
				relatedInfoItemCollectionProviderList,
				relatedInfoItemCollectionProvider -> Objects.equals(
					relatedInfoItemCollectionProvider.
						getCollectionItemClassName(),
					getSelectedItemType()));
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			relatedInfoItemCollectionProviderList = ListUtil.filter(
				relatedInfoItemCollectionProviderList,
				relatedInfoItemCollectionProvider -> {
					String label = StringUtil.toLowerCase(
						relatedInfoItemCollectionProvider.getLabel(
							themeDisplay.getLocale()));

					return label.contains(StringUtil.toLowerCase(keywords));
				});
		}

		searchContainer.setResultsAndTotal(
			relatedInfoItemCollectionProviderList);

		return searchContainer;
	}

}