/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.web.client;

import com.liferay.osb.faro.engine.client.exception.DuplicateEntryException;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.InvalidFilterException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpStatus;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * @author Shinn Lok
 */
public class ResponseErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse)
		throws IOException {

		int statusCode = clientHttpResponse.getRawStatusCode();

		if (statusCode < 400) {
			super.handleError(clientHttpResponse);

			return;
		}

		try (InputStream inputStream = clientHttpResponse.getBody()) {
			String input = null;

			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

			StringBuilder responseCreate = new StringBuilder();

			while ((input = bufferedReader.readLine()) != null) {
				responseCreate.append(input);
			}

			String response = responseCreate.toString();

			if (statusCode == HttpStatus.SC_CONFLICT) {
				throw new DuplicateEntryException(response);
			}

			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				throw new NoSuchEntryException(response);
			}

			if (statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
				throw new InvalidFilterException(response);
			}

			throw new FaroEngineClientException(response);
		}
	}

}