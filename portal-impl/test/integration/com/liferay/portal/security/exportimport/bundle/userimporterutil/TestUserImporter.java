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

package com.liferay.portal.security.exportimport.bundle.userimporterutil;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.exportimport.UserImporter;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=" + Integer.MAX_VALUE}
)
public class TestUserImporter implements UserImporter {

	@Override
	public long getLastImportTime() {
		return 0;
	}

	@Override
	public User importUser(
		long ldapServerId, long companyId, String emailAddress,
		String screenName) {

		return getUser(companyId, screenName);
	}

	@Override
	public User importUser(
		long companyId, String emailAddress, String screenName) {

		return getUser(companyId, screenName);
	}

	@Override
	public User importUserByScreenName(long companyId, String screenName) {
		return getUser(companyId, screenName);
	}

	@Override
	public void importUsers() {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public void importUsers(long companyId) throws Exception {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public void importUsers(long ldapServerId, long companyId) {
		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	protected User getUser(long companyId, String screenName) {
		User user = new UserImpl();

		user.setCompanyId(companyId);
		user.setScreenName(screenName);

		return user;
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}