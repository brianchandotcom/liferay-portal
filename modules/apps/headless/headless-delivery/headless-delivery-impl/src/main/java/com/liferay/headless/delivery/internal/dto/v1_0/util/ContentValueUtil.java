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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.util.Base64;

import java.io.InputStream;

import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class ContentValueUtil {

	public static String toContentValue(
			String field, InputStream inputStream,
			Optional<UriInfo> uriInfoOptional)
		throws Exception {

		boolean nestField = uriInfoOptional.map(
			UriInfo::getQueryParameters
		).map(
			parameters -> parameters.getFirst("nestedFields")
		).map(
			fields -> fields.contains(field)
		).orElse(
			false
		);

		if (nestField) {
			return Base64.encode(StreamUtil.toByteArray(inputStream));
		}

		return null;
	}

}