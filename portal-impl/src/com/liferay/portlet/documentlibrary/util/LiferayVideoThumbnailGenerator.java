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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

import java.io.File;

/**
 * @author Juan González
 * @author Sergio González
 */
public class LiferayVideoThumbnailGenerator extends LiferayConverter {

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
			doConvert();
		}
		finally {
			if (inputIContainer.isOpened()) {
				inputIContainer.close();
				inputIContainer = null;
			}
		}
	}

	protected void doConvert() throws Exception {
		inputIContainer = IContainer.make();

		int returnValue = 0;

		openContainer(inputIContainer, _inputUrl, false);

		int numOfStreams = inputIContainer.getNumStreams();

		if (numOfStreams < 0) {
			throw new RuntimeException("Input file doesn't have any stream");
		}

		IVideoPicture[] inputIVideoPictures = new IVideoPicture[numOfStreams];

		IStream[] inputIStreams = new IStream[numOfStreams];

		IStreamCoder[] inputIStreamCoders = new IStreamCoder[numOfStreams];

		for (int i = 0; i < numOfStreams; i++) {
			inputIVideoPictures[i] = null;

			IStream inputIStream = inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			inputIStreams[i] = inputIStream;

			inputIStreamCoders[i] = inputIStreamCoder;

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

		int countNonKeyAfterKey = 0;

		int currentPacketSize = 0;

		while (inputIContainer.readNextPacket(inputIPacket) == 0) {
			currentPacketSize = inputIPacket.getSize();

			int streamIndex = inputIPacket.getStreamIndex();

			IStream iStream = inputIContainer.getStream(streamIndex);

			long timeStampOffset = getStreamTimeStampOffset(iStream);

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];

			IVideoPicture inputIVideoPicture = inputIVideoPictures[streamIndex];

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			if (_log.isDebugEnabled()) {
				_log.debug("Current packet size: " + currentPacketSize);
			}

			if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
				keyPacketFound = keyPacketFound(inputIPacket, keyPacketFound);

				countNonKeyAfterKey = countNonKeyAfterKey(
					inputIPacket, keyPacketFound, countNonKeyAfterKey);

				boolean startDecoding = startDecoding(
					inputIPacket, inputIStreamCoder, countNonKeyAfterKey,
					onlyDecodeKeyPackets, keyPacketFound);

				if (startDecoding) {
					returnValue = decodeVideo(
						null, inputIVideoPicture, null, inputIPacket, null,
						inputIStreamCoder, null, null, _file, _extension,
						_height, _width, timeStampOffset);

					if (returnValue <= 0) {
						if (inputIPacket.isKey()) {
							throw new RuntimeException(
								"Cannot decode video stream: " + streamIndex);
						}

						onlyDecodeKeyPackets = true;

						continue;
					}
					else if (returnValue == THUMBNAIL_GENERATED) {
						break;
					}
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Do not decode yet stream: " + streamIndex);
					}
				}
			}
		}

		inputIVideoPictures = null;

		inputIStreamCoders = null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoThumbnailGenerator.class);

	private String _extension = null;

	private File _file = null;

	private String _inputUrl = null;

	private int _height = 240;

	private int _width = 320;

	private IContainer inputIContainer = null;

}