/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TestrayAttachment {

	public JSONObject getJSONObject();

	public String getKey();

	public String getName();

	public URL getURL();

	public String getValue();

}