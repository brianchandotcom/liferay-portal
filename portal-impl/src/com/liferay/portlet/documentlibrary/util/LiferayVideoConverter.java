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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import com.xuggle.xuggler.Configuration;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat.Type;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

import java.io.File;

import java.util.Properties;

/**
 * @author Juan González
 * @author Sergio González
 * @author Brian Wing Shun Chan
 */
public class LiferayVideoConverter extends LiferayConverter {

	public LiferayVideoConverter(
		String inputURL, String outputURL, int height, int width) {

		_ffpresetProperties = PropsUtil.getProperties(
			PropsKeys.XUGGLER_FFPRESET, true);

		_inputURL = inputURL;
		_outputURL = outputURL;
		_height = height;
		_width = width;
	}

	public void convert() throws Exception {
		try {
			doConvert();
		}
		finally {
			if (_inputIContainer.isOpened()) {
				_inputIContainer.close();
			}

			if (_outputIContainer.isOpened()) {
				_outputIContainer.close();
			}
		}
	}

	protected void doConvert() throws Exception {
		_inputIContainer = IContainer.make();
		_outputIContainer = IContainer.make();

		openContainer(_inputIContainer, _inputURL, false);
		openContainer(_outputIContainer, _outputURL, true);

		int inputStreamsCount = _inputIContainer.getNumStreams();

		if (inputStreamsCount < 0) {
			throw new RuntimeException("Input URL does not have any streams");
		}

		IAudioResampler[] iAudioResamplers =
			new IAudioResampler[inputStreamsCount];
		IVideoResampler[] iVideoResamplers =
			new IVideoResampler[inputStreamsCount];

		IAudioSamples[] inputIAudioSamples =
			new IAudioSamples[inputStreamsCount];
		IAudioSamples[] outputIAudioSamples =
			new IAudioSamples[inputStreamsCount];

		IVideoPicture[] inputIVideoPictures =
			new IVideoPicture[inputStreamsCount];
		IVideoPicture[] outputIVideoPictures =
			new IVideoPicture[inputStreamsCount];

		IStream[] outputIStreams = new IStream[inputStreamsCount];

		IStreamCoder[] inputIStreamCoders = new IStreamCoder[inputStreamsCount];
		IStreamCoder[] outputIStreamCoders =
			new IStreamCoder[inputStreamsCount];

		IContainerFormat iContainerFormat =
			_outputIContainer.getContainerFormat();

		String outputFormat = iContainerFormat.getOutputFormatShortName();

		for (int i = 0; i < inputStreamsCount; i++) {
			IStream inputIStream = _inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			inputIStreamCoders[i] = inputIStreamCoder;

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
				prepareAudio(
					iAudioResamplers, inputIAudioSamples, outputIAudioSamples,
					inputIStreamCoder, outputIStreamCoders, _outputIContainer,
					outputIStreams, inputICodecType, _outputURL, i);
			}
			else if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
				prepareVideo(
					iVideoResamplers, inputIVideoPictures, outputIVideoPictures,
					inputIStreamCoder, outputIStreamCoders, outputFormat,
					outputIStreams, inputICodecType, i);
			}

