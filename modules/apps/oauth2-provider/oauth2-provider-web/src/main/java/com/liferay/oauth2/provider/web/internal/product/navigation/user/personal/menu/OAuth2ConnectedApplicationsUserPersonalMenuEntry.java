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

package com.liferay.oauth2.provider.web.internal.product.navigation.user.personal.menu;

import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.product.navigation.user.personal.menu.BaseUserPersonalMenuEntry;
import com.liferay.product.navigation.user.personal.menu.UserPersonalMenuEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.user.personal.menu.entry.order:Integer=200",
		"product.navigation.user.personal.menu.group:Integer=300"
	},
	service = UserPersonalMenuEntry.class
)
public class OAuth2ConnectedApplicationsUserPersonalMenuEntry
	extends BaseUserPersonalMenuEntry {

	@Override
	protected String getPortletId() {
		return OAuth2ProviderPortletKeys.OAUTH2_CONNECTED_APPLICATIONS;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_CONNECTED_APPLICATIONS + ")",
		unbind = "-"
	)
	protected void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}