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

package com.liferay.portal.company.log.internal.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.log4j.Log4JUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Hai Yu
 */
@Component(
	enabled = false,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.portal.company.log.internal.servlet.CompanyLogServlet",
		"osgi.http.whiteboard.servlet.pattern=/company-log/*",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class CompanyLogServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			String path = HttpComponentsUtil.fixPath(
				httpServletRequest.getPathInfo());

			String[] pathArray = StringUtil.split(path, CharPool.SLASH);

			if (pathArray.length == 0) {
				_listCompaniesLogFiles(httpServletRequest, httpServletResponse);
			}
			else if (pathArray.length == 2) {
				_downloadLogFile(
					httpServletRequest, httpServletResponse, pathArray);
			}
		}
		catch (FileNotFoundException fileNotFoundException) {
			if (_log.isWarnEnabled()) {
				_log.warn(fileNotFoundException);
			}

			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, fileNotFoundException,
				httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			_portal.sendError(
				exception, httpServletRequest, httpServletResponse);
		}
	}

	private void _downloadLogFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String[] pathArray)
		throws Exception {

		long companyId = GetterUtil.getLongStrict(pathArray[0]);

		if (_companyLocalService.fetchCompanyById(companyId) == null) {
			throw new NoSuchCompanyException(
				"No Company exists with the primary key " + companyId);
		}

		PermissionChecker permissionChecker = _getPermissionChecker(
			httpServletRequest);

		if (!permissionChecker.isCompanyAdmin(companyId)) {
			throw new PrincipalException.MustBeCompanyAdmin(
				permissionChecker.getUserId());
		}

		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(companyId);

		String fileName = pathArray[1];

		Path path = Paths.get(companyLogDirectory.getPath(), fileName);

		path = path.normalize();

		if (!path.startsWith(companyLogDirectory.getPath())) {
			throw new PrincipalException("Unauthorized access");
		}

		File logFile = path.toFile();

		if (!logFile.exists()) {
			throw new FileNotFoundException(
				StringBundler.concat(
					"Unable to find log file ", fileName, " for company ",
					companyId));
		}

		String startIndex = ParamUtil.getString(
			httpServletRequest, "startIndex");
		String endIndex = ParamUtil.getString(httpServletRequest, "endIndex");

		if (Validator.isNull(startIndex) && Validator.isNull(endIndex)) {
			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, fileName,
				Files.newInputStream(logFile.toPath()), logFile.length(),
				_mimeTypes.getContentType(fileName),
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		else {
			long start = 0;

			if (Validator.isNotNull(startIndex)) {
				start = GetterUtil.getLongStrict(startIndex);
			}

			long end = logFile.length();

			if (Validator.isNotNull(endIndex)) {
				long parsedEnd = GetterUtil.getLongStrict(endIndex);

				if (parsedEnd < end) {
					end = parsedEnd;
				}
			}

			if ((start < 0) || (end < 0) || (start >= end)) {
				throw new PrincipalException(
					"startIndex or endIndex can not be less than 0, and " +
						"startIndex can not be greater than or equal to " +
							"endIndex");
			}

			if (start != 0) {
				--start;
			}

			try (FileChannel fileChannel = FileChannel.open(logFile.toPath())) {
				fileChannel.position(start);

				ServletResponseUtil.sendFile(
					httpServletRequest, httpServletResponse, fileName,
					Channels.newInputStream(fileChannel), end - start,
					_mimeTypes.getContentType(fileName),
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
			}
		}
	}

	private PermissionChecker _getPermissionChecker(
			HttpServletRequest httpServletRequest)
		throws Exception {

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			throw new PrincipalException(
				"The current user is not authenticated");
		}

		return _permissionCheckerFactory.create(user);
	}

	private void _listCompaniesLogFiles(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PermissionChecker permissionChecker = _getPermissionChecker(
			httpServletRequest);

		JSONArray companyLogFilesJSONArray = _jsonFactory.createJSONArray();

		if (permissionChecker.isOmniadmin()) {
			_companyLocalService.forEachCompany(
				company -> _listCompanyLogFiles(
					httpServletRequest, company, companyLogFilesJSONArray));
		}
		else if (permissionChecker.isCompanyAdmin()) {
			User user = permissionChecker.getUser();

			_listCompanyLogFiles(
				httpServletRequest,
				_companyLocalService.getCompany(user.getCompanyId()),
				companyLogFilesJSONArray);
		}
		else {
			throw new PrincipalException.MustBeCompanyAdmin(
				permissionChecker.getUserId());
		}

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse, companyLogFilesJSONArray.toString());
	}

	private void _listCompanyLogFiles(
			HttpServletRequest httpServletRequest, Company company,
			JSONArray companyLogFilesJSONArray)
		throws Exception {

		File companyLogDirectory = Log4JUtil.getCompanyLogDirectory(
			company.getCompanyId());

		JSONObject companyLogFileJSONObject = _jsonFactory.createJSONObject();

		companyLogFileJSONObject.put(
			"companyId", company.getCompanyId()
		).put(
			"webId", company.getWebId()
		);

		JSONArray companyLogFileInfosJSONArray = _jsonFactory.createJSONArray();

		File[] companyLogFiles = companyLogDirectory.listFiles();

		Arrays.sort(companyLogFiles);

		for (File file : companyLogFiles) {
			JSONObject companyLogFileInfoJSONObject =
				_jsonFactory.createJSONObject();

			companyLogFileInfoJSONObject.put(
				"logFileName", file.getName()
			).put(
				"logFileSize",
				_language.formatStorageSize(
					file.length(), httpServletRequest.getLocale())
			);

			companyLogFileInfosJSONArray.put(companyLogFileInfoJSONObject);
		}

		companyLogFileJSONObject.put(
			"companyLogFileInfos", companyLogFileInfosJSONArray);

		companyLogFilesJSONArray.put(companyLogFileJSONObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyLogServlet.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private MimeTypes _mimeTypes;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

}