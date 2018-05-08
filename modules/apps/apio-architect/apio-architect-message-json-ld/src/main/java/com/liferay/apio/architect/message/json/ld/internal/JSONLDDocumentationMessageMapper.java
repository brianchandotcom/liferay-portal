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
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_DESCRIPTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_ID;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TITLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_API_DOCUMENTATION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_HYDRA_PROFILE;

import com.liferay.apio.architect.documentation.Documentation;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;

import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.annotations.Component;

/**
 * Represents documentation in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class JSONLDDocumentationMessageMapper
	implements DocumentationMessageMapper {

	@Override
	public String getMediaType() {
		return MEDIA_TYPE;
	}

	@Override
	public void mapDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {

		jsonObjectBuilder.field(
			FIELD_NAME_DESCRIPTION
		).stringValue(
			description
		);
	}

	@Override
	public void mapTitle(JSONObjectBuilder jsonObjectBuilder, String title) {
		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			title
		);
	}

	@Override
	public void onStart(
		JSONObjectBuilder jsonObjectBuilder, Documentation documentation,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.field(
			FIELD_NAME_CONTEXT
		).stringValue(
			URL_HYDRA_PROFILE
		);

		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			"http://api.example.com/doc/"
		);

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_API_DOCUMENTATION
		);
	}

}