/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Sarai Díaz
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	description = "layout-reports-google-pagespeed-configuration-description",
	id = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration",
	localization = "content/Language",
	name = "layout-reports-google-pagespeed-configuration-name"
)
public interface LayoutReportsGooglePageSpeedConfiguration {

	@Meta.AD(deflt = "true", name = "enable-google-pagespeed", required = false)
	public boolean enabled();

}