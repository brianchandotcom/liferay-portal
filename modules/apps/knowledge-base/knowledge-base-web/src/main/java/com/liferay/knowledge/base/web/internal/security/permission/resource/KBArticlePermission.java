/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.security.permission.resource;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class KBArticlePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, KBArticle kbArticle,
			String actionId)
		throws PortalException {

		ModelResourcePermission<KBArticle> modelResourcePermission =
			_kbArticleModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, kbArticle, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		ModelResourcePermission<KBArticle> modelResourcePermission =
			_kbArticleModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	private static final Snapshot<ModelResourcePermission<KBArticle>>
		_kbArticleModelResourcePermissionSnapshot = new Snapshot<>(
			KBArticlePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.knowledge.base.model.KBArticle)");

}