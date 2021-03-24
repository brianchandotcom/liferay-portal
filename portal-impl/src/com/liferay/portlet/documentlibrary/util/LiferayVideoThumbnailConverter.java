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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link com.liferay.document.library.kernel.util.VideoConverter}
 */
@Deprecated
public class LiferayVideoThumbnailConverter extends LiferayConverter {

	public LiferayVideoThumbnailConverter(
		String inputURL, File outputFile, String extension, int height,
		int width, int percentage) {

		_inputURL = inputURL;
		_outputFile = outputFile;
		_extension = extension;
		_height = height;
		_width = width;
		_percentage = percentage;
	}

	@Override
	public void convert() throws Exception {
		try {
			doConvert();
		}
		finally {
			if ((_inputIContainer != null) && _inputIContainer.isOpened()) {
				_inputIContainer.close();
			}
		}
	}

	protected void doConvert() throws Exception {
		_inputIContainer = IContainer.make();

		openContainer(_inputIContainer, _inputURL, false);

		long seekTimeStamp = -1;

		if ((_percentage > 0) && (_percentage <= 100)) {
			seekTimeStamp = getSeekTimeStamp(_percentage);
		}

		int inputStreamsCount = _inputIContainer.getNumStreams();

		if (inputStreamsCount < 0) {
			throw new RuntimeException("Input URL does not have any streams");
		}

		boolean hasCodecTypeVideo = false;

		IVideoPicture[] inputIVideoPictures =
			new IVideoPicture[inputStreamsCount];

		IStreamCoder[] inputIStreamCoders = new IStreamCoder[inputStreamsCount];

		for (int i = 0; i < inputStreamsCount; i++) {
			IStream inputIStream = _inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			inputIStreamCoders[i] = inputIStreamCoder;

			if (inputIStreamCoder.getCodecType() ==
					ICodec.Type.CODEC_TYPE_VIDEO) {

				hasCodecTypeVideo = true;

				inputIVideoPictures[i] = IVideoPicture.make(
					inputIStreamCoder.getPixelType(),
					inputIStreamCoder.getWidth(),
					inputIStreamCoder.getHeight());
			}

			openStreamCoder(inputIStreamCoder);
		}

		if (hasCodecTypeVideo) {
			boolean thumbnailGenerated = false;

			try {
				if (seekTimeStamp != -1) {
					rewind();

					seek(seekTimeStamp);
				}

				thumbnailGenerated = generateThumbnail(
					inputIStreamCoders, inputIVideoPictures);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}
			}

			if (!thumbnailGenerated) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to create thumbnail from specified frame. " +
							"Will generate thumbnail from the beginning.");
				}

				rewind();

				generateThumbnail(inputIStreamCoders, inputIVideoPictures);
			}
		}
		else {
			BufferedImage bufferedImage = new BufferedImage(
				_width, _height, BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics2D = bufferedImage.createGraphics();

			graphics2D.setColor(Color.black);

			graphics2D.fillRect(
				0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

			_outputFile.createNewFile();

			ImageIO.write(
				bufferedImage, _extension, new FileOutputStream(_outputFile));
		}

		cleanUp(inputIVideoPictures, null);
		cleanUp(inputIStreamCoders, null);
	}

	protected boolean generateThumbnail(
			IStreamCoder[] inputIStreamCoders,
			IVideoPicture[] inputIVideoPictures)
		throws Exception {

		boolean keyPacketFound = false;
		int nonkeyAfterKeyCount = 0;
		boolean onlyDecodeKeyPackets = false;

		IPacket inputIPacket = IPacket.make();

		while (_inputIContainer.readNextPacket(inputIPacket) == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("Current packet size " + inputIPacket.getSize());
			}

			int streamIndex = inputIPacket.getStreamIndex();

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];

			if (inputIStreamCoder.getCodecType() !=
					ICodec.Type.CODEC_TYPE_VIDEO) {

				continue;
			}

			keyPacketFound = isKeyPacketFound(inputIPacket, keyPacketFound);

			nonkeyAfterKeyCount = countNonKeyAfterKey(
				inputIPacket, keyPacketFound, nonkeyAfterKeyCount);

			if (isStartDecoding(
					inputIPacket, inputIStreamCoder, keyPacketFound,
					nonkeyAfterKeyCount, onlyDecodeKeyPackets)) {

				IStream iStream = _inputIContainer.getStream(streamIndex);

				long timeStampOffset = getStreamTimeStampOffset(iStream);

				int value = decodeVideo(
					null, inputIVideoPictures[streamIndex], null, inputIPacket,
					null, inputIStreamCoder, null, null, _outputFile,
					_extension, _height, _width, timeStampOffset);

				if (value <= 0) {
					if (inputIPacket.isKey()) {
						throw new RuntimeException(
							"Unable to decode video stream " + streamIndex);
					}

					onlyDecodeKeyPackets = true;
				}
				else if (value == DECODE_VIDEO_THUMBNAIL) {
					cleanUp(inputIPacket, null);

					return true;
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Do not decode video stream " + streamIndex);
				}
			}
		}

		cleanUp(inputIPacket, null);

		return false;
	}

	@Override
	protected IContainer getInputIContainer() {
		return _inputIContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayVideoThumbnailConverter.class);

	private final String _extension;
	private final int _height;
	private IContainer _inputIContainer;
	private final String _inputURL;
	private final File _outputFile;
	private final int _percentage;
	private final int _width;

}