/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.constants;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSearchResultsConstants {

	public static final String CATEGORY_KEY_PAGINATION = "pagination";

	public static final String CATEGORY_KEY_RENDER_SELECTION =
		"render-selection";

	public static final String FORM_NAVIGATOR_ID_CONFIGURATION =
		"search.results.configuration";

	public static final String SORT_OPTION_DEFAULT = "relevance";

	public static final String[] SORT_OPTIONS = {
		"relevance", "price-low-to-high", "price-high-to-low", "new-items",
		"name-ascending", "name-descending"
	};

}