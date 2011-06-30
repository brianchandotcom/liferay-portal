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

import com.xuggle.mediatool.IMediaCoder;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPixelFormat.Type;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.IVideoResampler;

/**
 * @author Juan González
 * @author Sergio González
 */
public class VideoResizer extends MediaToolAdapter {

	public VideoResizer(Integer height, Integer width) {
		_height = height;
		_width = width;
	}

	@Override
	public void onAddStream(IAddStreamEvent iAddStreamEvent) {
		IMediaCoder mediaCoder = iAddStreamEvent.getSource();

		IContainer container = mediaCoder.getContainer();

		int streamIndex = iAddStreamEvent.getStreamIndex();

		IStream stream = container.getStream(streamIndex);

		IStreamCoder streamCoder = stream.getStreamCoder();

		Type type= Type.YUV420P;

		if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
			streamCoder.setSampleRate(44100);
		}
		else if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
			streamCoder.setHeight(_height);
			streamCoder.setWidth(_width);
			streamCoder.setPixelType( type);
		}

		super.onAddStream(iAddStreamEvent);
	}

	@Override
	public void onAudioSamples(IAudioSamplesEvent iAudioSamplesEvent) {
		IAudioSamples iAudioSamples = iAudioSamplesEvent.getAudioSamples();

		int channels = iAudioSamples.getChannels();

		if (channels <= 0) {
			channels = 1;
		}

		if (_iAudioResampler == null) {
			_iAudioResampler = IAudioResampler.make(
				iAudioSamples.getChannels(), channels, 44100,
				iAudioSamples.getSampleRate());
		}

		if (iAudioSamples.getNumSamples() > 0) {
			IAudioSamples resampledIAudioSamples = IAudioSamples.make(
				iAudioSamples.getNumSamples(), channels);

			_iAudioResampler.resample(
				resampledIAudioSamples, iAudioSamples,
				iAudioSamples.getNumSamples());

			AudioSamplesEvent audioSamplesEvent = new AudioSamplesEvent(
				iAudioSamplesEvent.getSource(), resampledIAudioSamples,
				iAudioSamplesEvent.getStreamIndex());

			super.onAudioSamples(audioSamplesEvent);

			resampledIAudioSamples.delete();
		}
	}

	@Override
	public void onVideoPicture(IVideoPictureEvent event) {
		IVideoPicture iVideoPicture = event.getPicture();

		Type type = Type.YUV420P;

		if (_iVideoResampler == null) {
			_iVideoResampler = IVideoResampler.make(
				_width, _height, type, iVideoPicture.getWidth(),
				iVideoPicture.getHeight(), iVideoPicture.getPixelType());
		}

		IVideoPicture resampledIVideoPicture = IVideoPicture.make(
			type, _width, _height);

		_iVideoResampler.resample(resampledIVideoPicture, iVideoPicture);

		IVideoPictureEvent iVideoPictureEvent = new VideoPictureEvent(
			event.getSource(), resampledIVideoPicture, event.getStreamIndex());

		super.onVideoPicture(iVideoPictureEvent);

		resampledIVideoPicture.delete();
	}

	private int _height;
	private IAudioResampler _iAudioResampler;
	private IVideoResampler _iVideoResampler;
	private int _width;

}