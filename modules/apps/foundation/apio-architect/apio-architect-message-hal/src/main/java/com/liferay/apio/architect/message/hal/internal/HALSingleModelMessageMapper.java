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

package com.liferay.apio.architect.message.hal.internal;

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.message.json.SingleModelMessageMapper;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Represents single models in <a
 * href="http://stateless.co/hal_specification.html">HAL </a> format.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class HALSingleModelMessageMapper<T>
	implements SingleModelMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/hal+json";
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
	public void mapEmbeddedResourceBooleanField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Boolean value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.booleanValue(value));
	}

	@Override
	public void mapEmbeddedResourceBooleanListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Boolean> value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.arrayValue(
			).addAllBooleans(
				value
			));
	}

	@Override
	public void mapEmbeddedResourceLink(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		Optional<String> optional = embeddedPathElements.lastOptional();

		String head = embeddedPathElements.head();

		Stream<String> middleStream = embeddedPathElements.middleStream();

		String[] middle = middleStream.toArray(String[]::new);

		jsonObjectBuilder.field(
			"_embedded"
		).ifElseCondition(
			optional.isPresent(),
			builder -> builder.nestedSuffixedField(
				"_embedded", head, middle
			).field(
				optional.get()
			),
			builder -> builder.field(head)
		).nestedField(
			"_links", fieldName, "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapEmbeddedResourceNumberField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Number value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.numberValue(value));
	}

	@Override
	public void mapEmbeddedResourceNumberListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<Number> value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.arrayValue(
			).addAllNumbers(
				value
			));
	}

	@Override
	public void mapEmbeddedResourceStringField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.stringValue(value));
	}

	@Override
	public void mapEmbeddedResourceStringListField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		List<String> value) {

		_mapEmbeddedResourceField(
			jsonObjectBuilder, embeddedPathElements, fieldName,
			builder -> builder.arrayValue(
			).addAllStrings(
				value
			));
	}

	@Override
	public void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		mapEmbeddedResourceLink(
			jsonObjectBuilder, embeddedPathElements, "self", url);
	}

	@Override
	public void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {

		jsonObjectBuilder.nestedField(
			"_links", fieldName, "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapLinkedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		Optional<String> optional = embeddedPathElements.lastOptional();

		String head = embeddedPathElements.head();

		if (!optional.isPresent()) {
			jsonObjectBuilder.nestedField(
				"_links", head, "href"
			).stringValue(
				url
			);
		}
		else {
			Stream<String> middleStream = embeddedPathElements.middleStream();

			List<String> middleList = middleStream.collect(Collectors.toList());

			if (!middleList.isEmpty()) {
				String prelast = middleList.remove(middleList.size() - 1);

				String[] middle = middleList.toArray(
					new String[middleList.size()]);

				jsonObjectBuilder.field(
					"_embedded"
				).nestedSuffixedField(
					"_embedded", head, middle
				).nestedField(
					prelast, "_links", optional.get(), "href"
				).stringValue(
					url
				);
			}
			else {
				jsonObjectBuilder.field(
					"_embedded"
				).nestedField(
					head, "_links", optional.get(), "href"
				).stringValue(
					url
				);
			}
		}
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
	public void mapSelfURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		mapLink(jsonObjectBuilder, "self", url);
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

	private void _mapEmbeddedResourceField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Consumer<JSONObjectBuilder.FieldStep> consumer) {

		Optional<String> optional = embeddedPathElements.lastOptional();

		String head = embeddedPathElements.head();

		Stream<String> middleStream = embeddedPathElements.middleStream();

		String[] middle = middleStream.toArray(String[]::new);

		JSONObjectBuilder.FieldStep builderStep = jsonObjectBuilder.field(
			"_embedded"
		).ifElseCondition(
			optional.isPresent(),
			builder -> builder.nestedSuffixedField(
				"_embedded", head, middle
			).field(
				optional.get()
			),
			builder -> builder.field(head)
		).field(
			fieldName
		);

		consumer.accept(builderStep);
	}

}