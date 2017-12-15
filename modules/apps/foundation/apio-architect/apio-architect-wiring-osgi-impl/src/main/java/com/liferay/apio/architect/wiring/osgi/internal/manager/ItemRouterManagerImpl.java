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
import com.liferay.apio.architect.router.ItemRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.ItemRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.manager.ItemRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;
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
public class ItemRouterManagerImpl
	extends BaseManager<ItemRouter> implements ItemRouterManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<ItemRoutes<T>> getItemRoutesOptional(String name) {
		Optional<Class<T>> optional =
			_representableManager.getModelClassOptional(name);

		return optional.map(
			Class::getName
		).map(
			_itemRoutesMap::get
		).map(
			routes -> (ItemRoutes<T>)routes
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<ItemRouter> serviceReference) {

		Optional<Class<Object>> optional = addService(
			serviceReference, ItemRouter.class);

		optional.ifPresent(this::_addRoutes);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<ItemRouter> serviceReference) {

		Optional<Class<Object>> optional = removeService(
			serviceReference, ItemRouter.class);

		optional.map(
			Class::getName
		).ifPresent(
			_itemRoutesMap::remove
		);

		optional.filter(
			modelClass -> {
				Optional<ItemRouter> itemRouterOptional = getServiceOptional(
					modelClass);

				return itemRouterOptional.isPresent();
			}
		).ifPresent(
			this::_addRoutes
		);
	}

	@SuppressWarnings("unchecked")
	private <T, U extends Identifier> void _addRoutes(Class<T> modelClass) {
		Optional<ItemRouter> optional = getServiceOptional(modelClass);

		optional.map(
			itemRouter -> (ItemRouter<T, U>)itemRouter
		).ifPresent(
			itemRouter -> {
				RequestFunction<Function<Class<?>, Optional<?>>>
					provideClassFunction =
						httpServletRequest -> clazz ->
							_providerManager.provideOptional(
								clazz, httpServletRequest);

				Class<U> identifierClass = ManagerUtil.getTypeParamOrFail(
					itemRouter, ItemRouter.class, 1);

				Builder<T, U> builder = new Builder<>(
					modelClass, identifierClass, provideClassFunction,
					() -> _pathIdentifierMapperManager::map);

				ItemRoutes<T> itemRoutes = itemRouter.itemRoutes(builder);

				_itemRoutesMap.put(modelClass.getName(), itemRoutes);
			}
		);
	}

	private final Map<String, ItemRoutes<?>> _itemRoutesMap =
		new ConcurrentHashMap<>();

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

}