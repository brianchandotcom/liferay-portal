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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_CONTEXT;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_FIRST;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_ID;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_LAST;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_MEMBER;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_NEXT;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_NUMBER_OF_ITEMS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_PREVIOUS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TOTAL_ITEMS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_VIEW;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_VOCAB;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_COLLECTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_PARTIAL_COLLECTION_VIEW;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_HYDRA_PROFILE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_SCHEMA_ORG;

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
		return MEDIA_TYPE;
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
			FIELD_NAME_VIEW, FIELD_NAME_ID
		).stringValue(
			url
		);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			FIELD_NAME_VIEW, FIELD_NAME_FIRST
		).stringValue(
			url
		);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			FIELD_NAME_TOTAL_ITEMS
		).numberValue(
			totalCount
		);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			FIELD_NAME_VIEW, FIELD_NAME_LAST
		).stringValue(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			FIELD_NAME_VIEW, FIELD_NAME_NEXT
		).stringValue(
			url
		);
	}

	@Override
	public void mapPageCount(JSONObjectBuilder jsonObjectBuilder, int count) {
		jsonObjectBuilder.field(
			FIELD_NAME_NUMBER_OF_ITEMS
		).numberValue(
			count
		);
	}

	@Override
	public void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			FIELD_NAME_VIEW, FIELD_NAME_PREVIOUS
		).stringValue(
			url
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.field(
			FIELD_NAME_CONTEXT
		).arrayValue(
		).add(
			builder -> builder.field(
				FIELD_NAME_VOCAB
			).stringValue(
				URL_SCHEMA_ORG
			)
		);

		jsonObjectBuilder.field(
			FIELD_NAME_CONTEXT
		).arrayValue(
		).addString(
			URL_HYDRA_PROFILE
		);

		jsonObjectBuilder.nestedField(
			FIELD_NAME_VIEW, FIELD_NAME_TYPE
		).arrayValue(
		).addString(
			TYPE_PARTIAL_COLLECTION_VIEW
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).arrayValue(
		).addString(
			TYPE_COLLECTION
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {

		pageJSONObjectBuilder.field(
			FIELD_NAME_MEMBER
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	private final SingleModelMessageMapper<T> _singleModelMessageMapper =
		new JSONLDSingleModelMessageMapper<>();

}