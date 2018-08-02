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

import com.liferay.apio.architect.impl.entrypoint.EntryPoint;
import com.liferay.apio.architect.impl.message.json.EntryPointMessageMapper;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * Represents the entry point in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component
public class JSONLDEntryPointMessageMapper implements EntryPointMessageMapper {

	@Override
	public String getMediaType() {
		return "application/ld+json";
	}

	@Override
	public void mapItemSelfURL(
		JSONObjectBuilder entryPointJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String resourceName,
		String url) {

		mapSelfURL(itemJSONObjectBuilder, url);
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
	public void mapSemantics(
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
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, EntryPoint entryPoint) {

		jsonObjectBuilder.field(
			"@type"
		).stringValue(
			"EntryPoint"
		);

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
	public void onFinishItem(
		JSONObjectBuilder entryPointJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder) {

		itemJSONObjectBuilder.field(
			"@type"
		).arrayValue(
		).addString(
			"Collection"
		);

		entryPointJSONObjectBuilder.field(
			"collection"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

}