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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"mvc.command.name=/document_library/embed_video"
	},
	service = MVCRenderCommand.class
)
public class EmbedVideoMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			FileEntry fileEntry = _getFileEntry(
				ParamUtil.getString(renderRequest, "url"));

			if (fileEntry != null) {
				FileVersion fileVersion = fileEntry.getFileVersion();

				renderRequest.setAttribute(
					FileEntry.class.getName(), fileEntry);

				if (_isPreviewFailure(fileVersion)) {
					return "/document_library/embed/error.jsp";
				}
				else if (!_videoProcessor.hasVideo(fileVersion)) {
					return "/document_library/embed/generating.jsp";
				}
				else {
					String videoPosterURL = _getVideoPosterURL(
						fileEntry,
						(ThemeDisplay)renderRequest.getAttribute(
							WebKeys.THEME_DISPLAY));

					renderRequest.setAttribute(
						DLWebKeys.PREVIEW_FILE_URLS,
						_getPreviewFileURLs(
							fileEntry, videoPosterURL, renderRequest));

					renderRequest.setAttribute(
						DLWebKeys.VIDEO_POSTER_URL, videoPosterURL);

					return "/document_library/embed/video.jsp";
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return "/document_library/embed/error.jsp";
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

	private boolean _isPreviewFailure(FileVersion fileVersion) {
		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			return true;
		}

		if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmbedVideoMVCRenderCommand.class);

	private static final Pattern _urlPattern = Pattern.compile(
		".*\\/documents\\/(.+)\\/.+\\/.+\\/([^?]+).*");

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	private VideoProcessor _videoProcessor;

}