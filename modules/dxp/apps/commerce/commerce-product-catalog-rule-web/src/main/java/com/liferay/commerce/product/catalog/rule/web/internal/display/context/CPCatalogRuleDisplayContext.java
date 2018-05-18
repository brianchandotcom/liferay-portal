/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.catalog.rule.web.internal.display.context;

import com.liferay.commerce.product.catalog.rule.CPRuleType;
import com.liferay.commerce.product.catalog.rule.CPRuleTypeJSPContributor;
import com.liferay.commerce.product.catalog.rule.CPRuleTypeJSPContributorRegistry;
import com.liferay.commerce.product.catalog.rule.CPRuleTypeRegistry;
import com.liferay.commerce.product.catalog.rule.web.internal.display.context.util.CPCatalogRuleRequestHelper;
import com.liferay.commerce.product.catalog.rule.web.internal.util.CPCatalogRulePortletUtil;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.CPRuleAssetCategoryRelService;
import com.liferay.commerce.product.service.CPRuleService;
import com.liferay.commerce.product.service.CPRuleUserSegmentRelService;
import com.liferay.commerce.product.util.comparator.CPRuleUserSegmentRelCreateDateComparator;
import com.liferay.commerce.user.segment.item.selector.criterion.CommerceUserSegmentEntryItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCatalogRuleDisplayContext {

	public CPCatalogRuleDisplayContext(
		CPRuleAssetCategoryRelService cpRuleAssetCategoryRelService,
		CPRuleService cpRuleService,
		CPRuleTypeJSPContributorRegistry cpRuleTypeJSPContributorRegistry,
		CPRuleTypeRegistry cpRuleTypeRegistry,
		CPRuleUserSegmentRelService cpRuleUserSegmentRelService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		_cpRuleAssetCategoryRelService = cpRuleAssetCategoryRelService;
		_cpRuleService = cpRuleService;
		_cpRuleTypeJSPContributorRegistry = cpRuleTypeJSPContributorRegistry;
		_cpRuleTypeRegistry = cpRuleTypeRegistry;
		_cpRuleUserSegmentRelService = cpRuleUserSegmentRelService;
		_itemSelector = itemSelector;

		_cpCatalogRuleRequestHelper = new CPCatalogRuleRequestHelper(
			httpServletRequest);
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
	}

	public String getAssetCategoryIds() throws PortalException {
		if (getCPRuleId() <= 0) {
			return StringPool.BLANK;
		}

		long[] assetCategoryIds =
			_cpRuleAssetCategoryRelService.getAssetCategoryIds(getCPRuleId());

		return StringUtil.merge(assetCategoryIds, StringPool.COMMA);
	}

	public CPRule getCPRule() throws PortalException {
		if (_cpRule != null) {
			return _cpRule;
		}

		HttpServletRequest httpServletRequest =
			_cpCatalogRuleRequestHelper.getRequest();

		long cpRuleId = ParamUtil.getLong(httpServletRequest, "cpRuleId");

		if (cpRuleId > 0) {
			_cpRule = _cpRuleService.getCPRule(cpRuleId);
		}

		return _cpRule;
	}

	public long getCPRuleId() throws PortalException {
		CPRule cpRule = getCPRule();

		if (cpRule == null) {
			return 0;
		}

		return cpRule.getCPRuleId();
	}

	public CPRuleTypeJSPContributor getCPRuleTypeJSPContributor(String key) {
		return _cpRuleTypeJSPContributorRegistry.getCPRuleTypeJSPContributor(
			key);
	}

	public List<CPRuleType> getCPRuleTypes() {
		return _cpRuleTypeRegistry.getCPRuleTypes();
	}

	public SearchContainer<CPRuleUserSegmentRel>
			getCPRuleUserSegmentRelsSearchContainer()
		throws PortalException {

		if (_cpRuleUserSegmentRelsSearchContainer != null) {
			return _cpRuleUserSegmentRelsSearchContainer;
		}

		_cpRuleUserSegmentRelsSearchContainer = new SearchContainer<>(
			_cpCatalogRuleRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-user-segment-catalog-rules");

		setOrderByColAndType(
			CPRuleUserSegmentRel.class, _cpRuleUserSegmentRelsSearchContainer,
			"create-date", "desc");

		OrderByComparator<CPRuleUserSegmentRel> orderByComparator =
			CPCatalogRulePortletUtil.getCPRuleUserSegmentRelOrderByComparator(
				_cpRuleUserSegmentRelsSearchContainer.getOrderByCol(),
				_cpRuleUserSegmentRelsSearchContainer.getOrderByType());

		_cpRuleUserSegmentRelsSearchContainer.setOrderByComparator(
			orderByComparator);

		_cpRuleUserSegmentRelsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_cpCatalogRuleRequestHelper.getLiferayPortletResponse()));

		int total = _cpRuleUserSegmentRelService.getCPRuleUserSegmentRelsCount(
			getCPRuleId());

		_cpRuleUserSegmentRelsSearchContainer.setTotal(total);

		List<CPRuleUserSegmentRel> results = getCPRuleUserSegmentRels(
			_cpRuleUserSegmentRelsSearchContainer.getStart(),
			_cpRuleUserSegmentRelsSearchContainer.getEnd(), orderByComparator);

		_cpRuleUserSegmentRelsSearchContainer.setResults(results);

		return _cpRuleUserSegmentRelsSearchContainer;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_cpCatalogRuleRequestHelper.getRequest());

		CommerceUserSegmentEntryItemSelectorCriterion
			commerceUserSegmentEntryItemSelectorCriterion =
				new CommerceUserSegmentEntryItemSelectorCriterion();

		commerceUserSegmentEntryItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "userSegmentSelectItem",
			commerceUserSegmentEntryItemSelectorCriterion);

		String checkedCommerceUserSegmentEntryIds = StringUtil.merge(
			getCheckedCommerceUserSegmentEntryIds());

		itemSelectorURL.setParameter(
			"checkedCommerceUserSegmentEntryIds",
			checkedCommerceUserSegmentEntryIds);

		return itemSelectorURL.toString();
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_cpCatalogRuleRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			_cpCatalogRuleRequestHelper.getRequest();

		long cpRuleId = ParamUtil.getLong(httpServletRequest, "cpRuleId");

		if (cpRuleId > 0) {
			portletURL.setParameter("cpRuleId", String.valueOf(cpRuleId));
		}

		String delta = ParamUtil.getString(httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String orderByCol = ParamUtil.getString(
			httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String screenNavigationCategoryKey = ParamUtil.getString(
			httpServletRequest, "screenNavigationCategoryKey");

		if (Validator.isNotNull(screenNavigationCategoryKey)) {
			portletURL.setParameter(
				"screenNavigationCategoryKey", screenNavigationCategoryKey);
		}

		String screenNavigationEntryKey = ParamUtil.getString(
			httpServletRequest, "screenNavigationEntryKey");

		if (Validator.isNotNull(screenNavigationEntryKey)) {
			portletURL.setParameter(
				"screenNavigationEntryKey", screenNavigationEntryKey);
		}

		return portletURL;
	}

	public SearchContainer<CPRule> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_cpCatalogRuleRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-catalog-rules");

		setOrderByColAndType(
			CPRule.class, _searchContainer, "create-date", "desc");

		OrderByComparator<CPRule> orderByComparator =
			CPCatalogRulePortletUtil.getCPRuleOrderByComparator(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

		_searchContainer.setOrderByComparator(orderByComparator);

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(
				_cpCatalogRuleRequestHelper.getLiferayPortletResponse()));

		long groupId = _cpCatalogRuleRequestHelper.getScopeGroupId();

		if (isSearch()) {
			Sort sort = CPCatalogRulePortletUtil.getCPRuleSort(
				_searchContainer.getOrderByCol(),
				_searchContainer.getOrderByType());

			BaseModelSearchResult<CPRule> baseModelSearchResult =
				_cpRuleService.searchCPRules(
					_cpCatalogRuleRequestHelper.getCompanyId(), groupId,
					getKeywords(), _searchContainer.getStart(),
					_searchContainer.getEnd(), sort);

			_searchContainer.setTotal(baseModelSearchResult.getLength());
			_searchContainer.setResults(baseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpRuleService.getCPRulesCount(groupId);

			_searchContainer.setTotal(total);

			List<CPRule> results = _cpRuleService.getCPRules(
				groupId, _searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

			_searchContainer.setResults(results);
		}

		return _searchContainer;
	}

	protected long[] getCheckedCommerceUserSegmentEntryIds()
		throws PortalException {

		List<Long> commerceUserSegmentEntryIdsList = new ArrayList<>();

		List<CPRuleUserSegmentRel> cpRuleUserSegmentRels =
			getCPRuleUserSegmentRels();

		for (CPRuleUserSegmentRel cpRuleUserSegmentRel :
				cpRuleUserSegmentRels) {

			commerceUserSegmentEntryIdsList.add(
				cpRuleUserSegmentRel.getCommerceUserSegmentEntryId());
		}

		if (commerceUserSegmentEntryIdsList.isEmpty()) {
			return new long[0];
		}

		Stream<Long> stream = commerceUserSegmentEntryIdsList.stream();

		return stream.mapToLong(l -> l).toArray();
	}

	protected List<CPRuleUserSegmentRel> getCPRuleUserSegmentRels()
		throws PortalException {

		return getCPRuleUserSegmentRels(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CPRuleUserSegmentRelCreateDateComparator());
	}

	protected List<CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
			int start, int end,
			OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws PortalException {

		return _cpRuleUserSegmentRelService.getCPRuleUserSegmentRels(
			getCPRuleId(), start, end, orderByComparator);
	}

	protected String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			_cpCatalogRuleRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected <T> void setOrderByColAndType(
		Class<T> clazz, SearchContainer<T> searchContainer,
		String defaultOrderByCol, String defaultOrderByType) {

		HttpServletRequest httpServletRequest =
			_cpCatalogRuleRequestHelper.getRequest();

		String orderByCol = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByColParam());
		String orderByType = ParamUtil.getString(
			httpServletRequest, searchContainer.getOrderByTypeParam());

		String namespace = _cpCatalogRuleRequestHelper.getPortletId();
		String prefix = TextFormatter.format(
			clazz.getSimpleName(), TextFormatter.K);

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			_portalPreferences.setValue(
				namespace, prefix + "-order-by-col", orderByCol);
			_portalPreferences.setValue(
				namespace, prefix + "-order-by-type", orderByType);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				namespace, prefix + "-order-by-col", defaultOrderByCol);
			orderByType = _portalPreferences.getValue(
				namespace, prefix + "-order-by-type", defaultOrderByType);
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
	}

	private final CPCatalogRuleRequestHelper _cpCatalogRuleRequestHelper;
	private CPRule _cpRule;
	private final CPRuleAssetCategoryRelService _cpRuleAssetCategoryRelService;
	private final CPRuleService _cpRuleService;
	private final CPRuleTypeJSPContributorRegistry
		_cpRuleTypeJSPContributorRegistry;
	private final CPRuleTypeRegistry _cpRuleTypeRegistry;
	private final CPRuleUserSegmentRelService _cpRuleUserSegmentRelService;
	private SearchContainer<CPRuleUserSegmentRel>
		_cpRuleUserSegmentRelsSearchContainer;
	private final ItemSelector _itemSelector;
	private String _keywords;
	private final PortalPreferences _portalPreferences;
	private SearchContainer<CPRule> _searchContainer;

}