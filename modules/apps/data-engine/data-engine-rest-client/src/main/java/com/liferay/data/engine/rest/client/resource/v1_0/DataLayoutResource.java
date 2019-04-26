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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.serdes.v1_0.DataLayoutSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutResource {

	public Page<DataLayout> getDataDefinitionDataLayoutsPage(
			Long dataDefinitionId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-layouts",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		return Page.of(content, DataLayoutSerDes::toDTO);
	}

	public DataLayout postDataDefinitionDataLayout(
			Long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			DataLayoutSerDes.toJSON(dataLayout), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-definitions/{dataDefinitionId}/data-layouts",
			dataDefinitionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return DataLayoutSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public void postDataLayoutDataLayoutPermission(
			Long dataLayoutId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission
				dataLayoutPermission)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-layout/{dataLayoutId}/data-layout-permissions",
			dataLayoutId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());
	}

	public void deleteDataLayout(Long dataLayoutId) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
			dataLayoutId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());
	}

	public DataLayout getDataLayout(Long dataLayoutId) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
			dataLayoutId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return DataLayoutSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public DataLayout putDataLayout(Long dataLayoutId, DataLayout dataLayout)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			DataLayoutSerDes.toJSON(dataLayout), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/data-layouts/{dataLayoutId}",
			dataLayoutId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		try {
			return DataLayoutSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public Page<DataLayout> getSiteDataLayoutPage(
			Long siteId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-layout",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());

		return Page.of(content, DataLayoutSerDes::toDTO);
	}

	public void postSiteDataLayoutPermission(
			Long siteId, String operation,
			com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPermission
				dataLayoutPermission)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/data-engine/v1.0/sites/{siteId}/data-layout-permissions",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine("HTTP response status: " + httpResponse.getStatus());
	}

	private static final Logger _logger = Logger.getLogger(
		DataLayoutResource.class.getName());

}