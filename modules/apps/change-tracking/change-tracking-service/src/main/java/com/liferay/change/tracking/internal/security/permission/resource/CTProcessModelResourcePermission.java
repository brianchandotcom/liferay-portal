/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.security.permission.resource;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTProcess",
	service = ModelResourcePermission.class
)
public class CTProcessModelResourcePermission
	implements ModelResourcePermission<CTProcess> {

	@Override
	public void check(
			PermissionChecker permissionChecker, CTProcess ctProcess,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctProcess, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTProcess.class.getName(),
				ctProcess.getCtProcessId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long ctProcessId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctProcessId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTProcess.class.getName(), ctProcessId,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, CTProcess ctProcess,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				ctProcess.getCompanyId(), CTProcess.class.getName(),
				ctProcess.getCtProcessId(), ctProcess.getUserId(), actionId)) {

			return true;
		}

		Group group = _groupLocalService.fetchGroup(
			ctProcess.getCompanyId(),
			_classNameLocalService.getClassNameId(CTProcess.class),
			ctProcess.getCtProcessId());

		return permissionChecker.hasPermission(
			group, CTProcess.class.getName(), ctProcess.getCtProcessId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long ctProcessId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, _ctProcessLocalService.getCTProcess(ctProcessId),
			actionId);
	}

	@Override
	public String getModelName() {
		return CTProcess.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}