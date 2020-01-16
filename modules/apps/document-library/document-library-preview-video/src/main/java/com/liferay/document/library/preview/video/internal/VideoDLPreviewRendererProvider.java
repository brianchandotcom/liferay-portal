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

package com.liferay.document.library.preview.video.internal;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
import com.liferay.document.library.preview.video.internal.constants.DLPreviewVideoWebKeys;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLPreviewRendererProvider.class)
public class VideoDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Set<String> getMimeTypes() {
		return _videoProcessor.getVideoMimeTypes();
	}

	@Override
	public DLPreviewRenderer getPreviewDLPreviewRenderer(
		FileVersion fileVersion) {

		if (!_videoProcessor.isVideoSupported(fileVersion)) {
			return null;
		}

		return (request, response) -> {
			checkForPreviewGenerationExceptions(fileVersion);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/preview/view.jsp");

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			String videoPosterURL = _getVideoPosterURL(
				fileVersion, themeDisplay);

			request.setAttribute(
				DLPreviewVideoWebKeys.PREVIEW_FILE_URLS,
				_getPreviewFileURLs(fileVersion, videoPosterURL, request));

			request.setAttribute(
				DLPreviewVideoWebKeys.VIDEO_POSTER_URL, videoPosterURL);

			requestDispatcher.include(request, response);
		};
	}

	@Override
	public DLPreviewRenderer getThumbnailDLPreviewRenderer(
		FileVersion fileVersion) {

		return null;
	}

	protected void checkForPreviewGenerationExceptions(FileVersion fileVersion)
		throws PortalException {

		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			throw new DLFileEntryPreviewGenerationException();
		}

		if (!_videoProcessor.hasVideo(fileVersion)) {
			if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
				throw new DLPreviewSizeException();
			}

			throw new DLPreviewGenerationInProcessException();
		}
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.VIDEO_PROCESSOR + ")",
		unbind = "-"
	)
	protected void setDLProcessor(DLProcessor dlProcessor) {
		_videoProcessor = (VideoProcessor)dlProcessor;
	}

	private List<String> _getPreviewFileURLs(
			FileVersion fileVersion, String videoPosterURL,
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
						fileVersion, dlFileEntryPreviewVideoContainer);

					if (previewFileSize > 0) {
						previewFileURLs.add(
							_dlURLHelper.getPreviewURL(
								fileVersion.getFileEntry(), fileVersion,
								themeDisplay,
								previewQueryString + "&type=" +
									dlFileEntryPreviewVideoContainer));
					}
				}

				if (previewFileURLs.isEmpty()) {
					throw new DLFileEntryPreviewGenerationException(
						"No preview available for " + fileVersion.getTitle());
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
			FileVersion fileVersion, ThemeDisplay themeDisplay)
		throws PortalException {

		return _dlURLHelper.getPreviewURL(
			fileVersion.getFileEntry(), fileVersion, themeDisplay,
			"&videoThumbnail=1");
	}

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.video)"
	)
	private ServletContext _servletContext;

	private VideoProcessor _videoProcessor;

}