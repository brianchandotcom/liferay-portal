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

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.item.selector.criterion.CPOptionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPDefinitionOptionRelDisplayContext extends
	BaseCPDefinitionsSearchContainerDisplayContext<CPDefinitionOptionRel> {

	public CPDefinitionOptionRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPDefinitionOptionRel.class.getSimpleName());

		setDefaultOrderByCol("priority");

		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_itemSelector = itemSelector;
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel()
		throws PortalException {

		if (_cpDefinitionOptionRel != null) {
			return _cpDefinitionOptionRel;
		}

		_cpDefinitionOptionRel = actionHelper.getCPDefinitionOptionRel(
			cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionRel;
	}

	public long getCPDefinitionOptionRelId() throws PortalException {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			getCPDefinitionOptionRel();

		if (cpDefinitionOptionRel == null) {
			return 0;
		}

		return cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		Stream<DDMFormFieldType> stream = ddmFormFieldTypes.stream();

		return stream.filter(
			fieldType -> {
				Map<String, Object> properties =
					_ddmFormFieldTypeServicesTracker.
						getDDMFormFieldTypeProperties(fieldType.getName());

				return !MapUtil.getBoolean(
					properties, "ddm.form.field.type.system");
			}
		).collect(
			Collectors.toList()
		);
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPOptionItemSelectorCriterion cpOptionItemSelectorCriterion =
			new CPOptionItemSelectorCriterion();

		cpOptionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productOptionsSelectItem",
			cpOptionItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_OPTIONS;
	}

	@Override
	public SearchContainer<CPDefinitionOptionRel> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-options-were-found");

		OrderByComparator<CPDefinitionOptionRel> orderByComparator =
			CPDefinitionsPortletUtil.getCPDefinitionOptionRelOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPDefinitionOptionRelSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPDefinitionOptionRel>
				cpDefinitionOptionRelBaseModelSearchResult =
					_cpDefinitionOptionRelService.searchCPDefinitionOptionRels(
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId(), getCPDefinitionId(),
						getKeywords(), searchContainer.getStart(),
						searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpDefinitionOptionRelBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpDefinitionOptionRelBaseModelSearchResult.getBaseModels());
		}
		else {
			int total =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRelsCount(
					getCPDefinitionId());

			searchContainer.setTotal(total);

			List<CPDefinitionOptionRel> results =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRels(
					getCPDefinitionId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CPDefinitionOptionRel.class.getName(),
			getCPDefinitionOptionRelId(), null);
	}

	private CPDefinitionOptionRel _cpDefinitionOptionRel;
	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final ItemSelector _itemSelector;

}