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

		File logFilesDir = Log4JUtil.getCompanyLogDirectory(
			_newCompany.getCompanyId());

		for (File file : logFilesDir.listFiles()) {
			_logFile = file;

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
			File logFilesDir = _logFile.getParentFile();

			_logFile.delete();

			logFilesDir.delete();
		}
	}

	@Test
	public void testDownloadLogFileWithFileNotFound() throws Exception {
		String fileName = StringUtil.randomString() + ".log";

		String message = StringBundler.concat(
			"Unable to find log file ", fileName, " for company ",
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

			Assert.assertEquals(
				FileNotFoundException.class, throwable.getClass());
			Assert.assertEquals(message, throwable.getMessage());
		}
	}

	@Test
	public void testDownloadLogFileWithNoSuchCompany() throws Exception {
		long companyId = 1;

		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, companyId, StringPool.SLASH,
					_logFile.getName()),
				_adminUser),
			NoSuchCompanyException.class,
			"No Company exists with the primary key " + companyId,
			HttpServletResponse.SC_NOT_FOUND);
	}

	@Test
	public void testDownloadLogFileWithoutStartIndexAndEndIndex()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(),
					StringPool.SLASH, _logFile.getName()),
				_adminUser);

		_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

		_assertHttpServletResponse(0, _logFile.length());
	}

	@Test
	public void testDownloadLogFileWithStartIndexGreaterThanOrEqualsToEndIndex()
		throws Exception {

		_assertDownloadLogFileWithInvalidData("0", "0");
		_assertDownloadLogFileWithInvalidData("10", "10");
		_assertDownloadLogFileWithInvalidData("2", "1");
		_assertDownloadLogFileWithInvalidData("10", "0");
	}

	@Test
	public void testDownloadLogFileWithStartIndexLessThanEndIndex()
		throws Exception {

		_assertDownloadLogFileWithValidData("2", "5");
		_assertDownloadLogFileWithValidData("0", "10");
		_assertDownloadLogFileWithValidData(
			"1", String.valueOf(Integer.MAX_VALUE));
		_assertDownloadLogFileWithValidData(
			"2", String.valueOf(_logFile.length() + 1));
	}

	@Test
	public void testDownloadLogFileWithStartIndexOrEndIndexForNull()
		throws Exception {

		_assertDownloadLogFileWithValidData("", "");
		_assertDownloadLogFileWithValidData(null, null);
		_assertDownloadLogFileWithValidData("3", "");
		_assertDownloadLogFileWithInvalidData("", "0");
		_assertDownloadLogFileWithInvalidData(
			String.valueOf(_logFile.length()), "");
	}

	@Test
	public void testDownloadLogFileWithStartIndexOrEndIndexLessThanZero()
		throws Exception {

		_assertDownloadLogFileWithInvalidData("-3", "3");
		_assertDownloadLogFileWithInvalidData("-3", "-2");
		_assertDownloadLogFileWithInvalidData("3", "-3");
	}

	@Test
	public void testDownloadLogFileWithUnauthorizedAccess() throws Exception {
		_assertHttpServletResponseStatusAndLogInfo(
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(), "/../"),
				_adminUser),
			PrincipalException.class, "Unauthorized access",
			HttpServletResponse.SC_FORBIDDEN);
	}

	@Test
	public void testListCompaniesLogFilesWithCompanyAdminUser()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest("/", _adminUser);

		_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

		JSONArray companyLogFilesJSONArray = _jsonFactory.createJSONArray(
			_mockHttpServletResponse.getContentAsString());

		_assertCompanyLogFiles(
			mockHttpServletRequest, _newCompany,
			(JSONObject)companyLogFilesJSONArray.get(0));
	}

	@Test
	public void testListCompaniesLogFilesWithCompanyUser() throws Exception {
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
	public void testListCompaniesLogFilesWithOmniAdminUser() throws Exception {
		User omniAdminUser = null;

		try {
			omniAdminUser = UserTestUtil.addOmniAdminUser();

			MockHttpServletRequest mockHttpServletRequest =
				_createMockHttpServletRequest("/", omniAdminUser);

			_servlet.service(mockHttpServletRequest, _mockHttpServletResponse);

			JSONArray companyLogFilesJSONArray = _jsonFactory.createJSONArray(
				_mockHttpServletResponse.getContentAsString());

			_assertCompanyLogFiles(
				mockHttpServletRequest, _defaultCompany,
				(JSONObject)companyLogFilesJSONArray.get(0));
			_assertCompanyLogFiles(
				mockHttpServletRequest, _newCompany,
				(JSONObject)companyLogFilesJSONArray.get(1));
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
			PrincipalException.class, "The current user is not authenticated",
			HttpServletResponse.SC_FORBIDDEN);
	}

	private void _assertCompanyLogFiles(
			MockHttpServletRequest mockHttpServletRequest, Company company,
			JSONObject companyLogFileJSONObject)
		throws Exception {

		Assert.assertEquals(
			HttpServletResponse.SC_OK, _mockHttpServletResponse.getStatus());
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON,
			_mockHttpServletResponse.getContentType());
		Assert.assertEquals(
			company.getCompanyId(),
			companyLogFileJSONObject.getLong("companyId"));
		Assert.assertEquals(
			company.getWebId(), companyLogFileJSONObject.getString("webId"));

		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(
			company.getCompanyId());

		File[] companyLogFiles = companyLogDirectory.listFiles();

		Assert.assertTrue(
			StringBundler.concat(
				"The directory ", companyLogDirectory.getPath(),
				" must have log files"),
			companyLogFiles.length > 0);

		JSONArray companyLogFileInfosJSONArray =
			companyLogFileJSONObject.getJSONArray("companyLogFileInfos");

		Arrays.sort(companyLogFiles);

		for (int i = 0; i < companyLogFiles.length; i++) {
			JSONObject companyLogFileInfoJSONObject =
				companyLogFileInfosJSONArray.getJSONObject(i);

			Assert.assertEquals(
				companyLogFiles[i].getName(),
				companyLogFileInfoJSONObject.getString("logFileName"));
			Assert.assertEquals(
				_language.formatStorageSize(
					companyLogFiles[i].length(),
					mockHttpServletRequest.getLocale()),
				companyLogFileInfoJSONObject.getString("logFileSize"));
		}
	}

	private void _assertDownloadLogFileWithInvalidData(
			String startIndex, String endIndex)
		throws Exception {

		try {
			_assertHttpServletResponseStatusAndLogInfo(
				_createMockHttpServletRequest(startIndex, endIndex),
				PrincipalException.class,
				"startIndex or endIndex can not be less than 0, and " +
					"startIndex can not be greater than or equal to endIndex",
				HttpServletResponse.SC_FORBIDDEN);
		}
		finally {
			_mockHttpServletResponse.setCommitted(false);
			_mockHttpServletResponse.reset();
		}
	}

	private void _assertDownloadLogFileWithValidData(
			String startIndex, String endIndex)
		throws Exception {

		try {
			_servlet.service(
				_createMockHttpServletRequest(startIndex, endIndex),
				_mockHttpServletResponse);

			long start = 0;

			if (Validator.isNotNull(startIndex)) {
				start = GetterUtil.getLongStrict(startIndex);
			}

			long end = _logFile.length();

			if (Validator.isNotNull(endIndex)) {
				long parsedEnd = GetterUtil.getLongStrict(endIndex);

				if (parsedEnd < end) {
					end = parsedEnd;
				}
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
		sb.append(_logFile.getName());
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
			_mimeTypes.getContentType(_logFile),
			_mockHttpServletResponse.getContentType());

		try (FileChannel fileChannel = FileChannel.open(_logFile.toPath());
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

			Assert.assertEquals(clazz, throwable.getClass());
			Assert.assertEquals(message, throwable.getMessage());
		}
	}

	private MockHttpServletRequest _createMockHttpServletRequest(
		String startIndex, String endIndex) {

		MockHttpServletRequest mockHttpServletRequest =
			_createMockHttpServletRequest(
				StringBundler.concat(
					StringPool.SLASH, _newCompany.getCompanyId(),
					StringPool.SLASH, _logFile.getName()),
				_adminUser);

		mockHttpServletRequest.setParameter("startIndex", startIndex);
		mockHttpServletRequest.setParameter("endIndex", endIndex);

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
	private static File _logFile;
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