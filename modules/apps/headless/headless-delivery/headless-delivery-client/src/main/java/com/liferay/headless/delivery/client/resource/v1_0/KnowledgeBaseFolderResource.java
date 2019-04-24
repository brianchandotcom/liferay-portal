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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseFolderSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class KnowledgeBaseFolderResource {

	public void deleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}",
			knowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}",
			knowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseFolderSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}",
			knowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseFolderSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{knowledgeBaseFolderId}",
			knowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseFolderSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
			parentKnowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), KnowledgeBaseFolderSerDes::toDTO);
	}

	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/knowledge-base-folders/{parentKnowledgeBaseFolderId}/knowledge-base-folders",
			parentKnowledgeBaseFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseFolderSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
		httpInvoker.parameter(
			"pageSize", String.valueOf(pagination.getPageSize()));

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), KnowledgeBaseFolderSerDes::toDTO);
	}

	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			KnowledgeBaseFolderSerDes.toJSON(knowledgeBaseFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/knowledge-base-folders",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return KnowledgeBaseFolderSerDes.toDTO(httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	private static final Logger _logger = Logger.getLogger(
		KnowledgeBaseFolderResource.class.getName());

}