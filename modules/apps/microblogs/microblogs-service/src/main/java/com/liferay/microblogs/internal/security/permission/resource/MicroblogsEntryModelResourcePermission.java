/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.security.permission.resource;

import com.liferay.microblogs.constants.MicroblogsConstants;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.social.kernel.service.SocialRelationLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.microblogs.model.MicroblogsEntry",
	service = ModelResourcePermission.class
)
public class MicroblogsEntryModelResourcePermission
	implements ModelResourcePermission<MicroblogsEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MicroblogsEntry.class.getName(), primaryKey,
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			MicroblogsEntry microblogsEntry, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, microblogsEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, MicroblogsEntry.class.getName(),
				microblogsEntry.getMicroblogsEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long microblogsEntryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_microblogsEntryLocalService.getMicroblogsEntry(microblogsEntryId),
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, MicroblogsEntry microblogsEntry,
		String actionId) {

		if (actionId.equals(ActionKeys.DELETE) ||
			actionId.equals(ActionKeys.UPDATE)) {

			if (permissionChecker.hasOwnerPermission(
					microblogsEntry.getCompanyId(),
					MicroblogsEntry.class.getName(),
					microblogsEntry.getMicroblogsEntryId(),
					microblogsEntry.getUserId(), actionId)) {

				return true;
			}

			return false;
		}

		if (permissionChecker.hasOwnerPermission(
				microblogsEntry.getCompanyId(), MicroblogsEntry.class.getName(),
				microblogsEntry.getMicroblogsEntryId(),
				microblogsEntry.getUserId(), actionId) ||
			(microblogsEntry.getSocialRelationType() == 0)) {

			return true;
		}

		if ((microblogsEntry.getUserId() != permissionChecker.getUserId()) &&
			_socialRelationLocalService.hasRelation(
				permissionChecker.getUserId(), microblogsEntry.getUserId(),
				microblogsEntry.getSocialRelationType())) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return MicroblogsEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private MicroblogsEntryLocalService _microblogsEntryLocalService;

	@Reference(
		target = "(resource.name=" + MicroblogsConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private SocialRelationLocalService _socialRelationLocalService;

}