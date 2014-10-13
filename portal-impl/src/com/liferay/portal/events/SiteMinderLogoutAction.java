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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 */
public class SiteMinderLogoutAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.SITEMINDER_AUTH_ENABLED,
					PropsValues.SITEMINDER_AUTH_ENABLED)) {

				return;
			}

			String domain = CookieKeys.getDomain(request);

			Cookie smSessionCookie = new Cookie(_SMSESSION, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				smSessionCookie.setDomain(domain);
			}

			smSessionCookie.setMaxAge(0);
			smSessionCookie.setPath(StringPool.SLASH);

			Cookie smIdentityCookie = new Cookie(_SMIDENTITY, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				smIdentityCookie.setDomain(domain);
			}

			smIdentityCookie.setMaxAge(0);
			smIdentityCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, smSessionCookie);
			CookieKeys.addCookie(request, response, smIdentityCookie);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final String _SMIDENTITY = "SMIDENTITY";

	private static final String _SMSESSION = "SMSESSION";

	private static final Log _log = LogFactoryUtil.getLog(
		SiteMinderLogoutAction.class);

}