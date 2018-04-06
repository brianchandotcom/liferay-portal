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

package com.liferay.journal.service.permission;

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @deprecated As of 4.0.0, with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFeed",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class JournalFeedPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalFeed feed,
			String actionId)
		throws PortalException {

		_journalFeedModelResourcePermission.check(
			permissionChecker, feed, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long id, String actionId)
		throws PortalException {

		_journalFeedModelResourcePermission.check(
			permissionChecker, id, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public static void check(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, feedId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, JournalFeed.class.getName(), feedId,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalFeed feed,
			String actionId)
		throws PortalException {

		return _journalFeedModelResourcePermission.contains(
			permissionChecker, feed, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long feedId, String actionId)
		throws PortalException {

		return _journalFeedModelResourcePermission.contains(
			permissionChecker, feedId, actionId);
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String feedId,
			String actionId)
		throws PortalException {

		JournalFeed feed = _journalFeedLocalService.getFeed(groupId, feedId);

		return _journalFeedModelResourcePermission.contains(
			permissionChecker, feed, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_journalFeedModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFeed)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<JournalFeed> modelResourcePermission) {

		_journalFeedModelResourcePermission = modelResourcePermission;
	}

	private static JournalFeedLocalService _journalFeedLocalService;
	private static ModelResourcePermission<JournalFeed>
		_journalFeedModelResourcePermission;

}