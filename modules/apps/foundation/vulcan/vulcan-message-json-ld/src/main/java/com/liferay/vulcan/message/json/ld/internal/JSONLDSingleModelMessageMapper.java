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

package com.liferay.vulcan.message.json.ld.internal;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.RequestInfo;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.SingleModelMessageMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Adds Vulcan the ability to represent single models in JSON-LD + Hydra format.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    <a href="https://json-ld.org/">JSON-LD</a>
 * @see    <a href="https://www.hydra-cg.com/">Hydra</a>
 */
@Component(
	immediate = true,
	service =
		{JSONLDSingleModelMessageMapper.class, SingleModelMessageMapper.class}
)
public class JSONLDSingleModelMessageMapper<T>
	implements SingleModelMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/ld+json";
	}

	@Override
	public void mapEmbeddedResourceField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).value(
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
		).value(
			url
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
		).addAll(
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
		).value(
			url
		);
	}

	@Override
	public void mapField(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, Object value) {

		jsonObjectBuilder.field(
			fieldName
		).value(
			value
		);
	}

	@Override
	public void mapLink(
		JSONObjectBuilder jsonObjectBuilder, String fieldName, String url) {

		jsonObjectBuilder.field(
			fieldName
		).value(
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
		).value(
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
		).value(
			"@id"
		);
	}

	@Override
	public void mapSelfURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		jsonObjectBuilder.field(
			"@id"
		).value(
			url
		);
	}

	@Override
	public void mapTypes(
		JSONObjectBuilder jsonObjectBuilder, List<String> types) {

		jsonObjectBuilder.field(
			"@type"
		).arrayValue(
		).addAll(
			types
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, T model, Class<T> modelClass,
		RequestInfo requestInfo) {

		jsonObjectBuilder.nestedField(
			"@context", "@vocab"
		).value(
			"http://schema.org"
		);
	}

}