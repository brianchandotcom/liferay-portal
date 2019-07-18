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

package com.liferay.html.preview.processor.image.internal;

import com.liferay.html.preview.processor.HtmlPreviewProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.awt.image.BufferedImage;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import org.osgi.service.component.annotations.Component;

import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = HtmlPreviewProcessor.class)
public class ImageHtmlPreviewProcessor implements HtmlPreviewProcessor {

	@Override
	public File generateContentHtmlPreview(String content) {
		return generateContentHtmlPreview(
			content, HtmlPreviewProcessor.WIDTH_DEFAULT);
	}

	@Override
	public File generateContentHtmlPreview(String content, int width) {
		try {
			File tempFile = FileUtil.createTempFile();

			Document document = Jsoup.parse(content);

			Document.OutputSettings outputSettings = document.outputSettings();

			outputSettings.syntax(Document.OutputSettings.Syntax.xml);
			outputSettings.escapeMode(Entities.EscapeMode.xhtml);

			FileUtil.write(tempFile, document.html());

			return _getFile(new Java2DRenderer(tempFile, width));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to generate HTML preview", e);
			}
		}

		return null;
	}

	@Override
	public File generateURLHtmlPreview(String url) {
		return generateURLHtmlPreview(url, HtmlPreviewProcessor.WIDTH_DEFAULT);
	}

	@Override
	public File generateURLHtmlPreview(String url, int width) {
		try {
			return _getFile(new Java2DRenderer(url, width));
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.error("Unable to generate HTML preview", e);
			}
		}

		return null;
	}

	@Override
	public String getMimeType() {
		return ContentTypes.IMAGE_PNG;
	}

	private File _getFile(Java2DRenderer renderer) throws Exception {
		renderer.setBufferedImageType(BufferedImage.TYPE_INT_RGB);

		File outputFile = FileUtil.createTempFile("png");

		FSImageWriter imageWriter = new FSImageWriter();

		imageWriter.write(renderer.getImage(), outputFile.getAbsolutePath());

		return outputFile;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageHtmlPreviewProcessor.class);

}