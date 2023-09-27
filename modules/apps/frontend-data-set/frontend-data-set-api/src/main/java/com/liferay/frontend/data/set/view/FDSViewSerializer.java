/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view;

import com.liferay.portal.kernel.json.JSONArray;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public interface FDSViewSerializer {

	public JSONArray serialize(String fdsName, Locale locale);

}