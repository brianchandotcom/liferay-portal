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

package com.liferay.portal.search.tuning.blueprints.engine.internal.parameter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterDataBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = ParameterDataBuilder.class)
public class ParameterDataBuilderImpl implements ParameterDataBuilder {

	@Override
	public ParameterDataBuilder addParameter(Parameter parameter) {
		if (!_parameterExists(parameter)) {
			_parameters.add(parameter);
		}

		return this;
	}

	@Override
	public ParameterData build() {
		return new ParameterDataImpl(_keywords, _parameters);
	}

	@Override
	public ParameterDataBuilder keywords(String keywords) {
		_keywords = keywords;

		return this;
	}

	private boolean _parameterExists(Parameter parameter) {
		String name = parameter.getName();

		Stream<Parameter> stream = _parameters.stream();

		Optional<Parameter> parameterOptional = stream.filter(
			p -> name.equals(p.getName())
		).findFirst();

		return parameterOptional.isPresent();
	}

	private String _keywords = StringPool.BLANK;
	private final List<Parameter> _parameters = new ArrayList<>();

}