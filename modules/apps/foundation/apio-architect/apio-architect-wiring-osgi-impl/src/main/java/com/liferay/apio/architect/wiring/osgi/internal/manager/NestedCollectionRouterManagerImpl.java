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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.manager.NestedCollectionRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.RepresentableManager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class NestedCollectionRouterManagerImpl
	extends BaseManager<NestedCollectionRouter>
	implements NestedCollectionRouterManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<NestedCollectionRoutes<T>>
		getNestedCollectionRoutesOptional(String name, String nestedName) {

		Optional<Class<T>> nameOptional =
			_representableManager.getModelClassOptional(name);

		Optional<Class<T>> nestedNameOptional =
			_representableManager.getModelClassOptional(nestedName);

		return nameOptional.map(
			Class::getName
		).map(
			_routesMap::get
		).flatMap(
			routesMap -> nestedNameOptional.map(
				Class::getName
			).map(
				routesMap::get
			)
		).map(
			routes -> (NestedCollectionRoutes<T>)routes
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<NestedCollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = addService(
			serviceReference, NestedCollectionRouter.class);

		optional.ifPresent(this::_addRoutes);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<NestedCollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = removeService(
			serviceReference, NestedCollectionRouter.class);

		optional.map(
			Class::getName
		).ifPresent(
			_routesMap::remove
		);

		optional.filter(
			modelClass -> {
				Optional<NestedCollectionRouter>
					nestedCollectionRouterOptional = getServiceOptional(
						modelClass);

				return nestedCollectionRouterOptional.isPresent();
			}
		).ifPresent(
			this::_addRoutes
		);
	}

	@SuppressWarnings("unchecked")
	private <T, U, V extends Identifier> void _addRoutes(Class<T> modelClass) {
		Optional<NestedCollectionRouter> optional = getServiceOptional(
			modelClass);

		optional.map(
			nestedCollectionRouter ->
				(NestedCollectionRouter<T, U, V>)nestedCollectionRouter
		).ifPresent(
			nestedCollectionRouter -> {
				Class<U> parentClass = ManagerUtil.getTypeParamOrFail(
					nestedCollectionRouter, NestedCollectionRouter.class, 1);

				Class<V> identifierClass = ManagerUtil.getTypeParamOrFail(
					nestedCollectionRouter, NestedCollectionRouter.class, 2);

				RequestFunction<Function<Class<?>, Optional<?>>>
					provideClassFunction =
						httpServletRequest -> clazz ->
							_providerManager.provideOptional(
								clazz, httpServletRequest);

				Builder<T, V> builder = new Builder<>(
					modelClass, identifierClass, provideClassFunction);

				NestedCollectionRoutes<T> routes =
					nestedCollectionRouter.collectionRoutes(builder);

				String className = parentClass.getName();

				Map<String, NestedCollectionRoutes<?>>
					nestedCollectionRoutesMap = _routesMap.computeIfAbsent(
						className, __ -> new ConcurrentHashMap<>());

				nestedCollectionRoutesMap.put(modelClass.getName(), routes);

				_routesMap.put(className, nestedCollectionRoutesMap);
			}
		);
	}

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

	private final Map<String, Map<String, NestedCollectionRoutes<?>>>
		_routesMap = new ConcurrentHashMap<>();

}