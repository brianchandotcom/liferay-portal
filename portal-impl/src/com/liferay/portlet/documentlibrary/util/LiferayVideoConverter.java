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

import com.xuggle.xuggler.Global;
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
public class LiferayVideoConverter {

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
			inputIContainer = IContainer.make();
			outputIContainer = IContainer.make();

			int returnValue = inputIContainer.open(
				_inputUrl, IContainer.Type.READ, null);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open input url");
			}

			returnValue = outputIContainer.open(
				_outputUrl, IContainer.Type.WRITE, null);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open output url to write");
			}

			int numOfStreams = inputIContainer.getNumStreams();

			if (numOfStreams < 0) {
				throw new RuntimeException(
					"Input file doesn't have any Stream");
			}

			IStream[] inputIStreams = new IStream[numOfStreams];
			IStream[] outputIStreams = new IStream[numOfStreams];

			IStreamCoder[] inputIStreamCoders = new IStreamCoder[numOfStreams];
			IStreamCoder[] outputIStreamCoders = new IStreamCoder[numOfStreams];

			IAudioResampler[] iAudioResamplers =
				new IAudioResampler[numOfStreams];
			IVideoResampler[] iVideoResamplers =
				new IVideoResampler[numOfStreams];

			IAudioSamples[] inputIAudioSamples =
				new IAudioSamples[numOfStreams];
			IAudioSamples[] outputIAudioSamples =
				new IAudioSamples[numOfStreams];

			IVideoPicture[] inputIVideoPictures =
				new IVideoPicture[numOfStreams];
			IVideoPicture[] outputIVideoPictures =
				new IVideoPicture[numOfStreams];

			for (int i = 0; i < numOfStreams; i++) {
				IStream inputIStream = inputIContainer.getStream(i);

				IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

				ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

				inputIStreams[i] = inputIStream;
				outputIStreams[i] = null;

				inputIStreamCoders[i] = inputIStreamCoder;
				outputIStreamCoders[i] = null;

				iAudioResamplers[i] = null;
				iVideoResamplers[i] = null;

				inputIAudioSamples[i] = null;
				outputIAudioSamples[i] = null;

				inputIVideoPictures[i] = null;
				outputIVideoPictures[i] = null;

				if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
					IStream outputIStream = outputIContainer.addNewStream(i);

					IStreamCoder outputIStreamCoder =
						outputIStream.getStreamCoder();

					outputIStreamCoders[i] = outputIStreamCoder;

					outputIStreams[i] = outputIStream;

					ICodec iCodec = ICodec.guessEncodingCodec(
						null, null, _outputUrl, null, inputICodecType);

					if (iCodec == null) {
						throw new RuntimeException(
							"Cannot guess " + inputICodecType +
								" encoder for " + _outputUrl);
					}

					outputIStreamCoder.setBitRate(
						inputIStreamCoder.getBitRate());

					int channels = inputIStreamCoder.getChannels();

					if (_channels > 0) {
						channels = _channels;
					}

					outputIStreamCoder.setChannels(channels);

					outputIStreamCoder.setCodec(iCodec);

					outputIStreamCoder.setGlobalQuality(0);

					int rate = inputIStreamCoder.getSampleRate();

					if (_rate > 0) {
						rate = _rate;
					}

					outputIStreamCoder.setSampleRate(rate);

					if ((inputIStreamCoder.getSampleRate() !=
							outputIStreamCoder.getSampleRate()) ||
						(inputIStreamCoder.getChannels() !=
							outputIStreamCoder.getChannels()) ||
						!inputIStreamCoder.getSampleFormat().equals(
							outputIStreamCoder.getSampleFormat())) {

						iAudioResamplers[i] = IAudioResampler.make(
							outputIStreamCoder.getChannels(),
							inputIStreamCoder.getChannels(),
							outputIStreamCoder.getSampleRate(),
							inputIStreamCoder.getSampleRate(),
							outputIStreamCoder.getSampleFormat(),
							inputIStreamCoder.getSampleFormat());

						if (iAudioResamplers[i] == null) {
							throw new RuntimeException(
								"Audio resampling is not supported");
						}
					}
					else {
						iAudioResamplers[i] = null;
					}

					inputIAudioSamples[i] = IAudioSamples.make(
						1024, inputIStreamCoder.getChannels());
					outputIAudioSamples[i] = IAudioSamples.make(
						1024, outputIStreamCoder.getChannels());
				}
				else if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
					IStream outputIStream = outputIContainer.addNewStream(i);

					IStreamCoder outputIStreamCoder =
						outputIStream.getStreamCoder();

					outputIStreamCoders[i] = outputIStreamCoder;

					outputIStreams[i] = outputIStream;

					ICodec iCodec = ICodec.guessEncodingCodec(
						null, null, _outputUrl, null, inputICodecType);

					if (iCodec == null) {
						throw new RuntimeException(
							"Could not guess " + inputICodecType +
								" encoder for " + _outputUrl);
					}

					outputIStreamCoder.setCodec(iCodec);

					if (inputIStreamCoder.getBitRate() == 0) {
						outputIStreamCoder.setBitRate(250000);
					}
					else {
						outputIStreamCoder.setBitRate(
							inputIStreamCoder.getBitRate());
					}

					int inputHeight = inputIStreamCoder.getHeight();

					int inputWidth = inputIStreamCoder.getWidth();

					if (inputHeight <= 0 || inputWidth <= 0) {
						throw new RuntimeException(
							"Cannot find width or height in url: " +
								_inputUrl);
					}

					outputIStreamCoder.setPixelType(Type.YUV420P);

					if (!(_height == inputHeight && _width == inputWidth)) {
						iVideoResamplers[i] = IVideoResampler.make(
							_width, _height, outputIStreamCoder.getPixelType(),
							inputIStreamCoder.getWidth(),
							inputIStreamCoder.getHeight(),
							inputIStreamCoder.getPixelType());

						if (iVideoResamplers[i] == null) {
							throw new RuntimeException(
								"Video resampling is not supported");
						}
					}
					else {
						iVideoResamplers[i] = null;
					}

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

				if (outputIStreamCoders[i] != null) {
					returnValue = outputIStreamCoders[i].open();

					if (returnValue < 0) {
						throw new RuntimeException("Cannot open output coder");
					}
				}

				if (inputIStreamCoders[i] != null) {
					returnValue = inputIStreamCoders[i].open();

					if (returnValue < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}
			}

			returnValue = outputIContainer.writeHeader();

			if (returnValue < 0) {
				throw new RuntimeException("Cannot write container header");
			}

			IPacket inputIPacket = IPacket.make();
			IPacket outputIPacket = IPacket.make();

			IAudioSamples inputIAudioSample = null;
			IAudioSamples outputIAudioSample = null;
			IAudioSamples resampledIAudioSample = null;

			boolean keyPacketFound = false;
			boolean onlyDecodeKeyPackets=false;
			int contNonKeyAfterKey = 0;

			int lastPacketSize = -1;
			int currentPacketSize = 0;

			while (inputIContainer.readNextPacket(inputIPacket) == 0) {
				currentPacketSize = inputIPacket.getSize();

				int streamIndex = inputIPacket.getStreamIndex();

				IStream iStream = inputIContainer.getStream(streamIndex);

				int offset = 0;

				long timeStampOffset = 0;

				if ((iStream.getStartTime() != Global.NO_PTS) &&
					(iStream.getStartTime() > 0) &&
					(iStream.getTimeBase() != null)) {

					IRational defTimeBase =	IRational.make(
						1, (int)Global.DEFAULT_PTS_PER_SECOND);

					timeStampOffset = defTimeBase.rescale(
						iStream.getStartTime(), iStream.getTimeBase());
				}

				IStreamCoder inputIStreamCoder =
					inputIStreamCoders[streamIndex];
				IStreamCoder outputIStreamCoder =
					outputIStreamCoders[streamIndex];

				if (outputIStreamCoder == null) {
					continue;
				}

				IAudioResampler iAudioResampler = iAudioResamplers[streamIndex];
				IVideoResampler iVideoResampler = iVideoResamplers[streamIndex];

				IVideoPicture inputIVideoPicture =
					inputIVideoPictures[streamIndex];
				IVideoPicture resampledIVideoPicture =
					outputIVideoPictures[streamIndex];

				inputIAudioSample = inputIAudioSamples[streamIndex];
				resampledIAudioSample = outputIAudioSamples[streamIndex];

				ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

				if (_log.isDebugEnabled()) {
					_log.debug("Current packet size:" + currentPacketSize);
				}

				if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
					while (offset < inputIPacket.getSize()) {
						boolean stopDecoding = false;

						returnValue = inputIStreamCoder.decodeAudio(
							inputIAudioSample, inputIPacket, offset);

						if (returnValue <= 0) {
							if ((currentPacketSize == lastPacketSize) &&
								(lastPacketSize != -1)) {

								throw new RuntimeException(
									"Cannot decode audio iStream: " +
										streamIndex);
							}
							else {
								stopDecoding = true;
							}
						}

						if (inputIAudioSample.getTimeStamp() != Global.NO_PTS) {
							inputIAudioSample.setTimeStamp(
								inputIAudioSample.getTimeStamp() -
									timeStampOffset);
						}

						offset += returnValue;

						int numSamplesConsumed = 0;

						if ((iAudioResampler != null) &&
							(inputIAudioSample.getNumSamples() > 0)) {

							returnValue = iAudioResampler.resample(
								resampledIAudioSample, inputIAudioSample,
								inputIAudioSample.getNumSamples());

							outputIAudioSample = resampledIAudioSample;
						}
						else {
							outputIAudioSample = inputIAudioSample;
						}

						while (
							numSamplesConsumed <
								outputIAudioSample.getNumSamples()) {

							returnValue = outputIStreamCoder.encodeAudio(
								outputIPacket, outputIAudioSample,
								numSamplesConsumed);

							if (returnValue <= 0) {
								throw new RuntimeException(
									"Cannot encode any audio: " + returnValue);
							}

							numSamplesConsumed += returnValue;

							if (outputIPacket.isComplete()) {
								returnValue = outputIContainer.writePacket(
									outputIPacket, true);

								if (returnValue < 0) {
									throw new RuntimeException(
										"Cannot write output packet");
								}
							}
						}

						if (stopDecoding) {
							if (_log.isDebugEnabled()) {
								_log.debug(
									"Forced stop decoding audio iStream: " +
										streamIndex);
							}

							break;
						}
					}
				}
				else if (inputICodecType == ICodec.Type.CODEC_TYPE_VIDEO) {
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
					else if (
						inputIStreamCoder.getCodecID().equals(
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
								"Do not decode yet iStream:" + streamIndex);
						}
					}

					if (startDecoding) {
						IVideoPicture outputIVideoPicture = null;

						returnValue = inputIStreamCoder.decodeVideo(
							inputIVideoPicture, inputIPacket, 0);

						if (returnValue <= 0) {
							if (!inputIPacket.isKey()) {
								onlyDecodeKeyPackets = true;

								continue;
							}
							else{
								throw new RuntimeException(
								"Cannot decode any video in iStream: " +
									streamIndex);
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
							if (iVideoResampler != null) {
								returnValue = iVideoResampler.resample(
									resampledIVideoPicture, inputIVideoPicture);

								if (returnValue < 0) {
									throw new RuntimeException(
										"Cannot resample video");
								}

								outputIVideoPicture = resampledIVideoPicture;
							}
							else {
								outputIVideoPicture = inputIVideoPicture;
							}

							outputIVideoPicture.setQuality(0);

							returnValue = outputIStreamCoder.encodeVideo(
								outputIPacket, outputIVideoPicture, 0);

							if (returnValue < 0) {
								throw new RuntimeException(
									"Cannot encode video");
							}

							if (outputIPacket.isComplete()) {
								returnValue = outputIContainer.writePacket(
									outputIPacket, true);

								if (returnValue < 0) {
									throw new RuntimeException(
										"Cannot write video packet");
								}
							}
						}
					}
				}

				lastPacketSize = inputIPacket.getSize();
			}

			numOfStreams = inputIContainer.getNumStreams();

			for (int i = 0; i < numOfStreams; i++) {
				if (outputIStreamCoders[i] != null) {
					IPacket outputTempIPacket = IPacket.make();

					if (
						outputIStreamCoders[i].getCodecType() ==
							ICodec.Type.CODEC_TYPE_AUDIO) {

						outputIStreamCoders[i].encodeAudio(
							outputTempIPacket, null, 0);
					}
					else {
						outputIStreamCoders[i].encodeVideo(
							outputTempIPacket, null, 0);
					}
					if (outputTempIPacket.isComplete()) {
						outputIContainer.writePacket(outputTempIPacket, true);
					}
				}
			}

			returnValue = outputIContainer.writeTrailer();

			if (returnValue < 0) {
				throw new RuntimeException(
					"Cannot write trailer to output file");
			}

			for (int i = 0; i < numOfStreams; i++) {
				if (outputIStreamCoders[i] != null) {
					outputIStreamCoders[i].close();
				}

				outputIStreamCoders[i] = null;

				if (inputIStreamCoders[i] != null) {
					inputIStreamCoders[i].close();
				}

				inputIStreamCoders[i] = null;
			}

			inputIStreamCoders = null;
			outputIStreamCoders = null;

			iAudioResamplers = null;
			iVideoResamplers = null;

			inputIAudioSamples = null;
			outputIAudioSamples = null;

			inputIVideoPictures = null;
			outputIVideoPictures = null;
		}
		finally {
			if (inputIContainer.isOpened()) {
				inputIContainer.close();
				inputIContainer = null;
			}

			if (outputIContainer.isOpened()) {
				outputIContainer.close();
				outputIContainer = null;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoConverter.class);

	private String _inputUrl = null;

	private String _outputUrl = null;

	private int _height = 240;

	private int _width = 320;

	private int _channels = 0;

	private int _rate = 0;

	private IContainer inputIContainer = null;

	private IContainer outputIContainer = null;

}