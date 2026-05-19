/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.consent.management.platform.integration.internal.consent;

import com.liferay.cookies.consent.CookiesConsentChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Moura
 */
@Component(service = CookiesConsentChecker.class)
public class ConsentManagementPlatformCookiesConsentChecker
	implements CookiesConsentChecker {

	@Override
	public boolean hasConsent(
		int consentType, HttpServletRequest httpServletRequest) {

		if (consentType == CookiesConstants.CONSENT_TYPE_NECESSARY) {
			return true;
		}

		String cookieValue = CookiesManagerUtil.getCookieValue(
			CookiesConstants.NAME_LIFERAY_CONSENT_STATE, httpServletRequest);

		if (Validator.isNull(cookieValue)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					CookiesConstants.NAME_LIFERAY_CONSENT_STATE +
						" cookie is absent");
			}

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				CookiesConstants.NAME_LIFERAY_CONSENT_STATE +
					" cookie value: " + cookieValue);
		}

		String consentTypeName = CookiesConstants.getConsentTypeName(
			consentType);

		if (consentTypeName == null) {
			return true;
		}

		try {
			JSONObject cookieValueJSONObject = _jsonFactory.createJSONObject(
				URLDecoder.decode(cookieValue, StandardCharsets.UTF_8));

			if (!cookieValueJSONObject.has(consentTypeName)) {
				return true;
			}

			return cookieValueJSONObject.getBoolean(consentTypeName);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to parse ",
						CookiesConstants.NAME_LIFERAY_CONSENT_STATE,
						" cookie value: ", exception.getMessage()));
			}

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConsentManagementPlatformCookiesConsentChecker.class);

	@Reference
	private JSONFactory _jsonFactory;

}