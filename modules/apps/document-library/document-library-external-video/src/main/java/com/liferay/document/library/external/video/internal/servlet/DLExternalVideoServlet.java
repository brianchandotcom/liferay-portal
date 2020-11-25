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

package com.liferay.document.library.external.video.internal.servlet;

import com.liferay.document.library.external.video.internal.constants.DLExternalVideoConstants;
import com.liferay.document.library.external.video.internal.constants.DLExternalVideoWebKeys;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.document.library.external.video.internal.servlet.DLExternalVideoServlet",
		"osgi.http.whiteboard.servlet.pattern=/" + DLExternalVideoConstants.SERVLET_PATH + "/*",
		"servlet.init.httpMethods=GET,HEAD"
	},
	service = Servlet.class
)
public class DLExternalVideoServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			FileEntry fileEntry = _getFileEntry(
				ParamUtil.getString(httpServletRequest, "url"));

			if (fileEntry != null) {
				String videoPosterURL = _getVideoPosterURL(
					fileEntry,
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY));

				httpServletRequest.setAttribute(
					DLExternalVideoWebKeys.PREVIEW_FILE_URLS,
					_getPreviewFileURLs(
						fileEntry, videoPosterURL, httpServletRequest));

				httpServletRequest.setAttribute(
					DLExternalVideoWebKeys.VIDEO_POSTER_URL, videoPosterURL);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher("/video.jsp");

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.VIDEO_PROCESSOR + ")",
		unbind = "-"
	)
	protected void setDLProcessor(DLProcessor dlProcessor) {
		_videoProcessor = (VideoProcessor)dlProcessor;
	}

	private FileEntry _getFileEntry(String url) throws PortalException {
		Matcher matcher = _urlPattern.matcher(url);

		if (matcher.matches()) {
			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				matcher.group(2), Integer.valueOf(matcher.group(1)));
		}

		return null;
	}

	private List<String> _getPreviewFileURLs(
			FileEntry fileEntry, String videoPosterURL,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		int status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		String previewQueryString = "&videoPreview=1";

		if (status != WorkflowConstants.STATUS_ANY) {
			previewQueryString += "&status=" + status;
		}

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS.length > 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			List<String> previewFileURLs = new ArrayList<>();

			try {
				for (String dlFileEntryPreviewVideoContainer :
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS) {

					long previewFileSize = _videoProcessor.getPreviewFileSize(
						fileEntry.getFileVersion(),
						dlFileEntryPreviewVideoContainer);

					if (previewFileSize > 0) {
						previewFileURLs.add(
							_dlURLHelper.getPreviewURL(
								fileEntry, fileEntry.getFileVersion(),
								themeDisplay,
								previewQueryString + "&type=" +
									dlFileEntryPreviewVideoContainer));
					}
				}

				if (previewFileURLs.isEmpty()) {
					throw new DLFileEntryPreviewGenerationException(
						"No preview available for " + fileEntry.getTitle());
				}

				return previewFileURLs;
			}
			catch (Exception exception) {
				throw new PortalException(exception);
			}
		}
		else {
			return Collections.singletonList(videoPosterURL);
		}
	}

	private String _getVideoPosterURL(
			FileEntry fileEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		return _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay,
			"&videoThumbnail=1");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLExternalVideoServlet.class);

	private static final Pattern _urlPattern = Pattern.compile(
		".*\\/documents\\/(.+)\\/.+\\/.+\\/([^?]+).*");

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.external.video)"
	)
	private ServletContext _servletContext;

	private VideoProcessor _videoProcessor;

}