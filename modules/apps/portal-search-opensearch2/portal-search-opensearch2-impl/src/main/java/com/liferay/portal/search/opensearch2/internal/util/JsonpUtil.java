/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;

import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.json.JsonpSerializable;

/**
 * @author Petteri Karttunen
 */
public class JsonpUtil {

	public static JsonpMapper getJsonpMapper() {
		OpenSearchConnectionManager openSearchConnectionManager =
			_openSearchConnectionManagerSnapshot.get();

		return openSearchConnectionManager.getJsonpMapper(null);
	}

	public static void logInfoResponse(
		Log log, JsonpSerializable jsonpSerializable) {

		if (jsonpSerializable == null) {
			return;
		}

		try {
			log.info(toString(jsonpSerializable));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public static String toString(JsonpSerializable jsonpSerializable) {
		JsonpMapper jsonpMapper = getJsonpMapper();

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		StringWriter stringWriter = new StringWriter();

		try (JsonGenerator generator = jsonProvider.createGenerator(
				stringWriter)) {

			jsonpSerializable.serialize(generator, jsonpMapper);

			generator.flush();

			return stringWriter.toString();
		}
		catch (Exception exception) {
			_log.error(exception);

			return exception.getMessage();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JsonpUtil.class);

	private static final Snapshot<OpenSearchConnectionManager>
		_openSearchConnectionManagerSnapshot = new Snapshot<>(
			MappingsUtil.class, OpenSearchConnectionManager.class, null, true);

}