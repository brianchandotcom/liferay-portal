/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.info.collection.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = RelatedInfoItemCollectionProvider.class)
public class BlogsEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider
	implements RelatedInfoItemCollectionProvider<AssetCategory, BlogsEntry> {

	@Override
	public InfoPage<BlogsEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		if (!(relatedItem instanceof AssetCategory)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(collectionQuery);

		try {
			List<BlogsEntry> blogsEntries = new ArrayList<>();

			AssetCategory assetCategory = (AssetCategory)relatedItem;

			SearchContext searchContext = _getSearchContext(assetCategory);

			Hits hits = _assetHelper.search(
				searchContext, assetEntryQuery, assetEntryQuery.getStart(),
				assetEntryQuery.getEnd());

			List<SearchResult> searchResults =
				SearchResultUtil.getSearchResults(
					hits, LocaleUtil.getDefault());

			for (SearchResult searchResult : searchResults) {
				BlogsEntry blogsEntry = _toBlogsEntry(searchResult);

				if (blogsEntry == null) {
					continue;
				}

				blogsEntries.add(blogsEntry);
			}

			Long count = _assetHelper.searchCount(
				searchContext, assetEntryQuery);

			return InfoPage.of(
				blogsEntries, collectionQuery.getPagination(),
				count.intValue());
		}
		catch (Exception exception) {
			_log.error("Unable to get blogs entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "blogs-with-this-category");
	}

	private AssetEntryQuery _getAssetEntryQuery(
		CollectionQuery collectionQuery) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		assetEntryQuery.setClassNameIds(
			new long[] {_portal.getClassNameId(BlogsEntry.class.getName())});
		assetEntryQuery.setEnablePermissions(true);

		Pagination pagination = collectionQuery.getPagination();

		if (pagination != null) {
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});
		assetEntryQuery.setOrderByCol1(Field.MODIFIED_DATE);

		Sort sort = collectionQuery.getSort();

		if ((sort != null) && sort.isReverse()) {
			assetEntryQuery.setOrderByType1("ASC");
		}
		else {
			assetEntryQuery.setOrderByType1("DESC");
		}

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
		}

		return assetEntryQuery;
	}

	private SearchContext _getSearchContext(AssetCategory assetCategory) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return SearchContextFactory.getInstance(
			new long[] {assetCategory.getCategoryId()}, new String[0],
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).put(
				"head", true
			).put(
				"latest", true
			).build(),
			serviceContext.getCompanyId(), null, themeDisplay.getLayout(), null,
			serviceContext.getScopeGroupId(), null, serviceContext.getUserId());
	}

	private BlogsEntry _toBlogsEntry(SearchResult searchResult) {
		try {
			return _blogsEntryService.getEntry(searchResult.getClassPK());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Blogs search index is stale and contains entry " +
						searchResult.getClassPK(),
					exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntriesWithSameAssetCategoryRelatedInfoItemCollectionProvider.
			class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}