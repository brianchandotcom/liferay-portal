/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.security.permission.resource;

import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class CTCollectionTemplatePermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			CTCollectionTemplate ctCollectionTemplate, String actionId)
		throws PortalException {

		ModelResourcePermission<CTCollectionTemplate> modelResourcePermission =
			_ctCollectionTemplateModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ctCollectionTemplate, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ctCollectionTemplateId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CTCollectionTemplate> modelResourcePermission =
			_ctCollectionTemplateModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ctCollectionTemplateId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CTCollectionTemplate>>
		_ctCollectionTemplateModelResourcePermissionSnapshot = new Snapshot<>(
			CTCollectionTemplatePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.change.tracking.model." +
				"CTCollectionTemplate)");

}