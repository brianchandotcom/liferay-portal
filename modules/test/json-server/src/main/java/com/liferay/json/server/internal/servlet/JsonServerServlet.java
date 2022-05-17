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

package com.liferay.json.server.internal.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.rmi.ServerException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/json-server",
		"osgi.http.whiteboard.servlet.name=com.liferay.json.server.internal.servlet.JsonServerServlet",
		"osgi.http.whiteboard.servlet.pattern=/json-server/*"
	},
	service = Servlet.class
)
public class JsonServerServlet extends HttpServlet {

	@Activate
	protected void activate() throws IOException {
		_objectMapper = new ObjectMapper();

		_objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		File repositoryDir = new File(
			PropsValues.LIFERAY_HOME, "data/json_server");

		if (!repositoryDir.exists()) {
			repositoryDir.mkdirs();

			return;
		}

		for (File file : repositoryDir.listFiles()) {
			String fileName = file.getName();

			if (fileName.endsWith(".json")) {
				URI uri = file.toURI();

				_load(
					fileName.substring(0, fileName.length() - 5), uri.toURL());
			}
		}
	}

	@Override
	protected void doDelete(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Request request = new Request(httpServletRequest);

		Object mockResponse = _processMock(request, HttpMethods.DELETE);

		if (mockResponse != null) {
			_objectMapper.writeValue(
				httpServletResponse.getWriter(), mockResponse);

			return;
		}

		if (request.getId() == -1) {
			throw new IllegalArgumentException(
				"Missing id in path " + httpServletRequest.getPathInfo());
		}

		List<Map<String, Object>> models = request.getModels();

		Iterator<Map<String, Object>> iterator = models.iterator();

		while (iterator.hasNext()) {
			Map<String, Object> model = iterator.next();

			if (_getId(model) == request.getId()) {
				iterator.remove();

				return;
			}
		}

		throw new ServletException("Unknown id " + request.getId());
	}

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Request request = new Request(httpServletRequest);

		Object mockResponse = _processMock(request, HttpMethods.GET);

		if (mockResponse != null) {
			_objectMapper.writeValue(
				httpServletResponse.getWriter(), mockResponse);

			return;
		}

		List<Map<String, Object>> models = request.getModels();

		if (request.getId() > 0) {
			for (Map<String, Object> model : models) {
				if (Objects.equals(request.getId(), _getId(model))) {
					_objectMapper.writeValue(
						httpServletResponse.getWriter(), model);

					return;
				}
			}

			throw new ServletException(
				"Unknown model id in path " + httpServletRequest.getPathInfo());
		}

