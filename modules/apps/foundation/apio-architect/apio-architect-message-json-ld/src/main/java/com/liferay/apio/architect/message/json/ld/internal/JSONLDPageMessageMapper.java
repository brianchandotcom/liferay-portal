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

import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

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
@Component(immediate = true)
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
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.nestedField(
			"@context", "Collection"
		).stringValue(
			"http://www.w3.org/ns/hydra/pagination.jsonld"
		);

		jsonObjectBuilder.nestedField(
			"@context", "@vocab"
		).stringValue(
			"http://schema.org/"
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
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {

		pageJSONObjectBuilder.field(
			"members"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	private final SingleModelMessageMapper<T> _singleModelMessageMapper =
		new JSONLDSingleModelMessageMapper<>();

}