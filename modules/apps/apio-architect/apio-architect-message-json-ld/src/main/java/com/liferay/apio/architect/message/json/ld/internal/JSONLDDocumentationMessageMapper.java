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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_CONTEXT;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_DESCRIPTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_ID;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_PROPERTY;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_REQUIRED;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TITLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_API_DOCUMENTATION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_CLASS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_COLLECTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_OPERATION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_HYDRA_PROFILE;

import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.form.FormField;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.operation.Method;
import com.liferay.apio.architect.operation.Operation;

import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.annotations.Component;

/**
 * Represents documentation in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class JSONLDDocumentationMessageMapper
	implements DocumentationMessageMapper {

	@Override
	public String getMediaType() {
		return MEDIA_TYPE;
	}

	@Override
	public void mapDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {

		jsonObjectBuilder.field(
			FIELD_NAME_DESCRIPTION
		).stringValue(
			description
		);
	}

	@Override
	public void mapOperation(
		JSONObjectBuilder jsonObjectBuilder, String resourceName,
		Operation operation) {

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"_:" + operation.name
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_OPERATION
		);

		jsonObjectBuilder.field(
			"method"
		).stringValue(
			operation.method.toString()
		);

		String returnValue = _getReturnValue(resourceName, operation);

		jsonObjectBuilder.field(
			"returns"
		).stringValue(
			returnValue
		);
	}

	@Override
	public void mapProperty(
		JSONObjectBuilder jsonObjectBuilder, FormField formField) {

		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			formField.name
		);

		Boolean required = formField.required;

		jsonObjectBuilder.field(
			FIELD_NAME_REQUIRED
		).booleanValue(
			required
		);

		jsonObjectBuilder.field(
			"readonly"
		).booleanValue(
			false
		);

		jsonObjectBuilder.field(
			"writeonly"
		).booleanValue(
			false
		);
	}

	@Override
	public void mapResource(
		JSONObjectBuilder jsonObjectBuilder, String resourceName) {

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"http://schema.org/" + resourceName
		);
		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_CLASS
		);
		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			resourceName
		);
	}

	@Override
	public void mapTitle(JSONObjectBuilder jsonObjectBuilder, String title) {
		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			title
		);
	}

	@Override
	public void onFinishOperation(
		JSONObjectBuilder documentationJsonObjectBuilder,
		JSONObjectBuilder operationJsonObjectBuilder, Operation operation) {

		documentationJsonObjectBuilder.field(
			"supportedOperation"
		).arrayValue(
		).add(
			operationJsonObjectBuilder
		);
	}

	@Override
	public void onFinishProperty(
		JSONObjectBuilder documentationJsonObjectBuilder,
		JSONObjectBuilder propertyJsonObjectBuilder, FormField formField) {

		documentationJsonObjectBuilder.field(
			"supportedProperty"
		).arrayValue(
		).add(
			propertyJsonObjectBuilder
		);
	}

	@Override
	public void onFinishResource(
		JSONObjectBuilder documentationJsonObjectBuilder,
		JSONObjectBuilder resourceJsonObjectBuilder) {

		documentationJsonObjectBuilder.field(
			"supportedClass"
		).arrayValue(
		).add(
			resourceJsonObjectBuilder
		);
	}

	@Override
	public void onStart(
		JSONObjectBuilder jsonObjectBuilder, Documentation documentation,
		HttpHeaders httpHeaders) {

		JSONObjectBuilder.FieldStep contextBuilder =
			jsonObjectBuilder.nestedField(FIELD_NAME_CONTEXT);

		contextBuilder.field(
			"hydra"
		).stringValue(
			URL_HYDRA_PROFILE
		);

		contextBuilder.field(
			TYPE_API_DOCUMENTATION
		).stringValue(
			"hydra:ApiDocumentation"
		);

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"http://api.example.com/doc/"
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_API_DOCUMENTATION
		);

		JSONObjectBuilder.FieldStep propertyBuilder =
			contextBuilder.nestedField("property");

		propertyBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			FIELD_NAME_PROPERTY
		);

		propertyBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			FIELD_NAME_ID
		);

		contextBuilder.field(
			"readonly"
		).stringValue(
			"hydra:readonly"
		);

		contextBuilder.field(
			"writeonly"
		).stringValue(
			"hydra:writeonly"
		);

		contextBuilder.field(
			"supportedClass"
		).stringValue(
			"hydra:supportedClass"
		);

		contextBuilder.field(
			"supportedProperty"
		).stringValue(
			"hydra:supportedProperty"
		);

		contextBuilder.field(
			"supportedOperation"
		).stringValue(
			"hydra:supportedOperation"
		);

		contextBuilder.field(
			"method"
		).stringValue(
			"hydra:method"
		);

		JSONObjectBuilder.FieldStep expectBuilder = contextBuilder.nestedField(
			"expect");

		expectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"hydra:expect"
		);

		expectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			FIELD_NAME_ID
		);

		JSONObjectBuilder.FieldStep returnsBuilder = contextBuilder.nestedField(
			"returns");

		returnsBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"hydra:returns"
		);

		returnsBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			FIELD_NAME_ID
		);

		contextBuilder.field(
			"statusCodes"
		).stringValue(
			"hydra:statusCodes"
		);

		contextBuilder.field(
			"code"
		).stringValue(
			"hydra:statusCodes"
		);
	}

	private String _getReturnValue(String resourceName, Operation operation) {
		String value = null;

		if (Method.DELETE.equals(operation.method)) {
			value = "http://www.w3.org/2002/07/owl#Nothing";
		}
		else if (operation.method.equals(Method.GET)) {
			value = URL_HYDRA_PROFILE + TYPE_COLLECTION;
		}
		else {
			value = "http://schema.org/" + resourceName;
		}

		return value;
	}

}