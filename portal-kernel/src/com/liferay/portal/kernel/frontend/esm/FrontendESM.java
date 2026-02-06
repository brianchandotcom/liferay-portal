/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.frontend.esm;

import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera Avellón
 */
@ProviderType
public interface FrontendESM {

	/**
	 * @param submodule A submodule name without extension (e.g: "react")
	 * @review
	 */
	public String buildURL(
		ThemeDisplay themeDisplay, String contextPath, String submodule);

}