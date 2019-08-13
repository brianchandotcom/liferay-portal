/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.elasticsearch6.spi.index.IndexDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		IndexDefinition.PROPERTY_KEY_INDEX_NAME + "=" + RankingIndexDefinition.INDEX_NAME,
		IndexDefinition.PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME + "=" + RankingIndexDefinition.INDEX_SETTINGS_RESOURCE_NAME
	},
	service = IndexDefinition.class
)
public class RankingIndexDefinition implements IndexDefinition {

	public static final String INDEX_NAME = "liferay-search-tuning-rankings";

	public static final String INDEX_SETTINGS_RESOURCE_NAME =
		"/META-INF/search/liferay-search-tuning-rankings-index.json";

}