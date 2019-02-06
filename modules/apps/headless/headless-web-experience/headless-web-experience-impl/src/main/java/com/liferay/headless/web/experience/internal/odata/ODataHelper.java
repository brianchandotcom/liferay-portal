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

package com.liferay.headless.web.experience.internal.odata;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = ODataHelper.class)
public class ODataHelper {

	public List<JournalArticle> getJournalArticles(
			Group group, Locale locale, Pagination pagination, String filter,
			String sort)
		throws PortalException {

		return _journalHelper.getArticles(
			getHits(group, locale, pagination, filter, sort));
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + StructuredContentEntityModel.NAME + ")",
		unbind = "unbind"
	)
	public void setFilterParser(FilterParser filterParser) {
		_filterParser = filterParser;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + StructuredContentEntityModel.NAME + ")",
		unbind = "unbind"
	)
	public void setSortParser(SortParser sortParser) {
		_sortParser = sortParser;
	}

	public void unbind(FilterParser filterParser) {
		_filterParser = null;
	}

	public void unbind(SortParser sortParser) {
		_sortParser = null;
	}

	protected Hits getHits(
			Group group, Locale locale, Pagination pagination,
			String filterString, String sortString)
		throws SearchException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		SearchContext searchContext = _createSearchContext(
			group, pagination, permissionChecker, locale, sortString);

		Query query = getQuery(locale, searchContext, filterString);

		if (permissionChecker != null) {
			SearchResultPermissionFilter searchResultPermissionFilter =
				_searchResultPermissionFilterFactory.create(
					searchContext1 -> IndexSearcherHelperUtil.search(
						searchContext1, query),
					permissionChecker);

			return searchResultPermissionFilter.search(searchContext);
		}

		return IndexSearcherHelperUtil.search(searchContext, query);
	}

	protected Query getQuery(
			Locale locale, SearchContext searchContext, String filter)
		throws SearchException {

		Indexer<JournalArticle> indexer = _indexerRegistry.nullSafeGetIndexer(
			JournalArticle.class);

		BooleanQuery booleanQuery = indexer.getFullQuery(searchContext);

		com.liferay.portal.kernel.search.filter.Filter searchFilter =
			_getSearchFilter(_getFilter(filter), locale);

		if (searchFilter != null) {
			BooleanFilter preBooleanFilter = booleanQuery.getPreBooleanFilter();

			preBooleanFilter.add(searchFilter, BooleanClauseOccur.MUST);
		}

		return booleanQuery;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(entity.model.name=" + StructuredContentEntityModel.NAME + ")",
		unbind = "unbind"
	)
	protected void setEntityModel(EntityModel entityModel) {
		_entityModel = entityModel;
	}

	protected void unbind(EntityModel entityModel) {
		_entityModel = null;
	}

	private SearchContext _createSearchContext(
		Group group, Pagination pagination, PermissionChecker permissionChecker,
		Locale locale, String sortString) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASSNAME_ID_DEFAULT);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute(
			Field.STATUS, WorkflowConstants.STATUS_APPROVED);
		searchContext.setCompanyId(group.getCompanyId());
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setGroupIds(new long[] {group.getGroupId()});
		searchContext.setStart(pagination.getStartPosition());

		if ((permissionChecker != null) && (searchContext.getUserId() == 0)) {
			searchContext.setUserId(permissionChecker.getUserId());
		}

		List<com.liferay.portal.kernel.search.Sort> sorts = _getSorts(
			sortString, locale);

		if (!sorts.isEmpty()) {
			searchContext.setSorts(
				sorts.toArray(new com.liferay.portal.kernel.search.Sort[0]));
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setSelectedFieldNames(
			Field.ARTICLE_ID, Field.SCOPE_GROUP_ID);

		return searchContext;
	}

	private Filter _getFilter(String filterString) {
		if (Validator.isNull(filterString)) {
			return Filter.emptyFilter();
		}

		try {
			return new Filter(_filterParser.parse(filterString));
		}
		catch (Exception e) {
			throw new InvalidFilterException(
				String.format(
					"Invalid query computed from filter '%s': %s", filterString,
					e.getMessage()),
				e);
		}
	}

	private com.liferay.portal.kernel.search.filter.Filter _getSearchFilter(
		Filter filter, Locale locale) {

		if ((filter == null) || (filter == Filter.emptyFilter())) {
			return null;
		}

		try {
			return _expressionConvert.convert(
				filter.getExpression(), locale, _entityModel);
		}
		catch (Exception e) {
			throw new BadRequestException(
				"Invalid filter: " + e.getMessage(), e);
		}
	}

	private Sort _getSort(String sortString) {
		if (Validator.isNull(sortString)) {
			return Sort.emptySort();
		}

		try {
			return new Sort(_sortParser.parse(sortString));
		}
		catch (Exception e) {
			throw new InvalidSortException(
				String.format(
					"Invalid query computed from sort '%s': %s", sortString,
					e.getMessage()),
				e);
		}
	}

	private List<com.liferay.portal.kernel.search.Sort> _getSorts(
		String sortString, Locale locale) {

		Sort sort = _getSort(sortString);

		List<SortField> sortFields = sort.getSortFields();

		Stream<SortField> stream = sortFields.stream();

		return stream.map(
			sortField -> new com.liferay.portal.kernel.search.Sort(
				sortField.getSortableFieldName(locale),
				!sortField.isAscending())
		).collect(
			Collectors.toList()
		);
	}

	private volatile EntityModel _entityModel;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	private FilterParser _filterParser;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

	private SortParser _sortParser;

}