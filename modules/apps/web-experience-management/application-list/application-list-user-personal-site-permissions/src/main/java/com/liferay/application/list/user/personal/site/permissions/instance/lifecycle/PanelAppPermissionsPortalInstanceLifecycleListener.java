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

package com.liferay.application.list.user.personal.site.permissions.instance.lifecycle;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.user.personal.site.permissions.UserPersonalSitePermissions;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class PanelAppPermissionsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		List<PanelApp> panelApps = panelCategoryHelper.getAllPanelApps(
			PanelCategoryKeys.SITE_ADMINISTRATION);

		List<Portlet> portlets = new ArrayList<>(panelApps.size());

		for (PanelApp panelApp : panelApps) {
			Portlet portlet = _portletLocalService.getPortletById(
				panelApp.getPortletId());

			portlets.add(portlet);
		}

		_userPersonalSitePermissions.initPermissions(
			company.getCompanyId(), portlets);
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserPersonalSitePermissions(
		UserPersonalSitePermissions userPersonalSitePermissions) {

		_userPersonalSitePermissions = userPersonalSitePermissions;
	}

	private PanelAppRegistry _panelAppRegistry;
	private PanelCategoryRegistry _panelCategoryRegistry;
	private PortletLocalService _portletLocalService;
	private UserPersonalSitePermissions _userPersonalSitePermissions;

}