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
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.PARENT_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getNameOrFail;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes.Builder;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.internal.service.reference.mapper.CustomServiceReferenceMapper;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.NestedCollectionRouterManager;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.Collections;
import java.util.List;
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
	public <T, S> Optional<NestedCollectionRoutes<T, S>>
		getNestedCollectionRoutesOptional(String name, String nestedName) {

		Optional<Class<Identifier>> optional1 =
			_identifierClassManager.getIdentifierClassOptional(name);

		Optional<Class<Identifier>> optional2 =
			_identifierClassManager.getIdentifierClassOptional(nestedName);

		return optional1.map(
			Class::getName
		).flatMap(
			className1 -> optional2.map(
				Class::getName
			).map(
				className2 -> className1 + "-" + className2
			).flatMap(
				this::getServiceOptional
			)
		).map(
			Unsafe::unsafeCast
		);
	}

	@Override
	public List<Operation> getOperations(String name, String nestedName) {
		Optional<NestedCollectionRoutes<Object, Identifier>> optional =
			getNestedCollectionRoutesOptional(name, nestedName);

		return optional.map(
			NestedCollectionRoutes::getOperations
		).orElseGet(
			Collections::emptyList
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
				bundleContext, NestedCollectionRouter.class, 1);

		NestedCollectionRouter nestedCollectionRouter =
			bundleContext.getService(serviceReference);

		Class<?> clazz = getGenericClassFromPropertyOrElse(
			serviceReference, PARENT_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 3));

		customServiceReferenceMapper.map(
			serviceReference, key -> emitter.emit(clazz.getName() + "-" + key));
	}

	@Override
	protected NestedCollectionRoutes map(
		NestedCollectionRouter nestedCollectionRouter,
		ServiceReference<NestedCollectionRouter> serviceReference,
		Class<?> clazz) {

		Class<?> parentIdentifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, PARENT_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(
				nestedCollectionRouter, NestedCollectionRouter.class, 3));

		String name = getNameOrFail(parentIdentifierClass, _nameManager);

		String nestedName = getNameOrFail(clazz, _nameManager);

		return _getNestedCollectionRoutes(
			unsafeCast(nestedCollectionRouter), name, nestedName);
	}

	private <T, S, U extends Identifier<S>> NestedCollectionRoutes<T, S>
		_getNestedCollectionRoutes(
			NestedCollectionRouter<T, ?, S, U> nestedCollectionRouter,
			String name, String nestedName) {

		Builder<T, S> builder = new Builder<>(
			name, nestedName, curry(_providerManager::provideOptional));

		return nestedCollectionRouter.collectionRoutes(builder);
	}

	@Reference
	private IdentifierClassManager _identifierClassManager;

	@Reference
	private NameManager _nameManager;

	@Reference
	private ProviderManager _providerManager;

}