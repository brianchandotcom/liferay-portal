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

package com.liferay.portal.servlet.filters.autologin.bundle.autologinfilter;

import com.liferay.portal.kernel.security.auto.login.AutoLogin;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestAutoLogin implements AutoLogin {

	@Override
	public String[] handleException(
		HttpServletRequest request, HttpServletResponse response, Exception e) {

		return null;
	}

	@Override
	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		_atomicBoolean.set(Boolean.TRUE);

		return null;
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicBoolean(AtomicBoolean atomicBoolean) {
		_atomicBoolean = atomicBoolean;
	}

	private AtomicBoolean _atomicBoolean;

}