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

package com.liferay.document.library.analytics.internal.servlet;

import com.liferay.document.library.analytics.internal.constants.DocumentLibraryAnalyticsConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.analytics.internal.servlet.FileEntryUUIDResolverServlet",
		"osgi.http.whiteboard.servlet.pattern=" +
			DocumentLibraryAnalyticsConstants.SERVLET_PATTERN,
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class FileEntryUUIDResolverServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			long groupId = ParamUtil.getLong(request, "groupId");
			String uuid = ParamUtil.getString(request, "uuid");

			_sendSuccess(
				response,
				_dlAppService.getFileEntryByUuidAndGroupId(uuid, groupId));
		}
		catch (PrincipalException pe) {
			_sendError(response, 403, pe);
		}
		catch (Exception e) {
			_sendError(response, 500, e);
		}
	}

	private void _sendError(
		HttpServletResponse response, int status, Throwable throwable) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("error", throwable.getMessage());

			PrintWriter printWriter = response.getWriter();

			printWriter.write(jsonObject.toString());

			response.setStatus(status);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			response.setStatus(500);
		}
	}

	private void _sendSuccess(HttpServletResponse response, FileEntry fileEntry)
		throws IOException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("fileEntryId", fileEntry.getFileEntryId());

		PrintWriter printWriter = response.getWriter();

		printWriter.write(jsonObject.toString());

		response.setStatus(200);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryUUIDResolverServlet.class);

	@Reference
	private DLAppService _dlAppService;

}