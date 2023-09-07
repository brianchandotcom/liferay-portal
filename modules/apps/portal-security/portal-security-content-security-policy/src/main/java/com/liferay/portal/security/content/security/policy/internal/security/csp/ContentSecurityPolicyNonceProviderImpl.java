/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal.security.csp;

import com.liferay.portal.kernel.security.csp.ContentSecurityPolicyNonceProvider;
import com.liferay.portal.security.content.security.policy.internal.ContentSecurityPolicyNonceManager;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ContentSecurityPolicyNonceProvider.class)
public class ContentSecurityPolicyNonceProviderImpl
	implements ContentSecurityPolicyNonceProvider {

	@Override
	public String getCSPNonce(HttpServletRequest httpServletRequest) {
		return _contentSecurityPolicyNonceManager.getCSPNonce(
			httpServletRequest);
	}

	@Reference
	private ContentSecurityPolicyNonceManager
		_contentSecurityPolicyNonceManager;

}