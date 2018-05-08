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

import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_DESCRIPTION;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_STATUS_CODE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TITLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;

import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * Represents errors in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class JSONLDErrorMessageMapper implements ErrorMessageMapper {

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
	public void mapStatusCode(
		JSONObjectBuilder jsonObjectBuilder, Integer statusCode) {

		jsonObjectBuilder.field(
			FIELD_NAME_STATUS_CODE
		).numberValue(
			statusCode
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
	public void mapType(JSONObjectBuilder jsonObjectBuilder, String type) {
		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			type
		);
	}

}