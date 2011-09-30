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
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * @author Juan González
 * @author Sergio González
 */
public class LiferayAudioConverter {

	public LiferayAudioConverter(String srcFile, String destFile, int rate) {
		_inputUrl = srcFile;
		_outputUrl = destFile;
		_rate = rate;
	}

	public void convert() throws Exception {
		try {
			_inputIContainer = IContainer.make();
			_outputIContainer = IContainer.make();

			int returnValue = _inputIContainer.open(
				_inputUrl, IContainer.Type.READ, null);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open input url");
			}

			returnValue = _outputIContainer.open(
				_outputUrl, IContainer.Type.WRITE, null);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open output url to write");
			}

			int numOfStreams = _inputIContainer.getNumStreams();

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

			IAudioSamples[] inputIAudioSamples =
				new IAudioSamples[numOfStreams];
			IAudioSamples[] outputIAudioSamples =
				new IAudioSamples[numOfStreams];

			for (int i = 0; i < numOfStreams; i++) {
				IStream inputIStream = _inputIContainer.getStream(i);

				IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

				ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

				inputIStreams[i] = inputIStream;
				outputIStreams[i] = null;

				inputIStreamCoders[i] = inputIStreamCoder;
				outputIStreamCoders[i] = null;

				iAudioResamplers[i] = null;

				inputIAudioSamples[i] = null;
				outputIAudioSamples[i] = null;

				if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
					IStream outputIStream = _outputIContainer.addNewStream(i);

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

					int channels = _channels;

					if (inputIStreamCoder.getChannels() > 0) {
						channels = inputIStreamCoder.getChannels();
					}

					outputIStreamCoder.setChannels(channels);

					outputIStreamCoder.setCodec(iCodec);

					outputIStreamCoder.setGlobalQuality(0);

					int rate = _rate;

					if (inputIStreamCoder.getSampleRate() > 0) {
						rate = inputIStreamCoder.getSampleRate();
					}

					outputIStreamCoder.setSampleRate(rate);

					if (inputIStreamCoder.getSampleRate() !=
						outputIStreamCoder.getSampleRate()) {

						iAudioResamplers[i] = IAudioResampler.make(
							outputIStreamCoder.getChannels(),
							inputIStreamCoder.getChannels(),
							outputIStreamCoder.getSampleRate(),
							inputIStreamCoder.getSampleRate());

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

				if (inputIStreamCoders[i] != null) {
					returnValue = inputIStreamCoders[i].open();

					if (returnValue < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}

				if (outputIStreamCoders[i] != null) {
					returnValue = outputIStreamCoders[i].open();

					if (returnValue < 0) {
						throw new RuntimeException("Cannot open output coder");
					}
				}
			}

			returnValue = _outputIContainer.writeHeader();

			if (returnValue < 0) {
				throw new RuntimeException("Cannot write container header");
			}

			IPacket inputIPacket = IPacket.make();
			IPacket outputIPacket = IPacket.make();

			IAudioSamples inputIAudioSample = null;
			IAudioSamples outputIAudioSample = null;
			IAudioSamples resampledIAudioSample = null;

			int lastPacketSize = -1;

			_inputIContainer.readNextPacket(inputIPacket);

			while (_inputIContainer.readNextPacket(inputIPacket) == 0) {
				int currentPacketSize = inputIPacket.getSize();

				int streamIndex = inputIPacket.getStreamIndex();

				IStream iStream = _inputIContainer.getStream(streamIndex);

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
									"Cannot decode audio Stream: " +
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
								returnValue = _outputIContainer.writePacket(
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
									"Forced stop decoding audio Stream: " +
										streamIndex);
							}

							break;
						}
					}
				}

				lastPacketSize = inputIPacket.getSize();
			}

			numOfStreams = _inputIContainer.getNumStreams();

			for (int i = 0; i < numOfStreams; i++) {
				if (outputIStreamCoders[i] != null) {
					IPacket outputTempIPacket = IPacket.make();

					if (outputIStreamCoders[i].getCodecType() ==
							ICodec.Type.CODEC_TYPE_AUDIO) {

						outputIStreamCoders[i].encodeAudio(
							outputTempIPacket, null, 0);
					}

					if (outputTempIPacket.isComplete()) {
						_outputIContainer.writePacket(outputTempIPacket, true);
					}
				}
			}

			returnValue = _outputIContainer.writeTrailer();

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

			inputIAudioSamples = null;
			outputIAudioSamples = null;
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

	private static Log _log = LogFactoryUtil.getLog(
		LiferayAudioConverter.class);

	private int _channels = 1;

	private String _inputUrl = null;

	private String _outputUrl = null;

	private IContainer _inputIContainer = null;

	private IContainer _outputIContainer = null;

	private int _rate = 0;

}