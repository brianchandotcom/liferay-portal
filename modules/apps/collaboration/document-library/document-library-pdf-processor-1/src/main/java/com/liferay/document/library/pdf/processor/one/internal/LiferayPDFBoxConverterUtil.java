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

package com.liferay.document.library.pdf.processor.one.internal;

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
 * @author Preston Crary
 */
public class LiferayPDFBoxConverterUtil {

	public static void generateImagesPB(
			File inputFile, File thumbnailFile, File[] previewFiles,
			String extension, String thumbnailExtension, int dpi, int height,
			int width, boolean generatePreview, boolean generateThumbnail)
		throws Exception {

		try (PDDocument pdDocument = PDDocument.load(inputFile)) {
			PDDocumentCatalog pdDocumentCatalog =
				pdDocument.getDocumentCatalog();

			List<PDPage> pdPages = pdDocumentCatalog.getAllPages();

			for (int i = 0; i < pdPages.size(); i++) {
				PDPage pdPage = pdPages.get(i);

				if (generateThumbnail && (i == 0)) {
					_generateImagesPB(
						pdPage, thumbnailFile, thumbnailExtension, dpi, height,
						width);
				}

				if (!generatePreview) {
					break;
				}

				_generateImagesPB(
					pdPage, previewFiles[i], extension, dpi, height, width);
			}
		}
	}

	private static void _generateImagesPB(
			PDPage pdPage, File outputFile, String extension, int dpi,
			int height, int width)
		throws Exception {

		RenderedImage renderedImage = pdPage.convertToImage(
			BufferedImage.TYPE_INT_RGB, dpi);

		ImageTool imageTool = ImageToolImpl.getInstance();

		if (height != 0) {
			renderedImage = imageTool.scale(renderedImage, height, width);
		}
		else {
			renderedImage = imageTool.scale(renderedImage, width);
		}

		outputFile.createNewFile();

		ImageIO.write(renderedImage, extension, outputFile);
	}

}