/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Thiago Buarque
 */
public abstract class BaseConfigurationScreen implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "other";
	}

	@Override
	public String getName(Locale locale) {
		return "configuration-name";
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public void render(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {
	}

}