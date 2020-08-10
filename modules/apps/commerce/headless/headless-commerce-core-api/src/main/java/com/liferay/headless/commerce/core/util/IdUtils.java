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

package com.liferay.headless.commerce.core.util;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Zoltán Takács
 */
public class IdUtils {

	public static String getExternalReferenceCodeFromId(String id) {
		if ((id != null) && (id.length() >= 5) && id.startsWith("ext-")) {
			return id.substring(4);
		}

		throw new ClientErrorException(
			"Unable to parse {Id} parameter:" + id, Response.Status.CONFLICT);
	}

	public static boolean isLocalPK(String id) {
		try {
			NumberFormat numberFormat = NumberFormat.getInstance();

			numberFormat.parse(id);
		}
		catch (ParseException pe) {
			return false;
		}

		return true;
	}

	private IdUtils() {
	}

}