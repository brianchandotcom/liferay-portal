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

package com.liferay.portal.security.ldap;

import com.liferay.portal.security.ldap.validator.LDAPFilter;

import java.util.List;

import javax.naming.Binding;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Edward C. Han
 */
@ProviderType
public interface PortalLDAP {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             com.liferay.portal.security.ldap.util.LDAPUtil#asLdapName(
	 *             String)} for RDN escape and {@link
	 *             LDAPFilter#rfc2254Escape(String)} to escape attribute value
	 *             inside a filter
	 */
	@Deprecated
	public String encodeFilterAttribute(String attribute, boolean rdnEscape);

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getSafeLDAPContext(long, long)}
	 */
	@Deprecated
	public LdapContext getContext(long ldapServerId, long companyId)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getSafeLDAPContext(long, String, String, String)}
	 */
	@Deprecated
	public LdapContext getContext(
			long companyId, String providerURL, String principal,
			String credentials)
		throws Exception;

	public Binding getGroup(long ldapServerId, long companyId, String groupName)
		throws Exception;

	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			LdapName userGroupDNLdapName)
		throws Exception;

	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			LdapName userGroupDNLdapName, boolean includeReferenceAttributes)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getGroupAttributes(long, long, LdapContext, LdapName)}
	 */
	@Deprecated
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getGroupAttributes(long, long, LdapContext, LdapName, boolean)}
	 */
	@Deprecated
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName, boolean includeReferenceAttributes)
		throws Exception;

	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, LdapName baseDNLdapName, LDAPFilter groupFilter,
			List<SearchResult> searchResults)
		throws Exception;

	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, LdapName baseDNLdapName, LDAPFilter groupFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getGroups(long, LdapContext, byte[], int, LdapName, LDAPFilter, List)}
	 */
	@Deprecated
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			List<SearchResult> searchResults)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getGroups(long, LdapContext, byte[], int, LdapName, LDAPFilter, String[], List)}
	 */
	@Deprecated
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception;

	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception;

	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception;

	public String getGroupsDN(long ldapServerId, long companyId)
		throws Exception;

	public long getLdapServerId(
			long companyId, String screenName, String emailAddress)
		throws Exception;

	public Attribute getMultivaluedAttribute(
			long companyId, LdapContext ldapContext, LdapName baseDNLdapName,
			LDAPFilter ldapFilter, Attribute attribute)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getMultivaluedAttribute(long, LdapContext, LdapName, LDAPFilter, Attribute)}
	 */
	@Deprecated
	public Attribute getMultivaluedAttribute(
			long companyId, LdapContext ldapContext, String baseDN,
			String filter, Attribute attribute)
		throws Exception;

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public String getNameInNamespace(
			long ldapServerId, long companyId, Binding binding)
		throws Exception;

	public SafeLdapContext getSafeLDAPContext(
		long ldapServerId, long companyId);

	public SafeLdapContext getSafeLDAPContext(
		long companyId, String providerURL, String principal,
		String credentials);

	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception;

	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress, boolean checkOriginalEmail)
		throws Exception;

	public Attributes getUserAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			LdapName fullDNLdapName)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getUserAttributes(long, long, LdapContext, LdapName)}
	 */
	@Deprecated
	public Attributes getUserAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception;

	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, LdapName baseDNLdapName, LDAPFilter userFilter,
			List<SearchResult> searchResults)
		throws Exception;

	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, LdapName baseDNLdapName, LDAPFilter userFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getUsers(long, LdapContext, byte[], int, LdapName, LDAPFilter, List)}
	 */
	@Deprecated
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			List<SearchResult> searchResults)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #getUsers(long, LdapContext, byte[], int, LdapName, LDAPFilter, String[], List)}
	 */
	@Deprecated
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception;

	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception;

	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception;

	public String getUsersDN(long ldapServerId, long companyId)
		throws Exception;

	public boolean hasUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception;

	public boolean isGroupMember(
			long ldapServerId, long companyId, LdapName groupDNLdapName,
			LdapName userDNLdapName)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #isGroupMember(long, long, LdapName, LdapName)}
	 */
	@Deprecated
	public boolean isGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception;

	public boolean isUserGroupMember(
			long ldapServerId, long companyId, LdapName groupDNLdapName,
			LdapName userDNLdapName)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #isUserGroupMember(long, long, LdapName, LdapName)}
	 */
	@Deprecated
	public boolean isUserGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception;

	public byte[] searchLDAP(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, LdapName baseDNLdapName, LDAPFilter filter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception;

	/**
	 * @deprecated As of Mueller (7.2.x), please use {@link
	 *             #searchLDAP(long, LdapContext, byte[], int, LdapName, LDAPFilter, String[], List)}
	 */
	@Deprecated
	public byte[] searchLDAP(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String filter, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception;

}