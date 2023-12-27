/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.site;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.ResourceRequest;

/**
 * @author Gustavo Lima
 */
@ProviderType
public interface SitesProvider {

	public JSONObject getSiteJSONObject(ResourceRequest resourceRequest)
		throws Exception;

	public JSONObject getSitesJSONObject(ResourceRequest resourceRequest)
		throws Exception;

}