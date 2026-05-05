/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.contributor;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @author Amos Fong
 */
public interface LayoutSEOCanonicalURLContributor {

	public Map<String, String[]> contributeURLParameters(
		HttpServletRequest httpServletRequest, long plid, String portletId);

}