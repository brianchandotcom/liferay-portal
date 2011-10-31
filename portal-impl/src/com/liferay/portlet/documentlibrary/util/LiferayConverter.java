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

import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
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

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 */
public abstract class LiferayConverter {

	protected void cleanStreamCoders(
		IStreamCoder[] inputIStreamCoders, IStreamCoder[] outputIStreamCoders,
		int numOfStreams) {

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
	}

	protected int countNonKeyAfterKey(
		IPacket inputIPacket, Boolean keyPacketFound, int countNonKeyAfterKey) {

		if (inputIPacket.isKey()) {
			countNonKeyAfterKey = 0;
		}
		else if (keyPacketFound) {
			countNonKeyAfterKey++;
		}

		return countNonKeyAfterKey;
	}

	protected IAudioResampler createAudioResampler(
			IStreamCoder inputIStreamCoder, IStreamCoder outputIStreamCoder)
		throws Exception {

		IAudioResampler iAudioResampler = null;

		if ((inputIStreamCoder.getSampleRate() !=
				outputIStreamCoder.getSampleRate()) ||
			(inputIStreamCoder.getChannels() !=
				outputIStreamCoder.getChannels()) ||
			!inputIStreamCoder.getSampleFormat().equals(
				outputIStreamCoder.getSampleFormat())) {

			iAudioResampler =
				IAudioResampler.make(
					outputIStreamCoder.getChannels(),
					inputIStreamCoder.getChannels(),
					outputIStreamCoder.getSampleRate(),
					inputIStreamCoder.getSampleRate(),
					outputIStreamCoder.getSampleFormat(),
					inputIStreamCoder.getSampleFormat());

			if (iAudioResampler == null) {
				throw new RuntimeException("Audio resampling is not supported");
			}
		}

		return iAudioResampler;
	}

	protected IVideoResampler createVideoResampler(
			IStreamCoder inputIStreamCoder, IStreamCoder outputIStreamCoder,
			int height, int width)
		throws Exception {

		IVideoResampler iVideoResampler = null;

		int inputHeight = inputIStreamCoder.getHeight();
		int inputWidth = inputIStreamCoder.getWidth();

		IPixelFormat.Type inputPixelType = inputIStreamCoder.getPixelType();
		IPixelFormat.Type outputPixelType = outputIStreamCoder.getPixelType();

		if (!(height == inputHeight && width == inputWidth) ||
			!(inputPixelType.equals(outputPixelType))) {

			iVideoResampler = IVideoResampler.make(
				width, height, outputIStreamCoder.getPixelType(),
				inputIStreamCoder.getWidth(), inputIStreamCoder.getHeight(),
				inputIStreamCoder.getPixelType());

			if (iVideoResampler == null) {
				throw new RuntimeException("Video resampling is not supported");
			}
		}

		return iVideoResampler;
	}

