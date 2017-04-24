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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.internal.util.CommerceProductDefinitionsPortletUtil;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionRelService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceProductDefinitionOptionRelDisplayContext
	extends BaseCommerceProductDefinitionsDisplayContext {

	public CommerceProductDefinitionOptionRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceProductDefinitionOptionRelService
				commerceProductDefinitionOptionRelService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			"rowIdsCommerceProductDefinitionOptionRel",
			"CommerceProductDefinitionOptionRel");

		setDefaultorderbytype("desc");

		_commerceProductDefinitionOptionRelService =
			commerceProductDefinitionOptionRelService;

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
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

	public SearchContainer
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CommerceProductDefinitionOptionRel> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductDefinitionOptionRel>
			orderByComparator =
				CommerceProductDefinitionsPortletUtil.
					getCommerceProductDefinitionOptionRelOrderByComparator(
						getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage("no-product-options-were-found");
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductDefinitionOptionRelService.
				getCommerceProductDefinitionOptionRelsCount(
					getCommerceProductDefinitionId());

		searchContainer.setTotal(total);

		List<CommerceProductDefinitionOptionRel> results =
			_commerceProductDefinitionOptionRelService.
				getCommerceProductDefinitionOptionRels(
					getCommerceProductDefinitionId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private final CommerceProductDefinitionOptionRelService
		_commerceProductDefinitionOptionRelService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;

}