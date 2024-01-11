/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.application.list;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppShowFilter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = PanelAppShowFilter.class)
public class DepotPanelAppShowFilter implements PanelAppShowFilter {

	@Override
	public boolean isShow(
			PanelApp panelApp, PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group.isDepot() &&
			!_depotPanelAppController.isShow(panelApp, group.getGroupId())) {

			return false;
		}

		return panelApp.isShow(permissionChecker, group);
	}

	@Reference
	private DepotPanelAppController _depotPanelAppController;

}