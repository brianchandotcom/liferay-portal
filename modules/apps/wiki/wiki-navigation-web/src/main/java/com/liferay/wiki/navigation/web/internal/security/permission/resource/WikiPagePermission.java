/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.navigation.web.internal.security.permission.resource;

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