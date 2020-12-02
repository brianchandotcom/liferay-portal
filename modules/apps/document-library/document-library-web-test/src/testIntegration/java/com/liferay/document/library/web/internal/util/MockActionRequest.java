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

package com.liferay.document.library.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
public class MockActionRequest extends MockLiferayPortletActionRequest {

	public MockActionRequest(
		Company company, Group group, Map<String, String[]> parameters) {

		_company = company;
		_group = group;
		_parameters = Collections.unmodifiableMap(parameters);
	}

	@Override
	public Object getAttribute(String name) {
		if (Objects.equals(name, WebKeys.THEME_DISPLAY)) {
			try {
				return _getThemeDisplay();
			}
			catch (PortalException portalException) {
				throw new AssertionError(portalException);
			}
		}

		return super.getAttribute(name);
	}

	@Override
	public String getParameter(String name) {
		String[] values = _parameters.get(name);

		if (values == null) {
			return StringPool.BLANK;
		}

		return values[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameters;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private final Company _company;
	private final Group _group;
	private final Map<String, String[]> _parameters;

}