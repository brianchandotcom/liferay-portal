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

package com.liferay.site.item.selector.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portlet.usersadmin.search.GroupSearch;
import com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion;
import com.liferay.site.util.GroupSearchProvider;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class MySitesItemSelectorViewDisplayContext
	extends BaseSitesItemSelectorViewDisplayContext {

	public MySitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL,
		GroupSearchProvider groupSearchProvider) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);

		_groupSearchProvider = groupSearchProvider;
		_portletRequest = getPortletRequest();

		addBreadcrumbEntries();
	}

	@Override
	public GroupSearch getGroupSearch() throws Exception {
		PortletURL portletURL = getPortletURL();

		Group group = getGroup();

		if (group != null) {
			portletURL.setParameter(
				"groupId", String.valueOf(group.getGroupId()));
		}

		return _groupSearchProvider.getGroupSearch(_portletRequest, portletURL);
	}

	@Override
	public boolean isShowChildSitesLink() {
		return true;
	}

	@Override
	public boolean isShowSortFilter() {
		return true;
	}

	protected void addBreadcrumbEntries() {
		Group group = getGroup();

		if (group == null) {
			return;
		}

		try {
			PortletURL portletURL = getPortletURL();

			PortalUtil.addPortletBreadcrumbEntry(
				request, LanguageUtil.get(request, "all"),
				portletURL.toString());

			SitesUtil.addPortletBreadcrumbEntries(group, request, portletURL);
		}
		catch (Exception e) {
			_log.error(
				"Unable to add breadcrumb entries for group " +
					group.getGroupId());
		}
	}

	protected Group getGroup() {
		long groupId = ParamUtil.getLong(
			request, "groupId", GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if (groupId > 0) {
			return GroupLocalServiceUtil.fetchGroup(groupId);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MySitesItemSelectorViewDisplayContext.class);

	private final GroupSearchProvider _groupSearchProvider;
	private final PortletRequest _portletRequest;

}