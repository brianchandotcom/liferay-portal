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

package com.liferay.bulk.rest.client.resource.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.Keyword;
import com.liferay.bulk.rest.client.http.HttpInvoker;
import com.liferay.bulk.rest.client.pagination.Page;
import com.liferay.bulk.rest.client.serdes.v1_0.KeywordSerDes;

import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class KeywordResource {

	public static void patchKeywordBatch(
			com.liferay.bulk.rest.client.dto.v1_0.KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = patchKeywordBatchHttpResponse(
			keywordBulkSelection);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse patchKeywordBatchHttpResponse(
			com.liferay.bulk.rest.client.dto.v1_0.KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(keywordBulkSelection.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path("http://localhost:8080/o/bulk/v1.0/keywords/batch");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static void putKeywordBatch(
			com.liferay.bulk.rest.client.dto.v1_0.KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = putKeywordBatchHttpResponse(
			keywordBulkSelection);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse putKeywordBatchHttpResponse(
			com.liferay.bulk.rest.client.dto.v1_0.KeywordBulkSelection
				keywordBulkSelection)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(keywordBulkSelection.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path("http://localhost:8080/o/bulk/v1.0/keywords/batch");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<Keyword> postKeywordsCommonPage(
			com.liferay.bulk.rest.client.dto.v1_0.DocumentBulkSelection
				documentBulkSelection)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postKeywordsCommonPageHttpResponse(documentBulkSelection);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, KeywordSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse postKeywordsCommonPageHttpResponse(
			com.liferay.bulk.rest.client.dto.v1_0.DocumentBulkSelection
				documentBulkSelection)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(documentBulkSelection.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path("http://localhost:8080/o/bulk/v1.0/keywords/common");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		KeywordResource.class.getName());

}