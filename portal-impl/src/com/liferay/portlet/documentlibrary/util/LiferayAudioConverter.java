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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import com.xuggle.ferry.RefCounted;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

import java.util.List;
import java.util.Properties;

/**
 * @author Juan González
 * @author Sergio González
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class LiferayAudioConverter {

	public LiferayAudioConverter(
		String inputURL, String outputURL, String audioContainer,
		Properties audioProperties) {

		_inputURL = inputURL;
		_outputURL = outputURL;
		_audioContainer = audioContainer;

		initAudioBitRate(audioProperties);
		initAudioSampleRate(audioProperties);
	}

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

	protected void cleanUp(IPacket inputIPacket, IPacket outputIPacket) {
		if (inputIPacket != null) {
			inputIPacket.delete();
		}

		if (outputIPacket != null) {
			outputIPacket.delete();
		}
	}

	protected void cleanUp(
		IStreamCoder[] inputIStreamCoders, IStreamCoder[] outputIStreamCoders) {

		if (inputIStreamCoders != null) {
			for (IStreamCoder iStreamCoder : inputIStreamCoders) {
				if (iStreamCoder != null) {
					iStreamCoder.close();
				}
			}
		}

		if (outputIStreamCoders != null) {
			for (IStreamCoder iStreamCoder : outputIStreamCoders) {
				if (iStreamCoder != null) {
					iStreamCoder.close();
				}
			}
		}
	}

	protected void cleanUp(
		RefCounted[] inputRefCountedArray, RefCounted[] outputRefCountedArray) {

		if (inputRefCountedArray != null) {
			for (RefCounted refCounted : inputRefCountedArray) {
				if (refCounted != null) {
					refCounted.delete();
				}
			}
		}

		if (outputRefCountedArray != null) {
			for (RefCounted refCounted : outputRefCountedArray) {
				if (refCounted != null) {
					refCounted.delete();
				}
			}
		}
	}

	protected IAudioResampler createIAudioResampler(
			IStreamCoder inputIStreamCoder, IStreamCoder outputIStreamCoder)
		throws Exception {

		IAudioResampler iAudioResampler = null;

		IAudioSamples.Format inputSampleFormat =
			inputIStreamCoder.getSampleFormat();
		IAudioSamples.Format outputSampleFormat =
			outputIStreamCoder.getSampleFormat();

		if ((inputIStreamCoder.getChannels() ==
				outputIStreamCoder.getChannels()) &&
			(inputIStreamCoder.getSampleRate() ==
				outputIStreamCoder.getSampleRate()) &&
			inputSampleFormat.equals(outputSampleFormat)) {

			return iAudioResampler;
		}

		iAudioResampler = IAudioResampler.make(
			outputIStreamCoder.getChannels(), inputIStreamCoder.getChannels(),
			outputIStreamCoder.getSampleRate(),
			inputIStreamCoder.getSampleRate(),
			outputIStreamCoder.getSampleFormat(),
			inputIStreamCoder.getSampleFormat());

		if (iAudioResampler == null) {
			throw new RuntimeException("Audio resampling is not supported");
		}

		return iAudioResampler;
	}

	protected void decodeAudio(
			IAudioResampler iAudioResampler, IAudioSamples inputIAudioSample,
			IAudioSamples resampledIAudioSample, IPacket inputIPacket,
			IPacket outputIPacket, IStreamCoder inputIStreamCoder,
			IStreamCoder outputIStreamCoder, IContainer outputIContainer,
			int currentPacketSize, int previousPacketSize, int streamIndex,
			long timeStampOffset)
		throws Exception {

		int offset = 0;

		while (offset < inputIPacket.getSize()) {
			boolean stopDecoding = false;

			int value = inputIStreamCoder.decodeAudio(
				inputIAudioSample, inputIPacket, offset);

			if (value <= 0) {
				if ((previousPacketSize == currentPacketSize) &&
					(previousPacketSize != -1)) {

					throw new RuntimeException(
						"Unable to decode audio stream " + streamIndex);
				}

				stopDecoding = true;
			}

			updateAudioTimeStamp(inputIAudioSample, timeStampOffset);

			offset += value;

			IAudioSamples outputIAudioSample = resampleAudio(
				iAudioResampler, inputIAudioSample, resampledIAudioSample);

			encodeAudio(
				outputIStreamCoder, outputIPacket, outputIAudioSample,
				outputIContainer);

			if (stopDecoding) {
				if (_log.isDebugEnabled()) {
					_log.debug("Stop decoding audio stream " + streamIndex);
				}

				break;
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

	protected void encodeAudio(
			IStreamCoder outputIStreamCoder, IPacket outputIPacket,
			IAudioSamples outputIAudioSample, IContainer outputIContainer)
		throws Exception {

		int consumedSamplesCount = 0;

		while (consumedSamplesCount < outputIAudioSample.getNumSamples()) {
			int value = outputIStreamCoder.encodeAudio(
				outputIPacket, outputIAudioSample, consumedSamplesCount);

			if (value <= 0) {
				throw new RuntimeException("Unable to encode audio");
			}

			consumedSamplesCount += value;

			if (outputIPacket.isComplete()) {
				value = outputIContainer.writePacket(outputIPacket, true);

				if (value < 0) {
					throw new RuntimeException("Unable to write audio packet");
				}
			}
		}
	}

	protected void flush(
		IStreamCoder outputIStreamCoder, IContainer outputIContainer,
		IPacket iPacket) {

		if (outputIStreamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
			outputIStreamCoder.encodeAudio(iPacket, null, 0);
		}

		if (iPacket.isComplete()) {
			outputIContainer.writePacket(iPacket, true);
		}
	}

	protected void flush(
		IStreamCoder[] outputIStreamCoders, IContainer outputIContainer) {

		for (IStreamCoder outputIStreamCoder : outputIStreamCoders) {
			if (outputIStreamCoder == null) {
				continue;
			}

			IPacket iPacket = IPacket.make();

			flush(outputIStreamCoder, outputIContainer, iPacket);

			while (iPacket.isComplete()) {
				flush(outputIStreamCoder, outputIContainer, iPacket);
			}
		}
	}

	protected int getAudioBitRate(ICodec outputICodec, int originalBitRate) {
		return getCodecBitRate(
			outputICodec,
			getProperty(originalBitRate, _audioBitRate, AUDIO_BIT_RATE_MAX));
	}

	protected int getAudioEncodingChannels(
		IContainer outputIContainer, int channels) {

		IContainerFormat iContainerFormat =
			outputIContainer.getContainerFormat();

		String outputFormat = iContainerFormat.getOutputFormatShortName();

		if (outputFormat.equals("ogg")) {
			return 2;
		}

		if ((channels == 0) || (channels > 2)) {
			channels = 2;
		}

		return channels;
	}

	protected ICodec getAudioEncodingICodec(IContainer outputIContainer) {
		IContainerFormat iContainerFormat =
			outputIContainer.getContainerFormat();

		String outputFormat = iContainerFormat.getOutputFormatShortName();

		if (outputFormat.equals("ogg")) {
			return ICodec.findEncodingCodec(ICodec.ID.CODEC_ID_VORBIS);
		}

		return null;
	}

	protected IAudioSamples.Format getAudioSampleFormat(
		ICodec outputICodec, IAudioSamples.Format originalSampleFormat) {

		IAudioSamples.Format sampleFormat = null;

		List<IAudioSamples.Format> supportedSampleFormats =
			outputICodec.getSupportedAudioSampleFormats();

		for (IAudioSamples.Format supportedSampleFormat :
				supportedSampleFormats) {

			sampleFormat = supportedSampleFormat;

			if (supportedSampleFormat == originalSampleFormat) {
				break;
			}
		}

		return sampleFormat;
	}

	protected int getAudioSampleRate() {
		return _audioSampleRate;
	}

	protected int getCodecBitRate(ICodec outputICodec, int originalBitRate) {
		if ((originalBitRate == 0) || (originalBitRate > AUDIO_BIT_RATE_MAX)) {
			originalBitRate = AUDIO_BIT_RATE_DEFAULT;
		}

		ICodec.ID iCodecID = outputICodec.getID();

		if (iCodecID.equals(ICodec.ID.CODEC_ID_VORBIS) &&
			(originalBitRate < 64000)) {

			return 64000;
		}

		return originalBitRate;
	}

	protected int getProperty(
		int originalValue, int defaultValue, int maxValue) {

		if (originalValue <= 0) {
			return defaultValue;
		}
		else if (originalValue > maxValue) {
			return maxValue;
		}

		return originalValue;
	}

	protected int getProperty(
		Properties properties, String propertyName, String prettyPropertyName,
		String container, int defaultValue, int maxValue) {

		int property = GetterUtil.getInteger(
			properties.getProperty(
				StringBundler.concat(propertyName, "[", container, "]")),
			defaultValue);

		if (property > maxValue) {
			property = maxValue;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Default ", prettyPropertyName, " for ", container,
					" configured to ", property));
		}

		return property;
	}

	protected long getStreamTimeStampOffset(IStream iStream) {
		long timeStampOffset = 0;

		if ((iStream.getStartTime() != Global.NO_PTS) &&
			(iStream.getStartTime() > 0) && (iStream.getTimeBase() != null)) {

			IRational iRational = IRational.make(
				1, (int)Global.DEFAULT_PTS_PER_SECOND);

			timeStampOffset = iRational.rescale(
				iStream.getStartTime(), iStream.getTimeBase());
		}

		return timeStampOffset;
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

	protected void openContainer(
			IContainer iContainer, String url, boolean writeContainer)
		throws Exception {

		int value = 0;

		if (writeContainer) {
			value = iContainer.open(url, IContainer.Type.WRITE, null);
		}
		else {
			value = iContainer.open(url, IContainer.Type.READ, null);
		}

		if (value < 0) {
			if (writeContainer) {
				throw new RuntimeException("Unable to open output URL");
			}

			throw new RuntimeException("Unable to open input URL");
		}
	}

	protected void openStreamCoder(IStreamCoder iStreamCoder) throws Exception {
		if ((iStreamCoder != null) &&
			(iStreamCoder.getCodecType() != ICodec.Type.CODEC_TYPE_UNKNOWN)) {

			int result = iStreamCoder.setStandardsCompliance(
				IStreamCoder.CodecStandardsCompliance.COMPLIANCE_EXPERIMENTAL);

			if (result < 0) {
				throw new RuntimeException(
					"Unable to set compliance mode to experimental");
			}

			if (iStreamCoder.open(null, null) < 0) {
				throw new RuntimeException("Unable to open coder");
			}
		}
	}

	protected void prepareAudio(
			IAudioResampler[] iAudioResamplers,
			IAudioSamples[] inputIAudioSamples,
			IAudioSamples[] outputIAudioSamples, IStreamCoder inputIStreamCoder,
			IStreamCoder[] outputIStreamCoders, IContainer outputIContainer,
			IStream[] outputIStreams, ICodec.Type inputICodecType,
			String outputURL, int index)
		throws Exception {

		ICodec iCodec = getAudioEncodingICodec(outputIContainer);

		if (iCodec == null) {
			iCodec = ICodec.guessEncodingCodec(
				null, null, outputURL, null, inputICodecType);
		}

		if (iCodec == null) {
			throw new RuntimeException(
				StringBundler.concat(
					"Unable to determine ", inputICodecType, " encoder for ",
					outputURL));
		}

		IStream outputIStream = outputIContainer.addNewStream(iCodec);

		outputIStreams[index] = outputIStream;

		IStreamCoder outputIStreamCoder = outputIStream.getStreamCoder();

		outputIStreamCoders[index] = outputIStreamCoder;

		int bitRate = inputIStreamCoder.getBitRate();

		if (_log.isInfoEnabled()) {
			_log.info("Original audio bitrate " + bitRate);
		}

		bitRate = getAudioBitRate(iCodec, bitRate);

		if (_log.isInfoEnabled()) {
			_log.info("Modified audio bitrate " + bitRate);
		}

		outputIStreamCoder.setBitRate(bitRate);

		int channels = getAudioEncodingChannels(
			outputIContainer, inputIStreamCoder.getChannels());

		outputIStreamCoder.setChannels(channels);

		outputIStreamCoder.setGlobalQuality(0);

		IAudioSamples.Format sampleFormat = inputIStreamCoder.getSampleFormat();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Original audio sample format " + sampleFormat.toString());
		}

		sampleFormat = getAudioSampleFormat(iCodec, sampleFormat);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Modified audio sample format " + sampleFormat.toString());
		}

		outputIStreamCoder.setSampleFormat(sampleFormat);

		outputIStreamCoder.setSampleRate(getAudioSampleRate());

		iAudioResamplers[index] = createIAudioResampler(
			inputIStreamCoder, outputIStreamCoder);

		inputIAudioSamples[index] = IAudioSamples.make(
			1024, inputIStreamCoder.getChannels(),
			inputIStreamCoder.getSampleFormat());
		outputIAudioSamples[index] = IAudioSamples.make(
			1024, outputIStreamCoder.getChannels(),
			outputIStreamCoder.getSampleFormat());
	}

	protected IAudioSamples resampleAudio(
			IAudioResampler iAudioResampler, IAudioSamples inputIAudioSample,
			IAudioSamples resampledIAudioSample)
		throws Exception {

		if ((iAudioResampler == null) ||
			(inputIAudioSample.getNumSamples() <= 0)) {

			return inputIAudioSample;
		}

		iAudioResampler.resample(
			resampledIAudioSample, inputIAudioSample,
			inputIAudioSample.getNumSamples());

		return resampledIAudioSample;
	}

	protected void updateAudioTimeStamp(
		IAudioSamples inputAudioSample, long timeStampOffset) {

		if (inputAudioSample.getTimeStamp() != Global.NO_PTS) {
			inputAudioSample.setTimeStamp(
				inputAudioSample.getTimeStamp() - timeStampOffset);
		}
	}

	protected static final int AUDIO_BIT_RATE_DEFAULT = 64000;

	protected static final int AUDIO_BIT_RATE_MAX = 500000;

	protected static final int AUDIO_SAMPLE_RATE_DEFAULT = 44100;

	protected static final int AUDIO_SAMPLE_RATE_MAX = 192000;

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