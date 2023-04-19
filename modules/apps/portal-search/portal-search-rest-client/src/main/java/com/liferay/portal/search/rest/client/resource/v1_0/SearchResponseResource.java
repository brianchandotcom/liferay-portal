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

package com.liferay.portal.search.rest.client.resource.v1_0;

import com.liferay.portal.search.rest.client.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchResponse;
import com.liferay.portal.search.rest.client.http.HttpInvoker;
import com.liferay.portal.search.rest.client.pagination.Pagination;
import com.liferay.portal.search.rest.client.problem.Problem;
import com.liferay.portal.search.rest.client.serdes.v1_0.SearchResponseSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public interface SearchResponseResource {

	public static Builder builder() {
		return new Builder();
	}

	public SearchResponse postSearch(
			Boolean basicFacetSelection, String[] entryClassNames,
			Boolean explain, Long[] groupIds, Boolean includeAssetSearchSummary,
			Boolean includeAssetTitle, Boolean includeRequest,
			Boolean includeResponse, String keywords, String[] resultFields,
			Long scopeGroupId, Pagination pagination, String sortString,
			SearchRequestBody searchRequestBody)
		throws Exception;

	public HttpInvoker.HttpResponse postSearchHttpResponse(
			Boolean basicFacetSelection, String[] entryClassNames,
			Boolean explain, Long[] groupIds, Boolean includeAssetSearchSummary,
			Boolean includeAssetTitle, Boolean includeRequest,
			Boolean includeResponse, String keywords, String[] resultFields,
			Long scopeGroupId, Pagination pagination, String sortString,
			SearchRequestBody searchRequestBody)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public Builder bearerToken(String token) {
			return header("Authorization", "Bearer " + token);
		}

		public SearchResponseResource build() {
			return new SearchResponseResourceImpl(this);
		}

		public Builder contextPath(String contextPath) {
			_contextPath = contextPath;

			return this;
		}

		public Builder endpoint(String address, String scheme) {
			String[] addressParts = address.split(":");

			String host = addressParts[0];

			int port = 443;

			if (addressParts.length > 1) {
				String portString = addressParts[1];

				try {
					port = Integer.parseInt(portString);
				}
				catch (NumberFormatException numberFormatException) {
					throw new IllegalArgumentException(
						"Unable to parse port from " + portString);
				}
			}

			return endpoint(host, port, scheme);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder header(String key, String value) {
			_headers.put(key, value);

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		public Builder parameter(String key, String value) {
			_parameters.put(key, value);

			return this;
		}

		public Builder parameters(String... parameters) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			for (int i = 0; i < parameters.length; i += 2) {
				String parameterName = String.valueOf(parameters[i]);
				String parameterValue = String.valueOf(parameters[i + 1]);

				_parameters.put(parameterName, parameterValue);
			}

			return this;
		}

		private Builder() {
		}

		private String _contextPath = "";
		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class SearchResponseResourceImpl
		implements SearchResponseResource {

		public SearchResponse postSearch(
				Boolean basicFacetSelection, String[] entryClassNames,
				Boolean explain, Long[] groupIds,
				Boolean includeAssetSearchSummary, Boolean includeAssetTitle,
				Boolean includeRequest, Boolean includeResponse,
				String keywords, String[] resultFields, Long scopeGroupId,
				Pagination pagination, String sortString,
				SearchRequestBody searchRequestBody)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = postSearchHttpResponse(
				basicFacetSelection, entryClassNames, explain, groupIds,
				includeAssetSearchSummary, includeAssetTitle, includeRequest,
				includeResponse, keywords, resultFields, scopeGroupId,
				pagination, sortString, searchRequestBody);

			String content = httpResponse.getContent();

			if ((httpResponse.getStatusCode() / 100) != 2) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response content: " + content);
				_logger.log(
					Level.WARNING,
					"HTTP response message: " + httpResponse.getMessage());
				_logger.log(
					Level.WARNING,
					"HTTP response status code: " +
						httpResponse.getStatusCode());

				Problem.ProblemException problemException = null;

				if (Objects.equals(
						httpResponse.getContentType(), "application/json")) {

					problemException = new Problem.ProblemException(
						Problem.toDTO(content));
				}
				else {
					_logger.log(
						Level.WARNING,
						"Unable to process content type: " +
							httpResponse.getContentType());

					Problem problem = new Problem();

					problem.setStatus(
						String.valueOf(httpResponse.getStatusCode()));

					problemException = new Problem.ProblemException(problem);
				}

				throw problemException;
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}

			try {
				return SearchResponseSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postSearchHttpResponse(
				Boolean basicFacetSelection, String[] entryClassNames,
				Boolean explain, Long[] groupIds,
				Boolean includeAssetSearchSummary, Boolean includeAssetTitle,
				Boolean includeRequest, Boolean includeResponse,
				String keywords, String[] resultFields, Long scopeGroupId,
				Pagination pagination, String sortString,
				SearchRequestBody searchRequestBody)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(searchRequestBody.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			for (Map.Entry<String, String> entry :
					_builder._headers.entrySet()) {

				httpInvoker.header(entry.getKey(), entry.getValue());
			}

			for (Map.Entry<String, String> entry :
					_builder._parameters.entrySet()) {

				httpInvoker.parameter(entry.getKey(), entry.getValue());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			if (basicFacetSelection != null) {
				httpInvoker.parameter(
					"basicFacetSelection", String.valueOf(basicFacetSelection));
			}

			if (entryClassNames != null) {
				for (int i = 0; i < entryClassNames.length; i++) {
					httpInvoker.parameter(
						"entryClassNames", String.valueOf(entryClassNames[i]));
				}
			}

			if (explain != null) {
				httpInvoker.parameter("explain", String.valueOf(explain));
			}

			if (groupIds != null) {
				for (int i = 0; i < groupIds.length; i++) {
					httpInvoker.parameter(
						"groupIds", String.valueOf(groupIds[i]));
				}
			}

			if (includeAssetSearchSummary != null) {
				httpInvoker.parameter(
					"includeAssetSearchSummary",
					String.valueOf(includeAssetSearchSummary));
			}

			if (includeAssetTitle != null) {
				httpInvoker.parameter(
					"includeAssetTitle", String.valueOf(includeAssetTitle));
			}

			if (includeRequest != null) {
				httpInvoker.parameter(
					"includeRequest", String.valueOf(includeRequest));
			}

			if (includeResponse != null) {
				httpInvoker.parameter(
					"includeResponse", String.valueOf(includeResponse));
			}

			if (keywords != null) {
				httpInvoker.parameter("keywords", String.valueOf(keywords));
			}

			if (resultFields != null) {
				for (int i = 0; i < resultFields.length; i++) {
					httpInvoker.parameter(
						"resultFields", String.valueOf(resultFields[i]));
				}
			}

			if (scopeGroupId != null) {
				httpInvoker.parameter(
					"scopeGroupId", String.valueOf(scopeGroupId));
			}

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			if (sortString != null) {
				httpInvoker.parameter("sort", sortString);
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port + _builder._contextPath +
						"/o/portal-search-rest/v1.0/search");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private SearchResponseResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			SearchResponseResource.class.getName());

		private Builder _builder;

	}

}