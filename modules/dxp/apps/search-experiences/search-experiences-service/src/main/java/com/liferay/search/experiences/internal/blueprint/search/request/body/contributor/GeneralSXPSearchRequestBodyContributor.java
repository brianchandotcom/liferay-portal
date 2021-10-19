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
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.General;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

/**
 * @author André de Oliveira
 */
public class GeneralSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterData sxpParameterData) {

		Configuration configuration = sxpBlueprint.getConfiguration();

		General general = configuration.getGeneral();

		if (general == null) {
			return;
		}

		if (general.getEmptySearchEnabled() != null) {
			searchRequestBuilder.emptySearchEnabled(
				general.getEmptySearchEnabled());
		}

		if (general.getExplain() != null) {
			searchRequestBuilder.explain(general.getExplain());
		}

		if (general.getIncludeResponseString() != null) {
			searchRequestBuilder.includeResponseString(
				general.getIncludeResponseString());
		}
	}

	@Override
	public String getName() {
		return "general";
	}

}