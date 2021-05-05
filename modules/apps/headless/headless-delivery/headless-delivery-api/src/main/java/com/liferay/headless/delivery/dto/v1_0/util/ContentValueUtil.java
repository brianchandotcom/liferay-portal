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

package com.liferay.headless.delivery.dto.v1_0.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;

import java.io.InputStream;

import java.util.Optional;

import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class ContentValueUtil {

	public static String toContentValue(
		String field, UnsafeSupplier<InputStream, Exception> unsafeSupplier,
		Optional<UriInfo> uriInfoOptional) {

		if (uriInfoOptional.map(
				UriInfo::getQueryParameters
			).map(
				parameters -> parameters.getFirst("nestedFields")
			).map(
				fields -> fields.contains(field)
			).orElse(
				false
			)) {

			try {
				return Base64.encode(
					StreamUtil.toByteArray(unsafeSupplier.get()));
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentValueUtil.class);

}