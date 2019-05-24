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

package com.liferay.headless.admin.taxonomy.client.resource.v1_0;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.TaxonomyCategorySerDes;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategoryResource {

	public static Page<TaxonomyCategory>
			getTaxonomyCategoryTaxonomyCategoriesPage(
				Long parentTaxonomyCategoryId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getTaxonomyCategoryTaxonomyCategoriesPageHttpResponse(
				parentTaxonomyCategoryId, search, filterString, pagination,
				sortString);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, TaxonomyCategorySerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getTaxonomyCategoryTaxonomyCategoriesPageHttpResponse(
				Long parentTaxonomyCategoryId, String search,
				String filterString, Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (search != null) {
			httpInvoker.parameter("search", String.valueOf(search));
		}

		if (filterString != null) {
			httpInvoker.parameter("filter", filterString);
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
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories",
			parentTaxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			Long parentTaxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postTaxonomyCategoryTaxonomyCategoryHttpResponse(
				parentTaxonomyCategoryId, taxonomyCategory);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return TaxonomyCategorySerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postTaxonomyCategoryTaxonomyCategoryHttpResponse(
				Long parentTaxonomyCategoryId,
				TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(taxonomyCategory.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{parentTaxonomyCategoryId}/taxonomy-categories",
			parentTaxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static void deleteTaxonomyCategory(Long taxonomyCategoryId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			deleteTaxonomyCategoryHttpResponse(taxonomyCategoryId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());
	}

	public static HttpInvoker.HttpResponse deleteTaxonomyCategoryHttpResponse(
			Long taxonomyCategoryId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}",
			taxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static TaxonomyCategory getTaxonomyCategory(Long taxonomyCategoryId)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = getTaxonomyCategoryHttpResponse(
			taxonomyCategoryId);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return TaxonomyCategorySerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse getTaxonomyCategoryHttpResponse(
			Long taxonomyCategoryId)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}",
			taxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static TaxonomyCategory patchTaxonomyCategory(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			patchTaxonomyCategoryHttpResponse(
				taxonomyCategoryId, taxonomyCategory);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return TaxonomyCategorySerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse patchTaxonomyCategoryHttpResponse(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(taxonomyCategory.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PATCH);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}",
			taxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static TaxonomyCategory putTaxonomyCategory(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse = putTaxonomyCategoryHttpResponse(
			taxonomyCategoryId, taxonomyCategory);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return TaxonomyCategorySerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse putTaxonomyCategoryHttpResponse(
			Long taxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(taxonomyCategory.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-categories/{taxonomyCategoryId}",
			taxonomyCategoryId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static Page<TaxonomyCategory>
			getTaxonomyVocabularyTaxonomyCategoriesPage(
				Long taxonomyVocabularyId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			getTaxonomyVocabularyTaxonomyCategoriesPageHttpResponse(
				taxonomyVocabularyId, search, filterString, pagination,
				sortString);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		return Page.of(content, TaxonomyCategorySerDes::toDTO);
	}

	public static HttpInvoker.HttpResponse
			getTaxonomyVocabularyTaxonomyCategoriesPageHttpResponse(
				Long taxonomyVocabularyId, String search, String filterString,
				Pagination pagination, String sortString)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

		if (search != null) {
			httpInvoker.parameter("search", String.valueOf(search));
		}

		if (filterString != null) {
			httpInvoker.parameter("filter", filterString);
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
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories",
			taxonomyVocabularyId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	public static TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			Long taxonomyVocabularyId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			postTaxonomyVocabularyTaxonomyCategoryHttpResponse(
				taxonomyVocabularyId, taxonomyCategory);

		String content = httpResponse.getContent();

		_logger.fine("HTTP response content: " + content);

		_logger.fine("HTTP response message: " + httpResponse.getMessage());
		_logger.fine(
			"HTTP response status code: " + httpResponse.getStatusCode());

		try {
			return TaxonomyCategorySerDes.toDTO(content);
		}
		catch (Exception e) {
			_logger.log(
				Level.WARNING, "Unable to process HTTP response: " + content,
				e);

			throw e;
		}
	}

	public static HttpInvoker.HttpResponse
			postTaxonomyVocabularyTaxonomyCategoryHttpResponse(
				Long taxonomyVocabularyId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(taxonomyCategory.toString(), "application/json");

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/{taxonomyVocabularyId}/taxonomy-categories",
			taxonomyVocabularyId);

		httpInvoker.userNameAndPassword("test@liferay.com:test");

		return httpInvoker.invoke();
	}

	private static final Logger _logger = Logger.getLogger(
		TaxonomyCategoryResource.class.getName());

}