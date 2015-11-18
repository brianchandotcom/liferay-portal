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

package com.liferay.service.access.policy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Mika Koivisto
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.service.access.policy.configuration.SAPConfiguration",
	localization = "content/Language"
)
public interface SAPConfiguration {

	@Meta.AD(
		deflt = "System Service Access Policy Applied on Every Request",
		required = false
	)
	public String systemDefaultSAPEntryDescription();

	@Meta.AD(deflt = "SYSTEM_DEFAULT", required = false)
	public String systemDefaultSAPEntryName();

	@Meta.AD(deflt = "", required = false)
	public String systemDefaultSAPEntryServiceSignatures();

	@Meta.AD(
		deflt =
			"System Service Access Policy for Requests Authenticated Using " +
				"User Password",
		required = false
	)
	public String systemUserPasswordSAPEntryDescription();

	@Meta.AD(deflt = "SYSTEM_USER_PASSWORD", required = false)
	public String systemUserPasswordSAPEntryName();

	@Meta.AD(deflt = "*", required = false)
	public String systemUserPasswordSAPEntryServiceSignatures();

	@Meta.AD(deflt = "true", required = false)
	public boolean useSystemSAPEntries();

}