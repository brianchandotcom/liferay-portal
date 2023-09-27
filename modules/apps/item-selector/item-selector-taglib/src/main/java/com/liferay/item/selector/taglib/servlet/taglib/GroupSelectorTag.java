/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib.servlet.taglib;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorProviderRegistryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class GroupSelectorTag extends IncludeTag {

	public List<Group> getGroups() {
		return _groups;
	}

	public int getGroupsCount() {
		return _groupsCount;
	}

	public void setGroups(List<Group> groups) {
		_groups = groups;
	}

	public void setGroupsCount(int groupsCount) {
		_groupsCount = groupsCount;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_groups = null;
		_groupsCount = -1;
	}

	@Override
	protected String getPage() {
		return "/group_selector/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groups",
			_getGroups(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groupsCount",
			_getGroupsCount(httpServletRequest));
	}

	private Group _getGroup(ThemeDisplay themeDisplay) {
		if (themeDisplay.getRefererGroup() != null) {
			return themeDisplay.getRefererGroup();
		}

		return themeDisplay.getScopeGroup();
	}

	private List<Group> _getGroups(HttpServletRequest httpServletRequest) {
		String groupType = _getGroupType(httpServletRequest);

		GroupItemSelectorProvider groupItemSelectorProvider =
			GroupItemSelectorProviderRegistryUtil.getGroupItemSelectorProvider(
				groupType);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _getGroup(themeDisplay);

		String scopeGroupType = ParamUtil.getString(
			httpServletRequest, "scopeGroupType");

		if (Validator.isNotNull(scopeGroupType) && groupType.equals("site")) {
			_groups = new ArrayList<>();

			_groups.add(group);

			return _groups;
		}

		int cur = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			cur, delta);

		if (groupItemSelectorProvider == null) {
			_groups = Collections.emptyList();

			return _groups;
		}

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		List<Group> groups = groupItemSelectorProvider.getGroups(
			group.getCompanyId(), group.getGroupId(), keywords, startAndEnd[0],
			startAndEnd[1]);

		if (groups == null) {
			_groups = Collections.emptyList();

			return _groups;
		}

		_groups = groups;

		return _groups;
	}

	private int _getGroupsCount(HttpServletRequest httpServletRequest) {
		String scopeGroupType = ParamUtil.getString(
			httpServletRequest, "scopeGroupType");

		if (Validator.isNotNull(scopeGroupType)) {
			_groupsCount = 1;

			return _groupsCount;
		}

		GroupItemSelectorProvider groupSelectorProvider =
			GroupItemSelectorProviderRegistryUtil.getGroupItemSelectorProvider(
				_getGroupType(httpServletRequest));

		if (groupSelectorProvider == null) {
			_groupsCount = 0;

			return _groupsCount;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _getGroup(themeDisplay);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		_groupsCount = groupSelectorProvider.getGroupsCount(
			group.getCompanyId(), group.getGroupId(), keywords);

		return _groupsCount;
	}

	private String _getGroupType(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "groupType");
	}

	private List<Group> _groups;
	private int _groupsCount = -1;

}