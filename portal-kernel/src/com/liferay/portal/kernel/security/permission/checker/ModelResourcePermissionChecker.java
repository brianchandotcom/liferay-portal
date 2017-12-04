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

package com.liferay.portal.kernel.security.permission.checker;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.permission.checker.PermissionCacheKey;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.spring.osgi.OSGIBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionChecker<T extends GroupedModel>
	implements ModelResourcePermission<T>, OSGIBean {

	public static <M extends GroupedModel> ModelResourcePermissionChecker<M>
		create(
			String modelName, ToLongFunction<M> primKeyFunction,
			UnsafeFunction<Long, M, ? extends PortalException> getModelFunction,
			PortletResourcePermission portletResourcePermission,
			ModelResourcePermissionConfigurator<M>
				modelResourcePermissionConfigurator) {

		return create(
			modelName, primKeyFunction, getModelFunction,
			portletResourcePermission, modelResourcePermissionConfigurator,
			UnaryOperator.identity());
	}

	public static <M extends GroupedModel> ModelResourcePermissionChecker<M>
		create(
			String modelName, ToLongFunction<M> primKeyFunction,
			UnsafeFunction<Long, M, ? extends PortalException> getModelFunction,
			PortletResourcePermission portletResourcePermission,
			ModelResourcePermissionConfigurator<M>
				modelResourcePermissionConfigurator,
			UnaryOperator<String> actionIdMapper) {

		List<ModelResourcePermissionCheck<M>> modelResourcePermissionChecks =
			new ArrayList<>();

		ModelResourcePermissionChecker<M> modelResourcePermissionChecker =
			new ModelResourcePermissionChecker<>(
				modelName, primKeyFunction, getModelFunction,
				portletResourcePermission, modelResourcePermissionChecks,
				actionIdMapper);

		modelResourcePermissionConfigurator.configChecks(
			modelResourcePermissionChecker, modelResourcePermissionChecks::add);

		return modelResourcePermissionChecker;
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
				_primKeyFunction.applyAsLong(model), actionId);
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
				permissionChecker, _getModelFunction.apply(primaryKey),
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
			_modelName, _primKeyFunction.applyAsLong(model), actionId);

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

	@Override
	public Map<String, Object> getProperties() {
		return Collections.singletonMap("model.class.name", _modelName);
	}

	@Override
	public Collection<Class<?>> getServices() {
		return Collections.singletonList(ModelResourcePermission.class);
	}

	@FunctionalInterface
	public interface ModelResourcePermissionConfigurator
		<M extends GroupedModel> {

		public void configChecks(
			ModelResourcePermission<M> modelResourcePermission,
			Consumer<ModelResourcePermissionCheck<M>> checksCollector);

	}

	private ModelResourcePermissionChecker(
		String modelName, ToLongFunction<T> primKeyFunction,
		UnsafeFunction<Long, T, ? extends PortalException> getModelFunction,
		PortletResourcePermission portletResourcePermission,
		List<ModelResourcePermissionCheck<T>> modelResourcePermissionChecks,
		UnaryOperator<String> actionIdMapper) {

		_modelName = Objects.requireNonNull(modelName);
		_primKeyFunction = Objects.requireNonNull(primKeyFunction);
		_getModelFunction = Objects.requireNonNull(getModelFunction);
		_portletResourcePermission = portletResourcePermission;
		_modelResourcePermissionChecks = Objects.requireNonNull(
			modelResourcePermissionChecks);
		_actionIdMapper = Objects.requireNonNull(actionIdMapper);
	}

	private boolean _contains(
			PermissionChecker permissionChecker, T model, String actionId)
		throws PortalException {

		actionId = _actionIdMapper.apply(actionId);

		for (ModelResourcePermissionCheck<T> modelResourcePermissionCheck :
				_modelResourcePermissionChecks) {

			Boolean contains = modelResourcePermissionCheck.contains(
				permissionChecker, _modelName, model, actionId);

			if (contains != null) {
				return contains;
			}
		}

		String primKey = String.valueOf(_primKeyFunction.applyAsLong(model));

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
		_getModelFunction;
	private final String _modelName;
	private final List<ModelResourcePermissionCheck<T>>
		_modelResourcePermissionChecks;
	private final PortletResourcePermission _portletResourcePermission;
	private final ToLongFunction<T> _primKeyFunction;

}