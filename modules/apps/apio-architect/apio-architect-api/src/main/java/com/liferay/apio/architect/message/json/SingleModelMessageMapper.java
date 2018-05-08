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

package com.liferay.apio.architect.message.json;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.operation.Method;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;

/**
 * Maps {@link SingleModel} data to its representation in a JSON object.
 * Instances of this interface work like events. The {@code
 * javax.ws.rs.ext.MessageBodyWriter} of the {@code SingleModel} calls the
 * {@code SingleModelMessageMapper} methods. In each method, developers should
 * only map the provided part of the resource to its representation in a JSON
 * object. To enable this, each method receives a {@link JSONObjectBuilder}.
 *
 * <p>
 * The methods {@link #onStart(JSONObjectBuilder, Object, HttpHeaders)} and
 * {@link #onFinish(JSONObjectBuilder, Object, HttpHeaders)} are called when the
 * writer starts and finishes the single model item, respectively. Otherwise,
 * the message mapper's methods aren't called in a particular order.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @param  <T> the model's type
 */
@ConsumerType
@SuppressWarnings("unused")
public interface SingleModelMessageMapper<T>
	extends MessageMapper<SingleModel<T>> {

	/**
	 * Maps a resource's boolean field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapBooleanField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Boolean value) {
	}

	/**
	 * Maps a resource's boolean list field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapBooleanListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<Boolean> value) {
	}

	/**
	 * Maps an embedded resource operation form's URL to its JSON object
	 * representation.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param url the operation form's URL
	 */
	public default void mapEmbeddedOperationFormURL(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps an embedded resource operation's method to its JSON object
	 * representation.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param method the operation's method
	 */
	public default void mapEmbeddedOperationMethod(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Method method) {
	}

	/**
	 * Maps an embedded resource's boolean field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * Maps an embedded resource's boolean list field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceBooleanListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Boolean> value) {
	}

	/**
	 * Maps an embedded resource's link to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * Maps an embedded resource's number list field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceNumberListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Number> value) {
	}

	/**
	 * Maps an embedded resource's string field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * Maps an embedded resource's string list field to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapEmbeddedResourceStringListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<String> value) {
	}

	/**
	 * Maps an embedded resource's types to their JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param url the link's URL
	 */
	public default void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {
	}

	/**
	 * Maps a linked resource's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
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
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapNumberField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Number value) {
	}

	/**
	 * Maps a resource's number list field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapNumberListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<Number> value) {
	}

	/**
	 * Maps a resource operation form's URL to its JSON object representation.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param url the operation form's URL
	 */
	public default void mapOperationFormURL(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, String url) {
	}

	/**
	 * Maps a resource operation's method to its JSON object representation.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param method the operation's method
	 */
	public default void mapOperationMethod(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Method method) {
	}

	/**
	 * Maps a resource's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param url the resource's URL
	 */
	public default void mapSelfURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource's string field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapStringField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String value) {
	}

	/**
	 * Maps a resource's string list field to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapStringListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<String> value) {
	}

	/**
	 * Maps a resource's types to their JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the model
	 * @param types the resource's types
	 */
	public default void mapTypes(
		JSONObjectBuilder jsonObjectBuilder, List<String> types) {
	}

	/**
	 * Finishes an embedded model's operation. This is the final
	 * embedded-operation-mapper method the writer calls.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param operation the operation
	 */
	public default void onFinishEmbeddedOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Operation operation) {
	}

	/**
	 * Finishes the operation. This is the final operation-mapper method the
	 * writer calls.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	public default void onFinishOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {
	}

	/**
	 * Starts an embedded model's operation. This is the first
	 * embedded-operation-mapper method the writer calls.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param operation the operation
	 */
	public default void onStartEmbeddedOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Operation operation) {
	}

	/**
	 * Starts an operation. This is the first operation-mapper method the writer
	 * calls.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the model
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	public default void onStartOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {
	}

}