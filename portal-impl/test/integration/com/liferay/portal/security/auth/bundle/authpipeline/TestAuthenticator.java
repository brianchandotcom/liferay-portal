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

package com.liferay.portal.security.auth.bundle.authpipeline;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.Authenticator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {"key=auth.pipeline.pre",
				"service.ranking:Integer=" + Integer.MAX_VALUE
			},
	service = Authenticator.class
)
public class TestAuthenticator implements Authenticator {

	@Override
	public int authenticateByEmailAddress(
		long companyId, String emailAddress, String password,
		Map<String, String[]> headerMap,
		Map<String, String[]> parameterMap) {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return Authenticator.SUCCESS;
	}

	@Override
	public int authenticateByScreenName(
		long companyId, String screenName, String password,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return Authenticator.SUCCESS;
	}

	@Override
	public int authenticateByUserId(
		long companyId, long userId, String password,
		Map<String, String[]> headerMap, Map<String, String[]> parameterMap) {

		_atomicReference.set(StackTraceUtil.getCallerKey());

		return Authenticator.SUCCESS;
	}

	@Reference(target = "(test=AtomicState)")
	protected void getAtomicReference(AtomicReference<String> atomicReference) {
		_atomicReference = atomicReference;
	}

	private AtomicReference<String> _atomicReference;

}