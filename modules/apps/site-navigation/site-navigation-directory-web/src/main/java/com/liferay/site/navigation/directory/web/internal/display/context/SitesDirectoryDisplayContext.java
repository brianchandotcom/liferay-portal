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

package com.liferay.site.navigation.directory.web.internal.display.context;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.site.navigation.directory.web.internal.configuration.SitesDirectoryPortletInstanceConfiguration;
import com.liferay.taglib.ui.SitesDirectoryTag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SitesDirectoryDisplayContext {

	public SitesDirectoryDisplayContext(HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_sitesDirectoryPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SitesDirectoryPortletInstanceConfiguration.class);
	}

	public List<Group> getBranchGroups() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<Group> branchGroups = new ArrayList<>();

		Group group = themeDisplay.getScopeGroup();

		branchGroups.add(group);
		branchGroups.addAll(group.getAncestors());

		return branchGroups;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle",
			_sitesDirectoryPortletInstanceConfiguration.displayStyle());

		return _displayStyle;
	}

	public Group getRootGroup() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group rootGroup = null;

		List<Group> branchGroups = getBranchGroups();

		Group group = themeDisplay.getScopeGroup();

		if (Objects.equals(getSites(), SitesDirectoryTag.SITES_TOP_LEVEL)) {
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_CHILDREN) &&
				 !branchGroups.isEmpty()) {

			rootGroup = branchGroups.get(0);
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_SIBLINGS) &&
				 (branchGroups.size() > 1)) {

			rootGroup = branchGroups.get(1);
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_SIBLINGS) &&
				 group.isRoot()) {
		}
		else if (Objects.equals(
					getSites(), SitesDirectoryTag.SITES_PARENT_LEVEL) &&
				 (branchGroups.size() > 2)) {

			rootGroup = branchGroups.get(2);
		}
		else if (Objects.equals(
					getSites(), SitesDirectoryTag.SITES_PARENT_LEVEL) &&
				 (branchGroups.size() == 2)) {
		}

		return rootGroup;
	}

	public SearchContainer getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			_httpServletRequest, portletDisplay.getId(),
			PortletRequest.RENDER_PHASE);

		_searchContainer = new SearchContainer(
			_portletRequest, portletURL, null, "no-sites-were-found");

		List<Group> childGroups = null;

		Group rootGroup = getRootGroup();

		if (rootGroup != null) {
			childGroups = rootGroup.getChildrenWithLayouts(
				true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new GroupNameComparator(true, themeDisplay.getLocale()));
		}
		else {
			childGroups = GroupLocalServiceUtil.getLayoutsGroups(
				themeDisplay.getCompanyId(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID, true, true,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new GroupNameComparator(true, themeDisplay.getLocale()));
		}

		Set<Group> visibleGroups = new LinkedHashSet<>();

		for (Group childGroup : childGroups) {
			if (childGroup.hasPublicLayouts()) {
				visibleGroups.add(childGroup);
			}
			else if (GroupLocalServiceUtil.hasUserGroup(
						themeDisplay.getUserId(), childGroup.getGroupId())) {

				visibleGroups.add(childGroup);
			}
		}

		_searchContainer.setResults(
			ListUtil.subList(
				new ArrayList<>(visibleGroups), _searchContainer.getStart(),
				_searchContainer.getEnd()));
		_searchContainer.setTotal(visibleGroups.size());

		return _searchContainer;
	}

	public String getSites() {
		if (_sites != null) {
			return _sites;
		}

		_sites = ParamUtil.getString(
			_httpServletRequest, "sites",
			_sitesDirectoryPortletInstanceConfiguration.sites());

		return _sites;
	}

	public boolean isHidden() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<Group> branchGroups = getBranchGroups();

		Group group = themeDisplay.getScopeGroup();

		if (Objects.equals(getSites(), SitesDirectoryTag.SITES_TOP_LEVEL)) {
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_CHILDREN) &&
				 !branchGroups.isEmpty()) {
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_SIBLINGS) &&
				 (branchGroups.size() > 1)) {
		}
		else if (Objects.equals(getSites(), SitesDirectoryTag.SITES_SIBLINGS) &&
				 group.isRoot()) {
		}
		else if (Objects.equals(
					getSites(), SitesDirectoryTag.SITES_PARENT_LEVEL) &&
				 (branchGroups.size() > 2)) {
		}
		else if (Objects.equals(
					getSites(), SitesDirectoryTag.SITES_PARENT_LEVEL) &&
				 (branchGroups.size() == 2)) {
		}
		else {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final PortletRequest _portletRequest;
	private SearchContainer _searchContainer;
	private String _sites;
	private final SitesDirectoryPortletInstanceConfiguration
		_sitesDirectoryPortletInstanceConfiguration;

}