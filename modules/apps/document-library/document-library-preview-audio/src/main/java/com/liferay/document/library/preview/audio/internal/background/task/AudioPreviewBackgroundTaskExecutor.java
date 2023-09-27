/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.audio.internal.background.task;

import com.liferay.document.library.kernel.util.AudioProcessor;
import com.liferay.document.library.preview.background.task.BasePreviewBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
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

	@Override
	protected void generatePreview(FileVersion fileVersion) throws Exception {
		audioProcessor.generateAudio(null, fileVersion);
	}

	@Override
	protected String[] getMimeTypes() {
		return ArrayUtil.toStringArray(audioProcessor.getAudioMimeTypes());
	}

	@Reference
	protected AudioProcessor audioProcessor;

}