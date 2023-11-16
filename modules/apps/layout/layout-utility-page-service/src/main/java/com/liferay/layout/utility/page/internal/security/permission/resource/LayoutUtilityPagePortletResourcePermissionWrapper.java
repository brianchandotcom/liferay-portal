/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.utility.page.constants.LayoutUtilityPageConstants;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Julius Lee
 */
@Component(
	property = "resource.name=" + LayoutUtilityPageConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class LayoutUtilityPagePortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			LayoutUtilityPageConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission, LayoutAdminPortletKeys.GROUP_PAGES));
	}

	@Reference
	private StagingPermission _stagingPermission;

}