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

package com.liferay.layout.set.prototype.web.portlet;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.service.LayoutSetPrototypeService;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-users-admin",
		"com.liferay.portlet.icon=/icons/site_settings.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Site Template Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/edit_layout_set_prototype.jsp",
		"javax.portlet.name=" + LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class SiteTemplateSettingsPortlet extends LayoutSetPrototypePortlet {

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeService(
		LayoutSetPrototypeService layoutSetPrototypeService) {

		this.layoutSetPrototypeService = layoutSetPrototypeService;
	}

}