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

package com.liferay.content.dashboard.web.internal.dao.search;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItem;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardSearchContextBuilder;
import com.liferay.content.dashboard.web.internal.searcher.ContentDashboardSearchRequestBuilderFactory;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardSearchClassNameUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Cristina González
 */
public class ContentDashboardItemSearchContainerFactory {

	public static ContentDashboardItemSearchContainerFactory getInstance(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		ContentDashboardItemFactoryTracker contentDashboardItemFactoryTracker,
		ContentDashboardSearchRequestBuilderFactory
			contentDashboardSearchRequestBuilderFactory,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, Searcher searcher) {

		return new ContentDashboardItemSearchContainerFactory(
			assetCategoryLocalService, assetVocabularyLocalService,
			contentDashboardItemFactoryTracker,
			contentDashboardSearchRequestBuilderFactory, portal, renderRequest,
			renderResponse, searcher);
	}

	public SearchContainer<ContentDashboardItem<?>> create()
		throws PortletException {

		SearchContainer<ContentDashboardItem<?>> searchContainer =
			new SearchContainer<>(
				_renderRequest,
				PortletURLUtil.clone(
					PortletURLUtil.getCurrent(_renderRequest, _renderResponse),
					_renderResponse),
				null, "there-is-no-content");

		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());

		SearchResponse searchResponse = _getSearchResponse(
			searchContainer.getEnd(), searchContainer.getStart());

		searchContainer.setResults(
			_getContentDashboardItems(searchResponse.getDocuments71()));
		searchContainer.setTotal(searchResponse.getTotalHits());

		return searchContainer;
	}

	private ContentDashboardItemSearchContainerFactory(
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService,
		ContentDashboardItemFactoryTracker contentDashboardItemFactoryTracker,
		ContentDashboardSearchRequestBuilderFactory
			contentDashboardSearchRequestBuilderFactory,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, Searcher searcher) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
		_contentDashboardItemFactoryTracker =
			contentDashboardItemFactoryTracker;
		_contentDashboardSearchRequestBuilderFactory =
			contentDashboardSearchRequestBuilderFactory;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_searcher = searcher;

		_locale = _portal.getLocale(_renderRequest);
	}

	private List<ContentDashboardItem<?>> _getContentDashboardItems(
		List<Document> documents) {

		Stream<Document> stream = documents.stream();

		return stream.map(
			this::_toContentDashboardItemOptional
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");
	}

	private String _getOrderByType() {
		String orderByCol = _getOrderByCol();

		String orderByType = ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM);

		if (Objects.equals(orderByCol, "title")) {
			if (Objects.equals("desc", orderByType)) {
				return "desc";
			}

			return "asc";
		}

		if (Objects.equals("asc", orderByType)) {
			return "asc";
		}

		return "desc";
	}

	private SearchResponse _getSearchResponse(int end, int start) {
		return _searcher.search(
			_contentDashboardSearchRequestBuilderFactory.builder(
				new ContentDashboardSearchContextBuilder(
					_portal.getHttpServletRequest(_renderRequest),
					_assetCategoryLocalService, _assetVocabularyLocalService
				).withEnd(
					end
				).withSort(
					_getSort(_getOrderByCol(), _getOrderByType())
				).withStart(
					start
				).build()
			).build());
	}

	private Sort _getSort(String orderByCol, String orderByType) {
		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("title")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_title_".concat(LocaleUtil.toLanguageId(_locale)));

			return new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
		}

		return new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
	}

	private Optional<ContentDashboardItem<?>> _toContentDashboardItemOptional(
		ContentDashboardItemFactory<?> contentDashboardItemFactory,
		Document document) {

		try {
			return Optional.of(
				contentDashboardItemFactory.create(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Optional.empty();
		}
	}

	private Optional<ContentDashboardItem<?>> _toContentDashboardItemOptional(
		Document document) {

		Optional<ContentDashboardItemFactory<?>>
			contentDashboardItemFactoryOptional =
				_contentDashboardItemFactoryTracker.
					getContentDashboardItemFactoryOptional(
						ContentDashboardSearchClassNameUtil.getClassName(
							document.get(Field.ENTRY_CLASS_NAME)));

		return contentDashboardItemFactoryOptional.flatMap(
			contentDashboardItemFactory -> _toContentDashboardItemOptional(
				contentDashboardItemFactory, document));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemSearchContainerFactory.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private final ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;
	private final ContentDashboardSearchRequestBuilderFactory
		_contentDashboardSearchRequestBuilderFactory;
	private final Locale _locale;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final Searcher _searcher;

}