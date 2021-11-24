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

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.rest.dto.v1_0.AggregationConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.QueryConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Rescore;
import com.liferay.search.experiences.rest.dto.v1_0.SortConfiguration;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public class ConfigurationUtil {

	public static Configuration toConfiguration(String json) {
		if (Validator.isNull(json)) {
			return null;
		}

		return unpack(Configuration.unsafeToDTO(json));
	}

	protected static Configuration unpack(Configuration configuration) {
		if (configuration == null) {
			return null;
		}

		AggregationConfiguration aggregationConfiguration =
			configuration.getAggregationConfiguration();

		if ((aggregationConfiguration != null) &&
			(aggregationConfiguration.getAggs() instanceof Map)) {

			aggregationConfiguration.setAggs(
				JSONFactoryUtil.createJSONObject(
					(Map<?, ?>)aggregationConfiguration.getAggs()));
		}

		QueryConfiguration queryConfiguration =
			configuration.getQueryConfiguration();

		if (queryConfiguration != null) {
			ArrayUtil.isNotEmptyForEach(
				queryConfiguration.getQueryEntries(),
				queryEntry -> {
					ArrayUtil.isNotEmptyForEach(
						queryEntry.getClauses(), ConfigurationUtil::_unpack);
					ArrayUtil.isNotEmptyForEach(
						queryEntry.getPostFilterClauses(),
						ConfigurationUtil::_unpack);
					ArrayUtil.isNotEmptyForEach(
						queryEntry.getRescores(), ConfigurationUtil::_unpack);
				});
		}

		SortConfiguration sortConfiguration =
			configuration.getSortConfiguration();

		if ((sortConfiguration != null) &&
			(sortConfiguration.getSorts() != null)) {

			sortConfiguration.setSorts(
				JSONFactoryUtil.createJSONArray(
					(Object[])sortConfiguration.getSorts()));
		}

		return configuration;
	}

	private static void _unpack(Clause clause) {
		if (clause.getQuery() instanceof Map) {
			clause.setQuery(
				JSONFactoryUtil.createJSONObject((Map<?, ?>)clause.getQuery()));
		}
		else if (clause.getQuery() instanceof String) {
			try {
				clause.setQuery(
					JSONFactoryUtil.createJSONObject(
						(String)clause.getQuery()));
			}
			catch (JSONException jsonException) {
			}
		}
	}

	private static void _unpack(Rescore rescore) {
		if (rescore.getQuery() instanceof Map) {
			rescore.setQuery(
				JSONFactoryUtil.createJSONObject(
					(Map<?, ?>)rescore.getQuery()));
		}
	}

}