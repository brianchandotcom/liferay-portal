/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet;

import com.liferay.portal.kernel.json.JSONArray;

/**
 * @author Petteri Karttunen
 */
public interface DateFacetPortletPreferences {

	public static final String PREFERENCE_KEY_AGGREGATION_FIELD =
		"aggregationField";

	public static final String PREFERENCE_KEY_CUSTOM_HEADING = "customHeading";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FREQUENCIES_VISIBLE =
		"frequenciesVisible";

	public static final String PREFERENCE_KEY_FREQUENCY_THRESHOLD =
		"frequencyThreshold";

	public static final String PREFERENCE_KEY_ORDER = "order";

	public static final String PREFERENCE_KEY_PARAMETER_NAME = "parameterName";

	public static final String PREFERENCE_KEY_RANGES = "ranges";

	public String getAggregationField();

	public String getCustomHeading();

	public String getFederatedSearchKey();

	public int getFrequencyThreshold();

	public String getOrder();

	public String getParameterName();

	public JSONArray getRangesJSONArray();

	public String getRangesString();

	public boolean isFrequenciesVisible();

}