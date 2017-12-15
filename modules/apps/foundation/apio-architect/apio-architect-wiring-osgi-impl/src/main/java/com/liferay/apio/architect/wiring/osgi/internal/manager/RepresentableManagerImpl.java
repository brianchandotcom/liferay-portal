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

import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.wiring.osgi.manager.RepresentableManager;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class RepresentableManagerImpl
	extends BaseManager<Representable> implements RepresentableManager {

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<Class<T>> getModelClassOptional(String name) {
		Optional<? extends Class<?>> optional = Optional.ofNullable(
			_classes.get(name));

		return optional.map(clazz -> (Class<T>)clazz);
	}

	@Override
	public Optional<String> getNameOptional(String className) {
		Optional<Representable> optional = getServiceOptional(className);

		return optional.map(Representable::getName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, U extends Identifier> Optional<Representor<T, U>>
		getRepresentorOptional(Class<T> modelClass) {

		Optional<? extends Representor<?, ?>> optional = Optional.ofNullable(
			_representorMap.get(modelClass.getName()));

		return optional.map(representor -> (Representor<T, U>)representor);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<Representable> serviceReference) {

		Optional<Class<Object>> optional = addService(
			serviceReference, Representable.class);

		optional.ifPresent(this::_addModelClassMaps);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<Representable> serviceReference) {

		Optional<Class<Object>> optional = removeService(
			serviceReference, Representable.class);

		optional.ifPresent(this::_removeModelClassMaps);

		optional.filter(
			modelClass -> {
				Optional<Representable> representableOptional =
					getServiceOptional(modelClass);

				return representableOptional.isPresent();
			}
		).ifPresent(
			this::_addModelClassMaps
		);
	}

	@SuppressWarnings("unchecked")
	private <T, U extends Identifier> void _addModelClassMaps(
		Class<T> modelClass) {

		String className = modelClass.getName();

		Optional<Representable> optional = getServiceOptional(className);

		optional.map(
			representable -> (Representable<T, U>)representable
		).ifPresent(
			representable -> {
				String name = representable.getName();

				_classes.put(name, modelClass);

				Class<U> identifierClass = _getIdentifierClass(representable);

				Supplier<List<RelatedCollection<T, ?>>>
					relatedCollectionSupplier =
						() -> (List)_relatedCollections.get(className);

				Representor<T, U> representor = representable.representor(
					new Representor.Builder<>(
						identifierClass,
						_addRelatedCollectionTriConsumer(modelClass),
						relatedCollectionSupplier));

				_representorMap.put(className, representor);
			}
		);
	}

	private <T> TriConsumer<String, Class<?>, Function<Object, Identifier>>
		_addRelatedCollectionTriConsumer(Class<T> relatedModelClass) {

		return (key, modelClass, identifierFunction) -> {
			List<RelatedCollection<?, ?>> relatedCollections =
				_relatedCollections.computeIfAbsent(
					modelClass.getName(), className -> new ArrayList<>());

			relatedCollections.add(
				new RelatedCollection<>(
					key, relatedModelClass, identifierFunction));
		};
	}

	private <T, U extends Identifier> Class<U> _getIdentifierClass(
		Representable<T, U> representable) {

		Class<? extends Representable> resourceClass = representable.getClass();

		Try<Class<U>> classTry = GenericUtil.getGenericTypeArgumentTry(
			resourceClass, Representable.class, 1);

		return classTry.orElseThrow(
			() -> new ApioDeveloperError.MustHaveValidGenericType(
				resourceClass));
	}

	private <T> void _removeModelClassMaps(Class<T> modelClass) {
		Collection<Class<?>> classes = _classes.values();

		classes.removeIf(next -> next.equals(modelClass));

		_relatedCollections.forEach(
			(className, relatedCollections) -> relatedCollections.removeIf(
				relatedCollection ->
					relatedCollection.getModelClass().equals(modelClass)));

		_representorMap.remove(modelClass.getName());
	}

	private final Map<String, Class<?>> _classes = new ConcurrentHashMap<>();
	private final Map<String, List<RelatedCollection<?, ?>>>
		_relatedCollections = new ConcurrentHashMap<>();
	private final Map<String, Representor<?, ?>> _representorMap =
		new ConcurrentHashMap<>();

}