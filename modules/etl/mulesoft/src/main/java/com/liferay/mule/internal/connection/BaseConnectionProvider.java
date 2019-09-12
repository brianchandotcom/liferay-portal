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

package com.liferay.mule.internal.connection;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

/**
 * @author Matija Petanjek
 */
public abstract class BaseConnectionProvider
	implements ConnectionProvider<LiferayConnection> {

	@Override
	public ConnectionValidationResult validate(
		LiferayConnection liferayConnection) {

		ConnectionValidationResult connectionValidationResult = null;

		try {
			HttpResponse httpResponse = liferayConnection.getOpenAPISpec();

			int statusCode = httpResponse.getStatusCode();

			if ((statusCode >= 200) && (statusCode < 300)) {
				connectionValidationResult =
					ConnectionValidationResult.success();
			}
			else {
				String errorMessage =
					HttpConstants.HttpStatus.getReasonPhraseForStatusCode(
						httpResponse.getStatusCode());

				connectionValidationResult = ConnectionValidationResult.failure(
					errorMessage + " (" + httpResponse.getStatusCode() + ")",
					new ConnectionException(
						"Unable to connect to Liferay Instance"));
			}
		}
		catch (IOException | TimeoutException e) {
			connectionValidationResult = ConnectionValidationResult.failure(
				e.getMessage(), e);
		}

		return connectionValidationResult;
	}

}