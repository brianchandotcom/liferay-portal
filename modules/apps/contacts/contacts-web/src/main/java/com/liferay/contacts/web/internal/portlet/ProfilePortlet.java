/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.web.internal.portlet;

import com.liferay.contacts.web.internal.constants.ContactsPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=contacts-portlet",
		"com.liferay.portlet.display-category=category.social",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/contacts_center.png",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Profile",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Profile",
		"javax.portlet.info.short-title=Profile",
		"javax.portlet.info.title=Profile",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.view-template=/profile/view.jsp",
		"javax.portlet.name=" + ContactsPortletKeys.PROFILE,
		"javax.portlet.portlet-mode=text/html;config",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class ProfilePortlet extends ContactsCenterPortlet {
}