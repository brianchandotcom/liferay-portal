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

package com.liferay.osb.faro.web.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leilany Ulisses
 * @author Marcos Martins
 */
@Component(
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class FaroThemePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (StringUtil.contains(
					httpServletRequest.getRequestURI(), "/c/portal/login") &&
				themeDisplay.isSignedIn()) {

				httpServletResponse.sendRedirect("/");

				return;
			}

			Layout layout = themeDisplay.getLayout();

			if (layout.isTypeControlPanel()) {
				return;
			}

			Theme theme = _themeLocalService.getTheme(
				themeDisplay.getCompanyId(), "osbfarotheme_WAR_osbfarotheme");

			httpServletRequest.setAttribute(WebKeys.THEME, theme);

			themeDisplay.setLookAndFeel(theme, themeDisplay.getColorScheme());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroThemePreAction.class);

	@Reference
	private ThemeLocalService _themeLocalService;

}