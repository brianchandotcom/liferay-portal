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

package com.liferay.content.dashboard.web.internal.search.request;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class ContentDashboardSearchContextBuilder {

	public ContentDashboardSearchContextBuilder(
		HttpServletRequest httpServletRequest,
		AssetCategoryLocalService assetCategoryLocalService,
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_httpServletRequest = httpServletRequest;
		_assetCategoryLocalService = assetCategoryLocalService;
		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	public SearchContext build() {
		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAssetCategoryIds(
			ParamUtil.getLongValues(_httpServletRequest, "assetCategoryId"));
		searchContext.setAssetTagNames(
			ParamUtil.getStringValues(_httpServletRequest, "assetTagId"));

		Integer status = GetterUtil.getInteger(
			ParamUtil.getInteger(
				_httpServletRequest, "status", WorkflowConstants.STATUS_ANY));

		if (status == WorkflowConstants.STATUS_APPROVED) {
			searchContext.setAttribute("head", Boolean.TRUE);
		}
		else {
			searchContext.setAttribute("latest", Boolean.TRUE);
		}

		searchContext.setAttribute("status", status);

		searchContext.setBooleanClauses(
			_getBooleanClauses(
				ParamUtil.getLongValues(_httpServletRequest, "authorIds")));

		String[] contentDashboardItemTypePayloads =
			ParamUtil.getParameterValues(
				_httpServletRequest, "contentDashboardItemTypePayload",
				new String[0], false);

		if (!ArrayUtil.isEmpty(contentDashboardItemTypePayloads)) {
			searchContext.setClassTypeIds(
				Stream.of(
					contentDashboardItemTypePayloads
				).map(
					contentDashboardItemTypePayload -> {
						try {
							return Optional.of(
								JSONFactoryUtil.createJSONObject(
									contentDashboardItemTypePayload));
						}
						catch (JSONException jsonException) {
							_log.error(jsonException, jsonException);

							return Optional.<JSONObject>empty();
						}
					}
				).filter(
					Optional::isPresent
				).map(
					Optional::get
				).mapToLong(
					jsonObject -> jsonObject.getLong("classPK")
				).toArray());
		}

		if (_end != null) {
			searchContext.setEnd(_end);
		}

		long groupId = ParamUtil.getLong(_httpServletRequest, "scopeId");

		if (groupId > 0) {
			searchContext.setGroupIds(new long[] {groupId});
		}
		else {
			searchContext.setGroupIds(null);
		}

		searchContext.setIncludeInternalAssetCategories(true);

		if (_sort != null) {
			searchContext.setSorts(_sort);
		}

		if (_start != null) {
			searchContext.setStart(_start);
		}

		return searchContext;
	}

	public ContentDashboardSearchContextBuilder withEnd(int end) {
		_end = end;

		return this;
	}

	public ContentDashboardSearchContextBuilder withSort(Sort sort) {
		_sort = sort;

		return this;
	}

	public ContentDashboardSearchContextBuilder withStart(int start) {
		_start = start;

		return this;
	}

	private BooleanClause[] _getBooleanClauses(long[] authorIds) {
		if (ArrayUtil.isEmpty(authorIds)) {
			return new BooleanClause[0];
		}

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		BooleanFilter booleanFilter = new BooleanFilter();

		TermsFilter termsFilter = new TermsFilter(Field.USER_ID);

		for (long authorId : authorIds) {
			termsFilter.addValue(String.valueOf(authorId));
		}

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);

		booleanQueryImpl.setPreBooleanFilter(booleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQueryImpl, BooleanClauseOccur.MUST.getName())
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardSearchContextBuilder.class);

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final AssetVocabularyLocalService _assetVocabularyLocalService;
	private Integer _end;
	private final HttpServletRequest _httpServletRequest;
	private Sort _sort;
	private Integer _start;

}