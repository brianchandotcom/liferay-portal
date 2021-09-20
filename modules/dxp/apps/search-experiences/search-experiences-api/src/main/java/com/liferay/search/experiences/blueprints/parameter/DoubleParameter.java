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

package com.liferay.search.experiences.blueprints.parameter;

import com.liferay.petra.string.StringBundler;
import com.liferay.search.experiences.blueprints.parameter.visitor.EvaluationVisitor;
import com.liferay.search.experiences.blueprints.parameter.visitor.ToStringVisitor;
import com.liferay.search.experiences.exception.SXPParameterEvaluationException;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class DoubleParameter extends BaseParameter implements SXPParameter {

	public DoubleParameter(
		String name, boolean templateVariable, Double value) {

		super(name, templateVariable);

		_value = value;
	}

	@Override
	public boolean accept(EvaluationVisitor visitor)
		throws SXPParameterEvaluationException {

		return visitor.visit(this);
	}

	@Override
	public String accept(ToStringVisitor visitor, Map<String, String> options)
		throws Exception {

		return visitor.visit(this, options);
	}

	public boolean equalsTo(Double value) {
		if (_value.doubleValue() == value.doubleValue()) {
			return true;
		}

		return false;
	}

	@Override
	public Double getValue() {
		return _value;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("DoubleParameter [name=");
		sb.append(name);
		sb.append(", templateVariable=");
		sb.append(templateVariable);
		sb.append(", _value=");
		sb.append(_value);
		sb.append("]");

		return sb.toString();
	}

	private final Double _value;

}