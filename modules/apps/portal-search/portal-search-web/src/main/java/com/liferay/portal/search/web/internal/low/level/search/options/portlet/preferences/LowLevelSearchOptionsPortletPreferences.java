/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences;

import com.liferay.portal.kernel.json.JSONArray;

/**
 * @author Wade Cao
 */
public interface LowLevelSearchOptionsPortletPreferences {

	public static final String PREFERENCE_ATTRIBUTES = "attributes";

	public static final String PREFERENCE_KEY_CONNECTION_ID = "connectionId";

	public static final String PREFERENCE_KEY_CONTRIBUTORS_TO_EXCLUDE =
		"contributorsToExclude";

	public static final String PREFERENCE_KEY_CONTRIBUTORS_TO_INCLUDE =
		"contributorsToInclude";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FIELDS_TO_RETURN =
		"fieldsToReturn";

	public static final String PREFERENCE_KEY_INDEXES = "indexes";

	public JSONArray getAttributesJSONArray();

	public String getAttributesString();

	public String getConnectionId();

	public String getContributorsToExclude();

	public String getContributorsToInclude();

	public String getFederatedSearchKey();

	public String getFieldsToReturn();

	public String getIndexes();

}