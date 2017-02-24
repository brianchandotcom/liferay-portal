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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ParentSitesItemSelectorViewDisplayContext
	extends BaseItemSelectorViewDisplayContext {

	public ParentSitesItemSelectorViewDisplayContext(
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

		long groupId = getGroupId();

		if (groupId == 0) {
			groupId = themeDisplay.getSiteGroupId();
		}

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Group> groups = group.getAncestors();

		groups = _filterParentSitesGroups(groups);

		groupSearch.setTotal(groups.size());

		groupSearch.setResults(groups);

		return groupSearch;
	}

	private List<Group> _filterParentSitesGroups(List<Group> groups) {
		List<Group> filteredGroups = new ArrayList();

		for (Group group : groups) {
			if (SitesUtil.isContentSharingWithChildrenEnabled(group)) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

}