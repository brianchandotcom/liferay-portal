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
		"javax.portlet.display-name=My Contacts",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=My Contacts",
		"javax.portlet.info.short-title=My Contacts",
		"javax.portlet.info.title=My Contacts",
		"javax.portlet.init-param.view-template=/my_contacts/view.jsp",
		"javax.portlet.name=" + ContactsPortletKeys.MY_CONTACTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class MyContactsPortlet extends ContactsCenterPortlet {
}