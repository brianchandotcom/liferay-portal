/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return contains(
			permissionChecker, _sapEntryLocalService.getSAPEntry(sapEntryId),
			actionId);
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