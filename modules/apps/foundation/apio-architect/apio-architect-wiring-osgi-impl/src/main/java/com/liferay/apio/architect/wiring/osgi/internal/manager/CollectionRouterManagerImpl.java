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
import com.liferay.apio.architect.router.CollectionRouter;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.manager.CollectionRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.RepresentableManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CollectionRouterManagerImpl
	extends BaseManager<CollectionRouter> implements CollectionRouterManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<CollectionRoutes<T>> getCollectionRoutesOptional(
		String name) {

		Optional<Class<T>> optional =
			_representableManager.getModelClassOptional(name);

		return optional.map(
			Class::getName
		).map(
			_collectionRoutesMap::get
		).map(
			routes -> (CollectionRoutes<T>)routes
		);
	}

	@Override
	public List<String> getResourceNames() {
		Set<String> keys = _collectionRoutesMap.keySet();

		Stream<String> stream = keys.stream();

		return stream.map(
			className -> _representableManager.getNameOptional(className)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<CollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = addService(
			serviceReference, CollectionRouter.class);

		optional.ifPresent(this::_addRoutes);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<CollectionRouter> serviceReference) {

		Optional<Class<Object>> optional = removeService(
			serviceReference, CollectionRouter.class);

		optional.map(
			Class::getName
		).ifPresent(
			_collectionRoutesMap::remove
		);

		optional.filter(
			modelClass -> {
				Optional<CollectionRouter> collectionRouterOptional =
					getServiceOptional(modelClass);

				return collectionRouterOptional.isPresent();
			}
		).ifPresent(
			this::_addRoutes
		);
	}

	@SuppressWarnings("unchecked")
	private <T> void _addRoutes(Class<T> modelClass) {
		Optional<CollectionRouter> optional = getServiceOptional(modelClass);

		optional.map(
			collectionRouter -> (CollectionRouter<T>)collectionRouter
		).ifPresent(
			collectionRouter -> {
				RequestFunction<Function<Class<?>, Optional<?>>>
					provideClassFunction =
						httpServletRequest -> clazz ->
							_providerManager.provideOptional(
								clazz, httpServletRequest);

				Builder<T> builder = new Builder<>(
					modelClass, provideClassFunction);

				CollectionRoutes<T> collectionRoutes =
					collectionRouter.collectionRoutes(builder);

				_collectionRoutesMap.put(
					modelClass.getName(), collectionRoutes);
			}
		);
	}

	private final Map<String, CollectionRoutes<?>> _collectionRoutesMap =
		new ConcurrentHashMap<>();

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

}