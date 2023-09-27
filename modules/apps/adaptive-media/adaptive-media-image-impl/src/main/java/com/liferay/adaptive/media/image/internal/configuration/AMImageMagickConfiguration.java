/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(category = "adaptive-media")
@Meta.OCD(
	id = "com.liferay.adaptive.media.image.internal.configuration.AMImageMagickConfiguration",
	localization = "content/Language",
	name = "adaptive-media-imagemagick-configuration-name"
)
public interface AMImageMagickConfiguration {

	@Meta.AD(
		description = "adaptive-media-imagemagick-supported-mime-types-key-description",
		name = "adaptive-media-imagemagick-supported-mime-type",
		required = false
	)
	public String[] supportedMimeTypes();

}