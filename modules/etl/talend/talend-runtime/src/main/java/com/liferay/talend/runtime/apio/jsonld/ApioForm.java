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

import com.liferay.talend.runtime.apio.constants.HydraConstants.FieldNames;
import com.liferay.talend.runtime.apio.constants.HydraConstants.FieldTypes;
import com.liferay.talend.runtime.apio.constants.JSONLDConstants;
import com.liferay.talend.runtime.apio.form.Property;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represent the Apio Architect Forms
 *
 * @author Zoltán Takács
 */
public class ApioForm extends ApioBaseResponse {

	public ApioForm(JsonNode responseJsonNode) throws IOException {
		super(responseJsonNode);

		_validateForm();
	}

	/**
	 * Parses the supportedProperty JsonNode of the form
	 *
	 * @return description of the Form or empty string if not present in the
	 *         String
	 */
	public String getDescription() {
		JsonNode jsonNode = responseJsonNode.path(FieldNames.DESCRIPTION);

		return jsonNode.asText();
	}

	/**
	 * Determines the supported properties of the resource collection and
	 * retruns them in a List
	 *
	 * @return <code>List</code> of <code>Operation</code>, empty List otherwise
	 */
	public List<Property> getSupportedProperties() {
		JsonNode supportedPropertiesJsonNode = getSupportedPropertiesJsonNode();

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

			if (!type.equals(FieldTypes.SUPPORTED_PROPERTY)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format("Skipping unexpected field: %s", type),
						type);
				}

				continue;
			}

			JsonNode propertyNameJsonNode = jsonNode.path(FieldNames.PROPERTY);
			JsonNode readableJsonNode = jsonNode.path(FieldNames.READABLE);
			JsonNode requiredJsonNode = jsonNode.path(FieldNames.REQUIRED);
			JsonNode writeableJsonNode = jsonNode.path(FieldNames.WRITEABLE);

			try {
				Property property = new Property(
					propertyNameJsonNode.asText(), requiredJsonNode.asBoolean(),
					readableJsonNode.asBoolean(),
					writeableJsonNode.asBoolean());

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

	public JsonNode getSupportedPropertiesJsonNode() {
		return findJsonNode(FieldNames.SUPPORTED_PROPERTY);
	}

	/**
	 * Parses the supportedProperty JsonNode of the form
	 *
	 * @return title of the Form or empty string if not present in the String
	 */
	public String getTitle() {
		JsonNode jsonNode = responseJsonNode.path(FieldNames.TITLE);

		return jsonNode.asText();
	}

	private void _validateForm() throws IOException {
		if (!hasValueOf(FieldTypes.CLASS, getTypeJsonNode())) {
			throw new IOException("The given resource is not a from");
		}
	}

	private static final Logger _log = LoggerFactory.getLogger(ApioForm.class);

}