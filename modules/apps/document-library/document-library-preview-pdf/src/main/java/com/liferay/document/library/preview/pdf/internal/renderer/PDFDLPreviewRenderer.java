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

package com.liferay.document.library.preview.pdf.internal.renderer;

import com.liferay.document.library.preview.renderer.DLPreviewRenderer;
import com.liferay.document.library.preview.renderer.DLPreviewRendererProvider;
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
public class PDFDLPreviewRenderer implements DLPreviewRendererProvider {

	@Override
	public Optional<DLPreviewRenderer> getPreviewRenderer(
		FileVersion fileVersion) {

		return Optional.of(
			(request, response) -> {
				request.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_VERSION, fileVersion);

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher("/page.jsp");

				requestDispatcher.include(request, response);
			});
	}

	@Override
	public Optional<DLPreviewRenderer> getThumbnailRenderer(
		FileVersion fileVersion) {

		return getPreviewRenderer(fileVersion);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.pdf)"
	)
	private ServletContext _servletContext;

}