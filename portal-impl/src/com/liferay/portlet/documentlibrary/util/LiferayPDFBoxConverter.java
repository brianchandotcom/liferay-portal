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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.image.ImageTool;

import java.awt.image.RenderedImage;

import java.io.File;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * @author Juan González
 */
public class LiferayPDFBoxConverter {

	public LiferayPDFBoxConverter(
		File inputFile, File thumbnailFile, File[] previewFiles,
		String extension, String thumbnailExtension, int dpi, int height,
		int width, boolean generatePreview, boolean generateThumbnail) {

		_inputFile = inputFile;
		_thumbnailFile = thumbnailFile;
		_previewFiles = previewFiles;
		_extension = extension;
		_thumbnailExtension = thumbnailExtension;
		_dpi = dpi;
		_height = height;
		_width = width;
		_generatePreview = generatePreview;
		_generateThumbnail = generateThumbnail;
	}

	public void generateImagesPB() throws Exception {
		try (PDDocument pdDocument = PDDocument.load(_inputFile)) {
			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

			PDPageTree pdPageTree = pdDocument.getPages();

			int count = pdPageTree.getCount();

			for (int i = 0; i < count; i++) {
				if (_generateThumbnail && (i == 0)) {
					ImageIO.write(
						_toRenderedImage(pdfRenderer, i), _thumbnailExtension,
						_thumbnailFile);
				}

				if (!_generatePreview) {
					break;
				}

				ImageIO.write(
					_toRenderedImage(pdfRenderer, i), _extension,
					_previewFiles[i]);
			}
		}
	}

	private RenderedImage _toRenderedImage(
			PDFRenderer pdfRenderer, int pageIndex)
		throws Exception {

		RenderedImage renderedImage = pdfRenderer.renderImageWithDPI(
			pageIndex, _dpi, ImageType.RGB);

		ImageTool imageTool = ImageToolImpl.getInstance();

		if (_height == 0) {
			return imageTool.scale(renderedImage, _width);
		}

		return imageTool.scale(renderedImage, _height, _width);
	}

	private final int _dpi;
	private final String _extension;
	private final boolean _generatePreview;
	private final boolean _generateThumbnail;
	private final int _height;
	private final File _inputFile;
	private final File[] _previewFiles;
	private final String _thumbnailExtension;
	private final File _thumbnailFile;
	private final int _width;

}