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

package com.liferay.saml.runtime.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.saml.persistence.model.SamlSpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public interface SingleLogoutProfile {

	public SamlSpSession getSamlSpSession(HttpServletRequest request);

	public boolean isSingleLogoutSupported(HttpServletRequest request);

	public void logout(
		HttpServletRequest request, HttpServletResponse response);

	public void processIdpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException;

	public void processSingleLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException;

	public void processSpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException;

	public void terminateSpSession(
		HttpServletRequest request, HttpServletResponse response);

	public void terminateSsoSession(
		HttpServletRequest request, HttpServletResponse response);

}