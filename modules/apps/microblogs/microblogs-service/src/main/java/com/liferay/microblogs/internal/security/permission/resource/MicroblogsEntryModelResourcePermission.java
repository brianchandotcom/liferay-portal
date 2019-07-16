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
	immediate = true,
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
				microblogsEntry.getUserId(), actionId)) {

			return true;
		}

		if (microblogsEntry.getSocialRelationType() == 0) {
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