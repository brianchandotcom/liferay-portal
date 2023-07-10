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

package com.liferay.document.library.preview.audio.internal.background.task;

import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.preview.background.task.BasePreviewBackgroundTaskExecutor;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	property = "background.task.executor.class.name=com.liferay.document.library.preview.audio.internal.background.task.AudioPreviewBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class AudioPreviewBackgroundTaskExecutor
	extends BasePreviewBackgroundTaskExecutor {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	@Override
	protected void generatePreview(FileVersion fileVersion) throws Exception {
		audioProcessor.generateAudio(null, fileVersion);
	}

	@Override
	protected String[] getMimeTypes() {
		Set<String> audioMimeTypes = audioProcessor.getAudioMimeTypes();

		return audioMimeTypes.toArray(new String[0]);
	}

	@Reference
	protected AudioProcessor audioProcessor;

}