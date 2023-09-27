/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.task.progress;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.internal.util.ZipInputStreamUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

/**
 * @author Matija Petanjek
 */
public class JSONBatchEngineTaskProgressImpl
	implements BatchEngineTaskProgress {

	@Override
	public int getTotalItemsCount(InputStream inputStream) {
		int totalItemsCount = 0;

		JsonParser jsonParser = null;

		try {
			inputStream = ZipInputStreamUtil.asZipInputStream(inputStream);

			jsonParser = _jsonFactory.createParser(inputStream);

			jsonParser.nextToken();

			while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
				_objectMapper.readValue(
					jsonParser,
					new TypeReference<Map<String, Object>>() {
					});

				totalItemsCount++;
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get total items count", exception);

			totalItemsCount = 0;
		}
		finally {
			try {
				if (jsonParser != null) {
					jsonParser.close();
				}
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return totalItemsCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONBatchEngineTaskProgressImpl.class);

	private static final JsonFactory _jsonFactory = new JsonFactory();
	private static final ObjectMapper _objectMapper = new ObjectMapper();

}