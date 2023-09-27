/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.site.facet.portlet;

/**
 * @author André de Oliveira
 */
public interface SiteFacetPortletPreferences {

	public static final String PREFERENCE_KEY_FREQUENCIES_VISIBLE =
		"frequenciesVisible";

	public static final String PREFERENCE_KEY_FREQUENCY_THRESHOLD =
		"frequencyThreshold";

	public static final String PREFERENCE_KEY_MAX_TERMS = "maxTerms";

	public static final String PREFERENCE_KEY_ORDER = "order";

	public static final String PREFERENCE_KEY_PARAMETER_NAME = "parameterName";

	public int getFrequencyThreshold();

	public int getMaxTerms();

	public String getOrder();

	public String getParameterName();

	public boolean isFrequenciesVisible();

}