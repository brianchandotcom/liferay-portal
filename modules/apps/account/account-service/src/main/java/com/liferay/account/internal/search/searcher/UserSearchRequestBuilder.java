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

package com.liferay.account.internal.search.searcher;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = UserSearchRequestBuilder.class)
public class UserSearchRequestBuilder {

	public UserSearchRequestBuilder attributes(
		Map<String, Serializable> attributes) {

		_attributes = attributes;

		return this;
	}

	public SearchRequest build() {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			User.class.getName()
		).withSearchContext(
			searchContext -> _populateSearchContext(searchContext)
		).emptySearchEnabled(
			true
		).highlightEnabled(
			false
		);

		if (_cur != QueryUtil.ALL_POS) {
			searchRequestBuilder.from(_cur);
			searchRequestBuilder.size(_delta);
		}

		if (Validator.isNotNull(_sortField)) {
			SortOrder sortOrder = SortOrder.ASC;

			if (_reverse) {
				sortOrder = SortOrder.DESC;
			}

			FieldSort fieldSort = _sorts.field(
				_sortFieldBuilder.getSortField(
					User.class.getName(), _sortField),
				sortOrder);

			searchRequestBuilder.sorts(fieldSort);
		}

		return searchRequestBuilder.build();
	}

	public UserSearchRequestBuilder cur(int cur) {
		_cur = cur;

		return this;
	}

	public UserSearchRequestBuilder delta(int delta) {
		_delta = delta;

		return this;
	}

	public UserSearchRequestBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	public UserSearchRequestBuilder reverse(boolean reverse) {
		_reverse = reverse;

		return this;
	}

	public UserSearchRequestBuilder sortField(String sortField) {
		_sortField = sortField;

		return this;
	}

	public UserSearchRequestBuilder status(int status) {
		_status = status;

		return this;
	}

	private void _populateSearchContext(SearchContext searchContext) {
		boolean andSearch = false;

		if (Validator.isNull(_keywords)) {
			andSearch = true;
		}
		else {
			searchContext.setKeywords(_keywords);
		}

		searchContext.setAndSearch(andSearch);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, _status
			).put(
				"city", _keywords
			).put(
				"country", _keywords
			).put(
				"firstName", _keywords
			).put(
				"fullName", _keywords
			).put(
				"lastName", _keywords
			).put(
				"middleName", _keywords
			).put(
				"params", new LinkedHashMap<>()
			).put(
				"region", _keywords
			).put(
				"screenName", _keywords
			).put(
				"street", _keywords
			).put(
				"zip", _keywords
			).putAll(
				_attributes
			).build());

		searchContext.setCompanyId(CompanyThreadLocal.getCompanyId());
	}

	private Map<String, Serializable> _attributes = new HashMap<>();
	private int _cur;
	private int _delta;
	private String _keywords;
	private boolean _reverse;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private String _sortField;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

	private int _status;

}