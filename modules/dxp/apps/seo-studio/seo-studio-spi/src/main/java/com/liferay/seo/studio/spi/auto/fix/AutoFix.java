/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.spi.auto.fix;

import jakarta.ws.rs.core.Response;

/**
 * @author David Truong
 */
public interface AutoFix {

	public Response apply(
			String accessToken, String baseURL,
			String cachedSiteExternalReferenceCode, String pagePath,
			String value)
		throws Exception;

	public String getInsightType();

}