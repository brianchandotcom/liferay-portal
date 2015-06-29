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

package com.liferay.portlet.blogsaggregator.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=", "mvc.command.name=/blogs_aggregator/view"
	}
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return "/html/portlet/blogs_aggregator/view.jsp";
	}

}