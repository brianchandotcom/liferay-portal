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
			_mIContainer = IContainer.make();

			int retval = _mIContainer.open(
				_inputUrl, IContainer.Type.READ, null);

			if (retval < 0) {
				throw new RuntimeException("Cannot open input url");
			}

			int numStreams = _mIContainer.getNumStreams();

			if (numStreams < 0) {
				throw new RuntimeException(
					"Input file doesn't have any stream");
			}

			IStream[] mIStreams = new IStream[numStreams];

			IStreamCoder[] mICoders = new IStreamCoder[numStreams];

			IVideoPicture[] mIVideoPictures = new IVideoPicture[numStreams];

			for (int i = 0; i < numStreams; i++) {
				IStream is = _mIContainer.getStream(i);

				IStreamCoder ic = is.getStreamCoder();

				ICodec.Type cType = ic.getCodecType();

				mIStreams[i] = is;

				mICoders[i] = ic;

				mIVideoPictures[i] = null;

				if (cType == ICodec.Type.CODEC_TYPE_VIDEO) {
					mIVideoPictures[i] = IVideoPicture.make(
						ic.getPixelType(), ic.getWidth(), ic.getHeight());
				}

				if (mICoders[i] != null) {
					retval = mICoders[i].open();

					if (retval < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}
			}

			IPacket iPacket = IPacket.make();

			boolean keyPacketFound = false;
			boolean onlyDecodeKeyPackets=false;
			boolean thumbnailGenerated = false;

			int contNonKeyAfterKey = 0;

			int currentPacketSize = 0;

			while ((_mIContainer.readNextPacket(iPacket) == 0) &&
				!thumbnailGenerated) {

				currentPacketSize = iPacket.getSize();

				int streamIndex = iPacket.getStreamIndex();

				IStream stream = _mIContainer.getStream(streamIndex);

				int offset = 0;

				long timeStampOffset = 0;

				if ((stream.getStartTime() != Global.NO_PTS) &&
					(stream.getStartTime() > 0) &&
					(stream.getTimeBase() != null)) {

					IRational defTimeBase =	IRational.make(
						1, (int) Global.DEFAULT_PTS_PER_SECOND);

					timeStampOffset = defTimeBase.rescale(
						stream.getStartTime(), stream.getTimeBase());
				}

				IStreamCoder ic = mICoders[streamIndex];
				IVideoPicture inFrame = mIVideoPictures[streamIndex];

				ICodec.Type cType = ic.getCodecType();

				if (_log.isDebugEnabled()) {
					_log.debug("Current packet size: " + currentPacketSize);
				}

				if (cType == ICodec.Type.CODEC_TYPE_VIDEO) {
					if (_log.isDebugEnabled()) {
						_log.debug("Is video packet Key: " + iPacket.isKey());
					}

					if (iPacket.isKey()) {
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

					if (ic.getCodecID().equals(ICodec.ID.CODEC_ID_MJPEG)) {
						startDecoding = true;
					}
					else if (ic.getCodecID().equals(
						ICodec.ID.CODEC_ID_MPEG2VIDEO)) {

						startDecoding = false;

						if (contNonKeyAfterKey != 1) {
							startDecoding = true;
						}
					}

					if (onlyDecodeKeyPackets && !iPacket.isKey()) {
						startDecoding = false;
					}

					if (!startDecoding) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Do not decode yet stream: " + streamIndex);
						}
					}

					if (startDecoding) {
						retval = ic.decodeVideo(inFrame, iPacket, 0);

						if (retval <= 0) {
							if (!iPacket.isKey()) {
								onlyDecodeKeyPackets = true;

								continue;
							}
							else {
								throw new RuntimeException(
									"Cannot decode any video in stream: " +
										streamIndex);
							}
						}

						if (inFrame.getTimeStamp() != Global.NO_PTS) {
							inFrame.setTimeStamp(
								inFrame.getTimeStamp() - timeStampOffset);
						}

						offset += retval;

						if (inFrame.isComplete()) {
							BufferedImage image = null;

							if (_mConverterType == null) {
								_mConverterType =
									ConverterFactory.findRegisteredConverter(
										ConverterFactory.XUGGLER_BGR_24);
							}

							if (_mConverterType == null) {
								throw new UnsupportedOperationException(
									"No converter \"" +
										ConverterFactory.XUGGLER_BGR_24 +
											"\" found.");
							}

							if (_mVideoConverter == null) {
								_mVideoConverter =
									ConverterFactory.createConverter(
										_mConverterType.getDescriptor(),
										inFrame);
							}

							image = _mVideoConverter.toImage(inFrame);

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

			mICoders = null;

			mIVideoPictures = null;
		}
		finally {
			if (_mIContainer.isOpened()) {
				_mIContainer.close();
				_mIContainer = null;
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

	private ConverterFactory.Type _mConverterType;

	private IContainer _mIContainer = null;

	private IConverter _mVideoConverter;

}