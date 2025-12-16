/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.display;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

import java.util.Dictionary;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface ConfigurationScreen {

	public default Dictionary<String, Object> exportProperties(
			Serializable scopePK)
		throws Exception {

		throw new UnsupportedOperationException(
			"Export capability is not implemented for configuration: " +
				getKey());
	}

	public String getCategoryKey();

	public String getKey();

	public String getName(Locale locale);

	public String getScope();

	public default void importProperties(
			Dictionary<String, Object> properties, Serializable scopePK)
		throws Exception {

		throw new UnsupportedOperationException(
			"Import capability is not implemented for configuration: " +
				getKey());
	}

	public default boolean isDeprecated() {
		return false;
	}

	public default boolean isVisible() {
		return true;
	}

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

}