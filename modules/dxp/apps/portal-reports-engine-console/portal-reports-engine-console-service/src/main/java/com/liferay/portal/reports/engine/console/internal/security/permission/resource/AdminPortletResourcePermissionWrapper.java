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

package com.liferay.portal.reports.engine.console.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leon Chi
 */
@Component(
	property = "resource.name=" + ReportsEngineConsoleConstants.RESOURCE_NAME,
	service = PortletResourcePermission.class
)
public class AdminPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
		return PortletResourcePermissionFactory.create(
			ReportsEngineConsoleConstants.RESOURCE_NAME,
			new StagedPortletPermissionLogic(
				_stagingPermission,
				ReportsEngineConsolePortletKeys.REPORTS_ADMIN));
	}

	@Reference
	private StagingPermission _stagingPermission;

}