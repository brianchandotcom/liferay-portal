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

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleType;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributor;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributorRegistry;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelService;
import com.liferay.commerce.discount.target.CommerceDiscountTargetTypeRegistry;
import com.liferay.commerce.discount.web.internal.util.CommerceDiscountPortletUtil;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceDiscountRuleDisplayContext
	extends CommerceDiscountDisplayContext {

	public CommerceDiscountRuleDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceDiscountRuleService commerceDiscountRuleService,
		CommerceDiscountRuleTypeJSPContributorRegistry
			commerceDiscountRuleTypeJSPContributorRegistry,
		CommerceDiscountRuleTypeRegistry commerceDiscountRuleTypeRegistry,
		CommerceDiscountService commerceDiscountService,
		CommerceDiscountTargetTypeRegistry commerceDiscountTargetTypeRegistry,
		CommerceDiscountUserSegmentRelService
			commerceDiscountUserSegmentRelService,
		CPDefinitionService cpDefinitionService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		super(
			commerceCurrencyService, commerceDiscountService,
			commerceDiscountTargetTypeRegistry,
			commerceDiscountUserSegmentRelService, httpServletRequest,
			itemSelector);

		_commerceDiscountRuleService = commerceDiscountRuleService;
		_commerceDiscountRuleTypeJSPContributorRegistry =
			commerceDiscountRuleTypeJSPContributorRegistry;
		_commerceDiscountRuleTypeRegistry = commerceDiscountRuleTypeRegistry;
		_cpDefinitionService = cpDefinitionService;
	}

	public CommerceDiscountRule getCommerceDiscountRule()
		throws PortalException {

		if (_commerceDiscountRule != null) {
			return _commerceDiscountRule;
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			commerceDiscountRequestHelper.getRequest(),
			"commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		return _commerceDiscountRule;
	}

	public long getCommerceDiscountRuleId() throws PortalException {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return 0;
		}

		return commerceDiscountRule.getCommerceDiscountRuleId();
	}

	public SearchContainer<CommerceDiscountRule>
			getCommerceDiscountRuleSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			commerceDiscountRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-discount-rules");

		setOrderByColAndType(
			CommerceDiscountRule.class, _searchContainer, "create-date",
			"desc");

		OrderByComparator<CommerceDiscountRule> orderByComparator =
			CommerceDiscountPortletUtil.
				getCommerceDiscountRuleOrderByComparator(
					_searchContainer.getOrderByCol(),
					_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				commerceDiscountRequestHelper.getLiferayPortletResponse()));

		int total = _commerceDiscountRuleService.getCommerceDiscountRulesCount(
			getCommerceDiscountId());

		_searchContainer.setTotal(total);

		List<CommerceDiscountRule> results =
			_commerceDiscountRuleService.getCommerceDiscountRules(
				getCommerceDiscountId(), _searchContainer.getStart(),
				_searchContainer.getEnd(), orderByComparator);

		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public CommerceDiscountRuleTypeJSPContributor
		getCommerceDiscountRuleTypeJSPContributor(String key) {

		return _commerceDiscountRuleTypeJSPContributorRegistry.
			getCommerceDiscountRuleTypeJSPContributor(key);
	}

	public List<CommerceDiscountRuleType> getCommerceDiscountRuleTypes() {
		return _commerceDiscountRuleTypeRegistry.getCommerceDiscountRuleTypes();
	}

	public List<CPDefinition> getCPDefinitions() throws PortalException {
		List<CPDefinition> cpDefinitions = new ArrayList<>();

		String[] cpDefinitionIds = StringUtil.split(getTypeSettings());

		for (String cpDefinitionId : cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.fetchCPDefinition(
				GetterUtil.getLong(cpDefinitionId));

			if (cpDefinition != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		return cpDefinitions;
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

	public String getTypeSettings() throws PortalException {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return StringPool.BLANK;
		}

		String type = BeanParamUtil.getString(
			commerceDiscountRule, commerceDiscountRequestHelper.getRequest(),
			"type");

		return commerceDiscountRule.getSettingsProperty(type);
	}

	protected long[] getCheckedCPDefinitionIds() throws PortalException {
		return ListUtil.toLongArray(
			getCPDefinitions(), CPDefinition.CP_DEFINITION_ID_ACCESSOR);
	}

	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final CommerceDiscountRuleTypeJSPContributorRegistry
		_commerceDiscountRuleTypeJSPContributorRegistry;
	private final CommerceDiscountRuleTypeRegistry
		_commerceDiscountRuleTypeRegistry;
	private final CPDefinitionService _cpDefinitionService;
	private SearchContainer<CommerceDiscountRule> _searchContainer;

}