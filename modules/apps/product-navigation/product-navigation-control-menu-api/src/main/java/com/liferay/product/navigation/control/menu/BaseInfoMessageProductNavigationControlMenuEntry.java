/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.menu;

import com.liferay.frontend.taglib.servlet.taglib.FeatureIndicatorTag;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PageContextFactoryUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Roberto Díaz
 */
public abstract class BaseInfoMessageProductNavigationControlMenuEntry
	extends BaseProductNavigationControlMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getURL(HttpServletRequest httpServletRequest) {
		return null;
	}

	@Override
	public boolean includeIcon(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		FeatureIndicatorTag featureIndicatorTag = new FeatureIndicatorTag();

		featureIndicatorTag.setDark(true);
		featureIndicatorTag.setInteractive(true);

		featureIndicatorTag.setPageContext(
			PageContextFactoryUtil.create(
				httpServletRequest, httpServletResponse));

		featureIndicatorTag.setType(getType());

		try {
			featureIndicatorTag.runTag();
		}
		catch (JspException jspException) {
			throw new IOException(jspException);
		}

		return true;
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return false;
		}

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (portletDisplay == null) {
			return false;
		}

		if (Validator.isNull(portletDisplay.getPortletName())) {
			return Objects.equals(getPortletName(), themeDisplay.getPpid());
		}

		return Objects.equals(
			getPortletName(), portletDisplay.getPortletName());
	}

	protected abstract String getPortletName();

	protected abstract String getType();

}