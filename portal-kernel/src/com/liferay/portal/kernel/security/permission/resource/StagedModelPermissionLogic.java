/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Objects;
import java.util.function.ToLongFunction;

/**
 * @author Preston Crary
 */
public class StagedModelPermissionLogic<T extends GroupedModel>
	implements ModelResourcePermissionLogic<T> {

	public StagedModelPermissionLogic(
		StagingPermission stagingPermission, String portletId,
		ToLongFunction<T> primKeyToLongFunction) {

		_stagingPermission = Objects.requireNonNull(stagingPermission);
		_portletId = Objects.requireNonNull(portletId);
		_primKeyToLongFunction = Objects.requireNonNull(primKeyToLongFunction);
	}

	@Override
	public Boolean contains(
		PermissionChecker permissionChecker, String name, T model,
		String actionId) {

		return _stagingPermission.hasPermission(
			permissionChecker, model.getGroupId(), name,
			_primKeyToLongFunction.applyAsLong(model), _portletId, actionId);
	}

	private final String _portletId;
	private final ToLongFunction<T> _primKeyToLongFunction;
	private final StagingPermission _stagingPermission;

}