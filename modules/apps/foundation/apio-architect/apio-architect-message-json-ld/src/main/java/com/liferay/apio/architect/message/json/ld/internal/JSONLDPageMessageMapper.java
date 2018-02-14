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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.CONTEXT;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIRST;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.HYDRA;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.HYDRA_COLLECTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.HYDRA_PARTIAL_COLLECTION_VIEW;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.HYDRA_PROFILE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.ID;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.LAST;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEMBER;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.NEXT;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.NUMBER_OF_ITEMS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.PREVIOUS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.SCHEMA_ORG;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TOTAL_ITEMS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.VIEW;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.VOCAB;

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
			VIEW, ID
		).stringValue(
			url
		);
	}

	@Override
	public void mapFirstPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			VIEW, FIRST
		).stringValue(
			url
		);
	}

	@Override
	public void mapItemTotalCount(
		JSONObjectBuilder jsonObjectBuilder, int totalCount) {

		jsonObjectBuilder.field(
			TOTAL_ITEMS
		).numberValue(
			totalCount
		);
	}

	@Override
	public void mapLastPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			VIEW, LAST
		).stringValue(
			url
		);
	}

	@Override
	public void mapNextPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			VIEW, NEXT
		).stringValue(
			url
		);
	}

	@Override
	public void mapPageCount(JSONObjectBuilder jsonObjectBuilder, int count) {
		jsonObjectBuilder.field(
			NUMBER_OF_ITEMS
		).numberValue(
			count
		);
	}

	@Override
	public void mapPreviousPageURL(
		JSONObjectBuilder jsonObjectBuilder, String url) {

		jsonObjectBuilder.nestedField(
			VIEW, PREVIOUS
		).stringValue(
			url
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Page<T> page,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.nestedField(
			CONTEXT, HYDRA
		).stringValue(
			HYDRA_PROFILE
		);

		jsonObjectBuilder.nestedField(
			CONTEXT, VOCAB
		).stringValue(
			SCHEMA_ORG
		);

		jsonObjectBuilder.nestedField(
			VIEW, TYPE
		).arrayValue(
		).addString(
			HYDRA_PARTIAL_COLLECTION_VIEW
		);

		jsonObjectBuilder.field(
			TYPE
		).arrayValue(
		).addString(
			HYDRA_COLLECTION
		);
	}

	@Override
	public void onFinishItem(
		JSONObjectBuilder pageJSONObjectBuilder,
		JSONObjectBuilder itemJSONObjectBuilder, SingleModel<T> singleModel,
		HttpHeaders httpHeaders) {

		pageJSONObjectBuilder.field(
			MEMBER
		).arrayValue(
		).add(
			itemJSONObjectBuilder
		);
	}

	private final SingleModelMessageMapper<T> _singleModelMessageMapper =
		new JSONLDSingleModelMessageMapper<>();

}