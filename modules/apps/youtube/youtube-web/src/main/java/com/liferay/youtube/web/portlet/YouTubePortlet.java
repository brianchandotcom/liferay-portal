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

package com.liferay.youtube.web.portlet;

import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.youtube.web.constants.YouTubePortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=youtube-portlet",
		"com.liferay.portlet.display-category=category.entertainment",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/youtube.png",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=YouTube",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + YouTubePortletKeys.YOUTUBE,
		"javax.portlet.portlet-info.keywords=YouTube",
		"javax.portlet.portlet-info.short-title=YouTube",
		"javax.portlet.portlet-info.title=YouTube",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class YouTubePortlet extends MVCPortlet {

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.youtube.web)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

}