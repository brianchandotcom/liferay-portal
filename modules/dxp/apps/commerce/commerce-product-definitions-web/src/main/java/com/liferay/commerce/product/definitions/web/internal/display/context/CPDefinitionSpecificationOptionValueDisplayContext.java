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

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.item.selector.criterion.CPSpecificationOptionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionSpecificationOptionValueDisplayContext extends
	BaseCPDefinitionsSearchContainerDisplayContext
		<CPDefinitionSpecificationOptionValue> {

	public CPDefinitionSpecificationOptionValueDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionSpecificationOptionValueService
				cpDefinitionSpecificationOptionValueService,
			CPOptionCategoryService cpOptionCategoryService,
			ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPDefinitionSpecificationOptionValue.class.getSimpleName());

		setDefaultOrderByCol("priority");

		_cpDefinitionSpecificationOptionValueService =
			cpDefinitionSpecificationOptionValueService;
		_cpOptionCategoryService = cpOptionCategoryService;
		_itemSelector = itemSelector;
	}

	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue()
		throws PortalException {

		if (_cpDefinitionSpecificationOptionValue != null) {
			return _cpDefinitionSpecificationOptionValue;
		}

		_cpDefinitionSpecificationOptionValue =
			actionHelper.getCPDefinitionSpecificationOptionValue(
				cpRequestHelper.getRenderRequest());

		return _cpDefinitionSpecificationOptionValue;
	}

	public long getCPDefinitionSpecificationOptionValueId()
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				getCPDefinitionSpecificationOptionValue();

		if (cpDefinitionSpecificationOptionValue == null) {
			return 0;
		}

		return cpDefinitionSpecificationOptionValue.
			getCPDefinitionSpecificationOptionValueId();
	}

	public List<CPOptionCategory> getCPOptionCategories() {
		return _cpOptionCategoryService.getCPOptionCategories(
			getScopeGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public String getCPOptionCategoryTitle(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		long cpOptionCategoryId =
			cpDefinitionSpecificationOptionValue.getCPOptionCategoryId();

		if (cpOptionCategoryId ==
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			CPOptionCategory cpOptionCategory =
				_cpOptionCategoryService.getCPOptionCategory(
					cpOptionCategoryId);

			return cpOptionCategory.getTitle(themeDisplay.getLocale());
		}
		catch (PrincipalException pe) {
		}

		return StringPool.BLANK;
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPSpecificationOptionItemSelectorCriterion
			cpSpecificationOptionItemSelectorCriterion =
				new CPSpecificationOptionItemSelectorCriterion();

		cpSpecificationOptionItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			"productSpecificationOptionsSelectItem",
			cpSpecificationOptionItemSelectorCriterion);

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
		return CPDefinitionScreenNavigationConstants.
			CATEGORY_KEY_SPECIFICATION_OPTIONS;
	}

	@Override
	public SearchContainer<CPDefinitionSpecificationOptionValue>
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-specifications-were-found");

		OrderByComparator<CPDefinitionSpecificationOptionValue>
			orderByComparator =
				CPDefinitionsPortletUtil.
					getCPDefinitionSpecificationOptionValueOrderByComparator(
						getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		List<CPDefinitionSpecificationOptionValue> results =
			_cpDefinitionSpecificationOptionValueService.
				getCPDefinitionSpecificationOptionValues(getCPDefinitionId());

		searchContainer.setTotal(results.size());

		Collections.sort(results, orderByComparator);

		results = ListUtil.subList(
			results, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	private CPDefinitionSpecificationOptionValue
		_cpDefinitionSpecificationOptionValue;
	private final CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;
	private final CPOptionCategoryService _cpOptionCategoryService;
	private final ItemSelector _itemSelector;

}