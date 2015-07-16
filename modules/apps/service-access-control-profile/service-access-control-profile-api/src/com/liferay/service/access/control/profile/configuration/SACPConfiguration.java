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

package com.liferay.service.access.control.profile.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Mika Koivisto
 */
@Meta.OCD(
	id = "com.liferay.service.access.control.profile.configuration.SACPConfiguration",
	localization = "content/Language"
)
public interface SACPConfiguration {

	@Meta.AD(
		deflt = "Default Service Access Profile for Applications",
		required = false
	)
	public String defaultApplicationSACPEntryDescription();

	@Meta.AD(deflt = "DEFAULT_APP", required = false)
	public String defaultApplicationSACPEntryName();

	@Meta.AD(deflt = "", required = false)
	public String defaultApplicationSACPEntryServiceSignatures();

	@Meta.AD(
		deflt = "Default Service Access Profile for User", required = false
	)
	public String defaultUserSACPEntryDescription();

	@Meta.AD(deflt = "DEFAULT_USER", required = false)
	public String defaultUserSACPEntryName();

	@Meta.AD(deflt = "*", required = false)
	public String defaultUserSACPEntryServiceSignatures();

	@Meta.AD(deflt = "true", required = false)
	public boolean requireDefaultSACPEntry();

}