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