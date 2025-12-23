/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.util;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.cxf.jaxrs.utils.ExceptionUtils;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.rs.security.oauth2.common.OAuthError;

/**
 * @author Jorge García Jiménez
 */
public class OAuth2ErrorUtil {

	public static void reportInvalidRequestError(
			String description, String error, Response.Status status)
		throws WebApplicationException {

		throw ExceptionUtils.toWebApplicationException(
			null,
			JAXRSUtils.toResponseBuilder(
				status.getStatusCode()
			).entity(
				new OAuthError(error, description)
			).type(
				MediaType.APPLICATION_JSON
			).build());
	}

}