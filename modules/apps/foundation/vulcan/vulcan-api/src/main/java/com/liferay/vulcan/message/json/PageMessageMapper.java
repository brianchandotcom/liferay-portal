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
 * Instances of this interface can be used to add Vulcan the ability to
 * represent data in a different format.
 *
 * PageMessageMappers works in an event like process. The {@link Page}
 * {@link javax.ws.rs.ext.MessageBodyWriter} will call each of the methods of
 * the mapper. In each method developers should only map the provided part of
 * the resource, to its representation in a JSON object. For that, in all
 * methods a {@link JSONObjectBuilder} is received.
 *
 * All methods are called in a not predefined order, except
 * {@link #onStart(JSONObjectBuilder, Page, HttpHeaders)},
 * {@link #onFinish(JSONObjectBuilder, Page, HttpHeaders)} (called when
 * the writer starts and finishes the page) and
 * {@link #onStartItem(JSONObjectBuilder, JSONObjectBuilder, Object, Class,
 * HttpHeaders)},
 * {@link #onFinishItem(JSONObjectBuilder, JSONObjectBuilder, Object, Class,
 * HttpHeaders)} (called when the writer starts and finishes an item).
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @review
 */
@ConsumerType
@SuppressWarnings("unused")
public interface PageMessageMapper<T> {

	/**
	 * Returns the media type that this mapper represents.
	 *
	 * @return the media type for this mapper.
	 * @review
	 */
	public String getMediaType();

	/**
	 * Maps a collection URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the URL of the collection.
	 * @review
	 */
	public default void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the current page URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the current page URL.
	 * @review
	 */
	public default void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the first page URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the first page URL.
	 * @review
	 */
	public default void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps a resource boolean field to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
	 */
	public default void mapItemBooleanField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Boolean value) {
	}

	/**
	 * Maps an embedded resource boolean field to its JSON object
	 * representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
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
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  fieldName the field name.
	 * @param  url the URL of the link.
	 * @review
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
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
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
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
	 */
	public default void mapItemEmbeddedResourceStringField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String value) {
	}

	/**
	 * Maps an embedded resource types to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  types the resource types.
	 * @review
	 */
	public default void mapItemEmbeddedResourceTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {
	}

	/**
	 * Maps an embedded resource URL to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  url the URL of the resource.
	 * @review
	 */
	public default void mapItemEmbeddedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource link to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  fieldName the field name.
	 * @param  url the URL of the link.
	 * @review
	 */
	public default void mapItemLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName, String url) {
	}

	/**
	 * Maps a linked resource URL to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  embeddedPathElements the embedded path elements of the current
	 *         resource.
	 * @param  url the URL of the resource.
	 * @review
	 */
	public default void mapItemLinkedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {
	}

	/**
	 * Maps a resource number field to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
	 */
	public default void mapItemNumberField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Number value) {
	}

	/**
	 * Maps a resource URL to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  url the URL of the resource.
	 * @review
	 */
	public default void mapItemSelfURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String url) {
	}

	/**
	 * Maps a resource string field to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  fieldName the field name.
	 * @param  value the value of the field.
	 * @review
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
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  totalCount the total number of elements in the collection.
	 * @review
	 */
	public default void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {
	}

	/**
	 * Maps a resource types to its JSON object representation.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the whole page.
	 * @param  itemJSONObjectBuilder the json object builder for the actual
	 *         item.
	 * @param  types the resource types.
	 * @review
	 */
	public default void mapItemTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, List<String> types) {
	}

	/**
	 * Maps the last page URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the last page URL.
	 * @review
	 */
	public default void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the next page URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the next page URL.
	 * @review
	 */
	public default void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * Maps the page count to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  count the number of elements in the page.
	 * @review
	 */
	public default void mapPageCount(
		JSONObjectBuilder jsonObjectBuilder, int count) {
	}

	/**
	 * Maps the previous page URL to its JSON object representation.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  url the previous page URL.
	 * @review
	 */
	public default void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {
	}

	/**
	 * This method is called when the writer is finishing the page. This means
	 * that no more methods in this mapper will be called.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  page the actual page.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called when the writer is finishing an item. This means
	 * that no more methods in this mapper will be called for that item.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the page.
	 * @param  itemJSONObjectBuilder the json object builder for the item.
	 * @param  item the actual item.
	 * @param  modelClass the model class of the item.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called when the writer is starting the page.
	 *
	 * @param  jsonObjectBuilder the json object builder for the page.
	 * @param  page the actual page.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onStart(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called when the writer is starting an item.
	 *
	 * @param  pageJSONObjectBuilder the json object builder for the page.
	 * @param  itemJSONObjectBuilder the json object builder for the item.
	 * @param  item the actual item.
	 * @param  modelClass the model class of the item.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @review
	 */
	public default void onStartItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		HttpHeaders httpHeaders) {
	}

	/**
	 * This method is called to check if the mapper supports mapping all things
	 * related to the current request.
	 *
	 * @param  page the actual page.
	 * @param  httpHeaders the HTTP headers of the current request.
	 * @return {@code true} if mapper supports mapping this request;
	 *         {@code false} otherwise.
	 * @review
	 */
	public default boolean supports(Page<T> page, HttpHeaders httpHeaders) {
		return true;
	}

}