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

package com.liferay.wiki.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.wiki.model.WikiPage;

/**
 * @author Preston Crary
 */
public class WikiPagePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		ModelResourcePermission<WikiPage> modelResourcePermission =
			_wikiPageModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, WikiPage page, String actionId)
		throws PortalException {

		ModelResourcePermission<WikiPage> modelResourcePermission =
			_wikiPageModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, page, actionId);
	}

	private static final Snapshot<ModelResourcePermission<WikiPage>>
		_wikiPageModelResourcePermissionSnapshot = new Snapshot<>(
			WikiPagePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.wiki.model.WikiPage)");

}