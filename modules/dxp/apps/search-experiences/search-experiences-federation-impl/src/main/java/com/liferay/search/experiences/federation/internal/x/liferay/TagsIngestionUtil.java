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

package com.liferay.search.experiences.federation.internal.x.liferay;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.search.experiences.federation.configuration.IngestionConfiguration;
import com.liferay.search.experiences.federation.internal.index.FederatedContentFields;
import com.liferay.search.experiences.federation.internal.ingest.TagResolver;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class TagsIngestionUtil {

	public static void ingestTags(
		DocumentBuilder documentBuilder,
		IngestionConfiguration ingestionConfiguration, List<String> tags) {

		TagResolver tagResolver = new TagResolver(
			ingestionConfiguration.tagEquivalences(),
			FederatedContentFields.TAGS,
			ingestionConfiguration.tagTransformations());

		MapUtil.isNotEmptyForEach(
			tagResolver.resolve(tags),
			(fieldName, values) -> documentBuilder.setStrings(
				fieldName, ArrayUtil.toStringArray(values)));
	}

}