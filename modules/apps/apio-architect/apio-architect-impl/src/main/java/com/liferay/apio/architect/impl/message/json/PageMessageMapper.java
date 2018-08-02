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

package com.liferay.apio.architect.impl.message.json;

import com.liferay.apio.architect.impl.list.FunctionalList;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;

/**
 * Maps {@link Page} data to its representation in a JSON object. Instances of
 * this interface work like events. The {@code
 * javax.ws.rs.ext.MessageBodyWriter} of the {@code Page} calls the {@code
 * PageMessageMapper} methods. In each method, developers should only map the
 * provided part of the resource to its representation in a JSON object. To
 * enable this, each method receives a {@link JSONObjectBuilder}.
 *
 * <p>
 * The method {@link #onFinish} is called when the writer finishes writing the
 * page. Otherwise, the page message mapper's methods aren't called in a
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
public interface PageMessageMapper<T>
	extends MessageMapper<Page<T>>, OperationMapper {

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
	 * Maps a resource operation form's URL to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the operation
	 * @param url the operation form's URL
	 */
	@Override
	public default void mapFormURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapFormURL(
				jsonObjectBuilder, url));
	}

	/**
	 * Maps a resource operation's method to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the operation
	 * @param httpMethod the operation's method
	 */
	@Override
	public default void mapHTTPMethod(
		JSONObjectBuilder jsonObjectBuilder, HTTPMethod httpMethod) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.mapHTTPMethod(
				jsonObjectBuilder, httpMethod));
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

	@Override
	public default void mapOperationURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper ->
				singleModelMessageMapper.mapOperationURL(
					jsonObjectBuilder, url));
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
	 * Maps the semantics to its JSON object representation.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param semantics Semantics of each member provided by the collection
	 */
	public default void mapSemantics(
		JSONObjectBuilder jsonObjectBuilder, String semantics) {
	}

	/**
	 * Finishes the operation. This is the final operation-mapper method the
	 * writer calls.
	 *
	 * @param resourceJSONObjectBuilder the JSON object builder for the page
	 * @param operationJSONObjectBuilder the JSON object builder for the
	 *        operation
	 * @param operation the operation
	 */
	@Override
	public default void onFinish(
		JSONObjectBuilder resourceJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {

		Optional<SingleModelMessageMapper<T>> optional =
			getSingleModelMessageMapperOptional();

		optional.ifPresent(
			singleModelMessageMapper -> singleModelMessageMapper.onFinish(
				resourceJSONObjectBuilder, operationJSONObjectBuilder,
				operation));
	}

	/**
	 * Finishes the item. This is the final page message mapper method the
	 * writer calls for the item.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param singleModel the single model
	 */
	public default void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel) {
	}

	/**
	 * Finishes a nested collection. This is the final nested-collection-mapper
	 * method the writer calls.
	 *
	 * @param singleModelJSONObjectBuilder the JSON object builder for the root
	 *        model
	 * @param collectionJsonObjectBuilder the JSON object builder for the
	 *        collection
	 * @param fieldName the collection's field name
	 * @param list the collection
	 * @param embeddedPathElements the current resource's embedded path elements
	 */
	public default void onFinishNestedCollection(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder collectionJsonObjectBuilder, String fieldName,
		List<?> list, FunctionalList<String> embeddedPathElements) {
	}

	/**
	 * Finishes a nested collection item. This is the final
	 * nested-collection-item-mapper method the writer calls.
	 *
	 * @param collectionJsonObjectBuilder the JSON object builder for the
	 *        collection
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param singleModel the single model
	 */
	public default void onFinishNestedCollectionItem(
		JSONObjectBuilder collectionJsonObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<?> singleModel) {
	}

}