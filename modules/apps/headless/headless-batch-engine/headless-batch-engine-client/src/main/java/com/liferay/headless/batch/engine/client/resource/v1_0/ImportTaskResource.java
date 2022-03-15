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

package com.liferay.headless.batch.engine.client.resource.v1_0;

import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.http.HttpInvoker;
import com.liferay.headless.batch.engine.client.problem.Problem;
import com.liferay.headless.batch.engine.client.serdes.v1_0.ImportTaskSerDes;

import java.io.File;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Ivica Cardic
 * @generated
 */
@Generated("")
public interface ImportTaskResource {

	public static Builder builder() {
		return new Builder();
	}

	public ImportTask deleteImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse deleteImportTaskHttpResponse(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception;

	public ImportTask deleteFormDataImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, ImportTask importTask,
			Map<String, File> multipartFiles)
		throws Exception;

	public HttpInvoker.HttpResponse deleteFormDataImportTaskHttpResponse(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, ImportTask importTask,
			Map<String, File> multipartFiles)
		throws Exception;

	public ImportTask postImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse postImportTaskHttpResponse(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName, Object object)
		throws Exception;

	public ImportTask postFormDataImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName,
			ImportTask importTask, Map<String, File> multipartFiles)
		throws Exception;

	public HttpInvoker.HttpResponse postFormDataImportTaskHttpResponse(
			String className, String callbackURL, String fieldNameMapping,
			String importStrategy, String taskItemDelegateName,
			ImportTask importTask, Map<String, File> multipartFiles)
		throws Exception;

	public ImportTask putImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception;

	public HttpInvoker.HttpResponse putImportTaskHttpResponse(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, Object object)
		throws Exception;

	public ImportTask putFormDataImportTask(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, ImportTask importTask,
			Map<String, File> multipartFiles)
		throws Exception;

	public HttpInvoker.HttpResponse putFormDataImportTaskHttpResponse(
			String className, String callbackURL, String importStrategy,
			String taskItemDelegateName, ImportTask importTask,
			Map<String, File> multipartFiles)
		throws Exception;

	public ImportTask getImportTask(Long importTaskId) throws Exception;

	public HttpInvoker.HttpResponse getImportTaskHttpResponse(Long importTaskId)
		throws Exception;

	public void getImportTaskFailedItemReport(Long importTaskId)
		throws Exception;

	public HttpInvoker.HttpResponse getImportTaskFailedItemReportHttpResponse(
			Long importTaskId)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public ImportTaskResource build() {
			return new ImportTaskResourceImpl(this);
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

		private Map<String, String> _headers = new LinkedHashMap<>();
		private String _host = "localhost";
		private Locale _locale;
		private String _login = "";
		private String _password = "";
		private Map<String, String> _parameters = new LinkedHashMap<>();
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class ImportTaskResourceImpl implements ImportTaskResource {

		public ImportTask deleteImportTask(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteImportTaskHttpResponse(
					className, callbackURL, importStrategy,
					taskItemDelegateName, object);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse deleteImportTaskHttpResponse(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask deleteFormDataImportTask(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, ImportTask importTask,
				Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteFormDataImportTaskHttpResponse(
					className, callbackURL, importStrategy,
					taskItemDelegateName, importTask, multipartFiles);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse deleteFormDataImportTaskHttpResponse(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, ImportTask importTask,
				Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.multipart();

			httpInvoker.part("importTask", ImportTaskSerDes.toJSON(importTask));

			for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
				httpInvoker.part(entry.getKey(), entry.getValue());
			}

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask postImportTask(
				String className, String callbackURL, String fieldNameMapping,
				String importStrategy, String taskItemDelegateName,
				Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = postImportTaskHttpResponse(
				className, callbackURL, fieldNameMapping, importStrategy,
				taskItemDelegateName, object);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postImportTaskHttpResponse(
				String className, String callbackURL, String fieldNameMapping,
				String importStrategy, String taskItemDelegateName,
				Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (fieldNameMapping != null) {
				httpInvoker.parameter(
					"fieldNameMapping", String.valueOf(fieldNameMapping));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask postFormDataImportTask(
				String className, String callbackURL, String fieldNameMapping,
				String importStrategy, String taskItemDelegateName,
				ImportTask importTask, Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postFormDataImportTaskHttpResponse(
					className, callbackURL, fieldNameMapping, importStrategy,
					taskItemDelegateName, importTask, multipartFiles);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse postFormDataImportTaskHttpResponse(
				String className, String callbackURL, String fieldNameMapping,
				String importStrategy, String taskItemDelegateName,
				ImportTask importTask, Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.multipart();

			httpInvoker.part("importTask", ImportTaskSerDes.toJSON(importTask));

			for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
				httpInvoker.part(entry.getKey(), entry.getValue());
			}

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

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (fieldNameMapping != null) {
				httpInvoker.parameter(
					"fieldNameMapping", String.valueOf(fieldNameMapping));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask putImportTask(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, Object object)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = putImportTaskHttpResponse(
				className, callbackURL, importStrategy, taskItemDelegateName,
				object);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse putImportTaskHttpResponse(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, Object object)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(object.toString(), "application/json");

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask putFormDataImportTask(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, ImportTask importTask,
				Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putFormDataImportTaskHttpResponse(
					className, callbackURL, importStrategy,
					taskItemDelegateName, importTask, multipartFiles);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse putFormDataImportTaskHttpResponse(
				String className, String callbackURL, String importStrategy,
				String taskItemDelegateName, ImportTask importTask,
				Map<String, File> multipartFiles)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.multipart();

			httpInvoker.part("importTask", ImportTaskSerDes.toJSON(importTask));

			for (Map.Entry<String, File> entry : multipartFiles.entrySet()) {
				httpInvoker.part(entry.getKey(), entry.getValue());
			}

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

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			if (callbackURL != null) {
				httpInvoker.parameter(
					"callbackURL", String.valueOf(callbackURL));
			}

			if (importStrategy != null) {
				httpInvoker.parameter(
					"importStrategy", String.valueOf(importStrategy));
			}

			if (taskItemDelegateName != null) {
				httpInvoker.parameter(
					"taskItemDelegateName",
					String.valueOf(taskItemDelegateName));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{className}");

			httpInvoker.path("className", className);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ImportTask getImportTask(Long importTaskId) throws Exception {
			HttpInvoker.HttpResponse httpResponse = getImportTaskHttpResponse(
				importTaskId);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
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
				return ImportTaskSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
		}

		public HttpInvoker.HttpResponse getImportTaskHttpResponse(
				Long importTaskId)
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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{importTaskId}");

			httpInvoker.path("importTaskId", importTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void getImportTaskFailedItemReport(Long importTaskId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getImportTaskFailedItemReportHttpResponse(importTaskId);

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

				throw new Problem.ProblemException(Problem.toDTO(content));
			}
			else {
				_logger.fine("HTTP response content: " + content);
				_logger.fine(
					"HTTP response message: " + httpResponse.getMessage());
				_logger.fine(
					"HTTP response status code: " +
						httpResponse.getStatusCode());
			}
		}

		public HttpInvoker.HttpResponse
				getImportTaskFailedItemReportHttpResponse(Long importTaskId)
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

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/headless-batch-engine/v1.0/import-task/{importTaskId}/failed-items/report");

			httpInvoker.path("importTaskId", importTaskId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private ImportTaskResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			ImportTaskResource.class.getName());

		private Builder _builder;

	}

}