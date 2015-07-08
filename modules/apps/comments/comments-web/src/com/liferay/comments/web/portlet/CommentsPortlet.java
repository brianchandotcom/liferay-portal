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

package com.liferay.comments.web.portlet;

import com.liferay.comments.web.constants.CommentsPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.icon=/icons/comments.png",
		"javax.portlet.display-name=Comments",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/comments/",
		"javax.portlet.name=" + CommentsPortletKeys.COMMENTS,
		"javax.portlet.security-role-ref=guest",
		"javax.portlet.security-role-ref=power-user",
		"javax.portlet.security-role-ref=user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class CommentsPortlet extends MVCPortlet {
}