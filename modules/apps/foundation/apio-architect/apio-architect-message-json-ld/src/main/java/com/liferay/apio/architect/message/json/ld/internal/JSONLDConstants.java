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
 * Constants for JSON-LD message mappers with the reserved types and fields
 * names.
 *
 * @author Alejandro Hernández
 * @review
 */
public class JSONLDConstants {

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#ApiDocumentation">
	 * ApiDocumentation</a>.
	 *
	 * @review
	 */
	public static final String API_DOCUMENTATION = hydra("ApiDocumentation");

	/**
	 * JSON-LD <a href="https://json-ld.org/spec/latest/json-ld/#the-context">
	 * Context </a>.
	 *
	 * @review
	 */
	public static final String CONTEXT = "@context";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#description">
	 * description</a>.
	 *
	 * @review
	 */
	public static final String DESCRIPTION = "description";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#expects">expects</a>.
	 *
	 * @review
	 */
	public static final String EXPECTS = "expects";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#first">first</a>.
	 *
	 * @review
	 */
	public static final String FIRST = "first";

	/**
	 * Hydra qualifier.
	 *
	 * @review
	 */
	public static final String HYDRA = "hydra";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#Class">Class</a>.
	 *
	 * @review
	 */
	public static final String HYDRA_CLASS = hydra("Class");

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#Collection">
	 * Collection</a>.
	 *
	 * @review
	 */
	public static final String HYDRA_COLLECTION = hydra("Collection");

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#operation">operation</a>.
	 *
	 * @review
	 */
	public static final String HYDRA_OPERATION = hydra("operation");

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#PartialCollectionView">
	 * PartialCollectionView</a>.
	 *
	 * @review
	 */
	public static final String HYDRA_PARTIAL_COLLECTION_VIEW = hydra(
		"PartialCollectionView");

	/**
	 * Hydra profile URL.
	 *
	 * @review
	 */
	public static final String HYDRA_PROFILE =
		"https://www.w3.org/ns/hydra/core#";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#SupportedProperty">
	 * SupportedProperty</a>.
	 *
	 * @review
	 */
	public static final String HYDRA_SUPPORTED_PROPERTY = hydra(
		"SupportedProperty");

	/**
	 * JSON-LD <a href="
	 * https://json-ld.org/spec/latest/json-ld/#node-identifiers">
	 * Identifier</a>.
	 *
	 * @review
	 */
	public static final String ID = "@id";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#last">last</a>.
	 *
	 * @review
	 */
	public static final String LAST = "last";

	/**
	 * JSON-LD <a href="
	 * https://json-ld.org/spec/latest/json-ld/#application-ld-json">Media
	 * Type</a>.
	 *
	 * @review
	 */
	public static final String MEDIA_TYPE = "application/ld+json";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#member">member</a>.
	 *
	 * @review
	 */
	public static final String MEMBER = "member";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#method">method</a>.
	 *
	 * @review
	 */
	public static final String METHOD = "method";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#next">next</a>.
	 *
	 * @review
	 */
	public static final String NEXT = "next";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#numberOfItems">
	 * numberOfItems</a>.
	 *
	 * @review
	 */
	public static final String NUMBER_OF_ITEMS = "numberOfItems";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#Operation">Operation</a>.
	 *
	 * @review
	 */
	public static final String OPERATION = "Operation";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#previous">previous</a>.
	 *
	 * @review
	 */
	public static final String PREVIOUS = "previous";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#property">property</a>.
	 *
	 * @review
	 */
	public static final String PROPERTY = "property";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#readable">readable</a>.
	 *
	 * @review
	 */
	public static final String READABLE = "readable";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#required">required</a>.
	 *
	 * @review
	 */
	public static final String REQUIRED = "required";

	/**
	 * schema.org URL.
	 *
	 * @review
	 */
	public static final String SCHEMA_ORG = "http://schema.org/";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#statusCode">
	 * statusCode</a>.
	 *
	 * @review
	 */
	public static final String STATUS_CODE = "statusCode";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#supportedProperty">
	 * supportedProperty</a>.
	 *
	 * @review
	 */
	public static final String SUPPORTED_PROPERTY = "supportedProperty";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#title">title</a>.
	 *
	 * @review
	 */
	public static final String TITLE = "title";

	/**
	 * Hydra <a
	 * href="https://www.w3.org/ns/hydra/core#totalItems">totalItems</a>.
	 *
	 * @review
	 */
	public static final String TOTAL_ITEMS = "totalItems";

	/**
	 * JSON-LD <a href="
	 * https://json-ld.org/spec/latest/json-ld/#specifying-the-type">Type</a>.
	 *
	 * @review
	 */
	public static final String TYPE = "@type";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#view">view</a>.
	 *
	 * @review
	 */
	public static final String VIEW = "view";

	/**
	 * JSON-LD <a href="
	 * https://json-ld.org/spec/latest/json-ld/#default-vocabulary">Vocab</a>.
	 *
	 * @review
	 */
	public static final String VOCAB = "@vocab";

	/**
	 * Hydra <a href="https://www.w3.org/ns/hydra/core#writeable">writeable</a>.
	 *
	 * @review
	 */
	public static final String WRITEABLE = "writeable";

	/**
	 * Prefixes a type with the Hydra qualifier
	 *
	 * @param  type the type to prefix
	 * @return the type prefixed with the Hydra qualifier
	 * @review
	 */
	public static String hydra(String type) {
		return HYDRA + ":" + type;
	}

}