/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.scim.configuration.web.internal.constants.ScimWebKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.scopeable=false",
		"javax.portlet.display-name=SCIM",
		"javax.portlet.name=" + ScimWebKeys.SCIM_CONFIGURATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class SCIMPortlet extends MVCPortlet {
}