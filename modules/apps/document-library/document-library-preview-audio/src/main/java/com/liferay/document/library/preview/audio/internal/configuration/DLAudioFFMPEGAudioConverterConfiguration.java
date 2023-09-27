/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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