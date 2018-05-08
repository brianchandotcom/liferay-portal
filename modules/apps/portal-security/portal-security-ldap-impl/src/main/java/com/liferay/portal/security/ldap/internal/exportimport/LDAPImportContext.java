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

package com.liferay.portal.security.ldap.internal.exportimport;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.ldap.LdapContext;

/**
 * @author Edward C. Han
 */
public class LDAPImportContext {

	public LDAPImportContext(
		long companyId, Properties contactExpandoMappings,
		Properties contactMappings, Properties groupMappings,
		LdapContext ldapContext, long ldapServerId,
		Set<String> ldapUserIgnoreAttributes, Properties userExpandoMappings,
		Properties userMappings) {

		_companyId = companyId;
		_contactExpandoMappings = contactExpandoMappings;
		_contactMappings = contactMappings;
		_groupMappings = groupMappings;
		_ldapContext = ldapContext;
		_ldapServerId = ldapServerId;
		_ldapUserIgnoreAttributes = ldapUserIgnoreAttributes;
		_userExpandoMappings = userExpandoMappings;
		_userMappings = userMappings;
	}

	public void addImportedUserId(String fullUserDN, long userId) {
		_importedLdapUsers.put(fullUserDN, userId);
	}

	public boolean containsImportedUser(String fullUserDN) {
		return _importedLdapUsers.containsKey(fullUserDN);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Properties getContactExpandoMappings() {
		return _contactExpandoMappings;
	}

	public Properties getContactMappings() {
		return _contactMappings;
	}

	public Properties getGroupMappings() {
		return _groupMappings;
	}

	public Long getImportedUserId(String fullUserDN) {
		return _importedLdapUsers.get(fullUserDN);
	}

	public LdapContext getLdapContext() {
		return _ldapContext;
	}

	public long getLdapServerId() {
		return _ldapServerId;
	}

	public Set<String> getLdapUserIgnoreAttributes() {
		return _ldapUserIgnoreAttributes;
	}

	public Properties getUserExpandoMappings() {
		return _userExpandoMappings;
	}

	public Properties getUserMappings() {
		return _userMappings;
	}

	private final long _companyId;
	private final Properties _contactExpandoMappings;
	private final Properties _contactMappings;
	private final Properties _groupMappings;
	private final Map<String, Long> _importedLdapUsers = new HashMap<>();
	private final LdapContext _ldapContext;
	private final long _ldapServerId;
	private final Set<String> _ldapUserIgnoreAttributes;
	private final Properties _userExpandoMappings;
	private final Properties _userMappings;

}