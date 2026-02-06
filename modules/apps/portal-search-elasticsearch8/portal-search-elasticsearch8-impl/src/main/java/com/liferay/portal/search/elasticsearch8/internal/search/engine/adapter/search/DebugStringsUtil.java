/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.JsonpSerializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;

import java.io.StringWriter;

/**
 * @author Bryan Engler
 */
public class DebugStringsUtil {

	public static String getPrettyPrintedSearchRequestString(
		ElasticsearchClient elasticsearchClient,
		JsonpSerializable jsonpSerializable) {

		String searchRequestString = getSearchRequestString(
			elasticsearchClient, jsonpSerializable);

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			JsonNode jsonNode = objectMapper.readTree(searchRequestString);

			searchRequestString = objectMapper.writeValueAsString(jsonNode);
		}
		catch (JsonProcessingException jsonProcessingException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonProcessingException);
			}
		}

		return searchRequestString;
	}

	public static String getSearchRequestString(
		ElasticsearchClient elasticsearchClient,
		JsonpSerializable jsonpSerializable) {

		JsonpMapper jsonpMapper = elasticsearchClient._jsonpMapper();

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		StringWriter stringWriter = new StringWriter();

		try (JsonGenerator jsonGenerator = jsonProvider.createGenerator(
				stringWriter)) {

			jsonpMapper.serialize(jsonpSerializable, jsonGenerator);
		}

		return stringWriter.toString();
	}

	public static String getStackTraceString() {
		StringBundler sb = new StringBundler(100);

		Thread thread = Thread.currentThread();

		for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
			sb.append(stackTraceElement.toString());
			sb.append(" @@ ");

			if (sb.index() == 100) {
				break;
			}
		}

		sb.setIndex(sb.index() - 1);

		sb.setStringAt(StringPool.BLANK, 0);
		sb.setStringAt(StringPool.BLANK, 1);
		sb.setStringAt(StringPool.BLANK, 2);
		sb.setStringAt(StringPool.BLANK, 3);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DebugStringsUtil.class);

}