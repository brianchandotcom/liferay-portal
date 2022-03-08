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
public class LiferayPDFBoxUtil {

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
				RenderedImage renderedImage = _toRenderedImage(
					pdfRenderer, i, dpi, height, width);

				if (generateThumbnail && (i == 0)) {
					ImageIO.write(
						renderedImage, thumbnailExtension, thumbnailFile);
				}

				if (!generatePreview) {
					break;
				}

				ImageIO.write(renderedImage, extension, previewFiles[i]);
			}
		}
	}

	private static RenderedImage _toRenderedImage(
			PDFRenderer pdfRenderer, int pageIndex, int dpi, int height,
			int width)
		throws Exception {

		RenderedImage renderedImage = pdfRenderer.renderImageWithDPI(
			pageIndex, dpi, ImageType.RGB);

		ImageTool imageTool = ImageToolImpl.getInstance();

		if (height == 0) {
			return imageTool.scale(renderedImage, width);
		}

		return imageTool.scale(renderedImage, height, width);
	}

}