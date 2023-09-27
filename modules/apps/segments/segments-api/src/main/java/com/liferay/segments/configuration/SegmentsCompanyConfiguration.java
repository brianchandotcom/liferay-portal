/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 */
@ExtendedObjectClassDefinition(
	category = "segments", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.segments.configuration.SegmentsCompanyConfiguration",
	localization = "content/Language",
	name = "segments-service-company-configuration-name"
)
public interface SegmentsCompanyConfiguration {

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

}