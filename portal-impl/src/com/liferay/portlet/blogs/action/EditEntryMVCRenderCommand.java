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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.BLOGS,
		"javax.portlet.name=" + PortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + PortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/edit_entry"
	}
)
public class EditEntryMVCRenderCommand extends GetEntryMVCRenderCommand {

	@Override
	protected String getPath() {
		return "/html/portlet/blogs/edit_entry.jsp";
	}

}