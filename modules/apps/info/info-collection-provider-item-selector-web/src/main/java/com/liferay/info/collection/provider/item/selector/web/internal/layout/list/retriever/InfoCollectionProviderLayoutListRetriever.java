/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.collection.provider.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
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
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutListRetriever.class)
public class InfoCollectionProviderLayoutListRetriever
	implements LayoutListRetriever
		<InfoListProviderItemSelectorReturnType, KeyListObjectReference> {

	@Override
	public InfoPage<?> getInfoPage(
		KeyListObjectReference keyListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, keyListObjectReference.getKey());

		if (infoCollectionProvider == null) {
			infoCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					keyListObjectReference.getKey());
		}

		if (infoCollectionProvider == null) {
			return InfoPage.of(
				Collections.emptyList(),
				layoutListRetrieverContext.getPagination(), 0);
		}

		CollectionQuery collectionQuery = new CollectionQuery();

		Pagination pagination = layoutListRetrieverContext.getPagination();

		if (pagination == null) {
			pagination = Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		collectionQuery.setPagination(pagination);

		if (infoCollectionProvider instanceof
				ConfigurableInfoCollectionProvider) {

			collectionQuery.setConfiguration(
				layoutListRetrieverContext.getConfiguration());
		}

		if (infoCollectionProvider instanceof
				RelatedInfoItemCollectionProvider) {

			Object relatedItem = layoutListRetrieverContext.getContextObject();

			if (relatedItem == null) {
				return InfoPage.of(
					Collections.emptyList(),
					layoutListRetrieverContext.getPagination(), 0);
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
			collectionQuery.setInfoFilters(
				layoutListRetrieverContext.getInfoFilters());
		}

		return infoCollectionProvider.getCollectionInfoPage(collectionQuery);
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters(
		KeyListObjectReference keyListObjectReference) {

		InfoCollectionProvider<Object> infoCollectionProvider =
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, keyListObjectReference.getKey());

		if (infoCollectionProvider == null) {
			infoCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
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
			_infoItemServiceRegistry.getFirstInfoItemService(
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

		String className = _infoSearchClassMapperRegistry.getSearchClassName(
			infoItemReference.getClassName());

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
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

}