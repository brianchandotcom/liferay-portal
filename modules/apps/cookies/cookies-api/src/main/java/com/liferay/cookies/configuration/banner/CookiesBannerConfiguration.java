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

package com.liferay.cookies.configuration.banner;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Olivér Kecskeméty
 */
@ExtendedObjectClassDefinition(
	category = "cookies", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.cookies.configuration.banner.CookiesBannerConfiguration",
	localization = "content/Language", name = "cookie-banner-configuration-name"
)
public interface CookiesBannerConfiguration {

	@Meta.AD(
		deflt = "${language:cookies-banner-content}", name = "content",
		required = false
	)
	public LocalizedValuesMap content();

	@Meta.AD(name = "privacy-policy-link", required = false)
	public String privacyPolicyLink();

	@Meta.AD(
		deflt = "${language:visit-our-privacy-policy}",
		name = "link-display-text", required = false
	)
	public LocalizedValuesMap linkDisplayText();

	@Meta.AD(
		deflt = "true", name = "include-decline-all-button", required = false
	)
	public boolean includeDeclineAllButton();

}