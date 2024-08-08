/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.events;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfigurationUtil;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(property = "key=logout.events.pre", service = LifecycleAction.class)
public class LogoutPreAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ClickToChatConfiguration clickToChatConfiguration =
			ClickToChatConfigurationUtil.getClickToChatConfiguration(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

		if ((clickToChatConfiguration == null) ||
			!clickToChatConfiguration.enabled() ||
			!StringUtil.equals(
				clickToChatConfiguration.chatProviderId(), _INTERCOM)) {

			return;
		}

		try {
			String domain = CookiesManagerUtil.getDomain(httpServletRequest);

			Cookie[] cookies = httpServletRequest.getCookies();

			for (Cookie cookie : cookies) {
				String name = cookie.getName();

				if (name.startsWith(_INTERCOM_COOKIE_PREFIX)) {
					CookiesManagerUtil.deleteCookies(
						domain, httpServletRequest, httpServletResponse, name);
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final String _INTERCOM = "intercom";

	private static final String _INTERCOM_COOKIE_PREFIX = "intercom-";

	private static final Log _log = LogFactoryUtil.getLog(
		LogoutPreAction.class);

}