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
	description = "cookie-consent-panel-configuration-description",
	id = "com.liferay.cookies.configuration.CookiesConsentConfiguration",
	localization = "content/Language",
	name = "cookie-consent-panel-configuration-name"
)
public interface CookiesConsentConfiguration {

	@Meta.AD(deflt = "Cookie Configuration", name = "title", required = false)
	public LocalizedValuesMap title();

	@Meta.AD(
		deflt = "When you visit any web site, it may store or retrieve information on your browser, mostly in the form of cookies. This information might be about you, your preferences, or your device and is mostly used to make the site work as you expect it to. The information does not usually directly identify you, but it can give you a more personalized web experience. You can choose not to allow some types of cookies. Click on the different category headings to find out more and change our default settings. However, blocking some types of cookies may impact your experience of the site and the services we are able to offer.",
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
		deflt = "These cookies are necessary for the website to function and cannot be switched off in our systems. They are usually only set in response to actions made by you which amount to a request for services, such as setting your privacy preferences, logging in or filling in forms. You can set your browser to block or alert you about these cookies, but some parts of the site will then not work. These cookies do not store any personally identifiable information.",
		name = "strictly-necessary-cookies-description-field", required = false
	)
	public LocalizedValuesMap strictlyNecessaryCookiesDescription();

	@Meta.AD(
		deflt = "These cookies enable the website to provide enhanced functionality and personalisation. They may be set by us or by third party providers whose services we have added to our pages. If you do not allow these cookies, then some or all of these services may not function properly.",
		name = "functional-cookies-description-field", required = false
	)
	public LocalizedValuesMap functionalCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean functionalCookiesPreChecked();

	@Meta.AD(
		deflt = "These cookies allow us to count visits and traffic sources so we can measure and improve the performance of our site. They help us know which pages are the most and least popular and see how visitors move around the site. All information these cookies collect is aggregated and therefore anonymous. If you do not allow these cookies, we will not know when you have visited our site.",
		name = "performance-cookies-description-field", required = false
	)
	public LocalizedValuesMap performanceCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean performanceCookiesPreChecked();

	@Meta.AD(
		deflt = "These cookies may be set through our site by our advertising partners. They may be used by those companies to build a profile of your interests and show you relevant adverts on other sites. They do not store directly personal information, but are based on uniquely identifying your browser and Internet device. If you do not allow these cookies, you will experience less targeted advertising.",
		name = "personalization-cookies-description-field", required = false
	)
	public LocalizedValuesMap personalizationCookiesDescription();

	@Meta.AD(deflt = "false", name = "pre-checked", required = false)
	public boolean personalizationCookiesPreChecked();

}