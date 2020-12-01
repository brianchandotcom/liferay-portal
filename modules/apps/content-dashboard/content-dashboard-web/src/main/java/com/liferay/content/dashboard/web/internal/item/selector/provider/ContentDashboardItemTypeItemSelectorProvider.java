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

package com.liferay.content.dashboard.web.internal.item.selector.provider;

import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemType;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemTypeUtil;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardItemTypeChecker;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemTypeItemSelectorProvider.class)
public class ContentDashboardItemTypeItemSelectorProvider {

	public SearchContainer<ContentDashboardItemType> getSearchContainer(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortletURL portletURL) {

		SearchContainer<ContentDashboardItemType> searchContainer =
			new SearchContainer<>(
				portletRequest, portletURL, null, "there-is-no-subtype");

		searchContainer.setOrderByCol(_getOrderByCol(portletRequest));
		searchContainer.setOrderByType(_getOrderByType(portletRequest));

		SearchResponse searchResponse = _getSearchResponse(
			portletRequest, searchContainer.getEnd(),
			searchContainer.getStart());

		searchContainer.setResults(
			_toContentDashboardItemTypes(searchResponse.getDocuments71()));

		searchContainer.setRowChecker(
			new ContentDashboardItemTypeChecker(
				_getCheckedContentDashboardItemTypes(portletRequest),
				(RenderResponse)portletResponse));
		searchContainer.setTotal(searchResponse.getTotalHits());

		return searchContainer;
	}

	private BooleanClause[] _getBooleanClauses(String keywords, Locale locale) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter booleanFilter = new BooleanFilter();

		TermsFilter classNameIdTermsFilter = new TermsFilter(
			Field.CLASS_NAME_ID);

		Collection<Long> classIds =
			_contentDashboardItemFactoryTracker.getClassIds();

		for (Long classId : classIds) {
			classNameIdTermsFilter.addValue(String.valueOf(classId));
		}

		booleanFilter.add(classNameIdTermsFilter, BooleanClauseOccur.MUST);

		if (Validator.isNotNull(keywords)) {
			String sortableKeywords = StringUtil.replace(
				StringUtil.toLowerCase(keywords), ' ', '*');

			WildcardQuery wildcardQuery = new WildcardQueryImpl(
				Field.getSortableFieldName(
					"localized_name_".concat(LocaleUtil.toLanguageId(locale))),
				StringPool.STAR + sortableKeywords + StringPool.STAR);

			booleanQueryImpl.add(wildcardQuery, BooleanClauseOccur.MUST);
		}

		booleanQueryImpl.setPreBooleanFilter(booleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
	}

	private List<? extends ContentDashboardItemType>
		_getCheckedContentDashboardItemTypes(PortletRequest portletRequest) {

		String[] checkedContentDashboardItemTypes =
			ParamUtil.getParameterValues(
				portletRequest, "checkedContentDashboardItemTypes", null,
				false);

		if (ArrayUtil.isEmpty(checkedContentDashboardItemTypes)) {
			return Collections.emptyList();
		}

		return Stream.of(
			checkedContentDashboardItemTypes
		).map(
			checkedContentDashboardItemType ->
				ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
					_contentDashboardItemTypeFactoryTracker,
					checkedContentDashboardItemType)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	private String _getKeywords(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "keywords");
	}

	private String _getOrderByCol(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");
	}

	private String _getOrderByType(PortletRequest portletRequest) {
		return ParamUtil.getString(
			portletRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	private SearchResponse _getSearchResponse(
		PortletRequest portletRequest, int end, int start) {

		Collection<String> classNames =
			_contentDashboardItemTypeFactoryTracker.getClassNames();

		return _searcher.search(
			_searchRequestBuilderFactory.builder(
			).emptySearchEnabled(
				true
			).entryClassNames(
				classNames.toArray(new String[0])
			).fields(
				Field.ENTRY_CLASS_NAME, Field.CLASS_TYPE_ID,
				Field.CLASS_NAME_ID, Field.ENTRY_CLASS_PK, Field.UID
			).highlightEnabled(
				false
			).withSearchContext(
				searchContext -> {
					searchContext.setBooleanClauses(
						_getBooleanClauses(
							_getKeywords(portletRequest),
							_portal.getLocale(portletRequest)));
					searchContext.setCompanyId(
						_portal.getCompanyId(portletRequest));
					searchContext.setEnd(end);
					searchContext.setSorts(_getSort(portletRequest));
					searchContext.setStart(start);
				}
			).build());
	}

	private Sort _getSort(PortletRequest portletRequest) {
		boolean orderByAsc = false;

		String orderByType = _getOrderByType(portletRequest);

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		String orderByCol = _getOrderByCol(portletRequest);

		if (orderByCol.equals("title")) {
			String sortFieldName = Field.getSortableFieldName(
				"localized_name_".concat(
					LocaleUtil.toLanguageId(
						_portal.getLocale(portletRequest))));

			return new Sort(sortFieldName, Sort.STRING_TYPE, !orderByAsc);
		}

		return new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
	}

	private List<ContentDashboardItemType> _toContentDashboardItemTypes(
		List<Document> documents) {

		Stream<Document> stream = documents.stream();

		return stream.map(
			document ->
				ContentDashboardItemTypeUtil.toContentDashboardItemTypeOptional(
					_contentDashboardItemTypeFactoryTracker, document)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardItemTypeFactoryTracker
		_contentDashboardItemTypeFactoryTracker;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}