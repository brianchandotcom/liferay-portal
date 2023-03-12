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

package com.liferay.depot.web.internal.application.list;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelAppShowFilter;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DepotPanelAppController.class)
public class DepotPanelAppController {

	public boolean isShow(PanelApp panelApp, long groupId) {
		String portletId = panelApp.getPortletId();

		if (_isAlwaysShow(portletId)) {
			return true;
		}

		return _depotApplicationController.isEnabled(portletId, groupId);
	}

	public boolean isShow(String portletId) {
		if (_isAlwaysShow(portletId)) {
			return true;
		}

		return _depotApplicationController.isEnabled(portletId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_panelCategoryHelper = new PanelCategoryHelper(
			_panelAppRegistry, _panelCategoryRegistry);

		_serviceRegistration = bundleContext.registerService(
			PanelAppShowFilter.class,
			(panelApp, permissionChecker, group) -> {
				if (group.isDepot() &&
					!DepotPanelAppController.this.isShow(
						panelApp, group.getGroupId())) {

					return false;
				}

				return panelApp.isShow(permissionChecker, group);
			},
			null);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private boolean _isAlwaysShow(String portletId) {
		if (portletId.equals(DepotPortletKeys.DEPOT_ADMIN) ||
			portletId.equals(DepotPortletKeys.DEPOT_SETTINGS) ||
			_panelCategoryHelper.isControlPanelApp(portletId) ||
			_panelCategoryHelper.isApplicationsMenuApp(portletId)) {

			return true;
		}

		return false;
	}

	@Reference
	private DepotApplicationController _depotApplicationController;

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	private PanelCategoryHelper _panelCategoryHelper;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	private ServiceRegistration<?> _serviceRegistration;

}