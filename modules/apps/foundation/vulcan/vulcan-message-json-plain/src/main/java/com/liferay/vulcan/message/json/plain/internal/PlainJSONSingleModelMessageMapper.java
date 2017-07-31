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

package com.liferay.vulcan.message.json.plain.internal;

import com.liferay.vulcan.list.FunctionalList;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.SingleModelMessageMapper;

import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Adds Vulcan the ability to represent single models in plain JSON.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	service = {
		PlainJSONSingleModelMessageMapper.class, SingleModelMessageMapper.class
	}
)
public class PlainJSONSingleModelMessageMapper<T>
	implements SingleModelMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/json";
	}

	@Override
	public void mapEmbeddedResourceField(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
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
	public void mapEmbeddedResourceURL(
		JSONObjectBuilder jsonObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			"self"
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

		Stream<String> tailStream = embeddedPathElements.tailStream();

		jsonObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).value(
			url
		);
	}

	@Override
	public void mapSelfURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		jsonObjectBuilder.field(
			"self"
		).value(
			url
		);
	}

}