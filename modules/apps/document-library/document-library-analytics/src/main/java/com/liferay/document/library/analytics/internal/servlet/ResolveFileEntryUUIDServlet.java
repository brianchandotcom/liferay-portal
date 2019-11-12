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
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.analytics.internal.servlet.ResolveFileEntryUUIDServlet",
		"osgi.http.whiteboard.servlet.pattern=" + DocumentLibraryAnalyticsConstants.PATH_RESOLVE_FILE_ENTRY,
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class ResolveFileEntryUUIDServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			_sendSuccess(
				httpServletResponse,
				_getFileEntryByUuidAndGroupId(httpServletRequest));
		}
		catch (PrincipalException pe) {
			_sendError(httpServletResponse, 403, pe);
		}
		catch (Exception e) {
			_sendError(httpServletResponse, 500, e);
		}
	}

	private FileEntry _getFileEntryByUuidAndGroupId(
			HttpServletRequest httpServletRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		String uuid = ParamUtil.getString(httpServletRequest, "uuid");

		return _dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	private void _sendError(
		HttpServletResponse httpServletResponse, int status,
		Throwable throwable) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			JSONObject jsonObject = JSONUtil.put(
				"error", throwable.getMessage());

			printWriter.write(jsonObject.toString());

			httpServletResponse.setStatus(status);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			httpServletResponse.setStatus(500);
		}
	}

	private void _sendSuccess(
			HttpServletResponse httpServletResponse, FileEntry fileEntry)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		JSONObject jsonObject = JSONUtil.put(
			"fileEntryId", fileEntry.getFileEntryId());

		printWriter.write(jsonObject.toString());

		httpServletResponse.setStatus(200);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResolveFileEntryUUIDServlet.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}