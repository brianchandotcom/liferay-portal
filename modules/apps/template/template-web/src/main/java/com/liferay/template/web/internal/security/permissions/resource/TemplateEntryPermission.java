/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.security.permissions.resource;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.template.model.TemplateEntry;

/**
 * @author Eudaldo Alonso
 */
public class TemplateEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, TemplateEntry templateEntry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMTemplate> modelResourcePermission =
			_ddmTemplateModelResourcePermissionSnapshot.get();

		DDMTemplateLocalService ddmTemplateLocalService =
			_ddmTemplateLocalServiceSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker,
			ddmTemplateLocalService.fetchDDMTemplate(
				templateEntry.getDDMTemplateId()),
			actionId);
	}

	private static final Snapshot<DDMTemplateLocalService>
		_ddmTemplateLocalServiceSnapshot = new Snapshot<>(
			TemplateEntryPermission.class, DDMTemplateLocalService.class);
	private static final Snapshot<ModelResourcePermission<DDMTemplate>>
		_ddmTemplateModelResourcePermissionSnapshot = new Snapshot<>(
			TemplateEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dynamic.data.mapping.model." +
				"DDMTemplate)");

}