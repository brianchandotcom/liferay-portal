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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.portlet.usersadmin.search.GroupSearchTerms;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SitesItemSelectorViewDisplayContext
	extends BaseItemSelectorViewDisplayContext {

	public SitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);
	}

	public String getFilter() {
		if (_filter != null) {
			return _filter;
		}

		_filter = ParamUtil.getString(request, "filter");

		return _filter;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(request, "groupId");

		return _groupId;
	}

	public LinkedHashMap<String, Object> getGroupParams()
		throws PortalException {

		if (_groupParams != null) {
			return _groupParams;
		}

		long groupId = ParamUtil.getLong(request, "groupId");
		boolean includeCurrentGroup = ParamUtil.getBoolean(
			request, "includeCurrentGroup", true);

		String type = getType();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();
		User user = themeDisplay.getUser();

		boolean filterManageableGroups = true;

		if (permissionChecker.isCompanyAdmin()) {
			filterManageableGroups = false;
		}

		_groupParams = new LinkedHashMap<>();

		_groupParams.put("active", Boolean.TRUE);

		if (isManualMembership()) {
			_groupParams.put("manualMembership", Boolean.TRUE);
		}

		if (type.equals("child-sites")) {
			Group parentGroup = GroupLocalServiceUtil.getGroup(groupId);

			List<Group> parentGroups = new ArrayList<>();

			parentGroups.add(parentGroup);

			_groupParams.put("groupsTree", parentGroups);
		}
		else if (filterManageableGroups) {
			_groupParams.put("usersGroups", user.getUserId());
		}

		_groupParams.put("site", Boolean.TRUE);

		if (!includeCurrentGroup && (groupId > 0)) {
			List<Long> excludedGroupIds = new ArrayList<>();

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isStagingGroup()) {
				excludedGroupIds.add(group.getLiveGroupId());
			}
			else {
				excludedGroupIds.add(groupId);
			}

			_groupParams.put("excludedGroupIds", excludedGroupIds);
		}

		return _groupParams;
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		GroupSearch groupSearch = new GroupSearch(
			getPortletRequest(), getPortletURL());

		GroupSearchTerms groupSearchTerms =
			(GroupSearchTerms)groupSearch.getSearchTerms();

		List<Group> results = new ArrayList<>();

		int additionalSites = 0;
		int total = 0;

		boolean includeCompany = ParamUtil.getBoolean(
			request, "includeCompany");
		boolean includeUserPersonalSite = ParamUtil.getBoolean(
			request, "includeUserPersonalSite");

		long[] classNameIds = _CLASS_NAME_IDS;

		if (includeCompany) {
			classNameIds = ArrayUtil.append(
				classNameIds, PortalUtil.getClassNameId(Company.class));
		}

		if (includeUserPersonalSite) {
			if (groupSearch.getStart() == 0) {
				Group userPersonalSite = GroupLocalServiceUtil.getGroup(
					company.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

				results.add(userPersonalSite);
			}

			additionalSites++;
		}

		String type = getType();

		if (type.equals("parent-sites")) {
		}
		else {
			total = GroupLocalServiceUtil.searchCount(
				themeDisplay.getCompanyId(), classNameIds,
				groupSearchTerms.getKeywords(), getGroupParams());
		}

		total += additionalSites;

		groupSearch.setTotal(total);

		int start = groupSearch.getStart();

		if (groupSearch.getStart() > additionalSites) {
			start = groupSearch.getStart() - additionalSites;
		}

		int end = groupSearch.getEnd() - additionalSites;

		List<Group> groups = null;

		if (type.equals("parent-sites")) {
			Group group = GroupLocalServiceUtil.getGroup(getGroupId());

			groups = group.getAncestors();

			String filter = getFilter();

			if (Validator.isNotNull(filter)) {
				groups = _filterGroups(groups, filter);
			}

			total = groups.size();

			total += additionalSites;

			groupSearch.setTotal(total);
		}
		else {
			groups = GroupLocalServiceUtil.search(
				company.getCompanyId(), classNameIds,
				groupSearchTerms.getKeywords(), getGroupParams(), start, end,
				groupSearch.getOrderByComparator());

			groups = _filterGroups(groups, themeDisplay.getPermissionChecker());

			total = groups.size();

			total += additionalSites;

			groupSearch.setTotal(total);
		}

		results.addAll(groups);

		groupSearch.setResults(results);

		return groupSearch;
	}

	public String getType() {
		if (_type != null) {
			return _type;
		}

		_type = ParamUtil.getString(request, "type");

		String[] types = getTypes();

		if (Validator.isNull(_type)) {
			_type = types[0];
		}

		return _type;
	}

	public String[] getTypes() {
		if (_types != null) {
			return _types;
		}

		_types = ParamUtil.getParameterValues(request, "types");

		if (_types.length == 0) {
			_types = new String[] {"sites-that-i-administer"};
		}

		return _types;
	}

	public Boolean isManualMembership() {
		if (_manualMembership != null) {
			return _manualMembership;
		}

		_manualMembership = ParamUtil.getBoolean(request, "manualMembership");

		return _manualMembership;
	}

	private List<Group> _filterGroups(
			List<Group> groups, PermissionChecker permissionChecker)
		throws Exception {

		List<Group> filteredGroups = new ArrayList();

		for (Group group : groups) {
			if (permissionChecker.isGroupAdmin(group.getGroupId())) {
				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private List<Group> _filterGroups(List<Group> groups, String filter)
		throws Exception {

		List<Group> filteredGroups = new ArrayList();

		for (Group group : groups) {
			if (filter.equals("contentSharingWithChildrenEnabled") &&
				SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				filteredGroups.add(group);
			}
		}

		return filteredGroups;
	}

	private static final long[] _CLASS_NAME_IDS = new long[] {
		PortalUtil.getClassNameId(Group.class),
		PortalUtil.getClassNameId(Organization.class)
	};

	private String _filter;
	private Long _groupId;
	private LinkedHashMap<String, Object> _groupParams;
	private Boolean _manualMembership;
	private String _type;
	private String[] _types;

}