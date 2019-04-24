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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentFolderSerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentFolderResource {

	public Page<StructuredContentFolder> getSiteStructuredContentFoldersPage(
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
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), StructuredContentFolderSerDes::toDTO);
	}

	public StructuredContentFolder postSiteStructuredContentFolder(
			Long siteId, StructuredContentFolder structuredContentFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentFolderSerDes.toJSON(structuredContentFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/sites/{siteId}/structured-content-folders",
			siteId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentFolderSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public Page<StructuredContentFolder>
			getStructuredContentFolderStructuredContentFoldersPage(
				Long parentStructuredContentFolderId, String search,
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
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders",
			parentStructuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return Page.of(
			httpResponse.getContent(), StructuredContentFolderSerDes::toDTO);
	}

	public StructuredContentFolder
			postStructuredContentFolderStructuredContentFolder(
				Long parentStructuredContentFolderId,
				StructuredContentFolder structuredContentFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentFolderSerDes.toJSON(structuredContentFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{parentStructuredContentFolderId}/structured-content-folders",
			parentStructuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentFolderSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public void deleteStructuredContentFolder(Long structuredContentFolderId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		httpInvoker.invoke();
	}

	public StructuredContentFolder getStructuredContentFolder(
			Long structuredContentFolderId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentFolderSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContentFolder patchStructuredContentFolder(
			Long structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentFolderSerDes.toJSON(structuredContentFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentFolderSerDes.toDTO(
				httpResponse.getContent());
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING,
				"Unable to process HTTP response: " + httpResponse.getContent(),
				e);

			throw e;
		}
	}

	public StructuredContentFolder putStructuredContentFolder(
			Long structuredContentFolderId,
			StructuredContentFolder structuredContentFolder)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			StructuredContentFolderSerDes.toJSON(structuredContentFolder),
			"application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-delivery/v1.0/structured-content-folders/{structuredContentFolderId}",
			structuredContentFolderId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		try {
			return StructuredContentFolderSerDes.toDTO(
				httpResponse.getContent());
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
		StructuredContentFolderResource.class.getName());

}