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
import com.liferay.vulcan.pagination.Page;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

/**
 * Maps {@link Page} data to its representation in a JSON object. Instances of
 * this interface work like events. The {@code
 * javax.ws.rs.ext.MessageBodyWriter} of the {@code Page} calls the {@code
 * PageMessageMapper} methods. In each method, developers should only map the
 * provided part of the resource to its representation in a JSON object. To
 * enable this, each method receives a {@link JSONObjectBuilder}.
 *
 * The methods {@link #onStart(JSONObjectBuilder, Page, HttpHeaders)} and
 * {@link #onFinish(JSONObjectBuilder, Page, HttpHeaders)} are called when the
 * writer starts and finishes the page, respectively. The methods
 * {@link #onStartItem(JSONObjectBuilder, JSONObjectBuilder, Object, Class,
 * HttpHeaders)}
 * and {@link #onFinishItem(JSONObjectBuilder, JSONObjectBuilder, Object, Class,
 * HttpHeaders)}
 * are called when the writer starts and finishes an item, respectively.
 * Otherwise, the page message mapper's methods aren't called in a particular
 * order.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ConsumerType
@SuppressWarnings("unused")
public interface PageMessageMapper<T> {

	/**
	 * Returns the media type the mapper represents.
	 *
	 * @return the media type the mapper represents
	 */
	public String getMediaType();

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
	}

	/**
	 * Maps an embedded resource number field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceNumberField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Number value) {
	}

	/**
	 * Maps an embedded resource string field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the json object builder for the page
	 * @param itemJSONObjectBuilder the json object builder for the item
	 * @param embeddedPathElements the current resource's embedded path elements
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemEmbeddedResourceStringField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String value) {
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
	}

	/**
	 * Maps a resource number field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemNumberField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Number value) {
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
	}

	/**
	 * Maps a resource string field to its JSON object representation.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param fieldName the field's name
	 * @param value the field's value
	 */
	public default void mapItemStringField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		String value) {
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
	 * Finishes the page. This is the final page message mapper method the
	 * writer calls.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param page the page
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Finishes the item. This is the final page message mapper method the
	 * writer calls for the item.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param item the item
	 * @param modelClass the item's model class
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Starts the page. This is the first page message mapper method the writer
	 * calls for the page.
	 *
	 * @param jsonObjectBuilder the JSON object builder for the page
	 * @param page the page
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Starts the item. This is the first page message mapper method the writer
	 * calls for the item.
	 *
	 * @param pageJSONObjectBuilder the JSON object builder for the page
	 * @param itemJSONObjectBuilder the JSON object builder for the item
	 * @param item the item
	 * @param modelClass the item's model class
	 * @param httpHeaders the current request's HTTP headers
	 */
	public default void onStartItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * Returns {@code true} if the mapper can map all things related to the
	 * current request.
	 *
	 * @param  page the page
	 * @param  httpHeaders the current request's HTTP headers
	 * @return {@code true} if the mapper can map the request; {@code false}
	 *         otherwise
	 */
	public default boolean supports(Page<T> page, HttpHeaders httpHeaders) {
		return true;
	}

}