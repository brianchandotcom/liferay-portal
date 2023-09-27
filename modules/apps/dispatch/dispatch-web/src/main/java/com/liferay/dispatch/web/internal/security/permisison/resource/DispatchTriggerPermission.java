/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.security.permisison.resource;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class DispatchTriggerPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			DispatchTrigger dispatchTrigger, String actionId)
		throws PortalException {

		ModelResourcePermission<DispatchTrigger> modelResourcePermission =
			_dispatchTriggerModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, dispatchTrigger.getDispatchTriggerId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long dispatchTriggerId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DispatchTrigger> modelResourcePermission =
			_dispatchTriggerModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, dispatchTriggerId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<DispatchTrigger>>
		_dispatchTriggerModelResourcePermissionSnapshot = new Snapshot<>(
			DispatchTriggerPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dispatch.model.DispatchTrigger)");

}