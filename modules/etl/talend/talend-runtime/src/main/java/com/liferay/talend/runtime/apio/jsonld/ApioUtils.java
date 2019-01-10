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

package com.liferay.talend.runtime.apio.jsonld;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.runtime.apio.constants.HydraConstants;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;
import com.liferay.talend.runtime.apio.form.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class ApioUtils {

	/**
	 * Finds the context node in the given JsonNode if there is any.
	 *
	 * @param  jsonNode Input JsonNode
	 * @return JsonNode for the context node, otherwise MissingNode
	 * @review
	 */
	public static JsonNode getContextJsonNode(JsonNode jsonNode) {
		return _findJsonNode(jsonNode, JSONLDConstants.CONTEXT);
	}

	/**
	 * Determines the type of the given <code>collection</code> JsonNode
	 *
	 * @param  collectionJsonNode
	 * @return String the managed type of the collection, empty string otherwise
	 * @review
	 */
	public static String getManagedType(JsonNode collectionJsonNode) {
		JsonNode typeJsonNode = collectionJsonNode.path(JSONLDConstants.TYPE);

		if (!hasValueOf(HydraConstants.FieldTypes.COLLECTION, typeJsonNode)) {
			_log.error(
				"Unexpected type for the collection: \"{}\"",
				typeJsonNode.toString());

			throw new NoSuchElementException(
				"Unable to determine the managed type of the collection");
		}

		JsonNode managesJsonNode = collectionJsonNode.path(
			HydraConstants.FieldNames.MANAGES);

		JsonNode typeObjectJsonNode = managesJsonNode.path(
			HydraConstants.FieldNames.OBJECT);

		String managedType = typeObjectJsonNode.asText();

		return managedType.replaceFirst("schema:", "");
	}

	/**
	 * Determines the supported properties of the Hydra Class and returns them
	 * in a List
	 *
	 * @return <code>List</code> of <code>Operation</code>, empty List otherwise
	 * @review
	 */
	public static List<Property> getSupportedProperties(
		JsonNode supportedPropertiesJsonNode) {

		if (!supportedPropertiesJsonNode.isArray() ||
			(supportedPropertiesJsonNode.size() == 0)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to fetch the resource's supported properties");
			}

			return Collections.<Property>emptyList();
		}

		List<Property> supportedProperties = new ArrayList<>();

		for (final JsonNode jsonNode : supportedPropertiesJsonNode) {
			JsonNode typeJsonNode = jsonNode.path(JSONLDConstants.TYPE);

			String type = typeJsonNode.asText();

			if (!type.equals(HydraConstants.FieldTypes.SUPPORTED_PROPERTY)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format("Skipping unexpected field: %s", type),
						type);
				}

				continue;
			}

			JsonNode propertyNameJsonNode = jsonNode.path(
				HydraConstants.FieldNames.PROPERTY);
			JsonNode readableJsonNode = jsonNode.path(
				HydraConstants.FieldNames.READABLE);
			JsonNode requiredJsonNode = jsonNode.path(
				HydraConstants.FieldNames.REQUIRED);
			JsonNode writableJsonNode = jsonNode.path(
				HydraConstants.FieldNames.WRITEABLE);

			try {
				Property property = new Property(
					propertyNameJsonNode.asText(), requiredJsonNode.asBoolean(),
					readableJsonNode.asBoolean(), writableJsonNode.asBoolean());

				supportedProperties.add(property);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"Unsupported property: %s", iae.getMessage()),
						iae);
				}
			}
		}

		return Collections.unmodifiableList(supportedProperties);
	}

	/**
	 * Parses the given JsonNode which is a <code>@context</code> node and find
	 * the type coercion terms.
	 *
	 * @param  contextJsonNode
	 * @return the name of the type coercion term keys otherwise empty
	 *         <code>List<String></code>
	 * @review
	 */
	public static List<String> getTypeCoercionTermKeys(
		JsonNode contextJsonNode) {

		List<String> typeCoercionTermKeys = new ArrayList<>();

		for (JsonNode jsonNode : contextJsonNode) {
			if (jsonNode.isObject()) {
				JsonNode typeObjectJsonNode = jsonNode.findParent(
					JSONLDConstants.TYPE);

				if (typeObjectJsonNode != null) {
					JsonNode typeJsonNode = typeObjectJsonNode.path(
						JSONLDConstants.TYPE);

					if (JSONLDConstants.ID.equals(typeJsonNode.asText())) {
						Iterator<String> it = jsonNode.fieldNames();

						while (it.hasNext()) {
							typeCoercionTermKeys.add(it.next());
						}
					}
				}
			}
		}

		return typeCoercionTermKeys;
	}

	/**
	 * Parses the given JsonNode which is a <code>@context</code> node and find
	 * the value of the <code>@vocab</code> node.
	 *
	 * @param  contextJsonNode
	 * @return <code>String</code> the Vocab's value e.g "@vocab":
	 *         "http://schema.org" otherwise empty String
	 * @review
	 */
	public static String getVocabulary(JsonNode contextJsonNode) {
		JsonNode jsonNode = contextJsonNode.path(JSONLDConstants.VOCAB);

		return jsonNode.asText();
	}

	public static boolean hasValueOf(String value, JsonNode jsonNode) {
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterator = jsonNode.elements();

			while (iterator.hasNext()) {
				JsonNode entryJsonNode = iterator.next();

				String entry = entryJsonNode.asText();

				if (entry.equals(value)) {
					return true;
				}
			}
		}
		else if (jsonNode.isValueNode()) {
			String entry = jsonNode.asText();

			if (entry.equals(value)) {
				return true;
			}
		}

		return false;
	}

	private static JsonNode _findJsonNode(JsonNode resource, String nodeName) {
		JsonNode jsonNode = resource.path(nodeName);

		if (_log.isDebugEnabled()) {
			if (jsonNode.isMissingNode()) {
				_log.debug("Unable to find the \"{}\" node", nodeName);
			}

			if (jsonNode.isArray() && (jsonNode.size() == 0)) {
				_log.debug("The \"{}\" array node is empty", jsonNode);
			}
		}

		return jsonNode;
	}

	private ApioUtils() {
	}

	private static final Logger _log = LoggerFactory.getLogger(ApioUtils.class);

}