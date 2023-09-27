/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.feature.flag.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(category = "release-feature-flags")
@Meta.OCD(
	id = "com.liferay.release.feature.flag.web.internal.configuration.ReleaseFeatureFlagConfiguration",
	localization = "content/Language",
	name = "release-feature-flag-configuration-name"
)
public interface ReleaseFeatureFlagConfiguration {

	@Meta.AD(deflt = "", name = "disabled-features", required = false)
	public String[] disabledReleaseFeatureFlags();

}