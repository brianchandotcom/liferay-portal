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

import com.liferay.document.library.kernel.util.PDFBoxConverter;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.File;

/**
 * @author Juan Gonzalez
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
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
		_pdfBoxConverter.generateImagesPB(
			_inputFile, _thumbnailFile, _previewFiles, _extension,
			_thumbnailExtension, _dpi, _height, _width, _generatePreview,
			_generateThumbnail);
	}

	@SuppressWarnings("deprecation")
	private static volatile PDFBoxConverter _pdfBoxConverter =
		ServiceProxyFactory.newServiceTrackedInstance(
			PDFBoxConverter.class, LiferayPDFBoxConverter.class,
			"_pdfBoxConverter", true);

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