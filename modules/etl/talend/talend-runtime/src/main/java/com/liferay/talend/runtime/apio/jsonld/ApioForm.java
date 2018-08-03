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
import com.liferay.talend.runtime.apio.form.Property;

import java.io.IOException;

import java.util.List;

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

	public List<Property> getSupportedProperties() {
		return ApioUtils.getSupportedProperties(
			getSupportedPropertiesJsonNode());
	}

	public JsonNode getSupportedPropertiesJsonNode() {
		return findJsonNode(FieldNames.SUPPORTED_PROPERTY);
	}

	private void _validateForm() throws IOException {
		if (!hasValueOf(FieldTypes.CLASS, getTypeJsonNode())) {
			throw new IOException("The given resource is not a from");
		}
	}

}