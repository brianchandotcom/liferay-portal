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
			_mIContainer = IContainer.make();
			_mOContainer = IContainer.make();

			int retval = _mIContainer.open(
				_inputUrl, IContainer.Type.READ, null);

			if (retval < 0) {
				throw new RuntimeException("Cannot open input url");
			}

			retval = _mOContainer.open(
				_outputUrl, IContainer.Type.WRITE, null);

			if (retval < 0) {
				throw new RuntimeException("Cannot open output url to write");
			}

			int numStreams = _mIContainer.getNumStreams();

			if (numStreams < 0) {
				throw new RuntimeException(
					"Input file doesn't have any stream");
			}

			IStream[] mIStreams = new IStream[numStreams];
			IStream[] mOStreams = new IStream[numStreams];

			IStreamCoder[] mICoders = new IStreamCoder[numStreams];
			IStreamCoder[] mOCoders = new IStreamCoder[numStreams];

			IAudioResampler[] mASamplers = new IAudioResampler[numStreams];
			IVideoResampler[] mVSamplers = new IVideoResampler[numStreams];

			IAudioSamples[] mISamples = new IAudioSamples[numStreams];
			IAudioSamples[] mOSamples = new IAudioSamples[numStreams];

			IVideoPicture[] mIVideoPictures = new IVideoPicture[numStreams];
			IVideoPicture[] mOVideoPictures = new IVideoPicture[numStreams];

			for (int i = 0; i < numStreams; i++) {
				IStream is = _mIContainer.getStream(i);

				IStreamCoder ic = is.getStreamCoder();

				ICodec.Type cType = ic.getCodecType();

				mIStreams[i] = is;
				mOStreams[i] = null;

				mICoders[i] = ic;
				mOCoders[i] = null;

				mASamplers[i] = null;
				mVSamplers[i] = null;

				mISamples[i] = null;
				mOSamples[i] = null;

				mIVideoPictures[i] = null;
				mOVideoPictures[i] = null;

				if (cType == ICodec.Type.CODEC_TYPE_AUDIO) {
					IStream os = _mOContainer.addNewStream(i);

					IStreamCoder oc = os.getStreamCoder();

					mOCoders[i] = oc;

					mOStreams[i] = os;

					ICodec codec = ICodec.guessEncodingCodec(
						null, null, _outputUrl, null, cType);

					if (codec == null) {
						throw new RuntimeException(
							"Cannot guess " + cType + " encoder for " +
								_outputUrl);
					}

					oc.setBitRate(ic.getBitRate());

					int channels = _channels;

					if (ic.getChannels() > 0) {
						channels = ic.getChannels();
					}

					oc.setChannels(channels);

					oc.setCodec(codec);

					oc.setGlobalQuality(0);

					int rate = _rate;

					if (ic.getSampleRate() > 0) {
						rate = ic.getSampleRate();
					}

					oc.setSampleRate(rate);

					if ((ic.getSampleRate() != oc.getSampleRate()) ||
						(ic.getChannels() != oc.getChannels()) ||
						!ic.getSampleFormat().equals(oc.getSampleFormat())) {

						mASamplers[i] =	IAudioResampler.make(
								oc.getChannels(), ic.getChannels(),
								oc.getSampleRate(), ic.getSampleRate(),
								oc.getSampleFormat(), ic.getSampleFormat());

						if (mASamplers[i] == null) {
							throw new RuntimeException(
								"Audio resampling is not supported");
						}
					}
					else {
						mASamplers[i] = null;
					}

					mISamples[i] = IAudioSamples.make(1024, ic.getChannels());
					mOSamples[i] = IAudioSamples.make(1024, oc.getChannels());
				}
				else if (cType == ICodec.Type.CODEC_TYPE_VIDEO) {
					IStream os = _mOContainer.addNewStream(i);

					IStreamCoder oc = os.getStreamCoder();

					mOCoders[i] = oc;

					mOStreams[i] = os;

					ICodec codec = ICodec.guessEncodingCodec(
						null, null, _outputUrl, null, cType);

					if (codec == null) {
						throw new RuntimeException(
							"Could not guess " + cType + " encoder for " +
								_outputUrl);
					}

					oc.setCodec(codec);

					if (ic.getBitRate() == 0) {
						oc.setBitRate(250000);
					}
					else {
						oc.setBitRate(ic.getBitRate());
					}

					int oHeight = ic.getHeight();

					int oWidth = ic.getWidth();

					if (oHeight <= 0 || oWidth <= 0) {
						throw new RuntimeException(
							"Cannot find width or height in url: " +
								_inputUrl);
					}

					oc.setPixelType(Type.YUV420P);

					if (!(_height == oHeight && _width == oWidth)) {
						mVSamplers[i] =	IVideoResampler.make(
							_width, _height, oc.getPixelType(), ic.getWidth(),
							ic.getHeight(),	ic.getPixelType());

						if (mVSamplers[i] == null) {
							throw new RuntimeException(
								"Video resampling is not supported");
						}
					}
					else {
						mVSamplers[i] = null;
					}

					oc.setHeight(_height);

					oc.setWidth(_width);

					IRational num = null;

					num = ic.getFrameRate();

					oc.setFrameRate(num);
					oc.setTimeBase(
						IRational.make(
							num.getDenominator(), num.getNumerator()));

					num = null;

					mIVideoPictures[i] = IVideoPicture.make(
						ic.getPixelType(), ic.getWidth(), ic.getHeight());

					mOVideoPictures[i] = IVideoPicture.make(
						oc.getPixelType(), oc.getWidth(), oc.getHeight());
				}

				if (mOCoders[i] != null) {
					retval = mOCoders[i].open();

					if (retval < 0) {
						throw new RuntimeException("Cannot open output coder");
					}
				}

				if (mICoders[i] != null) {
					retval = mICoders[i].open();

					if (retval < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}
			}

			retval = _mOContainer.writeHeader();

			if (retval < 0) {
				throw new RuntimeException("Cannot write container header");
			}

			IPacket iPacket = IPacket.make();
			IPacket oPacket = IPacket.make();

			IAudioSamples inSamples = null;
			IAudioSamples outSamples = null;
			IAudioSamples reSamples = null;

			boolean keyPacketFound = false;
			boolean onlyDecodeKeyPackets=false;
			int contNonKeyAfterKey = 0;

			int lastPacketSize = -1;
			int currentPacketSize = 0;

			while (_mIContainer.readNextPacket(iPacket) == 0) {
				currentPacketSize = iPacket.getSize();

				int streamIndex = iPacket.getStreamIndex();

				IStream stream = _mIContainer.getStream(streamIndex);

				int offset = 0;

				long timeStampOffset = 0;

				if ((stream.getStartTime() != Global.NO_PTS) &&
					(stream.getStartTime() > 0) &&
					(stream.getTimeBase() != null)) {

					IRational defTimeBase =	IRational.make(
						1, (int)Global.DEFAULT_PTS_PER_SECOND);

					timeStampOffset = defTimeBase.rescale(
						stream.getStartTime(), stream.getTimeBase());
				}

				IStreamCoder ic = mICoders[streamIndex];
				IStreamCoder oc = mOCoders[streamIndex];

				if (oc == null) {
					continue;
				}

				IAudioResampler as = mASamplers[streamIndex];
				IVideoResampler vs = mVSamplers[streamIndex];

				IVideoPicture inFrame = mIVideoPictures[streamIndex];
				IVideoPicture reFrame = mOVideoPictures[streamIndex];

				inSamples = mISamples[streamIndex];
				reSamples = mOSamples[streamIndex];

				ICodec.Type cType = ic.getCodecType();

				if (_log.isDebugEnabled()) {
					_log.debug("Current packet size:" + currentPacketSize);
				}

				if (cType == ICodec.Type.CODEC_TYPE_AUDIO) {
					while (offset < iPacket.getSize()) {
						boolean stopDecoding = false;

						retval = ic.decodeAudio(inSamples, iPacket, offset);

						if (retval <= 0) {
							if ((currentPacketSize == lastPacketSize) &&
								(lastPacketSize != -1)) {

								throw new RuntimeException(
									"Cannot decode audio stream: " +
										streamIndex);
							}
							else {
								stopDecoding = true;
							}
						}

						if (inSamples.getTimeStamp() != Global.NO_PTS) {
							inSamples.setTimeStamp(
								inSamples.getTimeStamp() - timeStampOffset);
						}

						offset += retval;

						int numSamplesConsumed = 0;

						if ((as != null) && (inSamples.getNumSamples() > 0)) {
							retval = as.resample(
								reSamples, inSamples,
								inSamples.getNumSamples());

							outSamples = reSamples;
						}
						else {
							outSamples = inSamples;
						}

						while (
							numSamplesConsumed < outSamples.getNumSamples()) {

							retval = oc.encodeAudio(
								oPacket, outSamples, numSamplesConsumed);

							if (retval <= 0) {
								throw new RuntimeException(
									"Cannot encode any audio: " + retval);
							}

							numSamplesConsumed += retval;

							if (oPacket.isComplete()) {
								retval = _mOContainer.writePacket(
									oPacket, true);

								if (retval < 0) {
									throw new RuntimeException(
										"Cannot write output packet");
								}
							}
						}

						if (stopDecoding) {
							if (_log.isDebugEnabled()) {
								_log.debug(
									"Forced stop decoding audio stream: " +
										streamIndex);
							}

							break;
						}
					}
				}
				else if (cType == ICodec.Type.CODEC_TYPE_VIDEO) {
					if (_log.isDebugEnabled()) {
						_log.debug("Is video packet Key: " + iPacket.isKey());
					}

					if (iPacket.isKey()) {
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

					if (ic.getCodecID().equals(ICodec.ID.CODEC_ID_MJPEG)) {
						startDecoding = true;
					}
					else if (
						ic.getCodecID().equals(
							ICodec.ID.CODEC_ID_MPEG2VIDEO)) {

						startDecoding = false;

						if (contNonKeyAfterKey != 1) {
							startDecoding = true;
						}
					}

					if (onlyDecodeKeyPackets && !iPacket.isKey()) {
						startDecoding = false;
					}

					if (!startDecoding) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Do not decode yet stream:" + streamIndex);
						}
					}

					if (startDecoding) {
						IVideoPicture outFrame = null;

						retval = ic.decodeVideo(inFrame, iPacket, 0);

						if (retval <= 0) {
							if (!iPacket.isKey()) {
								onlyDecodeKeyPackets = true;

								continue;
							}
							else{
								throw new RuntimeException(
								"Cannot decode any video in stream: " +
									streamIndex);
							}
						}

						if (inFrame.getTimeStamp() != Global.NO_PTS) {
							inFrame.setTimeStamp(
								inFrame.getTimeStamp() - timeStampOffset);
						}

						offset += retval;

						if (inFrame.isComplete()) {
							if (vs != null) {
								retval = vs.resample(reFrame, inFrame);

								if (retval < 0) {
									throw new RuntimeException(
										"Cannot resample video");
								}

								outFrame = reFrame;
							}
							else {
								outFrame = inFrame;
							}

							outFrame.setQuality(0);

							retval = oc.encodeVideo(oPacket, outFrame, 0);

							if (retval < 0) {
								throw new RuntimeException(
									"Cannot encode video");
							}

							if (oPacket.isComplete()) {
								retval = _mOContainer.writePacket(
									oPacket, true);

								if (retval < 0) {
									throw new RuntimeException(
										"Cannot write video packet");
								}
							}
						}
					}
				}

				lastPacketSize = iPacket.getSize();
			}

			numStreams = _mIContainer.getNumStreams();

			for (int i = 0; i < numStreams; i++) {
				if (mOCoders[i] != null) {
					IPacket oTmpPacket = IPacket.make();

					if (
						mOCoders[i].getCodecType() ==
							ICodec.Type.CODEC_TYPE_AUDIO) {

						mOCoders[i].encodeAudio(oTmpPacket, null, 0);
					}
					else {
						mOCoders[i].encodeVideo(oTmpPacket, null, 0);
					}
					if (oTmpPacket.isComplete()) {
						_mOContainer.writePacket(oTmpPacket, true);
					}
				}
			}

			retval = _mOContainer.writeTrailer();

			if (retval < 0) {
				throw new RuntimeException(
					"Cannot write trailer to output file");
			}

			for (int i = 0; i < numStreams; i++) {
				if (mOCoders[i] != null) {
					mOCoders[i].close();
				}

				mOCoders[i] = null;

				if (mICoders[i] != null) {
					mICoders[i].close();
				}

				mICoders[i] = null;
			}

			mICoders = null;
			mOCoders = null;

			mASamplers = null;
			mVSamplers = null;

			mISamples = null;
			mOSamples = null;

			mIVideoPictures = null;
			mOVideoPictures = null;
		}
		finally {
			if (_mIContainer.isOpened()) {
				_mIContainer.close();
				_mIContainer = null;
			}

			if (_mOContainer.isOpened()) {
				_mOContainer.close();
				_mOContainer = null;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayVideoConverter.class);

	private String _inputUrl = null;

	private String _outputUrl = null;

	private int _height = 240;

	private int _width = 320;

	private int _channels = 1;

	private int _rate = 0;

	private IContainer _mIContainer = null;

	private IContainer _mOContainer = null;

}