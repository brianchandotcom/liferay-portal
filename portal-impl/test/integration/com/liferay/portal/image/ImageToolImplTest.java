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
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Sampsa Sohlman
 */
public class ImageToolImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCropBMP() throws Exception {
		crop("liferay.bmp");
	}

	@Test
	public void testCropGIF() throws Exception {
		crop("liferay.gif");
	}

	@Test
	public void testCropJPEG() throws Exception {
		crop("liferay.jpeg");
	}

	@Test
	public void testCropJPG() throws Exception {
		crop("liferay.jpg");
	}

	@Test
	public void testCropPNG() throws Exception {
		crop("liferay.png");
	}

	@Test
	public void testReadBMP() throws Exception {
		read("liferay.bmp");
	}

	@Test
	public void testReadGIF() throws Exception {
		read("liferay.gif");
	}

	@Test
	public void testReadJPEG() throws Exception {
		read("liferay.jpeg");
	}

	@Test
	public void testReadJPG() throws Exception {
		read("liferay.jpg");
	}

	@Test
	public void testReadPNG() throws Exception {
		read("liferay.png");
	}

	protected void crop(String fileName) throws Exception {

		// Crop bottom right

		File file = getFile(fileName);

		ImageBag imageBag = ImageToolUtil.read(file);

		RenderedImage image = imageBag.getRenderedImage();

		testCrop(
			image, image.getHeight() / 2, image.getWidth() / 2,
			image.getWidth() / 2, image.getHeight() / 2);

		// Crop center

		testCrop(
			image, image.getHeight() - (image.getHeight() / 2),
			image.getWidth() - (image.getWidth() / 2), image.getWidth() / 4,
			image.getHeight() / 4);

		// Move down and right

		testCrop(
			image, image.getHeight(), image.getWidth(), image.getWidth() / 4,
			image.getHeight() / 4);

		// Move up and left

		testCrop(
			image, image.getHeight(), image.getWidth(), -(image.getWidth() / 4),
			-(image.getHeight() / 4));

		// Crop same image

		testCrop(image, image.getHeight(), image.getWidth(), 0, 0);

		// Crop top left

		testCrop(
			image, image.getHeight() - (image.getHeight() / 2),
			image.getWidth() - (image.getWidth() / 2), 0, 0);
	}

	protected File getFile(String fileName) {
		fileName =
			"portal-impl/test/integration/com/liferay/portal/image/" +
				"dependencies/" + fileName;

		return new File(fileName);
	}

	protected void read(String fileName) throws Exception {
		File file = getFile(fileName);

		BufferedImage expectedImage = ImageIO.read(file);

		Assert.assertNotNull(expectedImage);

		Raster raster = expectedImage.getData();

		DataBuffer expectedDataBuffer = raster.getDataBuffer();

		String expectedType = FileUtil.getExtension(fileName);

		if (expectedType.equals("jpeg")) {
			expectedType = ImageTool.TYPE_JPEG;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

		byte[] bytes = new byte[(int)randomAccessFile.length()];

		randomAccessFile.readFully(bytes);

		ImageBag imageBag = ImageToolUtil.read(bytes);

		RenderedImage resultImage = imageBag.getRenderedImage();

		Assert.assertNotNull(resultImage);

		raster = resultImage.getData();

		DataBuffer resultDataBuffer = raster.getDataBuffer();

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

	protected void testCrop(
			RenderedImage renderedImage, int height, int width, int x, int y)
		throws Exception {

		RenderedImage croppedRenderedImage = ImageToolUtil.crop(
			renderedImage, height, width, x, y);

		int maxHeight = renderedImage.getHeight() - Math.abs(y);
		int maxWidth = renderedImage.getWidth() - Math.abs(x);

		Assert.assertEquals(
			croppedRenderedImage.getHeight(), Math.min(maxHeight, height));

		Assert.assertEquals(
			croppedRenderedImage.getWidth(), Math.min(maxWidth, width));
	}

}