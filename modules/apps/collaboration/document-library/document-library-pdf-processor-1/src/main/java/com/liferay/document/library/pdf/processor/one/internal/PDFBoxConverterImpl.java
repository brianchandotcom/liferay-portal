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

import com.liferay.document.library.kernel.util.PDFBoxConverter;

import java.io.File;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(service = PDFBoxConverter.class)
public class PDFBoxConverterImpl implements PDFBoxConverter {

	@Override
	public void generateImagesPB(
			File inputFile, File thumbnailFile, File[] previewFiles,
			String extension, String thumbnailExtension, int dpi, int height,
			int width, boolean generatePreview, boolean generateThumbnail)
		throws Exception {

		LiferayPDFBoxConverterUtil.generateImagesPB(
			inputFile, thumbnailFile, previewFiles, extension,
			thumbnailExtension, dpi, height, width, generatePreview,
			generateThumbnail);
	}

}