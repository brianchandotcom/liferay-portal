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

package com.liferay.portal.security.service.access.policy.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.security.service.access.policy.model.SAPEntry",
	service = ModelResourcePermission.class
)
public class SAPEntryModelResourcePermission
	implements ModelResourcePermission<SAPEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long sapEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sapEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPEntry.class.getName(), sapEntryId,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sapEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPEntry.class.getName(),
				sapEntry.getSapEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long sapEntryId,
			String actionId)
		throws PortalException {

		SAPEntry sapEntry = _sapEntryLocalService.getSAPEntry(sapEntryId);

		return contains(permissionChecker, sapEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
			null, SAPEntry.class.getName(), sapEntry.getSapEntryId(), actionId);
	}

	@Override
	public String getModelName() {
		return SAPEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(target = "(resource.name=" + SAPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

}