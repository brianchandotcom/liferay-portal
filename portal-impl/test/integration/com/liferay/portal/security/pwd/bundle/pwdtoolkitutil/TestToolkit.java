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

package com.liferay.portal.security.pwd.bundle.pwdtoolkitutil;

import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.pwd.Toolkit;
import com.liferay.portal.kernel.util.StackTraceUtil;

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
public class TestToolkit implements Toolkit {

	public static String PASSWORD = "shibboleth";

	@Override
	public String generate(PasswordPolicy passwordPolicy) {
		return PASSWORD;
	}

	@Override
	public void validate(
		long userId, String password1, String password2,
		PasswordPolicy passwordPolicy) {

		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Override
	public void validate(
		String password1, String password2, PasswordPolicy passwordPolicy) {

		_atomicReference.set(StackTraceUtil.getCallerKey());
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}