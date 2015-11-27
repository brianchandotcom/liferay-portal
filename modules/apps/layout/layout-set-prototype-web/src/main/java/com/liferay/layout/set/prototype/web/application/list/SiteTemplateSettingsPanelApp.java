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

package com.liferay.layout.set.prototype.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION,
		"service.ranking:Integer=100"
	},
	service = PanelApp.class
)
public class SiteTemplateSettingsPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + LayoutSetPrototypePortletKeys.SITE_TEMPLATE_SETTINGS + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}