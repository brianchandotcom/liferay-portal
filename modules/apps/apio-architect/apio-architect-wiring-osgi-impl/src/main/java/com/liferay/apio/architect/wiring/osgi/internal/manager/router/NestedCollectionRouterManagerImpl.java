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
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_PARENT_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.ClassNameBaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.NestedCollectionRouterManager;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class NestedCollectionRouterManagerImpl
	extends ClassNameBaseManager<NestedCollectionRouter>
	implements NestedCollectionRouterManager {

	public NestedCollectionRouterManagerImpl() {
		super(NestedCollectionRouter.class, 1);
	}

	@Override
	public <T, S> Optional<NestedCollectionRoutes<T, S>>
		getNestedCollectionRoutesOptional(String name, String nestedName) {

		return INSTANCE.getNestedCollectionRoutesOptional(
			name, nestedName, this::_computeNestedCollectionRoutes);
	}

	protected void emit(
		ServiceReference<NestedCollectionRouter> serviceReference,
		Emitter<String> emitter) {

		NestedCollectionRouter nestedCollectionRouter =
			bundleContext.getService(serviceReference);

		Class<?> identifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, KEY_PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 1));

		Class<?> parentIdentifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, KEY_PARENT_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 3));

		emitter.emit(
			parentIdentifierClass.getName() + "-" + identifierClass.getName());
	}

	private void _computeNestedCollectionRoutes() {
		Stream<String> stream = getKeyStream();

		stream.forEach(
			key -> {
				String[] classNames = key.split("-");

				if (classNames.length != 2) {
					return;
				}

				String parentClassName = classNames[0];
				String nestedClassName = classNames[1];

				Optional<String> nameOptional = _nameManager.getNameOptional(
					parentClassName);

				if (!nameOptional.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Unable to find a Representable for parent class " +
								"name " + parentClassName);
					}

					return;
				}

				String name = nameOptional.get();

				Optional<String> nestedNameOptional =
					_nameManager.getNameOptional(nestedClassName);

				if (!nestedNameOptional.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Unable to find a Representable for nested class " +
								"name " + nestedClassName);
					}

					return;
				}

				String nestedName = nestedNameOptional.get();

				NestedCollectionRouter<Object, ?, Object, ?>
					nestedCollectionRouter = unsafeCast(
						serviceTrackerMap.getService(key));

				Set<String> neededProviders = new TreeSet<>();

				Builder<Object, Object> builder = new Builder<>(
					name, nestedName, curry(_providerManager::provideMandatory),
					neededProviders::add);

				NestedCollectionRoutes<Object, Object> nestedCollectionRoutes =
					nestedCollectionRouter.collectionRoutes(builder);

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

				Optional<ItemRoutes<Object, Object>> nestedItemRoutes =
					_itemRouterManager.getItemRoutesOptional(nestedName);

				if (!nestedItemRoutes.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Missing item router for resource with name " +
								nestedName);
					}

					return;
				}

				Optional<ItemRoutes<Object, Object>> parentItemRoutes =
					_itemRouterManager.getItemRoutesOptional(name);

				if (!parentItemRoutes.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							"Missing item router for resource with name " +
								name);
					}

					return;
				}

				INSTANCE.putNestedCollectionRoutes(
					name + "-" + nestedName, nestedCollectionRoutes);
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