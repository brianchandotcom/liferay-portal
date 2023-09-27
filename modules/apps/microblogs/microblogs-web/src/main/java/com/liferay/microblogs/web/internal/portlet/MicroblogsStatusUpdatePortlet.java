/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.web.internal.portlet;

import com.liferay.microblogs.constants.MicroblogsPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=microblogs-portlet",
		"com.liferay.portlet.display-category=category.collaboration",
		"com.liferay.portlet.footer-portlet-javascript=/microblogs/js/main.js",
		"com.liferay.portlet.header-portlet-css=/microblogs/css/main.css",
		"com.liferay.portlet.icon=/microblogs/icons/microblogs.png",
		"javax.portlet.display-name=Microblogs Status Update",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-jsp=/status_update/configuration.jsp",
		"javax.portlet.init-param.view-template=/status_update/view.jsp",
		"javax.portlet.name=" + MicroblogsPortletKeys.MICROBLOGS_STATUS_UPDATE,
		"javax.portlet.portlet-info.keywords=Microblogs Status Update",
		"javax.portlet.portlet-info.short-title=Microblogs Status Update",
		"javax.portlet.portlet-info.title=Microblogs Status Update",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user"
	},
	service = Portlet.class
)
public class MicroblogsStatusUpdatePortlet extends MicroblogsPortlet {
}