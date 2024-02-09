/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.internal.recommendation.search.index;

import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.commerce.machine.learning.internal.search.index.BaseCommerceMLIndexer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(service = CommerceMLIndexer.class)
public class FrequentPatternRecommendationCommerceMLIndexer
	extends BaseCommerceMLIndexer {

	public FrequentPatternRecommendationCommerceMLIndexer() {
		super(_INDEX_MAPPING_FILE_NAME, _INDEX_NAME_PATTERN);
	}

	private static final String _INDEX_MAPPING_FILE_NAME =
		"frequent-pattern-commerce-ml-recommendation-mappings.json";

	private static final String _INDEX_NAME_PATTERN =
		"%s-frequent-pattern-commerce-ml-recommendation";

}