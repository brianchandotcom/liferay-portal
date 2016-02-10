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

import java.util.List;

import javax.naming.Binding;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

/**
 * @author Edward C. Han
 */
public interface PortalLDAP {

	public LdapContext getContext(long ldapServerId, long companyId)
		throws Exception;

	public LdapContext getContext(
			long companyId, String providerURL, String principal,
			String credentials)
		throws Exception;

	public Binding getGroup(long ldapServerId, long companyId, String groupName)
		throws Exception;

	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception;

	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName, boolean includeReferenceAttributes)
		throws Exception;

	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			List<SearchResult> searchResults)
		throws Exception;

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
			long companyId, LdapContext ldapContext, String baseDN,
			String filter, Attribute attribute)
		throws Exception;

	public String getNameInNamespace(
			long ldapServerId, long companyId, Binding binding)
		throws Exception;

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
			String fullDistinguishedName)
		throws Exception;

	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			List<SearchResult> searchResults)
		throws Exception;

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
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception;

	public boolean isUserGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception;

	public byte[] searchLDAP(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String filter, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception;

}