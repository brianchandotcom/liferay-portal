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

package com.liferay.vulcan.wiring.osgi.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.consumer.TriConsumer;
import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.RelatedCollection;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.identifier.Identifier;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.wiring.osgi.internal.resource.builder.RepresentorBuilderImpl;
import com.liferay.vulcan.wiring.osgi.internal.resource.builder.RepresentorBuilderImpl.RepresentorImpl;
import com.liferay.vulcan.wiring.osgi.internal.resource.builder.RoutesBuilderImpl;
import com.liferay.vulcan.wiring.osgi.util.GenericUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to retrieve information provided by the different {@link
 * CollectionResource} instances, such as field functions, types, identifier
 * functions, and so on.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    CollectionResource
 * @review
 */
@Component(immediate = true, service = CollectionResourceManager.class)
public class CollectionResourceManager extends BaseManager<CollectionResource> {

	/**
	 * Returns the model class of a resource's name.
	 *
	 * @param  name name of the resource for the class.
	 * @return the class of a resource's name.
	 * @review
	 */
	public <T> Optional<Class<T>> getModelClassOptional(String name) {
		Optional<? extends Class<?>> optional = Optional.ofNullable(
			_classes.get(name));

		return optional.map(clazz -> (Class<T>)clazz);
	}

	/**
	 * Returns the name of a class names's resource.
	 *
	 * @param  className the class name of a {@link CollectionResource}
	 * @return the name of a class name's resource.
	 * @review
	 */
	public Optional<String> getNameOptional(String className) {
		Optional<CollectionResource> optional = _getCollectionResourceOptional(
			className);

		return optional.map(CollectionResource::getName);
	}

	/**
	 * Returns the representor of the model class, if present. Returns {@code
	 * Optional#empty()} if no representor can be found.
	 *
	 * @param  modelClass the model class of a {@link CollectionResource}.
	 * @return the representor of the model class, if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public <T, U extends Identifier> Optional<Representor<T, U>>
		getRepresentorOptional(Class<T> modelClass) {

		Optional<RepresentorImpl> optional = Optional.ofNullable(
			_representors.get(modelClass.getName()));

		return optional.map(representor -> (Representor<T, U>)representor);
	}

	/**
	 * Returns the routes of the model class for a certain name.
	 *
	 * @param  name the name of a {@link CollectionResource}.
	 * @param  httpServletRequest the actual request.
	 * @return the routes of the model class.
	 * @review
	 */
	public <T> Optional<Routes<T>> getRoutesOptional(
		String name, HttpServletRequest httpServletRequest) {

		Optional<Function<HttpServletRequest, Routes<?>>> optional =
			Optional.ofNullable(_routesFunctions.get(name));

		return optional.map(
			routesFunction -> routesFunction.apply(httpServletRequest)
		).map(
			routes -> (Routes<T>)routes
		);
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<CollectionResource> serviceReference) {

		Optional<Class<Object>> optional = addService(serviceReference);

		optional.ifPresent(this::_addModelClassMaps);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<CollectionResource> serviceReference) {

		Optional<Class<Object>> optional = removeService(serviceReference);

		optional.ifPresent(this::_removeModelClassMaps);

		optional.filter(
			modelClass -> {
				Optional<CollectionResource> collectionResourceOptional =
					_getCollectionResourceOptional(modelClass.getName());

				return collectionResourceOptional.isPresent();
			}
		).ifPresent(
			this::_addModelClassMaps
		);
	}

	private <T, U extends Identifier> void _addModelClassMaps(
		Class<T> modelClass) {

		Optional<CollectionResource> optional = _getCollectionResourceOptional(
			modelClass.getName());

		optional.map(
			collectionResource -> (CollectionResource<T, U>)collectionResource
		).ifPresent(
			collectionResource -> {
				_classes.put(collectionResource.getName(), modelClass);

				Class<U> identifierClass = _getIdentifierClass(
					collectionResource);

				Supplier<List<RelatedCollection<T, ?>>>
					relatedCollectionSupplier =
						() -> (List)_relatedCollections.get(
							modelClass.getName());

				RepresentorImpl representor =
					(RepresentorImpl)collectionResource.buildRepresentor(
						new RepresentorBuilderImpl<>(
							identifierClass,
							_addRelatedCollectionTriConsumer(modelClass),
							relatedCollectionSupplier));

				_representors.put(modelClass.getName(), representor);

				Function<HttpServletRequest, Routes<?>> routesFunction =
					_getRoutesFunction(
						modelClass, identifierClass, collectionResource);

				_routesFunctions.put(
					collectionResource.getName(), routesFunction);
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

	private Optional<CollectionResource> _getCollectionResourceOptional(
		String modelClassName) {

		return getServiceOptional(modelClassName);
	}

	private <T, U extends Identifier> Class<U> _getIdentifierClass(
		CollectionResource<T, U> collectionResource) {

		Class<? extends CollectionResource> resourceClass =
			collectionResource.getClass();

		Try<Class<U>> classTry = GenericUtil.getGenericTypeArgumentTry(
			resourceClass, 1);

		return classTry.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveValidGenericType(
				resourceClass));
	}

	private Function<Class<?>, Optional<?>> _getProvideClassFunction(
		HttpServletRequest httpServletRequest) {

		return clazz -> _providerManager.provide(clazz, httpServletRequest);
	}

	private <T, U extends Identifier> Function<HttpServletRequest, Routes<?>>
		_getRoutesFunction(
			Class<T> modelClass, Class<U> identifierClass,
			CollectionResource<T, U> collectionResource) {

		return httpServletRequest -> {
			RoutesBuilderImpl<T, U> routesBuilder = new RoutesBuilderImpl<>(
				modelClass, identifierClass,
				_getProvideClassFunction(httpServletRequest),
				_pathIdentifierMapperManager::map);

			return collectionResource.routes(routesBuilder);
		};
	}

	private <T> void _removeModelClassMaps(Class<T> modelClass) {
		Collection<Class<?>> classes = _classes.values();

		classes.removeIf(next -> next.equals(modelClass));

		_relatedCollections.forEach(
			(className, relatedCollections) -> relatedCollections.removeIf(
				relatedCollection ->
					relatedCollection.getModelClass().equals(modelClass)));
		_representors.remove(modelClass.getName());
	}

	private final Map<String, Class<?>> _classes = new ConcurrentHashMap<>();

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

	private final Map<String, List<RelatedCollection<?, ?>>>
		_relatedCollections = new ConcurrentHashMap<>();
	private final Map<String, RepresentorImpl> _representors =
		new ConcurrentHashMap<>();
	private final Map<String, Function<HttpServletRequest, Routes<?>>>
		_routesFunctions = new ConcurrentHashMap<>();

}