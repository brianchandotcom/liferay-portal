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

package com.liferay.cookies.consent.configuration;

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
	description = "cookie-consent-panel-configuration-description",
	id = "com.liferay.cookies.consent.configuration.CookiesConfiguration",
	localization = "content/Language",
	name = "cookie-consent-panel-configuration-name"
)
public interface CookiesConfiguration {

	@Meta.AD(deflt = "Cookie Configuration", name = "title", required = false)
	public LocalizedValuesMap title();

	@Meta.AD(
		deflt = "When you visit any web site, it may store or retrieve information on your browser, mostly in the form of cookies. This information might be about you, your preferences or your device and is mostly used to make the site work as you expect it to. The information does not usually directly identify you, but it can give you a more personalized web experience.\n\nBecause we respect your right to privacy, you can choose not to allow some types of cookies. Click on the different category headings to find out more and change our default settings. However, blocking some types of cookies may impact your experience of the site and the services we are able to offer.",
		name = "description", required = false
	)
	public LocalizedValuesMap description();

	@Meta.AD(name = "cookie-policy-link", required = false)
	public String cookiePolicyLink();

	@Meta.AD(
		deflt = "Visit our Cookie Policy", name = "link-display-text",
		required = false
	)
	public LocalizedValuesMap linkDisplayText();

	@Meta.AD(
		deflt = "These cookies are essential for you to browse the website and use its features, such as accessing secure areas of the site. Cookies that allow web shops to hold your items in your cart while you are shopping online are an example of strictly necessary cookies. These cookies will generally be first-party session cookies. While it is not required to obtain consent for these cookies, what they do and why they are necessary should be explained to the user.",
		name = "strictly-necessary-cookies-description-field", required = false
	)
	public LocalizedValuesMap strictlyNecessaryCookiesDescription();

	@Meta.AD(
		deflt = "These cookies allow a website to remember choices you have made in the past, like what language you prefer, what region you would like weather reports for, or what your user name and password are so you can automatically log in.",
		name = "functional-cookies-description-field", required = false
	)
	public LocalizedValuesMap functionalCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean functionalCookiesPreChecked();

	@Meta.AD(
		deflt = "These cookies collect information about how you use a website, like which pages you visited and which links you clicked on. None of this information can be used to identify you. It is all aggregated and, therefore, anonymized. Their sole purpose is to improve website functions. This includes cookies from third-party analytics services as long as the cookies are for the exclusive use of the owner of the website visited.",
		name = "performance-cookies-description-field", required = false
	)
	public LocalizedValuesMap performanceCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean performanceCookiesPreChecked();

	@Meta.AD(
		deflt = "These cookies track your online activity to help advertisers deliver more relevant advertising or to limit how many times you see an ad. These cookies can share that information with other organizations or advertisers. These are persistent cookies and almost always of third-party provenance.",
		name = "personalization-cookies-description-field", required = false
	)
	public LocalizedValuesMap personalizationCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean personalizationCookiesPreChecked();

}