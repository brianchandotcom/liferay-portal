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