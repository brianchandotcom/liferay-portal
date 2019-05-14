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

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Sampsa Sohlman
 */
public class ImageToolImplTest {

	@BeforeClass
	public static void setUpClass() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testCropBMP() throws Exception {
		_testCrop("liferay.bmp");
	}

	@Test
	public void testCropGIF() throws Exception {
		_testCrop("liferay.gif");
	}

	@Test
	public void testCropJPEG() throws Exception {
		_testCrop("liferay.jpeg");
	}

	@Test
	public void testCropJPG() throws Exception {
		_testCrop("liferay.jpg");
	}

	@Test
	public void testCropPNG() throws Exception {
		_testCrop("liferay.png");
	}

	@Test
	public void testReadBMP() throws Exception {
		_testRead("liferay.bmp");
	}

	@Test
	public void testReadGIF() throws Exception {
		_testRead("liferay.gif");
	}

	@Test
	public void testReadJPEG() throws Exception {
		_testRead("liferay.jpeg");
	}

	@Test
	public void testReadJPG() throws Exception {
		_testRead("liferay.jpg");
	}

	@Test
	public void testReadPNG() throws Exception {
		_testRead("liferay.png");
	}

	@Test
	public void testRotation90Degrees() throws Exception {
		ImageBag imageBag = _imageTool.read(
			new File(_FILE_DIR, "rotation_90_degrees.jpg"));

		RenderedImage originalImage = imageBag.getRenderedImage();

		RenderedImage rotatedImage = _imageTool.rotate(originalImage, 90);

		Assert.assertEquals(originalImage.getHeight(), rotatedImage.getWidth());
		Assert.assertEquals(originalImage.getWidth(), rotatedImage.getHeight());
	}

	private void _testCrop(
		RenderedImage renderedImage, int height, int width, int x, int y) {

		RenderedImage croppedRenderedImage = _imageTool.crop(
			renderedImage, height, width, x, y);

		Assert.assertEquals(
			croppedRenderedImage.getHeight(),
			Math.min(renderedImage.getHeight() - Math.abs(y), height));

		Assert.assertEquals(
			croppedRenderedImage.getWidth(),
			Math.min(renderedImage.getWidth() - Math.abs(x), width));
	}

	private void _testCrop(String fileName) throws Exception {

		// Crop bottom right

		ImageBag imageBag = _imageTool.read(new File(_FILE_DIR, fileName));

		RenderedImage image = imageBag.getRenderedImage();

		_testCrop(
			image, image.getHeight() / 2, image.getWidth() / 2,
			image.getWidth() / 2, image.getHeight() / 2);

		// Crop center

		_testCrop(
			image, image.getHeight() - (image.getHeight() / 2),
			image.getWidth() - (image.getWidth() / 2), image.getWidth() / 4,
			image.getHeight() / 4);

		// Move down and right

		_testCrop(
			image, image.getHeight(), image.getWidth(), image.getWidth() / 4,
			image.getHeight() / 4);

		// Move up and left

		_testCrop(
			image, image.getHeight(), image.getWidth(), -(image.getWidth() / 4),
			-(image.getHeight() / 4));

		// Crop same image

		_testCrop(image, image.getHeight(), image.getWidth(), 0, 0);

		// Crop top left

		_testCrop(
			image, image.getHeight() - (image.getHeight() / 2),
			image.getWidth() - (image.getWidth() / 2), 0, 0);
	}

	private void _testRead(String fileName) throws Exception {
		File file = new File(_FILE_DIR, fileName);

		BufferedImage expectedImage = ImageIO.read(file);

		Assert.assertNotNull(expectedImage);

		Raster expectedRaster = expectedImage.getData();

		DataBuffer expectedDataBuffer = expectedRaster.getDataBuffer();

		String expectedType = FileUtil.getExtension(fileName);

		if (expectedType.equals("jpeg")) {
			expectedType = ImageTool.TYPE_JPEG;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		byte[] bytes = new byte[(int)randomAccessFile.length()];

		randomAccessFile.readFully(bytes);

		ImageBag imageBag = _imageTool.read(bytes);

		RenderedImage resultImage = imageBag.getRenderedImage();

		Assert.assertNotNull(resultImage);

		Raster resultRaster = resultImage.getData();

		DataBuffer resultDataBuffer = resultRaster.getDataBuffer();

		String resultType = imageBag.getType();

		Assert.assertTrue(
			StringUtil.equalsIgnoreCase(expectedType, resultType));

		Assert.assertTrue(
			expectedDataBuffer instanceof DataBufferByte ||
			expectedDataBuffer instanceof DataBufferInt);

		if (expectedDataBuffer instanceof DataBufferByte) {
			DataBufferByte expectedDataBufferByte =
				(DataBufferByte)expectedDataBuffer;
			DataBufferByte resultDataBufferByte =
				(DataBufferByte)resultDataBuffer;

			Assert.assertTrue(
				Arrays.deepEquals(
					expectedDataBufferByte.getBankData(),
					resultDataBufferByte.getBankData()));
		}
		else if (expectedDataBuffer instanceof DataBufferInt) {
			DataBufferInt expectedDataBufferInt =
				(DataBufferInt)expectedDataBuffer;
			DataBufferInt resultDataBufferInt = (DataBufferInt)resultDataBuffer;

			Assert.assertTrue(
				Arrays.deepEquals(
					expectedDataBufferInt.getBankData(),
					resultDataBufferInt.getBankData()));
		}
	}

	private static final String _FILE_DIR =
		"portal-impl/test/unit/com/liferay/portal/image/dependencies";

	private final ImageTool _imageTool = ImageToolImpl.getInstance();

}