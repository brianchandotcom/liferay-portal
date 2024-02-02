/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.internal.security.permission.resource;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = "model.class.name=com.liferay.contacts.model.Entry",
	service = ModelResourcePermission.class
)
public class EntryModelResourcePermission
	implements ModelResourcePermission<Entry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, Entry entry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Entry.class.getName(), entry.getEntryId(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Entry.class.getName(), entryId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Entry entry, String actionId)
		throws PortalException {

		return contains(permissionChecker, entry.getEntryId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		Entry entry = entryLocalService.fetchEntry(entryId);

		if ((entry != null) &&
			permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), Entry.class.getName(),
				entryId, entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), Entry.class.getName(), entryId, actionId);
	}

	@Override
	public String getModelName() {
		return Entry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected EntryLocalService entryLocalService;

}