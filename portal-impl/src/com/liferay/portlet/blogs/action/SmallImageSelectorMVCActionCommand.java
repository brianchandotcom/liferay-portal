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

import com.liferay.portal.kernel.image.selector.ImageSelectorUploadHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.image.selector.SmallImageSelectorUploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Sergio González
 */
@OSGiBeanProperties(
	property = {
		"javax.portlet.name=" + PortletKeys.BLOGS,
		"javax.portlet.name=" + PortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/blogs/small_image_selector"
	}
)
public class SmallImageSelectorMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_imageSelectorUploadHandler.uploadSelectedImage(
			actionRequest, actionResponse);
	}

	private final ImageSelectorUploadHandler _imageSelectorUploadHandler =
		new SmallImageSelectorUploadHandler();

}