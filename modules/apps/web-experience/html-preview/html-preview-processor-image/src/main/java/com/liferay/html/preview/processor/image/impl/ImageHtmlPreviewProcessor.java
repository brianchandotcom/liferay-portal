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

import com.liferay.html.preview.processor.HtmlPreviewProcessor;
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
	public File generateHtmlPreview(String content) throws Exception {
		File tempFile = FileUtil.createTempFile();

		FileUtil.write(tempFile, content);

		Java2DRenderer renderer = new Java2DRenderer(tempFile, 1024);

		renderer.setBufferedImageType(BufferedImage.TYPE_INT_RGB);

		File outputFile = FileUtil.createTempFile("png");

		FSImageWriter imageWriter = new FSImageWriter();

		imageWriter.write(renderer.getImage(), outputFile.getAbsolutePath());

		return outputFile;
	}

	@Override
	public String getMimeType() {
		return ContentTypes.IMAGE_PNG;
	}

}