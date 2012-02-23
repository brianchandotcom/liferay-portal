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

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.BaseTestCase;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * @author Shuyang Zhou
 */
public class ImageToolImplTest extends BaseTestCase {

	public void testReadBMP() throws Exception {
		testRead("liferay.bmp");
	}

	public void testReadGIF() throws Exception {
		testRead("liferay.gif");
	}

	public void testReadJPEG() throws Exception {
		testRead("liferay.jpeg");
	}

	public void testReadJPG() throws Exception {
		testRead("liferay.jpg");
	}

	public void testReadPNG() throws Exception {
		testRead("liferay.png");
	}

	protected void testRead(String fileName) throws Exception {
		fileName =
			"portal-impl/test/integration/" +
				"com/liferay/portal/image/dependencies/" + fileName;

		File file = new File(fileName);

		BufferedImage expectedImage = ImageIO.read(file);

		assertNotNull(expectedImage);

		DataBufferByte expectedDataBufferByte =
			(DataBufferByte)expectedImage.getData().getDataBuffer();

		byte[][] expectedData = expectedDataBufferByte.getBankData();

		String expectedType = FileUtil.getExtension(fileName);

		if (expectedType.equals("jpeg")) {
			expectedType = ImageTool.TYPE_JPEG;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		byte[] bytes = new byte[(int)randomAccessFile.length()];

		randomAccessFile.readFully(bytes);

		ImageBag imageBag = ImageToolUtil.read(bytes);

		RenderedImage resultImage = imageBag.getRenderedImage();

		assertNotNull(resultImage);

		DataBufferByte resultDataBufferByte =
			(DataBufferByte)resultImage.getData().getDataBuffer();

		byte[][] resultData = resultDataBufferByte.getBankData();

		String resultType = imageBag.getType();

		assertTrue(expectedType.equalsIgnoreCase(resultType));
		assertTrue(Arrays.deepEquals(expectedData, resultData));
	}

}