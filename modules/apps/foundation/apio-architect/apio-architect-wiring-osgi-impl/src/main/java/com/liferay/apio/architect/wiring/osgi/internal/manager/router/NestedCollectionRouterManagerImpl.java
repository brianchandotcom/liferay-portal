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

import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.PARENT_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.PARENT_MODEL_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.alias.ProvideFunction;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.internal.service.reference.mapper.CustomServiceReferenceMapper;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.NestedCollectionRouterManager;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.Optional;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class NestedCollectionRouterManagerImpl
	extends BaseManager<NestedCollectionRouter, NestedCollectionRoutes>
	implements NestedCollectionRouterManager {

	public NestedCollectionRouterManagerImpl() {
		super(NestedCollectionRouter.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<NestedCollectionRoutes<T>>
		getNestedCollectionRoutesOptional(String name, String nestedName) {

		Optional<Class<T>> nameOptional =
			_modelClassManager.getModelClassOptional(name);

		Optional<Class<T>> nestedNameOptional =
			_modelClassManager.getModelClassOptional(nestedName);

		return nameOptional.map(
			Class::getName
		).flatMap(
			parentClassName -> nestedNameOptional.map(
				Class::getName
			).map(
				modelClassName -> modelClassName + "-" + parentClassName
			).flatMap(
				this::getServiceOptional
			)
		).map(
			routes -> (NestedCollectionRoutes<T>)routes
		);
	}

	@Override
	protected void emit(
		ServiceReference<NestedCollectionRouter> serviceReference,
		Emitter<String> emitter) {

		Bundle bundle = FrameworkUtil.getBundle(
			NestedCollectionRouterManagerImpl.class);

		BundleContext bundleContext = bundle.getBundleContext();

		CustomServiceReferenceMapper<NestedCollectionRouter>
			customServiceReferenceMapper = new CustomServiceReferenceMapper<>(
				bundleContext, NestedCollectionRouter.class);

		NestedCollectionRouter nestedCollectionRouter =
			bundleContext.getService(serviceReference);

		Class<?> genericClass = getGenericClassFromPropertyOrElse(
			serviceReference, PARENT_MODEL_CLASS,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 1));

		customServiceReferenceMapper.map(
			serviceReference,
			key -> emitter.emit(key + "-" + genericClass.getName()));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected NestedCollectionRoutes map(
		NestedCollectionRouter nestedCollectionRouter,
		ServiceReference<NestedCollectionRouter> serviceReference,
		Class<?> modelClass) {

		Class<?> identifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, PARENT_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 2));

		ProvideFunction provideFunction =
			httpServletRequest -> clazz -> _providerManager.provideOptional(
				clazz, httpServletRequest);

		Builder builder = new Builder<>(
			modelClass, identifierClass, provideFunction);

		return nestedCollectionRouter.collectionRoutes(builder);
	}

	@Reference
	private ModelClassManager _modelClassManager;

	@Reference
	private ProviderManager _providerManager;

}