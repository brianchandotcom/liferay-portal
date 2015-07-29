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

/**
 * @author Mika Koivisto
 */
@Meta.OCD(
	id = "com.liferay.service.access.policy.configuration.SAPConfiguration",
	localization = "content/Language"
)
public interface SAPConfiguration {

	@Meta.AD(
		deflt = "Default Service Access Policy for Applications",
		required = false
	)
	public String defaultApplicationSAPEntryDescription();

	@Meta.AD(deflt = "DEFAULT_APP", required = false)
	public String defaultApplicationSAPEntryName();

	@Meta.AD(deflt = "", required = false)
	public String defaultApplicationSAPEntryServiceSignatures();

	@Meta.AD(deflt = "Default Service Access Policy for User", required = false)
	public String defaultUserSAPEntryDescription();

	@Meta.AD(deflt = "DEFAULT_USER", required = false)
	public String defaultUserSAPEntryName();

	@Meta.AD(deflt = "*", required = false)
	public String defaultUserSAPEntryServiceSignatures();

	@Meta.AD(deflt = "true", required = false)
	public boolean requireDefaultSAPEntry();

}