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

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.operation.Method;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.annotations.Component;

/**
 * Represents single models in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class JSONLDSingleModelMessageMapper<T>
	implements SingleModelMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/ld+json";
	}

	@Override
	public void mapBooleanField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Boolean value) {

		jsonObjectBuilder.field(
			fieldName
		).booleanValue(
			value
		);
	}

	@Override
	public void mapBooleanListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<Boolean> value) {

		jsonObjectBuilder.field(
			fieldName
		).arrayValue(
		).addAllBooleans(
			value
		);
	}

	@Override
	public void mapEmbeddedOperationFormURL(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		operationJSONObjectBuilder.field(
			"expects"
		).stringValue(
			url
		);
	}

	@Override
	public void mapEmbeddedOperationMethod(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Method method) {

		operationJSONObjectBuilder.field(
			"method"
		).stringValue(
			method.name()
		);
	}

	@Override
	public void mapEmbeddedResourceBooleanField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Boolean value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).booleanValue(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceBooleanListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Boolean> value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).arrayValue(
		).addAllBooleans(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceLink(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).stringValue(
			url
		);
	}

	@Override
	public void mapEmbeddedResourceNumberField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Number value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).numberValue(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceNumberListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Number> value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).arrayValue(
		).addAllNumbers(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceStringField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).stringValue(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceStringListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<String> value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).arrayValue(
		).addAllStrings(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceTypes(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			"@type"
		).arrayValue(
		).addAllStrings(
			types
		);
	}

	@Override
	public void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			"@id"
		).stringValue(
			url
		);
	}

	@Override
	public void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {

		jsonObjectBuilder.field(
			fieldName
		).stringValue(
			url
		);
	}

	@Override
	public void mapLinkedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		String head = embeddedPathElements.head();

		Stream<String> tailStream = embeddedPathElements.tailStream();

		String[] tail = tailStream.toArray(String[]::new);

		jsonObjectBuilder.nestedField(
			head, tail
		).stringValue(
			url
		);

		Stream<String> middleStream = embeddedPathElements.middleStream();

		String[] middle = middleStream.toArray(String[]::new);

		Optional<String> optional = embeddedPathElements.lastOptional();

		jsonObjectBuilder.ifElseCondition(
			optional.isPresent(),
			builder -> builder.nestedField(
				head, middle
			).nestedField(
				"@context", optional.get()
			),
			builder -> builder.nestedField("@context", head)
		).field(
			"@type"
		).stringValue(
			"@id"
		);
	}

	@Override
	public void mapNumberField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Number value) {

		jsonObjectBuilder.field(
			fieldName
		).numberValue(
			value
		);
	}

	@Override
	public void mapNumberListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<Number> value) {

		jsonObjectBuilder.field(
			fieldName
		).arrayValue(
		).addAllNumbers(
			value
		);
	}

	@Override
	public void mapOperationFormURL(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, String url) {

		operationJSONObjectBuilder.field(
			"expects"
		).stringValue(
			url
		);
	}

	@Override
	public void mapOperationMethod(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Method method) {

		operationJSONObjectBuilder.field(
			"method"
		).stringValue(
			method.name()
		);
	}

	@Override
	public void mapSelfURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		jsonObjectBuilder.field(
			"@id"
		).stringValue(
			url
		);
	}

	@Override
	public void mapStringField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String value) {

		jsonObjectBuilder.field(
			fieldName
		).stringValue(
			value
		);
	}

	@Override
	public void mapStringListField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName,
		List<String> value) {

		jsonObjectBuilder.field(
			fieldName
		).arrayValue(
		).addAllStrings(
			value
		);
	}

	@Override
	public void mapTypes(
		JSONObjectBuilder jsonObjectBuilder, List<String> types) {

		jsonObjectBuilder.field(
			"@type"
		).arrayValue(
		).addAllStrings(
			types
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.nestedField(
			"@context", "@vocab"
		).stringValue(
			"http://schema.org/"
		);
	}

	@Override
	public void onFinishEmbeddedOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Operation operation) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		String head = embeddedPathElements.head();
		String[] tail = tailStream.toArray(String[]::new);

		operationJSONObjectBuilder.field(
			"@id"
		).stringValue(
			operation.name
		);

		operationJSONObjectBuilder.field(
			"@type"
		).stringValue(
			"Operation"
		);

		singleModelJSONObjectBuilder.nestedField(
			head, tail
		).field(
			"operation"
		).arrayValue(
		).add(
			operationJSONObjectBuilder
		);
	}

	@Override
	public void onFinishOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {

		operationJSONObjectBuilder.field(
			"@id"
		).stringValue(
			operation.name
		);

		operationJSONObjectBuilder.field(
			"@type"
		).stringValue(
			"Operation"
		);

		singleModelJSONObjectBuilder.field(
			"operation"
		).arrayValue(
		).add(
			operationJSONObjectBuilder
		);
	}

}