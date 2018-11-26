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

package com.liferay.portal.kernel.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class DefaultModelResourcePermission<T extends GroupedModel>
	implements ModelResourcePermission<T> {

	public DefaultModelResourcePermission(
		ModelResourcePermissionDefinition<T> modelResourcePermissionDefinition,
		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics) {

		_modelResourcePermissionDefinition = modelResourcePermissionDefinition;
		_modelResourcePermissionLogics = modelResourcePermissionLogics;

		Class<T> modelClass = modelResourcePermissionDefinition.getModelClass();

		_modelName = modelClass.getName();
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, primaryKey, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelName, primaryKey, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, model, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelName,
				_modelResourcePermissionDefinition.getPrimaryKey(model),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			_modelName, primaryKey, actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(
				permissionChecker,
				_modelResourcePermissionDefinition.getModel(primaryKey),
				actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		Map<Object, Object> permissionChecksMap =
			permissionChecker.getPermissionChecksMap();

		PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
			_modelName, _modelResourcePermissionDefinition.getPrimaryKey(model),
			actionId);

		Boolean contains = (Boolean)permissionChecksMap.get(permissionCacheKey);

		if (contains == null) {
			contains = _contains(permissionChecker, model, actionId);

			permissionChecksMap.put(permissionCacheKey, contains);
		}

		return contains;
	}

	@Override
	public String getModelName() {
		return _modelName;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _modelResourcePermissionDefinition.
			getPortletResourcePermission();
	}

	private boolean _contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		actionId = _modelResourcePermissionDefinition.mapActionId(actionId);

		for (ModelResourcePermissionLogic<T> modelResourcePermissionLogic :
				_modelResourcePermissionLogics) {

			Boolean contains = modelResourcePermissionLogic.contains(
				permissionChecker, _modelName, model, actionId);

			if (contains != null) {
				return contains;
			}
		}

		String primKey = String.valueOf(
			_modelResourcePermissionDefinition.getPrimaryKey(model));

		if (permissionChecker.hasOwnerPermission(
				model.getCompanyId(), _modelName, primKey, model.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			model.getGroupId(), _modelName, primKey, actionId);
	}

	private final String _modelName;
	private final ModelResourcePermissionDefinition<T>
		_modelResourcePermissionDefinition;
	private final List<ModelResourcePermissionLogic<T>>
		_modelResourcePermissionLogics;

}