			openStreamCoder(inputIStreamCoders[i]);
			openStreamCoder(outputIStreamCoders[i]);
		}

		if (_outputIContainer.writeHeader() < 0) {
			throw new RuntimeException("Unable to write container header");
		}

		boolean keyPacketFound = false;
		int nonKeyAfterKeyCount = 0;
		boolean onlyDecodeKeyPackets = false;
		int previousPacketSize = -1;

		IPacket inputIPacket = IPacket.make();
		IPacket outputIPacket = IPacket.make();

		while (_inputIContainer.readNextPacket(inputIPacket) == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("Current packet size " + inputIPacket.getSize());
			}

			int streamIndex = inputIPacket.getStreamIndex();

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];
			IStreamCoder outputIStreamCoder = outputIStreamCoders[streamIndex];

			if (outputIStreamCoder == null) {
				continue;
			}

			IStream iStream = _inputIContainer.getStream(streamIndex);

			long timeStampOffset = getStreamTimeStampOffset(iStream);

			if (inputIStreamCoder.getCodecType() ==
					ICodec.Type.CODEC_TYPE_AUDIO) {

				decodeAudio(
					iAudioResamplers[streamIndex],
					inputIAudioSamples[streamIndex],
					outputIAudioSamples[streamIndex], inputIPacket,
					outputIPacket, inputIStreamCoder, outputIStreamCoder,
					_outputIContainer, inputIPacket.getSize(),
					previousPacketSize, streamIndex, timeStampOffset);
			}
			else if (inputIStreamCoder.getCodecType() ==
						ICodec.Type.CODEC_TYPE_VIDEO) {

				keyPacketFound = isKeyPacketFound(inputIPacket, keyPacketFound);

				nonKeyAfterKeyCount = countNonKeyAfterKey(
					inputIPacket, keyPacketFound, nonKeyAfterKeyCount);

				if (isStartDecoding(
						inputIPacket, inputIStreamCoder, keyPacketFound,
						nonKeyAfterKeyCount, onlyDecodeKeyPackets)) {

					int value = decodeVideo(
						iVideoResamplers[streamIndex],
						inputIVideoPictures[streamIndex],
						outputIVideoPictures[streamIndex], inputIPacket,
						outputIPacket, inputIStreamCoder, outputIStreamCoder,
						_outputIContainer, null, null, 0, 0, timeStampOffset);

					if (value <= 0) {
						if (inputIPacket.isKey()) {
							throw new RuntimeException(
								"Unable to decode video stream " + streamIndex);
						}

						onlyDecodeKeyPackets = true;

						continue;
					}
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Do not decode video stream " + streamIndex);
					}
				}
			}

			previousPacketSize = inputIPacket.getSize();
		}

		flush(outputIStreamCoders, _outputIContainer);

		if (_outputIContainer.writeTrailer() < 0) {
			throw new RuntimeException(
				"Unable to write trailer to output file");
		}

		cleanUp(inputIStreamCoders, outputIStreamCoders);

		// Create MP4 fast start

		File videoFile = new File(_outputURL);

		if (outputFormat.equals("mp4") && videoFile.exists()) {
			File tempFile = FileUtil.createTempFile();

			try {
				JQTFastStart.convert(videoFile, tempFile);

				if (tempFile.exists() && tempFile.length() > 0) {
					FileUtil.move(tempFile, videoFile);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Error while moving MOOV atom to front of MP4 file");
				}
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
	}

	@Override
	protected int getAudioEncodingChannels(
		IContainer outputIContainer, int channels) {

		IContainerFormat iContainerFormat =
			outputIContainer.getContainerFormat();

		String outputFormat = iContainerFormat.getOutputFormatShortName();

		if (outputFormat.equals("ogg")) {
			return 2;
		}

		return super.getAudioEncodingChannels(outputIContainer, channels);
	}

	@Override
	protected ICodec getAudioEncodingICodec(IContainer outputIContainer) {
		String outputFormat =
			outputIContainer.getContainerFormat().getOutputFormatShortName();

		if (outputFormat.equals("ogg")) {
			return ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_VORBIS);
		}

		return super.getAudioEncodingICodec(outputIContainer);
	}

	@Override
	protected IContainer getInputIContainer() {
		return _inputIContainer;
	}

	protected void prepareVideo(
			IVideoResampler[] iVideoResamplers,
			IVideoPicture[] inputIVideoPictures,
			IVideoPicture[] outputIVideoPictures,
			IStreamCoder inputIStreamCoder, IStreamCoder[] outputIStreamCoders,
			String outputFormat, IStream[] outputIStreams,
			ICodec.Type inputICodecType, int index)
		throws Exception {

		IStream outputIStream = _outputIContainer.addNewStream(index);

		outputIStreams[index] = outputIStream;

		IStreamCoder outputIStreamCoder = outputIStream.getStreamCoder();

		outputIStreamCoders[index] = outputIStreamCoder;

		int bitRate = inputIStreamCoder.getBitRate();

		if ((bitRate == 0) || (bitRate > VIDEO_BIT_RATE_MAX)) {
			bitRate = VIDEO_BIT_RATE_DEFAULT;
		}

		outputIStreamCoder.setBitRate(bitRate);

		ICodec iCodec = ICodec.guessEncodingCodec(
			null, null, _outputURL, null, inputICodecType);

		if (outputFormat.equals("mp4")) {
			iCodec = ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_H264);
		}

		if (iCodec == null) {
			throw new RuntimeException(
				"Unable to determine " + inputICodecType + " encoder for " +
					_outputURL);
		}

		outputIStreamCoder.setCodec(iCodec);

		if (inputIStreamCoder.getHeight() <= 0) {
			throw new RuntimeException(
				"Unable to determine height for " + _inputURL);
		}

		outputIStreamCoder.setHeight(_height);

		IRational frameRate = inputIStreamCoder.getFrameRate();

		if (outputFormat.equals("mp4")) {
			frameRate = IRational.make(30, 1);
		}

		outputIStreamCoder.setFrameRate(frameRate);

		outputIStreamCoder.setPixelType(Type.YUV420P);
		outputIStreamCoder.setTimeBase(
			IRational.make(
				frameRate.getDenominator(), frameRate.getNumerator()));

		if (inputIStreamCoder.getWidth() <= 0) {
			throw new RuntimeException(
				"Unable to determine width for " + _inputURL);
		}

		outputIStreamCoder.setWidth(_width);

		iVideoResamplers[index] = createIVideoResampler(
			inputIStreamCoder, outputIStreamCoder, _height, _width);

		inputIVideoPictures[index] = IVideoPicture.make(
			inputIStreamCoder.getPixelType(), inputIStreamCoder.getWidth(),
			inputIStreamCoder.getHeight());
		outputIVideoPictures[index] = IVideoPicture.make(
			outputIStreamCoder.getPixelType(), outputIStreamCoder.getWidth(),
			outputIStreamCoder.getHeight());

		if (iCodec.getID().equals(ICodec.ID.CODEC_ID_H264)) {
			Configuration.configure(_ffpresetProperties, outputIStreamCoder);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoConverter.class);

	private Properties _ffpresetProperties;
	private int _height = 240;
	private IContainer _inputIContainer;
	private String _inputURL;
	private IContainer _outputIContainer;
	private String _outputURL;
	private int _width = 320;

}