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

package com.liferay.html.preview.processor.image.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.html.preview.model.HtmlPreview;
import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.awt.image.BufferedImage;

import java.io.File;

import org.osgi.service.component.annotations.Component;

import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = HtmlPreviewProcessor.class)
public class ImageHtmlPreviewProcessor implements HtmlPreviewProcessor {

	@Override
	public FileEntry generateHtmlPreview(
			long userId, long groupId, String content)
		throws PortalException {

		File tempFile = FileUtil.createTempFile();

		try {
			FileUtil.write(tempFile, content);

			Java2DRenderer renderer = new Java2DRenderer(tempFile, 1024);

			renderer.setBufferedImageType(BufferedImage.TYPE_INT_RGB);

			BufferedImage image = renderer.getImage();

			FSImageWriter imageWriter = new FSImageWriter();

			File outputFile = FileUtil.createTempFile("png");

			imageWriter.write(image, outputFile.getAbsolutePath());

			FileEntry fileEntry = PortletFileRepositoryUtil.addPortletFileEntry(
				groupId, userId, HtmlPreview.class.getName(), 0,
				HtmlPreview.class.getName(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, tempFile,
				tempFile.getName(), getMimeType(), false);

			return fileEntry;
		}
		catch (Exception e) {
			_log.error("Unable to generate a preview image file entry", e);

			throw new PortalException(e);
		}
	}

	@Override
	public String getMimeType() {
		return ContentTypes.IMAGE_PNG;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageHtmlPreviewProcessor.class);

}