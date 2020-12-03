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

import com.liferay.document.library.external.video.DLExternalVideo;
import com.liferay.document.library.external.video.resolver.DLExternalVideoResolver;
import com.liferay.document.library.kernel.model.DLProcessorConstants;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

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
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/preview/view.jsp");

			request.setAttribute(
				DLExternalVideo.class.getName(),
				_dlExternalVideoResolver.resolve(fileVersion.getFileEntry()));

			requestDispatcher.include(request, response);
		};
	}

	@Override
	public DLPreviewRenderer getThumbnailDLPreviewRenderer(
		FileVersion fileVersion) {

		return null;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + DLProcessorConstants.VIDEO_PROCESSOR + ")",
		unbind = "-"
	)
	protected void setDLProcessor(DLProcessor dlProcessor) {
		_videoProcessor = (VideoProcessor)dlProcessor;
	}

	@Reference
	private DLExternalVideoResolver _dlExternalVideoResolver;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.video)"
	)
	private ServletContext _servletContext;

	private VideoProcessor _videoProcessor;

}