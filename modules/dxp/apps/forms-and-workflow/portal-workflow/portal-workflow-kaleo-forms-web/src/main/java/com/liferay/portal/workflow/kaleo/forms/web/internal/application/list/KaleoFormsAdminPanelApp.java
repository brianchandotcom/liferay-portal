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

package com.liferay.portal.workflow.kaleo.forms.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		"service.ranking:Integer=1200"
	},
	service = PanelApp.class
)
public class KaleoFormsAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return KaleoFormsPortletKeys.KALEO_FORMS_ADMIN;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}