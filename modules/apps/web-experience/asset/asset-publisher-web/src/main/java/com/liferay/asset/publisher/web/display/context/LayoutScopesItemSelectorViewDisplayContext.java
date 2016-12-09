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

package com.liferay.asset.publisher.web.display.context;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutScopesItemSelectorViewDisplayContext
	extends BaseItemSelectorViewDisplayContext {

	public LayoutScopesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		List<Group> results = new ArrayList<>();

		int additionalSites = 0;

		boolean includeUserPersonalSite = ParamUtil.getBoolean(
			request, "includeUserPersonalSite");

		if (includeUserPersonalSite) {
			if (groupSearch.getStart() == 0) {
				Group userPersonalSite = GroupLocalServiceUtil.getGroup(
					company.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

				results.add(userPersonalSite);
			}

			additionalSites++;
		}

		int total = GroupLocalServiceUtil.getGroupsCount(
			themeDisplay.getCompanyId(), Layout.class.getName(), getGroupId());

		total += additionalSites;

		groupSearch.setTotal(total);

		int start = groupSearch.getStart();

		if (groupSearch.getStart() > additionalSites) {
			start = groupSearch.getStart() - additionalSites;
		}

		int end = groupSearch.getEnd() - additionalSites;

		List<Group> groups = GroupLocalServiceUtil.getGroups(
			company.getCompanyId(), Layout.class.getName(), getGroupId(), start,
			end);

		groups = _filterLayoutGroups(groups, _isPrivateLayout());

		results.addAll(groups);

		groupSearch.setResults(results);

		return groupSearch;
	}

	private List<Group> _filterLayoutGroups(
			List<Group> groups, Boolean privateLayout)
		throws Exception {

		List<Group> filteredGroups = new ArrayList();

		if (privateLayout == null) {
			return groups;
		}

		for (Group group : groups) {
			if (!group.isLayout()) {
				continue;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			if (layout.isPrivateLayout() == privateLayout) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private Boolean _isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(request, "privateLayout");

		return _privateLayout;
	}

	private Boolean _privateLayout;

}