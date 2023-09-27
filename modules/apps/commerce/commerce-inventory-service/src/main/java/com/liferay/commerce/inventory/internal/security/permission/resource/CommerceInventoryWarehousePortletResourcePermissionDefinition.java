/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.security.permission.resource;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.PortletResourcePermissionDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = PortletResourcePermissionDefinition.class)
public class CommerceInventoryWarehousePortletResourcePermissionDefinition
	implements PortletResourcePermissionDefinition {

	@Override
	public PortletResourcePermissionLogic[]
		getPortletResourcePermissionLogics() {

		return new PortletResourcePermissionLogic[] {
			(permissionChecker, name, group, actionId) -> {
				if (permissionChecker.hasPermission(group, name, 0, actionId)) {
					return true;
				}

				return false;
			}
		};
	}

	@Override
	public String getResourceName() {
		return CommerceInventoryConstants.RESOURCE_NAME;
	}

}