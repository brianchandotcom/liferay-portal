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

package com.liferay.cookies.configuration.consent;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Olivér Kecskeméty
 */
@ExtendedObjectClassDefinition(
	category = "cookies", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.cookies.configuration.consent.CookiesConsentConfiguration",
	localization = "content/Language",
	name = "cookie-consent-panel-configuration-name"
)
public interface CookiesConsentConfiguration {

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookie-configuration}", name = "title",
		required = false
	)
	public LocalizedValuesMap title();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookies-consent-description}", name = "description",
		required = false
	)
	public LocalizedValuesMap description();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(name = "cookie-policy-link", required = false)
	public String cookiePolicyLink();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:visit-our-cookie-policy}",
		name = "link-display-text", required = false
	)
	public LocalizedValuesMap linkDisplayText();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookies-description[CONSENT_TYPE_NECESSARY]}",
		name = "strictly-necessary-cookies-description-field", required = false
	)
	public LocalizedValuesMap strictlyNecessaryCookiesDescription();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookies-description[CONSENT_TYPE_FUNCTIONAL]}",
		name = "functional-cookies-description-field", required = false
	)
	public LocalizedValuesMap functionalCookiesDescription();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(deflt = "false", name = "prechecked", required = false)
	public boolean functionalCookiesPrechecked();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookies-description[CONSENT_TYPE_PERFORMANCE]}",
		name = "performance-cookies-description-field", required = false
	)
	public LocalizedValuesMap performanceCookiesDescription();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(deflt = "false", name = "prechecked", required = false)
	public boolean performanceCookiesPrechecked();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:cookies-description[CONSENT_TYPE_PERSONALIZATION]}",
		name = "personalization-cookies-description-field", required = false
	)
	public LocalizedValuesMap personalizationCookiesDescription();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(deflt = "false", name = "prechecked", required = false)
	public boolean personalizationCookiesPrechecked();

}