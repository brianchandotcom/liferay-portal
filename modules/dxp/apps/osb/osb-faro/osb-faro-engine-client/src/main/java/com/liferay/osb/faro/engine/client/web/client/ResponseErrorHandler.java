/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.web.client;

import com.liferay.osb.faro.engine.client.exception.DuplicateEntryException;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.InvalidFilterException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
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
			String response = IOUtils.toString(inputStream);

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