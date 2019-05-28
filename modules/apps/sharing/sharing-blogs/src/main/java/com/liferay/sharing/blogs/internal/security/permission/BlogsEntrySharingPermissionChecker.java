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

package com.liferay.sharing.blogs.internal.security.permission;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.security.permission.SharingPermissionChecker;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = SharingPermissionChecker.class
)
public class BlogsEntrySharingPermissionChecker
	implements SharingPermissionChecker {

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, long groupId,
			Collection<SharingEntryAction> sharingEntryActions)
		throws PortalException {

		for (SharingEntryAction sharingEntryAction : sharingEntryActions) {
			if (!_actionKeysSet.contains(sharingEntryAction)) {
				return false;
			}

			if (!_blogsEntryModelResourcePermission.contains(
					permissionChecker, classPK,
					sharingEntryAction.getActionId())) {

				return false;
			}
		}

		return true;
	}

	private static final Set<SharingEntryAction> _actionKeysSet = new HashSet<>(
		Arrays.asList(
			SharingEntryAction.ADD_DISCUSSION, SharingEntryAction.UPDATE,
			SharingEntryAction.VIEW));

	@Reference(target = "(model.class.name=com.liferay.blogs.model.BlogsEntry)")
	private ModelResourcePermission<BlogsEntry>
		_blogsEntryModelResourcePermission;

}