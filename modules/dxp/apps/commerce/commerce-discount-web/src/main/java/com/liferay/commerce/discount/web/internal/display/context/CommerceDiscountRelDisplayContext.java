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

package com.liferay.commerce.discount.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelService;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.discount.util.comparator.CommerceDiscountRelCreateDateComparator;
import com.liferay.commerce.discount.web.internal.util.CommerceDiscountPortletUtil;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRelDisplayContext
	extends CommerceDiscountDisplayContext {

	public CommerceDiscountRelDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceDiscountRelService commerceDiscountRelService,
		CommerceDiscountService commerceDiscountService,
		CommerceDiscountTargetRegistry commerceDiscountTargetRegistry,
		CommerceDiscountUserSegmentRelService
			commerceDiscountUserSegmentRelService,
		CommercePriceFormatter commercePriceFormatter,
		CPDefinitionService cpDefinitionService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		super(
			commerceCurrencyService, commerceDiscountService,
			commerceDiscountTargetRegistry,
			commerceDiscountUserSegmentRelService, commercePriceFormatter,
			httpServletRequest, itemSelector);

		_commerceDiscountRelService = commerceDiscountRelService;
		_cpDefinitionService = cpDefinitionService;
	}

	public String getAssetCategoryIds() throws PortalException {
		long[] assetCategoryIds = _commerceDiscountRelService.getClassPKs(
			getCommerceDiscountId(), AssetCategory.class.getName());

		return StringUtil.merge(assetCategoryIds, StringPool.COMMA);
	}

	public CPDefinition getCPDefinition(CommerceDiscountRel commerceDiscountRel)
		throws PortalException {

		if (Objects.equals(
				commerceDiscountRel.getClassName(),
				CPDefinition.class.getName())) {

			return _cpDefinitionService.fetchCPDefinition(
				commerceDiscountRel.getClassPK());
		}

		return null;
	}

	public SearchContainer<CommerceDiscountRel>
			getCPDefinitionCommerceDiscountRelSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceDiscountRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-entries");

		setOrderByColAndType(
			CommerceDiscountRel.class, _searchContainer, "create-date", "desc");

		OrderByComparator<CommerceDiscountRel> orderByComparator =
			CommerceDiscountPortletUtil.getCommerceDiscountRelOrderByComparator(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceDiscountRequestHelper.getLiferayPortletResponse()));

		int total = _commerceDiscountRelService.getCommerceDiscountRelsCount(
			getCommerceDiscountId(), CPDefinition.class.getName());

		_searchContainer.setTotal(total);

		List<CommerceDiscountRel> results = getCommerceDiscountRels(
			_searchContainer.getStart(), _searchContainer.getEnd(),
			orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	@Override
	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceDiscountRequestHelper.getRequest());

		CPDefinitionItemSelectorCriterion cpDefinitionItemSelectorCriterion =
			new CPDefinitionItemSelectorCriterion();

		cpDefinitionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "productDefinitionsSelectItem",
			cpDefinitionItemSelectorCriterion);

		String checkedCPDefinitionIds = StringUtil.merge(
			getCheckedCPDefinitionIds());

		itemSelectorURL.setParameter(
			"checkedCPDefinitionIds", checkedCPDefinitionIds);
		itemSelectorURL.setParameter(
			"disabledCPDefinitionIds", checkedCPDefinitionIds);

		return itemSelectorURL.toString();
	}

	protected long[] getCheckedCPDefinitionIds() throws PortalException {
		List<Long> cpDefinitionIdsList = new ArrayList<>();

		List<CommerceDiscountRel> commerceDiscountRels =
			getCommerceDiscountRels();

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			cpDefinitionIdsList.add(commerceDiscountRel.getClassPK());
		}

		if (cpDefinitionIdsList.isEmpty()) {
			return new long[0];
		}

		Stream<Long> stream = cpDefinitionIdsList.stream();

		return stream.mapToLong(l -> l).toArray();
	}

	protected List<CommerceDiscountRel> getCommerceDiscountRels()
		throws PortalException {

		return getCommerceDiscountRels(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CommerceDiscountRelCreateDateComparator());
	}

	protected List<CommerceDiscountRel> getCommerceDiscountRels(
			int start, int end,
			OrderByComparator<CommerceDiscountRel> orderByComparator)
		throws PortalException {

		return _commerceDiscountRelService.getCommerceDiscountRels(
			getCommerceDiscountId(), CPDefinition.class.getName(), start, end,
			orderByComparator);
	}

	private final CommerceDiscountRelService _commerceDiscountRelService;
	private final CPDefinitionService _cpDefinitionService;
	private SearchContainer<CommerceDiscountRel> _searchContainer;

}