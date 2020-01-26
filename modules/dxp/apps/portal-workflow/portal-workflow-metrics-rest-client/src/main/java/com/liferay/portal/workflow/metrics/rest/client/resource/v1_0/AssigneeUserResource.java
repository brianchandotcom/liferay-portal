/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.resource.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.client.problem.Problem;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.AssigneeUserSerDes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface AssigneeUserResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<AssigneeUser> getProcessAssigneeUsersPage(
			Long processId, Boolean completed, java.util.Date dateEnd,
			java.util.Date dateStart, String keywords, Long[] roleIds,
			String[] taskKeys, Pagination pagination, String sortString)
		throws Exception;

	public HttpInvoker.HttpResponse getProcessAssigneeUsersPageHttpResponse(
			Long processId, Boolean completed, java.util.Date dateEnd,
			java.util.Date dateStart, String keywords, Long[] roleIds,
			String[] taskKeys, Pagination pagination, String sortString)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public AssigneeUserResource build() {
			return new AssigneeUserResourceImpl(this);
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
		private String _login = "test@liferay.com";
		private String _password = "test";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class AssigneeUserResourceImpl
		implements AssigneeUserResource {

		public Page<AssigneeUser> getProcessAssigneeUsersPage(
				Long processId, Boolean completed, java.util.Date dateEnd,
				java.util.Date dateStart, String keywords, Long[] roleIds,
				String[] taskKeys, Pagination pagination, String sortString)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProcessAssigneeUsersPageHttpResponse(
					processId, completed, dateEnd, dateStart, keywords, roleIds,
					taskKeys, pagination, sortString);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return Page.of(content, AssigneeUserSerDes::toDTO);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getProcessAssigneeUsersPageHttpResponse(
				Long processId, Boolean completed, java.util.Date dateEnd,
				java.util.Date dateStart, String keywords, Long[] roleIds,
				String[] taskKeys, Pagination pagination, String sortString)
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

			DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			if (completed != null) {
				httpInvoker.parameter("completed", String.valueOf(completed));
			}

			if (dateEnd != null) {
				httpInvoker.parameter(
					"dateEnd", liferayToJSONDateFormat.format(dateEnd));
			}

			if (dateStart != null) {
				httpInvoker.parameter(
					"dateStart", liferayToJSONDateFormat.format(dateStart));
			}

			if (keywords != null) {
				httpInvoker.parameter("keywords", String.valueOf(keywords));
			}

			if (roleIds != null) {
				for (int i = 0; i < roleIds.length; i++) {
					httpInvoker.parameter(
						"roleIds", String.valueOf(roleIds[i]));
				}
			}

			if (taskKeys != null) {
				for (int i = 0; i < taskKeys.length; i++) {
					httpInvoker.parameter(
						"taskKeys", String.valueOf(taskKeys[i]));
				}
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
						"/o/portal-workflow-metrics/v1.0/processes/{processId}/assignee-users",
				processId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private AssigneeUserResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			AssigneeUserResource.class.getName());

		private Builder _builder;

	}

}