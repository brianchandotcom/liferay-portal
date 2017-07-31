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
import com.liferay.vulcan.message.json.PageMessageMapper;
import com.liferay.vulcan.pagination.Page;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Adds Vulcan the ability to represent collection pages in JSON-LD + Hydra
 * format.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 * @see    <a href="https://json-ld.org/">JSON-LD</a>
 * @see    <a href="https://www.hydra-cg.com/">Hydra</a>
 */
@Component(immediate = true)
public class JSONLDPageMessageMapper<T> implements PageMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/ld+json";
	}

	@Override
	public void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		_jsonLDSingleModelMessageMapper.mapSelfURL(jsonObjectBuilder, url);
	}

	@Override
	public void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "@id"
		).value(
			url
		);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "first"
		).value(
			url
		);
	}

	@Override
	public void mapItemEmbeddedResourceField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object fieldData) {

		Stream<String> tailStream = embeddedPathElements.tailStream();

		itemJSONObjectBuilder.nestedField(
			embeddedPathElements.head(), tailStream.toArray(String[]::new)
		).field(
			fieldName
		).value(
			fieldData
		);
	}

	@Override
	public void mapItemEmbeddedResourceLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		_jsonLDSingleModelMessageMapper.mapEmbeddedResourceLink(
			itemJSONObjectBuilder, embeddedPathElements, fieldName, url);
	}

	@Override
	public void mapItemEmbeddedResourceTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, List<String> types) {

		_jsonLDSingleModelMessageMapper.mapEmbeddedResourceTypes(
			itemJSONObjectBuilder, embeddedPathElements, types);
	}

	@Override
	public void mapItemEmbeddedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		_jsonLDSingleModelMessageMapper.mapEmbeddedResourceURL(
			itemJSONObjectBuilder, embeddedPathElements, url);
	}

	@Override
	public void mapItemField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Object value) {

		itemJSONObjectBuilder.field(
			fieldName
		).value(
			value
		);
	}

	@Override
	public void mapItemLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName, String url) {

		_jsonLDSingleModelMessageMapper.mapLink(
			itemJSONObjectBuilder, fieldName, url);
	}

	@Override
	public void mapItemLinkedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		_jsonLDSingleModelMessageMapper.mapLinkedResourceURL(
			itemJSONObjectBuilder, embeddedPathElements, url);
	}

	@Override
	public void mapItemSelfURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String url) {

		_jsonLDSingleModelMessageMapper.mapSelfURL(itemJSONObjectBuilder, url);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			"totalItems"
		).value(
			totalCount
		);
	}

	@Override
	public void mapItemTypes(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, List<String> types) {

		_jsonLDSingleModelMessageMapper.mapTypes(itemJSONObjectBuilder, types);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "last"
		).value(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "next"
		).value(
			url
		);
	}

	@Override
	public void mapPageCount(JSONObjectBuilder jsonObjectBuilder, int count) {
		jsonObjectBuilder.field(
			"numberOfItems"
		).value(
			count
		);
	}

	@Override
	public void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"view", "previous"
		).value(
			url
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page, Class<T> modelClass,
		RequestInfo requestInfo) {

		jsonObjectBuilder.nestedField(
			"@context", "Collection"
		).value(
			"http://www.w3.org/ns/hydra/pagination.jsonld"
		);

		jsonObjectBuilder.nestedField(
			"@context", "@vocab"
		).value(
			"http://schema.org"
		);

		jsonObjectBuilder.nestedField(
			"view", "@type"
		).arrayValue(
		).add(
			"PartialCollectionView"
		);

		jsonObjectBuilder.field(
			"@type"
		).arrayValue(
		).add(
			"Collection"
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T model, Class<T> modelClass,
		RequestInfo requestInfo) {

		pageJSONObjectBuilder.field(
			"members"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	@Reference
	private JSONLDSingleModelMessageMapper<T> _jsonLDSingleModelMessageMapper;

}