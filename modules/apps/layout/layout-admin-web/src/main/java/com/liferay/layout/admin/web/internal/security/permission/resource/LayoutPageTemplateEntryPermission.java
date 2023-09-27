/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.security.permission.resource;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class LayoutPageTemplateEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, long layoutPageTemplateEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutPageTemplateEntry>
			modelResourcePermission =
				_layoutPageTemplateEntryModelResourcePermissionSnapshot.get();

		modelResourcePermission.check(
			permissionChecker, layoutPageTemplateEntryId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker,
			LayoutPageTemplateEntry layoutPageTemplateEntry, String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutPageTemplateEntry>
			modelResourcePermission =
				_layoutPageTemplateEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, layoutPageTemplateEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long layoutPageTemplateEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<LayoutPageTemplateEntry>
			modelResourcePermission =
				_layoutPageTemplateEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, layoutPageTemplateEntryId, actionId);
	}

	private static final Snapshot
		<ModelResourcePermission<LayoutPageTemplateEntry>>
			_layoutPageTemplateEntryModelResourcePermissionSnapshot =
				new Snapshot<>(
					LayoutPageTemplateEntryPermission.class,
					Snapshot.cast(ModelResourcePermission.class),
					"(model.class.name=com.liferay.layout.page.template." +
						"model.LayoutPageTemplateEntry)");

}