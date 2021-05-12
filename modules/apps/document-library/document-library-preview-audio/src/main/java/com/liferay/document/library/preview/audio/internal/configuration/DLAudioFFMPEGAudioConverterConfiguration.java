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

package com.liferay.document.library.preview.audio.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media",
	descriptionArguments = "https://ffmpeg.org"
)
@Meta.OCD(
	description = "dl-audio-ffmpeg-audio-converter-configuration-description",
	id = "com.liferay.document.library.preview.audio.internal.configuration.DLAudioFFMPEGAudioConverterConfiguration",
	localization = "content/Language",
	name = "dl-audio-ffmpeg-audio-converter-configuration-name"
)
public interface DLAudioFFMPEGAudioConverterConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}