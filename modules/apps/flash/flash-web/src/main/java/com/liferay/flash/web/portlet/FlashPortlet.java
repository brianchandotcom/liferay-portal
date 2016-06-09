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

package com.liferay.flash.web.portlet;

import com.liferay.flash.web.constants.FlashPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=flash-portlet",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.icon=/icons/flash.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Flash", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FlashPortletKeys.FLASH,
		"javax.portlet.portlet-info.keywords=Flash",
		"javax.portlet.portlet-info.short-title=Flash",
		"javax.portlet.portlet-info.title=Flash",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class FlashPortlet extends MVCPortlet {
}