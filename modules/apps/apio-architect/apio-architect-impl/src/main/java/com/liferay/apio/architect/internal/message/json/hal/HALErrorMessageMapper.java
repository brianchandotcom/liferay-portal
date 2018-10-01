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

package com.liferay.apio.architect.internal.message.json.hal;

import com.liferay.apio.architect.internal.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.internal.message.json.JSONObjectBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * Represents errors in HAL format.
 *
 * <p>
 * For more information, see <a
 * href="http://stateless.co/hal_specification.html">HAL </a>.
 * </p>
 *
 * @author Julio Camarero
 * @review
 */
@Component(service = ErrorMessageMapper.class)
public class HALErrorMessageMapper implements ErrorMessageMapper {

	@Override
	public String getMediaType() {
		return "application/hal+json";
	}

	@Override
	public void mapDescription(
		JSONObjectBuilder jsonObjectBuilder, String description) {

		jsonObjectBuilder.field(
			"description"
		).stringValue(
			description
		);
	}

	@Override
	public void mapStatusCode(
		JSONObjectBuilder jsonObjectBuilder, Integer statusCode) {

		jsonObjectBuilder.field(
			"statusCode"
		).numberValue(
			statusCode
		);
	}

	@Override
	public void mapTitle(JSONObjectBuilder jsonObjectBuilder, String title) {
		jsonObjectBuilder.field(
			"title"
		).stringValue(
			title
		);
	}

}