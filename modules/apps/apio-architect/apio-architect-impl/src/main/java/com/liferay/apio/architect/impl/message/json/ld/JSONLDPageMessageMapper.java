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

import com.liferay.apio.architect.impl.list.FunctionalList;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.message.json.PageMessageMapper;
import com.liferay.apio.architect.impl.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * Represents collection pages in JSON-LD + Hydra format.
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
public class JSONLDPageMessageMapper<T> implements PageMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/ld+json";
	}

	@Override
	public Optional<SingleModelMessageMapper<T>>
		getSingleModelMessageMapperOptional() {

		return Optional.of(_singleModelMessageMapper);
	}

	@Override
	public void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		_singleModelMessageMapper.mapSelfURL(jsonObjectBuilder, url);
	}

	@Override
	public void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "@id"
		).stringValue(
			url
		);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "first"
		).stringValue(
			url
		);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			"totalItems"
		).numberValue(
			totalCount
		);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "last"
		).stringValue(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "next"
		).stringValue(
			url
		);
	}

	@Override
	public void mapPageCount(JSONObjectBuilder jsonObjectBuilder, int count) {
		jsonObjectBuilder.field(
			"numberOfItems"
		).numberValue(
			count
		);
	}

	@Override
	public void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "previous"
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
	public void onFinish(JSONObjectBuilder jsonObjectBuilder, Page<T> page) {
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

		jsonObjectBuilder.nestedField(
			"view", "@type"
		).arrayValue(
		).addString(
			"PartialCollectionView"
		);

		jsonObjectBuilder.field(
			"@type"
		).arrayValue(
		).addString(
			"Collection"
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel) {

		pageJSONObjectBuilder.field(
			"member"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
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

	private String[] _getTail(FunctionalList<String> embeddedPathElements) {
		Stream<String> stream = embeddedPathElements.tailStream();

		return stream.toArray(String[]::new);
	}

	private final SingleModelMessageMapper<T> _singleModelMessageMapper =
		new JSONLDSingleModelMessageMapper<>();

}