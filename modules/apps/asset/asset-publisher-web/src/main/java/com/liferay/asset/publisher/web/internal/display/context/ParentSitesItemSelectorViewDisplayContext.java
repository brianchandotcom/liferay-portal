/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.display.context;

import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.search.GroupSearch;

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
		HttpServletRequest httpServletRequest,
		AssetPublisherHelper assetPublisherHelper, PortletURL portletURL) {

		super(httpServletRequest, assetPublisherHelper, portletURL);
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), portletURL);

		Group group = themeDisplay.getSiteGroup();

		List<Group> groups = _filterParentSitesGroups(group.getAncestors());

		groupSearch.setResultsAndTotal(() -> groups, groups.size());

		return groupSearch;
	}

	@Override
	public boolean isShowSearch() {
		return false;
	}

	private List<Group> _filterParentSitesGroups(List<Group> groups) {
		List<Group> filteredGroups = new ArrayList<>();

		for (Group group : groups) {
			if (group.isContentSharingWithChildrenEnabled()) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

}