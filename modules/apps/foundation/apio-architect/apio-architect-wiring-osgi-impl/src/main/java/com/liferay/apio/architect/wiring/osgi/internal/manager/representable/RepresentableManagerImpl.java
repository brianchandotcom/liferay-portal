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

package com.liferay.apio.architect.wiring.osgi.internal.manager.representable;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.representor.Representor.Builder;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class RepresentableManagerImpl
	extends BaseManager<Representable, Representor>
	implements RepresentableManager {

	public RepresentableManagerImpl() {
		super(Representable.class);
	}

	@Override
	public <T, U> Optional<Representor<T, U>> getRepresentorOptional(
		String name) {

		Optional<Class<Identifier>> optional =
			_identifierClassManager.getIdentifierClassOptional(name);

		return optional.flatMap(
			this::getServiceOptional
		).map(
			Unsafe::unsafeCast
		);
	}

	@Override
	protected Representor map(
		Representable representable,
		ServiceReference<Representable> serviceReference, Class<?> clazz) {

		return _getRepresentor(unsafeCast(representable), unsafeCast(clazz));
	}

	@Override
	protected void onRemovedService(
		ServiceReference<Representable> serviceReference,
		Representor representor) {

		Class<?> identifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(representor, Representor.class, 2));

		_relatedCollections.forEach(
			(className, relatedCollections) -> relatedCollections.removeIf(
				relatedCollection -> identifierClass.equals(
					relatedCollection.getIdentifierClass())));
	}

	private <T, S, U extends Identifier<S>> Representor<T, S> _getRepresentor(
		Representable<T, S, U> representable, Class<U> clazz) {

		Supplier<List<RelatedCollection<?>>> relatedCollectionSupplier =
			() -> _relatedCollections.get(clazz.getName());

		BiConsumer<Class<?>, RelatedCollection<?>> biConsumer =
			(collectionIdentifierClass, relatedCollection) -> {
				List<RelatedCollection<?>> relatedCollections =
					_relatedCollections.computeIfAbsent(
						collectionIdentifierClass.getName(),
						className -> new ArrayList<>());

				relatedCollections.add(relatedCollection);
			};

		Builder<T, S> builder = new Builder<>(
			clazz, biConsumer, relatedCollectionSupplier);

		return representable.representor(builder);
	}

	@Reference
	private IdentifierClassManager _identifierClassManager;

	private final Map<String, List<RelatedCollection<?>>> _relatedCollections =
		new ConcurrentHashMap<>();

}