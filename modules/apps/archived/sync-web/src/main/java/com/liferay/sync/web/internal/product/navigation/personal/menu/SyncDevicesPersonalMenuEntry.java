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

package com.liferay.sync.web.internal.product.navigation.personal.menu;

import com.liferay.product.navigation.personal.menu.BasePersonalMenuEntry;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.sync.constants.SyncPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.personal.menu.entry.order:Integer=400",
		"product.navigation.personal.menu.group:Integer=300"
	},
	service = PersonalMenuEntry.class
)
public class SyncDevicesPersonalMenuEntry extends BasePersonalMenuEntry {

	@Override
	public String getPortletId() {
		return SyncPortletKeys.SYNC_DEVICES_PORTLET;
	}

}