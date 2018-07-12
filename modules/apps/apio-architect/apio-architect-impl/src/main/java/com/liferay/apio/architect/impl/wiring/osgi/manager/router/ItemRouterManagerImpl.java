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

package com.liferay.apio.architect.impl.wiring.osgi.manager.router;

import static com.liferay.apio.architect.impl.alias.ProvideFunction.curry;

import static org.slf4j.LoggerFactory.getLogger;

import com.liferay.apio.architect.impl.routes.ItemRoutesImpl.BuilderImpl;
import com.liferay.apio.architect.impl.wiring.osgi.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.cache.ManagerCache;
import com.liferay.apio.architect.impl.wiring.osgi.manager.provider.ProviderManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.uri.mapper.PathIdentifierMapperManager;
import com.liferay.apio.architect.router.ItemRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.ItemRoutes.Builder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;

/**
 * @author Alejandro Hernández
 */
@Component
public class ItemRouterManagerImpl
	extends ClassNameBaseManager<ItemRouter> implements ItemRouterManager {

	public ItemRouterManagerImpl() {
		super(ItemRouter.class, 2);
	}

	@Override
	public Map<String, ItemRoutes> getItemRoutes() {
		return ManagerCache.INSTANCE.getItemRoutesMap(this::_computeItemRoutes);
	}

	@Override
	public <T, S> Optional<ItemRoutes<T, S>> getItemRoutesOptional(
		String name) {

		return ManagerCache.INSTANCE.getItemRoutesOptional(
			name, this::_computeItemRoutes);
	}

	private void _computeItemRoutes() {
		forEachService(
			(className, itemRouter) -> {
				Optional<String> nameOptional = _nameManager.getNameOptional(
					className);

				if (!nameOptional.isPresent()) {
					_logger.warn(
						"Unable to find a Representable for class name {}",
						className);

					return;
				}

				String name = nameOptional.get();

				Set<String> neededProviders = new TreeSet<>();

				Builder builder = new BuilderImpl<>(
					name, curry(_providerManager::provideMandatory),
					neededProviders::add,
					_pathIdentifierMapperManager::mapToIdentifierOrFail);

				@SuppressWarnings("unchecked")
				ItemRoutes itemRoutes = itemRouter.itemRoutes(builder);

				List<String> missingProviders =
					_providerManager.getMissingProviders(neededProviders);

				if (!missingProviders.isEmpty()) {
					_logger.warn(
						"Missing providers for classes: {}", missingProviders);

					return;
				}

				boolean hasPathIdentifierMapper =
					_pathIdentifierMapperManager.hasPathIdentifierMapper(name);

				if (!hasPathIdentifierMapper) {
					_logger.warn(
						"Missing path identifier mapper for resource with " +
							"name {}",
						name);

					return;
				}

				ManagerCache.INSTANCE.putItemRoutes(name, itemRoutes);
			});
	}

	private Logger _logger = getLogger(getClass());

	@Reference
	private NameManager _nameManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

}