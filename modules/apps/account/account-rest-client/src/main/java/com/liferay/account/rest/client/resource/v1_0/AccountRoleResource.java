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

package com.liferay.account.rest.client.resource.v1_0;

import com.liferay.account.rest.client.dto.v1_0.AccountRole;
import com.liferay.account.rest.client.http.HttpInvoker;
import com.liferay.account.rest.client.pagination.Page;
import com.liferay.account.rest.client.pagination.Pagination;
import com.liferay.account.rest.client.problem.Problem;
import com.liferay.account.rest.client.serdes.v1_0.AccountRoleSerDes;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Drew Brokke
 * @generated
 */
@Generated("")
public interface AccountRoleResource {

	public static Builder builder() {
		return new Builder();
	}

	public void deleteAccountRoleUserAssociationByExternalReferenceCode(
			String accountExternalReferenceCode, Long accountRoleId,
			String accountUserExternalReferenceCode)
		throws Exception;

	public HttpInvoker.HttpResponse
			deleteAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
				String accountExternalReferenceCode, Long accountRoleId,
				String accountUserExternalReferenceCode)
		throws Exception;

	public void postAccountRoleUserAssociationByExternalReferenceCode(
			String accountExternalReferenceCode, Long accountRoleId,
			String accountUserExternalReferenceCode)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
				String accountExternalReferenceCode, Long accountRoleId,
				String accountUserExternalReferenceCode)
		throws Exception;

	public Page<AccountRole> getAccountRolesByExternalReferenceCodePage(
			String externalReferenceCode, String keywords,
			Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAccountRolesByExternalReferenceCodePageHttpResponse(
				String externalReferenceCode, String keywords,
				Pagination pagination, String sortString)
		throws Exception;

	public AccountRole postAccountRoleByExternalReferenceCode(
			String externalReferenceCode, AccountRole accountRole)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAccountRoleByExternalReferenceCodeHttpResponse(
				String externalReferenceCode, AccountRole accountRole)
		throws Exception;

	public Page<AccountRole> getAccountRolesPage(
			Long accountId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getAccountRolesPageHttpResponse(
			Long accountId, String keywords, Pagination pagination,
			String sortString)
		throws Exception;

	public AccountRole postAccountRole(Long accountId, AccountRole accountRole)
		throws Exception;

	public HttpInvoker.HttpResponse postAccountRoleHttpResponse(
			Long accountId, AccountRole accountRole)
		throws Exception;

	public void deleteAccountRoleUserAssociation(
			Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception;

	public HttpInvoker.HttpResponse
			deleteAccountRoleUserAssociationHttpResponse(
				Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception;

	public void postAccountRoleUserAssociation(
			Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception;

	public HttpInvoker.HttpResponse postAccountRoleUserAssociationHttpResponse(
			Long accountId, Long accountRoleId, Long accountUserId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public AccountRoleResource build() {
			return new AccountRoleResourceImpl(this);
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

		private Builder() {
		}

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class AccountRoleResourceImpl implements AccountRoleResource {

		public void deleteAccountRoleUserAssociationByExternalReferenceCode(
				String accountExternalReferenceCode, Long accountRoleId,
				String accountUserExternalReferenceCode)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					accountExternalReferenceCode, accountRoleId,
					accountUserExternalReferenceCode);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				deleteAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					String accountExternalReferenceCode, Long accountRoleId,
					String accountUserExternalReferenceCode)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/by-externalReferenceCode/{accountExternalReferenceCode}/account-roles/{accountRoleId}/account-users/{accountUserExternalReferenceCode}",
				accountExternalReferenceCode, accountRoleId,
				accountUserExternalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postAccountRoleUserAssociationByExternalReferenceCode(
				String accountExternalReferenceCode, Long accountRoleId,
				String accountUserExternalReferenceCode)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					accountExternalReferenceCode, accountRoleId,
					accountUserExternalReferenceCode);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postAccountRoleUserAssociationByExternalReferenceCodeHttpResponse(
					String accountExternalReferenceCode, Long accountRoleId,
					String accountUserExternalReferenceCode)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(
				accountUserExternalReferenceCode.toString(),
				"application/json");

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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/by-externalReferenceCode/{accountExternalReferenceCode}/account-roles/{accountRoleId}/account-users/{accountUserExternalReferenceCode}",
				accountExternalReferenceCode, accountRoleId,
				accountUserExternalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<AccountRole> getAccountRolesByExternalReferenceCodePage(
				String externalReferenceCode, String keywords,
				Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAccountRolesByExternalReferenceCodePageHttpResponse(
					externalReferenceCode, keywords, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, AccountRoleSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				getAccountRolesByExternalReferenceCodePageHttpResponse(
					String externalReferenceCode, String keywords,
					Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (keywords != null) {
				httpInvoker.parameter("keywords", String.valueOf(keywords));
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
					_builder._port +
						"/o/account-rest/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-roles",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public AccountRole postAccountRoleByExternalReferenceCode(
				String externalReferenceCode, AccountRole accountRole)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAccountRoleByExternalReferenceCodeHttpResponse(
					externalReferenceCode, accountRole);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return AccountRoleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postAccountRoleByExternalReferenceCodeHttpResponse(
					String externalReferenceCode, AccountRole accountRole)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(accountRole.toString(), "application/json");

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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-roles",
				externalReferenceCode);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<AccountRole> getAccountRolesPage(
				Long accountId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAccountRolesPageHttpResponse(
					accountId, keywords, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, AccountRoleSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getAccountRolesPageHttpResponse(
				Long accountId, String keywords, Pagination pagination,
				String sortString)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (keywords != null) {
				httpInvoker.parameter("keywords", String.valueOf(keywords));
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
					_builder._port +
						"/o/account-rest/v1.0/accounts/{accountId}/account-roles",
				accountId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public AccountRole postAccountRole(
				Long accountId, AccountRole accountRole)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = postAccountRoleHttpResponse(
				accountId, accountRole);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return AccountRoleSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postAccountRoleHttpResponse(
				Long accountId, AccountRole accountRole)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(accountRole.toString(), "application/json");

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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/{accountId}/account-roles",
				accountId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteAccountRoleUserAssociation(
				Long accountId, Long accountRoleId, Long accountUserId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteAccountRoleUserAssociationHttpResponse(
					accountId, accountRoleId, accountUserId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				deleteAccountRoleUserAssociationHttpResponse(
					Long accountId, Long accountRoleId, Long accountUserId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/account-users/{accountUserId}",
				accountId, accountRoleId, accountUserId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void postAccountRoleUserAssociation(
				Long accountId, Long accountRoleId, Long accountUserId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAccountRoleUserAssociationHttpResponse(
					accountId, accountRoleId, accountUserId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return;
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse
				postAccountRoleUserAssociationHttpResponse(
					Long accountId, Long accountRoleId, Long accountUserId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(accountUserId.toString(), "application/json");

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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/account-rest/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/account-users/{accountUserId}",
				accountId, accountRoleId, accountUserId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private AccountRoleResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			AccountRoleResource.class.getName());

		private Builder _builder;

	}

}