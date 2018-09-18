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

package com.liferay.fragment.internal.security.permission.resource;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntry",
	service = ModelResourcePermission.class
)
public class FragmentEntryModelResourcePermission
	implements ModelResourcePermission<FragmentEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, FragmentEntry fragmentEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, fragmentEntry, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, FragmentEntry fragmentEntry,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, fragmentEntry.getFragmentEntryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long fragmentEntryId,
			String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.VIEW)) {
			return true;
		}

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		return _portletResourcePermission.contains(
			permissionChecker, fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);
	}

	@Override
	public String getModelName() {
		return FragmentEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")",
		unbind = "-"
	)
	private PortletResourcePermission _portletResourcePermission;

}