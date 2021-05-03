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

import com.liferay.portal.search.tuning.blueprints.engine.parameter.Parameter;
import com.liferay.portal.search.tuning.blueprints.engine.parameter.ParameterData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class ParameterDataImpl implements ParameterData {

	public ParameterDataImpl(String keywords, List<Parameter> parameters) {
		_keywords = keywords;
		_parameters = parameters;
	}

	@Override
	public Optional<Parameter> getByNameOptional(String name) {
		Stream<Parameter> stream = _parameters.stream();

		return stream.filter(
			p -> name.equals(p.getName())
		).findFirst();
	}

	@Override
	public Optional<Parameter> getByTemplateVariableNameOptional(String name) {
		Stream<Parameter> stream = _parameters.stream();

		return stream.filter(
			p -> name.equals(p.getTemplateVariable())
		).findFirst();
	}

	@Override
	public String getKeywords() {
		return _keywords;
	}

	@Override
	public List<Parameter> getParameters() {
		return _parameters;
	}

	private final String _keywords;
	private final List<Parameter> _parameters;

}