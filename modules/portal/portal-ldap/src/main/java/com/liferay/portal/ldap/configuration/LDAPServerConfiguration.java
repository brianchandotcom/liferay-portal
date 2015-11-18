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

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.ldap.configuration.LDAPServerConfiguration",
	localization = "content/Language"
)
public interface LDAPServerConfiguration {

	@Meta.AD(deflt = "(mail=@email_address@)", required = false)
	public String authSearchFilter();

	@Meta.AD(deflt = "dc=example,dc=com", required = false)
	public String baseDN();

	@Meta.AD(deflt = "ldap://localhost:10389", required = false)
	public String baseProviderURL();

	@Meta.AD(deflt = "0", required = false)
	public long companyId();

	@Meta.AD(deflt = "", required = false)
	public String[] contactCustomMappings();

	@Meta.AD(
		deflt = "birthday=|facebookSn=|jabberSn=|jobTitle=|skypeSn=|smsSn=|twitterSn=",
		required = false
	)
	public String[] contactMappings();

	@Meta.AD(deflt = "top|groupOfUniqueNames", required = false)
	public String[] groupDefaultObjectClasses();

	@Meta.AD(
		deflt = "description=description|groupName=cn|user=uniqueMember",
		required = false
	)
	public String[] groupMappings();

	@Meta.AD(deflt = "ou=groups,dc=example,dc=com", required = false)
	public String groupsDN();

	@Meta.AD(deflt = "(objectClass=groupOfUniqueNames)", required = false)
	public String groupSearchFilter();

	@Meta.AD(deflt = "0", required = false)
	public long ldapServerId();

	@Meta.AD(deflt = "secret", required = false)
	public String securityCredential();

	@Meta.AD(deflt = "uid=admin,ou=system", required = false)
	public String securityPrincipal();

	@Meta.AD(deflt = "", required = false)
	public String serverName();

	@Meta.AD(deflt = "", required = false)
	public String[] userCustomMappings();

	@Meta.AD(
		deflt = "top|person|inetOrgPerson|organizationalPerson",
		required = false
	)
	public String[] userDefaultObjectClasses();

	@Meta.AD(deflt = "", required = false)
	public String[] userIgnoreAttributes();

	@Meta.AD(
		deflt = "emailAddress=mail|firstName=givenName|group=groupMembership|jobTitle=title|lastName=sn|password=userPassword|screenName=cn|uuid=uuid",
		required = false
	)
	public String[] userMappings();

	@Meta.AD(deflt = "users,dc=example,dc=com", required = false)
	public String usersDN();

	@Meta.AD(deflt = "(objectClass=inetOrgPerson)", required = false)
	public String userSearchFilter();

}