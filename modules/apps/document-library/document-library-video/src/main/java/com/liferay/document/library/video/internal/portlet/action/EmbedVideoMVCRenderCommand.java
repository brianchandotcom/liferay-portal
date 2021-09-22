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

package com.liferay.document.library.video.internal.portlet.action;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.video.internal.constants.DLVideoPortletKeys;
import com.liferay.document.library.video.internal.constants.DLVideoWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DLVideoPortletKeys.DL_VIDEO,
		"mvc.command.name=/document_library_video/embed_video"
	},
	service = MVCRenderCommand.class
)
public class EmbedVideoMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			FileVersion fileVersion = _dlAppLocalService.getFileVersion(
				ParamUtil.getLong(renderRequest, "fileVersionId"));

			if (fileVersion != null) {
				renderRequest.setAttribute(
					FileVersion.class.getName(), fileVersion);

				if (_videoProcessor.hasVideo(fileVersion)) {
					String videoPosterURL = _getVideoPosterURL(
						fileVersion,
						(ThemeDisplay)renderRequest.getAttribute(
							WebKeys.THEME_DISPLAY));

					renderRequest.setAttribute(
						DLVideoWebKeys.PREVIEW_FILE_URLS,
						_getPreviewFileURLs(
							fileVersion, videoPosterURL, renderRequest));

					renderRequest.setAttribute(
						DLVideoWebKeys.VIDEO_POSTER_URL, videoPosterURL);

					return "/embed/video.jsp";
				}
				else if (_isPreviewFailure(fileVersion)) {
					return "/embed/error.jsp";
				}
				else {
					return "/embed/generating.jsp";
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return "/embed/error.jsp";
	}

	private List<String> _getPreviewFileURLs(
			FileVersion fileVersion, String videoPosterURL,
			RenderRequest renderRequest)
		throws PortalException {

		int status = ParamUtil.getInteger(
			renderRequest, "status", WorkflowConstants.STATUS_ANY);

		String previewQueryString = "&videoPreview=1";

		if (status != WorkflowConstants.STATUS_ANY) {
			previewQueryString += "&status=" + status;
		}

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS.length > 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

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

	private boolean _isPreviewFailure(FileVersion fileVersion) {
		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE) ||
			!_videoProcessor.isVideoSupported(fileVersion)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbedVideoMVCRenderCommand.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private VideoProcessor _videoProcessor;

}