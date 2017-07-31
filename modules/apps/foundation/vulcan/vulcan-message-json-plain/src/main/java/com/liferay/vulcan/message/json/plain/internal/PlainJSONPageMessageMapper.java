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
import com.liferay.vulcan.message.RequestInfo;
import com.liferay.vulcan.message.json.JSONObjectBuilder;
import com.liferay.vulcan.message.json.PageMessageMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Adds Vulcan the ability to represent collection pages in plain JSON.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true)
public class PlainJSONPageMessageMapper<T> implements PageMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/json";
	}

	@Override
	public void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.field(
			"collection"
		).value(
			url
		);
	}

	@Override
	public void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		_plainJSONSingleModelMessageMapper.mapSelfURL(jsonObjectBuilder, url);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"pages", "first"
		).value(
			url
		);
	}

	@Override
	public void mapItemEmbeddedResourceField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		Object value) {

		_plainJSONSingleModelMessageMapper.mapEmbeddedResourceField(
			itemJSONObjectBuilder, embeddedPathElements, fieldName, value);
	}

	@Override
	public void mapItemEmbeddedResourceLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String fieldName,
		String url) {

		_plainJSONSingleModelMessageMapper.mapEmbeddedResourceLink(
			itemJSONObjectBuilder, embeddedPathElements, fieldName, url);
	}

	@Override
	public void mapItemEmbeddedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		_plainJSONSingleModelMessageMapper.mapEmbeddedResourceURL(
			itemJSONObjectBuilder, embeddedPathElements, url);
	}

	@Override
	public void mapItemField(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName,
		Object value) {

		_plainJSONSingleModelMessageMapper.mapField(
			itemJSONObjectBuilder, fieldName, value);
	}

	@Override
	public void mapItemLink(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String fieldName, String url) {

		_plainJSONSingleModelMessageMapper.mapLink(
			itemJSONObjectBuilder, fieldName, url);
	}

	@Override
	public void mapItemLinkedResourceURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder,
		FunctionalList<String> embeddedPathElements, String url) {

		_plainJSONSingleModelMessageMapper.mapLinkedResourceURL(
			itemJSONObjectBuilder, embeddedPathElements, url);
	}

	@Override
	public void mapItemSelfURL(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, String url) {

		_plainJSONSingleModelMessageMapper.mapSelfURL(
			itemJSONObjectBuilder, url);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			"totalNumberOfItems"
		).value(
			totalCount
		);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"pages", "last"
		).value(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"pages", "next"
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
			"pages", "prev"
		).value(
			url
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, T item, Class<T> modelClass,
		RequestInfo requestInfo) {

		pageJSONObjectBuilder.field(
			"elements"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	@Reference
	private PlainJSONSingleModelMessageMapper
		_plainJSONSingleModelMessageMapper;

}