/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sarai Díaz
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	id = "com.liferay.segments.asah.connector.internal.configuration.SegmentsExperimentConfiguration"
)
public interface SegmentsExperimentConfiguration {

	@Meta.AD(deflt = "BOUNCE_RATE|CLICK_RATE", required = false)
	public String[] goalsEnabled();

}