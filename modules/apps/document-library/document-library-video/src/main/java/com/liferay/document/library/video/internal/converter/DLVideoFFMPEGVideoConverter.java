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

package com.liferay.document.library.video.internal.converter;

import com.liferay.document.library.kernel.util.VideoConverter;
import com.liferay.document.library.video.internal.configuration.DLVideoFFMPEGVideoConverterConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.video.internal.configuration.DLVideoFFMPEGVideoConverterConfiguration",
	service = {DLVideoFFMPEGVideoConverter.class, VideoConverter.class}
)
public class DLVideoFFMPEGVideoConverter implements VideoConverter {

	@Override
	public InputStream generateVideoPreview(File file, String containerType)
		throws Exception {

		Properties videoProperties = PropsUtil.getProperties(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO, false);

		return _runFFMPEGCommand(
			String.format(
				"ffmpeg -y -i %s -b:v %dk -vf scale=min(%d\\,iw):-2 -r %d",
				file.getAbsolutePath(),
				_getVideoBitRate(videoProperties, containerType) / 1000,
				GetterUtil.getInteger(
					PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH),
				_getVideoFrameRate(videoProperties, containerType)),
			containerType);
	}

	@Override
	public InputStream generateVideoThumbnail(File file, String format)
		throws Exception {

		try {
			return _runFFMPEGCommand(
				String.format(
					"ffmpeg -y -i %s -vf thumbnail,scale=w=min(%s\\,iw):h=-1 " +
						"-frames:v 1",
					file.getAbsolutePath(),
					PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH),
				format);
		}
		catch (Exception exception) {
			String message = exception.getMessage();

			if (message.contains(
					"Output file #0 does not contain any stream")) {

				BufferedImage bufferedImage = new BufferedImage(
					PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH,
					PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
					BufferedImage.TYPE_INT_RGB);

				try (UnsyncByteArrayOutputStream baos =
						new UnsyncByteArrayOutputStream()) {

					ImageToolUtil.write(bufferedImage, format, baos);

					return new ByteArrayInputStream(baos.toByteArray());
				}
			}

			throw exception;
		}
	}

	@Override
	public boolean isEnabled() {
		return _dlVideoFFMPEGVideoConverterConfiguration.enabled();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		modified(properties);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_dlVideoFFMPEGVideoConverterConfiguration =
			ConfigurableUtil.createConfigurable(
				DLVideoFFMPEGVideoConverterConfiguration.class, properties);
	}

	private int _getVideoBitRate(
		Properties videoProperties, String videoContainer) {

		int videoBitRate = GetterUtil.getInteger(
			videoProperties.getProperty(
				StringBundler.concat(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_BIT_RATE, "[",
					videoContainer, "]")),
			_VIDEO_BIT_RATE_DEFAULT);

		if (videoBitRate > _VIDEO_BIT_RATE_MAX) {
			videoBitRate = _VIDEO_BIT_RATE_MAX;
		}

		return videoBitRate;
	}

	private int _getVideoFrameRate(
		Properties videoProperties, String videoContainer) {

		int denominator = GetterUtil.getInteger(
			videoProperties.getProperty(
				StringBundler.concat(
					PropsKeys.
						DL_FILE_ENTRY_PREVIEW_VIDEO_FRAME_RATE_DENOMINATOR,
					StringPool.OPEN_BRACKET, videoContainer,
					StringPool.CLOSE_BRACKET)));

		int numerator = GetterUtil.getInteger(
			videoProperties.getProperty(
				StringBundler.concat(
					PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_FRAME_RATE_NUMERATOR,
					"[", videoContainer, "]")));

		return numerator / denominator;
	}

	private InputStream _runFFMPEGCommand(String ffmpegCommand, String format)
		throws Exception {

		File destinationFile = FileUtil.createTempFile(format);

		Runtime runtime = Runtime.getRuntime();

		Process process = runtime.exec(
			ffmpegCommand + StringPool.SPACE +
				destinationFile.getAbsolutePath());

		if (process.waitFor() != 0) {
			throw new Exception(StringUtil.read(process.getErrorStream()));
		}

		return new FileInputStream(destinationFile);
	}

	private static final int _VIDEO_BIT_RATE_DEFAULT = 250000;

	private static final int _VIDEO_BIT_RATE_MAX = 1200000;

	private volatile DLVideoFFMPEGVideoConverterConfiguration
		_dlVideoFFMPEGVideoConverterConfiguration;

}