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

package com.liferay.microblogs.web.internal.security.permission.resource;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class MicroblogsEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long microblogsEntryId,
			String actionId)
		throws PortalException {

		return _microblogsEntryModelResourcePermission.contains(
			permissionChecker, microblogsEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			MicroblogsEntry microblogsEntry, String actionId)
		throws PortalException {

		return _microblogsEntryModelResourcePermission.contains(
			permissionChecker, microblogsEntry, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.microblogs.model.MicroblogsEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<MicroblogsEntry> modelResourcePermission) {

		_microblogsEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<MicroblogsEntry>
		_microblogsEntryModelResourcePermission;

}