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

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;

import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * @author Juan Gonzalez
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
			PDDocumentCatalog pdDocumentCatalog =
				pdDocument.getDocumentCatalog();

			List<PDPage> pdPages = pdDocumentCatalog.getAllPages();

			for (int i = 0; i < pdPages.size(); i++) {
				PDPage pdPage = pdPages.get(i);

				if (_generateThumbnail && (i == 0)) {
					_generateImagesPB(
						pdPage, _thumbnailFile, _thumbnailExtension);
				}

				if (!_generatePreview) {
					break;
				}

				_generateImagesPB(pdPage, _previewFiles[i], _extension);
			}
		}
	}

	private void _generateImagesPB(
			PDPage pdPage, File outputFile, String extension)
		throws Exception {

		RenderedImage renderedImage = pdPage.convertToImage(
			BufferedImage.TYPE_INT_RGB, _dpi);

		ImageTool imageTool = ImageToolImpl.getInstance();

		if (_height != 0) {
			renderedImage = imageTool.scale(renderedImage, _width, _height);
		}
		else {
			renderedImage = imageTool.scale(renderedImage, _width);
		}

		outputFile.createNewFile();

		ImageIO.write(renderedImage, extension, outputFile);
	}

	private int _dpi;
	private String _extension;
	private boolean _generatePreview;
	private boolean _generateThumbnail;
	private int _height;
	private File _inputFile;
	private File[] _previewFiles;
	private String _thumbnailExtension;
	private File _thumbnailFile;
	private int _width;

}