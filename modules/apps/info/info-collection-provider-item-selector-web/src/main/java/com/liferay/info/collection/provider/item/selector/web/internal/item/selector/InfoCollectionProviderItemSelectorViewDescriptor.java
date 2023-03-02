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

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
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
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorViewDescriptor
	extends BaseItemSelectorViewDescriptor<InfoCollectionProvider<?>> {

	public InfoCollectionProviderItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest, PortletURL portletURL,
		List<InfoCollectionProvider<?>> infoCollectionProviders,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		super(httpServletRequest, portletURL, infoCollectionProviders);

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		InfoCollectionProvider<?> infoCollectionProvider) {

		return new InfoCollectionProviderItemDescriptor(
			httpServletRequest, infoCollectionProvider,
			_infoItemServiceRegistry);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoListProviderItemSelectorReturnType();
	}

	public SearchContainer<InfoCollectionProvider<?>> getSearchContainer() {
		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<InfoCollectionProvider<?>> searchContainer =
			new SearchContainer<>(
				portletRequest, portletURL, null,
				"there-are-no-info-collection-providers");

		List<InfoCollectionProvider<?>> infoCollectionProviderList =
			new ArrayList<>(infoCollectionProviders);

		String itemType = ParamUtil.getString(httpServletRequest, "itemType");

		if (Validator.isNotNull(itemType)) {
			infoCollectionProviderList = ListUtil.filter(
				infoCollectionProviderList,
				infoCollectionProvider -> Objects.equals(
					infoCollectionProvider.getCollectionItemClassName(),
					itemType));
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			infoCollectionProviderList = ListUtil.filter(
				infoCollectionProviderList,
				infoCollectionProvider -> {
					String label = StringUtil.toLowerCase(
						infoCollectionProvider.getLabel(
							themeDisplay.getLocale()));

					return label.contains(StringUtil.toLowerCase(keywords));
				});
		}

		searchContainer.setResultsAndTotal(infoCollectionProviderList);

		return searchContainer;
	}

	private final InfoItemServiceRegistry _infoItemServiceRegistry;

}