/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 */
public class LiferayVideoThumbnailGenerator {

	public LiferayVideoThumbnailGenerator(
		String srcFile, File file, String extension, int height, int width) {

		_inputUrl = srcFile;
		_file = file;
		_height = height;
		_width = width;
		_extension = extension;
	}

	public void convert() throws Exception {
		try {
			inputIContainer = IContainer.make();

			int returnValue = inputIContainer.open(
				_inputUrl, IContainer.Type.READ, null);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open input url");
			}

			int numOfStreams = inputIContainer.getNumStreams();

			if (numOfStreams < 0) {
				throw new RuntimeException(
					"Input file doesn't have any iStream");
			}

			IStream[] inputIStreams = new IStream[numOfStreams];

			IStreamCoder[] inputIStreamCoders = new IStreamCoder[numOfStreams];

			IVideoPicture[] inputIVideoPictures =
				new IVideoPicture[numOfStreams];

			for (int i = 0; i < numOfStreams; i++) {
				IStream inputIStream = inputIContainer.getStream(i);

				IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

				ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

				inputIStreams[i] = inputIStream;

				inputIStreamCoders[i] = inputIStreamCoder;

				inputIVideoPictures[i] = null;

				if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
					inputIVideoPictures[i] = IVideoPicture.make(
						inputIStreamCoder.getPixelType(),
						inputIStreamCoder.getWidth(),
						inputIStreamCoder.getHeight());
				}

				if (inputIStreamCoders[i] != null) {
					returnValue = inputIStreamCoders[i].open();

					if (returnValue < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}
			}

			IPacket inputIPacket = IPacket.make();

			boolean keyPacketFound = false;
			boolean onlyDecodeKeyPackets=false;
			boolean thumbnailGenerated = false;

			int contNonKeyAfterKey = 0;

			int currentPacketSize = 0;

			while ((inputIContainer.readNextPacket(inputIPacket) == 0) &&
				!thumbnailGenerated) {

				currentPacketSize = inputIPacket.getSize();

				int iStreamIndex = inputIPacket.getStreamIndex();

				IStream iStream = inputIContainer.getStream(iStreamIndex);

				int offset = 0;

				long timeStampOffset = 0;

				if ((iStream.getStartTime() != Global.NO_PTS) &&
					(iStream.getStartTime() > 0) &&
					(iStream.getTimeBase() != null)) {

					IRational defTimeBase =	IRational.make(
						1, (int) Global.DEFAULT_PTS_PER_SECOND);

					timeStampOffset = defTimeBase.rescale(
						iStream.getStartTime(), iStream.getTimeBase());
				}

				IStreamCoder inputIStreamCoder =
					inputIStreamCoders[iStreamIndex];
				IVideoPicture inputIVideoPicture =
					inputIVideoPictures[iStreamIndex];

				ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

				if (_log.isDebugEnabled()) {
					_log.debug("Current packet size: " + currentPacketSize);
				}

				if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
					if (_log.isDebugEnabled()) {
						_log.debug("Is video packet Key: " +
							inputIPacket.isKey());
					}

					if (inputIPacket.isKey()) {
						contNonKeyAfterKey = 0;

						if (!keyPacketFound) {
							keyPacketFound = true;
						}
					}
					else {
						if (keyPacketFound) {
							contNonKeyAfterKey++;
						}
					}

					boolean startDecoding = keyPacketFound;

					if (inputIStreamCoder.getCodecID().equals(
						ICodec.ID.CODEC_ID_MJPEG)) {

						startDecoding = true;
					}
					else if (inputIStreamCoder.getCodecID().equals(
						ICodec.ID.CODEC_ID_MPEG2VIDEO)) {

						startDecoding = false;

						if (contNonKeyAfterKey != 1) {
							startDecoding = true;
						}
					}

					if (onlyDecodeKeyPackets && !inputIPacket.isKey()) {
						startDecoding = false;
					}

					if (!startDecoding) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Do not decode yet iStream: " + iStreamIndex);
						}
					}

					if (startDecoding) {
						returnValue = inputIStreamCoder.decodeVideo(
							inputIVideoPicture, inputIPacket, 0);

						if (returnValue <= 0) {
							if (!inputIPacket.isKey()) {
								onlyDecodeKeyPackets = true;

								continue;
							}
							else {
								throw new RuntimeException(
									"Cannot decode any video in iStream: " +
										iStreamIndex);
							}
						}

						if (inputIVideoPicture.getTimeStamp() !=
							Global.NO_PTS) {

							inputIVideoPicture.setTimeStamp(
								inputIVideoPicture.getTimeStamp() -
									timeStampOffset);
						}

						offset += returnValue;

						if (inputIVideoPicture.isComplete()) {
							BufferedImage image = null;

							if (_converterType == null) {
								_converterType =
									ConverterFactory.findRegisteredConverter(
										ConverterFactory.XUGGLER_BGR_24);
							}

							if (_converterType == null) {
								throw new UnsupportedOperationException(
									"No converter \"" +
										ConverterFactory.XUGGLER_BGR_24 +
											"\" found.");
							}

							if (_videoConverter == null) {
								_videoConverter =
									ConverterFactory.createConverter(
										_converterType.getDescriptor(),
										inputIVideoPicture);
							}

							image = _videoConverter.toImage(inputIVideoPicture);

							_file.createNewFile();

							RenderedImage renderedImage =
								ImageProcessorUtil.scale(
									image, _height,	_width);

							ImageIO.write(
								renderedImage, _extension,
								new FileOutputStream(_file));

							thumbnailGenerated = true;
						}
					}
				}
			}

			inputIStreamCoders = null;

			inputIVideoPictures = null;
		}
		finally {
			if (inputIContainer.isOpened()) {
				inputIContainer.close();
				inputIContainer = null;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoThumbnailGenerator.class);

	private String _extension = null;

	private File _file = null;

	private String _inputUrl = null;

	private int _height = 240;

	private int _width = 320;

	private ConverterFactory.Type _converterType;

	private IContainer inputIContainer = null;

	private IConverter _videoConverter;

}