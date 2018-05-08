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
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

/**
 * Maps {@link Page} data to its representation in a JSON object. Instances of
 * this interface work like events. The {@code
 * javax.ws.rs.ext.MessageBodyWriter} of the {@code Page} calls the {@code
 * PageMessageMapper} methods. In each method, developers should only map the
 * provided part of the resource to its representation in a JSON object. To
 * enable this, each method receives a {@link JSONObjectBuilder}.
 *
 * <p>
 * The methods {@link #onStart(JSONObjectBuilder, Object, HttpHeaders)} and
 * {@link #onFinish(JSONObjectBuilder, Object, HttpHeaders)} are called when the
 * writer starts and finishes the page, respectively. The methods {@link
 * #onStartItem(JSONObjectBuilder, JSONObjectBuilder, SingleModel, HttpHeaders)}
 * and {@link #onFinishItem(JSONObjectBuilder, JSONObjectBuilder, SingleModel,
 * HttpHeaders)} are called when the writer starts and finishes an item,
 * respectively. Otherwise, the page message mapper's methods aren't called in a
 * particular order.
 * </p>
 *
 * <p>
 * By default, each item method calls {@link
 * #getSingleModelMessageMapperOptional()} to get a {@link
 * SingleModelMessageMapper}.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @param  <T> the model's type
 */
@ConsumerType
@SuppressWarnings("unused")
public interface PageMessageMapper<T> extends MessageMapper<Page<T>> {

	/**
	 * Returns the {@link SingleModelMessageMapper} used by the item methods.
	 *
	 * @return the {@code SingleModelMessageMapper}
	 */
	public default Optional<SingleModelMessageMapper<T>>
		getSingleModelMessageMapperOptional() {

		return Optional.empty();
	}

	/**
	 * Maps a collection URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the collection's URL
	 */
	public default void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the current page's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the current page's URL
	 */
	public default void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the first page's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the first page's URL
	 */
	public default void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource's boolean field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemBooleanField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Boolean value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapBooleanField(
					itemJSONObjectBuilder, fieldName, value));
	}

	/**
	 * Maps a resource's boolean list field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemBooleanListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		List<Boolean> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapBooleanListField(
					itemJSONObjectBuilder, fieldName, value));
	}

	/**
	 * Maps an embedded resource's boolean field to its JSON object
	 * representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceBooleanField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Boolean value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceBooleanField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					value));
	}

	/**
	 * Maps an embedded resource's boolean list field to its JSON object
	 * representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceBooleanListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Boolean> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceBooleanListField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					value));
	}

	/**
	 * Maps an embedded resource link to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param url the link's URL
	 */
	public default void mapItemEmbeddedResourceLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceLink(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					url));
	}

	/**
	 * Maps an embedded resource number field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param number the field's value
	 */
	public default void mapItemEmbeddedResourceNumberField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Number number) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceNumberField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					number));
	}

	/**
	 * Maps an embedded resource's number list field to its JSON object
	 * representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceNumberListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Number> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceNumberListField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					value));
	}

	/**
	 * Maps an embedded resource string field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the json object builder for the page
	 * @param itemJSONObjectBuilder the json object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param string the field's value
	 */
	public default void mapItemEmbeddedResourceStringField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String string) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceStringField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					string));
	}

	/**
	 * Maps an embedded resource's string list field to its JSON object
	 * representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceStringListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<String> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceStringListField(
					itemJSONObjectBuilder, embeddedPathElements, fieldName,
					value));
	}

	/**
	 * Maps embedded resource types to their JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param types the resource types
	 */
	public default void mapItemEmbeddedResourceTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceTypes(
					itemJSONObjectBuilder, embeddedPathElements, types));
	}

	/**
	 * Maps an embedded resource URL to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param url the resource's URL
	 */
	public default void mapItemEmbeddedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapEmbeddedResourceURL(
					itemJSONObjectBuilder, embeddedPathElements, url));
	}

	/**
	 * Maps a resource link to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param url the link's URL
	 */
	public default void mapItemLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapLink(
				itemJSONObjectBuilder, fieldName, url));
	}

	/**
	 * Maps a linked resource URL to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param url the resource's URL
	 */
	public default void mapItemLinkedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapLinkedResourceURL(
					itemJSONObjectBuilder, embeddedPathElements, url));
	}

	/**
	 * Maps a resource number field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param number the field's value
	 */
	public default void mapItemNumberField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Number number) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapNumberField(
				itemJSONObjectBuilder, fieldName, number));
	}

	/**
	 * Maps a resource's number list field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemNumberListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		List<Number> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapNumberListField(
					itemJSONObjectBuilder, fieldName, value));
	}

	/**
	 * Maps a resource URL to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param url the resource's URL
	 */
	public default void mapItemSelfURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapSelfURL(
				itemJSONObjectBuilder, url));
	}

	/**
	 * Maps a resource string field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param string the field's value
	 */
	public default void mapItemStringField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		String string) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapStringField(
				itemJSONObjectBuilder, fieldName, string));
	}

	/**
	 * Maps a resource's string list field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemStringListField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		List<String> value) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapStringListField(
					itemJSONObjectBuilder, fieldName, value));
	}

	/**
	 * Maps the total number of elements in the collection to its JSON object
	 * representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param totalCount the total number of elements in the collection
	 */
	public default void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {
	}

	/**
	 * Maps resource types to their JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param types the resource types
	 */
	public default void mapItemTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, List<String> types) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapTypes(
				itemJSONObjectBuilder, types));
	}

	/**
	 * Maps the last page's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the last page's URL
	 */
	public default void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the next page's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the next page's URL
	 */
	public default void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource operation form's URL to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param url the operation form's URL
	 */
	public default void mapOperationFormURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapOperationFormURL(
					pageJSONObjectBuilder, operationJSONObjectBuilder, url));
	}

	/**
	 * Maps a resource operation's method to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param method the operation's method
	 */
	public default void mapOperationMethod(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Method method) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapOperationMethod(
					pageJSONObjectBuilder, operationJSONObjectBuilder, method));
	}

	/**
	 * Maps the page count to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param count the number of elements in the page
	 */
	public default void mapPageCount(
		JSONObjectBuilder jsonObjectBuilder, int count) {
	}

	/**
	 * Maps the previous page's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param url the previous page's URL
	 */
	public default void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Finishes the item. This is the final page message mapper method the
	 * writer calls for the item.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param singleModel the single model
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Finishes the operation. This is the final operation-mapper method the
	 * writer calls.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	public default void onFinishOperation(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.onFinishOperation(
					pageJSONObjectBuilder, operationJSONObjectBuilder,
					operation));
	}

	/**
	 * Starts the item. This is the first page message mapper method the writer
	 * calls for the item.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param singleModel the single model
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStartItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Starts an operation. This is the first operation-mapper method the writer
	 * calls.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	public default void onStartOperation(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.onStartOperation(
					pageJSONObjectBuilder, operationJSONObjectBuilder,
					operation));
	}

}