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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalServiceUtil;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.taglib.internal.servlet.ServletContextUtil;
import com.liferay.item.selector.taglib.internal.servlet.item.selector.ItemSelectorUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

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

		_groups = Collections.emptyList();
		_groupsCount = -1;
	}

	protected List<Group> getGroups(HttpServletRequest httpServletRequest) {
		if (isRepositories(httpServletRequest)) {
			return _getRepositories(httpServletRequest);
		}

		return _getSites(httpServletRequest);
	}

	protected int getGroupsCount(HttpServletRequest httpServletRequest) {
		if (_groupsCount < 0) {
			getGroups(httpServletRequest);
		}

		return _groupsCount;
	}

	protected PortletURL getIteratorURL(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		String itemSelectedEventName = ParamUtil.getString(
			request, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				httpServletRequest.getParameterMap());

		PortletURL portletURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(httpServletRequest, "selectedTab"));
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		return portletURL;
	}

	@Override
	protected String getPage() {
		return "/group_selector/page.jsp";
	}

	protected PortletURL getRepositoriesURL(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		String itemSelectedEventName = ParamUtil.getString(
			request, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				httpServletRequest.getParameterMap());

		PortletURL portletURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		portletURL.setParameter("repositories", Boolean.TRUE.toString());
		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(httpServletRequest, "selectedTab"));
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		return portletURL;
	}

	protected PortletURL getSitesURL(
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		String itemSelectedEventName = ParamUtil.getString(
			request, "itemSelectedEventName");

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(
				httpServletRequest.getParameterMap());

		PortletURL portletURL = itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		portletURL.setParameter("repositories", Boolean.FALSE.toString());
		portletURL.setParameter(
			"selectedTab",
			ParamUtil.getString(httpServletRequest, "selectedTab"));
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		return portletURL;
	}

	protected boolean isRepositories(HttpServletRequest httpServletRequest) {
		return ParamUtil.getBoolean(httpServletRequest, "repositories");
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groups",
			getGroups(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:groupsCount",
			getGroupsCount(httpServletRequest));

		ItemSelector itemSelector = ItemSelectorUtil.getItemSelector();

		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:itemSelector", itemSelector);
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:iteratorURL",
			getIteratorURL(httpServletRequest, itemSelector));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:repositoriesURL",
			getRepositoriesURL(httpServletRequest, itemSelector));
		httpServletRequest.setAttribute(
			"liferay-item-selector:group-selector:sitesURL",
			getSitesURL(httpServletRequest, itemSelector));
	}

	private List<Group> _getRepositories(
		HttpServletRequest httpServletRequest) {

		_searchRepositories(httpServletRequest);

		return _groups;
	}

	private List<Group> _getSites(HttpServletRequest httpServletRequest) {
		_searchSites(httpServletRequest);

		return _groups;
	}

	private int[] _getStartAndEnd(HttpServletRequest httpServletRequest) {
		int cur = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		return SearchPaginationUtil.calculateStartAndEnd(cur, delta);
	}

	private void _searchRepositories(HttpServletRequest httpServletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			int[] startAndEnd = _getStartAndEnd(httpServletRequest);

			List<DepotEntryGroupRel> depotEntryGroupRels =
				DepotEntryGroupRelLocalServiceUtil.getDepotEntryGroupRels(
					themeDisplay.getScopeGroupId(), startAndEnd[0],
					startAndEnd[1]);

			List<Group> groups = new ArrayList<>();

			for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
				DepotEntry depotEntry =
					DepotEntryLocalServiceUtil.getDepotEntry(
						depotEntryGroupRel.getDepotEntryId());

				groups.add(
					GroupLocalServiceUtil.getGroup(depotEntry.getGroupId()));
			}

			_groupsCount =
				DepotEntryGroupRelLocalServiceUtil.getDepotEntryGroupRelsCount(
					themeDisplay.getScopeGroupId());

			setGroups(groups);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			_groupsCount = 0;
		}
	}

	private void _searchSites(HttpServletRequest httpServletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String keywords = ParamUtil.getString(
				httpServletRequest, "keywords");

			LinkedHashMap<String, Object> groupParams =
				LinkedHashMapBuilder.<String, Object>put(
					"site", Boolean.TRUE
				).build();

			List<Group> groups = GroupServiceUtil.search(
				themeDisplay.getCompanyId(), _CLASS_NAME_IDS, keywords,
				groupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			int[] startAndEnd = _getStartAndEnd(httpServletRequest);

			_groupsCount = groups.size();

			setGroups(ListUtil.subList(groups, startAndEnd[0], startAndEnd[1]));
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			_groups = Collections.emptyList();
			_groupsCount = 0;
		}
	}

	private static final long[] _CLASS_NAME_IDS = {
		ClassNameLocalServiceUtil.getClassNameId(Company.class),
		ClassNameLocalServiceUtil.getClassNameId(Group.class),
		ClassNameLocalServiceUtil.getClassNameId(Organization.class)
	};

	private static final Log _log = LogFactoryUtil.getLog(
		GroupSelectorTag.class);

	private List<Group> _groups = Collections.emptyList();
	private int _groupsCount = -1;

}