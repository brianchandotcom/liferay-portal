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

package com.liferay.product.navigation.personal.menu;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.personal.menu.util.PersonalApplicationURLUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides a skeletal implementation of the {@link PersonalMenuEntry} to
 * minimize the effort required to implement this interface.
 *
 * To implement a user personal menu entry, this class should be extended and
 * {@link #getPortletId()} and {@link#setPortlet(Portlet)} should be overridden.
 *
 * @author Pei-Jung Lan
 * @review
 */
public abstract class BasePersonalMenuEntry implements PersonalMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				getPortletId());
	}

	@Override
	public String getPortletURL(HttpServletRequest request)
		throws PortalException {

		if (Validator.isNull(getPortletId())) {
			return null;
		}

		return PersonalApplicationURLUtil.getPersonalApplicationURL(
			request, getPortletId());
	}

	@Override
	public boolean isActive(PortletRequest portletRequest, String portletId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String layoutFriendlyURL = layout.getFriendlyURL();

		if ((!layout.isTypeControlPanel() && !layout.isSystem()) ||
			!layoutFriendlyURL.equals(
				PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_FRIENDLY_URL))) {

			return false;
		}

		if (portletId.equals(getPortletId())) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the portlet's ID associated with the user personal menu entry.
	 *
	 * @return the portlet's ID associated with the user personal menu entry
	 * @review
	 */
	protected abstract String getPortletId();

	protected ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

}