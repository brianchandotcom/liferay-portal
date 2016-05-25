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
import com.liferay.blogs.web.constants.BlogsWebKeys;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/blogs/view"
	},
	service = MVCRenderCommand.class
)
public class BlogsAdminViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			BlogsWebKeys.DL_MIME_TYPE_DISPLAY_CONTEXT,
			_dlMimeTypeDisplayContext);

		return "/blogs_admin/view.jsp";
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void setDLMimeTypeDisplayContext(
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

		_dlMimeTypeDisplayContext = dlMimeTypeDisplayContext;
	}

	public void unsetDLMimeTypeDisplayContext(
		DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

		_dlMimeTypeDisplayContext = null;
	}

	private DLMimeTypeDisplayContext _dlMimeTypeDisplayContext;

}