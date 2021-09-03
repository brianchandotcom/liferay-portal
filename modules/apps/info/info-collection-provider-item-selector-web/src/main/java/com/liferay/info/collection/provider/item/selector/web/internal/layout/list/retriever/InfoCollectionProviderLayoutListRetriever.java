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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.ConfigurableInfoCollectionProvider;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
			infoCollectionProvider = _infoItemServiceTracker.getInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				keyListObjectReference.getKey());
		}

		if (infoCollectionProvider == null) {
			return Collections.emptyList();
		}

		CollectionQuery collectionQuery = new CollectionQuery();

		if (infoCollectionProvider instanceof
				ConfigurableInfoCollectionProvider) {

			Optional<Map<String, String[]>> configurationOptional =
				layoutListRetrieverContext.getConfigurationOptional();

			collectionQuery.setConfiguration(
				configurationOptional.orElse(null));
		}

		if (infoCollectionProvider instanceof
				RelatedInfoItemCollectionProvider) {

			Optional<Object> contextObjectOptional =
				layoutListRetrieverContext.getContextObjectOptional();

			Object relatedItem = contextObjectOptional.orElse(null);

			if (relatedItem == null) {
				return Collections.emptyList();
			}

			RelatedInfoItemCollectionProvider<Object, ?>
				relatedInfoItemCollectionProvider =
					(RelatedInfoItemCollectionProvider<Object, ?>)
						infoCollectionProvider;

			if (Objects.equals(
					relatedInfoItemCollectionProvider.getSourceItemClass(),
					AssetEntry.class)) {

				relatedItem = _getAssetEntryOptional(relatedItem);
			}

			collectionQuery.setRelatedItemObject(relatedItem);
		}

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		collectionQuery.setPagination(paginationOptional.orElse(null));

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			Optional<Map<String, InfoFilter>> infoFiltersOptional =
				layoutListRetrieverContext.getInfoFiltersOptional();

			collectionQuery.setInfoFilters(infoFiltersOptional.orElse(null));
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
			infoCollectionProvider = _infoItemServiceTracker.getInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				keyListObjectReference.getKey());
		}

		if (infoCollectionProvider == null) {
			return 0;
		}

		CollectionQuery collectionQuery = new CollectionQuery();

		if (infoCollectionProvider instanceof
				ConfigurableInfoCollectionProvider) {

			Optional<Map<String, String[]>> configurationOptional =
				layoutListRetrieverContext.getConfigurationOptional();

			collectionQuery.setConfiguration(
				configurationOptional.orElse(null));
		}

		if (infoCollectionProvider instanceof
				RelatedInfoItemCollectionProvider) {

			Optional<Object> contextObjectOptional =
				layoutListRetrieverContext.getContextObjectOptional();

			Object relatedItem = contextObjectOptional.orElse(null);

			if (relatedItem == null) {
				return 0;
			}

			RelatedInfoItemCollectionProvider<Object, ?>
				relatedInfoItemCollectionProvider =
					(RelatedInfoItemCollectionProvider<Object, ?>)
						infoCollectionProvider;

			if (Objects.equals(
					relatedInfoItemCollectionProvider.getSourceItemClass(),
					AssetEntry.class)) {

				relatedItem = _getAssetEntryOptional(relatedItem);
			}

			collectionQuery.setRelatedItemObject(relatedItem);
		}

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			Optional<Map<String, InfoFilter>> infoFiltersOptional =
				layoutListRetrieverContext.getInfoFiltersOptional();

			collectionQuery.setInfoFilters(infoFiltersOptional.orElse(null));
		}

		InfoPage<?> infoPage = infoCollectionProvider.getCollectionInfoPage(
			collectionQuery);

		return infoPage.getTotalCount();
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters(
		KeyListObjectReference keyListObjectReference) {

		InfoCollectionProvider<Object> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, keyListObjectReference.getKey());

		if (infoCollectionProvider == null) {
			infoCollectionProvider = _infoItemServiceTracker.getInfoItemService(
				RelatedInfoItemCollectionProvider.class,
				keyListObjectReference.getKey());
		}

		if (infoCollectionProvider == null) {
			return Collections.emptyList();
		}

		if (infoCollectionProvider instanceof FilteredInfoCollectionProvider) {
			FilteredInfoCollectionProvider<Object>
				filteredInfoCollectionProvider =
					(FilteredInfoCollectionProvider<Object>)
						infoCollectionProvider;

			return filteredInfoCollectionProvider.getSupportedInfoFilters();
		}

		return Collections.emptyList();
	}

	private AssetEntry _getAssetEntryOptional(Object contextObject) {
		if (contextObject instanceof AssetEntry) {
			return (AssetEntry)contextObject;
		}

		if (!(contextObject instanceof ClassedModel)) {
			return null;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				_getModelClassName(contextObject));

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(contextObject);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		String className = infoItemReference.getClassName();

		if (Objects.equals(className, FileEntry.class.getName())) {

			// LPS-111037

			className = DLFileEntry.class.getName();
		}

		return _assetEntryLocalService.fetchEntry(
			className, classPKInfoItemIdentifier.getClassPK());
	}

	private String _getModelClassName(Object contextObject) {
		if (contextObject instanceof FileEntry) {
			return FileEntry.class.getName();
		}

		ClassedModel classedModel = (ClassedModel)contextObject;

		return classedModel.getModelClassName();
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