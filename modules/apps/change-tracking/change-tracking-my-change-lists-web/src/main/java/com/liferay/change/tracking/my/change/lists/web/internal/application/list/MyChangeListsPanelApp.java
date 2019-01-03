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

package com.liferay.change.tracking.my.change.lists.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.PortletCategoryKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=250",
		"panel.category.key=" + PortletCategoryKeys.USER_MY_ACCOUNT
	},
	service = PanelApp.class
)
public class MyChangeListsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CTPortletKeys.MY_CHANGE_LISTS;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.MY_CHANGE_LISTS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}