	protected void decodeAudio(
			IAudioResampler iAudioResampler, IAudioSamples inputIAudioSample,
			IAudioSamples resampledIAudioSample, IPacket inputIPacket,
			IPacket outputIPacket, IStreamCoder inputIStreamCoder,
			IStreamCoder outputIStreamCoder, IContainer outputIContainer,
			int curPacketSize, int lastPacketSize, int streamIndex,
			long timeStampOffset)
		throws Exception {

		int returnValue = 0;

		int offset = 0;

		IAudioSamples outputIAudioSample = null;

		while (offset < inputIPacket.getSize()) {
			boolean stopDecoding = false;

			returnValue = inputIStreamCoder.decodeAudio(
				inputIAudioSample, inputIPacket, offset);

			if (returnValue <= 0) {
				if ((curPacketSize == lastPacketSize) &&
					(lastPacketSize != -1)) {

					throw new RuntimeException(
						"Cannot decode audio stream: " + streamIndex);
				}
				else {
					stopDecoding = true;
				}
			}

			updateAudioTimeStamp(inputIAudioSample, timeStampOffset);

			offset += returnValue;

			outputIAudioSample = resampleAudio(
				iAudioResampler, inputIAudioSample, resampledIAudioSample);

			encodeAudio(
				outputIStreamCoder, outputIPacket, outputIAudioSample,
				outputIContainer);

			if (stopDecoding) {
				if (_log.isDebugEnabled()) {
					_log.debug("Stop decoding audio stream: " + streamIndex);
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
			File thumbnailFile,	String thumbnailExtension, int thumbnailHeight,
			int thumbnailWidth, long timeStampOffset)
		throws Exception {

		int returnValue = inputIStreamCoder.decodeVideo(
			inputIVideoPicture, inputIPacket, 0);

		if (returnValue <= 0) {
			return returnValue;
		}

		updateVideoTimeStamp(inputIVideoPicture, timeStampOffset);

		if (inputIVideoPicture.isComplete()) {
			if (thumbnailFile != null) {
				BufferedImage image = null;

				if (_converterType == null) {
					_converterType = ConverterFactory.findRegisteredConverter(
						ConverterFactory.XUGGLER_BGR_24);
				}

				if (_converterType == null) {
					throw new UnsupportedOperationException(
						"No converter \"" +	ConverterFactory.XUGGLER_BGR_24 +
							"\" found");
				}

				if (_videoConverter == null) {
					_videoConverter = ConverterFactory.createConverter(
						_converterType.getDescriptor(),
						inputIVideoPicture);
				}

				image = _videoConverter.toImage(inputIVideoPicture);

				thumbnailFile.createNewFile();

				RenderedImage renderedImage = ImageProcessorUtil.scale(
					image, thumbnailHeight,	thumbnailWidth);

				ImageIO.write(
					renderedImage, thumbnailExtension,
					new FileOutputStream(thumbnailFile));

				return THUMBNAIL_GENERATED;
			}
			else {
				IVideoPicture outputIVideoPicture = null;

				outputIVideoPicture = resampleVideo(
					iVideoResampler, inputIVideoPicture,
					resampledIVideoPicture);

				outputIVideoPicture.setQuality(0);

				encodeVideo(
					outputIStreamCoder, outputIVideoPicture, outputIPacket,
					outputIContainer);
			}
		}

		return 1;
	}

	protected int decodeVideo(
			IVideoResampler iVideoResampler, IVideoPicture inputIVideoPicture,
			IVideoPicture resampledIVideoPicture, IPacket inputIPacket,
			IPacket outputIPacket, IStreamCoder inputIStreamCoder,
			IStreamCoder outputIStreamCoder, IContainer outputIContainer,
			long timeStampOffset)
		throws Exception {

		return decodeVideo(
			iVideoResampler, inputIVideoPicture, resampledIVideoPicture,
			inputIPacket, outputIPacket, inputIStreamCoder, outputIStreamCoder,
			outputIContainer, null, null, 0, 0, timeStampOffset);
	}

	protected void encodeAudio(
			IStreamCoder outputIStreamCoder, IPacket outputIPacket,
			IAudioSamples outputIAudioSample, IContainer outputIContainer)
		throws Exception {

		int numSamplesConsumed = 0;

		int returnValue = 0;

		while (numSamplesConsumed < outputIAudioSample.getNumSamples()) {
			returnValue = outputIStreamCoder.encodeAudio(
				outputIPacket, outputIAudioSample, numSamplesConsumed);

			if (returnValue <= 0) {
				throw new RuntimeException(
					"Cannot encode audio");
			}

			numSamplesConsumed += returnValue;

			if (outputIPacket.isComplete()) {
				returnValue = outputIContainer.writePacket(outputIPacket, true);

				if (returnValue < 0) {
					throw new RuntimeException("Cannot write audio packet");
				}
			}
		}
	}

	protected void encodeVideo(
			IStreamCoder outputIStreamCoder, IVideoPicture outputIVideoPicture,
			IPacket outputIPacket, IContainer outputIContainer)
		throws Exception {

		int returnValue = outputIStreamCoder.encodeVideo(
			outputIPacket, outputIVideoPicture, 0);

		if (returnValue < 0) {
			throw new RuntimeException("Cannot encode video");
		}

		if (outputIPacket.isComplete()) {
			returnValue = outputIContainer.writePacket(outputIPacket, true);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot write video packet");
			}
		}
	}

	protected void flushData(
		IStreamCoder[] outputIStreamCoders, IContainer outputIContainer,
		int numOfStreams) {

		for (int i = 0; i < numOfStreams; i++) {
			if (outputIStreamCoders[i] != null) {
				IPacket outputTempIPacket = IPacket.make();

				if (outputIStreamCoders[i].getCodecType() ==
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
	}

	protected long getStreamTimeStampOffset(IStream iStream) {
		long timeStampOffset = 0;

		if ((iStream.getStartTime() != Global.NO_PTS) &&
			(iStream.getStartTime() > 0) && (iStream.getTimeBase() != null)) {

			IRational defTimeBase =	IRational.make(
				1, (int)Global.DEFAULT_PTS_PER_SECOND);

			timeStampOffset = defTimeBase.rescale(
				iStream.getStartTime(), iStream.getTimeBase());
		}

		return timeStampOffset;
	}

	protected boolean keyPacketFound(
		IPacket inputIPacket, boolean keyPacketFound) {

		if (inputIPacket.isKey() && !keyPacketFound) {
			return true;
		}

		return keyPacketFound;
	}

	protected void openContainer(IContainer container, String url,
			boolean writeContainer)
		throws Exception {

		int returnValue = 0;

		if (writeContainer) {
			returnValue = container.open(url, IContainer.Type.WRITE, null);
		}
		else {
			returnValue = container.open(url, IContainer.Type.READ, null);
		}

		if (returnValue < 0) {
			if (writeContainer) {
				throw new RuntimeException("Cannot open output url");
			}
			else {
				throw new RuntimeException("Cannot open input url");
			}
		}
	}

	protected void openStreamCoder(IStreamCoder iStreamCoder)
		throws Exception {

		int returnValue = 0;

		if (iStreamCoder != null) {
			returnValue = iStreamCoder.open();

			if (returnValue < 0) {
				throw new RuntimeException("Cannot open coder");
			}
		}
	}

	protected void prepareAudio(
			IAudioResampler[] iAudioResamplers,
			IAudioSamples[] inputIAudioSamples,
			IAudioSamples[] outputIAudioSamples, IStreamCoder inputIStreamCoder,
			IStreamCoder[] outputIStreamCoders, IContainer outputIContainer,
			IStream[] outputIStreams, ICodec.Type inputICodecType,
			String outputUrl, int channels, int rate, int index)
		throws Exception {

		IStream outputIStream = outputIContainer.addNewStream(index);

		IStreamCoder outputIStreamCoder =
			outputIStream.getStreamCoder();

		outputIStreamCoders[index] = outputIStreamCoder;

		outputIStreams[index] = outputIStream;

		ICodec iCodec = ICodec.guessEncodingCodec(
			null, null, outputUrl, null, inputICodecType);

		if (iCodec == null) {
			throw new RuntimeException(
				"Cannot guess " + inputICodecType +	" encoder for " +
					outputUrl);
		}

		outputIStreamCoder.setBitRate(inputIStreamCoder.getBitRate());

		outputIStreamCoder.setChannels(channels);

		outputIStreamCoder.setCodec(iCodec);

		outputIStreamCoder.setGlobalQuality(0);

		outputIStreamCoder.setSampleRate(rate);

		iAudioResamplers[index] = createAudioResampler(
			inputIStreamCoder, outputIStreamCoder);

		inputIAudioSamples[index] = IAudioSamples.make(
			1024, inputIStreamCoder.getChannels());
		outputIAudioSamples[index] = IAudioSamples.make(
			1024, outputIStreamCoder.getChannels());
	}

	protected IAudioSamples resampleAudio(
			IAudioResampler iAudioResampler, IAudioSamples inputIAudioSample,
			IAudioSamples resampledIAudioSample)
		throws Exception {

		IAudioSamples outputIAudioSample = null;

		if ((iAudioResampler != null) &&
			(inputIAudioSample.getNumSamples() > 0)) {

			iAudioResampler.resample(
				resampledIAudioSample, inputIAudioSample,
				inputIAudioSample.getNumSamples());

			outputIAudioSample = resampledIAudioSample;
		}
		else {
			outputIAudioSample = inputIAudioSample;
		}

		return outputIAudioSample;
	}

	protected IVideoPicture resampleVideo(
		IVideoResampler iVideoResampler, IVideoPicture inputIVideoPicture,
		IVideoPicture resampledIVideoPicture)
		throws Exception {

		IVideoPicture outputIVideoPicture = null;

		int returnValue = 0;

		if (iVideoResampler != null) {
			returnValue = iVideoResampler.resample(
				resampledIVideoPicture, inputIVideoPicture);

			if (returnValue < 0) {
				throw new RuntimeException("Cannot resample video");
			}

			outputIVideoPicture = resampledIVideoPicture;
		}
		else {
			outputIVideoPicture = inputIVideoPicture;
		}

		return outputIVideoPicture;
	}

	protected boolean startDecoding(
		IPacket inputIPacket, IStreamCoder inputIStreamCoder,
		int countNonKeyAfterKey, boolean onlyDecodeKeyPackets,
		boolean keyPacketFound) {

		boolean startDecoding = keyPacketFound;

		if (inputIStreamCoder.getCodecID().equals(
			ICodec.ID.CODEC_ID_MJPEG)) {

			startDecoding = true;
		}
		else if (inputIStreamCoder.getCodecID().equals(
			ICodec.ID.CODEC_ID_MPEG2VIDEO)) {

			startDecoding = false;

			if (countNonKeyAfterKey != 1) {
				startDecoding = true;
			}
		}

		if (onlyDecodeKeyPackets && !inputIPacket.isKey()) {
			startDecoding = false;
		}

		return startDecoding;
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
				inputIVideoPicture.getTimeStamp() -	timeStampOffset);
		}
	}

	private ConverterFactory.Type _converterType;

	private IConverter _videoConverter;

	protected static final int THUMBNAIL_GENERATED = 2;

	private static Log _log = LogFactoryUtil.getLog(LiferayConverter.class);

}