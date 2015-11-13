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

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Juergen Kappler
 */
@ConfigurationAdmin(category = "web-experience-management")
@Meta.OCD(
	id = "com.liferay.iframe.web.configuration.IFramePortletInstanceConfiguration"
)
public interface IFramePortletInstanceConfiguration {

	@Meta.AD(required = false)
	public String alt();

	@Meta.AD(deflt = "false", required = false)
	public boolean auth();

	@Meta.AD(required = false)
	public String authType();

	@Meta.AD(required = false)
	public String basicPassword();

	@Meta.AD(required = false)
	public String basicUserName();

	@Meta.AD(deflt = "0", required = false)
	public String border();

	@Meta.AD(deflt = "#000000", required = false)
	public String bordercolor();

	@Meta.AD(required = false)
	public String formMethod();

	@Meta.AD(required = false)
	public String formPassword();

	@Meta.AD(required = false)
	public String formUserName();

	@Meta.AD(deflt = "0", required = false)
	public String frameborder();

	@Meta.AD(deflt = "600", required = false)
	public String heightMaximized();

	@Meta.AD(deflt = "600", required = false)
	public String heightNormal();

	@Meta.AD(required = false)
	public String hiddenVariables();

	@Meta.AD(deflt = "0", required = false)
	public String hspace();

	@Meta.AD(required = false)
	public String longdesc();

	@Meta.AD(required = false)
	public String password();

	@Meta.AD(required = false)
	public String passwordField();

	@Meta.AD(required = false)
	public boolean relative();

	@Meta.AD(deflt = "true", required = false)
	public boolean resizeAutomatically();

	@Meta.AD(deflt = "auto", required = false)
	public String scrolling();

	@Meta.AD(required = false)
	public String src();

	@Meta.AD(required = false)
	public String title();

	@Meta.AD(required = false)
	public String userName();

	@Meta.AD(required = false)
	public String userNameField();

	@Meta.AD(deflt = "0", required = false)
	public String vspace();

	@Meta.AD(deflt = "100%", required = false)
	public String width();

}