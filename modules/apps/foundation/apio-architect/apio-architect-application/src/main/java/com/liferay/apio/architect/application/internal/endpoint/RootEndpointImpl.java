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

package com.liferay.apio.architect.application.internal.endpoint;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.alias.BinaryFunction;
import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.endpoint.RootEndpoint;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.function.ThrowableFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.identifier.RootIdentifier;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.Routes;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.url.ServerURL;
import com.liferay.apio.architect.wiring.osgi.manager.CollectionResourceManager;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;

import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class RootEndpointImpl implements RootEndpoint {

	@Override
	public <T> Try<SingleModel<T>> addCollectionItem(
		String name, Map<String, Object> body) {

		Try<Routes<T>> routesTry = _getRoutesTry(name);

		return routesTry.map(
			Routes::getCreateItemFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotAllowedExceptionSupplier("POST", name)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(new RootIdentifier() {})
		).map(
			function -> function.apply(body)
		);
	}

	@Override
	public <T> Try<SingleModel<T>> addNestedCollectionItem(
		String name, String id, String nestedName, Map<String, Object> body) {

		Try<Routes<T>> routesTry = _getRoutesTry(nestedName);

		return routesTry.map(
			Routes::getCreateItemFunctionOptional
		).map(
			Optional::get
		).map(
			function -> function.apply(_httpServletRequest)
		).flatMap(
			postFunction -> {
				Try<SingleModel<T>> parentSingleModelTry =
					getCollectionItemSingleModelTry(name, id);

				return parentSingleModelTry.map(
					_getIdentifierFunction(nestedName)
				).map(
					optional -> optional.map(postFunction)
				);
			}
		).map(
			Optional::get
		).map(
			function -> function.apply(body)
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotAllowedExceptionSupplier(
				"POST", String.join("/", name, id, nestedName))
		);
	}

	@Override
	public Response deleteCollectionItem(String name, String id) {
		Try<Routes<Object>> routesTry = _getRoutesTry(name);

		routesTry.map(
			Routes::getDeleteConsumerOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotAllowedExceptionSupplier("DELETE", name + "/" + id)
		).getUnchecked(
		).apply(
			_httpServletRequest
		).accept(
			new Path(name, id)
		);

		ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Try<InputStream> getCollectionItemInputStreamTry(
		String name, String id, String binaryId) {

		Optional<Class<Object>> modelClassOptional =
			_collectionResourceManager.getModelClassOptional(name);

		Optional<BinaryFunction<Object>> binaryFunctionOptional =
			modelClassOptional.flatMap(
				_collectionResourceManager::getRepresentorOptional
			).map(
				Representor::getBinaryFunctions
			).map(
				binaryFunctions -> binaryFunctions.get(binaryId)
			);

		Try<BinaryFunction<Object>> binaryFunctionTry = Try.fromFallible(
			binaryFunctionOptional::get);

		return binaryFunctionTry.mapFailMatching(
			NoSuchElementException.class,
			_getNotFoundExceptionSupplier(String.join("/", name, id, binaryId))
		).flatMap(
			binaryFunction -> _getInputStreamTry(name, id, binaryFunction)
		);
	}

	@Override
	public <T> Try<SingleModel<T>> getCollectionItemSingleModelTry(
		String name, String id) {

		Try<Routes<T>> routesTry = _getRoutesTry(name);

		return routesTry.map(
			Routes::getItemFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotFoundExceptionSupplier(name + "/" + id)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(new Path(name, id))
		);
	}

	@Override
	public <T> Try<Page<T>> getCollectionPageTry(String name) {
		Try<Routes<T>> routesTry = _getRoutesTry(name);

		return routesTry.map(
			Routes::getGetPageFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, _getNotFoundExceptionSupplier(name)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(new Path())
		).map(
			function -> function.apply(new RootIdentifier() {})
		);
	}

	@Override
	public Documentation getDocumentation() {
		return _collectionResourceManager.getDocumentation();
	}

	@Override
	public String getHome() {
		List<String> rootCollectionResourceNames =
			_collectionResourceManager.getRootCollectionResourceNames();

		Optional<ServerURL> optional = _providerManager.provideOptional(
			ServerURL.class, _httpServletRequest);

		ServerURL serverURL = optional.orElseThrow(
			() -> new ApioDeveloperError.MustHaveProvider(ServerURL.class));

		JsonObject resourcesJsonObject = new JsonObject();

		rootCollectionResourceNames.forEach(
			name -> {
				String url = serverURL.get() + "/p/" + name;

				JsonObject jsonObject = new JsonObject();

				jsonObject.addProperty("href", url);

				resourcesJsonObject.add(name, jsonObject);
			});

		JsonObject rootJsonObject = new JsonObject();

		rootJsonObject.add("resources", resourcesJsonObject);

		return rootJsonObject.toString();
	}

	@Override
	public <T> Try<Page<T>> getNestedCollectionPageTry(
		String name, String id, String nestedName) {

		Try<Routes<T>> routesTry = _getRoutesTry(nestedName);

		return routesTry.map(
			Routes::getGetPageFunctionOptional
		).map(
			Optional::get
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(new Path(name, id))
		).flatMap(
			_getNestedCollectionPageTryFunction(name, id, nestedName)
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotFoundExceptionSupplier(
				String.join("/", name, id, nestedName))
		);
	}

	@Override
	public <T> Try<SingleModel<T>> updateCollectionItem(
		String name, String id, Map<String, Object> body) {

		Try<Routes<T>> routesTry = _getRoutesTry(name);

		return routesTry.map(
			Routes::getUpdateItemFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotAllowedExceptionSupplier("PUT", name + "/" + id)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(new Path(name, id))
		).map(
			function -> function.apply(body)
		);
	}

	private <T> Predicate<RelatedCollection<T, ?>>
		_getFilterRelatedCollectionPredicate(String nestedName) {

		return relatedCollection -> {
			Class<?> relatedModelClass = relatedCollection.getModelClass();

			String relatedClassName = relatedModelClass.getName();

			Optional<Class<Object>> optional =
				_collectionResourceManager.getModelClassOptional(nestedName);

			return optional.map(
				Class::getName
			).map(
				relatedClassName::equals
			).orElse(
				false
			);
		};
	}

	private <T> ThrowableFunction<SingleModel<T>, Optional<Identifier>>
		_getIdentifierFunction(String nestedName) {

		return parentSingleModel -> {
			Optional<Representor<T, Identifier>> optional =
				_collectionResourceManager.getRepresentorOptional(
					parentSingleModel.getModelClass());

			return optional.map(
				Representor::getRelatedCollections
			).flatMap(
				(Stream<RelatedCollection<T, ?>> stream) -> stream.filter(
					_getFilterRelatedCollectionPredicate(nestedName)
				).findFirst(
				).map(
					RelatedCollection::getIdentifierFunction
				).map(
					function -> function.apply(parentSingleModel.getModel())
				)
			);
		};
	}

	private <T> Try<InputStream> _getInputStreamTry(
		String name, String id, BinaryFunction<T> binaryFunction) {

		Try<SingleModel<T>> singleModelTry = getCollectionItemSingleModelTry(
			name, id);

		return singleModelTry.map(
			SingleModel::getModel
		).map(
			binaryFunction::apply
		);
	}

	private <T, S> ThrowableFunction<Function<Identifier, Page<S>>,
		Try<Optional<Page<S>>>> _getNestedCollectionPageTryFunction(
			String name, String id, String nestedName) {

		return pageFunction -> {
			Try<SingleModel<T>> parentSingleModelTry =
				getCollectionItemSingleModelTry(name, id);

			return parentSingleModelTry.map(
				_getIdentifierFunction(nestedName)
			).map(
				optional -> optional.map(pageFunction)
			);
		};
	}

	private Supplier<NotAllowedException> _getNotAllowedExceptionSupplier(
		String method, String path) {

		return () -> new NotAllowedException(
			method + " method is not allowed for path " + path);
	}

	private Supplier<NotFoundException> _getNotFoundExceptionSupplier(
		String name) {

		return () -> new NotFoundException("No endpoint found at path " + name);
	}

	private <T> Try<Routes<T>> _getRoutesTry(String name) {
		Try<Optional<Routes<T>>> optionalTry = Try.success(
			_collectionResourceManager.getRoutesOptional(name));

		return optionalTry.map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path " + name)
		);
	}

	@Reference
	private CollectionResourceManager _collectionResourceManager;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ProviderManager _providerManager;

}