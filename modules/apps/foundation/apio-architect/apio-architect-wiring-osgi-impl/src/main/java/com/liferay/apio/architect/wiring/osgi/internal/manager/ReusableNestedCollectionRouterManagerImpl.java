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
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.RepresentableManager;
import com.liferay.apio.architect.wiring.osgi.manager.ReusableNestedCollectionRouterManager;

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
public class ReusableNestedCollectionRouterManagerImpl
	extends BaseManager<ReusableNestedCollectionRouter>
	implements ReusableNestedCollectionRouterManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<NestedCollectionRoutes<T>>
		getNestedCollectionRoutesOptional(String name) {

		Optional<Class<T>> optional =
			_representableManager.getModelClassOptional(name);

		return optional.map(
			Class::getName
		).map(
			_routesMap::get
		).map(
			routes -> (NestedCollectionRoutes<T>)routes
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<ReusableNestedCollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = addService(
			serviceReference, ReusableNestedCollectionRouter.class);

		optional.ifPresent(this::_addRoutes);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<ReusableNestedCollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = removeService(
			serviceReference, ReusableNestedCollectionRouter.class);

		optional.map(
			Class::getName
		).ifPresent(
			_routesMap::remove
		);

		optional.filter(
			modelClass -> {
				Optional<ReusableNestedCollectionRouter>
					nestedCollectionRouterOptional = getServiceOptional(
						modelClass);

				return nestedCollectionRouterOptional.isPresent();
			}
		).ifPresent(
			this::_addRoutes
		);
	}

	@SuppressWarnings("unchecked")
	private <T, U extends Identifier> void _addRoutes(Class<T> modelClass) {
		Optional<ReusableNestedCollectionRouter> optional = getServiceOptional(
			modelClass);

		optional.map(
			nestedCollectionRouter ->
				(ReusableNestedCollectionRouter<T, U>)nestedCollectionRouter
		).ifPresent(
			nestedCollectionRouter -> {
				Class<U> identifierClass = ManagerUtil.getTypeParamOrFail(
					nestedCollectionRouter,
					ReusableNestedCollectionRouter.class, 1);

				RequestFunction<Function<Class<?>, Optional<?>>>
					provideClassFunction =
						httpServletRequest -> clazz ->
							_providerManager.provideOptional(
								clazz, httpServletRequest);

				Builder<T, U> builder = new Builder<>(
					modelClass, identifierClass, provideClassFunction);

				NestedCollectionRoutes<T> routes =
					nestedCollectionRouter.collectionRoutes(builder);

				_routesMap.put(modelClass.getName(), routes);
			}
		);
	}

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

	private final Map<String, NestedCollectionRoutes<?>> _routesMap =
		new ConcurrentHashMap<>();

}