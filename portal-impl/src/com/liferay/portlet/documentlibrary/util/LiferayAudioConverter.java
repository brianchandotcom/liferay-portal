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

			IAudioSamples[] mISamples = new IAudioSamples[numStreams];
			IAudioSamples[] mOSamples = new IAudioSamples[numStreams];

			for (int i = 0; i < numStreams; i++) {
				IStream is = _mIContainer.getStream(i);

				IStreamCoder ic = is.getStreamCoder();

				ICodec.Type cType = ic.getCodecType();

				mIStreams[i] = is;
				mOStreams[i] = null;

				mICoders[i] = ic;
				mOCoders[i] = null;

				mASamplers[i] = null;

				mISamples[i] = null;
				mOSamples[i] = null;

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

					if (ic.getSampleRate() != oc.getSampleRate()) {
						mASamplers[i] = IAudioResampler.make(
							oc.getChannels(), ic.getChannels(),
							oc.getSampleRate(), ic.getSampleRate());

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

				if (mICoders[i] != null) {
					retval = mICoders[i].open();

					if (retval < 0) {
						throw new RuntimeException("Cannot open input coder");
					}
				}

				if (mOCoders[i] != null) {
					retval = mOCoders[i].open();

					if (retval < 0) {
						throw new RuntimeException("Cannot open output coder");
					}
				}
			}

			retval = _mOContainer.writeHeader();

			if (retval < 0) {
				throw new RuntimeException("cannot write container header");
			}

			IPacket iPacket = IPacket.make();
			IPacket oPacket = IPacket.make();

			IAudioSamples inSamples = null;
			IAudioSamples outSamples = null;
			IAudioSamples reSamples = null;

			int lastPacketSize = -1;

			_mIContainer.readNextPacket(iPacket);

			while (_mIContainer.readNextPacket(iPacket) == 0) {
				int currentPacketSize = iPacket.getSize();

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

				lastPacketSize = iPacket.getSize();
			}

			numStreams = _mIContainer.getNumStreams();

			for (int i = 0; i < numStreams; i++) {
				if (mOCoders[i] != null) {
					IPacket oTmpPacket = IPacket.make();

					if (mOCoders[i].getCodecType() ==
							ICodec.Type.CODEC_TYPE_AUDIO) {

						mOCoders[i].encodeAudio(oTmpPacket, null, 0);
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

			mISamples = null;
			mOSamples = null;
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
		LiferayAudioConverter.class);

	private int _channels = 1;

	private String _inputUrl = null;

	private String _outputUrl = null;

	private IContainer _mIContainer = null;

	private IContainer _mOContainer = null;

	private int _rate = 0;

}