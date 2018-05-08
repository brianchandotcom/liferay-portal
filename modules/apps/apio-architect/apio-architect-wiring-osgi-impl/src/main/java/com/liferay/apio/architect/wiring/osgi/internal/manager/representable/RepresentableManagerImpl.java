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
import static com.liferay.apio.architect.wiring.osgi.internal.manager.TypeArgumentProperties.KEY_PRINCIPAL_TYPE_ARGUMENT;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.cache.ManagerCache.INSTANCE;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.representor.Representor.Builder;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.IdentifierClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.NameManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper.Emitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class RepresentableManagerImpl
	extends BaseManager<Representable, Class<Identifier>>
	implements NameManager, IdentifierClassManager, RepresentableManager {

	public RepresentableManagerImpl() {
		super(Representable.class);
	}

	@Override
	public <T extends Identifier> Optional<Class<T>> getIdentifierClassOptional(
		String name) {

		return INSTANCE.getIdentifierClassOptional(
			name, this::_computeRepresentables);
	}

	@Override
	public Optional<String> getNameOptional(String className) {
		return INSTANCE.getNameOptional(
			className, this::_computeRepresentables);
	}

	@Override
	public <T, U> Optional<Representor<T, U>> getRepresentorOptional(
		String name) {

		return INSTANCE.getRepresentorOptional(
			name, this::_computeRepresentables);
	}

	@Override
	protected void emit(
		ServiceReference<Representable> serviceReference,
		Emitter<Class<Identifier>> emitter) {

		Representable representable = bundleContext.getService(
			serviceReference);

		Class<Identifier> genericClass = getGenericClassFromPropertyOrElse(
			serviceReference, KEY_PRINCIPAL_TYPE_ARGUMENT,
			() -> getTypeParamOrFail(representable, Representable.class, 2));

		emitter.emit(genericClass);
	}

	private void _computeRepresentables() {
		Map<String, List<RelatedCollection<?>>> relatedCollections =
			new HashMap<>();

		Stream<Class<Identifier>> keyStream = getKeyStream();

		keyStream.forEach(
			clazz -> {
				Representable representable = serviceTrackerMap.getService(
					clazz);

				String name = representable.getName();

				Optional<Map<String, String>> optional =
					INSTANCE.getNamesOptional();

				Optional<String> classNameOptional = optional.map(
					Map::entrySet
				).map(
					Collection::stream
				).orElseGet(
					Stream::empty
				).filter(
					entry -> Objects.equals(entry.getValue(), name)
				).map(
					Entry::getKey
				).findFirst();

				if (classNameOptional.isPresent()) {
					if (_apioLogger != null) {
						_apioLogger.warning(
							_getDuplicateErrorMessage(
								clazz, name, classNameOptional.get()));
					}

					return;
				}

				INSTANCE.putName(clazz.getName(), name);
				INSTANCE.putIdentifierClass(name, clazz);
				INSTANCE.putRepresentor(
					name,
					_getRepresentor(
						unsafeCast(representable), unsafeCast(clazz),
						relatedCollections));
			});
	}

	private String _getDuplicateErrorMessage(
		Class<Identifier> clazz, String name, String className) {

		StringBuilder stringBuilder = new StringBuilder();

		return stringBuilder.append(
			"Representable registered under "
		).append(
			clazz.getName()
		).append(
			" has name "
		).append(
			name
		).append(
			", but it's already in use by Representable "
		).append(
			"registered under "
		).append(
			className
		).toString();
	}

	private <T, S, U extends Identifier<S>> Representor<T, S> _getRepresentor(
		Representable<T, S, U> representable, Class<U> clazz,
		Map<String, List<RelatedCollection<?>>> relatedCollections) {

		Supplier<List<RelatedCollection<?>>> relatedCollectionSupplier =
			() -> relatedCollections.get(clazz.getName());

		BiConsumer<Class<?>, RelatedCollection<?>> biConsumer =
			(identifierClass, relatedCollection) -> {
				List<RelatedCollection<?>> list =
					relatedCollections.computeIfAbsent(
						identifierClass.getName(), __ -> new ArrayList<>());

				list.add(relatedCollection);
			};

		Builder<T, S> builder = new Builder<>(
			clazz, biConsumer, relatedCollectionSupplier);

		return representable.representor(builder);
	}

	@Reference(cardinality = OPTIONAL, policyOption = GREEDY)
	private ApioLogger _apioLogger;

}