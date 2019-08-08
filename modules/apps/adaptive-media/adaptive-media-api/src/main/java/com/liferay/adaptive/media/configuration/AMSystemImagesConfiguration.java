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

package com.liferay.adaptive.media.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author István András Dézsi
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(category = "adaptive-media")
@Meta.OCD(
	id = "com.liferay.adaptive.media.configuration.AMSystemImagesConfiguration",
	localization = "content/Language",
	name = "adaptive-media-system-images-configuration-name"
)
public interface AMSystemImagesConfiguration {

	/**
	 * Sets the Adaptive Media Configuration ID for preview resolution.
	 */
	@Meta.AD(
		deflt = "", description = "am-configuration-id-for-preview",
		name = "preview-am-configuration", required = false
	)
	public String previewlAMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for thumbnail resolution.
	 */
	@Meta.AD(
		deflt = "Thumbnail-300x300",
		description = "am-configuration-id-for-thumbnail",
		name = "thumbnail-am-configuration", required = false
	)
	public String thumbnailAMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for first custom thumbnail resolution.
	 */
	@Meta.AD(
		deflt = "", description = "am-configuration-id-for-custom-thumbnail-1",
		name = "thumbnail-custom-1-am-configuration", required = false
	)
	public String thumbnailCustom1AMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for second custom thumbnail resolution.
	 */
	@Meta.AD(
		deflt = "", description = "am-configuration-id-for-custom-thumbnail-2",
		name = "thumbnail-custom-2-am-configuration", required = false
	)
	public String thumbnailCustom2AMConfiguration();

}