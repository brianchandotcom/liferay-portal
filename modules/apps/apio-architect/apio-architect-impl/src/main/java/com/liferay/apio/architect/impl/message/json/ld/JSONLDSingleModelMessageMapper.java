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

import static com.liferay.apio.architect.impl.message.json.ld.JSONLDMessageMapperUtil.getOperationTypes;

import com.liferay.apio.architect.impl.list.FunctionalList;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.operation.HTTPMethod;
import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
@Component
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
		FunctionalList<String> embeddedPathElements, HTTPMethod httpMethod) {

		operationJSONObjectBuilder.field(
			"method"
		).stringValue(
			httpMethod.name()
		);
	}

	@Override
	public void mapEmbeddedResourceBooleanField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Boolean value) {

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
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

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
		).field(
			"@id"
		).stringValue(
			url
		);
	}

	@Override
	public void mapFormURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		jsonObjectBuilder.field(
			"expects"
		).stringValue(
			url
		);
	}

	@Override
	public void mapHTTPMethod(
		JSONObjectBuilder jsonObjectBuilder, HTTPMethod httpMethod) {

		jsonObjectBuilder.field(
			"method"
		).stringValue(
			httpMethod.name()
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

		jsonObjectBuilder.nestedField(
			head, _getTail(embeddedPathElements)
		).stringValue(
			url
		);

		Optional<String> optional = embeddedPathElements.lastOptional();

		jsonObjectBuilder.ifElseCondition(
			optional.isPresent(),
			builder -> builder.nestedField(
				head, _getMiddle(embeddedPathElements)
			).field(
				"@context"
			),
			builder -> builder.field("@context")
		).arrayValue(
		).add(
			builder -> builder.field(
				optional.orElse(head)
			).field(
				"@type"
			).stringValue(
				"@id"
			)
		);
	}

	@Override
	public void mapNestedPageItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			"totalItems"
		).numberValue(
			totalCount
		);
	}

	@Override
	public void mapNestedPageSemantics(
		JSONObjectBuilder jsonObjectBuilder, String semantics) {

		jsonObjectBuilder.nestedField(
			"manages", "property"
		).stringValue(
			"rdf:type"
		);
		jsonObjectBuilder.nestedField(
			"manages", "object"
		).stringValue(
			"schema:" + semantics
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
	public void mapOperationURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.field(
			"target"
		).stringValue(
			url
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
		JSONObjectBuilder resourceJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder, Operation operation) {

		operationJSONObjectBuilder.field(
			"@id"
		).stringValue(
			"_:" + operation.getName()
		);

		operationJSONObjectBuilder.field(
			"@type"
		).arrayValue(
		).addAllStrings(
			getOperationTypes(operation)
		);

		resourceJSONObjectBuilder.field(
			"operation"
		).arrayValue(
		).add(
			operationJSONObjectBuilder
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, SingleModel<T> singleModel) {

		jsonObjectBuilder.field(
			"@context"
		).arrayValue(
			arrayBuilder -> arrayBuilder.add(
				builder -> builder.field(
					"@vocab"
				).stringValue(
					"http://schema.org/"
				)),
			arrayBuilder -> arrayBuilder.addString(
				"https://www.w3.org/ns/hydra/core#")
		);
	}

	@Override
	public void onFinishEmbeddedOperation(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder operationJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, Operation operation) {

		String head = embeddedPathElements.head();
		String[] tail = _getTail(embeddedPathElements);

		operationJSONObjectBuilder.field(
			"@id"
		).stringValue(
			"_:" + operation.getName()
		);

		operationJSONObjectBuilder.field(
			"@type"
		).arrayValue(
		).addAllStrings(
			getOperationTypes(operation)
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
	public void onFinishNestedCollection(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder collectionJsonObjectBuilder, String fieldName,
		List<?> list, FunctionalList<String> embeddedPathElements) {

		collectionJsonObjectBuilder.field(
			"@type"
		).arrayValue(
		).addString(
			"Collection"
		);

		singleModelJSONObjectBuilder.nestedField(
			embeddedPathElements.head(), _getTail(embeddedPathElements)
		).objectValue(
			collectionJsonObjectBuilder
		);
	}

	@Override
	public void onFinishNestedCollectionItem(
		JSONObjectBuilder collectionJsonObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<?> singleModel) {

		collectionJsonObjectBuilder.field(
			"member"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	private String[] _getMiddle(FunctionalList<String> embeddedPathElements) {
		Stream<String> stream = embeddedPathElements.middleStream();

		return stream.toArray(String[]::new);
	}

	private String[] _getTail(FunctionalList<String> embeddedPathElements) {
		Stream<String> stream = embeddedPathElements.tailStream();

		return stream.toArray(String[]::new);
	}

}