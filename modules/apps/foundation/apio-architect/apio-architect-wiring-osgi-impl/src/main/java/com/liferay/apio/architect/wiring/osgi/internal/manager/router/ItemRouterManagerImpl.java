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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.ITEM_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.error.ApioDeveloperError.MustHavePathIdentifierMapper;
import com.liferay.apio.architect.router.ItemRouter;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.ItemRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ItemRouterManagerImpl
	extends BaseManager<ItemRouter, ItemRoutes> implements ItemRouterManager {

	public ItemRouterManagerImpl() {
		super(ItemRouter.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<ItemRoutes<T>> getItemRoutesOptional(String name) {
		Optional<Class<T>> optional = _modelClassManager.getModelClassOptional(
			name);

		return optional.map(
			Class::getName
		).flatMap(
			this::getServiceOptional
		).map(
			routes -> (ItemRoutes<T>)routes
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected ItemRoutes map(
		ItemRouter itemRouter, ServiceReference<ItemRouter> serviceReference,
		Class<?> modelClass) {

		ProvideFunction provideFunction =
			httpServletRequest -> clazz -> _providerManager.provideOptional(
				clazz, httpServletRequest);

		Class<?> identifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, ITEM_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(itemRouter, ItemRouter.class, 1));

		Builder builder = new Builder<>(
			modelClass, provideFunction,
			path -> {
				Optional<?> optional = _pathIdentifierMapperManager.map(
					identifierClass, path);

				return optional.orElseThrow(
					() -> new MustHavePathIdentifierMapper(identifierClass));
			});

		return itemRouter.itemRoutes(builder);
	}

	@Reference
	private ModelClassManager _modelClassManager;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

}