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

package com.liferay.apio.architect.impl.message.json.hal;

import com.liferay.apio.architect.impl.list.FunctionalList;
import com.liferay.apio.architect.impl.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.impl.message.json.PageMessageMapper;
import com.liferay.apio.architect.impl.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.impl.wiring.osgi.manager.representable.RepresentableManager;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.single.model.SingleModel;

import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Represents collection pages in <a
 * href="http://stateless.co/hal_specification.html">HAL </a> format.
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component
public class HALPageMessageMapper<T> implements PageMessageMapper<T> {

	@Override
	public String getMediaType() {
		return "application/hal+json";
	}

	@Override
	public Optional<SingleModelMessageMapper<T>>
		getSingleModelMessageMapperOptional() {

		return Optional.of(_singleModelMessageMapper);
	}

	@Override
	public void mapCollectionURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "collection", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapCurrentPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "self", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "first", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			"total"
		).numberValue(
			totalCount
		);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "last", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "next", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void mapPageCount(JSONObjectBuilder jsonObjectBuilder, int count) {
		jsonObjectBuilder.field(
			"count"
		).numberValue(
			count
		);
	}

	@Override
	public void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			"_links", "prev", "href"
		).stringValue(
			url
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel) {

		Optional<Representor<T>> optional =
			representableManager.getRepresentorOptional(
				singleModel.getResourceName());

		optional.map(
			Representor::getTypes
		).ifPresent(
			types -> pageJSONObjectBuilder.nestedField(
				"_embedded", types.get(0)
			).arrayValue(
			).add(
				itemJSONObjectBuilder
			)
		);
	}

	@Override
	public void onFinishNestedCollection(
		JSONObjectBuilder singleModelJSONObjectBuilder,
		JSONObjectBuilder collectionJsonObjectBuilder, String fieldName,
		List<?> list, FunctionalList<String> embeddedPathElements) {

		singleModelJSONObjectBuilder.field(
			"_embedded"
		).field(
			fieldName
		).objectValue(
			collectionJsonObjectBuilder
		);
	}

	@Override
	public void onFinishNestedCollectionItem(
		JSONObjectBuilder collectionJsonObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<?> singleModel) {

		collectionJsonObjectBuilder.field(
			"_embedded"
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	@Reference
	protected RepresentableManager representableManager;

	private final SingleModelMessageMapper<T> _singleModelMessageMapper =
		new HALSingleModelMessageMapper<>();

}