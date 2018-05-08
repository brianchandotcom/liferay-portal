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
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_PROPERTY;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_READABLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_REQUIRED;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_SUPPORTED_PROPERTY;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TITLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_VOCAB;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.FIELD_NAME_WRITEABLE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.MEDIA_TYPE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_CLASS;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.TYPE_SUPPORTED_PROPERTY;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_HYDRA_PROFILE;
import static com.liferay.apio.architect.message.json.ld.internal.JSONLDConstants.URL_SCHEMA_ORG;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.FormField;
import com.liferay.apio.architect.message.json.FormMessageMapper;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;

import javax.ws.rs.core.HttpHeaders;

import org.osgi.service.component.annotations.Component;

/**
 * Represents forms in JSON-LD + Hydra format.
 *
 * <p>
 * For more information, see <a href="https://json-ld.org/">JSON-LD </a> and <a
 * href="https://www.hydra-cg.com/">Hydra </a> .
 * </p>
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class JSONLDFormMessageMapper implements FormMessageMapper {

	@Override
	public String getMediaType() {
		return MEDIA_TYPE;
	}

	@Override
	public void mapFormDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {

		jsonObjectBuilder.field(
			FIELD_NAME_DESCRIPTION
		).stringValue(
			description
		);
	}

	@Override
	public void mapFormField(
		JSONObjectBuilder jsonObjectBuilder, FormField formField) {

		jsonObjectBuilder.field(
			FIELD_NAME_SUPPORTED_PROPERTY
		).arrayValue(
		).add(
			builder -> {
				builder.field(
					FIELD_NAME_TYPE
				).stringValue(
					TYPE_SUPPORTED_PROPERTY
				);

				builder.field(
					FIELD_NAME_PROPERTY
				).stringValue(
					formField.name
				);

				builder.field(
					FIELD_NAME_READABLE
				).booleanValue(
					false
				);

				builder.field(
					FIELD_NAME_REQUIRED
				).booleanValue(
					formField.required
				);

				builder.field(
					FIELD_NAME_WRITEABLE
				).booleanValue(
					true
				);
			}
		);
	}

	@Override
	public void mapFormTitle(
		JSONObjectBuilder jsonObjectBuilder, String title) {

		jsonObjectBuilder.field(
			FIELD_NAME_TITLE
		).stringValue(
			title
		);
	}

	@Override
	public void mapFormURL(JSONObjectBuilder jsonObjectBuilder, String url) {
		jsonObjectBuilder.field(
			FIELD_NAME_ID
		).stringValue(
			url
		);
	}

	@Override
	public void onFinish(
		JSONObjectBuilder jsonObjectBuilder, Form form,
		HttpHeaders httpHeaders) {

		jsonObjectBuilder.field(
			FIELD_NAME_TYPE
		).stringValue(
			TYPE_CLASS
		);

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
	}

}