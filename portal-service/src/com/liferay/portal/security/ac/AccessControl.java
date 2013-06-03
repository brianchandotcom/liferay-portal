/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ac;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.AuthVerifierResult;

import java.lang.annotation.Annotation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public interface AccessControl {

	public void initAccessControlContext(
		HttpServletRequest request, HttpServletResponse response,
		Map<String, Object> settings);

	public void initContextUser(long userId) throws AuthException;

	public AuthVerifierResult.State verifyRequest()
		throws PortalException, SystemException;

	public AccessControlled NULL_ACCESS_CONTROLLED = new AccessControlled() {

		@Override
		public Class<? extends Annotation> annotationType() {
			return AccessControlled.class;
		}

		@Override
		public boolean guestAccessEnabled() {
			return false;
		}

		@Override
		public boolean hostAllowedValidationEnabled() {
			return false;
		}

	};

}