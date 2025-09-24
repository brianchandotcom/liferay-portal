/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.util;

import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.JsonpSerializable;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionManager;

import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;

/**
 * @author Petteri Karttunen
 */
public class JsonpUtil {

	public static JsonpMapper getJsonpMapper() {
		ElasticsearchConnectionManager elasticsearchConnectionManager =
			_elasticsearchConnectionManagerSnapshot.get();

		if (elasticsearchConnectionManager == null) {
			return new JacksonJsonpMapper();
		}

		return elasticsearchConnectionManager.getJsonpMapper(null);
	}

	public static void logBulkResponse(BulkResponse bulkResponse, Log log) {
		if (bulkResponse.errors()) {
			log.error(toString(bulkResponse));
		}

		logInfoResponse(bulkResponse, log);
	}

	public static void logInfoResponse(
		JsonpSerializable jsonpSerializable, Log log) {

		if (!log.isInfoEnabled() || (jsonpSerializable == null)) {
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

	private static final Snapshot<ElasticsearchConnectionManager>
		_elasticsearchConnectionManagerSnapshot = new Snapshot<>(
			JsonpUtil.class, ElasticsearchConnectionManager.class, null, true);

}