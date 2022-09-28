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

package com.liferay.search.experiences.federation.internal.index;

import com.liferay.portal.search.spi.index.IndexDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gustavo Lima
 */
@Component(
	enabled = false, immediate = true,
	property = {
		IndexDefinition.PROPERTY_KEY_INDEX_NAME + "=" + FederatedContentIndexDefinition.INDEX_NAME,
		IndexDefinition.PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME + "=" + FederatedContentIndexDefinition.TYPE_MAPPING_FILE_NAME
	},
	service = IndexDefinition.class
)
public class FederatedContentIndexDefinition implements IndexDefinition {

	public static final String INDEX_NAME = "federated-content";

	public static final String TYPE_MAPPING_FILE_NAME =
		"federated-content.json";

	public static final String TYPE_NAME = "_doc";

}