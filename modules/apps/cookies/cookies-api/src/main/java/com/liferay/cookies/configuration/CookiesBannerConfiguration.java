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

package com.liferay.cookies.configuration;

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
	id = "com.liferay.cookies.configuration.CookiesBannerConfiguration",
	localization = "content/Language", name = "cookie-banner-configuration-name"
)
public interface CookiesBannerConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "true", description = "cookie-explicit-consent-mode-help",
		name = "cookie-explicit-consent-mode", required = false
	)
	public boolean explicitConsentMode();

	@Meta.AD(
		deflt = "We use cookies to deliver personalized content, analyze trends, administer the site, track user movements on the site, and collect demographic information about our user base as a whole. Accept all cookies for the best possible experience on our website or manage your preferences.",
		name = "content", required = false
	)
	public LocalizedValuesMap content();

	@Meta.AD(name = "privacy-policy-link", required = false)
	public String privacyPolicyLink();

	@Meta.AD(
		deflt = "Visit our Privacy Policy", name = "link-display-text",
		required = false
	)
	public LocalizedValuesMap linkDisplayText();

	@Meta.AD(
		deflt = "true", name = "include-decline-all-button", required = false
	)
	public boolean includeDeclineAllButton();

}