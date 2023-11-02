/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Julius Lee
 */
@Component(
	property = "resource.name=" + CPConstants.RESOURCE_NAME_PRODUCT,
	service = PortletResourcePermission.class
)
public class CPPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			CPConstants.RESOURCE_NAME_PRODUCT,
			new StagedPortletPermissionLogic(
				_stagingPermission, CPPortletKeys.CP_DEFINITIONS));
	}

	@Reference
	private StagingPermission _stagingPermission;

}