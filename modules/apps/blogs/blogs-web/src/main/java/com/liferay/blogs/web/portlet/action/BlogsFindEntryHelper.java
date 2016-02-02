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

package com.liferay.blogs.web.portlet.action;

import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.BaseFindActionHelper;
import com.liferay.portal.struts.FindActionHelper;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;

import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portlet.blogs.model.BlogsEntry",
	service = FindActionHelper.class
)
public class BlogsFindEntryHelper extends BaseFindActionHelper {

	@Override
	public long getGroupId(long primaryKey) throws Exception {
		BlogsEntry entry = _blogsEntryLocalService.getEntry(primaryKey);

		return entry.getGroupId();
	}

	@Override
	public String getPrimaryKeyParameterName() {
		return "entryId";
	}

	@Override
	public PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		portletURL.setWindowState(WindowState.MAXIMIZED);

		return portletURL;
	}

	@Override
	public void setPrimaryKeyParameter(PortletURL portletURL, long primaryKey)
		throws Exception {

		BlogsEntry entry = _blogsEntryLocalService.getEntry(primaryKey);

		portletURL.setParameter("urlTitle", entry.getUrlTitle());
	}

	@Override
	protected void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL) {

		boolean showAllEntries = ParamUtil.getBoolean(
			request, "showAllEntries");

		if (showAllEntries) {
			String mvcPath = null;

			if (portletId.equals(BlogsPortletKeys.BLOGS)) {
				mvcPath += "/blogs/view.jsp";
			}
			else if (portletId.equals(BlogsPortletKeys.BLOGS_ADMIN)) {
				mvcPath += "/blogs_admin/view.jsp";
			}
			else {
				mvcPath += "/blogs_aggregator/view.jsp";
			}

			portletURL.setParameter("mvcPath", mvcPath);
		}
		else {
			String mvcRenderCommandName = null;

			if (portletId.equals(BlogsPortletKeys.BLOGS)) {
				mvcRenderCommandName = "/blogs/view_entry";
			}
			else if (portletId.equals(BlogsPortletKeys.BLOGS_ADMIN)) {
				mvcRenderCommandName = "/blogs_admin/view_entry";
			}
			else {
				mvcRenderCommandName = "/blogs_aggregator/view";
			}

			portletURL.setParameter(
				"mvcRenderCommandName", mvcRenderCommandName);
		}
	}

	@Override
	protected PortletLayoutFinder getPortletLayoutFinder() {
		return _portletLayoutFinder;
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portlet.blogs.model.BlogsEntry)",
		unbind = "-"
	)
	protected void setPortletLayoutFinder(
		PortletLayoutFinder portletPageFinder) {

		_portletLayoutFinder = portletPageFinder;
	}

	private BlogsEntryLocalService _blogsEntryLocalService;
	private PortletLayoutFinder _portletLayoutFinder;

}