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

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.petra.string.StringPool;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPParameterDataBuilder.class)
public class SXPParameterDataBuilderImpl implements SXPParameterDataBuilder {

	@Override
	public SXPParameterDataBuilder addSXPParameter(SXPParameter sxpParameter) {
		if (!_parameterExists(sxpParameter)) {
			_sxpParameters.add(sxpParameter);
		}

		return this;
	}

	@Override
	public SXPParameterData build() {
		return new SXPParameterDataImpl(_keywords, _sxpParameters);
	}

	@Override
	public SXPParameterDataBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	private boolean _parameterExists(SXPParameter sxpParameter) {
		String name = sxpParameter.getName();

		Stream<SXPParameter> stream = _sxpParameters.stream();

		Optional<SXPParameter> optional = stream.filter(
			p -> name.equals(p.getName())
		).findFirst();

		return optional.isPresent();
	}

	private String _keywords = StringPool.BLANK;
	private final List<SXPParameter> _sxpParameters = new ArrayList<>();

}