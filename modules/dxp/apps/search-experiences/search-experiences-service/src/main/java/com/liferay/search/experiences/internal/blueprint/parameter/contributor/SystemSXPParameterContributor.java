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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=system",
	service = SXPParameterContributor.class
)
public class SystemSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		_addExcludedSearchRequestBodyContributors(
			searchRequestBuilder, sxpParameterDataBuilder);

		_addExplainParameter(searchRequestBuilder, sxpParameterDataBuilder);

		_addIncludeResponseStringParameter(
			searchRequestBuilder, sxpParameterDataBuilder);

		_addPreviewParameter(searchRequestBuilder, sxpParameterDataBuilder);
	}

	@Override
	public String getCategoryNameKey() {
		return "system";
	}

	@Override
	public List<SXPParameterContributionDefinition>
		getSXPParameterContributionDefinitions() {

		return new ArrayList<>();
	}

	private void _addExcludedSearchRequestBodyContributors(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		String[] value = SearchContextUtil.getStringArrayAttribute(
			"search.experiences.excluded_search_request_body_contributors",
			searchRequestBuilder);

		if (value != null) {
			sxpParameterDataBuilder.addSXPParameter(
				new StringArraySXPParameter(
					"system.excluded_search_request_body_contributors", false,
					GetterUtil.getStringValues(value)));
		}
	}

	private void _addExplainParameter(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Boolean value = SearchContextUtil.getBooleanAttribute(
			"search.experiences.explain", searchRequestBuilder);

		if (value != null) {
			sxpParameterDataBuilder.addSXPParameter(
				new BooleanSXPParameter("explain", false, value));
		}
	}

	private void _addIncludeResponseStringParameter(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Boolean value = SearchContextUtil.getBooleanAttribute(
			"search.experiences.include_response_string", searchRequestBuilder);

		if (value != null) {
			sxpParameterDataBuilder.addSXPParameter(
				new BooleanSXPParameter(
					"system.include_response_string", false, value));
		}
	}

	private void _addPreviewParameter(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Boolean value = SearchContextUtil.getBooleanAttribute(
			"search.experiences.preview", searchRequestBuilder);

		if (value != null) {
			sxpParameterDataBuilder.addSXPParameter(
				new BooleanSXPParameter("preview", false, value));
		}
	}

}