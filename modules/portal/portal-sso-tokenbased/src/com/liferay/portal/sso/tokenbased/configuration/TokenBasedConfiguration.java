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

package com.liferay.portal.sso.tokenbased.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
@Meta.OCD(
	id = "com.liferay.portal.sso.tokenbased", localization = "content.Language"
)
public interface TokenBasedConfiguration {

	@Meta.AD(deflt ="SMIDENTITY,SMSESSION", required = false)
	public String[] authenticationCookies();

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "false", required = false)
	public boolean importFromLDAP();

	@Meta.AD(
		deflt = "cookie", optionValues = {"cookie", "none", "redirect"},
		required = false
	)
	public String logoutProcessorType();

	@Meta.AD(deflt ="", required = false)
	public String logoutRedirectURL();

	@Meta.AD(
		deflt = "requestHeader",
		optionValues = {"cookie", "request", "requestHeader", "session"},
		required = false
	)
	public String tokenLocation();

	@Meta.AD(deflt = "SM_USER", required = false)
	public String userTokenName();

}