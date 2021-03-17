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

package com.liferay.marketplace.app.manager.web.internal.display.context;

import com.liferay.marketplace.app.manager.web.internal.util.BundleManagerUtil;
import com.liferay.marketplace.app.manager.web.internal.util.MarketplaceAppManagerSearchUtil;
import com.liferay.marketplace.app.manager.web.internal.util.comparator.MarketplaceAppManagerComparator;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class AppManagerSearchResultsManagementToolbarDisplayContext
	extends BaseAppManagerManagementToolbarDisplayContext {

	public AppManagerSearchResultsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse);
	}

	public String getKeywords() {
		return ParamUtil.getString(httpServletRequest, "keywords");
	}

	@Override
	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCPath(
			"/view_search_results.jsp"
		).setParameter(
			"category", getCategory()
		).setParameter(
			"state", getState()
		).setParameter(
			"orderByType", getOrderByType()
		).build();

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		String redirect = ParamUtil.getString(
			httpServletRequest, "redirect",
			String.valueOf(liferayPortletResponse.createRenderURL()));

		portletURL.setParameter("redirect", redirect);

		if (_searchContainer != null) {
			portletURL.setParameter(
				_searchContainer.getCurParam(),
				String.valueOf(_searchContainer.getCur()));
			portletURL.setParameter(
				_searchContainer.getDeltaParam(),
				String.valueOf(_searchContainer.getDelta()));
		}

		return portletURL;
	}

	@Override
	public SearchContainer<Object> getSearchContainer() throws Exception {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<Object> searchContainer = new SearchContainer(
			liferayPortletRequest, getPortletURL(), null,
			"no-results-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		List<Object> results = MarketplaceAppManagerSearchUtil.getResults(
			BundleManagerUtil.getBundles(), getKeywords(),
			httpServletRequest.getLocale());

		results = ListUtil.sort(
			results, new MarketplaceAppManagerComparator(getOrderByType()));

		int end = searchContainer.getEnd();

		if (end > results.size()) {
			end = results.size();
		}

		searchContainer.setResults(
			results.subList(searchContainer.getStart(), end));

		searchContainer.setTotal(results.size());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private SearchContainer<Object> _searchContainer;

}