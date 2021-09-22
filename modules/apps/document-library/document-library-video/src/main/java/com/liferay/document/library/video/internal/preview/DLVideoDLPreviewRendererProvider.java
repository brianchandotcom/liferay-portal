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

package com.liferay.document.library.video.internal.preview;

import com.liferay.document.library.constants.DLContentTypes;
import com.liferay.document.library.kernel.util.VideoProcessor;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.video.renderer.DLVideoRenderer;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashSet;
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
public class DLVideoDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Set<String> getMimeTypes() {
		Set<String> mimeTypes = new HashSet<>();

		mimeTypes.add(DLContentTypes.VIDEO_EXTERNAL_SHORTCUT);
		mimeTypes.addAll(_videoProcessor.getVideoMimeTypes());

		return mimeTypes;
	}

	@Override
	public DLPreviewRenderer getPreviewDLPreviewRenderer(
		FileVersion fileVersion) {

		return (request, response) -> {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/preview.jsp");

			request.setAttribute(FileVersion.class.getName(), fileVersion);

			request.setAttribute(
				DLVideoRenderer.class.getName(), _dlVideoRenderer);

			requestDispatcher.include(request, response);
		};
	}

	@Override
	public DLPreviewRenderer getThumbnailDLPreviewRenderer(
		FileVersion fileVersion) {

		return null;
	}

	@Reference
	private DLVideoRenderer _dlVideoRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.video)"
	)
	private ServletContext _servletContext;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private VideoProcessor _videoProcessor;

}