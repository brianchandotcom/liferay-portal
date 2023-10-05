/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.pdf.internal;

import com.liferay.portal.image.ImageToolUtil;

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
			PDDocument pdDocument, File thumbnailFile, File[] previewFiles,
			String extension, String thumbnailExtension, int dpi, int height,
			int width, boolean generatePreview, boolean generateThumbnail,
			int maxNumberOfPages)
		throws Exception {

		PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

		PDPageTree pdPageTree = pdDocument.getPages();

		if ((maxNumberOfPages == 0) ||
			(maxNumberOfPages > pdPageTree.getCount())) {

			maxNumberOfPages = pdPageTree.getCount();
		}

		for (int i = 0; i < maxNumberOfPages; i++) {
			RenderedImage renderedImage = _toRenderedImage(
				pdfRenderer, i, dpi, height, width);

			if (generateThumbnail && (i == 0)) {
				ImageIO.write(renderedImage, thumbnailExtension, thumbnailFile);
			}

			if (!generatePreview) {
				break;
			}

			ImageIO.write(renderedImage, extension, previewFiles[i]);
		}
	}

	private static RenderedImage _toRenderedImage(
			PDFRenderer pdfRenderer, int pageIndex, int dpi, int height,
			int width)
		throws Exception {

		RenderedImage renderedImage = pdfRenderer.renderImageWithDPI(
			pageIndex, dpi, ImageType.RGB);

		if (height == 0) {
			return ImageToolUtil.scale(renderedImage, width);
		}

		return ImageToolUtil.scale(renderedImage, height, width);
	}

}