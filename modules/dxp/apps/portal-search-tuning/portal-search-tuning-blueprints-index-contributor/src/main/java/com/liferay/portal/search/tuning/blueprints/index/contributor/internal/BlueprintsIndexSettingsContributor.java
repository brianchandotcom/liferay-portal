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

package com.liferay.portal.search.tuning.blueprints.index.contributor.internal;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch7.settings.IndexSettingsContributor;
import com.liferay.portal.search.elasticsearch7.settings.IndexSettingsHelper;
import com.liferay.portal.search.elasticsearch7.settings.TypeMappingsHelper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false, immediate = true, service = IndexSettingsContributor.class
)
public class BlueprintsIndexSettingsContributor
	implements IndexSettingsContributor {

	@Override
	public int compareTo(IndexSettingsContributor indexSettingsContributor) {
		if (getPriority() > indexSettingsContributor.getPriority()) {
			return 1;
		}
		else if (getPriority() == indexSettingsContributor.getPriority()) {
			return 0;
		}

		return -1;
	}

	@Override
	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper) {

		String mappingSource = StringUtil.read(
			getClass(), _INDEX_SETTINGS_RESOURCE_NAME);

		typeMappingsHelper.addTypeMappings(indexName, mappingSource);
	}

	@Override
	public int getPriority() {
		return 1;
	}

	@Override
	public void populate(IndexSettingsHelper indexSettingsHelper) {
	}

	private static final String _INDEX_SETTINGS_RESOURCE_NAME =
		"/META-INF/search/liferay-search-tuning-blueprints-index-" +
			"contributor.json";

}