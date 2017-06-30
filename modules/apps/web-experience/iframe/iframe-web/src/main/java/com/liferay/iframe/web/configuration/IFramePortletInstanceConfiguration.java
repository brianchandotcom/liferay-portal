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

package com.liferay.iframe.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "web-experience",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.iframe.web.configuration.IFramePortletInstanceConfiguration",
	localization = "content/Language",
	name = "iframe-portlet-instance-configuration-name"
)
public interface IFramePortletInstanceConfiguration {

	@Meta.AD(name = "source-url", required = false)
	public String src();

	@Meta.AD(name = "relative-to-context-path", required = false)
	public boolean relative();

	@Meta.AD(deflt = "true", name = "dynamic-url", required = false)
	public boolean dynamicUrlEnabled();

	@Meta.AD(deflt = "false", name = "authenticate", required = false)
	public boolean auth();

	@Meta.AD(deflt = "basic", name = "authentication-type", required = false)
	public String authType();

	@Meta.AD(name = "user-name", required = false)
	public String basicUserName();

	@Meta.AD(name = "password", required = false)
	public String basicPassword();

	@Meta.AD(deflt = "post", name = "form-method", required = false)
	public String formMethod();

	@Meta.AD(name = "user-name-field", required = false)
	public String userNameField();

	@Meta.AD(name = "user-name", required = false)
	public String formUserName();

	@Meta.AD(name = "password-field", required = false)
	public String passwordField();

	@Meta.AD(name = "password", required = false)
	public String formPassword();

	@Meta.AD(
		deflt = "var1=hello|var2=world", name = "hidden-variables",
		required = false
	)
	public String[] hiddenVariables();

	@Meta.AD(deflt = "true", name = "resize-automatically", required = false)
	public boolean resizeAutomatically();

	@Meta.AD(deflt = "600", name = "height-maximized", required = false)
	public String heightMaximized();

	@Meta.AD(deflt = "600", name = "height-normal", required = false)
	public String heightNormal();

	@Meta.AD(deflt = "100%", name = "width", required = false)
	public String width();

	@Meta.AD(required = false)
	public String alt();

	@Meta.AD(deflt = "0", required = false)
	public String border();

	@Meta.AD(deflt = "#000000", required = false)
	public String bordercolor();

	@Meta.AD(deflt = "0", required = false)
	public String frameborder();

	@Meta.AD(deflt = "0", required = false)
	public String hspace();

	@Meta.AD(required = false)
	public String longdesc();

	@Meta.AD(deflt = "auto", required = false)
	public String scrolling();

	@Meta.AD(required = false)
	public String title();

	@Meta.AD(deflt = "0", required = false)
	public String vspace();

}