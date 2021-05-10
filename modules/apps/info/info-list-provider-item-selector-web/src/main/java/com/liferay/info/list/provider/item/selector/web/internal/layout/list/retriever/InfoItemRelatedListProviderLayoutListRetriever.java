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

package com.liferay.info.list.provider.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoItemRelatedListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class InfoItemRelatedListProviderLayoutListRetriever
	implements LayoutListRetriever
		<InfoItemRelatedListProviderItemSelectorReturnType,
		 KeyListObjectReference> {

	@Override
	public List<Object> getList(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoPage<Object> infoPage = _getRelatedItemInfoPage(
			keyListObjectReference, layoutListRetrieverContext);

		return (List<Object>)infoPage.getPageItems();
	}

	@Override
	public int getListCount(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoPage<Object> infoPage = _getRelatedItemInfoPage(
			keyListObjectReference, layoutListRetrieverContext);

		return infoPage.getTotalCount();
	}

	private Optional<AssetEntry> _getAssetEntryOptional(Object contextObject) {
		if (!(contextObject instanceof ClassedModel)) {
			return Optional.empty();
		}

		ClassedModel classedModel = (ClassedModel)contextObject;

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				classedModel.getModelClassName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(contextObject);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return Optional.empty();
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			infoItemReference.getClassName(),
			classPKInfoItemIdentifier.getClassPK());

		return Optional.ofNullable(assetEntry);
	}

	private InfoListProviderContext _getInfoListProviderContext() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Group group = _groupLocalService.fetchGroup(
			serviceContext.getScopeGroupId());

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		return new DefaultInfoListProviderContext(group, user);
	}

	private InfoPage<Object> _getRelatedItemInfoPage(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		Optional<Object> contextObjectOptional =
			layoutListRetrieverContext.getContextObjectOptional();

		if (!contextObjectOptional.isPresent()) {
			return InfoPage.of(Collections.emptyList());
		}

		InfoItemRelatedListProvider<Object, Object>
			infoItemRelatedListProvider =
				_infoItemServiceTracker.getInfoItemService(
					InfoItemRelatedListProvider.class,
					keyListObjectReference.getKey());

		if (infoItemRelatedListProvider == null) {
			return InfoPage.of(Collections.emptyList());
		}

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		Pagination pagination = paginationOptional.orElse(
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		if (Objects.equals(
				infoItemRelatedListProvider.getSourceItemClass(),
				AssetEntry.class)) {

			Optional<AssetEntry> assetEntryOptional = _getAssetEntryOptional(
				contextObjectOptional.get());

			return (InfoPage<Object>)assetEntryOptional.map(
				assetEntry ->
					infoItemRelatedListProvider.getRelatedItemsInfoPage(
						assetEntry, _getInfoListProviderContext(), pagination,
						null)
			).orElse(
				InfoPage.of(Collections.emptyList())
			);
		}

		return (InfoPage<Object>)
			infoItemRelatedListProvider.getRelatedItemsInfoPage(
				contextObjectOptional.get(), _getInfoListProviderContext(),
				pagination, null);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private UserLocalService _userLocalService;

}