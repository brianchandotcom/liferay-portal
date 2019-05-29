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

package com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences;

import java.util.Optional;

/**
 * @author Wade Cao
 */
public interface LowLevelSearchOptionsPortletPreferences {

	public static final String PREFERENCE_KEY_CONTRIBUTORS_TO_EXCLUDE =
		"contributorsToExclude";

	public static final String PREFERENCE_KEY_CONTRIBUTORS_TO_INCLUDE =
		"contributorsToInclude";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public static final String PREFERENCE_KEY_FIELDS_TO_RETURN =
		"fieldsToReturn";

	public static final String PREFERENCE_KEY_INDEXES = "indexes";

	public Optional<String> getContributorsToExcludeOptional();

	public String getContributorsToExcludeString();

	public Optional<String> getContributorsToIncludeOptional();

	public String getContributorsToIncludeString();

	public Optional<String> getFederatedSearchKeyOptional();

	public String getFederatedSearchKeyString();

	public Optional<String> getFieldsToReturnOptional();

	public String getFieldsToReturnString();

	public Optional<String> getIndexesOptional();

	public String getIndexesString();

}