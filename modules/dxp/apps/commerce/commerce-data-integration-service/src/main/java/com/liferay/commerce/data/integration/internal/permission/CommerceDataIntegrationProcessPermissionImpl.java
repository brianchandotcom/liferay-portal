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

package com.liferay.commerce.data.integration.internal.permission;

import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.permission.CommerceDataIntegrationProcessPermission;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true, service = CommerceDataIntegrationProcessPermission.class
)
public class CommerceDataIntegrationProcessPermissionImpl
	implements CommerceDataIntegrationProcessPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceDataIntegrationProcess, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceDataIntegrationProcess.class.getName(),
				commerceDataIntegrationProcess.
					getCommerceDataIntegrationProcessId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long commerceDataIntegrationProcessId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, commerceDataIntegrationProcessId,
				actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				CommerceDataIntegrationProcess.class.getName(),
				commerceDataIntegrationProcessId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				commerceDataIntegrationProcess.
					getCommerceDataIntegrationProcessId(),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long commerceDataIntegrationProcessId, String actionId)
		throws PortalException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			_commerceDataIntegrationProcessLocalService.
				fetchCommerceDataIntegrationProcess(
					commerceDataIntegrationProcessId);

		if (commerceDataIntegrationProcess == null) {
			return false;
		}

		return _contains(
			permissionChecker, commerceDataIntegrationProcess, actionId);
	}

	private boolean _contains(
			PermissionChecker permissionChecker,
			CommerceDataIntegrationProcess commerceDataIntegrationProcess,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				commerceDataIntegrationProcess.getCompanyId(),
				CommerceDataIntegrationProcess.class.getName(),
				commerceDataIntegrationProcess.
					getCommerceDataIntegrationProcessId(),
				commerceDataIntegrationProcess.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, CommerceDataIntegrationProcess.class.getName(),
			commerceDataIntegrationProcess.
				getCommerceDataIntegrationProcessId(),
			actionId);
	}

	@Reference
	private CommerceDataIntegrationProcessLocalService
		_commerceDataIntegrationProcessLocalService;

}