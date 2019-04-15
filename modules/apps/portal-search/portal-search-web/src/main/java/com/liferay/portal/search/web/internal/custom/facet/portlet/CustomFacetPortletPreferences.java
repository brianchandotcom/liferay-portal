/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet;

import java.util.Optional;

/**
 * @author Wade Cao
 */
public interface CustomFacetPortletPreferences {

	public static final String PREFERENCE_KEY_AGGREGATION_FIELD =
		"aggregationField";

	public static final String PREFERENCE_KEY_CUSTOM_HEADING = "customHeading";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FREQUENCIES_VISIBLE =
		"frequenciesVisible";

	public static final String PREFERENCE_KEY_FREQUENCY_THRESHOLD =
		"frequencyThreshold";

	public static final String PREFERENCE_KEY_MAX_TERMS = "maxTerms";

	public static final String PREFERENCE_KEY_PARAMETER_NAME = "parameterName";

	public Optional<String> getAggregationFieldOptional();

	public String getAggregationFieldString();

	public Optional<String> getCustomHeadingOptional();

	public String getCustomHeadingString();

	public Optional<String> getFederatedSearchKeyOptional();

	public String getFederatedSearchKeyString();

	public int getFrequencyThreshold();

	public int getMaxTerms();

	public Optional<String> getParameterNameOptional();

	public String getParameterNameString();

	public boolean isFrequenciesVisible();

}