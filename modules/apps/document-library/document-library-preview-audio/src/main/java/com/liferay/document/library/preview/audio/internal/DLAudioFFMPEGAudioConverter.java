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

package com.liferay.document.library.preview.audio.internal;

import com.liferay.document.library.kernel.util.AudioConverter;
import com.liferay.document.library.preview.audio.internal.configuration.DLAudioFFMPEGAudioConverterConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.preview.audio.internal.configuration.DLAudioFFMPEGAudioConverterConfiguration",
	service = {AudioConverter.class, DLAudioFFMPEGAudioConverter.class}
)
public class DLAudioFFMPEGAudioConverter implements AudioConverter {

	@Override
	public InputStream generateAudioPreview(File file, String format)
		throws Exception {

		Properties audioProperties = PropsUtil.getProperties(
			PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO, false);

		return _runFFMPEGCommand(
			String.format(
				"ffmpeg -y -i %s -b:a %d -ar %d", file.getAbsolutePath(),
				_getProperty(
					audioProperties,
					PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_BIT_RATE, format,
					_AUDIO_BIT_RATE_DEFAULT),
				_getProperty(
					audioProperties,
					PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_SAMPLE_RATE, format,
					_AUDIO_SAMPLE_RATE_DEFAULT)),
			format);
	}

	@Override
	public boolean isEnabled() {
		return _dlAudioFFMPEGAudioConverterConfiguration.enabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlAudioFFMPEGAudioConverterConfiguration =
			ConfigurableUtil.createConfigurable(
				DLAudioFFMPEGAudioConverterConfiguration.class, properties);
	}

	private int _getProperty(
		Properties audioProperties, String name, String audioContainer,
		int defaultValue) {

		return GetterUtil.getInteger(
			audioProperties.getProperty(
				StringBundler.concat(
					name, StringPool.OPEN_BRACKET, audioContainer,
					StringPool.CLOSE_BRACKET)),
			defaultValue);
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

	private static final int _AUDIO_BIT_RATE_DEFAULT = 64000;

	private static final int _AUDIO_SAMPLE_RATE_DEFAULT = 44100;

	private volatile DLAudioFFMPEGAudioConverterConfiguration
		_dlAudioFFMPEGAudioConverterConfiguration;

}