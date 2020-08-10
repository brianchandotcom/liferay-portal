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

package com.liferay.commerce.data.integration.web.internal.security.permisison.resource;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = {})
public class CommerceDataintegrationProcessPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			String actionId)
		throws PortalException {

		return _commerceDataIntegrationProcessModelResourcePermission.contains(
			permissionChecker,
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			long commerceDataIntegrationProcessId, String actionId)
		throws PortalException {

		return _commerceDataIntegrationProcessModelResourcePermission.contains(
			permissionChecker, commerceDataIntegrationProcessId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<CommerceDataIntegrationProcess>
			modelResourcePermission) {

		_commerceDataIntegrationProcessModelResourcePermission =
			modelResourcePermission;
	}

	private static ModelResourcePermission<CommerceDataIntegrationProcess>
		_commerceDataIntegrationProcessModelResourcePermission;

}