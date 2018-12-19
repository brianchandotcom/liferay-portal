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

package com.liferay.contacts.web.internal.portlet;

import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.model.Release;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=contacts-portlet",
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.friendly-url-mapping=contacts",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.icon=/icons/contacts_center.png",
		"javax.portlet.display-name=Members",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Members",
		"javax.portlet.info.short-title=Members",
		"javax.portlet.info.title=Members",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ContactsPortletKeys.MEMBERS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MembersPortlet extends ContactsCenterPortlet {

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.contacts.web)(&(release.schema.version>=1.0.1)(!(release.schema.version>=1.1.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

}