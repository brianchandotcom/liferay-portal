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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
	immediate = true,
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
			_checkPermission(httpServletRequest);

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
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchFileEntryException);
			}

			_portal.sendError(
				HttpServletResponse.SC_NOT_FOUND, noSuchFileEntryException,
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

	private void _checkPermission(HttpServletRequest httpServletRequest)
		throws Exception {

		User user = _portal.getUser(httpServletRequest);

		if (user == null) {
			throw new PrincipalException(
				"The current user is not company admin");
		}

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		if (!permissionChecker.isCompanyAdmin()) {
			throw new PrincipalException.MustBeCompanyAdmin(
				permissionChecker.getUserId());
		}
	}

	private void _downloadLogFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String[] pathArray)
		throws Exception {

		long companyId = Long.valueOf(pathArray[0]);

		if (_companyLocalService.fetchCompanyById(companyId) == null) {
			throw new NoSuchCompanyException(
				"No Company exists with the primary key " + companyId);
		}

		if (!_isViewCompanyAllowed(httpServletRequest, companyId)) {
			throw new PrincipalException(
				"The current user is not current company " + companyId +
					" admin");
		}

		String fileName = pathArray[1];

		String path = StringBundler.concat(
			StringUtil.replace(
				PropsUtil.get(PropsKeys.LIFERAY_HOME), '\\', '/'),
			"/logs/companies/", companyId, StringPool.SLASH, fileName);

		File logFile = new File(path);

		String canonicalPath = logFile.getCanonicalPath();

		if (File.separatorChar != CharPool.SLASH) {
			canonicalPath = StringUtil.replace(
				canonicalPath, File.separatorChar, CharPool.SLASH);
		}

		if (!path.equals(canonicalPath)) {
			throw new PrincipalException("Unauthorized access");
		}

		if (logFile.exists()) {
			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, fileName,
				new FileInputStream(logFile), logFile.length(),
				_mimeTypes.getContentType(fileName),
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		else {
			throw new NoSuchFileEntryException();
		}
	}

	private boolean _isViewCompanyAllowed(
			HttpServletRequest httpServletRequest, long companyId)
		throws Exception {

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			_portal.getUser(httpServletRequest));

		return permissionChecker.isCompanyAdmin(companyId);
	}

	private void _listCompaniesLogFiles(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletResponse.setContentType("text/html");

		PrintWriter printWriter = httpServletResponse.getWriter();

		printWriter.println("<html><body>");

		_companyLocalService.forEachCompany(
			company -> {
				if (_isViewCompanyAllowed(
						httpServletRequest, company.getCompanyId())) {

					File logFilesDir = new File(
						StringBundler.concat(
							StringUtil.replace(
								PropsUtil.get(PropsKeys.LIFERAY_HOME), '\\',
								'/'),
							"/logs/companies/", company.getCompanyId()));

					if (!logFilesDir.isDirectory()) {
						return;
					}

					printWriter.println("<h1>" + company.getWebId() + "</h1>");
					printWriter.println("<ul>");

					for (File file : logFilesDir.listFiles()) {
						String herf = StringBundler.concat(
							_portal.getPortalURL(httpServletRequest),
							_portal.getPathContext(), "/o/company-log/",
							company.getCompanyId(), StringPool.SLASH,
							file.getName());

						printWriter.println(
							"<li><a target=\"_self\" href=\"" + herf + "\">");

						printWriter.println(file.getName());
						printWriter.println("</a>");
						printWriter.println("</li>");
					}

					printWriter.println("</ul>");
				}
			});

		printWriter.println("</body></html>");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyLogServlet.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private MimeTypes _mimeTypes;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}