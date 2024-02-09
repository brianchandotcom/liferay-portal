/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.search.api;

import com.liferay.portal.search.index.IndexNameBuilder;

/**
 * @author Shuyang Zhou
 */
public class IndexNamePatterns {

	public static final String FORECAST = "%s-commerce-ml-forecast";

	public static final String FREQUENT_PATTERN_RECOMMENDATION =
		"%s-frequent-pattern-commerce-ml-recommendation";

	public static final String PRODUCT_CONTENT_RECOMMENDATION =
		"%s-product-content-commerce-ml-recommendation";

	public static final String PRODUCT_INTERACTION_RECOMMENDATION =
		"%s-product-interaction-commerce-ml-recommendation";

	public static final String USER_RECOMMENDATION =
		"%s-user-commerce-ml-recommendation";

	public static String getIndexName(
		IndexNameBuilder indexNameBuilder, String indexNamePattern,
		long companyId) {

		return String.format(
			indexNamePattern, indexNameBuilder.getIndexName(companyId));
	}

}