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

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=false",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-javascript=/js/custom_fields.js",
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-mapping",
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.header-portlet-css=css/main.css",
		"com.liferay.portlet.autopropagated-parameters=refererPortletName",
		"com.liferay.portlet.autopropagated-parameters=refererWebDAVToken",
		"com.liferay.portlet.autopropagated-parameters=scopeTitle",
		"com.liferay.portlet.autopropagated-parameters=showAncestorScopes",
		"com.liferay.portlet.autopropagated-parameters=showBackURL",
		"com.liferay.portlet.autopropagated-parameters=showManageTemplates",
		"com.liferay.portlet.autopropagated-parameters=showToolbar",
		"javax.portlet.display-name=Dynamic Data Mapping Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + PortletKeys.DYNAMIC_DATA_MAPPING,
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = { Portlet.class }
)
public class DDMPortlet extends MVCPortlet {

}
