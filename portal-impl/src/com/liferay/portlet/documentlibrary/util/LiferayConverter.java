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
import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import com.xuggle.ferry.RefCounted;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileOutputStream;

import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class LiferayConverter {

	public abstract void convert() throws Exception;

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

	protected int countNonKeyAfterKey(
		IPacket inputIPacket, Boolean keyPacketFound, int nonKeyAfterKeyCount) {

		if (inputIPacket.isKey()) {
			nonKeyAfterKeyCount = 0;
		}
		else if (keyPacketFound) {
			nonKeyAfterKeyCount++;
		}

		return nonKeyAfterKeyCount;
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

	protected IVideoResampler createIVideoResampler(
			IStreamCoder inputIStreamCoder, IStreamCoder outputIStreamCoder,
			int height, int width)
		throws Exception {

		IVideoResampler iVideoResampler = null;

		IPixelFormat.Type inputIPixelFormatType =
			inputIStreamCoder.getPixelType();
		IPixelFormat.Type outputIPixelFormatType =
			outputIStreamCoder.getPixelType();

		if ((height == inputIStreamCoder.getHeight()) &&
			(width == inputIStreamCoder.getWidth()) &&
			inputIPixelFormatType.equals(outputIPixelFormatType)) {

			return iVideoResampler;
		}

		iVideoResampler = IVideoResampler.make(
			width, height, outputIStreamCoder.getPixelType(),
			inputIStreamCoder.getWidth(), inputIStreamCoder.getHeight(),
			inputIStreamCoder.getPixelType());

		if (iVideoResampler == null) {
			throw new RuntimeException("Video resampling is not supported");
		}

		return iVideoResampler;
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

	protected int decodeVideo(
			IVideoResampler iVideoResampler, IVideoPicture inputIVideoPicture,
			IVideoPicture resampledIVideoPicture, IPacket inputIPacket,
			IPacket outputIPacket, IStreamCoder inputIStreamCoder,
			IStreamCoder outputIStreamCoder, IContainer outputIContainer,
			File thumbnailFile, String thumbnailExtension, int thumbnailHeight,
			int thumbnailWidth, long timeStampOffset)
		throws Exception {

		int offset = 0;

		boolean stopDecoding = false;

		while (offset < inputIPacket.getSize()) {
			int value = inputIStreamCoder.decodeVideo(
				inputIVideoPicture, inputIPacket, offset);

			if (value <= 0) {
				return value;
			}

			updateVideoTimeStamp(inputIVideoPicture, timeStampOffset);

			offset += value;

			// Workaround for FFmpeg bug. See
			// http://comments.gmane.org/gmane.comp.video.ffmpeg.devel/135657

			ICodec.ID iCodecID = inputIStreamCoder.getCodecID();

			if (iCodecID.equals(ICodec.ID.CODEC_ID_MJPEG)) {
				stopDecoding = true;
			}

			if (!inputIVideoPicture.isComplete()) {
				if (stopDecoding) {
					return 1;
				}

				continue;
			}

			if (thumbnailFile != null) {
				BufferedImage bufferedImage = null;

				if (_converterFactoryType == null) {
					_converterFactoryType =
						ConverterFactory.findRegisteredConverter(
							ConverterFactory.XUGGLER_BGR_24);
				}

				if (_converterFactoryType == null) {
					throw new UnsupportedOperationException(
						"No converter found for " +
							ConverterFactory.XUGGLER_BGR_24);
				}

				if (_videoIConverter == null) {
					_videoIConverter = ConverterFactory.createConverter(
						_converterFactoryType.getDescriptor(),
						inputIVideoPicture);
				}

				bufferedImage = _videoIConverter.toImage(inputIVideoPicture);

				thumbnailFile.createNewFile();

				ImageTool imageTool = ImageToolImpl.getInstance();

				RenderedImage renderedImage = imageTool.scale(
					bufferedImage, thumbnailHeight, thumbnailWidth);

				ImageIO.write(
					renderedImage, thumbnailExtension,
					new FileOutputStream(thumbnailFile));

				return DECODE_VIDEO_THUMBNAIL;
			}

			if ((outputIStreamCoder != null) && (outputIContainer != null)) {
				IVideoPicture outputIVideoPicture = resampleVideo(
					iVideoResampler, inputIVideoPicture,
					resampledIVideoPicture);

				outputIVideoPicture.setQuality(0);

				encodeVideo(
					outputIStreamCoder, outputIVideoPicture, outputIPacket,
					outputIContainer);
			}

			if (stopDecoding) {
				break;
			}
		}

		return 1;
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

	protected void encodeVideo(
			IStreamCoder outputIStreamCoder, IVideoPicture outputIVideoPicture,
			IPacket outputIPacket, IContainer outputIContainer)
		throws Exception {

		int value = outputIStreamCoder.encodeVideo(
			outputIPacket, outputIVideoPicture, 0);

		if (value < 0) {
			throw new RuntimeException("Unable to encode video");
		}

		if (outputIPacket.isComplete()) {
			value = outputIContainer.writePacket(outputIPacket, true);

			if (value < 0) {
				throw new RuntimeException("Unable to write video packet");
			}
		}
	}

	protected void flush(
		IStreamCoder outputIStreamCoder, IContainer outputIContainer,
		IPacket iPacket) {

		if (outputIStreamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
			outputIStreamCoder.encodeAudio(iPacket, null, 0);
		}
		else {
			outputIStreamCoder.encodeVideo(iPacket, null, 0);
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
			getProperty(
				originalBitRate, AUDIO_BIT_RATE_DEFAULT, AUDIO_BIT_RATE_MAX));
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
		return AUDIO_SAMPLE_RATE_DEFAULT;
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

	protected abstract IContainer getInputIContainer();

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

	protected long getSeekTimeStamp(int percentage) throws Exception {
		IContainer inputIContainer = getInputIContainer();

		long seekTimeStamp = -1;

		long videoSeconds = inputIContainer.getDuration() / 1000000L;

		long seekSeconds = (videoSeconds * percentage) / 100L;

		for (int i = 0; i < inputIContainer.getNumStreams(); i++) {
			IStream inputIStream = inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			if (inputIStreamCoder.getCodecType() !=
					ICodec.Type.CODEC_TYPE_VIDEO) {

				continue;
			}

			IRational iRational = inputIStream.getTimeBase();

			long timeStampOffset =
				iRational.getDenominator() / iRational.getNumerator() *
					seekSeconds;

			seekTimeStamp = inputIContainer.getStartTime() + timeStampOffset;

			break;
		}

		return seekTimeStamp;
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

	protected boolean isKeyPacketFound(
		IPacket inputIPacket, boolean keyPacketFound) {

		if (inputIPacket.isKey() && !keyPacketFound) {
			return true;
		}

		return keyPacketFound;
	}

	protected boolean isStartDecoding(
		IPacket inputIPacket, IStreamCoder inputIStreamCoder,
		boolean keyPacketFound, int nonKeyAfterKeyCount,
		boolean onlyDecodeKeyPackets) {

		if (onlyDecodeKeyPackets && !inputIPacket.isKey()) {
			return false;
		}

		ICodec.ID iCodecID = inputIStreamCoder.getCodecID();

		if (iCodecID.equals(ICodec.ID.CODEC_ID_MJPEG)) {
			return true;
		}
		else if (iCodecID.equals(ICodec.ID.CODEC_ID_MPEG2VIDEO) ||
				 iCodecID.equals(ICodec.ID.CODEC_ID_THEORA)) {

			if (nonKeyAfterKeyCount != 1) {
				return true;
			}

			return false;
		}

		return keyPacketFound;
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

	protected IVideoPicture resampleVideo(
			IVideoResampler iVideoResampler, IVideoPicture inputIVideoPicture,
			IVideoPicture resampledIVideoPicture)
		throws Exception {

		if (iVideoResampler == null) {
			return inputIVideoPicture;
		}

		int result = iVideoResampler.resample(
			resampledIVideoPicture, inputIVideoPicture);

		if (result < 0) {
			throw new RuntimeException("Unable to resample video");
		}

		return resampledIVideoPicture;
	}

	protected void rewind() throws Exception {
		IContainer inputIContainer = getInputIContainer();

		if (inputIContainer == null) {
			return;
		}

		int value = 0;

		for (int i = 0; i < inputIContainer.getNumStreams(); i++) {
			IStream inputIStream = inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			if (inputIStreamCoder.getCodecType() !=
					ICodec.Type.CODEC_TYPE_VIDEO) {

				continue;
			}

			value = rewind(i);

			if (value < 0) {
				throw new RuntimeException("Error while seeking file");
			}

			break;
		}
	}

	protected int rewind(int index) throws Exception {
		IContainer inputIContainer = getInputIContainer();

		if (inputIContainer == null) {
			return -1;
		}

		int value = inputIContainer.seekKeyFrame(index, -1, 0);

		if (value < 0) {
			value = inputIContainer.seekKeyFrame(
				index, -1, IContainer.SEEK_FLAG_BACKWARDS);
		}

		if (value < 0) {
			throw new RuntimeException("Error while seeking file");
		}

		return value;
	}

	protected int seek(int index, long timeStamp) throws Exception {
		IContainer inputIContainer = getInputIContainer();

		if (inputIContainer == null) {
			return -1;
		}

		int value = inputIContainer.seekKeyFrame(index, timeStamp, 0);

		if (value < 0) {
			throw new RuntimeException("Error while seeking file");
		}

		return value;
	}

	protected long seek(long timeStamp) throws Exception {
		IContainer inputIContainer = getInputIContainer();

		if (inputIContainer == null) {
			return -1;
		}

		int value = 0;

		for (int i = 0; i < inputIContainer.getNumStreams(); i++) {
			IStream inputIStream = inputIContainer.getStream(i);

			IStreamCoder inputIStreamCoder = inputIStream.getStreamCoder();

			if (inputIStreamCoder.getCodecType() !=
					ICodec.Type.CODEC_TYPE_VIDEO) {

				continue;
			}

			value = seek(i, timeStamp);

			if (value < 0) {
				throw new RuntimeException("Error while seeking file");
			}

			break;
		}

		return value;
	}

	protected void updateAudioTimeStamp(
		IAudioSamples inputAudioSample, long timeStampOffset) {

		if (inputAudioSample.getTimeStamp() != Global.NO_PTS) {
			inputAudioSample.setTimeStamp(
				inputAudioSample.getTimeStamp() - timeStampOffset);
		}
	}

	protected void updateVideoTimeStamp(
		IVideoPicture inputIVideoPicture, long timeStampOffset) {

		if (inputIVideoPicture.getTimeStamp() != Global.NO_PTS) {
			inputIVideoPicture.setTimeStamp(
				inputIVideoPicture.getTimeStamp() - timeStampOffset);
		}
	}

	protected static final int AUDIO_BIT_RATE_DEFAULT = 64000;

	protected static final int AUDIO_BIT_RATE_MAX = 500000;

	protected static final int AUDIO_SAMPLE_RATE_DEFAULT = 44100;

	protected static final int AUDIO_SAMPLE_RATE_MAX = 192000;

	protected static final int DECODE_VIDEO_THUMBNAIL = 2;

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayConverter.class);

	private ConverterFactory.Type _converterFactoryType;
	private IConverter _videoIConverter;

}