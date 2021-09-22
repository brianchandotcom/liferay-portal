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

package com.liferay.document.library.preview.audio.internal;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.audio.internal.constants.DLPreviewAudioWebKeys;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
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
public class AudioDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Set<String> getMimeTypes() {
		return _audioProcessor.getAudioMimeTypes();
	}

	@Override
	public DLPreviewRenderer getPreviewDLPreviewRenderer(
		FileVersion fileVersion) {

		if (!_audioProcessor.hasAudio(fileVersion) &&
			!_audioProcessor.isAudioSupported(fileVersion)) {

			return null;
		}

		return (request, response) -> {
			checkForPreviewGenerationExceptions(fileVersion);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/preview/view.jsp");

			request.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

			request.setAttribute(
				DLPreviewAudioWebKeys.PREVIEW_FILE_URLS,
				_getPreviewFileURLs(fileVersion, request));

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

		if (!_audioProcessor.hasAudio(fileVersion)) {
			if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
				throw new DLPreviewSizeException();
			}

			throw new DLPreviewGenerationInProcessException();
		}
	}

	private List<String> _getPreviewFileURLs(
			FileVersion fileVersion, HttpServletRequest httpServletRequest)
		throws PortalException {

		int status = ParamUtil.getInteger(
			httpServletRequest, "status", WorkflowConstants.STATUS_ANY);

		String previewQueryString = "&audioPreview=1";

		if (status != WorkflowConstants.STATUS_ANY) {
			previewQueryString += "&status=" + status;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<String> previewFileURLs = new ArrayList<>();

		try {
			for (String dlFileEntryPreviewAudioContainer :
					PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS) {

				long previewFileSize = _audioProcessor.getPreviewFileSize(
					fileVersion, dlFileEntryPreviewAudioContainer);

				if (previewFileSize > 0) {
					previewFileURLs.add(
						_dlURLHelper.getPreviewURL(
							fileVersion.getFileEntry(), fileVersion,
							themeDisplay,
							previewQueryString + "&type=" +
								dlFileEntryPreviewAudioContainer));
				}
			}
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}

		if (previewFileURLs.isEmpty()) {
			throw new PortalException(
				"No preview available for " + fileVersion.getTitle());
		}

		return previewFileURLs;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private AudioProcessor _audioProcessor;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.audio)"
	)
	private ServletContext _servletContext;

}