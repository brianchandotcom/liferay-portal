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

package com.liferay.vulcan.message.json;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.RequestInfo;

import java.util.List;

/**
 * Instances of this interface can be used to add Vulcan the ability to
 * represent data in a different format.
 *
 * SingleModelMessageMappers works in an event like process.
 * The {@link com.liferay.vulcan.pagination.SingleModel}
 * {@link javax.ws.rs.ext.MessageBodyWriter} will call each of the methods of
 * the mapper. In each method developers should only map the provided part of
 * the resource, to its representation in a JSON object. For that, in all
 * methods a {@link JSONObjectBuilder} is received.
 *
 * All methods are called in a not predefined order, except
 * {@link #onStart(JSONObjectBuilder, Object, Class, RequestInfo)},
 * {@link #onFinish(JSONObjectBuilder, Object, Class, RequestInfo)} (called when
 * the writer starts and finishes the item).
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ConsumerType
public interface SingleModelMessageMapper<T> {

	/**
	 * Returns the media type that this mapper represents.
	 *
	 * @return the media type for this mapper.
	 */
	public String getMediaType();

	/**
	 * Maps an embedded resource field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param embeddedPathElements the embedded path elements of the current
	 *        resource.
	 * @param fieldName the field name.
	 * @param value the value of the field.
	 */
	public default void mapEmbeddedResourceField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {
	}

	/**
	 * Maps an embedded resource link to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param embeddedPathElements the embedded path elements of the current
	 *        resource.
	 * @param fieldName the field name.
	 * @param url the URL of the link.
	 */
	public default void mapEmbeddedResourceLink(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {
	}

	/**
	 * Maps an embedded resource types to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param embeddedPathElements the embedded path elements of the current
	 *        resource.
	 * @param types the resource types.
	 */
	public default void mapEmbeddedResourceTypes(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {
	}

	/**
	 * Maps an embedded resource URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param embeddedPathElements the embedded path elements of the current
	 *        resource.
	 * @param url the URL of the resource.
	 */
	public default void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param fieldName the field name.
	 * @param value the value of the field.
	 */
	public default void mapField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Object value) {
	}

	/**
	 * Maps a resource link to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param fieldName the field name.
	 * @param url the URL of the link.
	 */
	public default void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {
	}

	/**
	 * Maps a linked resource URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param embeddedPathElements the embedded path elements of the current
	 *        resource.
	 * @param url the URL of the resource.
	 */
	public default void mapLinkedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param url the URL of the resource.
	 */
	public default void mapSelfURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource types to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the json object builder for the actual item.
	 * @param types the resource types.
	 */
	public default void mapTypes(
		JSONObjectBuilder jsonObjectBuilder, List<String> types) {
	}

	/**
	 * This method is called when the writer is finishing the model. This means
	 * that no more methods in this mapper will be called.
	 *
	 * @param jsonObjectBuilder the json object builder for the model.
	 * @param model the actual model.
	 * @param modelClass the model class of the model.
	 * @param requestInfo the request info for the current request.
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, T model, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	/**
	 * This method is called when the writer is starting the model.
	 *
	 * @param jsonObjectBuilder the json object builder for the model.
	 * @param model the actual model.
	 * @param modelClass the model class of the model.
	 * @param requestInfo the request info for the current request.
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, T model, Class<T> modelClass,
		RequestInfo requestInfo) {
	}

	/**
	 * This method is called to check if the mapper supports mapping all things
	 * related to the current request.
	 *
	 * @param  model the actual model.
	 * @param  modelClass the model class of the model.
	 * @param  requestInfo the request info for the current request.
	 * @return <code>true</code> if mapper supports mapping this request;
	 *         <code>false</code> otherwise.
	 */
	public default boolean supports(
		T model, Class<T> modelClass, RequestInfo requestInfo) {

		return true;
	}

}