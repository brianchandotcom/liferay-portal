/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;

/**
 * @author Alejandro Tardín
 */
public class OpenAPIJsonSerializer extends JsonSerializer<OpenAPI> {

	@Override
	public void serialize(
		OpenAPI openAPI, JsonGenerator jsonGenerator,
		SerializerProvider serializerProvider) {

		try {
			jsonGenerator.writeRawValue(Json.pretty(openAPI));
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

}