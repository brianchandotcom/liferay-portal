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

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.impl.routes.CollectionRoutesImpl.BuilderImpl;
import com.liferay.apio.architect.impl.url.ApplicationURL;
import com.liferay.apio.architect.impl.url.ServerURL;
import com.liferay.apio.architect.impl.wiring.osgi.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.cache.ManagerCache;
import com.liferay.apio.architect.impl.wiring.osgi.manager.provider.ProviderManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.impl.wiring.osgi.manager.uri.mapper.PathIdentifierMapperManager;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.CollectionRouter;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.routes.ItemRoutes;

import java.util.Arrays;
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
public class CollectionRouterManagerImpl
	extends ClassNameBaseManager<CollectionRouter>
	implements CollectionRouterManager {

	public CollectionRouterManagerImpl() {
		super(CollectionRouter.class, 2);
	}

	@Override
	public Map<String, CollectionRoutes> getCollectionRoutes() {
		return ManagerCache.INSTANCE.getCollectionRoutes(
			this::_computeCollectionRoutes);
	}

	@Override
	public <T, S> Optional<CollectionRoutes<T, S>> getCollectionRoutesOptional(
		String name) {

		return ManagerCache.INSTANCE.getCollectionRoutesOptional(
			name, this::_computeCollectionRoutes);
	}

	@Override
	public List<String> getResourceNames() {
		return ManagerCache.INSTANCE.getRootResourceNames(
			this::_computeCollectionRoutes);
	}

	private void _computeCollectionRoutes() {
		List<String> list = _providerManager.getMissingProviders(
			_mandatoryClassNames);

		if (!list.isEmpty()) {
			_logger.warn("Missing providers for mandatory classes: {}", list);

			return;
		}

		forEachService(
			(className, collectionRouter) -> {
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
				CollectionRoutes collectionRoutes =
					collectionRouter.collectionRoutes(builder);

				List<String> missingProviders =
					_providerManager.getMissingProviders(neededProviders);

				if (!missingProviders.isEmpty()) {
					_logger.warn(
						"Missing providers for classes: {}", missingProviders);

					return;
				}

				Optional<ItemRoutes<Object, Object>> optional =
					_itemRouterManager.getItemRoutesOptional(name);

				if (!optional.isPresent()) {
					_logger.warn(
						"Missing item router for resource with name {}", name);

					return;
				}

				ManagerCache.INSTANCE.putRootResourceName(name);
				ManagerCache.INSTANCE.putCollectionRoutes(
					name, collectionRoutes);
			});
	}

	private static final List<String> _mandatoryClassNames = Arrays.asList(
		ApplicationURL.class.getName(), Credentials.class.getName(),
		Pagination.class.getName(), ServerURL.class.getName());

	@Reference
	private ItemRouterManager _itemRouterManager;

	private Logger _logger = getLogger(getClass());

	@Reference
	private NameManager _nameManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

}