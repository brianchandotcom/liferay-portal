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

package com.liferay.data.engine.rest.client.resource.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataRecordSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataRecordResource {

	public static Page<DataRecord> getDataDefinitionDataRecordsPage(
			Long dataDefinitionId, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getDataDefinitionDataRecordsPageHttpResponse(
				dataDefinitionId, pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, DataRecordSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getDataDefinitionDataRecordsPageHttpResponse(
				Long dataDefinitionId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-records",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataRecord postDataDefinitionDataRecord(
			Long dataDefinitionId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postDataDefinitionDataRecordHttpResponse(
				dataDefinitionId, dataRecord);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataRecordSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postDataDefinitionDataRecordHttpResponse(
				Long dataDefinitionId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(dataRecord.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-records",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<DataRecord> getDataRecordCollectionDataRecordsPage(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getDataRecordCollectionDataRecordsPageHttpResponse(
				dataRecordCollectionId, pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, DataRecordSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getDataRecordCollectionDataRecordsPageHttpResponse(
				Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-record-collections/{dataRecordCollectionId}/data-records",
			dataRecordCollectionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataRecord postDataRecordCollectionDataRecord(
			Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postDataRecordCollectionDataRecordHttpResponse(
				dataRecordCollectionId, dataRecord);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataRecordSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postDataRecordCollectionDataRecordHttpResponse(
				Long dataRecordCollectionId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(dataRecord.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-record-collections/{dataRecordCollectionId}/data-records",
			dataRecordCollectionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static String getDataRecordCollectionDataRecordExport(
			Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getDataRecordCollectionDataRecordExportHttpResponse(
				dataRecordCollectionId, pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return content;
	}

	public static HttpInvoker.HttpResponse
			getDataRecordCollectionDataRecordExportHttpResponse(
				Long dataRecordCollectionId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-record-collections/{dataRecordCollectionId}/data-records/export",
			dataRecordCollectionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static void deleteDataRecord(Long dataRecordId) throws Exception {
		HttpInvoker.HttpResponse httpResponse = deleteDataRecordHttpResponse(
			dataRecordId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse deleteDataRecordHttpResponse(
			Long dataRecordId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-records/{dataRecordId}",
			dataRecordId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataRecord getDataRecord(Long dataRecordId) throws Exception {
		HttpInvoker.HttpResponse httpResponse = getDataRecordHttpResponse(
			dataRecordId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataRecordSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getDataRecordHttpResponse(
			Long dataRecordId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-records/{dataRecordId}",
			dataRecordId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataRecord putDataRecord(
			Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = putDataRecordHttpResponse(
			dataRecordId, dataRecord);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataRecordSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse putDataRecordHttpResponse(
			Long dataRecordId, DataRecord dataRecord)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(dataRecord.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-records/{dataRecordId}",
			dataRecordId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		DataRecordResource.class.getName());

}