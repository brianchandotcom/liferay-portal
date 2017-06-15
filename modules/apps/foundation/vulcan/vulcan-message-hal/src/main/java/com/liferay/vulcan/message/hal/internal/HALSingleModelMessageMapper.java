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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	service = {
		HALSingleModelMessageMapper.class, SingleModelMessageMapper.class
	}
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

		Stream<String> tailStream = embeddedPathElements.tail();

		String[] tail = tailStream.toArray(String[]::new);

		Stream<String> middleStream = embeddedPathElements.middle();

		String[] middle = middleStream.toArray(String[]::new);

		jsonObjectBuilder.field(
			"_embedded"
		).ifCondition(
			tail.length == 0,
			fieldStep -> fieldStep.nestedSuffixedField(
				"_embedded", embeddedPathElements.head(), middle)
		).nestedField(
			embeddedPathElements.last(), fieldName
		).value(
			value
		);
	}

	@Override
	public void mapEmbeddedResourceLink(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		Stream<String> tailStream = embeddedPathElements.tail();

		String[] tail = tailStream.toArray(String[]::new);

		Stream<String> middleStream = embeddedPathElements.middle();

		String[] middle = middleStream.toArray(String[]::new);

		jsonObjectBuilder.field(
			"_embedded"
		).ifCondition(
			tail.length == 0,
			fieldStep -> fieldStep.nestedSuffixedField(
				"_embedded", embeddedPathElements.head(), middle)
		).nestedField(
			embeddedPathElements.last(), "_links", fieldName, "href"
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

		Stream<String> tailStream = embeddedPathElements.tail();

		String[] tail = tailStream.toArray(String[]::new);

		if (tail.length == 0) {
			jsonObjectBuilder.nestedField(
				"_links", embeddedPathElements.head(), "href"
			).value(
				url
			);
		}
		else {
			Stream<String> middleStream = embeddedPathElements.middle();

			List<String> middleList = middleStream.collect(Collectors.toList());

			String prelast = middleList.remove(middleList.size() - 1);

			String[] middle = middleList.toArray(new String[middleList.size()]);

			jsonObjectBuilder.field(
				"_embedded"
			).nestedSuffixedField(
				"_embedded", embeddedPathElements.head(), middle
			).nestedField(
				prelast, "_links", embeddedPathElements.last(), "href"
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