/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.messaging;

import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portlet.documentlibrary.model.DLProcessorConstants;
import com.liferay.portlet.documentlibrary.util.DLProcessor;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessor;

/**
 * @author Juan González
 * @author Sergio González
 */
public class VideoProcessorMessageListener
	extends BaseProcessorMessageListener {

	@Override
	protected void generate(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		DLProcessor dlProcessor = DLProcessorRegistryUtil.getDLProcessor(
			DLProcessorConstants.VIDEO_PROCESSOR);

		if (dlProcessor == null) {
			return;
		}

		VideoProcessor videoProcessor = (VideoProcessor)dlProcessor;

		videoProcessor.generateVideo(sourceFileVersion, destinationFileVersion);
	}

}