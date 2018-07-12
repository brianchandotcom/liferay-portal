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

package com.liferay.apio.architect.impl.message.json.ld;

import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_CONTEXT;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_DESCRIPTION;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_ID;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_MEMBER;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_NUMBER_OF_ITEMS;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_PROPERTY;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_TITLE;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_TOTAL_ITEMS;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.FIELD_NAME_VOCAB;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.TYPE_API_DOCUMENTATION;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.TYPE_CLASS;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.TYPE_COLLECTION;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.TYPE_OPERATION;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.TYPE_SUPPORTED_PROPERTY;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.URL_HYDRA_PROFILE;
import static com.liferay.apio.architect.impl.message.json.ld.JSONLDConstants.URL_SCHEMA_ORG;
import static com.liferay.apio.architect.operation.HTTPMethod.DELETE;
import static com.liferay.apio.architect.operation.HTTPMethod.GET;

import com.liferay.apio.architect.impl.documentation.Documentation;
import com.liferay.apio.architect.impl.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;

import java.util.stream.Stream;

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
 * @author Zoltán Takács
 */
@Component
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
		JSONObjectBuilder jsonObjectBuilder, String resourceName, String type,
		Operation operation) {

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"_:" + operation.getName()
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_OPERATION
		);

		jsonObjectBuilder.field(
			"method"
		).stringValue(
			operation.getHttpMethod().toString()
		);

		jsonObjectBuilder.field(
			"returns"
		).stringValue(
			_getReturnValue(type, operation)
		);
	}

	@Override
	public void mapProperty(
		JSONObjectBuilder jsonObjectBuilder, String fieldName) {

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_SUPPORTED_PROPERTY
		);

		jsonObjectBuilder.field(
			FIELD_NAME_PROPERTY
		).stringValue(
			fieldName
		);
	}

	@Override
	public void mapResource(
		JSONObjectBuilder jsonObjectBuilder, String resourceType) {

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			resourceType
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_CLASS
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			resourceType
		);
	}

	@Override
	public void mapResourceCollection(
		JSONObjectBuilder jsonObjectBuilder, String resourceType) {

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"vocab:" + resourceType + "Collection"
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_CLASS
		);

		jsonObjectBuilder.field(
			"subClassOf"
		).stringValue(
			TYPE_COLLECTION
		);

		jsonObjectBuilder.field(
			"description"
		).stringValue(
			"A collection of " + resourceType
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			resourceType + "Collection"
		);

		Stream.of(
			FIELD_NAME_TOTAL_ITEMS, FIELD_NAME_MEMBER,
			FIELD_NAME_NUMBER_OF_ITEMS
		).forEach(
			fieldName -> {
				JSONObjectBuilder propertyJsonObjectBuilder =
					new JSONObjectBuilder();

				mapProperty(propertyJsonObjectBuilder, fieldName);

				onFinishProperty(
					jsonObjectBuilder, propertyJsonObjectBuilder, fieldName);
			}
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
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Documentation documentation) {

		jsonObjectBuilder.field(
			FIELD_NAME_CONTEXT
		).arrayValue(
			arrayBuilder -> arrayBuilder.add(
				builder -> builder.field(
					FIELD_NAME_VOCAB
				).stringValue(
					URL_SCHEMA_ORG
				)
			),
			arrayBuilder -> arrayBuilder.addString(URL_HYDRA_PROFILE),
			arrayBuilder -> arrayBuilder.add(
				builder -> builder.field(
					"expects"
				).fields(
					nestedBuilder -> nestedBuilder.field(
						FIELD_NAME_TYPE
					).stringValue(
						FIELD_NAME_ID
					),
					nestedBuilder -> nestedBuilder.field(
						FIELD_NAME_ID
					).stringValue(
						"hydra:expects"
					)
				),
				builder -> builder.field(
					"returns"
				).fields(
					nestedBuilder -> nestedBuilder.field(
						FIELD_NAME_ID
					).stringValue(
						"hydra:returns"
					),
					nestedBuilder -> nestedBuilder.field(
						FIELD_NAME_TYPE
					).stringValue(
						FIELD_NAME_ID
					)
				)
			)
		);

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"/doc"
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_API_DOCUMENTATION
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
		JSONObjectBuilder propertyJsonObjectBuilder, String formField) {

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
		JSONObjectBuilder resourceJsonObjectBuilder, String type) {

		documentationJsonObjectBuilder.field(
			"supportedClass"
		).arrayValue(
		).add(
			resourceJsonObjectBuilder
		);
	}

	private String _getReturnValue(String resourceName, Operation operation) {
		String value = null;

		HTTPMethod httpMethod = operation.getHttpMethod();

		if (DELETE.equals(httpMethod)) {
			value = "http://www.w3.org/2002/07/owl#Nothing";
		}
		else if (operation.isCollection() && httpMethod.equals(GET)) {
			value = TYPE_COLLECTION;
		}
		else {
			value = resourceName;
		}

		return value;
	}

}