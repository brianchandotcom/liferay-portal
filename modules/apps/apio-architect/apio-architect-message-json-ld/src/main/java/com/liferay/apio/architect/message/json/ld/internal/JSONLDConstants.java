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

package com.liferay.apio.architect.message.json.ld.internal;

/**
 * Defines constants for JSON-LD message mappers with the reserved type and
 * field names.
 *
 * @author Alejandro Hernández
 */
public class JSONLDConstants {

	/**
	 * The JSON-LD <a
	 * href="https://json-ld.org/spec/latest/json-ld/#the-context">context </a>
	 * property.
	 */
	public static final String FIELD_NAME_CONTEXT = "@context";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#description">description </a>
	 * property.
	 */
	public static final String FIELD_NAME_DESCRIPTION = "description";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#expects">expects </a>
	 * property.
	 */
	public static final String FIELD_NAME_EXPECTS = "expects";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#first">first </a>
	 * property.
	 */
	public static final String FIELD_NAME_FIRST = "first";

	/**
	 * The JSON-LD <a
	 * href="https://json-ld.org/spec/latest/json-ld/#node-identifiers">
	 * identifier </a> property.
	 */
	public static final String FIELD_NAME_ID = "@id";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#last">last </a>
	 * property.
	 */
	public static final String FIELD_NAME_LAST = "last";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#member">member </a>
	 * property.
	 */
	public static final String FIELD_NAME_MEMBER = "member";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#method">method </a>
	 * property.
	 */
	public static final String FIELD_NAME_METHOD = "method";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#next">next </a>
	 * property.
	 */
	public static final String FIELD_NAME_NEXT = "next";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#numberOfItems">numberOfItems </a>
	 * property.
	 */
	public static final String FIELD_NAME_NUMBER_OF_ITEMS = "numberOfItems";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#operation">operation
	 * </a> property.
	 */
	public static final String FIELD_NAME_OPERATION = "operation";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#previous">previous
	 * </a> property.
	 */
	public static final String FIELD_NAME_PREVIOUS = "previous";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#property">property
	 * </a>.
	 */
	public static final String FIELD_NAME_PROPERTY = "property";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#readable">readable
	 * </a> property.
	 */
	public static final String FIELD_NAME_READABLE = "readable";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#required">required
	 * </a> property.
	 */
	public static final String FIELD_NAME_REQUIRED = "required";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#statusCode">statusCode </a>
	 * property.
	 */
	public static final String FIELD_NAME_STATUS_CODE = "statusCode";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#supportedProperty">
	 * supportedProperty </a>.
	 */
	public static final String FIELD_NAME_SUPPORTED_PROPERTY =
		"supportedProperty";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#title">title </a>
	 * property.
	 */
	public static final String FIELD_NAME_TITLE = "title";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#totalItems">totalItems </a>
	 * property.
	 */
	public static final String FIELD_NAME_TOTAL_ITEMS = "totalItems";

	/**
	 * The JSON-LD <a
	 * href="https://json-ld.org/spec/latest/json-ld/#specifying-the-type">type
	 * </a> property.
	 */
	public static final String FIELD_NAME_TYPE = "@type";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#view">view </a>
	 * property.
	 */
	public static final String FIELD_NAME_VIEW = "view";

	/**
	 * The JSON-LD <a
	 * href="https://json-ld.org/spec/latest/json-ld/#default-vocabulary">vocab
	 * </a> property.
	 */
	public static final String FIELD_NAME_VOCAB = "@vocab";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#writeable">writeable
	 * </a> property.
	 */
	public static final String FIELD_NAME_WRITEABLE = "writeable";

	/**
	 * The JSON-LD <a
	 * href="https://json-ld.org/spec/latest/json-ld/#application-ld-json">media
	 * type </a> property.
	 */
	public static final String MEDIA_TYPE = "application/ld+json";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#ApiDocumentation">ApiDocumentation
	 * </a> property.
	 */
	public static final String TYPE_API_DOCUMENTATION = "ApiDocumentation";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#Class">Class </a>
	 * property.
	 */
	public static final String TYPE_CLASS = "Class";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#Collection">Collection </a>
	 * property.
	 */
	public static final String TYPE_COLLECTION = "Collection";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#Operation">Operation
	 * </a> property.
	 */
	public static final String TYPE_OPERATION = "Operation";

	/**
	 * The Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#PartialCollectionView">
	 * PartialCollectionView </a> property.
	 */
	public static final String TYPE_PARTIAL_COLLECTION_VIEW =
		"PartialCollectionView";

	/**
	 * The Hydra <a href="https://www.w3.org/ns/hydra/core#SupportedProperty">
	 * SupportedProperty </a> property.
	 */
	public static final String TYPE_SUPPORTED_PROPERTY = "SupportedProperty";

	/**
	 * The Hydra profile URL.
	 */
	public static final String URL_HYDRA_PROFILE =
		"https://www.w3.org/ns/hydra/core#";

	/**
	 * The schema.org URL.
	 */
	public static final String URL_SCHEMA_ORG = "http://schema.org/";

}