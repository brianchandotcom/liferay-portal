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

package com.liferay.asset.list.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class AssetEntryListLayoutListRetriever
	implements LayoutListRetriever
		<InfoListItemSelectorReturnType, ClassedModelListObjectReference> {

	@Override
	public List<Object> getList(
		ClassedModelListObjectReference classedModelListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return Collections.emptyList();
		}

		Optional<long[]> segmentsEntryIdsOptional =
			layoutListRetrieverContext.getSegmentsEntryIdsOptional();

		long[] segmentsEntryIds = segmentsEntryIdsOptional.orElse(
			new long[] {0});

		Optional<Pagination> paginationOptional =
			layoutListRetrieverContext.getPaginationOptional();

		Pagination pagination = paginationOptional.orElse(
			Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		List<AssetEntry> assetEntries =
			_assetListAssetEntryProvider.getAssetEntries(
				assetListEntry, segmentsEntryIds,
				_getAssetCategoryIds(layoutListRetrieverContext),
				_getKeywords(layoutListRetrieverContext), StringPool.BLANK,
				pagination.getStart(), pagination.getEnd());

		if (Objects.equals(
				AssetEntry.class.getName(),
				assetListEntry.getAssetEntryType())) {

			return Collections.unmodifiableList(assetEntries);
		}

		return _toAssetObjects(assetEntries);
	}

	@Override
	public int getListCount(
		ClassedModelListObjectReference classedModelListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return 0;
		}

		Optional<long[]> segmentsEntryIdsOptional =
			layoutListRetrieverContext.getSegmentsEntryIdsOptional();

		long[] segmentsEntryIds = segmentsEntryIdsOptional.orElse(
			new long[] {0});

		return _assetListAssetEntryProvider.getAssetEntriesCount(
			assetListEntry, segmentsEntryIds,
			_getAssetCategoryIds(layoutListRetrieverContext),
			_getKeywords(layoutListRetrieverContext), StringPool.BLANK);
	}

	@Override
	public List<InfoFilter> getSupportedInfoFilters(
		ClassedModelListObjectReference classedModelListObjectReference) {

		return _supportedInfoFilters;
	}

	private long[][] _getAssetCategoryIds(
		LayoutListRetrieverContext layoutListRetrieverContext) {

		Optional<CategoriesInfoFilter> infoFilterOptional =
			layoutListRetrieverContext.getInfoFilterOptional(
				CategoriesInfoFilter.class);

		CategoriesInfoFilter categoriesInfoFilter = infoFilterOptional.orElse(
			null);

		if (categoriesInfoFilter == null) {
			return new long[0][];
		}

		return categoriesInfoFilter.getCategoryIds();
	}

	private String _getKeywords(
		LayoutListRetrieverContext layoutListRetrieverContext) {

		Optional<KeywordsInfoFilter> infoFilterOptional =
			layoutListRetrieverContext.getInfoFilterOptional(
				KeywordsInfoFilter.class);

		KeywordsInfoFilter keywordsInfoFilter = infoFilterOptional.orElse(null);

		if (keywordsInfoFilter == null) {
			return StringPool.BLANK;
		}

		return keywordsInfoFilter.getKeywords();
	}

	private List<Object> _toAssetObjects(List<AssetEntry> assetEntries) {
		List<Object> assetObjects = new ArrayList<>(assetEntries.size());

		for (AssetEntry assetEntry : assetEntries) {
			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			assetObjects.add(assetRenderer.getAssetObject());
		}

		return assetObjects;
	}

	private static final List<InfoFilter> _supportedInfoFilters = Arrays.asList(
		new CategoriesInfoFilter(), new KeywordsInfoFilter());

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}