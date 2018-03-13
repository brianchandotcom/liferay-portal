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

package com.liferay.commerce.tax.category.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommerceTaxCategoryItemSelectorCriterion;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceTaxCategoryRelService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxCategoryRelDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext
		<CommerceTaxCategoryRel> {

	public CommerceTaxCategoryRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceTaxCategoryRelService commerceTaxCategoryRelService,
			ItemSelector itemSelector)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CommerceTaxCategoryRel.class.getSimpleName());

		_commerceTaxCategoryRelService = commerceTaxCategoryRelService;
		_itemSelector = itemSelector;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommerceTaxCategoryItemSelectorCriterion
			commerceTaxCategoryItemSelectorCriterion =
				new CommerceTaxCategoryItemSelectorCriterion();

		commerceTaxCategoryItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "taxCategoriesSelectItem",
			commerceTaxCategoryItemSelectorCriterion);

		String checkedCommerceTaxCategoryIds = StringUtil.merge(
			getCheckedCommerceTaxCategoryIds());

		itemSelectorURL.setParameter(
			"checkedCommerceTaxCategoryIds", checkedCommerceTaxCategoryIds);

		return itemSelectorURL.toString();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION;
	}

	@Override
	public SearchContainer<CommerceTaxCategoryRel> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		String emptyResultsMessage = "there-are-no-tax-categories";

		_searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceTaxCategoryRel> orderByComparator =
			CommerceUtil.getCommerceTaxCategoryRelOrderByComparator(
				orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);
		_searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceTaxCategoryRelService.getCommerceTaxCategoryRelsCount(
				CPDefinition.class.getName(), getCPDefinitionId());

		List<CommerceTaxCategoryRel> results =
			_commerceTaxCategoryRelService.getCommerceTaxCategoryRels(
				CPDefinition.class.getName(), getCPDefinitionId(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected long[] getCheckedCommerceTaxCategoryIds() throws PortalException {
		List<Long> commerceTaxCategoryIdsList = new ArrayList<>();

		List<CommerceTaxCategoryRel> commerceTaxCategoryRels =
			_commerceTaxCategoryRelService.getCommerceTaxCategoryRels(
				CPDefinition.class.getName(), getCPDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CommerceTaxCategoryRel commerceTaxCategoryRel :
				commerceTaxCategoryRels) {

			commerceTaxCategoryIdsList.add(
				commerceTaxCategoryRel.getCommerceTaxCategoryId());
		}

		if (!commerceTaxCategoryIdsList.isEmpty()) {
			return ArrayUtil.toLongArray(commerceTaxCategoryIdsList);
		}

		return new long[0];
	}

	private final CommerceTaxCategoryRelService _commerceTaxCategoryRelService;
	private final ItemSelector _itemSelector;
	private SearchContainer<CommerceTaxCategoryRel> _searchContainer;

}