/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.manager.http;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Guilherme Camacho
 */
public interface ObjectEntryManagerHttp {

	public JSONObject delete(long companyId, long groupId, String location);

	public JSONObject get(long companyId, long groupId, String location);

	public JSONObject getAccessToken(long companyId, long groupId);

	public String getBaseURL(long companyId, long groupId);

	public JSONObject patch(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject);

	public JSONObject post(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject);

	public JSONObject put(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject);

}