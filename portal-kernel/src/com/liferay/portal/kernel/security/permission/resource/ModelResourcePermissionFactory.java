/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.permission.resource.PermissionCacheKey;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionFactory {

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator) {

		return create(
			modelClass, primKeyToLongFunction, getModelUnsafeFunction,
			portletResourcePermission, modelResourcePermissionConfigurator,
			UnaryOperator.identity());
	}

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator,
		UnaryOperator<String> actionIdMapper) {

		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics =
			new ArrayList<>();

		ModelResourcePermission<T> modelResourcePermission =
			new DefaultModelResourcePermission<>(
				modelResourcePermissionLogics, modelClass,
				primKeyToLongFunction, getModelUnsafeFunction,
				portletResourcePermission, actionIdMapper);

		modelResourcePermissionConfigurator.
			configureModelResourcePermissionLogics(
				modelResourcePermission, modelResourcePermissionLogics::add);

		return modelResourcePermission;
	}

	@FunctionalInterface
	public interface ModelResourcePermissionConfigurator
		<T extends GroupedModel> {

		public void configureModelResourcePermissionLogics(
			ModelResourcePermission<T> modelResourcePermission,
			Consumer<ModelResourcePermissionLogic<T>> consumer);

	}

	private static class DefaultModelResourcePermission<T extends GroupedModel>
		implements ModelResourcePermission<T> {

		@Override
		public void check(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			if (!contains(permissionChecker, primaryKey, actionId)) {
				throw new PrincipalException.MustHavePermission(
					permissionChecker, _modelClass.getName(), primaryKey,
					actionId);
			}
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, T model, String actionId)
			throws PortalException {

			if (!contains(permissionChecker, model, actionId)) {
				throw new PrincipalException.MustHavePermission(
					permissionChecker, _modelClass.getName(),
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
				_modelClass.getName(), primaryKey, actionId);

			Boolean contains = (Boolean)permissionChecksMap.get(
				permissionCacheKey);

			if (contains == null) {
				contains = _contains(
					permissionChecker,
					_getModelUnsafeFunction.apply(primaryKey), actionId);

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
				_modelClass.getName(),
				_primKeyToLongFunction.applyAsLong(model), actionId);

			Boolean contains = (Boolean)permissionChecksMap.get(
				permissionCacheKey);

			if (contains == null) {
				contains = _contains(permissionChecker, model, actionId);

				permissionChecksMap.put(permissionCacheKey, contains);
			}

			return contains;
		}

		@Override
		public String getModelName() {
			return _modelClass.getName();
		}

		@Override
		public PortletResourcePermission getPortletResourcePermission() {
			return _portletResourcePermission;
		}

		private DefaultModelResourcePermission(
			List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics,
			Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
			UnsafeFunction<Long, T, ? extends PortalException>
				getModelUnsafeFunction,
			PortletResourcePermission portletResourcePermission,
			UnaryOperator<String> actionIdMapper) {

			_modelResourcePermissionLogics = modelResourcePermissionLogics;

			_modelClass = Objects.requireNonNull(modelClass);
			_primKeyToLongFunction = Objects.requireNonNull(
				primKeyToLongFunction);
			_getModelUnsafeFunction = Objects.requireNonNull(
				getModelUnsafeFunction);
			_portletResourcePermission = portletResourcePermission;
			_actionIdMapper = Objects.requireNonNull(actionIdMapper);
		}

		private boolean _contains(
				PermissionChecker permissionChecker, T model, String actionId)
			throws PortalException {

			actionId = _actionIdMapper.apply(actionId);

			for (ModelResourcePermissionLogic<T> modelResourcePermissionLogic :
					_modelResourcePermissionLogics) {

				Boolean contains = modelResourcePermissionLogic.contains(
					permissionChecker, _modelClass.getName(), model, actionId);

				if (contains != null) {
					return contains;
				}
			}

			String primKey = String.valueOf(
				_primKeyToLongFunction.applyAsLong(model));

			if (permissionChecker.hasOwnerPermission(
					model.getCompanyId(), _modelClass.getName(), primKey,
					model.getUserId(), actionId)) {

				return true;
			}

			return permissionChecker.hasPermission(
				model.getGroupId(), _modelClass.getName(), primKey, actionId);
		}

		private final UnaryOperator<String> _actionIdMapper;
		private final UnsafeFunction<Long, T, ? extends PortalException>
			_getModelUnsafeFunction;
		private final Class<T> _modelClass;
		private final List<ModelResourcePermissionLogic<T>>
			_modelResourcePermissionLogics;
		private final PortletResourcePermission _portletResourcePermission;
		private final ToLongFunction<T> _primKeyToLongFunction;

	}

}