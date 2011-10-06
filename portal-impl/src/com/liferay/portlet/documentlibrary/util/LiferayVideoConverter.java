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

import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat.Type;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

/**
 * @author Juan González
 * @author Sergio González
 */
public class LiferayVideoConverter extends LiferayConverter {

	public LiferayVideoConverter(
		String srcFile, String destFile, int height, int width, int rate) {

		_inputUrl = srcFile;
		_outputUrl = destFile;
		_height = height;
		_width = width;
		_rate = rate;
	}

	public void convert() throws Exception {
		try {
			doConvert();
		}
		finally {
			if (_inputIContainer.isOpened()) {
				_inputIContainer.close();
				_inputIContainer = null;
			}

			if (_outputIContainer.isOpened()) {
				_outputIContainer.close();
				_outputIContainer = null;
			}
		}
	}

	protected void doConvert() throws Exception {
		_inputIContainer = IContainer.make();
		_outputIContainer = IContainer.make();

		int returnValue = 0;

		openContainer(_inputIContainer, _inputUrl, false);
		openContainer(_outputIContainer, _outputUrl, true);

		int numOfStreams = _inputIContainer.getNumStreams();

		if (numOfStreams < 0) {
			throw new RuntimeException("Input file doesn't have any stream");
		}

		IAudioResampler[] iAudioResamplers = new IAudioResampler[numOfStreams];
		IVideoResampler[] iVideoResamplers = new IVideoResampler[numOfStreams];

		IAudioSamples[] inputIAudioSamples = new IAudioSamples[numOfStreams];
		IAudioSamples[] outputIAudioSamples = new IAudioSamples[numOfStreams];

		IVideoPicture[] inputIVideoPictures = new IVideoPicture[numOfStreams];
		IVideoPicture[] outputIVideoPictures = 	new IVideoPicture[numOfStreams];

		IStream[] inputIStreams = new IStream[numOfStreams];
		IStream[] outputIStreams = new IStream[numOfStreams];

		IStreamCoder[] inputIStreamCoders = new IStreamCoder[numOfStreams];
		IStreamCoder[] outputIStreamCoders = new IStreamCoder[numOfStreams];

		for (int i = 0; i < numOfStreams; i++) {
			iAudioResamplers[i] = null;
			iVideoResamplers[i] = null;

			inputIAudioSamples[i] = null;
			outputIAudioSamples[i] = null;

			inputIVideoPictures[i] = null;
			outputIVideoPictures[i] = null;

			IStream inputIStream = _inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			inputIStreams[i] = inputIStream;
			outputIStreams[i] = null;

			inputIStreamCoders[i] = inputIStreamCoder;
			outputIStreamCoders[i] = null;

			if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
				int channels = inputIStreamCoder.getChannels();

				if (_channels > 0) {
					channels = _channels;
				}

				int rate = inputIStreamCoder.getSampleRate();

				if (_rate > 0) {
					rate = _rate;
				}

				prepareAudio(
					iAudioResamplers, inputIAudioSamples, outputIAudioSamples,
					inputIStreamCoder, outputIStreamCoders, _outputIContainer,
					outputIStreams, inputICodecType, _outputUrl, channels, rate,
					i);
			}
			else if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
				IStream outputIStream = _outputIContainer.addNewStream(i);

				IStreamCoder outputIStreamCoder =
					outputIStream.getStreamCoder();

				outputIStreamCoders[i] = outputIStreamCoder;

				outputIStreams[i] = outputIStream;

				ICodec iCodec = ICodec.guessEncodingCodec(
					null, null, _outputUrl, null, inputICodecType);

				if (iCodec == null) {
					throw new RuntimeException(
						"Cannot guess " + inputICodecType + " encoder for " +
							_outputUrl);
				}

				outputIStreamCoder.setCodec(iCodec);

				if (inputIStreamCoder.getBitRate() == 0) {
					outputIStreamCoder.setBitRate(250000);
				}
				else {
					outputIStreamCoder.setBitRate(
						inputIStreamCoder.getBitRate());
				}

				if (inputIStreamCoder.getHeight() <= 0 ||
					inputIStreamCoder.getWidth() <= 0) {

					throw new RuntimeException(
						"Cannot find height or width in url: " + _inputUrl);
				}

				outputIStreamCoder.setPixelType(Type.YUV420P);

				iVideoResamplers[i] = createVideoResampler(
					inputIStreamCoder, outputIStreamCoder, _height, _width);

				outputIStreamCoder.setHeight(_height);
				outputIStreamCoder.setWidth(_width);

				IRational frameRate = null;

				frameRate = inputIStreamCoder.getFrameRate();

				outputIStreamCoder.setFrameRate(frameRate);

				outputIStreamCoder.setTimeBase(
					IRational.make(
						frameRate.getDenominator(),
						frameRate.getNumerator()));

				frameRate = null;

				inputIVideoPictures[i] = IVideoPicture.make(
					inputIStreamCoder.getPixelType(),
					inputIStreamCoder.getWidth(),
					inputIStreamCoder.getHeight());

				outputIVideoPictures[i] = IVideoPicture.make(
					outputIStreamCoder.getPixelType(),
					outputIStreamCoder.getWidth(),
					outputIStreamCoder.getHeight());
			}

