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

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.model.CommerceProductOption;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.options.web.internal.display.context.util.CommerceProductRequestHelper;
import com.liferay.commerce.product.options.web.internal.util.CommerceProductOptionsPortletUtil;
import com.liferay.commerce.product.service.CommerceProductOptionLocalService;
import com.liferay.commerce.product.service.CommerceProductOptionValueLocalService;
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
public class CommerceProductOptionsDisplayContext {

	public CommerceProductOptionsDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceProductOptionLocalService
				commerceProductOptionLocalService,
			CommerceProductOptionValueLocalService
				commerceProductOptionValueLocalService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
        _commerceProductOptionLocalService = commerceProductOptionLocalService;
        _commerceProductOptionValueLocalService =
            commerceProductOptionValueLocalService;
        _ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_commerceProductRequestHelper = new CommerceProductRequestHelper(
			_httpServletRequest);

		_liferayPortletRequest =
			_commerceProductRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse =
			_commerceProductRequestHelper.getLiferayPortletResponse();

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);

		CommerceProductOption commerceProductOption =
			getCommerceProductOption();

		if (commerceProductOption == null) {
			RenderRequest renderRequest =
				_commerceProductRequestHelper.getRenderRequest();

			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public CommerceProductOption getCommerceProductOption() {
		if (_commerceProductOption != null) {
			return _commerceProductOption;
		}

		RenderRequest renderRequest =
			_commerceProductRequestHelper.getRenderRequest();

		_commerceProductOption =
			(CommerceProductOption)renderRequest.getAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION);

		if (_commerceProductOption != null) {
			return _commerceProductOption;
		}

		_commerceProductOption =
			_commerceProductOptionLocalService.fetchCommerceProductOption(
				getCommerceProductOptionId());

		return _commerceProductOption;
	}

	public long getCommerceProductOptionId() {
		return PrefsParamUtil.getLong(
			_commerceProductRequestHelper.getPortletPreferences(),
			_commerceProductRequestHelper.getRenderRequest(),
			"commerceProductOptionId");
	}

	public List<CommerceProductOption> getCommerceProductOptions(
			ResourceRequest request)
		throws Exception {

		String[] commerceProductOptionIds = ParamUtil.getStringValues(
			request, "rowIdsCommerceProductOption");

		List<CommerceProductOption> commerceProductOptions = new ArrayList<>();

		for (String commerceProductOptionId : commerceProductOptionIds) {
			CommerceProductOption commerceProductOption =
				_commerceProductOptionLocalService.getCommerceProductOption(
					Long.parseLong(commerceProductOptionId));

			commerceProductOptions.add(commerceProductOption);
		}

		return commerceProductOptions;
	}

	public SearchContainer<CommerceProductOption>
		getCommerceProductOptionSearchContainer() throws PortalException {

		if (_commerceProductOptionSearchContainer != null) {
			return _commerceProductOptionSearchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<CommerceProductOption> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductOption> orderByComparator =
			CommerceProductOptionsPortletUtil.
				getCommerceProductOptionOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		searchContainer.setEmptyResultsMessage("no-product-options-were-found");
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceProductOptionLocalService.getCommerceProductOptionsCount(
				themeDisplay.getScopeGroupId());

		searchContainer.setTotal(total);

		List<CommerceProductOption> results =
			_commerceProductOptionLocalService.getCommerceProductOptions(
				themeDisplay.getScopeGroupId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		_commerceProductOptionSearchContainer = searchContainer;

		return _commerceProductOptionSearchContainer;
	}

	public SearchContainer<CommerceProductOptionValue>
			getCommerceProductOptionValueSearchContainer()
		throws PortalException {

		if (_commerceProductOptionValueSearchContainer != null) {
			return _commerceProductOptionValueSearchContainer;
		}

		SearchContainer<CommerceProductOptionValue> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CommerceProductOptionValue> orderByComparator =
			CommerceProductOptionsPortletUtil.
				getCommerceProductOptionValueOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setEmptyResultsMessage(
			"no-product-option-values-were-found");

		int total =
			_commerceProductOptionValueLocalService.
				getCommerceProductOptionValuesCount(
					_commerceProductOption.getCommerceProductOptionId());

		searchContainer.setTotal(total);

		List<CommerceProductOptionValue> results =
			_commerceProductOptionValueLocalService.
				getCommerceProductOptionValues(
					_commerceProductOption.getCommerceProductOptionId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator);

		searchContainer.setResults(results);

		_commerceProductOptionValueSearchContainer = searchContainer;

		return _commerceProductOptionValueSearchContainer;
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
				CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION, "order-by-col",
				"name");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION,
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
				CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION, "order-by-type",
				"desc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(
				_httpServletRequest, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					CommerceProductWebKeys.COMMERCE_PRODUCT_OPTION,
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

		rowChecker.setRowIds("rowIdsCommerceProductOption");

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
				CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
				"display-style", "list");
		}
		else {
			portalPreferences.setValue(
				CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
				"display-style", displayStyle);

			request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		return displayStyle;
	}

	private CommerceProductOption _commerceProductOption;
	private final CommerceProductOptionLocalService
		_commerceProductOptionLocalService;
	private SearchContainer<CommerceProductOption>
		_commerceProductOptionSearchContainer;
	private final CommerceProductOptionValueLocalService
		_commerceProductOptionValueLocalService;
	private SearchContainer<CommerceProductOptionValue>
		_commerceProductOptionValueSearchContainer;
	private final CommerceProductRequestHelper _commerceProductRequestHelper;
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