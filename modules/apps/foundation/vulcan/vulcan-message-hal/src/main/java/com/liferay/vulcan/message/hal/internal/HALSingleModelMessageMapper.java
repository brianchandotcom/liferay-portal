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

package com.liferay.vulcan.message.hal.internal;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.SingleModelMessageMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Adds Vulcan the ability to represent single models in HAL format.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    <a href="http://stateless.co/hal_specification.html">HAL</a>
 */
@Component(
	immediate = true,
	service =
		{HALSingleModelMessageMapper.class, SingleModelMessageMapper.class}
)
public class HALSingleModelMessageMapper<T>
	implements SingleModelMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/hal+json";
	}

	@Override
	public void mapEmbeddedResourceField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {

		Optional<String> optional = embeddedPathElements.lastOptional();

		String head = embeddedPathElements.head();

		Stream<String> middleStream = embeddedPathElements.middleStream();

		String[] middle = middleStream.toArray(String[]::new);

		jsonObjectBuilder.field(
			"_embedded"
		).ifElseCondition(
			optional.isPresent(),
			fieldStep -> fieldStep.nestedSuffixedField(
				"_embedded", head, middle
			).field(
				optional.get()
			),
			fieldStep -> fieldStep.field(head)
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

		Optional<String> optional = embeddedPathElements.lastOptional();

		String head = embeddedPathElements.head();

		Stream<String> middleStream = embeddedPathElements.middleStream();

		String[] middle = middleStream.toArray(String[]::new);

		jsonObjectBuilder.field(
			"_embedded"
		).ifElseCondition(
			optional.isPresent(),
			fieldStep -> fieldStep.nestedSuffixedField(
				"_embedded", head, middle
			).field(
				optional.get()
			),
			fieldStep -> fieldStep.field(head)
		).nestedField(
			"_links", fieldName, "href"
		).value(
			url
		);
	}

	@Override
	public void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		mapEmbeddedResourceLink(
			jsonObjectBuilder, embeddedPathElements, "self", url);
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

		jsonObjectBuilder.nestedField(
			"_links", fieldName, "href"
		).value(
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
			).value(
				url
			);
		}
		else {
			Stream<String> middleStream = embeddedPathElements.middleStream();

			List<String> middleList = middleStream.collect(Collectors.toList());

			String prelast = middleList.remove(middleList.size() - 1);

			String[] middle = middleList.toArray(new String[middleList.size()]);

			jsonObjectBuilder.field(
				"_embedded"
			).nestedSuffixedField(
				"_embedded", head, middle
			).nestedField(
				prelast, "_links", optional.get(), "href"
			).value(
				url
			);
		}
	}

	@Override
	public void mapSelfURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		mapLink(jsonObjectBuilder, "self", url);
	}

}