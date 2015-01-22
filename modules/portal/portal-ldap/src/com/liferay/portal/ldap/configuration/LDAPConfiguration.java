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
@Meta.OCD(
	id = "com.liferay.portal.ldap",
	localization = "content.Language-LDAPConfiguration"
)
public interface LDAPConfiguration {

	@Meta.AD(
		deflt = "com.sun.jndi.ldap.LdapCtxFactory", id = "ldap.factory.initial",
		required = false)
	public String ldapFactoryInitial();

	@Meta.AD(deflt = "1000", id = "ldap.page.size", required = false)
	public int ldapPageSize();

	@Meta.AD(
		deflt = "false", id = "ldap.password.policy.enabled", required = false)
	public boolean ldapPasswordPolicyEnabled();

	@Meta.AD(deflt = "1000", id = "ldap.range.size", required = false)
	public int ldapRangeSize();

	@Meta.AD(
		deflt = "follow", id = "ldap.referral",
		optionValues = {"follow", "ingore", "throws"}
	)
	public String ldapReferral();

}