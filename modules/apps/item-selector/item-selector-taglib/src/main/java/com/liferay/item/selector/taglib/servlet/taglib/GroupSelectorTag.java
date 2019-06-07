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

import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.item.selector.taglib.internal.servlet.item.selector.ItemSelectorUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public class GroupSelectorTag extends IncludeTag {

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
		_groupsCount = 0;
	}

	protected List<Group> getGroups(HttpServletRequest httpServletRequest) {
		if (_groups != null) {
			return _groups;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		List<Long> excludedGroupIds = _getExcludedGroupIds(httpServletRequest);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("excludedGroupIds", excludedGroupIds);
		groupParams.put("site", Boolean.TRUE);

		int cur = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			cur, delta);

		return GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), _CLASS_NAME_IDS, keywords, groupParams,
			startAndEnd[0], startAndEnd[1], null);
	}

	protected int getGroupsCount(HttpServletRequest httpServletRequest) {
		if ((_groups != null) || (_groupsCount > 0)) {
			return _groupsCount;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		List<Long> excludedGroupIds = _getExcludedGroupIds(httpServletRequest);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("excludedGroupIds", excludedGroupIds);
		groupParams.put("site", Boolean.TRUE);

		return GroupLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), _CLASS_NAME_IDS, keywords,
			groupParams);
	}

	@Override
	protected String getPage() {
		return "/group_selector/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groups",
			getGroups(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groupsCount",
			getGroupsCount(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:itemSelector",
			ItemSelectorUtil.getItemSelector());
	}

	private List<Long> _getExcludedGroupIds(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String keywords = ParamUtil.getString(httpServletRequest, "keywords");

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		List<Integer> types = new ArrayList<>();

		int privateSiteType = GroupConstants.TYPE_SITE_PRIVATE;

		types.add(privateSiteType);

		LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

		groupParams.put("site", Boolean.TRUE);
		groupParams.put("types", types);

		int cur = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			cur, delta);

		List<Group> privateSites = GroupLocalServiceUtil.search(
			themeDisplay.getCompanyId(), _CLASS_NAME_IDS, keywords, groupParams,
			startAndEnd[0], startAndEnd[1], null);

		List<Long> excludedGroupIds = new ArrayList<>();

		for (Group privateSite : privateSites) {
			long groupId = privateSite.getGroupId();

			if (!permissionChecker.isGroupMember(groupId)) {
				excludedGroupIds.add(groupId);
			}
		}

		return excludedGroupIds;
	}

	private static final long[] _CLASS_NAME_IDS = {
		ClassNameLocalServiceUtil.getClassNameId(Company.class),
		ClassNameLocalServiceUtil.getClassNameId(Group.class),
		ClassNameLocalServiceUtil.getClassNameId(Organization.class)
	};

	private List<Group> _groups;
	private int _groupsCount;

}