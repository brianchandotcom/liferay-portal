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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * @author Preston Crary
 */
public class DefaultModelResourcePermission<T extends GroupedModel>
	implements ModelResourcePermission<T> {

	public DefaultModelResourcePermission(
		String modelName, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics,
		UnaryOperator<String> actionIdMapper) {

		_modelName = Objects.requireNonNull(modelName);
		_primKeyToLongFunction = Objects.requireNonNull(primKeyToLongFunction);
		_getModelUnsafeFunction = Objects.requireNonNull(
			getModelUnsafeFunction);
		_portletResourcePermission = portletResourcePermission;
		_modelResourcePermissionLogics = Objects.requireNonNull(
			modelResourcePermissionLogics);
		_actionIdMapper = Objects.requireNonNull(actionIdMapper);
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
				_primKeyToLongFunction.applyAsLong(model), actionId);
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
				permissionChecker, _getModelUnsafeFunction.apply(primaryKey),
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
			_modelName, _primKeyToLongFunction.applyAsLong(model), actionId);

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
		return _portletResourcePermission;
	}

	private boolean _contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		actionId = _actionIdMapper.apply(actionId);

		for (ModelResourcePermissionLogic<T> modelResourcePermissionLogic :
				_modelResourcePermissionLogics) {

			Boolean contains = modelResourcePermissionLogic.contains(
				permissionChecker, _modelName, model, actionId);

			if (contains != null) {
				return contains;
			}
		}

		String primKey = String.valueOf(
			_primKeyToLongFunction.applyAsLong(model));

		if (permissionChecker.hasOwnerPermission(
				model.getCompanyId(), _modelName, primKey, model.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			model.getGroupId(), _modelName, primKey, actionId);
	}

	private final UnaryOperator<String> _actionIdMapper;
	private final UnsafeFunction<Long, T, ? extends PortalException>
		_getModelUnsafeFunction;
	private final String _modelName;
	private final List<ModelResourcePermissionLogic<T>>
		_modelResourcePermissionLogics;
	private final PortletResourcePermission _portletResourcePermission;
	private final ToLongFunction<T> _primKeyToLongFunction;

}