			openStreamCoder(inputIStreamCoders[i]);
			openStreamCoder(outputIStreamCoders[i]);
		}

		returnValue = _outputIContainer.writeHeader();

		if (returnValue < 0) {
			throw new RuntimeException("Cannot write container header");
		}

		IAudioSamples inputIAudioSample = null;

		IAudioSamples resampledIAudioSample = null;

		IPacket inputIPacket = IPacket.make();
		IPacket outputIPacket = IPacket.make();

		boolean keyPacketFound = false;
		boolean onlyDecodeKeyPackets = false;
		int countNonKeyAfterKey = 0;

		int lastPacketSize = -1;
		int currentPacketSize = 0;

		while (_inputIContainer.readNextPacket(inputIPacket) == 0) {
			currentPacketSize = inputIPacket.getSize();

			int streamIndex = inputIPacket.getStreamIndex();

			IStream iStream = _inputIContainer.getStream(streamIndex);

			long timeStampOffset = getStreamTimeStampOffset(iStream);

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];
			IStreamCoder outputIStreamCoder = outputIStreamCoders[streamIndex];

			if (outputIStreamCoder == null) {
				continue;
			}

			IAudioResampler iAudioResampler = iAudioResamplers[streamIndex];
			IVideoResampler iVideoResampler = iVideoResamplers[streamIndex];

			IVideoPicture inputIVideoPicture = inputIVideoPictures[streamIndex];
			IVideoPicture resampledIVideoPicture =
				outputIVideoPictures[streamIndex];

			inputIAudioSample = inputIAudioSamples[streamIndex];
			resampledIAudioSample = outputIAudioSamples[streamIndex];

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			if (_log.isDebugEnabled()) {
				_log.debug("Current packet size: " + currentPacketSize);
			}

			if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
				decodeAudio(
					iAudioResampler, inputIAudioSample, resampledIAudioSample,
					inputIPacket, outputIPacket, inputIStreamCoder,
					outputIStreamCoder, _outputIContainer, currentPacketSize,
					lastPacketSize,	streamIndex, timeStampOffset);
			}
			else if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
				keyPacketFound = keyPacketFound(inputIPacket, keyPacketFound);

				countNonKeyAfterKey = countNonKeyAfterKey(
					inputIPacket, keyPacketFound, countNonKeyAfterKey);

				boolean startDecoding = startDecoding(
					inputIPacket, inputIStreamCoder, countNonKeyAfterKey,
					onlyDecodeKeyPackets, keyPacketFound);

				if (startDecoding) {
					returnValue = decodeVideo(
						iVideoResampler, inputIVideoPicture,
						resampledIVideoPicture, inputIPacket, outputIPacket,
						inputIStreamCoder, outputIStreamCoder,
						_outputIContainer, timeStampOffset);

					if (returnValue <= 0) {
						if (inputIPacket.isKey()) {
							throw new RuntimeException(
								"Cannot decode video stream: " + streamIndex);
						}

						onlyDecodeKeyPackets = true;

						continue;
					}
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Do not decode yet stream: " + streamIndex);
					}
				}
			}

			lastPacketSize = inputIPacket.getSize();
		}

		numOfStreams = _inputIContainer.getNumStreams();

		flushData(outputIStreamCoders, _outputIContainer, numOfStreams);

		returnValue = _outputIContainer.writeTrailer();

		if (returnValue < 0) {
			throw new RuntimeException("Cannot write trailer to output file");
		}

		cleanStreamCoders(
			inputIStreamCoders, outputIStreamCoders, numOfStreams);

		iAudioResamplers = null;
		iVideoResamplers = null;

		inputIAudioSamples = null;
		outputIAudioSamples = null;

		inputIVideoPictures = null;
		outputIVideoPictures = null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoConverter.class);

	private String _inputUrl = null;

	private String _outputUrl = null;

	private int _height = 240;

	private int _width = 320;

	private int _channels = 0;

	private int _rate = 0;

	private IContainer _inputIContainer = null;

	private IContainer _outputIContainer = null;

}