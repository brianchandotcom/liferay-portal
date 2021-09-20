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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.List;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=commerce",
	service = SXPParameterContributor.class
)
public class CommerceSXPParameterContributor
	implements SXPParameterContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		_addAccountGroupIds(searchRequestBuilder, sxpParameterDataBuilder);

		_addChannelGroupId(searchRequestBuilder, sxpParameterDataBuilder);
	}

	@Override
	public String getCategoryNameKey() {
		return "commerce";
	}

	@Override
	public List<SXPParameterContributionDefinition>
		getSXPParameterContributionDefinitions() {

		return ListUtil.fromArray(
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "account-group-ids",
				"commerce.account_group_ids"),
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "channel-group-id",
				"commerce.channel_group_id"));
	}

	private void _addAccountGroupIds(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		long[] accountGroupIds = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getLongValues(
				searchContext.getAttribute("commerceAccountGroupIds")));

		if (accountGroupIds.length > 0) {
			sxpParameterDataBuilder.addSXPParameter(
				new LongArraySXPParameter(
					"commerce.account_group_ids", true,
					_toBoxedArray(accountGroupIds)));
		}
	}

	private void _addChannelGroupId(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		long channelGroupId = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getLong(
				searchContext.getAttribute("commerceChannelGroupId")));

		if (channelGroupId > 0) {
			sxpParameterDataBuilder.addSXPParameter(
				new LongSXPParameter(
					"commerce.channel_group_id", true, channelGroupId));
		}
	}

	private Long[] _toBoxedArray(long[] array) {
		return LongStream.of(
			array
		).boxed(
		).toArray(
			Long[]::new
		);
	}

}