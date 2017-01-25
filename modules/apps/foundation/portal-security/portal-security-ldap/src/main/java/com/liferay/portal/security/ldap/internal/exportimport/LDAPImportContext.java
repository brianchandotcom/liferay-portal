package com.liferay.portal.security.ldap.internal.exportimport;

import com.liferay.portal.kernel.model.User;

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

		_importedLdapUsers = new HashMap<>();
	}

	public void addImportedUser(String fullUserDN, User user) {
		_importedLdapUsers.put(fullUserDN, user);
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

	public boolean isImportedUser(String fullUserDN) {
		return _importedLdapUsers.containsKey(fullUserDN);
	}

	private final long _companyId;
	private final Properties _contactExpandoMappings;
	private final Properties _contactMappings;
	private final Properties _groupMappings;
	private final Map<String, User> _importedLdapUsers;
	private final LdapContext _ldapContext;
	private final long _ldapServerId;
	private final Set<String> _ldapUserIgnoreAttributes;
	private final Properties _userExpandoMappings;
	private final Properties _userMappings;

}