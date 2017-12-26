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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.router.ReusableNestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ReusableNestedCollectionRouterManager;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ReusableNestedCollectionRouterManagerImpl
	extends BaseManager<ReusableNestedCollectionRouter, NestedCollectionRoutes>
	implements ReusableNestedCollectionRouterManager {

	public ReusableNestedCollectionRouterManagerImpl() {
		super(ReusableNestedCollectionRouter.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<NestedCollectionRoutes<T>>
		getNestedCollectionRoutesOptional(String name) {

		Optional<Class<T>> optional = _modelClassManager.getModelClassOptional(
			name);

		return optional.map(
			Class::getName
		).flatMap(
			this::getServiceOptional
		).map(
			routes -> (NestedCollectionRoutes<T>)routes
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected NestedCollectionRoutes map(
		ReusableNestedCollectionRouter reusableNestedCollectionRouter,
		ServiceReference<ReusableNestedCollectionRouter> serviceReference,
		Class<?> modelClass) {

		Class<?> identifierClass = getTypeParamOrFail(
			reusableNestedCollectionRouter,
			ReusableNestedCollectionRouter.class, 1);

		ProvideFunction provideFunction =
			httpServletRequest -> clazz -> _providerManager.provideOptional(
				clazz, httpServletRequest);

		Builder builder = new Builder<>(
			modelClass, identifierClass, provideFunction);

		return reusableNestedCollectionRouter.collectionRoutes(builder);
	}

	@Reference
	private ModelClassManager _modelClassManager;

	@Reference
	private ProviderManager _providerManager;

}