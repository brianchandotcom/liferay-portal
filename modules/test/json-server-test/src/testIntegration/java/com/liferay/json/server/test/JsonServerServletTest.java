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

package com.liferay.json.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class JsonServerServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Map<?, ?> appDatas = ReflectionTestUtil.getFieldValue(
			_servlet, "_appDatas");

		appDatas.clear();

		ReflectionTestUtil.invoke(
			_servlet, "_load", new Class<?>[] {String.class, URL.class},
			"fruit", JsonServerServletTest.class.getResource("/fruit.json"));
		ReflectionTestUtil.invoke(
			_servlet, "_load", new Class<?>[] {String.class, URL.class}, "meat",
			JsonServerServletTest.class.getResource("/meat.json"));
	}

	@Test
	public void testDelete() throws Exception {

		// Mocking beef/Chunk

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(HttpMethods.DELETE);
		mockHttpServletRequest.setPathInfo("/meat/beef");
		mockHttpServletRequest.setContent("{\"cut\":\"Chunk\"}".getBytes());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Map<String, Object> message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals("Done", message.get("message"));

		// Mocking beef/Rib

		mockHttpServletRequest.setContent("{\"cut\":\"Rib\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals("Are you sure?", message.get("message"));

		// Mocking beef/Round

		mockHttpServletRequest.setContent("{\"cut\":\"Round\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Item does not exist", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Item does not exist", message.get("message"));
		}

		// Mocking beef/no cut

		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Invalid input", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Invalid input", message.get("message"));
		}

		// Missing id

		mockHttpServletRequest.setPathInfo("/meat/pork");

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Assert.assertEquals(
				"Missing id in path /meat/pork", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Missing id in path /meat/pork", message.get("message"));
		}

		// Delete by id

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo("/meat/pork/2");

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(2, message.get("id"));
		Assert.assertEquals("Pork Ribs", message.get("name"));

		mockHttpServletRequest.setMethod(HttpMethods.DELETE);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo("/meat/pork/2");

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals(
				"Unknown model id in path /meat/pork/2",
				throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Unknown model id in path /meat/pork/2",
				message.get("message"));
		}
	}

	@Test
	public void testGet() throws Exception {

		// Missing app name

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(HttpMethods.GET);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Assert.assertEquals(
				"Missing app name in path null", throwable.getMessage());

			Map<String, Object> message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Missing app name in path null", message.get("message"));
		}

		// Missing model name

		mockHttpServletRequest.setPathInfo("/fruit");

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Assert.assertEquals(
				"Missing model name in path /fruit", throwable.getMessage());

			Map<String, Object> message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Missing model name in path /fruit", message.get("message"));
		}

		// Mocking apple/green

		mockHttpServletRequest.setPathInfo("/fruit/apple");
		mockHttpServletRequest.setContent("{\"color\":\"green\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Map<String, Object> message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(5, message.get("price"));

		// Mocking apple/red

		mockHttpServletRequest.setPathInfo("/fruit/apple");
		mockHttpServletRequest.setContent("{\"color\":\"red\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(6, message.get("price"));

		// Mocking apple/yellow

		mockHttpServletRequest.setPathInfo("/fruit/apple");
		mockHttpServletRequest.setContent("{\"color\":\"yellow\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Out of stock", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Out of stock", message.get("message"));
		}

		// Mocking apple/no color

		mockHttpServletRequest.setPathInfo("/fruit/apple");
		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Invalid input", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Invalid input", message.get("message"));
		}

		// Get list

		mockHttpServletRequest.setPathInfo("/fruit/orange");

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		List<Map<String, Object>> oranges = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), List.class);

		Assert.assertEquals(oranges.toString(), 2, oranges.size());

		Map<String, Object> orange1 = oranges.get(0);

		Assert.assertEquals(1, orange1.get("id"));
		Assert.assertEquals("Navel Orange", orange1.get("name"));

		Map<String, Object> orange2 = oranges.get(1);

		Assert.assertEquals(2, orange2.get("id"));
		Assert.assertEquals("Boold Orange", orange2.get("name"));

		// Get by id

		mockHttpServletRequest.setPathInfo("/fruit/orange/2");

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Map<String, Object> orange3 = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(2, orange3.get("id"));
		Assert.assertEquals("Boold Orange", orange3.get("name"));

		// Get by unknown id

		mockHttpServletRequest.setPathInfo("/fruit/orange/3");

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals(
				"Unknown model id in path /fruit/orange/3",
				throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Unknown model id in path /fruit/orange/3",
				message.get("message"));
		}
	}

	@Test
	public void testPost() throws Exception {

		// Mocking banana/green

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(HttpMethods.POST);
		mockHttpServletRequest.setPathInfo("/fruit/banana");
		mockHttpServletRequest.setContent("{\"color\":\"green\"}".getBytes());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Unripen banana :(", throwable.getMessage());

			Map<String, Object> message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Unripen banana :(", message.get("message"));
		}

		// Mocking banana/yellow

		mockHttpServletRequest.setPathInfo("/fruit/banana");
		mockHttpServletRequest.setContent("{\"color\":\"yellow\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Map<String, Object> message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals("Yummy!", message.get("message"));

		// Mocking banana/no color

		mockHttpServletRequest.setMethod(HttpMethods.POST);
		mockHttpServletRequest.setPathInfo("/fruit/banana");
		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Invalid input", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Invalid input", message.get("message"));
		}

		// Post with auto id

		mockHttpServletRequest.setPathInfo("/fruit/raspberry");
		mockHttpServletRequest.setContent(
			"{\"name\":\"Allen Black\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo("/fruit/raspberry/2");
		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(2, message.get("id"));
		Assert.assertEquals("Allen Black", message.get("name"));

		// Post with given id

		mockHttpServletRequest.setMethod(HttpMethods.POST);
		mockHttpServletRequest.setPathInfo("/fruit/raspberry");
		mockHttpServletRequest.setContent(
			"{\"id\":7,\"name\":\"Jewel\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo("/fruit/raspberry/7");
		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(7, message.get("id"));
		Assert.assertEquals("Jewel", message.get("name"));
	}

	@Test
	public void testPut() throws Exception {

		// Mocking chicken/Breast

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setMethod(HttpMethods.PUT);
		mockHttpServletRequest.setPathInfo("/meat/chicken");
		mockHttpServletRequest.setContent("{\"cut\":\"Breast\"}".getBytes());

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Map<String, Object> message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals("Done", message.get("message"));

		// Mocking chicken/Thighs

		mockHttpServletRequest.setContent("{\"cut\":\"Thighs\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Item does not exist", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Item does not exist", message.get("message"));
		}

		// Mocking chicken/no cut

		mockHttpServletRequest.setContent(null);

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());

			Assert.assertEquals("Invalid input", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Invalid input", message.get("message"));
		}

		// Missing id

		mockHttpServletRequest.setPathInfo("/meat/fish");
		mockHttpServletRequest.setContent(
			"{\"name\":\"Rainbow Trout\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());
			Assert.assertEquals(
				"Missing id {name=Rainbow Trout}", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals(
				"Missing id {name=Rainbow Trout}", message.get("message"));
		}

		// Put with unknown id

		mockHttpServletRequest.setContent(
			"{\"id\":2,\"name\":\"Rainbow Trout\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_TEST_CLASS_NAME, LoggerTestUtil.ERROR)) {

			_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(ServletException.class, throwable.getClass());
			Assert.assertEquals("Unknown id 2", throwable.getMessage());

			message = _objectMapper.readValue(
				mockHttpServletResponse.getContentAsString(), HashMap.class);

			Assert.assertEquals(500, message.get("code"));
			Assert.assertEquals("Unknown id 2", message.get("message"));
		}

		// Put with id

		mockHttpServletRequest.setContent(
			"{\"id\":1,\"name\":\"Rainbow Trout\"}".getBytes());

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		mockHttpServletRequest.setMethod(HttpMethods.GET);
		mockHttpServletRequest.setPathInfo("/meat/fish/1");

		mockHttpServletResponse = new MockHttpServletResponse();

		_servlet.service(mockHttpServletRequest, mockHttpServletResponse);

		message = _objectMapper.readValue(
			mockHttpServletResponse.getContentAsString(), HashMap.class);

		Assert.assertEquals(1, message.get("id"));
		Assert.assertEquals("Rainbow Trout", message.get("name"));
	}

	private static final String _TEST_CLASS_NAME =
		"com.liferay.json.server.internal.servlet.JsonServerServlet";

	@Inject(filter = "osgi.http.whiteboard.servlet.name=" + _TEST_CLASS_NAME)
	private static Servlet _servlet;

	private final ObjectMapper _objectMapper = new ObjectMapper();

}