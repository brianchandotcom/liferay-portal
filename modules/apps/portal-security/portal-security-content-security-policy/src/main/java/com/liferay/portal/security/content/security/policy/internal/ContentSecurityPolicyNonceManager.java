/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.content.security.policy.internal;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.content.security.policy.internal.configuration.ContentSecurityPolicyConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ContentSecurityPolicyNonceManager.class)
public class ContentSecurityPolicyNonceManager {

	public String ensureNonce(HttpServletRequest httpServletRequest) {
		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		String nonce = (String)httpSession.getAttribute(_NONCE);

		if (nonce != null) {
			return nonce;
		}

		synchronized (httpSession) {
			nonce = (String)httpSession.getAttribute(_NONCE);

			if (nonce == null) {
				nonce = _generateNonce();

				httpSession.setAttribute(_NONCE, nonce);
			}
		}

		return nonce;
	}

	public String getNonce(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return GetterUtil.getString(_threadLocal.get());
		}

		ContentSecurityPolicyConfiguration contentSecurityPolicyConfiguration =
			_getContentSecurityPolicyConfiguration(httpServletRequest);

		if (!contentSecurityPolicyConfiguration.enabled()) {
			return StringPool.BLANK;
		}

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		return GetterUtil.getString(httpSession.getAttribute(_NONCE));
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

	private ContentSecurityPolicyConfiguration
		_getContentSecurityPolicyConfiguration(
			HttpServletRequest httpServletRequest) {

		try {
			long groupId = _portal.getScopeGroupId(httpServletRequest);

			if (groupId > 0) {
				return _configurationProvider.getGroupConfiguration(
					ContentSecurityPolicyConfiguration.class, groupId);
			}

			return _configurationProvider.getCompanyConfiguration(
				ContentSecurityPolicyConfiguration.class,
				_portal.getCompanyId(httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private static final String _NONCE =
		ContentSecurityPolicyNonceManager.class.getName() + "#NONCE";

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	private final ThreadLocal<String> _threadLocal = new ThreadLocal<>();

}