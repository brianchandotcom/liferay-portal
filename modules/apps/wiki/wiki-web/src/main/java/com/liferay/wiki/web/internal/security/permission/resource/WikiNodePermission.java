/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.security.permission.resource;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.wiki.model.WikiNode;

/**
 * @author Preston Crary
 */
public class WikiNodePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException {

		ModelResourcePermission<WikiNode> modelResourcePermission =
			_wikiNodeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, nodeId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, WikiNode node, String actionId)
		throws PortalException {

		ModelResourcePermission<WikiNode> modelResourcePermission =
			_wikiNodeModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, node, actionId);
	}

	private static final Snapshot<ModelResourcePermission<WikiNode>>
		_wikiNodeModelResourcePermissionSnapshot = new Snapshot<>(
			WikiNodePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.wiki.model.WikiNode)");

}