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

package com.liferay.search.experiences.parameter.visitor;

import com.liferay.search.experiences.exception.ParameterEvaluationException;
import com.liferay.search.experiences.parameter.BooleanParameter;
import com.liferay.search.experiences.parameter.DateParameter;
import com.liferay.search.experiences.parameter.DoubleParameter;
import com.liferay.search.experiences.parameter.FloatParameter;
import com.liferay.search.experiences.parameter.IntegerArrayParameter;
import com.liferay.search.experiences.parameter.IntegerParameter;
import com.liferay.search.experiences.parameter.LongArrayParameter;
import com.liferay.search.experiences.parameter.LongParameter;
import com.liferay.search.experiences.parameter.StringArrayParameter;
import com.liferay.search.experiences.parameter.StringParameter;

/**
 * @author Petteri Karttunen
 */
public interface EvaluationVisitor {

	public boolean visit(BooleanParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(DateParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(DoubleParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(FloatParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(IntegerArrayParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(IntegerParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(LongArrayParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(LongParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(StringArrayParameter parameter)
		throws ParameterEvaluationException;

	public boolean visit(StringParameter parameter)
		throws ParameterEvaluationException;

}