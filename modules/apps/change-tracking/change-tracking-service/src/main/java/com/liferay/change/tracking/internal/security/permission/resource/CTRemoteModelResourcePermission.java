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

package com.liferay.change.tracking.internal.security.permission.resource;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.change.tracking.service.CTRemoteLocalService;
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
	property = "model.class.name=com.liferay.change.tracking.model.CTRemote",
	service = ModelResourcePermission.class
)
public class CTRemoteModelResourcePermission
	implements ModelResourcePermission<CTRemote> {

	@Override
	public void check(
			PermissionChecker permissionChecker, CTRemote ctRemote,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctRemote, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTRemote.class.getName(),
				ctRemote.getCtRemoteId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long ctRemoteId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, ctRemoteId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CTRemote.class.getName(), ctRemoteId,
				actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, CTRemote ctRemote,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				ctRemote.getCompanyId(), CTRemote.class.getName(),
				ctRemote.getCtRemoteId(), ctRemote.getUserId(), actionId)) {

			return true;
		}

		Group group = _groupLocalService.fetchGroup(
			ctRemote.getCompanyId(),
			_classNameLocalService.getClassNameId(CTRemote.class),
			ctRemote.getCtRemoteId());

		return permissionChecker.hasPermission(
			group, CTRemote.class.getName(), ctRemote.getCtRemoteId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long ctRemoteId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, _ctRemoteLocalService.getCTRemote(ctRemoteId),
			actionId);
	}

	@Override
	public String getModelName() {
		return CTRemote.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTRemoteLocalService _ctRemoteLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}