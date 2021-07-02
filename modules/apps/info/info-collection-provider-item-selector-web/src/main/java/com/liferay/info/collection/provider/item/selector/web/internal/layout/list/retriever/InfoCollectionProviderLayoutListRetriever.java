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

package com.liferay.info.collection.provider.item.selector.web.internal.layout.list.retriever;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.InfoRequestItemProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.filter.PropertyInfoItemServiceFilter;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class InfoCollectionProviderLayoutListRetriever
	implements LayoutListRetriever
		<InfoListProviderItemSelectorReturnType, KeyListObjectReference> {

	@Override
	public List<Object> getList(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoCollectionProvider<Object> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, keyListObjectReference.getKey());

		if (infoCollectionProvider == null) {
			return Collections.emptyList();
		}

		CollectionQuery collectionQuery = new CollectionQuery();

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		collectionQuery.setPagination(paginationOptional.orElse(null));

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			FilteredInfoCollectionProvider<Object, InfoFilter>
				filteredInfoCollectionProvider =
					(FilteredInfoCollectionProvider<Object, InfoFilter>)
						infoCollectionProvider;

			collectionQuery.setInfoFilter(
				_getInfoFilter(
					filteredInfoCollectionProvider,
					layoutListRetrieverContext));
		}

		InfoPage<?> infoPage = infoCollectionProvider.getCollectionInfoPage(
			collectionQuery);

		return (List<Object>)infoPage.getPageItems();
	}

	@Override
	public int getListCount(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, keyListObjectReference.getKey());

		if (infoCollectionProvider == null) {
			return 0;
		}

		CollectionQuery collectionQuery = new CollectionQuery();

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			FilteredInfoCollectionProvider<Object, InfoFilter>
				filteredInfoCollectionProvider =
					(FilteredInfoCollectionProvider<Object, InfoFilter>)
						infoCollectionProvider;

			collectionQuery.setInfoFilter(
				_getInfoFilter(
					filteredInfoCollectionProvider,
					layoutListRetrieverContext));
		}

		InfoPage<?> infoPage = infoCollectionProvider.getCollectionInfoPage(
			collectionQuery);

		return infoPage.getTotalCount();
	}

	private InfoFilter _getInfoFilter(
		FilteredInfoCollectionProvider<Object, InfoFilter>
			filteredInfoCollectionProvider,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		Optional<HttpServletRequest> httpServletRequestOptional =
			layoutListRetrieverContext.getHttpServletRequestOptional();

		HttpServletRequest httpServletRequest =
			httpServletRequestOptional.orElse(null);

		if (!httpServletRequestOptional.isPresent()) {
			return null;
		}

		Class<?> infoFilterClass =
			filteredInfoCollectionProvider.getInfoFilterClass();

		InfoRequestItemProvider<InfoFilter> infoRequestItemProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoRequestItemProvider.class, InfoFilter.class.getName(),
				new PropertyInfoItemServiceFilter(
					"infoFilterKey", infoFilterClass.getName()));

		return infoRequestItemProvider.create(httpServletRequest);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private UserLocalService _userLocalService;

}