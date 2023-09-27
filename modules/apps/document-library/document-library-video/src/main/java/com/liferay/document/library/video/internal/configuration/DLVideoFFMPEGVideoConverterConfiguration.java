/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.video.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	descriptionArguments = "https://ffmpeg.org/"
)
@Meta.OCD(
	description = "dl-video-ffmpeg-video-converter-configuration-description",
	id = "com.liferay.document.library.video.internal.configuration.DLVideoFFMPEGVideoConverterConfiguration",
	localization = "content/Language",
	name = "dl-video-ffmpeg-video-converter-configuration-name"
)
public interface DLVideoFFMPEGVideoConverterConfiguration {

	/**
	 * Enables video conversion
	 */
	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}