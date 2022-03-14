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

package com.liferay.fragment.collection.item.selector.web.internal;

import com.liferay.fragment.collection.item.selector.FragmentCollectionItemSelectorReturnType;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class FragmentCollectionContributorItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<FragmentCollectionContributor> {

	public FragmentCollectionContributorItemSelectorViewDescriptor(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		HttpServletRequest httpServletRequest, PortletURL portletURL) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
		_httpServletRequest = httpServletRequest;
		_portletURL = portletURL;
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		FragmentCollectionContributor fragmentCollectionContributor) {

		return new FragmentCollectionContributorItemDescriptor(
			fragmentCollectionContributor);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new FragmentCollectionItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name"};
	}

	@Override
	public SearchContainer<FragmentCollectionContributor> getSearchContainer()
		throws PortalException {

		SearchContainer<FragmentCollectionContributor> searchContainer =
			new SearchContainer<>(
				_getPortletRequest(), _portletURL, null,
				"there-are-no-items-to-display");

		searchContainer.setOrderByCol(
			ParamUtil.getString(_httpServletRequest, "orderByCol", "name"));

		boolean orderByAsc = true;

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		if (orderByType.equals("desc")) {
			orderByAsc = false;
		}

		searchContainer.setOrderByType(orderByType);

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_getFragmentCollectionContributors(orderByAsc);

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNull(keywords)) {
			searchContainer.setResultsAndTotal(fragmentCollectionContributors);
		}
		else {
			searchContainer.setResultsAndTotal(
				ListUtil.filter(
					fragmentCollectionContributors,
					fragmentCollectionContributor -> {
						String lowerCaseName = StringUtil.toLowerCase(
							fragmentCollectionContributor.getName());

						return lowerCaseName.contains(
							StringUtil.toLowerCase(keywords));
					}));
		}

		return searchContainer;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowManagementToolbar() {
		return true;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private List<FragmentCollectionContributor>
		_getFragmentCollectionContributors(boolean orderByAsc) {

		if (_fragmentCollectionContributorTracker == null) {
			return Collections.emptyList();
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Collections.sort(
			fragmentCollectionContributors,
			new FragmentCollectionContributorNameComparator(
				themeDisplay.getLocale(), orderByAsc));

		return fragmentCollectionContributors;
	}

	private PortletRequest _getPortletRequest() {
		return (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final HttpServletRequest _httpServletRequest;
	private final PortletURL _portletURL;

}