/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.image;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageToolUtil {

	public static BufferedImage convertImageType(
		BufferedImage sourceImage, int type) {

		return getImageProcessor().convertImageType(sourceImage, type);
	}

	public static void encodeGIF(RenderedImage renderedImage, OutputStream os)
		throws IOException {

		getImageProcessor().encodeGIF(renderedImage, os);
	}

	public static void encodeWBMP(RenderedImage renderedImage, OutputStream os)
		throws InterruptedException, IOException {

		getImageProcessor().encodeWBMP(renderedImage, os);
	}

	public static BufferedImage getBufferedImage(RenderedImage renderedImage) {
		return getImageProcessor().getBufferedImage(renderedImage);
	}

	public static byte[] getBytes(
			RenderedImage renderedImage, String contentType)
		throws IOException {

		return getImageProcessor().getBytes(renderedImage, contentType);
	}

	public static ImageTool getImageProcessor() {
		return _imageProcessor;
	}

	public static ImageBag read(byte[] bytes) throws IOException {
		return getImageProcessor().read(bytes);
	}

	public static ImageBag read(File file) throws IOException {
		return getImageProcessor().read(file);
	}

	public static RenderedImage scale(RenderedImage renderedImage, int width) {
		return getImageProcessor().scale(renderedImage, width);
	}

	public static RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth) {

		return getImageProcessor().scale(renderedImage, maxHeight, maxWidth);
	}

	public void setImageProcessor(ImageTool imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	private static ImageTool _imageProcessor;

}