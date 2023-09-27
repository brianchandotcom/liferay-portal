/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public interface JSONWebServiceClientFactory {

	public JSONWebServiceClient getInstance(
			Map<String, Object> properties, boolean oAuthEnabled)
		throws Exception;

}