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

import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ReusableNestedCollectionRouterManager;

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
public class ReusableNestedCollectionRouterManagerImpl
	extends ClassNameBaseManager<ReusableNestedCollectionRouter>
	implements ReusableNestedCollectionRouterManager {

	public ReusableNestedCollectionRouterManagerImpl() {
		super(ReusableNestedCollectionRouter.class, 2);
	}

	@Override
	public <T, S> Optional<NestedCollectionRoutes<T, S>>
		getNestedCollectionRoutesOptional(String name) {

		return INSTANCE.getReusableNestedCollectionRoutesOptional(
			name, this::_computeNestedCollectionRoutes);
	}

	private void _computeNestedCollectionRoutes() {
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

				ReusableNestedCollectionRouter<Object, Object, ?>
					reusableNestedCollectionRouter = unsafeCast(
						serviceTrackerMap.getService(className));

				Set<String> neededProviders = new TreeSet<>();

				Builder<Object, Object> builder = new Builder<>(
					"r", name, curry(_providerManager::provideMandatory),
					neededProviders::add);

				NestedCollectionRoutes<Object, Object> nestedCollectionRoutes =
					reusableNestedCollectionRouter.collectionRoutes(builder);

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

				INSTANCE.putReusableNestedCollectionRoutes(
					name, nestedCollectionRoutes);
			});
	}

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private ApioLogger _apioLogger;

	@Reference
	private ItemRouterManager _itemRouterManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private ProviderManager _providerManager;

}