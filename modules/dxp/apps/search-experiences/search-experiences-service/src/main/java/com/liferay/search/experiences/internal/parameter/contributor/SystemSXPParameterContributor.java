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

package com.liferay.search.experiences.internal.parameter.contributor;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.search.experiences.attributes.SXPAttributes;
import com.liferay.search.experiences.internal.attributes.util.SXPAttributeValueHelper;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.SXPParameterContributor;
import com.liferay.search.experiences.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.parameter.SXPParameterDefinition;
import com.liferay.search.experiences.parameter.StringArrayParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes, SXPBlueprint sxpBlueprint) {

		_addExcludedSearchRequestBodyContributors(
			sxpParameterDataBuilder, sxpAttributes);

		_addExplainParameter(sxpParameterDataBuilder, sxpAttributes);

		_addIncludeResponseStringParameter(
			sxpParameterDataBuilder, sxpAttributes);

		_addPreviewParameter(sxpParameterDataBuilder, sxpAttributes);
	}

	@Override
	public String getCategoryNameKey() {
		return "system";
	}

	@Override
	public List<SXPParameterDefinition> getParameterDefinitions() {
		return new ArrayList<>();
	}

	private void _addExcludedSearchRequestBodyContributors(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<String[]> excludedSearchRequestBodyContributorsOptional =
			_sxpAttributeValuesHelper.getStringArrayOptional(
				sxpAttributes, "excluded_search_request_body_contributors");

		if (!excludedSearchRequestBodyContributorsOptional.isPresent()) {
			return;
		}

		sxpParameterDataBuilder.addParameter(
			new StringArrayParameter(
				"excluded_search_request_body_contributors", null,
				GetterUtil.getStringValues(
					excludedSearchRequestBodyContributorsOptional.get())));
	}

	private void _addExplainParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Boolean> optional =
			_sxpAttributeValuesHelper.getBooleanOptional(
				sxpAttributes, "explain");

		if (optional.isPresent()) {
			sxpParameterDataBuilder.addParameter(
				new BooleanParameter("explain", "${explain}", optional.get()));
		}
	}

	private void _addIncludeResponseStringParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Boolean> optional =
			_sxpAttributeValuesHelper.getBooleanOptional(
				sxpAttributes, "include_response_string");

		if (optional.isPresent()) {
			sxpParameterDataBuilder.addParameter(
				new BooleanParameter(
					"include_response_string", "${include_response_string}",
					optional.get()));
		}
	}

	private void _addPreviewParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder,
		SXPAttributes sxpAttributes) {

		Optional<Boolean> optional =
			_sxpAttributeValuesHelper.getBooleanOptional(
				sxpAttributes, "preview");

		if (optional.isPresent()) {
			sxpParameterDataBuilder.addParameter(
				new BooleanParameter("preview", null, optional.get()));
		}
	}

	@Reference
	private SXPAttributeValueHelper _sxpAttributeValuesHelper;

}