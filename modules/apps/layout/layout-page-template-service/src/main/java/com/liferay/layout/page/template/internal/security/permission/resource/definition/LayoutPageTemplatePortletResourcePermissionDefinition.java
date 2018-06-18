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

package com.liferay.layout.page.template.internal.security.permission.resource.definition;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.PortletResourcePermissionDefinition;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true, service = PortletResourcePermissionDefinition.class
)
public class LayoutPageTemplatePortletResourcePermissionDefinition
	implements PortletResourcePermissionDefinition {

	@Override
	public PortletResourcePermissionLogic[]
		getPortletResourcePermissionLogics() {

		return new PortletResourcePermissionLogic[] {
			new StagedPortletPermissionLogic(
				_stagingPermission, LayoutAdminPortletKeys.GROUP_PAGES)
		};
	}

	@Override
	public String getResourceName() {
		return LayoutPageTemplateConstants.RESOURCE_NAME;
	}

	@Reference
	private StagingPermission _stagingPermission;

}