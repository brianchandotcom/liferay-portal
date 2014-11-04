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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;

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
 * @author Alexander Chow
 */
public class LiferayVideoConverter extends LiferayConverter {

	public LiferayVideoConverter(
		String inputURL, String outputURL, String videoContainer,
		Properties videoProperties, Properties ffpresetProperties) {

		_inputURL = inputURL;
		_outputURL = outputURL;
		_videoContainer = videoContainer;
		_ffpresetProperties = ffpresetProperties;

		_height = GetterUtil.getInteger(
			videoProperties.getProperty(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT),
			_height);
		_width = GetterUtil.getInteger(
			videoProperties.getProperty(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH),
			_width);

		initVideoBitRate(videoProperties);
		initVideoFrameRate(videoProperties);
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

			if ((_outputIContainer != null) && _outputIContainer.isOpened()) {
				_outputIContainer.close();
			}
		}

		createMP4FastStart();
	}

	protected void createMP4FastStart() {
		File videoFile = new File(_outputURL);

		if (!_videoContainer.equals("mp4") || !videoFile.exists()) {
			return;
		}

		File tempFile = new File(_outputURL + ".tmp");

		try {
			JQTFastStart.convert(videoFile, tempFile);

			if (tempFile.exists() && (tempFile.length() > 0)) {
				videoFile.delete();

				tempFile.renameTo(videoFile);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to move MOOV atom to front of MP4 file");
			}
		}
		finally {
			tempFile.delete();
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
					inputIStreamCoder, outputIStreamCoders, _outputIContainer,
					outputIStreams, inputICodecType, _outputURL, i);
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

		cleanUp(iAudioResamplers, iVideoResamplers);
		cleanUp(inputIAudioSamples, outputIAudioSamples);
		cleanUp(inputIVideoPictures, outputIVideoPictures);
		cleanUp(inputIStreamCoders, outputIStreamCoders);
		cleanUp(inputIPacket, outputIPacket);
	}

	@Override
	protected IContainer getInputIContainer() {
		return _inputIContainer;
	}

	protected int getVideoBitRate(int originalBitRate) {
		return getProperty(originalBitRate, _videoBitRate, _VIDEO_BIT_RATE_MAX);
	}

	protected ICodec getVideoEncodingICodec(
		ICodec.Type inputICodecType, String outputURL) {

		IContainerFormat iContainerFormat =
			_outputIContainer.getContainerFormat();

		String outputFormat = iContainerFormat.getOutputFormatShortName();

		if (outputFormat.equals("mp4")) {
			return ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_H264);
		}
		else {
			return ICodec.guessEncodingCodec(
				null, null, outputURL, null, inputICodecType);
		}
	}

	protected IRational getVideoFrameRate(IRational originalFrameRate) {
		if (_videoFrameRate != null) {
			originalFrameRate = _videoFrameRate;
		}

		return originalFrameRate;
	}

	protected void initVideoBitRate(Properties videoProperties) {
		_videoBitRate = getProperty(
			videoProperties, PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_BIT_RATE,
			"video bit rate", _videoContainer, _VIDEO_BIT_RATE_DEFAULT,
			_VIDEO_BIT_RATE_MAX);
	}

	protected void initVideoFrameRate(Properties videoProperties) {
		int numerator = GetterUtil.getInteger(
			videoProperties.getProperty(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_FRAME_RATE_NUMERATOR +
					"[" + _videoContainer + "]"));
		int denominator = GetterUtil.getInteger(
			videoProperties.getProperty(
				PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_FRAME_RATE_DENOMINATOR +
					StringPool.OPEN_BRACKET + _videoContainer +
						StringPool.CLOSE_BRACKET));

		if ((numerator > 0) && (denominator > 0)) {
			_videoFrameRate = IRational.make(numerator, denominator);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Default frame rate for " + _videoContainer +
						" configured to " + _videoFrameRate.getNumerator() +
							"/" + _videoFrameRate.getDenominator());
			}
		}
	}

	protected void prepareVideo(
			IVideoResampler[] iVideoResamplers,
			IVideoPicture[] inputIVideoPictures,
			IVideoPicture[] outputIVideoPictures,
			IStreamCoder inputIStreamCoder, IStreamCoder[] outputIStreamCoders,
			IContainer outputIContainer, IStream[] outputIStreams,
			ICodec.Type inputICodecType, String outputURL, int index)
		throws Exception {

		ICodec iCodec = getVideoEncodingICodec(inputICodecType, outputURL);

		if (iCodec == null) {
			throw new RuntimeException(
				"Unable to determine " + inputICodecType + " encoder for " +
					outputURL);
		}

		IStream outputIStream = outputIContainer.addNewStream(iCodec);

		outputIStreams[index] = outputIStream;

		IStreamCoder outputIStreamCoder = outputIStream.getStreamCoder();

		outputIStreamCoders[index] = outputIStreamCoder;

		int bitRate = inputIStreamCoder.getBitRate();

		if (_log.isInfoEnabled()) {
			_log.info("Original video bitrate " + bitRate);
		}

		bitRate = getVideoBitRate(bitRate);

		if (_log.isInfoEnabled()) {
			_log.info("Modified video bitrate " + bitRate);
		}

		outputIStreamCoder.setBitRate(bitRate);

		IRational iRational = inputIStreamCoder.getFrameRate();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Original frame rate " + iRational.getNumerator() + "/" +
					iRational.getDenominator());
		}

		iRational = getVideoFrameRate(iRational);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Modified frame rate " + iRational.getNumerator() + "/" +
					iRational.getDenominator());
		}

		outputIStreamCoder.setFrameRate(iRational);

		if (inputIStreamCoder.getHeight() <= 0) {
			throw new RuntimeException(
				"Unable to determine height for " + _inputURL);
		}

		if (_height == 0) {
			_height = inputIStreamCoder.getHeight();
		}

		outputIStreamCoder.setHeight(_height);

		outputIStreamCoder.setPixelType(Type.YUV420P);
		outputIStreamCoder.setTimeBase(
			IRational.make(
				iRational.getDenominator(), iRational.getNumerator()));

		if (inputIStreamCoder.getWidth() <= 0) {
			throw new RuntimeException(
				"Unable to determine width for " + _inputURL);
		}

		if (_width == 0) {
			_width = inputIStreamCoder.getWidth();
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

		ICodec.ID iCodecID = iCodec.getID();

		if (iCodecID.equals(ICodec.ID.CODEC_ID_H264)) {
			Configuration.configure(_ffpresetProperties, outputIStreamCoder);
		}
	}

	private static final int _VIDEO_BIT_RATE_DEFAULT = 250000;

	private static final int _VIDEO_BIT_RATE_MAX = 1200000;

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoConverter.class);

	private Properties _ffpresetProperties;
	private int _height = 0;
	private IContainer _inputIContainer;
	private String _inputURL;
	private IContainer _outputIContainer;
	private String _outputURL;
	private int _videoBitRate;
	private String _videoContainer;
	private IRational _videoFrameRate;
	private int _width = 0;

}