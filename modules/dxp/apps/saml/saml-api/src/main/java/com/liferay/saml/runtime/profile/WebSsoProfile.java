/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.runtime.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.saml.persistence.model.SamlSpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public interface WebSsoProfile {

	public SamlSpSession getSamlSpSession(HttpServletRequest request);

	public void processAuthnRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException;

	public void processResponse(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException;

	public void sendAuthnRequest(
			HttpServletRequest request, HttpServletResponse response,
			String relayState)
		throws PortalException;

	public void updateSamlSpSession(
		HttpServletRequest request, HttpServletResponse httpServletResponse);

}