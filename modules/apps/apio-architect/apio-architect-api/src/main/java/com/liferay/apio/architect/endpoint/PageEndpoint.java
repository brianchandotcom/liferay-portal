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

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.single.model.SingleModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Declares the endpoint for page operations.
 *
 * @author Alejandro Hernández
 */
public interface PageEndpoint<T> {

	/**
	 * Adds a new {@link SingleModel} to the resource specified by {@code name}.
	 * This occurs via a POST request to the resource.
	 *
	 * @param  body the request's body
	 * @return the new single model, or an exception if an error occurred
	 */
	@Consumes({APPLICATION_JSON, MULTIPART_FORM_DATA})
	@Path("/")
	@POST
	public Try<SingleModel<T>> addCollectionItem(Body body);

	/**
	 * Adds a new {@link SingleModel} to the nested resource specified. This
	 * occurs via a POST request to the nested resource.
	 *
	 * @param  id the parent resource's ID
	 * @param  nestedName the nested resource's name, extracted from the URL
	 * @param  body the request's body
	 * @return the new single model, or an exception if an error occurred
	 */
	@Consumes({APPLICATION_JSON, MULTIPART_FORM_DATA})
	@Path("{id}/{nestedName}")
	@POST
	public Try<SingleModel<T>> addNestedCollectionItem(
		@PathParam("id") String id, @PathParam("nestedName") String nestedName,
		Body body);

	/**
	 * Deletes the collection item specified by {@code name}.
	 *
	 * @param  id the ID of the resource to delete
	 * @return the operation's {@code javax.ws.rs.core.Response}, or an
	 *         exception if an error occurred
	 */
	@DELETE
	@Path("{id}")
	public Response deleteCollectionItem(@PathParam("id") String id)
		throws Exception;

	/**
	 * Returns the {@link SingleModel} for the specified resource.
	 *
	 * @param  id the resource's ID
	 * @return the {@link SingleModel} for the specified resource, or an
	 *         exception if an error occurred
	 */
	@GET
	@Path("{id}")
	public Try<SingleModel<T>> getCollectionItemSingleModelTry(
		@PathParam("id") String id);

	/**
	 * Returns the collection {@link Page} for the specified resource.
	 *
	 * @return the collection {@link Page} for the specified resource, or an
	 *         exception if an error occurred
	 */
	@GET
	@Path("/")
	public Try<Page<T>> getCollectionPageTry();

	/**
	 * Returns a nested collection {@link Page} for the specified resource.
	 *
	 * @param  id the parent resource's ID
	 * @param  nestedName the nested resource's name
	 * @return the nested collection {@link Page} for the specified resource, or
	 *         an exception if an error occurred
	 */
	@GET
	@Path("{id}/{nestedName}")
	public Try<Page<T>> getNestedCollectionPageTry(
		@PathParam("id") String id, @PathParam("nestedName") String nestedName);

	/**
	 * Updates the specified collection item.
	 *
	 * @param  id the resource's ID
	 * @param  body the request's body
	 * @return the updated single model, or an exception if an error occurred
	 */
	@Consumes({APPLICATION_JSON, MULTIPART_FORM_DATA})
	@Path("{id}")
	@PUT
	public Try<SingleModel<T>> updateCollectionItem(
		@PathParam("id") String id, Body body);

}