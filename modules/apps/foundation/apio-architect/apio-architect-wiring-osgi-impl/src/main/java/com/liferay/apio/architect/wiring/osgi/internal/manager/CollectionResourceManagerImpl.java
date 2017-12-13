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
import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.documentation.APIDescription;
import com.liferay.apio.architect.documentation.APITitle;
import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.resource.ScopedCollectionResource;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.wiring.osgi.manager.CollectionResourceManager;
import com.liferay.apio.architect.wiring.osgi.manager.PathIdentifierMapperManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class CollectionResourceManagerImpl
	extends BaseManager<CollectionResource>
	implements CollectionResourceManager {

	@Activate
	public void activate() {
		RequestFunction<Optional<APITitle>> apiTitleRequestFunction =
			httpServletRequest -> _providerManager.provideOptional(
				APITitle.class, httpServletRequest);

		RequestFunction<Optional<APIDescription>>
			apiDescriptionRequestFunction =
				httpServletRequest -> _providerManager.provideOptional(
					APIDescription.class, httpServletRequest);

		_documentation = new Documentation(
			apiTitleRequestFunction, apiDescriptionRequestFunction);
	}

	@Override
	public Documentation getDocumentation() {
		return _documentation;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<Class<T>> getModelClassOptional(String name) {
		Optional<? extends Class<?>> optional = Optional.ofNullable(
			_classes.get(name));

		return optional.map(clazz -> (Class<T>)clazz);
	}

	@Override
	public Optional<String> getNameOptional(String className) {
		Optional<CollectionResource> optional = _getCollectionResourceOptional(
			className);

		return optional.map(CollectionResource::getName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, U extends Identifier> Optional<Representor<T, U>>
		getRepresentorOptional(Class<T> modelClass) {

		Optional<? extends Representor<?, ?>> optional = Optional.ofNullable(
			_representorMap.get(modelClass.getName()));

		return optional.map(representor -> (Representor<T, U>)representor);
	}

	@Override
	public List<String> getRootCollectionResourceNames() {
		return _rootCollectionResourceNames;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<Routes<T>> getRoutesOptional(String name) {
		Optional<Class<T>> optional = getModelClassOptional(name);

		return optional.map(
			Class::getName
		).map(
			_routesMap::get
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

	@SuppressWarnings("unchecked")
	private <T, U extends Identifier> void _addModelClassMaps(
		Class<T> modelClass) {

		String className = modelClass.getName();

		Optional<CollectionResource> optional = _getCollectionResourceOptional(
			className);

		optional.map(
			collectionResource -> (CollectionResource<T, U>)collectionResource
		).ifPresent(
			collectionResource -> {
				String name = collectionResource.getName();

				_classes.put(name, modelClass);

				if (!(collectionResource instanceof ScopedCollectionResource)) {
					_rootCollectionResourceNames.add(name);
				}

				Class<U> identifierClass = _getIdentifierClass(
					collectionResource);

				Supplier<List<RelatedCollection<T, ?>>>
					relatedCollectionSupplier =
						() -> (List)_relatedCollections.get(className);

				Representor<T, U> representor = collectionResource.representor(
					new Representor.Builder<>(
						identifierClass,
						_addRelatedCollectionTriConsumer(modelClass),
						relatedCollectionSupplier));

				_representorMap.put(className, representor);

				RequestFunction<Function<Class<?>, Optional<?>>>
					provideClassFunction =
						httpServletRequest -> clazz ->
							_providerManager.provideOptional(
								clazz, httpServletRequest);

				Routes.Builder<T, U> builder = new Routes.Builder<>(
					modelClass, identifierClass, provideClassFunction,
					_pathIdentifierMapperManager::map);

				Routes<T> routes = collectionResource.routes(builder);

				_routesMap.put(className, routes);
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
			() -> new ApioDeveloperError.MustHaveValidGenericType(
				resourceClass));
	}

	private <T> void _removeModelClassMaps(Class<T> modelClass) {
		Collection<Class<?>> classes = _classes.values();

		classes.removeIf(next -> next.equals(modelClass));

		Optional<String> optional = getNameOptional(modelClass.getName());

		optional.ifPresent(_rootCollectionResourceNames::remove);

		_relatedCollections.forEach(
			(className, relatedCollections) -> relatedCollections.removeIf(
				relatedCollection ->
					relatedCollection.getModelClass().equals(modelClass)));

		_representorMap.remove(modelClass.getName());

		_routesMap.remove(modelClass.getName());
	}

	private final Map<String, Class<?>> _classes = new ConcurrentHashMap<>();
	private Documentation _documentation;

	@Reference
	private PathIdentifierMapperManager _pathIdentifierMapperManager;

	@Reference
	private ProviderManager _providerManager;

	private final Map<String, List<RelatedCollection<?, ?>>>
		_relatedCollections = new ConcurrentHashMap<>();
	private final Map<String, Representor<?, ?>> _representorMap =
		new ConcurrentHashMap<>();
	private final List<String> _rootCollectionResourceNames = new ArrayList<>();
	private final Map<String, Routes<?>> _routesMap = new ConcurrentHashMap<>();

}