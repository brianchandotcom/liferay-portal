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

import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.InfoRequestItemProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.filter.PropertyInfoItemServiceFilter;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.FilteredInfoListProvider;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
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

		InfoListProvider<Object> infoListProvider =
			(InfoListProvider<Object>)
				_infoItemServiceTracker.getInfoItemService(
					InfoListProvider.class, keyListObjectReference.getKey());

		if (infoListProvider == null) {
			return Collections.emptyList();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Group group = _groupLocalService.fetchGroup(
			serviceContext.getScopeGroupId());

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		InfoListProviderContext infoListProviderContext =
			new DefaultInfoListProviderContext(group, user);

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		Pagination pagination = paginationOptional.orElse(
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		if (infoListProvider instanceof FilteredInfoListProvider) {
			FilteredInfoListProvider<Object, InfoFilter>
				filteredInfoListProvider =
					(FilteredInfoListProvider<Object, InfoFilter>)
						infoListProvider;

			return filteredInfoListProvider.getInfoList(
				infoListProviderContext,
				_getInfoFilter(
					filteredInfoListProvider, layoutListRetrieverContext),
				pagination, null);
		}

		return infoListProvider.getInfoList(
			infoListProviderContext, pagination, null);
	}

	@Override
	public int getListCount(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoListProvider<?> infoListProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoListProvider.class, keyListObjectReference.getKey());

		if (infoListProvider == null) {
			return 0;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Group group = _groupLocalService.fetchGroup(
			serviceContext.getScopeGroupId());

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		InfoListProviderContext infoListProviderContext =
			new DefaultInfoListProviderContext(group, user);

		if (infoListProvider instanceof FilteredInfoListProvider) {
			FilteredInfoListProvider<Object, InfoFilter>
				filteredInfoListProvider =
					(FilteredInfoListProvider<Object, InfoFilter>)
						infoListProvider;

			return filteredInfoListProvider.getInfoListCount(
				infoListProviderContext,
				_getInfoFilter(
					filteredInfoListProvider, layoutListRetrieverContext));
		}

		return infoListProvider.getInfoListCount(infoListProviderContext);
	}

	private InfoFilter _getInfoFilter(
		FilteredInfoListProvider<Object, InfoFilter> filteredInfoListProvider,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		Optional<HttpServletRequest> httpServletRequestOptional =
			layoutListRetrieverContext.getHttpServletRequestOptional();

		HttpServletRequest httpServletRequest =
			httpServletRequestOptional.orElse(null);

		if (!httpServletRequestOptional.isPresent()) {
			return null;
		}

		Class<?> infoFilterClass =
			filteredInfoListProvider.getInfoFilterClass();

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