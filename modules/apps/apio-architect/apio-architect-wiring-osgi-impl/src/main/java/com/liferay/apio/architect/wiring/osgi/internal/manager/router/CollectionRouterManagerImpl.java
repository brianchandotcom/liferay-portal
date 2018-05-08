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

import static com.liferay.apio.architect.alias.ProvideFunction.curry;
import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.CollectionRouter;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.CollectionRoutes.Builder;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.url.ServerURL;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.CollectionRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CollectionRouterManagerImpl
	extends ClassNameBaseManager<CollectionRouter>
	implements CollectionRouterManager {

	public CollectionRouterManagerImpl() {
		super(CollectionRouter.class, 1);
	}

	@Override
	public <T> Optional<CollectionRoutes<T>> getCollectionRoutesOptional(
		String name) {

		return INSTANCE.getCollectionRoutesOptional(
			name, this::_computeCollectionRoutes);
	}

	@Override
	public List<String> getResourceNames() {
		return INSTANCE.getRootResourceNames(this::_computeCollectionRoutes);
	}

	private void _computeCollectionRoutes() {
		List<String> missingMandatoryProviders =
			_providerManager.getMissingProviders(_mandatoryClassNames);

		if (!missingMandatoryProviders.isEmpty()) {
			if (_apioLogger != null) {
				_apioLogger.warning(
					"Missing providers for mandatory classes: " +
						missingMandatoryProviders);
			}

			return;
		}

		Stream<String> stream = getKeyStream();

		stream.forEach(
			className -> {
				Optional<String> nameOptional = _nameManager.getNameOptional(
					className);

				if (!nameOptional.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Unable to find a Representable for class name " +
								className);
					}

					return;
				}

				String name = nameOptional.get();

				CollectionRouter<Object, ?> collectionRouter = unsafeCast(
					serviceTrackerMap.getService(className));

				Set<String> neededProviders = new TreeSet<>();

				Builder<Object> builder = new Builder<>(
					name, curry(_providerManager::provideMandatory),
					neededProviders::add);

				CollectionRoutes<Object> collectionRoutes =
					collectionRouter.collectionRoutes(builder);

				List<String> missingProviders =
					_providerManager.getMissingProviders(neededProviders);

				if (!missingProviders.isEmpty()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Missing providers for classes: " +
								missingProviders);
					}

					return;
				}

				Optional<ItemRoutes<Object, Object>> optional =
					_itemRouterManager.getItemRoutesOptional(name);

				if (!optional.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Missing item router for resource with name " +
								name);
					}

					return;
				}

				INSTANCE.putRootResourceName(name);
				INSTANCE.putCollectionRoutes(name, collectionRoutes);
			});
	}

	private static final List<String> _mandatoryClassNames = Arrays.asList(
		Credentials.class.getName(), ServerURL.class.getName(),
		Pagination.class.getName());

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private ApioLogger _apioLogger;

	@Reference
	private ItemRouterManager _itemRouterManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private ProviderManager _providerManager;

}