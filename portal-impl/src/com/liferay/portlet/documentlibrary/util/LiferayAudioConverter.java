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
import com.liferay.portal.kernel.util.PropsKeys;

import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

import java.util.Properties;

/**
 * @author Juan González
 * @author Sergio González
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link com.liferay.document.library.kernel.util.AudioConverter}
 */
@Deprecated
public class LiferayAudioConverter extends LiferayConverter {

	public LiferayAudioConverter(
		String inputURL, String outputURL, String audioContainer,
		Properties audioProperties) {

		_inputURL = inputURL;
		_outputURL = outputURL;
		_audioContainer = audioContainer;

		initAudioBitRate(audioProperties);
		initAudioSampleRate(audioProperties);
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
				prepareAudio(
					iAudioResamplers, inputIAudioSamples, outputIAudioSamples,
					inputIStreamCoder, outputIStreamCoders, _outputIContainer,
					outputIStreams, inputICodecType, _outputURL, i);
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

			IStreamCoder outputIStreamCoder = outputIStreamCoders[streamIndex];

			if (outputIStreamCoder == null) {
				continue;
			}

			IStreamCoder inputIStreamCoder = inputIStreamCoders[streamIndex];

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

		cleanUp(iAudioResamplers, null);
		cleanUp(inputIAudioSamples, outputIAudioSamples);
		cleanUp(inputIStreamCoders, outputIStreamCoders);
		cleanUp(inputIPacket, outputIPacket);
	}

	@Override
	protected int getAudioBitRate(ICodec outputICodec, int originalBitRate) {
		return getCodecBitRate(
			outputICodec,
			getProperty(originalBitRate, _audioBitRate, AUDIO_BIT_RATE_MAX));
	}

	@Override
	protected int getAudioSampleRate() {
		return _audioSampleRate;
	}

	@Override
	protected IContainer getInputIContainer() {
		return _inputIContainer;
	}

	protected void initAudioBitRate(Properties audioProperties) {
		_audioBitRate = getProperty(
			audioProperties, PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_BIT_RATE,
			"audio bit rate", _audioContainer, AUDIO_BIT_RATE_DEFAULT,
			AUDIO_BIT_RATE_MAX);
	}

	protected void initAudioSampleRate(Properties audioProperties) {
		_audioSampleRate = getProperty(
			audioProperties, PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_SAMPLE_RATE,
			"audio sample rate", _audioContainer, AUDIO_SAMPLE_RATE_DEFAULT,
			AUDIO_SAMPLE_RATE_MAX);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayAudioConverter.class);

	private int _audioBitRate;
	private final String _audioContainer;
	private int _audioSampleRate;
	private IContainer _inputIContainer;
	private final String _inputURL;
	private IContainer _outputIContainer;
	private final String _outputURL;

}