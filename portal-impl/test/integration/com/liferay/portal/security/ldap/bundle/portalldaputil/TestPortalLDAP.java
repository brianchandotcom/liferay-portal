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

package com.liferay.portal.security.ldap.bundle.portalldaputil;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.ldap.PortalLDAP;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.naming.Binding;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestPortalLDAP implements PortalLDAP {

	@Override
	public LdapContext getContext(long ldapServerId, long companyId)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public LdapContext getContext(
			long companyId, String providerURL, String principal,
			String credentials)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public Binding getGroup(long ldapServerId, long companyId, String groupName)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName, boolean includeReferenceAttributes)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getGroups(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String groupFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public String getGroupsDN(long ldapServerId, long companyId)
		throws Exception {

		return ldapServerId + ":" + companyId;
	}

	@Override
	public long getLdapServerId(
			long companyId, String screenName, String emailAddress)
		throws Exception {

		return 1234567890;
	}

	@Override
	public Attribute getMultivaluedAttribute(
			long companyId, LdapContext ldapContext, String baseDN,
			String filter, Attribute attribute)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public String getNameInNamespace(
			long ldapServerId, long companyId, Binding binding)
		throws Exception {

		return ldapServerId + ":" + companyId;
	}

	@Override
	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public Binding getUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress, boolean checkOriginalEmail)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public Attributes getUserAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return null;
	}

	@Override
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getUsers(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String userFilter,
			String[] attributeIds, List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public byte[] getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			byte[] cookie, int maxResults, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Override
	public String getUsersDN(long ldapServerId, long companyId)
		throws Exception {

		return ldapServerId + ":" + companyId;
	}

	@Override
	public boolean hasUser(
			long ldapServerId, long companyId, String screenName,
			String emailAddress)
		throws Exception {

		if (ldapServerId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception {

		if (ldapServerId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUserGroupMember(
			long ldapServerId, long companyId, String groupDN, String userDN)
		throws Exception {

		if (ldapServerId == 1) {
			return true;
		}

		return false;
	}

	@Override
	public byte[] searchLDAP(
			long companyId, LdapContext ldapContext, byte[] cookie,
			int maxResults, String baseDN, String filter, String[] attributeIds,
			List<SearchResult> searchResults)
		throws Exception {

		return new byte[maxResults];
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}