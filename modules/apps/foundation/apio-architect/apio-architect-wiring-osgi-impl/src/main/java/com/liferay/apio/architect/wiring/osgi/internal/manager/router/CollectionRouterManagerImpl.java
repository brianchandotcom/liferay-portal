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

package com.liferay.apio.architect.wiring.osgi.internal.manager.router;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.router.CollectionRouter;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.CollectionRouterManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
	extends BaseManager<CollectionRouter, CollectionRoutes>
	implements CollectionRouterManager {

	public CollectionRouterManagerImpl() {
		super(CollectionRouter.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<CollectionRoutes<T>> getCollectionRoutesOptional(
		String name) {

		Optional<Class<T>> optional = _modelClassManager.getModelClassOptional(
			name);

		return optional.map(
			Class::getName
		).flatMap(
			this::getServiceOptional
		).map(
			routes -> (CollectionRoutes<T>)routes
		);
	}

	@Override
	public List<String> getResourceNames() {
		Set<String> keys = getServiceTrackerMap().keySet();

		Stream<String> stream = keys.stream();

		return stream.map(
			className -> _nameManager.getNameOptional(className)
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected CollectionRoutes map(
		CollectionRouter collectionRouter,
		ServiceReference<CollectionRouter> serviceReference,
		Class<?> modelClass) {

		ProvideFunction provideFunction =
			httpServletRequest -> clazz -> _providerManager.provideOptional(
				clazz, httpServletRequest);

		Builder builder = new Builder<>(modelClass, provideFunction);

		return collectionRouter.collectionRoutes(builder);
	}

	@Reference
	private ModelClassManager _modelClassManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private ProviderManager _providerManager;

}