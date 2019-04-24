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

package com.liferay.headless.delivery.client.resource.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentResource {

	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			Long contentStructureId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("filter", filterString);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.parameter("sort", sortString);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/content-structures/{contentStructureId}/structured-contents",
			contentStructureId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), StructuredContentSerDes::toDTO);
	}

	public Page<StructuredContent> getSiteStructuredContentsPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("filter", filterString);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.parameter("sort", sortString);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), StructuredContentSerDes::toDTO);
	}

	public StructuredContent postSiteStructuredContent(
			Long siteId, StructuredContent structuredContent)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentSerDes.toJSON(structuredContent),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContent getSiteStructuredContentByKey(
			Long siteId, String key)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-key/{key}",
			siteId, key);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContent getSiteStructuredContentByUuid(
			Long siteId, String uuid)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-contents/by-uuid/{uuid}",
			siteId, uuid);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				Long structuredContentFolderId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("filter", filterString);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.parameter("sort", sortString);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), StructuredContentSerDes::toDTO);
	}

	public StructuredContent postStructuredContentFolderStructuredContent(
			Long structuredContentFolderId, StructuredContent structuredContent)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentSerDes.toJSON(structuredContent),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}/structured-contents",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public void deleteStructuredContent(Long structuredContentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public StructuredContent getStructuredContent(Long structuredContentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContent patchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentSerDes.toJSON(structuredContent),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentSerDes.toJSON(structuredContent),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public void deleteStructuredContentMyRating(Long structuredContentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			getStructuredContentMyRating(Long structuredContentId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			postStructuredContentMyRating(
				Long structuredContentId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public com.liferay.headless.delivery.client.dto.v1_0.Rating
			putStructuredContentMyRating(
				Long structuredContentId,
				com.liferay.headless.delivery.client.dto.v1_0.Rating rating)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/my-rating",
			structuredContentId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return com.liferay.headless.delivery.client.serdes.v1_0.
				RatingSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public String getStructuredContentRenderedContentTemplate(
			Long structuredContentId, Long templateId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-contents/{structuredContentId}/rendered-content/{templateId}",
			structuredContentId, templateId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	private static final Logger _logger = Logger.getLogger(
		StructuredContentResource.class.getName());

}