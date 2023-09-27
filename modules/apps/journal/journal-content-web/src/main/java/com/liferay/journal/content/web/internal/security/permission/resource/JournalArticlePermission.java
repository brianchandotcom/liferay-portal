/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.security.permission.resource;

import com.liferay.journal.model.JournalArticle;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class JournalArticlePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, JournalArticle article,
			String actionId)
		throws PortalException {

		ModelResourcePermission<JournalArticle> modelResourcePermission =
			_journalArticleModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, article, actionId);
	}

	private static final Snapshot<ModelResourcePermission<JournalArticle>>
		_journalArticleModelResourcePermissionSnapshot = new Snapshot<>(
			JournalArticlePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.journal.model.JournalArticle)");

}