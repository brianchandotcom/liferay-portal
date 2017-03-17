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

package com.liferay.document.library.pdf.processor.two.internal;

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
 * @author Preston Crary
 */
public class LiferayPDFBoxConverterUtil {

	public static void generateImagesPB(
			File inputFile, File thumbnailFile, File[] previewFiles,
			String extension, String thumbnailExtension, int dpi, int height,
			int width, boolean generatePreview, boolean generateThumbnail)
		throws Exception {

		try (PDDocument pdDocument = PDDocument.load(inputFile)) {
			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

			PDPageTree pdPageTree = pdDocument.getPages();

			int count = pdPageTree.getCount();

			for (int i = 0; i < count; i++) {
				if (generateThumbnail && (i == 0)) {
					_generateImagesPB(
						pdfRenderer, i, thumbnailFile, thumbnailExtension, dpi,
						height, width);
				}

				if (!generatePreview) {
					break;
				}

				_generateImagesPB(
					pdfRenderer, i, previewFiles[i], extension, dpi, height,
					width);
			}
		}
	}

	private static void _generateImagesPB(
			PDFRenderer pdfRenderer, int pageIndex, File outputFile,
			String extension, int dpi, int height, int width)
		throws Exception {

		RenderedImage renderedImage = pdfRenderer.renderImageWithDPI(
			pageIndex, dpi, ImageType.RGB);

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