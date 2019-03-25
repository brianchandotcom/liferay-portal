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

package com.liferay.configuration.admin.web.internal.display.context;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import javax.portlet.PortletRequest;

/**
 * @author Drew Brokke
 */
public class ConfigurationScopeDisplayContext {

	public ConfigurationScopeDisplayContext(PortletRequest portletRequest) {
		ExtendedObjectClassDefinition.Scope scope =
			ExtendedObjectClassDefinition.Scope.SYSTEM;

		Serializable scopePK = null;

		String portletId = PortalUtil.getPortletId(portletRequest);

		if (portletId.equals(ConfigurationAdminPortletKeys.INSTANCE_SETTINGS)) {
			scope = ExtendedObjectClassDefinition.Scope.COMPANY;

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			scopePK = themeDisplay.getCompanyId();
		}

		_scope = scope;
		_scopePK = scopePK;
	}

	public ExtendedObjectClassDefinition.Scope getScope() {
		return _scope;
	}

	public Serializable getScopePK() {
		return _scopePK;
	}

	private final ExtendedObjectClassDefinition.Scope _scope;
	private final Serializable _scopePK;

}