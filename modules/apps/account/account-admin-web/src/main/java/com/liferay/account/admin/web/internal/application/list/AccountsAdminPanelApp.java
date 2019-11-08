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

package com.liferay.account.admin.web.internal.application.list;

import com.liferay.account.constants.AccountsPanelCategoryKeys;
import com.liferay.account.constants.AccountsPortletKeys;
import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + AccountsPanelCategoryKeys.CONTROL_PANEL_ACCOUNTS_ADMIN
	},
	service = PanelApp.class
)
public class AccountsAdminPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return AccountsPortletKeys.ACCOUNT_ENTRIES_ADMIN;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + AccountsPortletKeys.ACCOUNT_ENTRIES_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}