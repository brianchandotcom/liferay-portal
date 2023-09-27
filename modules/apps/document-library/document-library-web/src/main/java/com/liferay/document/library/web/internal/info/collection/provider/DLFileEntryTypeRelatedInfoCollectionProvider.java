/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class DLFileEntryTypeRelatedInfoCollectionProvider
	implements RelatedInfoItemCollectionProvider<AssetCategory, FileEntry>,
			   SingleFormVariationInfoCollectionProvider<FileEntry> {

	public DLFileEntryTypeRelatedInfoCollectionProvider(
		DLAppLocalService dlAppLocalService, DLFileEntryType dlFileEntryType) {

		_dlAppLocalService = dlAppLocalService;
		_dlFileEntryType = dlFileEntryType;
	}

	@Override
	public InfoPage<FileEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		return getFileEntryInfoPage(collectionQuery);
	}

	@Override
	public String getCollectionItemClassName() {
		return FileEntry.class.getName();
	}

	public InfoPage<FileEntry> getFileEntryInfoPage(
		CollectionQuery collectionQuery) {

		try {
			Object relatedItem = collectionQuery.getRelatedItem();

			if (!(relatedItem instanceof AssetCategory)) {
				return InfoPage.of(
					Collections.emptyList(), collectionQuery.getPagination(),
					0);
			}

			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				DLFileEntryConstants.getClassName());

			SearchContext searchContext = _buildSearchContext(
				(AssetCategory)relatedItem, collectionQuery);

			Hits hits = indexer.search(searchContext);

			List<FileEntry> fileEntries = new ArrayList<>();

			for (Document document : hits.getDocs()) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				fileEntries.add(_dlAppLocalService.getFileEntry(classPK));
			}

			return InfoPage.of(
				fileEntries, collectionQuery.getPagination(), hits.getLength());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	@Override
	public String getFormVariationKey() {
		return String.valueOf(_dlFileEntryType.getFileEntryTypeId());
	}

	@Override
	public String getKey() {
		return StringBundler.concat(
			RelatedInfoItemCollectionProvider.class.getName(), StringPool.DASH,
			DLFileEntryType.class.getName(), StringPool.DASH,
			_dlFileEntryType.getFileEntryTypeId());
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, _dlFileEntryType.getName(locale));
	}

	@Override
	public String getSourceItemClassName() {
		return AssetCategory.class.getName();
	}

	@Override
	public boolean isAvailable() {
		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if ((_dlFileEntryType.getGroupId() == 0) ||
				ArrayUtil.contains(
					SiteConnectedGroupGroupProviderUtil.
						getCurrentAndAncestorSiteAndDepotGroupIds(
							serviceContext.getScopeGroupId(), true),
					_dlFileEntryType.getGroupId())) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private SearchContext _buildSearchContext(
		AssetCategory assetCategory, CollectionQuery collectionQuery) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(true);
		searchContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});
		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"fileEntryTypeId",
				String.valueOf(_dlFileEntryType.getFileEntryTypeId())
			).put(
				"head", true
			).put(
				"latest", true
			).build());
		searchContext.setClassTypeIds(
			new long[] {_dlFileEntryType.getFileEntryTypeId()});

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		searchContext.setCompanyId(serviceContext.getCompanyId());

		Pagination pagination = collectionQuery.getPagination();

		searchContext.setEnd(pagination.getEnd());

		searchContext.setEntryClassNames(
			new String[] {DLFileEntryConstants.getClassName()});
		searchContext.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});

		Sort sort = collectionQuery.getSort();

		if (sort != null) {
			searchContext.setSorts(
				new com.liferay.portal.kernel.search.Sort(
					sort.getFieldName(),
					com.liferay.portal.kernel.search.Sort.LONG_TYPE,
					sort.isReverse()));
		}

		searchContext.setStart(pagination.getStart());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeRelatedInfoCollectionProvider.class);

	private final DLAppLocalService _dlAppLocalService;
	private final DLFileEntryType _dlFileEntryType;

}