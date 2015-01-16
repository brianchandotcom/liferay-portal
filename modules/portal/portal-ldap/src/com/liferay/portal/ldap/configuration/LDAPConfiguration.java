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

package com.liferay.portal.ldap.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
@Meta.OCD(id = "com.liferay.portal.ldap", localization = "content.Language")
public interface LDAPConfiguration {

	@Meta.AD(
		deflt = "com.sun.jndi.ldap.LdapCtxFactory", id = "ldap.factory.initial")
	public String getLDAPFactoryInitial();

	@Meta.AD(deflt = "1000", id = "ldap.page.size")
	public int getLDAPPageSize();

	@Meta.AD(deflt = "1000", id = "ldap.range.size")
	public int getLDAPRangeSize();

	@Meta.AD(
		deflt = "follow", id = "ldap.referral",
		optionValues = {"follow", "ingore", "throws"}
	)
	public String getLDAPReferral();

	@Meta.AD(deflt = "false", id = "ldap.password.policy.enabled")
	public boolean isLDAPPasswordPolicyEnabled();

}