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

package com.liferay.portal.search.tuning.blueprints.commerce.internal.parameter;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.engine.constants.ReservedParameterNames;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongArrayParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.LongParameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDefinition;
import com.liferay.portal.search.tuning.blueprints.engine.spi.parameter.ParameterContributor;
import com.liferay.portal.search.tuning.blueprints.message.Messages;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=commerce",
	service = ParameterContributor.class
)
public class CommerceParameterContributor implements ParameterContributor {

	@Override
	public void contribute(
		ParameterDataBuilder parameterDataBuilder, Blueprint blueprint,
		BlueprintsAttributes blueprintsAttributes, Messages messages) {

		_addAccountGroupIds(parameterDataBuilder, blueprintsAttributes);

		_addChannelGroupId(parameterDataBuilder, blueprintsAttributes);
	}

	@Override
	public String getCategoryNameKey() {
		return "commerce";
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.COMMERCE_ACCOUNT_GROUP_IDS.getKey()),
				LongParameter.class.getName(),
				"commerce.parameter.account-group-ids"));

		parameterDefinitions.add(
			new ParameterDefinition(
				_getTemplateVariableName(
					ReservedParameterNames.COMMERCE_CHANNEL_GROUP_ID.getKey()),
				LongParameter.class.getName(),
				"commerce.parameter.channel-group-id"));

		return parameterDefinitions;
	}

	private void _addAccountGroupIds(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> optional = blueprintsAttributes.getAttributeOptional(
			ReservedParameterNames.COMMERCE_ACCOUNT_GROUP_IDS.getKey());

		if (!optional.isPresent()) {
			return;
		}

		long[] accountGroupIds = GetterUtil.getLongValues(optional.get());

		parameterDataBuilder.addParameter(
			new LongArrayParameter(
				ReservedParameterNames.COMMERCE_ACCOUNT_GROUP_IDS.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.COMMERCE_ACCOUNT_GROUP_IDS.getKey()),
				_toBoxedArray(accountGroupIds)));
	}

	private void _addChannelGroupId(
		ParameterDataBuilder parameterDataBuilder,
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> optional = blueprintsAttributes.getAttributeOptional(
			ReservedParameterNames.COMMERCE_CHANNEL_GROUP_ID.getKey());

		if (!optional.isPresent()) {
			return;
		}

		parameterDataBuilder.addParameter(
			new LongParameter(
				ReservedParameterNames.COMMERCE_CHANNEL_GROUP_ID.getKey(),
				_getTemplateVariableName(
					ReservedParameterNames.COMMERCE_CHANNEL_GROUP_ID.getKey()),
				GetterUtil.getLong(optional.get())));
	}

	private String _getTemplateVariableName(String key) {
		StringBundler sb = new StringBundler(3);

		sb.append("${commerce.");
		sb.append(key);
		sb.append("}");

		return sb.toString();
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