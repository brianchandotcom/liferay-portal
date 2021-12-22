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

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.Source;

/**
 * @author Gustavo Lima
 */
public class AdvancedSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		AdvancedConfiguration advancedConfiguration =
			configuration.getAdvancedConfiguration();

		if (advancedConfiguration == null) {
			return;
		}

		Source source = advancedConfiguration.getSource();

		if (source == null) {
			return;
		}

		if (source.getExcludes() != null) {
			searchRequestBuilder.fetchSourceExcludes(source.getExcludes());
		}

		if (source.getFetchSource() != null) {
			searchRequestBuilder.fetchSource(source.getFetchSource());
		}

		if (source.getIncludes() != null) {
			searchRequestBuilder.fetchSourceIncludes(source.getIncludes());
		}
	}

	@Override
	public String getName() {
		return "advancedConfiguration";
	}

}