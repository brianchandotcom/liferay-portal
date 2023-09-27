/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseModelResourcePermissionWrapper<T extends ClassedModel>
	implements ModelResourcePermission<T> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		modelResourcePermission.check(permissionChecker, primaryKey, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		modelResourcePermission.check(permissionChecker, model, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		return modelResourcePermission.contains(
			permissionChecker, primaryKey, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		return modelResourcePermission.contains(
			permissionChecker, model, actionId);
	}

	@Override
	public String getModelName() {
		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		return modelResourcePermission.getModelName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		ModelResourcePermission<T> modelResourcePermission =
			_modelResourcePermissionDCLSingleton.getSingleton(
				this::doGetModelResourcePermission);

		return modelResourcePermission.getPortletResourcePermission();
	}

	protected abstract ModelResourcePermission<T>
		doGetModelResourcePermission();

	private final DCLSingleton<ModelResourcePermission<T>>
		_modelResourcePermissionDCLSingleton = new DCLSingleton<>();

}