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

package com.liferay.portal.company.log.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.log4j.Log4JUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;

import java.io.File;
import java.io.FileNotFoundException;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import java.util.Arrays;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Hai Yu
 */
@RunWith(Arquillian.class)
public class CompanyLogServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_newCompany = CompanyTestUtil.addCompany();

		_adminUser = UserTestUtil.addCompanyAdminUser(_newCompany);

		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(
			_newCompany.getCompanyId());

		for (File file : companyLogDirectory.listFiles()) {
			_file = file;

			break;
		}

		_defaultCompany = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		try {
			_companyLocalService.deleteCompany(_newCompany);
		}
		finally {
			File parentDirectory = _file.getParentFile();

			_file.delete();

			parentDirectory.delete();
		}
	}

	@Test
	public void testDownloadWithFileNotFound() throws Exception {
		String fileName = StringUtil.randomString() + ".log";

		String message = StringBundler.concat(
			"Unable to get file ", fileName, " for company ",
			_newCompany.getCompanyId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				PortalImpl.class.getName(), LoggerTestUtil.WARN)) {

			_assertHttpServletResponseStatusAndLogInfo(
				_createMockHttpServletRequest(
					StringBundler.concat(
						StringPool.SLASH, _newCompany.getCompanyId(),
						StringPool.SLASH, fileName),
					_adminUser),
				FileNotFoundException.class, message,
				HttpServletResponse.SC_NOT_FOUND);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(
				FileNotFoundException.class, throwable.getClass());
			Assert.assertEquals(message, throwable.getMessage());
		}
	}

	@Test
	public void testDownloadWithInvalidPath() throws Exception {
		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(
			_newCompany.getCompanyId());

		File parentCompanyLogDirectory = companyLogDirectory.getParentFile();

		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(), "/../"),
				_adminUser),
			PrincipalException.class,
			"Invalid path " + parentCompanyLogDirectory.getPath(),
			HttpServletResponse.SC_FORBIDDEN);
	}

	@Test
	public void testDownloadWithNoSuchCompany() throws Exception {
		long companyId = 1;

		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, companyId, StringPool.SLASH,
					_file.getName()),
				_adminUser),
			NoSuchCompanyException.class,
			"No Company exists with the primary key " + companyId,
			HttpServletResponse.SC_NOT_FOUND);
	}

	@Test
	public void testDownloadWithoutStartAndEnd() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(),
					StringPool.SLASH, _file.getName()),
				_adminUser);

		_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

		_assertHttpServletResponse(0, _file.length());
	}

	@Test
	public void testDownloadWithStartGreaterThanOrEqualsToEnd()
		throws Exception {

		_assertDownloadWithInvalidData("0", "0");
		_assertDownloadWithInvalidData("10", "10");
		_assertDownloadWithInvalidData("2", "1");
		_assertDownloadWithInvalidData("10", "0");
	}

	@Test
	public void testDownloadWithStartLessThanEnd() throws Exception {
		_assertDownloadWithValidData("2", "5");
		_assertDownloadWithValidData("0", "10");
		_assertDownloadWithValidData("1", String.valueOf(Integer.MAX_VALUE));
		_assertDownloadWithValidData("2", String.valueOf(_file.length() + 1));
	}

	@Test
	public void testDownloadWithStartOrEndForNull() throws Exception {
		_assertDownloadWithValidData("", "");
		_assertDownloadWithValidData(null, null);
		_assertDownloadWithValidData("3", "");
		_assertDownloadWithInvalidData("", "0");
		_assertDownloadWithInvalidData(String.valueOf(_file.length()), "");
	}

	@Test
	public void testDownloadWithStartOrEndLessThanZero() throws Exception {
		_assertDownloadWithInvalidData("-3", "3");
		_assertDownloadWithInvalidData("-3", "-2");
		_assertDownloadWithInvalidData("3", "-3");
	}

	@Test
	public void testListWithCompanyAdminUser() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest("/", _adminUser);

		_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_mockHttpServletResponse.getContentAsString());

		_assertCompanyLogFiles(
			mockHttpServletRequest, _newCompany, (JSONObject)jsonArray.get(0));
	}

	@Test
	public void testListWithCompanyUser() throws Exception {
		User user = UserTestUtil.addUser(_newCompany);

		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest("/", user),
			PrincipalException.MustBeCompanyAdmin.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must be the company ",
				"administrator to perform the action"),
			HttpServletResponse.SC_FORBIDDEN);
	}

	@Test
	public void testListWithOmniAdminUser() throws Exception {
		User omniAdminUser = null;

		try {
			omniAdminUser = UserTestUtil.addOmniAdminUser();

			MockHttpServletRequest mockHttpServletRequest =
				_createMockHttpServletRequest("/", omniAdminUser);

			_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

			JSONArray jsonArray = _jsonFactory.createJSONArray(
				_mockHttpServletResponse.getContentAsString());

			_assertCompanyLogFiles(
				mockHttpServletRequest, _defaultCompany,
				(JSONObject)jsonArray.get(0));
			_assertCompanyLogFiles(
				mockHttpServletRequest, _newCompany,
				(JSONObject)jsonArray.get(1));
		}
		finally {
			if (omniAdminUser != null) {
				_userLocalService.deleteUser(omniAdminUser);
			}
		}
	}

	@Test
	public void testUserUnauthenticated() throws Exception {
		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest("/", (User)null),
			PrincipalException.MustBeAuthenticated.class,
			"User 0 must be authenticated", HttpServletResponse.SC_FORBIDDEN);
	}

	private void _assertCompanyLogFiles(
			MockHttpServletRequest mockHttpServletRequest, Company company,
			JSONObject jsonObject)
		throws Exception {

		Assert.assertEquals(
			HttpServletResponse.SC_OK, _mockHttpServletResponse.getStatus());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			_mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			company.getCompanyId(), jsonObject.getLong("companyId"));
		Assert.assertEquals(company.getWebId(), jsonObject.getString("webId"));

		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(
			company.getCompanyId());

		File[] files = companyLogDirectory.listFiles();

		Assert.assertTrue(
			StringBundler.concat(
				"The directory ", companyLogDirectory.getPath(),
				" must have log files"),
			files.length > 0);

		JSONArray companyLogsJSONArray = jsonObject.getJSONArray("companyLogs");

		Arrays.sort(files);

		for (int i = 0; i < files.length; i++) {
			JSONObject companyLogJSONObject =
				companyLogsJSONArray.getJSONObject(i);

			Assert.assertEquals(
				files[i].getName(), companyLogJSONObject.getString("fileName"));
			Assert.assertEquals(
				_language.formatStorageSize(
					files[i].length(), mockHttpServletRequest.getLocale()),
				companyLogJSONObject.getString("fileSize"));
		}
	}

	private void _assertDownloadWithInvalidData(
			String startString, String endString)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				PortalImpl.class.getName(), LoggerTestUtil.WARN)) {

			String message =
				"Start and end cannot be less than 0. Start cannot be " +
					"greater than or equal to end.";

			_assertHttpServletResponseStatusAndLogInfo(
				_createMockHttpServletRequest(startString, endString),
				IllegalArgumentException.class, message,
				HttpServletResponse.SC_BAD_REQUEST);

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());
			Assert.assertEquals(message, throwable.getMessage());
		}
		finally {
			_mockHttpServletResponse.setCommitted(false);
			_mockHttpServletResponse.reset();
		}
	}

	private void _assertDownloadWithValidData(
			String startString, String endString)
		throws Exception {

		try {
			_servlet.service(
				_createMockHttpServletRequest(startString, endString),
				_mockHttpServletResponse);

			long start = 0;

			if (Validator.isNotNull(startString)) {
				start = GetterUtil.getLongStrict(startString);
			}

			long end = _file.length();

			if (Validator.isNotNull(endString) &&
				(GetterUtil.getLongStrict(endString) < end)) {

				end = GetterUtil.getLongStrict(endString);
			}

			if (start != 0) {
				--start;
			}

			_assertHttpServletResponse(start, end);
		}
		finally {
			_mockHttpServletResponse.setCommitted(false);
			_mockHttpServletResponse.reset();
		}
	}

	private void _assertHttpServletResponse(long start, long end)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append(HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		sb.append("; filename=\"");
		sb.append(_file.getName());
		sb.append("\"");

		Assert.assertEquals(
			sb.toString(),
			_mockHttpServletResponse.getHeader(
				HttpHeaders.CONTENT_DISPOSITION));

		long contentLength = end - start;

		Assert.assertEquals(
			String.valueOf(contentLength),
			_mockHttpServletResponse.getHeader(HttpHeaders.CONTENT_LENGTH));

		Assert.assertEquals(
			_mimeTypes.getContentType(_file),
			_mockHttpServletResponse.getContentType());

		try (FileChannel fileChannel = FileChannel.open(_file.toPath());
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			fileChannel.position(start);

			StreamUtil.transfer(
				Channels.newInputStream(fileChannel),
				unsyncByteArrayOutputStream, contentLength);

			Assert.assertEquals(
				unsyncByteArrayOutputStream.toString(),
				_mockHttpServletResponse.getContentAsString());
		}
	}

	private void _assertHttpServletResponseStatusAndLogInfo(
			MockHttpServletRequest mockHttpServletRequest, Class<?> clazz,
			String message, int status)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.company.log.internal.servlet." +
					"CompanyLogServlet",
				LoggerTestUtil.WARN)) {

			_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

			Assert.assertEquals(status, _mockHttpServletResponse.getStatus());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(clazz, throwable.getClass());
			Assert.assertEquals(message, throwable.getMessage());
		}
	}

	private MockHttpServletRequest _createMockHttpServletRequest(
		String startString, String endString) {

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(),
					StringPool.SLASH, _file.getName()),
				_adminUser);

		mockHttpServletRequest.setParameter("start", startString);
		mockHttpServletRequest.setParameter("end", endString);

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _createMockHttpServletRequest(
		String path, User user) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/company-log" + path);

		mockHttpServletRequest.setAttribute(WebKeys.USER, user);
		mockHttpServletRequest.setContextPath("/company-log");
		mockHttpServletRequest.setPathInfo(path);

		return mockHttpServletRequest;
	}

	private static User _adminUser;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Company _defaultCompany;
	private static File _file;
	private static Company _newCompany;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Language _language;

	@Inject
	private MimeTypes _mimeTypes;

	private final MockHttpServletResponse _mockHttpServletResponse =
		new MockHttpServletResponse();

	@Inject(
		filter = "osgi.http.whiteboard.servlet.name=com.liferay.portal.company.log.internal.servlet.CompanyLogServlet"
	)
	private Servlet _servlet;

	@Inject
	private UserLocalService _userLocalService;

}