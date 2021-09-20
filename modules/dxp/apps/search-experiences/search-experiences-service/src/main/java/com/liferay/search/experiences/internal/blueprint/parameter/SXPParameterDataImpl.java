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

import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 */
public class SXPParameterDataImpl implements SXPParameterData {

	public SXPParameterDataImpl(
		String keywords,List<SXPParameter> sxpParameters) {

		_keywords = keywords;
		_sxpParameters = sxpParameters;
	}

	@Override
	public Optional<SXPParameter> getSXPParameterOptionalByName(String name) {
		Stream<SXPParameter> stream = _sxpParameters.stream();

		return stream.filter(
			p -> name.equals(p.getName())
		).findFirst();
	}

	@Override
	public Optional<SXPParameter> getSXPParameterOptionalByNameTemplateVariableName(
		String name) {

		Stream<SXPParameter> stream = _sxpParameters.stream();

		return stream.filter(
			p -> name.equals(p.getTemplateVariable())
		).findFirst();
	}
	
	@Override
	public String getKeywords() {
		return _keywords;
	}

	@Override
	public List<SXPParameter> getSXPParameters() {
		return _sxpParameters;
	}

	private final String _keywords;
	private final List<SXPParameter> _sxpParameters;

}