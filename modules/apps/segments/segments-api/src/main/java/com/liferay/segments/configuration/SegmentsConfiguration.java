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

package com.liferay.segments.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author David Arques
 */
@ExtendedObjectClassDefinition(category = "segments")
@Meta.OCD(
	id = "com.liferay.segments.configuration.SegmentsConfiguration",
	localization = "content/Language",
	name = "segments-service-configuration-name"
)
public interface SegmentsConfiguration {

	@Meta.AD(
		deflt = "true", description = "segmentation-enabled-description",
		name = "segmentation-enabled-name", required = false
	)
	public boolean segmentationEnabled();

	@Meta.AD(
		deflt = "false", description = "role-segmentation-enabled-description",
		name = "role-segmentation-enabled-name", required = false
	)
	public boolean roleSegmentationEnabled();

	@Meta.AD(
		deflt = "120",
		description = "segments-preview-check-interval-description",
		name = "segments-preview-check-interval-name", required = false
	)
	public int segmentsPreviewCheckInterval();

}