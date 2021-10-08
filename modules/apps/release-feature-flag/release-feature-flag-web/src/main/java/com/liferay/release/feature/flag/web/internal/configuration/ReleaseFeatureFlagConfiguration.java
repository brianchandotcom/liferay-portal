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

package com.liferay.release.feature.flag.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Alejandro Tardín
 */
@Meta.OCD(
	id = "com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration",
	localization = "content/Language",
	name = "release-feature-flag-configuration-name"
)
public interface ReleaseFeatureFlagConfiguration {

	@Meta.AD(
		deflt = "", name = "disabled-release-feature-flag", required = false
	)
	public String[] disabledReleaseFeatureFlags();

}