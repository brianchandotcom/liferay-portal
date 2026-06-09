/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.internal.security.permission.resource;

import com.liferay.audience.constants.AudienceConstants;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.audience.model.AudienceEntry",
	service = ModelResourcePermission.class
)
public class AudienceEntryModelResourcePermission
	implements ModelResourcePermission<AudienceEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AudienceEntry audienceEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, audienceEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AudienceEntry.class.getName(),
				audienceEntry.getAudienceEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long audienceEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, audienceEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AudienceEntry.class.getName(),
				audienceEntryId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, AudienceEntry audienceEntry,
		String actionId) {

		return permissionChecker.hasPermission(
			null, AudienceEntry.class.getName(),
			audienceEntry.getAudienceEntryId(), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long audienceEntryId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, AudienceEntry.class.getName(), audienceEntryId, actionId);
	}

	@Override
	public String getModelName() {
		return AudienceEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(
		target = "(resource.name=" + AudienceConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}