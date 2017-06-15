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

package com.liferay.vulcan.wiring.osgi;

import com.liferay.vulcan.representor.Resource;
import com.liferay.vulcan.representor.Routes;
import com.liferay.vulcan.wiring.osgi.internal.RepresentorBuilderImpl;
import com.liferay.vulcan.wiring.osgi.internal.RoutesBuilderImpl;
import com.liferay.vulcan.wiring.osgi.internal.ServiceReferenceServiceTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = ResourceManager.class)
public class ResourceManager {

	public <T> List<RelatedModel<T, ?>> getEmbeddedRelatedModels(
		Class<T> modelClass) {

		return (List)_embeddedRelatedModels.get(modelClass.getName());
	}

	public <T> Map<String, Function<T, Object>> getFieldFunctions(
		Class<T> modelClass) {

		return (Map)_fieldFunctions.get(modelClass.getName());
	}

	public <T> String getIdentifier(Class<T> modelClass, T model) {
		Function<T, String> identifierFunction =
			(Function<T, String>)_identifierFunctions.get(modelClass.getName());

		return identifierFunction.apply(model);
	}

	public <T> List<RelatedModel<T, ?>> getLinkedRelatedModels(
		Class<T> modelClass) {

		return (List)_linkedRelatedModels.get(modelClass.getName());
	}

	public <T> Map<String, String> getLinks(Class<T> modelClass) {
		return _links.get(modelClass.getName());
	}

	public <T> Optional<Resource<T>> getResourceOptional(Class<T> modelClass) {
		Optional<TreeSet<ServiceReferenceServiceTuple
			<Resource<?>>>> optional = Optional.ofNullable(
				_resources.get(modelClass.getName()));

		return optional.filter(
			serviceReferenceServiceTuples ->
				!serviceReferenceServiceTuples.isEmpty()
		).map(
			TreeSet::first
		).map(
			serviceReferenceServiceTuple ->
				(Resource<T>)
					serviceReferenceServiceTuple.getService()
		);
	}

	public <T> Routes<T> getRoutes(Class<T> modelClass) {
		return (Routes)_routes.get(modelClass.getName());
	}

	public <T> List<String> getTypes(Class<T> modelClass) {
		return _types.get(modelClass.getName());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected <T> void setServiceReference(
		ServiceReference<Resource<T>> serviceReference) {

		Resource<T> resource = _bundleContext.getService(serviceReference);

		Class<T> modelClass = GenericUtil.getGenericClass(
			resource, Resource.class);

		_addResource(serviceReference, resource, modelClass);

		_addModelClassMaps(modelClass);
	}

	protected <T> void unsetServiceReference(
		ServiceReference<Resource<T>> serviceReference) {

		Resource<T> resource = _bundleContext.getService(serviceReference);

		Class<T> modelClass = GenericUtil.getGenericClass(
			resource, Resource.class);

		_removeResource(resource, modelClass);

		_removeModelClassMaps(modelClass);

		Optional<Resource<T>> optional = getResourceOptional(modelClass);

		optional.ifPresent(firstResource -> _addModelClassMaps(modelClass));
	}

	private <T> void _addModelClassMaps(Class<T> modelClass) {
		Optional<Resource<T>> optional = getResourceOptional(modelClass);

		Map<String, Function<?, Object>> fieldFunctions = new HashMap<>();

		_fieldFunctions.put(modelClass.getName(), fieldFunctions);

		List<RelatedModel<?, ?>> embeddedRelatedModels = new ArrayList<>();

		_embeddedRelatedModels.put(modelClass.getName(), embeddedRelatedModels);

		List<RelatedModel<?, ?>> linkedRelatedModels = new ArrayList<>();

		_linkedRelatedModels.put(modelClass.getName(), linkedRelatedModels);

		Map<String, String> links = new HashMap<>();

		_links.put(modelClass.getName(), links);

		List<String> types = new ArrayList<>();

		_types.put(modelClass.getName(), types);

		optional.ifPresent(
			resource -> {
				resource.buildRepresentor(
					new RepresentorBuilderImpl<>(
						modelClass, _identifierFunctions, fieldFunctions,
						embeddedRelatedModels, linkedRelatedModels, links,
						types));

				Routes<T> routes = resource.routes(new RoutesBuilderImpl<>());

				_routes.put(modelClass.getName(), routes);
			});
	}

	private <T> void _addResource(
		ServiceReference<Resource<T>> serviceReference, Resource<T> resource,
		Class<T> modelClass) {

		_resources.computeIfAbsent(
			modelClass.getName(), name -> new TreeSet<>());

		ServiceReferenceServiceTuple<Resource<?>> serviceReferenceServiceTuple =
			(ServiceReferenceServiceTuple)new ServiceReferenceServiceTuple<>(
				serviceReference, resource);

		TreeSet<ServiceReferenceServiceTuple<Resource<?>>>
			serviceReferenceServiceTuples = _resources.get(
				modelClass.getName());

		serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);
	}

	private <T> void _removeModelClassMaps(Class<T> modelClass) {
		_embeddedRelatedModels.remove(modelClass.getName());
		_fieldFunctions.remove(modelClass.getName());
		_identifierFunctions.remove(modelClass.getName());
		_linkedRelatedModels.remove(modelClass.getName());
		_links.remove(modelClass.getName());
		_types.remove(modelClass.getName());
	}

	private <T> void _removeResource(Resource resource, Class<T> modelClass) {
		TreeSet<ServiceReferenceServiceTuple<Resource<?>>>
			serviceReferenceServiceTuples = _resources.get(
				modelClass.getName());

		serviceReferenceServiceTuples.removeIf(
			resourceTuple -> {
				if (resourceTuple.getService() == resource) {
					return true;
				}

				return false;
			});
	}

	private final BundleContext _bundleContext = FrameworkUtil.getBundle(
		ResourceManager.class).getBundleContext();
	private final Map<String, List<RelatedModel<?, ?>>> _embeddedRelatedModels =
		new ConcurrentHashMap<>();
	private final Map<String, Map<String, Function<?, Object>>>
		_fieldFunctions = new ConcurrentHashMap<>();
	private final Map<String, Function<?, String>> _identifierFunctions =
		new ConcurrentHashMap<>();
	private final Map<String, List<RelatedModel<?, ?>>> _linkedRelatedModels =
		new ConcurrentHashMap<>();
	private final Map<String, Map<String, String>> _links =
		new ConcurrentHashMap<>();
	private final Map<String,
		TreeSet<ServiceReferenceServiceTuple<Resource<?>>>> _resources =
			new ConcurrentHashMap<>();
	private final Map<String, Routes<?>> _routes = new ConcurrentHashMap<>();
	private final Map<String, List<String>> _types = new ConcurrentHashMap<>();

}