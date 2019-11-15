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

package com.liferay.item.selector.taglib.servlet.taglib;

import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.item.selector.taglib.internal.util.GroupItemSelectorTrackerUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

		servletContext = ServletContextUtil.getServletContext();
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

	private List<Group> _getGroups(HttpServletRequest httpServletRequest) {
		Optional<GroupItemSelectorProvider> groupItemSelectorProviderOptional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				_getGroupType(httpServletRequest));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		int cur = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			cur, delta);

		_groups = groupItemSelectorProviderOptional.map(
			groupItemSelectorProvider -> groupItemSelectorProvider.getGroups(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords, startAndEnd[0], startAndEnd[1])
		).orElse(
			Collections.emptyList()
		);

		return _groups;
	}

	private int _getGroupsCount(HttpServletRequest httpServletRequest) {
		Optional<GroupItemSelectorProvider> groupSelectorProviderOptional =
			GroupItemSelectorTrackerUtil.getGroupItemSelectorProviderOptional(
				_getGroupType(httpServletRequest));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		_groupsCount = groupSelectorProviderOptional.map(
			groupSelectorProvider -> groupSelectorProvider.getGroupsCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords)
		).orElse(
			0
		);

		return _groupsCount;
	}

	private String _getGroupType(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "groupType");
	}

	private List<Group> _groups;
	private int _groupsCount = -1;

}