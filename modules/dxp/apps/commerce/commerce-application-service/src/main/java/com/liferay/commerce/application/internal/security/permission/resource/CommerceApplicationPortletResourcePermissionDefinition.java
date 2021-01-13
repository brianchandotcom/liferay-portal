/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.application.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.PortletResourcePermissionDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	service = PortletResourcePermissionDefinition.class
)
public class CommerceApplicationPortletResourcePermissionDefinition
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
		return "com.liferay.commerce.application";
	}

}