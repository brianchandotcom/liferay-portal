/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.cdi.extension.internal.scope;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.enterprise.inject.Produces;

import javax.inject.Inject;
import javax.inject.Named;

import javax.portlet.PortletRequest;
import javax.portlet.annotations.PortletRequestScoped;

/**
 * @author Neil Griffin
 */
public class LiferayBeanProducer {

	@Named("themeDisplay")
	@PortletRequestScoped
	@Produces
	public ThemeDisplay getThemeDisplay() {
		if (_portletRequest == null) {
			return null;
		}

		return (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Inject
	private PortletRequest _portletRequest;

}