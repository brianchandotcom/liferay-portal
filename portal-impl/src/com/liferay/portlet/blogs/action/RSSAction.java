/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.rss.BlogsCompanyRSSRenderer;
import com.liferay.portlet.blogs.rss.BlogsGroupRSSRenderer;
import com.liferay.portlet.blogs.rss.BlogsOrganizationRSSRenderer;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.rss.RSSRenderer;
import com.liferay.portlet.rss.action.DefaultRSSAction;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSAction extends DefaultRSSAction {

	@Override
	public RSSRenderer getRSSRenderer(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = ParamUtil.getLong(request, "companyId");
		long groupId = ParamUtil.getLong(request, "groupId");
		int max = ParamUtil.getInteger(
			request, "max", SearchContainer.DEFAULT_DELTA);
		long organizationId = ParamUtil.getLong(request, "organizationId");

		List<BlogsEntry> entries = null;

		if (companyId > 0) {
			entries = BlogsEntryServiceUtil.getCompanyEntries(
				companyId, new Date(), WorkflowConstants.STATUS_APPROVED, max);

			return new BlogsCompanyRSSRenderer(
				CompanyLocalServiceUtil.getCompany(companyId), entries,
				request);
		}
		else if (groupId > 0) {
			entries = BlogsEntryServiceUtil.getGroupEntries(
				groupId, new Date(), WorkflowConstants.STATUS_APPROVED, max);

			return new BlogsGroupRSSRenderer(
				GroupLocalServiceUtil.getGroup(
					groupId), entries, request, true);
		}
		else if (organizationId > 0) {
			entries = BlogsEntryServiceUtil.getOrganizationEntries(
				organizationId, new Date(), WorkflowConstants.STATUS_APPROVED,
				max);

			return new BlogsOrganizationRSSRenderer(
				OrganizationLocalServiceUtil.getOrganization(organizationId),
				entries, request);
		}
		else if (themeDisplay.getLayout() != null) {
			groupId = themeDisplay.getScopeGroupId();

			entries = BlogsEntryServiceUtil.getGroupEntries(
				groupId, new Date(), WorkflowConstants.STATUS_APPROVED, max);

			return new BlogsGroupRSSRenderer(
				GroupLocalServiceUtil.getGroup(groupId), entries, request,
				false);
		}

		throw new UnsupportedOperationException();
	}

}