		_objectMapper.writeValue(httpServletResponse.getWriter(), models);
	}

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Request request = new Request(httpServletRequest);

		Object mockResponse = _processMock(request, HttpMethods.POST);

		if (mockResponse != null) {
			_objectMapper.writeValue(
				httpServletResponse.getWriter(), mockResponse);

			return;
		}

		List<Map<String, Object>> models = request.getModels();

		Map<String, Object> model = request.getParameters();

		_fixId(model, models);

		models.add(model);
	}

	@Override
	protected void doPut(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Request request = new Request(httpServletRequest);

		Object mockResponse = _processMock(request, HttpMethods.PUT);

		if (mockResponse != null) {
			_objectMapper.writeValue(
				httpServletResponse.getWriter(), mockResponse);

			return;
		}

		Map<String, Object> model = request.getParameters();

		Long id = _getId(model);

		if (id == null) {
			throw new ServletException("Missing id " + model);
		}

		List<Map<String, Object>> models = request.getModels();

		Iterator<Map<String, Object>> iterator = models.iterator();

		while (iterator.hasNext()) {
			Map<String, Object> curModel = iterator.next();

			if (_getId(curModel) == id) {
				iterator.remove();

				models.add(model);

				return;
			}
		}

		throw new ServletException("Unknown id " + id);
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			super.service(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);

			httpServletResponse.setStatus(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			_objectMapper.writeValue(
				httpServletResponse.getWriter(),
				HashMapBuilder.<String, Object>put(
					"code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR
				).put(
					"message", exception.getMessage()
				).build());
		}
	}

	private boolean _contains(
		Map<String, Object> expectedData, Map<String, Object> parameters) {

		if (expectedData == null) {
			return true;
		}

		if (parameters == null) {
			return false;
		}

		for (Map.Entry<String, Object> entry : expectedData.entrySet()) {
			if (!Objects.equals(
					entry.getValue(), parameters.get(entry.getKey()))) {

				return false;
			}
		}

		return true;
	}

	private void _fixId(
		Map<String, Object> model, List<Map<String, Object>> models) {

		try {
			GetterUtil.getLongStrict(String.valueOf(model.get("id")));
		}
		catch (NumberFormatException numberFormatException) {
			long maxId = -1;

			for (Map<String, Object> curModel : models) {
				Long id = _getId(curModel);

				if ((id != null) && (id > maxId)) {
					maxId = id;
				}
			}

			model.put("id", maxId + 1);
		}
	}

	private Long _getId(Map<String, Object> model) {
		Object idObject = model.get("id");

		if (idObject == null) {
			return null;
		}

		return GetterUtil.getLongStrict(String.valueOf(idObject));
	}

	private void _load(String appName, URL url) throws IOException {
		_appDatas.put(
			appName,
			(Map<String, Object>)_objectMapper.readValue(url, HashMap.class));
	}

	private Object _processMock(Request request, String method)
		throws IOException, ServletException {

		List<Map<String, Object>> mockList = request.getMockList(method);

		if (mockList == null) {
			return null;
		}

		Map<String, Object> parameters = request.getParameters();

		for (Map<String, Object> mock : mockList) {
			if (_contains(
					(Map<String, Object>)mock.get("request"), parameters)) {

				Object response = mock.get("response");

				if (response instanceof Map) {
					Map<String, Object> responseMap =
						(Map<String, Object>)response;

					Object exception = responseMap.get("!exception");

					if (exception != null) {
						throw new ServletException(String.valueOf(exception));
					}
				}

				return mock.get("response");
			}
		}

		throw new ServerException("Unable to match for " + parameters);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JsonServerServlet.class);

	private final Map<String, Map<String, Object>> _appDatas = new HashMap<>();
	private ObjectMapper _objectMapper;

	private class Request {

		public long getId() {
			return _id;
		}

		public List<Map<String, Object>> getMockList(String method) {
			Object mockList = _appData.get(
				StringBundler.concat(_relativePath, StringPool.AT, method));

			if ((mockList == null) || !(mockList instanceof List)) {
				return null;
			}

			return (List<Map<String, Object>>)mockList;
		}

		public List<Map<String, Object>> getModels() throws ServletException {
			Object models = _appData.get(_modelName);

			if ((models == null) || !(models instanceof List)) {
				throw new ServletException(
					"Unknown model name : " + _modelName);
			}

			return (List<Map<String, Object>>)models;
		}

		public Map<String, Object> getParameters() {
			return _parameters;
		}

		private Request(HttpServletRequest httpServletRequest)
			throws IOException {

			String path = httpServletRequest.getPathInfo();

			List<String> pathElements = StringUtil.split(path, '/');

			if (pathElements.isEmpty()) {
				throw new IllegalArgumentException(
					"Missing app name in path " + path);
			}

			if (pathElements.size() < 2) {
				throw new IllegalArgumentException(
					"Missing model name in path " + path);
			}

			_appData = _appDatas.get(pathElements.get(0));

			if (_appData == null) {
				throw new IllegalArgumentException(
					"Unknown app name " + pathElements.get(0));
			}

			_modelName = pathElements.get(1);

			if (pathElements.size() > 2) {
				_id = GetterUtil.getLongStrict(pathElements.get(2));
			}
			else {
				_id = -1;
			}

			_relativePath = StringUtil.merge(
				pathElements.subList(1, pathElements.size()), StringPool.SLASH);

			byte[] bytes = StreamUtil.toByteArray(
				httpServletRequest.getInputStream());

			if (bytes.length == 0) {
				_parameters = null;
			}
			else {
				_parameters = _objectMapper.readValue(bytes, HashMap.class);
			}
		}

		private final Map<String, Object> _appData;
		private final long _id;
		private final String _modelName;
		private final Map<String, Object> _parameters;
		private final String _relativePath;

	}

}