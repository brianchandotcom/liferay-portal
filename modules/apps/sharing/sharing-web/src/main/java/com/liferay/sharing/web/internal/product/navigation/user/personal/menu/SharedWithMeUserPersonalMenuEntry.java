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

package com.liferay.sharing.web.internal.product.navigation.user.personal.menu;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.product.navigation.user.personal.menu.BaseUserPersonalMenuEntry;
import com.liferay.product.navigation.user.personal.menu.UserPersonalMenuEntry;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.user.personal.menu.entry.order:Integer=300",
		"product.navigation.user.personal.menu.group:Integer=200"
	},
	service = UserPersonalMenuEntry.class
)
public class SharedWithMeUserPersonalMenuEntry
	extends BaseUserPersonalMenuEntry {

	@Override
	protected String getPortletId() {
		return SharingPortletKeys.SHARED_WITH_ME;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + SharingPortletKeys.SHARED_WITH_ME + ")",
		unbind = "-"
	)
	protected void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}