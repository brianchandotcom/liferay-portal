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

package com.liferay.document.library.preview.pdf.internal;

import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"content.type=" + ContentTypes.APPLICATION_PDF,
		"content.type=" + ContentTypes.APPLICATION_X_PDF
	},
	service = DLPreviewRendererProvider.class
)
public class PDFDLPreviewRendererProvider implements DLPreviewRendererProvider {

	@Override
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion) {

		if (!PDFProcessorUtil.isDocumentSupported(fileVersion)) {
			return Optional.empty();
		}

		return Optional.of(
			(request, response) -> {
				if (!PDFProcessorUtil.hasImages(fileVersion)) {
					if (!DLProcessorRegistryUtil.isPreviewableSize(
							fileVersion)) {

						throw new DLPreviewGenerationInProcessException();
					}

					throw new DLPreviewGenerationInProcessException();
				}

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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.pdf)"
	)
	private ServletContext _servletContext;

}