/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.saved.content.constants.MySavedContentPortletKeys;
import com.liferay.saved.content.constants.SavedContentConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 * @author Julius Lee
 */
@Component(
	property = "resource.name=" + SavedContentConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class SavedContentPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			SavedContentConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission,
				MySavedContentPortletKeys.MY_SAVED_CONTENT));
	}

	@Reference
	private StagingPermission _stagingPermission;

}