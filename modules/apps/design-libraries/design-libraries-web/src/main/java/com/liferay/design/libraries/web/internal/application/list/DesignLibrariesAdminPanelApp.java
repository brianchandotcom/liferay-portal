/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.design.libraries.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.design.libraries.web.internal.constants.DesignLibrariesAdminPortletKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mario Leandro
 */
@Component(
	property = {
		"panel.app.order:Integer=50",
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS_DESIGN
	},
	service = PanelApp.class
)
public class DesignLibrariesAdminPanelApp extends BasePanelApp {

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return DesignLibrariesAdminPortletKeys.DESIGN_LIBRARIES_ADMIN;
	}

	@Reference(
		target = "(jakarta.portlet.name=" + DesignLibrariesAdminPortletKeys.DESIGN_LIBRARIES_ADMIN + ")"
	)
	private Portlet _portlet;

}