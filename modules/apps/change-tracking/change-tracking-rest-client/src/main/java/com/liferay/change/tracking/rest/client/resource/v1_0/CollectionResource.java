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

package com.liferay.change.tracking.rest.client.resource.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.client.http.HttpInvoker;
import com.liferay.change.tracking.rest.client.pagination.Page;
import com.liferay.change.tracking.rest.client.pagination.Pagination;
import com.liferay.change.tracking.rest.client.serdes.v1_0.CollectionSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Mate Thurzo
 * @generated
 */
@Generated("")
public class CollectionResource {

	public static Page<Collection> getCollectionsPage(
			Long companyId, String type, Long userId, Pagination pagination,
			String sortString)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = getCollectionsPageHttpResponse(
			companyId, type, userId, pagination, sortString);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, CollectionSerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse getCollectionsPageHttpResponse(
			Long companyId, String type, Long userId, Pagination pagination,
			String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (companyId != null) {
			httpInvoker.parameter("companyId", String.valueOf(companyId));
		}

		if (type != null) {
			httpInvoker.parameter("type", String.valueOf(type));
		}

		if (userId != null) {
			httpInvoker.parameter("userId", String.valueOf(userId));
		}

		if (pagination != null) {
			httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
			httpInvoker.parameter(
				"pageSize", String.valueOf(pagination.getPageSize()));
		}

		if (sortString != null) {
			httpInvoker.parameter("sort", sortString);
		}

		httpInvoker.path(
			"http://localhost:8080/o/change-tracking/v1.0/collections");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Collection postCollection(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.CollectionUpdate
				collectionUpdate)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = postCollectionHttpResponse(
			companyId, userId, collectionUpdate);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return CollectionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse postCollectionHttpResponse(
			Long companyId, Long userId,
			com.liferay.change.tracking.rest.client.dto.v1_0.CollectionUpdate
				collectionUpdate)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(collectionUpdate.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		if (companyId != null) {
			httpInvoker.parameter("companyId", String.valueOf(companyId));
		}

		if (userId != null) {
			httpInvoker.parameter("userId", String.valueOf(userId));
		}

		httpInvoker.path(
			"http://localhost:8080/o/change-tracking/v1.0/collections");

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Collection getCollection(Long collectionId) throws Exception {
		HttpInvoker.HttpResponse httpResponse = getCollectionHttpResponse(
			collectionId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return CollectionSerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getCollectionHttpResponse(
			Long collectionId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/change-tracking/v1.0/collections/{collectionId}",
			collectionId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		CollectionResource.class.getName());

}