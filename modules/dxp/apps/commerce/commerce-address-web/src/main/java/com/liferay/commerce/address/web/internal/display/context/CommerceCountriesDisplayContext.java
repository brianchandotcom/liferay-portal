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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountriesDisplayContext
	extends BaseCommerceCountriesDisplayContext<CommerceCountry> {

	public CommerceCountriesDisplayContext(
		ActionHelper actionHelper,
		CommerceCountryService commerceCountryService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(actionHelper, renderRequest, renderResponse);

		_commerceCountryService = commerceCountryService;
	}

	@Override
	public SearchContainer<CommerceCountry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-countries";

		String navigation = getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-countries";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-countries";
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceCountry> orderByComparator =
			CommerceUtil.getCommerceCountryOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(getRowChecker());

		Sort sort = CommerceUtil.getCommerceCountrySort(
			orderByCol, orderByType);

		SearchContext searchContext = buildSearchContext(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), active,
			getKeywords(), searchContainer.getStart(), searchContainer.getEnd(),
			sort);

		BaseModelSearchResult<CommerceCountry> results =
			_commerceCountryService.searchCommerceCountries(searchContext);

		searchContainer.setTotal(results.getLength());
		searchContainer.setResults(results.getBaseModels());

		return searchContainer;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, Boolean active, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("active", active);

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.NAME, keywords);
		attributes.put("numericISOCode", keywords);
		attributes.put("threeLettersISOCode", keywords);
		attributes.put("twoLettersISOCode", keywords);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(new Sort[] {sort});
		}

		return searchContext;
	}

	protected String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(renderRequest, "keywords");

		return _keywords;
	}

	private final CommerceCountryService _commerceCountryService;
	private String _keywords;

}