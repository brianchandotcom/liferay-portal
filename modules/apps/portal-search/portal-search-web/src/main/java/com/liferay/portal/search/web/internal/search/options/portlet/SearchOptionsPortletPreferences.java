/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.options.portlet;

/**
 * @author Wade Cao
 */
public interface SearchOptionsPortletPreferences {

	public static final String PREFERENCE_KEY_ALLOW_EMPTY_SEARCHES =
		"allowEmptySearches";

	public static final String PREFERENCE_KEY_BASIC_FACET_SELECTION =
		"basicFacetSelection";

	public static final String PREFERENCE_KEY_FEDERATED_SEARCH_KEY =
		"federatedSearchKey";

	public String getFederatedSearchKey();

	public boolean isAllowEmptySearches();

	public boolean isBasicFacetSelection();

}