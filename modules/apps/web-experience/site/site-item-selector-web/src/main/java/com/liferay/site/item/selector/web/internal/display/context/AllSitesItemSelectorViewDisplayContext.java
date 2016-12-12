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

import com.liferay.portal.kernel.exception.PortalException;
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
public class AllSitesItemSelectorViewDisplayContext
	extends BaseSitesItemSelectorViewDisplayContext {

	public AllSitesItemSelectorViewDisplayContext(
		HttpServletRequest request,
		SiteItemSelectorCriterion siteItemSelectorCriterion,
		String itemSelectedEventName, PortletURL portletURL,
		GroupSearchProvider groupSearchProvider) {

		super(
			request, siteItemSelectorCriterion, itemSelectedEventName,
			portletURL);

		_groupSearchProvider = groupSearchProvider;
		_portletRequest = getPortletRequest();

		addBreadcrumbEntries(portletURL);
	}

	@Override
	public GroupSearch getGroupSearch() throws PortalException {
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

	protected void addBreadcrumbEntries(PortletURL portletURL) {
		Group group = getGroup();

		if (group == null) {
			return;
		}

		PortalUtil.addPortletBreadcrumbEntry(
			request, LanguageUtil.get(request, "all"), portletURL.toString());

		try {
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
		AllSitesItemSelectorViewDisplayContext.class);

	private final GroupSearchProvider _groupSearchProvider;
	private final PortletRequest _portletRequest;

}