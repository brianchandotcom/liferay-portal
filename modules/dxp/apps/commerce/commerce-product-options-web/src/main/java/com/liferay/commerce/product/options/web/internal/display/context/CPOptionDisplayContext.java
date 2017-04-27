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
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionDisplayContext extends BaseCPOptionsDisplayContext {

	public CPOptionDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPOptionService cpOptionService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		super(actionHelper, httpServletRequest, "rowIdsCPOption", "CPOption");

		_cpOptionService = cpOptionService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	public List<CPOption> getCommerceProductOptions(ResourceRequest request)
		throws Exception {

		String[] cpOptionIds = ParamUtil.getStringValues(
			request, "rowIdsCPOption");

		List<CPOption> cpOptions = new ArrayList<>();

		for (String cpOptionId : cpOptionIds) {
			CPOption cpOption = _cpOptionService.getCPOption(
				Long.parseLong(cpOptionId));

			cpOptions.add(cpOption);
		}

		return cpOptions;
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		Stream<DDMFormFieldType> stream =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes().stream();

		stream = stream.filter(
			fieldType -> {
				Map<String, Object> properties =
					_ddmFormFieldTypeServicesTracker.
						getDDMFormFieldTypeProperties(fieldType.getName());

				return !MapUtil.getBoolean(
					properties, "ddm.form.field.type.system");
			});

		List<DDMFormFieldType> formFieldTypes = stream.collect(
			Collectors.toList());

		return formFieldTypes;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (searchContainer != null) {
			return searchContainer;
		}

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

		int total = _cpOptionService.getCPOptionsCount(getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPOption> results = _cpOptionService.getCPOptions(
			getScopeGroupId(), searchContainer.getStart(),
			searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CPOptionService _cpOptionService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}