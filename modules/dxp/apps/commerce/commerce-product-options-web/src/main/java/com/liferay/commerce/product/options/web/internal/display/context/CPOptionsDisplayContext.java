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

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.options.web.internal.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionsDisplayContext {

	public CPOptionsDisplayContext(
			HttpServletRequest httpServletRequest,
			CPOptionLocalService
				cpOptionLocalService,
			CPOptionValueLocalService
				cpOptionValueLocalService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
        _cpOptionLocalService = cpOptionLocalService;
        _cpOptionValueLocalService =
            cpOptionValueLocalService;
        _ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_cpRequestHelper = new CPRequestHelper(
			_httpServletRequest);

		_liferayPortletRequest =
			_cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		CPOption cpOption =
			getCPOption();

		if (cpOption == null) {
			RenderRequest renderRequest =
				_cpRequestHelper.getRenderRequest();

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public CPOption getCPOption() {
		if (_cpOption != null) {
			return _cpOption;
		}

		RenderRequest renderRequest =
			_cpRequestHelper.getRenderRequest();

		_cpOption =
			(CPOption)renderRequest.getAttribute(
				CPWebKeys.COMMERCE_PRODUCT_OPTION);

		if (_cpOption != null) {
			return _cpOption;
		}

		_cpOption =
			_cpOptionLocalService.fetchCPOption(
				getCPOptionId());

		return _cpOption;
	}

	public long getCPOptionId() {
		return PrefsParamUtil.getLong(
			_cpRequestHelper.getPortletPreferences(),
			_cpRequestHelper.getRenderRequest(),
			"cpOptionId");
	}

	public List<CPOption> getCPOptions(
			ResourceRequest request)
		throws Exception {

		String[] cpOptionIds = ParamUtil.getStringValues(
			request, "rowIdsCPOption");

		List<CPOption> cpOptions = new ArrayList<>();

		for (String cpOptionId : cpOptionIds) {
			CPOption cpOption =
				_cpOptionLocalService.getCPOption(
					Long.parseLong(cpOptionId));

			cpOptions.add(cpOption);
		}

		return cpOptions;
	}

	public SearchContainer<CPOption>
			getCPOptionSearchContainer()
		throws PortalException {

		if (_cpOptionSearchContainer != null) {
			return _cpOptionSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CPOption> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPOption> orderByComparator =
			CPOptionsPortletUtil.
				getCPOptionOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		searchContainer.setEmptyResultsMessage("no-product-options-were-found");
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_cpOptionLocalService.getCPOptionsCount(
				themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CPOption> results =
			_cpOptionLocalService.getCPOptions(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		_cpOptionSearchContainer = searchContainer;

		return _cpOptionSearchContainer;
	}

	public SearchContainer<CPOptionValue>
			getCPOptionValueSearchContainer()
		throws PortalException {

		if (_cpOptionValueSearchContainer != null) {
			return _cpOptionValueSearchContainer;
		}

		SearchContainer<CPOptionValue> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPOptionValue> orderByComparator =
			CPOptionsPortletUtil.
				getCPOptionValueOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage(
			"no-product-option-values-were-found");

		int total =
			_cpOptionValueLocalService.
				getCPOptionValuesCount(
					_cpOption.getCPOptionId());

		searchContainer.setTotal(total);

		List<CPOptionValue> results =
			_cpOptionValueLocalService.
				getCPOptionValues(
					_cpOption.getCPOptionId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

		searchContainer.setResults(results);

		_cpOptionValueSearchContainer = searchContainer;

		return _cpOptionValueSearchContainer;
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

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				_httpServletRequest, _portalPreferences);
		}

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				CPWebKeys.COMMERCE_PRODUCT_OPTION, "order-by-col",
				"name");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CPWebKeys.COMMERCE_PRODUCT_OPTION,
					"order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				CPWebKeys.COMMERCE_PRODUCT_OPTION, "order-by-type",
				"desc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CPWebKeys.COMMERCE_PRODUCT_OPTION,
					"order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			_liferayPortletResponse);

		rowChecker.setRowIds("rowIdsCPOption");

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowInfoPanel() {
		if (isSearch()) {
			return false;
		}

		return true;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, PortalPreferences portalPreferences) {

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				CPPortletKeys.COMMERCE_PRODUCT_OPTIONS,
				"display-style", "list");
		}
		else {
			portalPreferences.setValue(
				CPPortletKeys.COMMERCE_PRODUCT_OPTIONS,
				"display-style", displayStyle);

			request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	private CPOption _cpOption;
	private final CPOptionLocalService
		_cpOptionLocalService;
	private SearchContainer<CPOption>
		_cpOptionSearchContainer;
	private final CPOptionValueLocalService
		_cpOptionValueLocalService;
	private SearchContainer<CPOptionValue>
		_cpOptionValueSearchContainer;
	private final CPRequestHelper _cpRequestHelper;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private RowChecker _rowChecker;

}