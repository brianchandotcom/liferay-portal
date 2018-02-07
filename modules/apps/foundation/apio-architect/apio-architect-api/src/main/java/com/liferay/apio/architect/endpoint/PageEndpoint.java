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

package com.liferay.apio.architect.endpoint;

import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notAllowed;
import static com.liferay.apio.architect.endpoint.ExceptionSupplierUtil.notFound;
import static com.liferay.apio.architect.uri.Path.path;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.noContent;

import com.liferay.apio.architect.alias.routes.NestedCreateItemFunction;
import com.liferay.apio.architect.function.ThrowableFunction;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.operation.Method;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Declares the endpoint for the page operations.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PageEndpoint<T> {

	public PageEndpoint(
		HttpServletRequest httpServletRequest,
		Function<String, Optional<Class<Identifier>>> identifierClassFunction,
		BiFunction<String, String, Try<SingleModel<T>>> singleModelFunction,
		ThrowableFunction<String, Optional<CollectionRoutes<T>>>
			collectionRoutesFunction,
		Function<String, Optional<Representor<Object, Object>>>
			representorFunction,
		ThrowableFunction<String, Optional<ItemRoutes<T>>> itemRoutesFunction,
		BiFunction<String, String, Optional<NestedCollectionRoutes<T, Object>>>
			nestedCollectionRoutesFunction) {

		_httpServletRequest = httpServletRequest;
		_identifierClassFunction = identifierClassFunction;
		_singleModelFunction = singleModelFunction;
		_collectionRoutesFunction = collectionRoutesFunction;
		_representorFunction = representorFunction;
		_itemRoutesFunction = itemRoutesFunction;
		_nestedCollectionRoutesFunction = nestedCollectionRoutesFunction;
	}

	/**
	 * Adds a new {@link SingleModel} to the resource specified by {@code name}.
	 * This occurs via a POST request to the resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @param  body the request's body
	 * @return the new single model, or an exception if an error occurred
	 */
	@Consumes(APPLICATION_JSON)
	@Path("{name}")
	@POST
	public Try<SingleModel<T>> addCollectionItem(
		@PathParam("name") String name, Map<String, Object> body) {

		Try<String> stringTry = Try.success(name);

		return stringTry.mapOptional(
			_collectionRoutesFunction, notFound(name)
		).mapOptional(
			CollectionRoutes::getCreateItemFunctionOptional,
			notAllowed(Method.POST, name)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(body)
		);
	}

	/**
	 * Adds a new {@link SingleModel} to the nested resource specified. This
	 * occurs via a POST request to the nested resource.
	 *
	 * @param  name the parent resource's name, extracted from the URL
	 * @param  id the parent resource's ID
	 * @param  nestedName the nested resource's name, extracted from the URL
	 * @param  body the request's body
	 * @return the new single model, or an exception if an error occurred
	 */
	@Consumes(APPLICATION_JSON)
	@Path("{name}/{id}/{nestedName}")
	@POST
	public Try<SingleModel<T>> addNestedCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("nestedName") String nestedName, Map<String, Object> body) {

		Try<NestedCollectionRoutes<T, Object>> nestedCollectionRoutesTry =
			Try.fromOptional(
				() -> _nestedCollectionRoutesFunction.apply(name, nestedName),
				notFound(name, nestedName));

		return nestedCollectionRoutesTry.mapOptional(
			NestedCollectionRoutes::getNestedCreateItemFunctionOptional
		).flatMap(
			(NestedCreateItemFunction<T, Object> nestedCreateItemFunction) -> {
				Try<SingleModel<T>> singleModelTry =
					getCollectionItemSingleModelTry(name, id);

				return singleModelTry.mapOptional(
					_getIdentifierFunction(name, nestedName)
				).map(
					identifier -> nestedCreateItemFunction.apply(
						_httpServletRequest
					).apply(
						identifier
					).apply(
						body
					)
				);
			}
		).mapFailMatching(
			NoSuchElementException.class,
			notAllowed(Method.POST, name, id, nestedName)
		);
	}

	/**
	 * Deletes the collection item specified by {@code name}.
	 *
	 * @param  name the name of the resource to delete, extracted from the URL
	 * @param  id the ID of the resource to delete
	 * @return the operation's {@code javax.ws.rs.core.Response}, or an
	 *         exception if an error occurred
	 */
	@DELETE
	@Path("{name}/{id}")
	public Response deleteCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id) {

		Try<String> stringTry = Try.success(name);

		stringTry.mapOptional(
			_itemRoutesFunction, notFound(name)
		).mapOptional(
			ItemRoutes::getDeleteConsumerOptional,
			notAllowed(Method.DELETE, name, id)
		).getUnchecked(
		).apply(
			_httpServletRequest
		).accept(
			path(name, id)
		);

		return noContent().build();
	}

	/**
	 * Returns the {@link SingleModel} for the specified resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @param  id the resource's ID
	 * @return the {@link SingleModel} for the specified resource, or an
	 *         exception if an error occurred
	 */
	@GET
	@Path("{name}/{id}")
	public Try<SingleModel<T>> getCollectionItemSingleModelTry(
		@PathParam("name") String name, @PathParam("id") String id) {

		return _singleModelFunction.apply(name, id);
	}

	/**
	 * Returns the collection {@link Page} for the specified resource.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @return the collection {@link Page} for the specified resource, or an
	 *         exception if an error occurred
	 */
	@GET
	@Path("{name}")
	public Try<Page<T>> getCollectionPageTry(@PathParam("name") String name) {
		Try<String> stringTry = Try.success(name);

		return stringTry.mapOptional(
			_collectionRoutesFunction
		).mapOptional(
			CollectionRoutes::getGetPageFunctionOptional, notFound(name)
		).map(
			function -> function.apply(_httpServletRequest)
		);
	}

	/**
	 * Returns a nested collection {@link Page} for the specified resource.
	 *
	 * @param  name the parent resource's name, extracted from the URL
	 * @param  id the parent resource's ID
	 * @param  nestedName the nested resource's name
	 * @return the nested collection {@link Page} for the specified resource, or
	 *         an exception if an error occurred
	 */
	@GET
	@Path("{name}/{id}/{nestedName}")
	public Try<Page<T>> getNestedCollectionPageTry(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("nestedName") String nestedName) {

		Try<NestedCollectionRoutes<T, Object>> nestedCollectionRoutesTry =
			Try.fromOptional(
				() -> _nestedCollectionRoutesFunction.apply(name, nestedName),
				notFound(name, id, nestedName));

		return nestedCollectionRoutesTry.map(
			NestedCollectionRoutes::getNestedGetPageFunctionOptional
		).map(
			Optional::get
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(path(name, id))
		).flatMap(
			pageFunction -> {
				Try<SingleModel<T>> parentSingleModelTry =
					getCollectionItemSingleModelTry(name, id);

				return parentSingleModelTry.map(
					_getIdentifierFunction(name, nestedName)
				).map(
					optional -> optional.map(pageFunction)
				);
			}
		).map(
			Optional::get
		).mapFailMatching(
			NoSuchElementException.class, notFound(name, id, nestedName)
		);
	}

	/**
	 * Updates the specified collection item.
	 *
	 * @param  name the resource's name, extracted from the URL
	 * @param  id the resource's ID
	 * @param  body the request's body
	 * @return the updated single model, or an exception if an error occurred
	 */
	@Consumes(APPLICATION_JSON)
	@Path("{name}/{id}")
	@PUT
	public Try<SingleModel<T>> updateCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id,
		Map<String, Object> body) {

		Try<String> stringTry = Try.success(name);

		return stringTry.mapOptional(
			_itemRoutesFunction, notFound(name, id)
		).mapOptional(
			ItemRoutes::getUpdateItemFunctionOptional,
			notAllowed(Method.PUT, name, id)
		).map(
			function -> function.apply(_httpServletRequest)
		).map(
			function -> function.apply(path(name, id))
		).map(
			function -> function.apply(body)
		);
	}

	private Predicate<RelatedCollection<?>>
		_getFilterRelatedCollectionPredicate(String nestedName) {

		return relatedCollection -> {
			Class<?> relatedIdentifierClass =
				relatedCollection.getIdentifierClass();

			String className = relatedIdentifierClass.getName();

			Optional<Class<Identifier>> optional =
				_identifierClassFunction.apply(nestedName);

			return optional.map(
				Class::getName
			).map(
				className::equals
			).orElse(
				false
			);
		};
	}

	private ThrowableFunction<SingleModel<T>, Optional<Object>>
		_getIdentifierFunction(String name, String nestedName) {

		return parentSingleModel -> _representorFunction.apply(
			name
		).map(
			Representor::getRelatedCollections
		).filter(
			stream -> stream.anyMatch(
				_getFilterRelatedCollectionPredicate(nestedName))
		).flatMap(
			__ -> _representorFunction.apply(name)
		).map(
			representor -> representor.getIdentifier(
				parentSingleModel.getModel())
		);
	}

	private final ThrowableFunction<String, Optional<CollectionRoutes<T>>>
		_collectionRoutesFunction;
	private final HttpServletRequest _httpServletRequest;
	private final Function<String, Optional<Class<Identifier>>>
		_identifierClassFunction;
	private final ThrowableFunction<String, Optional<ItemRoutes<T>>>
		_itemRoutesFunction;
	private final BiFunction<String, String,
		Optional<NestedCollectionRoutes<T, Object>>>
			_nestedCollectionRoutesFunction;
	private final Function<String, Optional<Representor<Object, Object>>>
		_representorFunction;
	private final BiFunction<String, String, Try<SingleModel<T>>>
		_singleModelFunction;

}