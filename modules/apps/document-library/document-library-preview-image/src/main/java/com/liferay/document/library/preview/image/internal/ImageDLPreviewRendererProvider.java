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

package com.liferay.document.library.preview.image.internal;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.ImageProcessor;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author Alejandro Tardín
 */
public class ImageDLPreviewRendererProvider
	implements DLPreviewRendererProvider {

	public ImageDLPreviewRendererProvider(
		ImageProcessor imageProcessor,
		DLFileVersionPreviewLocalService dlFileVersionPreviewLocalService,
		ServletContext servletContext) {

		_imageProcessor = imageProcessor;
		_dlFileVersionPreviewLocalService = dlFileVersionPreviewLocalService;
		_servletContext = servletContext;
	}

	@Override
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion) {

		if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
			return Optional.of(
				(httpServletRequest, httpServletResponse) -> {
					throw new DLPreviewSizeException();
				});
		}

		if (!_imageProcessor.isImageSupported(fileVersion)) {
			return Optional.empty();
		}

		return Optional.of(
			(request, response) -> {
				checkForPreviewGenerationExceptions(fileVersion);

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher("/preview/view.jsp");

				request.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

				requestDispatcher.include(request, response);
			});
	}

	@Override
	public Optional<DLPreviewRenderer> getThumbnailDLPreviewRendererOptional(
		FileVersion fileVersion) {

		return Optional.empty();
	}

	protected void checkForPreviewGenerationExceptions(FileVersion fileVersion)
		throws PortalException {

		if (_dlFileVersionPreviewLocalService.hasDLFileVersionPreview(
				fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
				DLFileVersionPreviewConstants.STATUS_FAILURE)) {

			throw new DLFileEntryPreviewGenerationException();
		}

		if (!_imageProcessor.hasImages(fileVersion)) {
			throw new DLPreviewGenerationInProcessException();
		}
	}

	private final DLFileVersionPreviewLocalService
		_dlFileVersionPreviewLocalService;
	private final ImageProcessor _imageProcessor;
	private final ServletContext _servletContext;

}