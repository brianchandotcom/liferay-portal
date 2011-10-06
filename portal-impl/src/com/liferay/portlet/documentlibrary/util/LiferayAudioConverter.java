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
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * @author Juan González
 * @author Sergio González
 */
public class LiferayAudioConverter extends LiferayConverter {

	public LiferayAudioConverter(String srcFile, String destFile, int rate) {
		_inputUrl = srcFile;
		_outputUrl = destFile;
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

		IAudioSamples[] inputIAudioSamples = new IAudioSamples[numOfStreams];
		IAudioSamples[] outputIAudioSamples = new IAudioSamples[numOfStreams];

		IStream[] inputIStreams = new IStream[numOfStreams];
		IStream[] outputIStreams = new IStream[numOfStreams];

		IStreamCoder[] inputIStreamCoders = new IStreamCoder[numOfStreams];
		IStreamCoder[] outputIStreamCoders = new IStreamCoder[numOfStreams];

		for (int i = 0; i < numOfStreams; i++) {
			iAudioResamplers[i] = null;

			inputIAudioSamples[i] = null;
			outputIAudioSamples[i] = null;

			IStream inputIStream = _inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			ICodec.Type inputICodecType = inputIStreamCoder.getCodecType();

			inputIStreams[i] = inputIStream;
			outputIStreams[i] = null;

			inputIStreamCoders[i] = inputIStreamCoder;
			outputIStreamCoders[i] = null;

			if (inputICodecType == ICodec.Type.CODEC_TYPE_AUDIO) {
				int channels = _channels;

				if (inputIStreamCoder.getChannels() > 0) {
					channels = inputIStreamCoder.getChannels();
				}

				int rate = _rate;

				if (inputIStreamCoder.getSampleRate() > 0) {
					rate = inputIStreamCoder.getSampleRate();
				}

				prepareAudio(
					iAudioResamplers, inputIAudioSamples, outputIAudioSamples,
					inputIStreamCoder, outputIStreamCoders, _outputIContainer,
					outputIStreams, inputICodecType, _outputUrl, channels, rate,
					i);
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

		int lastPacketSize = -1;

		_inputIContainer.readNextPacket(inputIPacket);

		while (_inputIContainer.readNextPacket(inputIPacket) == 0) {
			int currentPacketSize = inputIPacket.getSize();

			int streamIndex = inputIPacket.getStreamIndex();

			IStream iStream = _inputIContainer.getStream(streamIndex);

			long timeStampOffset = getStreamTimeStampOffset(iStream);

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];
			IStreamCoder outputIStreamCoder = outputIStreamCoders[streamIndex];

			if (outputIStreamCoder == null) {
				continue;
			}

			IAudioResampler iAudioResampler = iAudioResamplers[streamIndex];

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
					outputIStreamCoder,	_outputIContainer, currentPacketSize,
					lastPacketSize,	streamIndex, timeStampOffset);
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

		inputIAudioSamples = null;
		outputIAudioSamples = null;

		inputIStreamCoders = null;
		outputIStreamCoders = null;
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