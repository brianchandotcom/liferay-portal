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
import com.liferay.apio.architect.alias.RequestFunction;
import com.liferay.apio.architect.documentation.APIDescription;
import com.liferay.apio.architect.documentation.APITitle;
import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.endpoint.RootEndpoint;
import com.liferay.apio.architect.error.ApioDeveloperError;
import com.liferay.apio.architect.function.ThrowableFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.url.ServerURL;
import com.liferay.apio.architect.wiring.osgi.manager.ProviderManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.ModelClassManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.CollectionRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ItemRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.NestedCollectionRouterManager;
import com.liferay.apio.architect.wiring.osgi.manager.router.ReusableNestedCollectionRouterManager;

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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class RootEndpointImpl implements RootEndpoint {

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
	public <T> Try<SingleModel<T>> addCollectionItem(
		String name, Map<String, Object> body) {

		Try<CollectionRoutes<T>> collectionRoutesTry = _getCollectionRoutesTry(
			name);

		return collectionRoutesTry.map(
			CollectionRoutes::getCreateItemFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			_getNotAllowedExceptionSupplier("POST", name)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(body)
		);
	}

	@Override
	public <T> Try<SingleModel<T>> addNestedCollectionItem(
		String name, String id, String nestedName, Map<String, Object> body) {

		Try<NestedCollectionRoutes<T>> nestedCollectionRoutesTry =
			_getNestedCollectionRoutesTry(name, nestedName);

		return nestedCollectionRoutesTry.map(
			NestedCollectionRoutes::getNestedCreateItemFunctionOptional
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
		Try<ItemRoutes<Object>> itemRoutesTry = _getItemRoutesTry(name);

		itemRoutesTry.map(
			ItemRoutes::getDeleteConsumerOptional
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
			_modelClassManager.getModelClassOptional(name);

		Optional<BinaryFunction<Object>> binaryFunctionOptional =
			modelClassOptional.flatMap(
				_representableManager::getRepresentorOptional
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

		Try<ItemRoutes<T>> itemRoutesTry = _getItemRoutesTry(name);

		return itemRoutesTry.map(
			ItemRoutes::getItemFunctionOptional
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
		Try<CollectionRoutes<T>> collectionRoutesTry = _getCollectionRoutesTry(
			name);

		return collectionRoutesTry.map(
			CollectionRoutes::getGetPageFunctionOptional
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, _getNotFoundExceptionSupplier(name)
		).map(
			function -> function.apply(_httpServletRequest)
		);
	}

	@Override
	public Documentation getDocumentation() {
		return _documentation;
	}

	@Override
	public String getHome() {
		List<String> resourceNames =
			_collectionRouterManager.getResourceNames();

		Optional<ServerURL> optional = _providerManager.provideOptional(
			ServerURL.class, _httpServletRequest);

		ServerURL serverURL = optional.orElseThrow(
			() -> new ApioDeveloperError.MustHaveProvider(ServerURL.class));

		JsonObject resourcesJsonObject = new JsonObject();

		resourceNames.forEach(
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

		Try<NestedCollectionRoutes<T>> nestedCollectionRoutesTry =
			_getNestedCollectionRoutesTry(name, nestedName);

		return nestedCollectionRoutesTry.map(
			NestedCollectionRoutes::getNestedGetPageFunctionOptional
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

		Try<ItemRoutes<T>> itemRoutesTry = _getItemRoutesTry(name);

		return itemRoutesTry.map(
			ItemRoutes::getUpdateItemFunctionOptional
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

	private <T> Try<CollectionRoutes<T>> _getCollectionRoutesTry(String name) {
		Try<Optional<CollectionRoutes<T>>> optionalTry = Try.success(
			_collectionRouterManager.getCollectionRoutesOptional(name));

		return optionalTry.map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path " + name)
		);
	}

	private <T> Predicate<RelatedCollection<T, ?>>
		_getFilterRelatedCollectionPredicate(String nestedName) {

		return relatedCollection -> {
			Class<?> relatedModelClass = relatedCollection.getModelClass();

			String relatedClassName = relatedModelClass.getName();

			Optional<Class<Object>> optional =
				_modelClassManager.getModelClassOptional(nestedName);

			return optional.map(
				Class::getName
			).map(
				relatedClassName::equals
			).orElse(
				false
			);
		};
	}

	private <T> ThrowableFunction<SingleModel<T>, Optional<Object>>
		_getIdentifierFunction(String nestedName) {

		return parentSingleModel -> {
			Optional<Representor<T, Object>> optional =
				_representableManager.getRepresentorOptional(
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

	private <T> Try<ItemRoutes<T>> _getItemRoutesTry(String name) {
		Try<Optional<ItemRoutes<T>>> optionalTry = Try.success(
			_itemRouterManager.getItemRoutesOptional(name));

		return optionalTry.map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path " + name)
		);
	}

	private <T, S> ThrowableFunction<Function<Object, Page<S>>,
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

	private <T> Try<NestedCollectionRoutes<T>> _getNestedCollectionRoutesTry(
		String name, String nestedName) {

		Try<Optional<NestedCollectionRoutes<T>>> optionalTry = Try.success(
			_nestedCollectionRouterManager.getNestedCollectionRoutesOptional(
				name, nestedName));

		return optionalTry.map(
			Optional::get
		).recoverWith(
			__ -> _getReusableNestedCollectionRoutesTry(nestedName)
		).mapFailMatching(
			NoSuchElementException.class,
			() -> new NotFoundException("No resource found for path " + name)
		);
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

	private <T> Try<NestedCollectionRoutes<T>>
		_getReusableNestedCollectionRoutesTry(String name) {

		Try<Optional<NestedCollectionRoutes<T>>> optionalTry = Try.success(
			_reusableNestedCollectionRouterManager.
				getNestedCollectionRoutesOptional(name));

		return optionalTry.map(Optional::get);
	}

	@Reference
	private CollectionRouterManager _collectionRouterManager;

	private Documentation _documentation;

	@Context
	private HttpServletRequest _httpServletRequest;

	@Reference
	private ItemRouterManager _itemRouterManager;

	@Reference
	private ModelClassManager _modelClassManager;

	@Reference
	private NestedCollectionRouterManager _nestedCollectionRouterManager;

	@Reference
	private ProviderManager _providerManager;

	@Reference
	private RepresentableManager _representableManager;

	@Reference
	private ReusableNestedCollectionRouterManager
		_reusableNestedCollectionRouterManager;

}