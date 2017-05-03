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

package com.liferay.commerce.product.options.web.internal.display.context;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.options.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.search.CPSearcher;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPOptionDisplayContext
	extends BaseCPOptionsDisplayContext<CPOption> {

	public CPOptionDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionService cpOptionService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPOption");

		_cpOptionService = cpOptionService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		Stream<DDMFormFieldType> stream = ddmFormFieldTypes.stream().filter(
			fieldType -> {
				Map<String, Object> properties =
					_ddmFormFieldTypeServicesTracker.
						getDDMFormFieldTypeProperties(fieldType.getName());

				return !MapUtil.getBoolean(
					properties, "ddm.form.field.type.system");
			});

		return stream.collect(Collectors.toList());
	}

	@Override
	public SearchContainer<CPOption> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer<CPOption> searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-options-were-found");

		OrderByComparator<CPOption> orderByComparator =
			CPOptionsPortletUtil.getCPOptionOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());


		if(isSearch()) {

			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			Sort sort = null;

			if (Objects.equals(getOrderByCol(), "name")) {
				sort = new Sort("name", Sort.STRING_TYPE, orderByAsc);
			}

			LinkedHashMap<String, Object> params = new LinkedHashMap<>();

			params.put("expandoAttributes", getKeywords());

			Indexer indexer = CPSearcher.getInstance();

			SearchContext searchContext = buildSearchContext(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				CPOption.class.getName(), getKeywords(),
				params, searchContainer.getStart(),
				searchContainer.getEnd(), sort);

			Hits hits = indexer.search(searchContext);

			int total = hits.getLength();

			searchContainer.setTotal(total);

			List results = new ArrayList<>();

			Document[] documents = hits.getDocs();

			for (int i = 0; i < documents.length; i++) {
				Document document = documents[i];

				CPOption cpOption = null;
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				cpOption =
					_cpOptionService.getCPOption(classPK);

				results.add(cpOption);
			}

			searchContainer.setResults(results);

		}
		else {
			int total = _cpOptionService.getCPOptionsCount(getScopeGroupId());

			searchContainer.setTotal(total);

			List<CPOption> results = _cpOptionService.getCPOptions(
				getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}
		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId,
		String className,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end, Sort sort) {

		String cpOptionId = null;
		String name = null;
		String description = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			cpOptionId = keywords;
			name = keywords;
			description = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andOperator);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.CLASS_PK, cpOptionId);
		attributes.put(Field.ENTRY_CLASS_NAME, className);
		attributes.put(Field.DESCRIPTION, description);
		attributes.put(Field.NAME, name);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (params != null) {
			keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		searchContext.setAttribute("params", params);
		searchContext.setEnd(end);
		searchContext.setStart(start);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private final CPOptionService _cpOptionService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}