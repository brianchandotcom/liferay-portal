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

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

/**
 * Maps {@link com.liferay.vulcan.pagination.SingleModel} data to its
 * representation in a JSON object. Instances of this interface work like
 * events. The {@code javax.ws.rs.ext.MessageBodyWriter} of the {@code
 * SingleModel} calls the {@code SingleModelMessageMapper} methods. In each
 * method, developers should only map the provided part of the resource to its
 * representation in a JSON object. To enable this, each method receives a
 * {@link JSONObjectBuilder}.
 *
 * The methods {@link #onStart(JSONObjectBuilder, Object, Class, HttpHeaders)}
 * and {@link #onFinish(JSONObjectBuilder, Object, Class, HttpHeaders)} are
 * called when the writer starts and finishes the single model item,
 * respectively. Otherwise, the message mapper's methods aren't called in a
 * particular order.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ConsumerType
@SuppressWarnings("unused")
public interface SingleModelMessageMapper<T> {

	/**
	 * Returns the media type the mapper represents.
	 *
	 * @return the media type the mapper represents
	 */
	public String getMediaType();

	/**
	 * Maps a resource's boolean field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapBooleanField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Boolean value) {
	}

	/**
	 * Maps an embedded resource's boolean field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceBooleanField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Boolean value) {
	}

	/**
	 * Maps an embedded resource's link to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param url the field's value
	 */
	public default void mapEmbeddedResourceLink(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {
	}

	/**
	 * Maps an embedded resource's number field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceNumberField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Number value) {
	}

	/**
	 * Maps an embedded resource's string field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceStringField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String value) {
	}

	/**
	 * Maps an embedded resource's types to their JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param types the resource's types
	 */
	public default void mapEmbeddedResourceTypes(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {
	}

	/**
	 * Maps an embedded resource's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param url the resource's URL
	 */
	public default void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource's link to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param url the link's URL
	 */
	public default void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {
	}

	/**
	 * Maps a linked resource's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param url the resource's URL
	 */
	public default void mapLinkedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource's number field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapNumberField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Number value) {
	}

	/**
	 * Maps a resource's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param url the resource's URL
	 */
	public default void mapSelfURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource's string field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapStringField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String value) {
	}

	/**
	 * Maps a resource's types to their JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the item
	 * @param types the resource's types
	 */
	public default void mapTypes(
		JSONObjectBuilder jsonObjectBuilder, List<String> types) {
	}

	/**
	 * Finishes the model. This is the final mapper method the writer calls.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param model the model
	 * @param modelClass the model class
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, T model, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Starts the model. This is the first mapper method the writer calls for
	 * the model.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param model the model
	 * @param modelClass the model class
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, T model, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Returns {@code true} if the mapper can map all things related to the
	 * current request.
	 *
	 * @param  model the model
	 * @param  modelClass the model class
	 * @param  httpHeaders the HTTP headers of the current request
	 * @return {@code true} if the mapper can map the request; {@code false}
	 *         otherwise
	 */
	public default boolean supports(
		T model, Class<T> modelClass, HttpHeaders httpHeaders) {

		return true;
	}

}