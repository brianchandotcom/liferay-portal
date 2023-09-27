/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.openapi.contributor;

import com.liferay.portal.vulcan.openapi.OpenAPIContext;

import io.swagger.v3.oas.models.OpenAPI;

/**
 * @author Luis Miguel Barcos
 */
public interface OpenAPIContributor {

	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception;

}