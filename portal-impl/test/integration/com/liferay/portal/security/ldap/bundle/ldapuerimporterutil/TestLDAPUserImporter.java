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

package com.liferay.portal.security.ldap.bundle.ldapuerimporterutil;

import com.liferay.portal.kernel.security.ldap.LDAPUserImporter;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;

import java.util.concurrent.atomic.AtomicReference;

import javax.naming.directory.Attributes;
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
public class TestLDAPUserImporter implements LDAPUserImporter {

	@Override
	public long getLastImportTime() throws Exception {
		return 123456789;
	}

	@Override
	public User importUser(
		long ldapServerId, long companyId, LdapContext ldapContext,
		Attributes attributes, String password) {

		User user = new UserImpl();

		user.setCompanyId(companyId);

		return user;
	}

	@Override
	public User importUser(
		long ldapServerId, long companyId, String emailAddress,
		String screenName) {

		User user = new UserImpl();

		user.setCompanyId(companyId);

		return user;
	}

	@Override
	public User importUser(
		long companyId, String emailAddress, String screenName) {

		User user = new UserImpl();

		user.setCompanyId(companyId);

		return user;
	}

	@Override
	public User importUserByScreenName(long companyId, String screenName) {
		User user = new UserImpl();

		user.setCompanyId(companyId);

		return user;
	}

	@Override
	public void importUsers() {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public void importUsers(long companyId) {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public void importUsers(long ldapServerId, long companyId) {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}