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

package com.liferay.dynamic.data.mapping.web.portlet;

import com.liferay.portal.util.PortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=scopeTitle",
		"com.liferay.portlet.autopropagated-parameters=showAncestorScopes",
		"com.liferay.portlet.autopropagated-parameters=showManageTemplates",
		"com.liferay.portlet.autopropagated-parameters=showToolbar",
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-mapping",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/portlet_display_template.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Application Display Templates",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.refererWebDAVToken=application_display_template",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view_template.jsp",
		"javax.portlet.name=" + PortletKeys.PORTLET_DISPLAY_TEMPLATE,
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class PortletDisplayTemplatePortlet extends DDMPortlet {
}