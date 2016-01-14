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

import com.liferay.blogs.web.BlogsItemSelectorHelper;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.blogs.web.constants.BlogsWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/edit_entry"
	},
	service = MVCRenderCommand.class
)
public class EditEntryMVCRenderCommand extends GetEntryMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		request.setAttribute(
			BlogsWebKeys.BLOGS_ITEM_SELECTOR_HELPER, _blogsItemSelectorHelper);

		return super.render(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	public void setItemSelectorHelper(
		BlogsItemSelectorHelper blogsItemSelectorHelper) {

		_blogsItemSelectorHelper = blogsItemSelectorHelper;
	}

	@Override
	protected String getPath() {
		return "/blogs/edit_entry.jsp";
	}

	private BlogsItemSelectorHelper _blogsItemSelectorHelper;

}