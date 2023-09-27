/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.thumbnails.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author István András Dézsi
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(category = "adaptive-media")
@Meta.OCD(
	id = "com.liferay.adaptive.media.document.library.thumbnails.internal.configuration.AMSystemImagesConfiguration",
	localization = "content/Language",
	name = "adaptive-media-system-images-configuration-name"
)
public interface AMSystemImagesConfiguration {

	/**
	 * Sets the Adaptive Media Configuration ID for preview resolution.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = StringPool.BLANK,
		description = "preview-am-configuration-description",
		name = "preview-am-configuration", required = false
	)
	public String previewAMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for thumbnail resolution.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = StringPool.BLANK,
		description = "thumbnail-am-configuration-description",
		name = "thumbnail-am-configuration", required = false
	)
	public String thumbnailAMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for first custom thumbnail
	 * resolution.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = StringPool.BLANK,
		description = "thumbnail-custom-1-am-configuration-description",
		name = "thumbnail-custom-1-am-configuration", required = false
	)
	public String thumbnailCustom1AMConfiguration();

	/**
	 * Sets the Adaptive Media Configuration ID for second custom thumbnail
	 * resolution.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = StringPool.BLANK,
		description = "thumbnail-custom-2-am-configuration-description",
		name = "thumbnail-custom-2-am-configuration", required = false
	)
	public String thumbnailCustom2AMConfiguration();

}