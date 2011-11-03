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
 * @author Brian Wing Shun Chan
 */
public class LiferayAudioConverter extends LiferayConverter {

	public LiferayAudioConverter(String inputURL, String outputURL, int rate) {
		_inputURL = inputURL;
		_outputURL = outputURL;
		_rate = rate;
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

		IAudioSamples[] inputIAudioSamples =
			new IAudioSamples[inputStreamsCount];
		IAudioSamples[] outputIAudioSamples =
			new IAudioSamples[inputStreamsCount];

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
					outputIStreams, inputICodecType, _outputURL, channels, rate,
					i);
			}

			openStreamCoder(inputIStreamCoders[i]);
			openStreamCoder(outputIStreamCoders[i]);
		}

		if (_outputIContainer.writeHeader() < 0) {
			throw new RuntimeException("Unable to write container header");
		}

		IPacket inputIPacket = IPacket.make();
		IPacket outputIPacket = IPacket.make();

		int previousPacketSize = -1;

		_inputIContainer.readNextPacket(inputIPacket);

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

			if (inputIStreamCoder.getCodecType() ==
					ICodec.Type.CODEC_TYPE_AUDIO) {

				IStream iStream = _inputIContainer.getStream(streamIndex);

				long timeStampOffset = getStreamTimeStampOffset(iStream);

				decodeAudio(
					iAudioResamplers[streamIndex],
					inputIAudioSamples[streamIndex],
					outputIAudioSamples[streamIndex], inputIPacket,
					outputIPacket, inputIStreamCoder, outputIStreamCoder,
					_outputIContainer, inputIPacket.getSize(),
					previousPacketSize, streamIndex, timeStampOffset);
			}

			previousPacketSize = inputIPacket.getSize();
		}

		flush(outputIStreamCoders, _outputIContainer);

		if (_outputIContainer.writeTrailer() < 0) {
			throw new RuntimeException(
				"Unable to write trailer to output file");
		}

		cleanUp(inputIStreamCoders, outputIStreamCoders);
	}

	@Override
	protected IContainer getInputIContainer() {
		return _inputIContainer;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayAudioConverter.class);

	private int _channels = 1;
	private IContainer _inputIContainer;
	private String _inputURL;
	private IContainer _outputIContainer;
	private String _outputURL;
	private int _rate;

}