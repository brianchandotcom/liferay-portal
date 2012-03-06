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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaProps;
import com.liferay.portal.util.FileImpl;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Enumeration;

import javax.imageio.ImageIO;

import javax.media.jai.RenderedImageAdapter;

import net.jmge.gif.Gif89Encoder;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageToolImpl implements ImageTool {

	public static ImageTool getInstance() {
		return _instance;
	}

	public BufferedImage convertImageType(BufferedImage sourceImage, int type) {
		BufferedImage targetImage = new BufferedImage(
			sourceImage.getWidth(), sourceImage.getHeight(), type);

		Graphics2D graphics = targetImage.createGraphics();

		graphics.drawRenderedImage(sourceImage, null);

		graphics.dispose();

		return targetImage;
	}

	public void encodeGIF(RenderedImage renderedImage, OutputStream os)
		throws IOException {

		if (JavaProps.isJDK6()) {
			ImageIO.write(renderedImage, TYPE_GIF, os);
		}
		else {
			BufferedImage bufferedImage = getBufferedImage(renderedImage);

			if (!(bufferedImage.getColorModel() instanceof IndexColorModel)) {
				bufferedImage = convertImageType(
					bufferedImage, BufferedImage.TYPE_BYTE_INDEXED);
			}

			Gif89Encoder encoder = new Gif89Encoder(bufferedImage);

			encoder.encode(os);
		}
	}

	public void encodeWBMP(RenderedImage renderedImage, OutputStream os)
		throws IOException {

		BufferedImage bufferedImage = getBufferedImage(renderedImage);

		SampleModel sampleModel = bufferedImage.getSampleModel();

		int type = sampleModel.getDataType();

		if ((bufferedImage.getType() != BufferedImage.TYPE_BYTE_BINARY) ||
			(type < DataBuffer.TYPE_BYTE) || (type > DataBuffer.TYPE_INT) ||
			(sampleModel.getNumBands() != 1) ||
			(sampleModel.getSampleSize(0) != 1)) {

			BufferedImage binaryImage = new BufferedImage(
				bufferedImage.getWidth(), bufferedImage.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

			Graphics graphics = binaryImage.getGraphics();

			graphics.drawImage(bufferedImage, 0, 0, null);

			renderedImage = binaryImage;
		}

		if (!ImageIO.write(renderedImage, "wbmp", os)) {

			// See http://www.jguru.com/faq/view.jsp?EID=127723

			os.write(0);
			os.write(0);
			os.write(_toMultiByte(bufferedImage.getWidth()));
			os.write(_toMultiByte(bufferedImage.getHeight()));

			DataBuffer dataBuffer = bufferedImage.getData().getDataBuffer();

			int size = dataBuffer.getSize();

			for (int i = 0; i < size; i++) {
				os.write((byte)dataBuffer.getElem(i));
			}
		}
	}

	public BufferedImage getBufferedImage(RenderedImage renderedImage) {
		if (renderedImage instanceof BufferedImage) {
			return (BufferedImage)renderedImage;
		}
		else {
			RenderedImageAdapter adapter = new RenderedImageAdapter(
				renderedImage);

			return adapter.getAsBufferedImage();
		}
	}

	public byte[] getBytes(RenderedImage renderedImage, String contentType)
		throws IOException {

		UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();

		write(renderedImage, contentType, baos);

		return baos.toByteArray();
	}

	public ImageBag read(byte[] bytes) {
		RenderedImage renderedImage = null;
		String type = TYPE_NOT_AVAILABLE;

		Enumeration<ImageCodec> enu = ImageCodec.getCodecs();

		while (enu.hasMoreElements()) {
			ImageCodec codec = enu.nextElement();

			if (codec.isFormatRecognized(bytes)) {
				type = codec.getFormatName();

				ImageDecoder decoder = ImageCodec.createImageDecoder(
					type, new UnsyncByteArrayInputStream(bytes), null);

				try {
					renderedImage = decoder.decodeAsRenderedImage();
				}
				catch (IOException ioe) {
					if (_log.isDebugEnabled()) {
						_log.debug(type + ": " + ioe.getMessage());
					}
				}

				break;
			}
		}

		if (type.equals("jpeg")) {
			type = TYPE_JPEG;
		}

		return new ImageBag(renderedImage, type);
	}

	public ImageBag read(File file) throws IOException {
		return read(_fileUtil.getBytes(file));
	}

	public RenderedImage scale(RenderedImage renderedImage, int width) {
		if (width <= 0) {
			return renderedImage;
		}

		int imageHeight = renderedImage.getHeight();
		int imageWidth = renderedImage.getWidth();

		double factor = (double) width / imageWidth;

		int scaledHeight = (int)(factor * imageHeight);
		int scaledWidth = width;

		BufferedImage bufferedImage = getBufferedImage(renderedImage);

		int type = bufferedImage.getType();

		if (type == 0) {
			type = BufferedImage.TYPE_INT_ARGB;
		}

		BufferedImage scaledBufferedImage = new BufferedImage(
			scaledWidth, scaledHeight, type);

		Graphics graphics = scaledBufferedImage.getGraphics();

		Image scaledImage = bufferedImage.getScaledInstance(
			scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

		graphics.drawImage(scaledImage, 0, 0, null);

		return scaledBufferedImage;
	}

	public RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth) {

		int imageHeight = renderedImage.getHeight();
		int imageWidth = renderedImage.getWidth();

		if (maxHeight == 0) {
			maxHeight = imageHeight;
		}

		if (maxWidth == 0) {
			maxWidth = imageWidth;
		}

		if ((imageHeight <= maxHeight) && (imageWidth <= maxWidth)) {
			return renderedImage;
		}

		double factor = Math.min(
			(double)maxHeight / imageHeight, (double)maxWidth / imageWidth);

		int scaledHeight = Math.max(1, (int)(factor * imageHeight));
		int scaledWidth = Math.max(1, (int)(factor * imageWidth));

		BufferedImage bufferedImage = getBufferedImage(renderedImage);

		int type = bufferedImage.getType();

		if (type == 0) {
			type = BufferedImage.TYPE_INT_ARGB;
		}

		BufferedImage scaledBufferedImage = null;

		if ((type == BufferedImage.TYPE_BYTE_BINARY) ||
			(type == BufferedImage.TYPE_BYTE_INDEXED)) {

			IndexColorModel indexColorModel =
				(IndexColorModel)bufferedImage.getColorModel();

			BufferedImage tempBufferedImage = new BufferedImage(
				1, 1, type, indexColorModel);

			int bits = indexColorModel.getPixelSize();
			int size = indexColorModel.getMapSize();

			byte[] reds = new byte[size];

			indexColorModel.getReds(reds);

			byte[] greens = new byte[size];

			indexColorModel.getGreens(greens);

			byte[] blues = new byte[size];

			indexColorModel.getBlues(blues);

			WritableRaster writableRaster = tempBufferedImage.getRaster();

			int pixel = writableRaster.getSample(0, 0, 0);

			IndexColorModel scaledIndexColorModel = new IndexColorModel(
				bits, size, reds, greens, blues, pixel);

			scaledBufferedImage = new BufferedImage(
				scaledWidth, scaledHeight, type, scaledIndexColorModel);
		}
		else {
			scaledBufferedImage = new BufferedImage(
				scaledWidth, scaledHeight, type);
		}

		Graphics graphics = scaledBufferedImage.getGraphics();

		Image scaledImage = bufferedImage.getScaledInstance(
			scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

		graphics.drawImage(scaledImage, 0, 0, null);

		return scaledBufferedImage;
	}

	public void write(
			RenderedImage renderedImage, String contentType, OutputStream os)
		throws IOException {

		if (contentType.contains(TYPE_BMP)) {
			ImageEncoder imageEncoder = ImageCodec.createImageEncoder(
				TYPE_BMP, os, null);

			imageEncoder.encode(renderedImage);
		}
		else if (contentType.contains(TYPE_GIF)) {
			encodeGIF(renderedImage, os);
		}
		else if ((contentType.contains(TYPE_JPEG)) ||
				 (contentType.contains("jpeg"))) {

			ImageIO.write(renderedImage, "jpeg", os);
		}
		else if (contentType.contains(TYPE_PNG)) {
			ImageIO.write(renderedImage, TYPE_PNG, os);
		}
		else if (contentType.contains(TYPE_TIFF) ||
				 contentType.contains("tif")) {

			ImageEncoder imageEncoder = ImageCodec.createImageEncoder(
				TYPE_TIFF, os, null);

			imageEncoder.encode(renderedImage);
		}
	}

	private byte[] _toMultiByte(int intValue) {
		int numBits = 32;
		int mask = 0x80000000;

		while ((mask != 0) && ((intValue & mask) == 0)) {
			numBits--;
			mask >>>= 1;
		}

		int numBitsLeft = numBits;
		byte[] multiBytes = new byte[(numBitsLeft + 6) / 7];

		int maxIndex = multiBytes.length - 1;

		for (int b = 0; b <= maxIndex; b++) {
			multiBytes[b] = (byte)((intValue >>> ((maxIndex - b) * 7)) & 0x7f);

			if (b != maxIndex) {
				multiBytes[b] |= (byte)0x80;
			}
		}

		return multiBytes;
	}

	private static Log _log = LogFactoryUtil.getLog(ImageToolImpl.class);

	private static ImageTool _instance = new ImageToolImpl();

	private static FileImpl _fileUtil = FileImpl.getInstance();

}