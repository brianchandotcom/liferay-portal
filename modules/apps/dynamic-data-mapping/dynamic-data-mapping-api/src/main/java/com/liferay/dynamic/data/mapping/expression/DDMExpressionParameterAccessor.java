/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.portal.kernel.json.JSONArray;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMExpressionParameterAccessor {

	public long getCompanyId();

	public String getGooglePlacesAPIKey();

	public long getGroupId();

	public Locale getLocale();

	public JSONArray getObjectFieldsJSONArray();

	public default Map<String, Object> getObjectFieldsOldValues() {
		return Collections.emptyMap();
	}

	public String getTimeZoneId();

	public long getUserId();

}