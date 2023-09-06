/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = CSPNonceManager.class)
public class CSPNonceManager {

	public String ensureNonce(HttpServletRequest httpServletRequest) {

		// Unwrap request as some wrappers hide attributes from request/session

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		String nonce;

		if (PropsValues.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED) {
			HttpSession httpSession = httpServletRequest.getSession();

			nonce = (String)httpSession.getAttribute(_NONCE_ATTR);

			if (nonce == null) {
				synchronized (httpSession) {
					nonce = (String)httpSession.getAttribute(_NONCE_ATTR);

					if (nonce == null) {
						nonce = _generateNonce();

						httpSession.setAttribute(_NONCE_ATTR, nonce);
					}
				}
			}
		}
		else {
			nonce = (String)httpServletRequest.getAttribute(_NONCE_ATTR);

			if (nonce == null) {
				nonce = _generateNonce();

				httpServletRequest.setAttribute(_NONCE_ATTR, nonce);
			}
		}

		return nonce;
	}

	public String getCSPNonce(HttpServletRequest httpServletRequest) {

		// Unwrap request as some wrappers hide attributes from request/session

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		String nonce;

		if (httpServletRequest == null) {
			nonce = _threadLocal.get();
		}
		else if (PropsValues.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED) {
			HttpSession httpSession = httpServletRequest.getSession();

			nonce = (String)httpSession.getAttribute(_NONCE_ATTR);
		}
		else {
			nonce = (String)httpServletRequest.getAttribute(_NONCE_ATTR);
		}

		if (nonce == null) {
			nonce = StringPool.BLANK;
		}

		return nonce;
	}

	public void removeTLSNonce() {
		_threadLocal.remove();
	}

	public void setTLSNonce(String nonce) {
		_threadLocal.set(nonce);
	}

	private String _generateNonce() {
		SecureRandom secureRandom = new SecureRandom();

		byte[] bytes = new byte[16];

		secureRandom.nextBytes(bytes);

		return Base64.encode(bytes);
	}

	private static final String _NONCE_ATTR =
		CSPNonceManager.class.getName() + ":nonce";

	@Reference
	private Portal _portal;

	private final ThreadLocal<String> _threadLocal = new ThreadLocal<>();

}