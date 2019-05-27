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

import com.liferay.data.engine.rest.client.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataDefinitionSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataDefinitionResource {

	public static void deleteDataDefinition(Long dataDefinitionId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			deleteDataDefinitionHttpResponse(dataDefinitionId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse deleteDataDefinitionHttpResponse(
			Long dataDefinitionId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = getDataDefinitionHttpResponse(
			dataDefinitionId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataDefinitionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getDataDefinitionHttpResponse(
			Long dataDefinitionId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataDefinition putDataDefinition(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = putDataDefinitionHttpResponse(
			dataDefinitionId, dataDefinition);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataDefinitionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse putDataDefinitionHttpResponse(
			Long dataDefinitionId, DataDefinition dataDefinition)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(dataDefinition.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static void postDataDefinitionDataDefinitionPermission(
			Long dataDefinitionId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.
				DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postDataDefinitionDataDefinitionPermissionHttpResponse(
				dataDefinitionId, operation, dataDefinitionPermission);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse
			postDataDefinitionDataDefinitionPermissionHttpResponse(
				Long dataDefinitionId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			dataDefinitionPermission.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		if (operation != null) {
			httpInvoker.parameter("operation", String.valueOf(operation));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-definition-permissions",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static void postSiteDataDefinitionPermission(
			Long siteId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.
				DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postSiteDataDefinitionPermissionHttpResponse(
				siteId, operation, dataDefinitionPermission);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse
			postSiteDataDefinitionPermissionHttpResponse(
				Long siteId, String operation,
				com.liferay.data.engine.rest.client.dto.v1_0.
					DataDefinitionPermission dataDefinitionPermission)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			dataDefinitionPermission.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		if (operation != null) {
			httpInvoker.parameter("operation", String.valueOf(operation));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-definition-permissions",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<DataDefinition> getSiteDataDefinitionsPage(
			Long siteId, String keywords, Pagination pagination)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getSiteDataDefinitionsPageHttpResponse(
				siteId, keywords, pagination);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, DataDefinitionSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getSiteDataDefinitionsPageHttpResponse(
				Long siteId, String keywords, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (keywords != null) {
			httpInvoker.parameter("keywords", String.valueOf(keywords));
		}

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-definitions",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataDefinition postSiteDataDefinition(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postSiteDataDefinitionHttpResponse(siteId, dataDefinition);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataDefinitionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse postSiteDataDefinitionHttpResponse(
			Long siteId, DataDefinition dataDefinition)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(dataDefinition.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-definitions",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static DataDefinition getSiteDataDefinition(
			Long siteId, String dataDefinitionKey)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getSiteDataDefinitionHttpResponse(siteId, dataDefinitionKey);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return DataDefinitionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getSiteDataDefinitionHttpResponse(
			Long siteId, String dataDefinitionKey)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-definitions/{dataDefinitionKey}",
			siteId, dataDefinitionKey);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		DataDefinitionResource.class.getName());

}