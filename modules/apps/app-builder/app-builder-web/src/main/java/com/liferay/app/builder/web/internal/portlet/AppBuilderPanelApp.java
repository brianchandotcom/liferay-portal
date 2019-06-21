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

package com.liferay.app.builder.web.internal.portlet;

import com.liferay.app.builder.web.constants.AppBuilderPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + AppBuilderPortletKeys.CONTROL_PANEL_APP_BUILDER
	},
	service = PanelApp.class
)
public class AppBuilderPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return AppBuilderPortletKeys.APP_BUILDER;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + AppBuilderPortletKeys.APP_BUILDER + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}