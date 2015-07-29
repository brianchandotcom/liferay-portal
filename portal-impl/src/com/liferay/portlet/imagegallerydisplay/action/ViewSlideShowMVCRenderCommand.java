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

package com.liferay.portlet.imagegallerydisplay.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/image_gallery_display/view_slide_show"
	},
	service = MVCRenderCommand.class
)
public class ViewSlideShowMVCRenderCommand extends GetFolderMVCRenderCommand {

	@Override
	protected String getPath() {
		return "/html/portlet/image_gallery_display/view_slide_show.jsp";
	}

}