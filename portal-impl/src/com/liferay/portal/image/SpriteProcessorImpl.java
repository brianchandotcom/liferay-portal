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
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.image.SpriteProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.Validator;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;
import javax.media.jai.operator.LookupDescriptor;
import javax.media.jai.operator.MosaicDescriptor;
import javax.media.jai.operator.TranslateDescriptor;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class SpriteProcessorImpl implements SpriteProcessor {

	@Override
	public Properties generate(
			ServletContext servletContext, List<URL> imageURLs,
			String spriteRootDirName, String spriteFileName,
			String spritePropertiesFileName, String rootPath, int maxHeight,
			int maxWidth, int maxSize)
		throws IOException {

		if (imageURLs.isEmpty()) {
			return null;
		}

		Collections.sort(imageURLs, _urlPathComparator);

		File spriteRootDir = null;

		if (Validator.isNull(spriteRootDirName)) {
			File tempDir = (File)servletContext.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			spriteRootDir = new File(tempDir, SpriteProcessor.PATH);
		}
		else {
			spriteRootDir = new File(spriteRootDirName);
		}

		FileUtil.mkdirs(spriteRootDir);

		File spritePropertiesFile = new File(
			spriteRootDir, spritePropertiesFileName);

		File spritePropertiesParentFile = spritePropertiesFile.getParentFile();

		FileUtil.mkdirs(spritePropertiesParentFile);

		boolean build = false;

		long lastModified = 0;

		if (spritePropertiesFile.exists()) {
			lastModified = spritePropertiesFile.lastModified();

			for (URL imageURL : imageURLs) {
				if (URLUtil.getLastModifiedTime(imageURL) > lastModified) {
					build = true;

					break;
				}
			}
		}
		else {
			build = true;
		}

		if (!build) {
			String spritePropertiesString = FileUtil.read(spritePropertiesFile);

			if (Validator.isNull(spritePropertiesString)) {
				return null;
			}
			else {
				return PropertiesUtil.load(spritePropertiesString);
			}
		}

		List<RenderedImage> renderedImages = new ArrayList<>();

		Properties spriteProperties = new SortedProperties();

		float x = 0;
		float y = 0;

		URLConnection urlConnection = null;

		for (URL imageURL : imageURLs) {
			urlConnection = imageURL.openConnection();

			if ((urlConnection != null) &&
				(urlConnection.getContentLength() > maxSize)) {

				continue;
			}

			try {
				ImageBag imageBag = ImageToolUtil.read(
					urlConnection.getInputStream());

				RenderedImage renderedImage = imageBag.getRenderedImage();

				int height = renderedImage.getHeight();
				int width = renderedImage.getWidth();

				if ((height <= maxHeight) && (width <= maxWidth)) {
					renderedImage = convert(renderedImage);

					renderedImage = TranslateDescriptor.create(
						renderedImage, x, y, null, null);

					renderedImages.add(renderedImage);

					String key = ServletContextUtil.getResourcePath(imageURL);

					int pos = key.indexOf(rootPath);

					if (pos == 0) {
						key = key.substring(rootPath.length());
					}

					String contextPath = servletContext.getContextPath();

					key = contextPath.concat(key);

					String value = (int)y + "," + height + "," + width;

					spriteProperties.setProperty(key, value);

					y += renderedImage.getHeight();
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to process " + imageURL);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		if (renderedImages.size() <= 1) {
			renderedImages.clear();
			spriteProperties.clear();
		}
		else {

			// PNG

			RenderedImage renderedImage = MosaicDescriptor.create(
				renderedImages.toArray(
					new RenderedImage[renderedImages.size()]),
				MosaicDescriptor.MOSAIC_TYPE_OVERLAY, null, null, null, null,
				null);

			File spriteFile = new File(spriteRootDir, spriteFileName);

			File spriteDir = spriteFile.getParentFile();

			FileUtil.mkdirs(spriteDir);

			try {
				ImageIO.write(renderedImage, "png", spriteFile);
			}
			catch (Exception e) {
				if (e instanceof IIOException ||
					e instanceof NullPointerException) {

					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(4);

						sb.append("Unable to generate ");
						sb.append(spriteFileName);
						sb.append(" for ");
						sb.append(servletContext.getServletContextName());

						_log.warn(sb.toString());
					}

					return null;
				}

				throw e;
			}

			if (lastModified > 0) {
				spriteFile.setLastModified(lastModified);
			}
		}

		FileUtil.write(
			spritePropertiesFile, PropertiesUtil.toString(spriteProperties));

		if (lastModified > 0) {
			spritePropertiesFile.setLastModified(lastModified);
		}

		return spriteProperties;
	}

	protected RenderedImage convert(RenderedImage renderedImage)
		throws Exception {

		int height = renderedImage.getHeight();
		int width = renderedImage.getWidth();

		SampleModel sampleModel = renderedImage.getSampleModel();
		ColorModel colorModel = renderedImage.getColorModel();

		Raster raster = renderedImage.getData();

		DataBuffer dataBuffer = raster.getDataBuffer();

		if (colorModel instanceof IndexColorModel) {
			IndexColorModel indexColorModel = (IndexColorModel)colorModel;

			int mapSize = indexColorModel.getMapSize();

			byte[][] data = new byte[4][mapSize];

			indexColorModel.getReds(data[0]);
			indexColorModel.getGreens(data[1]);
			indexColorModel.getBlues(data[2]);
			indexColorModel.getAlphas(data[3]);

			LookupTableJAI lookupTableJAI = new LookupTableJAI(data);

			renderedImage = LookupDescriptor.create(
				renderedImage, lookupTableJAI, null);
		}
		else if (sampleModel.getNumBands() == 1) {
			List<Byte> bytesList = new ArrayList<>(
				height * width * _NUM_OF_BANDS);

			for (int i = 0; i < dataBuffer.getSize(); i++) {
				byte elem = (byte)dataBuffer.getElem(i);

				if (elem == -1) {
					bytesList.add((byte)0);
				}
				else {
					bytesList.add((byte)255);
				}

				bytesList.add(elem);
				bytesList.add(elem);
				bytesList.add(elem);
			}

			byte[] data = ArrayUtil.toArray(
				bytesList.toArray(new Byte[bytesList.size()]));

			DataBuffer newDataBuffer = new DataBufferByte(data, data.length);

			renderedImage = createRenderedImage(
				renderedImage, height, width, newDataBuffer);
		}
		else if (sampleModel.getNumBands() == 2) {
			List<Byte> bytesList = new ArrayList<>(
				height * width * _NUM_OF_BANDS);

			List<Byte> tempBytesList = new ArrayList<>(_NUM_OF_BANDS);

			for (int i = 0; i < dataBuffer.getSize(); i++) {
				int mod = (i + 1) % 2;

				int elemPos = i;

				if (mod == 0) {
					tempBytesList.add((byte)dataBuffer.getElem(elemPos - 1));
					tempBytesList.add((byte)dataBuffer.getElem(elemPos - 1));
				}

				tempBytesList.add((byte)dataBuffer.getElem(elemPos));

				if (mod == 0) {
					Collections.reverse(tempBytesList);

					bytesList.addAll(tempBytesList);

					tempBytesList.clear();
				}
			}

			byte[] data = ArrayUtil.toArray(
				bytesList.toArray(new Byte[bytesList.size()]));

			DataBuffer newDataBuffer = new DataBufferByte(data, data.length);

			renderedImage = createRenderedImage(
				renderedImage, height, width, newDataBuffer);
		}
		else if (colorModel.getTransparency() != Transparency.TRANSLUCENT) {
			List<Byte> bytesList = new ArrayList<>(
				height * width * _NUM_OF_BANDS);

			List<Byte> tempBytesList = new ArrayList<>(_NUM_OF_BANDS);

			for (int i = 0; i < dataBuffer.getSize(); i++) {
				int mod = (i + 1) % 3;

				int elemPos = i;

				tempBytesList.add((byte)dataBuffer.getElem(elemPos));

				if (mod == 0) {
					tempBytesList.add((byte)255);

					Collections.reverse(tempBytesList);

					bytesList.addAll(tempBytesList);

					tempBytesList.clear();
				}
			}

			byte[] data = ArrayUtil.toArray(
				bytesList.toArray(new Byte[bytesList.size()]));

			DataBuffer newDataBuffer = new DataBufferByte(data, data.length);

			renderedImage = createRenderedImage(
				renderedImage, height, width, newDataBuffer);
		}

		return renderedImage;
	}

	protected RenderedImage createRenderedImage(
		RenderedImage renderedImage, int height, int width,
		DataBuffer dataBuffer) {

		SampleModel sampleModel =
			RasterFactory.createPixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, _NUM_OF_BANDS);

		ColorModel colorModel = PlanarImage.createColorModel(sampleModel);

		TiledImage tiledImage = new TiledImage(
			0, 0, width, height, 0, 0, sampleModel, colorModel);

		Raster raster = RasterFactory.createWritableRaster(
			sampleModel, dataBuffer, new Point(0, 0));

		tiledImage.setData(raster);

		/*javax.media.jai.JAI.create(
			"filestore", tiledImage, "test.png", "PNG");

		printImage(renderedImage);
		printImage(tiledImage);*/

		return tiledImage;
	}

	protected void printImage(RenderedImage renderedImage) {
		SampleModel sampleModel = renderedImage.getSampleModel();

		int height = renderedImage.getHeight();
		int width = renderedImage.getWidth();
		int numOfBands = sampleModel.getNumBands();

		int[] pixels = new int[height * width * numOfBands];

		Raster raster = renderedImage.getData();

		raster.getPixels(0, 0, width, height, pixels);

		int offset = 0;

		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				offset = (h * width * numOfBands) + (w * numOfBands);

				System.out.print("[" + w + ", " + h + "] = ");

				for (int b = 0; b < numOfBands; b++) {
					System.out.print(pixels[offset + b] + " ");
				}
			}

			System.out.println();
		}
	}

	private static final int _NUM_OF_BANDS = 4;

	private static final Log _log = LogFactoryUtil.getLog(
		SpriteProcessorImpl.class);

	private static final Comparator<URL> _urlPathComparator =
		new Comparator<URL>() {

			@Override
			public int compare(URL url1, URL url2) {
				String path1 = url1.getPath();
				String path2 = url2.getPath();

				return path1.compareToIgnoreCase(path2);
			}

		};

}