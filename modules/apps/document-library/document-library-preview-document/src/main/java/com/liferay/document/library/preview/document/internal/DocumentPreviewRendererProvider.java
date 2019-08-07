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

package com.liferay.document.library.preview.document.internal;

import com.liferay.document.library.constants.DLFileVersionPreviewConstants;
import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.document.library.preview.DLPreviewRenderer;
import com.liferay.document.library.preview.DLPreviewRendererProvider;
import com.liferay.document.library.preview.exception.DLFileEntryPreviewGenerationException;
import com.liferay.document.library.preview.exception.DLPreviewGenerationInProcessException;
import com.liferay.document.library.preview.exception.DLPreviewSizeException;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.portal.kernel.exception.PortalException;
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
		"content.type=" + ContentTypes.APPLICATION_MSWORD,
		"content.type=" + ContentTypes.APPLICATION_PDF,
		"content.type=" + ContentTypes.APPLICATION_TEXT,
		"content.type=" + ContentTypes.APPLICATION_VND_MS_EXCEL,
		"content.type=" + ContentTypes.APPLICATION_VND_MS_POWERPOINT,
		"content.type=" + ContentTypes.APPLICATION_X_PDF,
		"content.type=" + ContentTypes.TEXT_HTML,
		"content.type=" + ContentTypes.TEXT_PLAIN,
		"content.type=application/rtf",
		"content.type=application/vnd.oasis.opendocument.graphics",
		"content.type=application/vnd.oasis.opendocument.presentation",
		"content.type=application/vnd.oasis.opendocument.spreadsheet",
		"content.type=application/vnd.oasis.opendocument.text",
		"content.type=application/vnd.openxmlformats-officedocument.presentationml.presentation",
		"content.type=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		"content.type=application/vnd.openxmlformats-officedocument.wordprocessingml.document",
		"content.type=application/vnd.sun.xml.calc",
		"content.type=application/vnd.sun.xml.writer",
		"content.type=application/wordperfect", "content.type=text/rtf"
	},
	service = DLPreviewRendererProvider.class
)
public class DocumentPreviewRendererProvider
	implements DLPreviewRendererProvider {

	@Override
	public Optional<DLPreviewRenderer> getPreviewDLPreviewRendererOptional(
		FileVersion fileVersion) {

		if (!PDFProcessorUtil.isDocumentSupported(fileVersion)) {
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

		if (!PDFProcessorUtil.hasImages(fileVersion)) {
			if (!DLProcessorRegistryUtil.isPreviewableSize(fileVersion)) {
				throw new DLPreviewSizeException();
			}

			throw new DLPreviewGenerationInProcessException();
		}
	}

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.preview.document)"
	)
	private ServletContext _servletContext;

}